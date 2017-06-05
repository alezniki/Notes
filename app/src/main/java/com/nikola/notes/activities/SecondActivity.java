package com.nikola.notes.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.nikola.notes.R;

/**
 * Created by Dzoni on 5/16/2017.
 */

public class SecondActivity extends MainActivity {
//    private Context context;

    Toolbar toolbar;
    EditText etNoteTitle;
    EditText etNoteContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etNoteTitle = (EditText)findViewById(R.id.et_note_title);
        etNoteContent = (EditText)findViewById(R.id.et_note_content);
    }

    // Inflate second menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu_second; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_second, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        switch (item.getItemId()) {
            case R.id.save:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void saveNote(){
        String title = String.valueOf(etNoteTitle.getText());
        String content = String.valueOf(etNoteContent.getText());

        if (title.trim().isEmpty() && content.trim().isEmpty()) {
            Intent returnIntent = new Intent();
            setResult(Activity.RESULT_CANCELED, returnIntent);
            finish();
        } else {
            Intent returnIntent = new Intent();

            returnIntent.putExtra("note_title", title);
            returnIntent.putExtra("note_content",content);

            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        }

    }
}
