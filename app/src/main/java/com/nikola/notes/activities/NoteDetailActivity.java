package com.nikola.notes.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.nikola.notes.R;
import com.nikola.notes.db.DataBaseHelper;
import com.nikola.notes.model.Note;

import java.sql.SQLException;

/**
 * Created by nikola on 6/12/17.
 */

public class NoteDetailActivity extends AppCompatActivity {

    // Reference of DatabaseHelper class to access its DAOs and other components
    private DataBaseHelper dataBaseHelper = null;

    private TextView tvDetailNoteTitle;
    private TextView tvDetailNoteContent;
    private Dao<Note,Integer> noteDao;


    private Toolbar toolbar;
    private Note note = null;




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tvDetailNoteTitle = (TextView)findViewById(R.id.et_detail_note_title);
        tvDetailNoteContent = (TextView)findViewById(R.id.et_detail_note_content);

        //Receive the NoteDetails object which has sent through Intent from MainActivity
        int id = getIntent().getExtras().getInt("details");

        try {
            Note detail = getHelper().getNoteDao().queryForId(id);
            tvDetailNoteTitle.setText(detail.getTitle().toString());
            tvDetailNoteContent.setText(detail.getContent().toString());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note_detail, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        switch (item.getItemId()) {
            case R.id.update:
                updateNote();
                return true;
            case R.id.delete:
                deleteNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void deleteNote() {
        Toast.makeText(this, "Note Deleted", Toast.LENGTH_SHORT).show();
    }

    private void updateNote() {
        Toast.makeText(this, "Note Updated", Toast.LENGTH_SHORT).show();
    }

    private DataBaseHelper getHelper() {
        if (dataBaseHelper == null) {
            dataBaseHelper = OpenHelperManager.getHelper(this, DataBaseHelper.class);
        }
        return dataBaseHelper;
    }

    // You'll need this in your class to release the helper when done.
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dataBaseHelper != null) {
            OpenHelperManager.releaseHelper();
            dataBaseHelper = null;
        }
    }
}
