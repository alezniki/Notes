package com.nikola.notes.db.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Dzoni on 5/23/2017.
 */

@DatabaseTable(tableName = Note.TABLE_NOTES)
public class Note {

    // Table Name
    public static final String TABLE_NOTES = "notes";

    // Table Columns
    public static final String NOTE_ID = "id";
    public static final String NOTE_TITLE = "title";
    public static final String NOTE_CONTENT = "content";

    @DatabaseField(columnName = NOTE_ID, generatedId = true)
    private int id;
    @DatabaseField(columnName = NOTE_TITLE)
    private String title;
    @DatabaseField(columnName = NOTE_CONTENT)
    private String content;

    // Default constructor is needed for the SQLite
    public Note(){

    }

    public Note(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
//        return super.toString();
        return title + ", " + content;
    }
}
