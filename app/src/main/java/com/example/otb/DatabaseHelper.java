package com.example.otb;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashSet;
import java.util.concurrent.CountDownLatch;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "myDatabase.db";
    private static final int DATABASE_VERSION = 2;
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

    public int howManyPuzzleCompleted(final String difficulty) {
        final Cursor cursor = getPuzzleData(-1 ,difficulty);
        boolean easy_puzzle1_objective1Completed = false;
        boolean easy_puzzle1_objective2Completed = false;
        boolean easy_puzzle2_objective1Completed = false;
        boolean easy_puzzle2_objective2Completed = false;
        boolean medium_puzzle2_objective2Completed = false;
        boolean medium_puzzle2_objective3Completed = false;
        boolean medium_puzzle2_objective4Completed = false;

        int totalCompleted = 0;

        if (cursor != null && cursor.getCount() > 0) {
            switch(difficulty) {
                case "Easy":
                    while (cursor.moveToNext()) {
                        @SuppressLint("Range") int objNumber = cursor.getInt(cursor.getColumnIndex("obj_number"));
                        if (objNumber == 1) {
                            easy_puzzle1_objective1Completed = true;
                        } else if (objNumber == 2) {
                            easy_puzzle1_objective2Completed = true;
                        } else if (objNumber == 3) {
                            easy_puzzle2_objective1Completed = true;
                        } else if (objNumber == 4) {
                            easy_puzzle2_objective2Completed = true;
                        } else if (objNumber == 5) {
                            totalCompleted++;
                        }
                    }
                    if (easy_puzzle1_objective1Completed && easy_puzzle1_objective2Completed) {
                        totalCompleted++;
                    }

                    if (easy_puzzle2_objective1Completed && easy_puzzle2_objective2Completed) {
                        totalCompleted++;
                    }
                    break;
                case "Medium":
                    while (cursor.moveToNext()) {
                        @SuppressLint("Range") int objNumber = cursor.getInt(cursor.getColumnIndex("obj_number"));
                        if (objNumber == 1) {
                            totalCompleted++;
                        } else if (objNumber == 2) {
                            medium_puzzle2_objective2Completed = true;
                        } else if (objNumber == 3) {
                            medium_puzzle2_objective3Completed = true;
                        } else if (objNumber == 4) {
                            medium_puzzle2_objective4Completed = true;
                        }
                    }
                    if (medium_puzzle2_objective2Completed && medium_puzzle2_objective3Completed
                            && medium_puzzle2_objective4Completed) {

                        totalCompleted++;
                    }
                    break;
                case "Hard":
                    while (cursor.moveToNext()) {
                        @SuppressLint("Range") int objNumber = cursor.getInt(cursor.getColumnIndex("obj_number"));
                        if (objNumber == 1) {
                            totalCompleted++;
                        }
                    }
                    break;
                default:
                    break;
            }
            cursor.close();
        }
        return totalCompleted;
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



    // THIS NEEDS TO CHANGE TO INCLUDE THE NEW PUZZLE FOR MEDIUM DIFFICULTY. ASK ARTHUR ABOUT THIS
    private int getTotalObjectivesForPuzzle(int puzzleId, String difficulty) {
        // This method should return the total number of objectives for a given puzzle.
        switch (puzzleId) {
            case 1:
                return 2; // Example: Puzzle 1 - Easy has 2 objectives in "Easy" difficulty.
            case 2:
                return 1; // Example: Puzzle 2 (Renaming this to Puzzle 1 Hard) has 1 objective in "Hard" difficulty.
            default:
                return 0; // Default case, if puzzle ID is unknown.
        }
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
                //@SuppressLint("Range") int objNumber = cursor.getInt(cursor.getColumnIndex("obj_number"));
                int objNumber = cursor.getInt(cursor.getColumnIndexOrThrow("obj_number"));
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
    public synchronized Cursor getPuzzleData(final int puzzleId, final String difficulty) {

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

    // needed to add since the database wasn't being upgraded correctly
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        if (oldVersion < 2) {
//            // If the column doesn't exist in version 1, add it in version 2.
//            db.execSQL("ALTER TABLE myTable ADD COLUMN obj_number INTEGER DEFAULT 0");
//        }
        // Handle further upgrades
    }

}