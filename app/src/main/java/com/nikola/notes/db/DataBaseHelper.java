package com.nikola.notes.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by nikola on 5/27/17.
 */

public class DataBaseHelper extends SQLiteOpenHelper{
    // Database Info
    private static final String DATABASE_NAME = "notesDB";
    private static final int DATABASE_VERSION = 1;

    // Table Name
    private static final String TABLE_NOTES = "notes";

    // Table Columns
    private static final String KEY_NOTE_ID = "id";
    private static final String KEY_NOTE_TITLE = "title";
    private static final String KEY_NOTE_CONTENT = "content";

    // Create Table
    private static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NOTES + "(" +
                    KEY_NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + // Define a primary key
                    KEY_NOTE_TITLE  + " TEXT " +
                    KEY_NOTE_CONTENT + " TEXT" + ")";


    // Apply singleton pattern to avoid memory leaks and unnecessary reallocation
    private static DataBaseHelper sInstance;


    // Create constructor matching super - private to prevent direct instantiation
    // Make a call to the static method getInstance() instead
    private DataBaseHelper(Context context) {
        //super(context, name, factory, version);
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    // The static getInstance() method ensures that only one DataBaseHelper will ever exists at any given time
    public static synchronized DataBaseHelper getInstance(Context context) {
        // Use the application context which will ensure that you don't accidentally leak an Activity's context
        if (sInstance == null) {
            sInstance = new DataBaseHelper(context.getApplicationContext());
        }
        return sInstance;

    }

    // Implement onCreate method
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Called when the table is created for the first time,
        // if a database already exists this method will not be called
        db.execSQL(CREATE_TABLE);

    }

    // Implement onUpgrade method
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Called when the database needs to be upgrade: Drop all old tables and recreate them
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        }

        onCreate(db);

    }
}
