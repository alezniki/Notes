package com.nikola.notes.model;

/**
 * Created by Dzoni on 5/23/2017.
 */

public class Note {
    private String title;
    private String content;

    public Note(){

    }
    public Note(String title, String content) {
        this.title = title;
        this.content = content;
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
}
