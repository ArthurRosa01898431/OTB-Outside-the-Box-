package com.example.otb;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
    public void deleteData(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("myTable", "id=?", new String[]{String.valueOf(id)});
    }


    // Method to fetch puzzle data by ID
    public Cursor getPuzzleData(int puzzleId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("myTable", new String[] {"puzzle_id", "obj_number"}, "puzzle_id=?", new String[]{String.valueOf(puzzleId)}, null, null, null);
        if(cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("puzzle_id"));
            @SuppressLint("Range") int numberOfObj = cursor.getInt(cursor.getColumnIndex("obj_number"));
            // Assuming you have a PuzzleData class to hold this data
            return cursor;
        }
        return null; // Handle this case
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrade here
    }
}
    