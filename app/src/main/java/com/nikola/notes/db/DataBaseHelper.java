package com.nikola.notes.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.nikola.notes.model.Note;

import java.util.ArrayList;
import java.util.List;

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


    /* TODO DATABASE CRUD OPERATIONS Create, Read, Update, Delete
    * */

    /* Inserting New Records OR Updating Existing New Records Into Database */

    public long addInsertQuery(Note note) {
        // UPDATE if note already exists, INSERT if note does not already exists

        // Create and/or open Database for writing
        SQLiteDatabase db = getWritableDatabase();
        long noteID = -1;

        // Wrap our Insert in a transaction - for database performance and consistency
        db.beginTransaction();


        try {
            ContentValues values = new ContentValues();
            values.put(KEY_NOTE_TITLE,note.getTitle());
            values.put(KEY_NOTE_CONTENT,note.getContent());

            // First try to update note in case the note already exists
            int rows = db.update(TABLE_NOTES,values,KEY_NOTE_CONTENT +"= ?" ,new String[]{note.getContent()});

            // Check if update succeeded
            if (rows == 1) {
                // Get the primary key of the note we just updated
                String selectQuery = String.format("SELECT %s FROM %s WHERE %s = ?", KEY_NOTE_ID, TABLE_NOTES, KEY_NOTE_CONTENT);
                Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(note.getContent())});
                try {
                    if (cursor.moveToFirst()) {
                        noteID = cursor.getInt(0); // columnIndex
                        db.setTransactionSuccessful();
                    }
                } finally {
                    if (cursor != null && !cursor.isClosed()) {
                        cursor.close();
                    }
                }
            } else {
                // Note does not already exists - Insert new note
                noteID = db.insertOrThrow(TABLE_NOTES,null,values);
                db.setTransactionSuccessful();
            }

        } catch (Exception e) {
            Log.v("TAG", "ERROR WHILE TRYING TO ADD OR UPDATE NOTE");
        } finally {
            db.endTransaction();
        }

        return noteID;
    }

    /* Querying Records */

    public List<Note> getAllNotes() {
        List notes = new ArrayList();

        String selectQuery = String.format("SELECT FROM %s WHERE $s = ?");

        // getReadableDatabase() and getWriteableDatabase return the same object
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    Note newNote = new Note();
                    newNote.setTitle(cursor.getString(cursor.getColumnIndex(KEY_NOTE_TITLE)));
                    newNote.setContent(cursor.getString(cursor.getColumnIndex(KEY_NOTE_CONTENT)));

                    notes.add(newNote);
                } while (cursor.moveToNext());
            }

        } catch (Exception e) {
            Log.v("TAG", "ERROR WHILE TRYING TO GET NOTE FROM DATABASE");
        }  finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return notes;
    }
}
