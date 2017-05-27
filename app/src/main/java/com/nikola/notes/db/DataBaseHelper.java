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


    // Create constructor matching super
    public DataBaseHelper(Context context) {
        //super(context, name, factory, version);
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }


    // Implement onCreate method
    @Override
    public void onCreate(SQLiteDatabase db) {

    }
    // Implement onUpgrade method
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
