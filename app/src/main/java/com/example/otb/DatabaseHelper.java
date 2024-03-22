package com.example.otb;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.concurrent.CountDownLatch;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "myDatabase.db";
    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_TABLE = "CREATE TABLE myTable (puzzle_id INTEGER, obj_number INTEGER, difficulty STRING)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    // Insert data
    public void insertData(int puz_id, int objectiveCount, String dif) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("puzzle_id", puz_id);
        contentValues.put("obj_number", objectiveCount);
        contentValues.put("difficulty", dif);
        db.insert("myTable", null, contentValues);
    }

    // Delete data
    public void deleteAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + "myTable");
    }

    public boolean isObjectiveNumberInDatabase(String difficulty, int puzzleID, int targetObjectiveNumber) {
        final Cursor cursor = getPuzzleData(puzzleID, difficulty);
        boolean found = false;
        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int objNumber = cursor.getInt(cursor.getColumnIndex("obj_number"));
                if (objNumber == targetObjectiveNumber) {
                    found = true;
                    break; // No need to continue searching if found
                }
            } while (cursor.moveToNext());

            cursor.close(); // Close the cursor when finished with it
        }
        return found;
    }

    public int howManyPuzzleCompleted(final String difficulty) {
        final Cursor cursor = getPuzzleData(-1 ,difficulty);
        boolean puzzle1_objective1Completed = false;
        boolean puzzle1_objective2Completed = false;

        int totalCompleted = 0;

        if (cursor != null && cursor.getCount() > 0) {
            switch(difficulty) {
                case "Easy":
                    while (cursor.moveToNext()) {
                        @SuppressLint("Range") int objNumber = cursor.getInt(cursor.getColumnIndex("obj_number"));
                        if (objNumber == 1) {
                            puzzle1_objective1Completed = true;
                        } else if (objNumber == 2) {
                            puzzle1_objective2Completed = true;
                        }
                    }
                    if (puzzle1_objective1Completed && puzzle1_objective2Completed) {
                        totalCompleted++;
                    }
            }
            cursor.close();
        }
        return totalCompleted;
    }

    /*
        Show on the UI that an objective is solved if it is already solved according
        to the data base.

        @param puzzleId - The puzzle ID to check in the database.
        @param difficulty - The difficulty to check in the database. Can be empty if specific difficulty doesn't matter.
        @param function - Lambda that shows in UI that a objective is solved which is puzzle and puzzle selector specific.
     */
    public void reflectDataOnUI(final int puzzleId, final String difficulty, final IntRunnable function) {
        final Cursor cursor = getPuzzleData(puzzleId, difficulty);
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") int objNumber = cursor.getInt(cursor.getColumnIndex("obj_number"));
                // Update UI components here
                function.run(objNumber);
            }
            cursor.close();
        }
    }

    /*
       Get the data of all the objectives that have been solved with
       certain parameters such as puzzleID and difficulty.

       @param puzzleId - The puzzle ID to query for. Can be 0 if specific puzzle doesn't matter.
       @param difficulty - The difficulty to query for. Can be empty if specific difficulty doesn't matter.
    */
    private synchronized Cursor getPuzzleData(final int puzzleId, final String difficulty) {

        final Cursor[] cursor = new Cursor[1];
        final CountDownLatch latch = new CountDownLatch(1);

        SQLiteDatabase db = this.getReadableDatabase();
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Check if both puzzleId and difficulty are provided
                if (puzzleId != -1 && !difficulty.isEmpty()) {
                    cursor[0] = db.query("myTable", new String[]{"puzzle_id", "obj_number"}, "puzzle_id=? AND difficulty=?",
                            new String[]{String.valueOf(puzzleId), difficulty}, null, null, null);
                }
                // Check if only puzzleId is provided
                else if (puzzleId != -1) {
                    cursor[0] = db.query("myTable", new String[]{"puzzle_id", "obj_number"}, "puzzle_id=?",
                            new String[]{String.valueOf(puzzleId)}, null, null, null);
                }
                // Check if only difficulty is provided
                else if (!difficulty.isEmpty()) {
                    cursor[0] = db.query("myTable", new String[]{"puzzle_id", "obj_number"}, "difficulty=?",
                            new String[]{difficulty}, null, null, null);
                }
                // Return all data if both puzzleId and difficulty are not provided
                else {
                    cursor[0] = db.query("myTable", new String[]{"puzzle_id", "obj_number"}, null,
                            null, null, null, null);
                }
                latch.countDown();

            }
        }).start();

        // Wait for the query thread to finish
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return cursor[0];
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrade here
    }
}
    