package com.example.otb;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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

    // Read data
    public Cursor readData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM myTable";
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    // Update data
    public void updateData(int id, int puz_id, int objectiveCount, String dif) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("puzzle_id", puz_id);
        contentValues.put("obj_number", objectiveCount);
        contentValues.put("difficulty", dif);
        db.update("myTable", contentValues, "id=?", new String[]{String.valueOf(id)});
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


    /*
        Get the data of all the objectives that have been solved with
        certain parameters such as puzzleID and difficulty.

        @param puzzleId - The puzzle ID to query for. Can be 0 if specific puzzle doesn't matter.
        @param difficulty - The difficulty to query for. Can be empty if specific difficulty doesn't matter.
     */
    public Cursor getPuzzleData(final int puzzleId, final String difficulty) {
        Cursor cursor;
        SQLiteDatabase db = this.getReadableDatabase();
        // Check if both puzzleId and difficulty are provided
        if (puzzleId != -1 && !difficulty.isEmpty()) {
            cursor = db.query("myTable", new String[]{"puzzle_id", "obj_number"}, "puzzle_id=? AND difficulty=?",
                    new String[]{String.valueOf(puzzleId), difficulty}, null, null, null);
        }
        // Check if only puzzleId is provided
        else if (puzzleId != -1) {
            cursor = db.query("myTable", new String[]{"puzzle_id", "obj_number"}, "puzzle_id=?",
                    new String[]{String.valueOf(puzzleId)}, null, null, null);
        }
        // Check if only difficulty is provided
        else if (!difficulty.isEmpty()) {
            cursor = db.query("myTable", new String[]{"puzzle_id", "obj_number"}, "difficulty=?",
                    new String[]{difficulty}, null, null, null);
        }
        // Return all data if both puzzleId and difficulty are not provided
        else {
            cursor = db.query("myTable", new String[]{"puzzle_id", "obj_number"}, null,
                    null, null, null, null);
        }
        return cursor;
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrade here
    }
}
    