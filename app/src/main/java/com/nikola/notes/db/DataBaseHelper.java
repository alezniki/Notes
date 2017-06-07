package com.nikola.notes.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.nikola.notes.model.Note;

import java.sql.SQLException;

/**
 * Created by nikola on 5/27/17.
 */

public class DataBaseHelper extends OrmLiteSqliteOpenHelper {

    // Database Info
    private static final String DATABASE_NAME = "notesDB";
    private static final int DATABASE_VERSION = 1;

    private Dao<Note, Integer> noteDao = null;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Note.class);
        } catch (SQLException e) {
            e.printStackTrace();
            Log.e(DataBaseHelper.class.getName(),"UNABLE TO CREATE DATABASE",e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            try {
                TableUtils.dropTable(connectionSource, Note.class,true);
            } catch (SQLException e) {
                e.printStackTrace();
                Log.e(DataBaseHelper.class.getName(),
                        "UNABLE TO UPGRADE DATABASE FROM VERSION " + oldVersion + " TO NEW " + newVersion, e);
            }
        }
    }


    // Insert, delete, read, update everything will be happened through DAOs
    public Dao<Note, Integer> getNoteDao() throws SQLException {
        if (noteDao == null){
            noteDao = getDao(Note.class);
        }

        return noteDao;
    }

    // Clear resources and close database
    @Override
    public void close() {
        noteDao = null;

        super.close();
    }
}
