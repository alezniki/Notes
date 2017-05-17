package com.nikola.notes.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.nikola.notes.R;

/**
 * Created by Dzoni on 5/16/2017.
 */

public class SecondActivity extends MainActivity {

    Toolbar toolbar;
    EditText enterNote;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        enterNote = (EditText)findViewById(R.id.edit_text_enter_note);
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
        String note = String.valueOf(enterNote.getText());

        if (note.trim().isEmpty()) {
            Snackbar.make(findViewById(R.id.save),"Enter note dummy", Snackbar.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(SecondActivity.this, MainActivity.class);
            intent.putExtra("note", note); // String name, Bundle value

            startActivity(intent);
        }

    }
}
