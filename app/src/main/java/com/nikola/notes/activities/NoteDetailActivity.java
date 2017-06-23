package com.nikola.notes.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.nikola.notes.R;
import com.nikola.notes.db.DataBaseHelper;
import com.nikola.notes.db.model.Note;

import java.sql.SQLException;

/**
 * Created by nikola on 6/12/17.
 */

public class NoteDetailActivity extends AppCompatActivity {

    // Reference of DatabaseHelper class to access its DAOs and other components
    private DataBaseHelper dataBaseHelper = null;
    private SharedPreferences preferences;

    private EditText etDetailNoteTitle;
    private EditText etDetailNoteContent;
    private Dao<Note,Integer> noteDao;

    private Toolbar toolbar;
    Note note;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //Receive the NoteDetails object which has sent through Intent from MainActivity
        int keyID = getIntent().getExtras().getInt("details");

        try {
            etDetailNoteTitle = (EditText) findViewById(R.id.et_detail_note_title);
            etDetailNoteContent = (EditText) findViewById(R.id.et_detail_note_content);

            note = getHelper().getNoteDao().queryForId(keyID);

            etDetailNoteTitle.setText(note.getTitle());
            etDetailNoteContent.setText(note.getContent());

        } catch (SQLException e) {
            e.printStackTrace();
        }

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
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
        if (note != null) {
            try {
                getHelper().getNoteDao().delete(note);
                boolean toast = preferences.getBoolean("allow_toast",false);
                if (toast) Toast.makeText(this, "Note Deleted Successfully", Toast.LENGTH_SHORT).show();
            } catch (SQLException e) {
                e.printStackTrace();
                Log.v("TAG", "ERROR ON DELETE QUERY: " + e.toString());
            }
        }
        onBackPressed();
    }

    private void updateNote() {
//        Toast.makeText(this, "Note Updated", Toast.LENGTH_SHORT).show();

//        String title = String.valueOf(etDetailNoteTitle.getText());
//        String content = String.valueOf(etDetailNoteContent.getText());

        note.setTitle(etDetailNoteTitle.getText().toString());
        note.setContent(etDetailNoteContent.getText().toString());

//        if (title.trim().isEmpty() && content.trim().isEmpty()){
//            Intent updateIntent = new Intent();
//            setResult(Activity.RESULT_CANCELED, updateIntent);
//            finish();
//        } else {
//            Intent updateIntent = new Intent();
//            updateIntent.putExtra("title_updated",title);
//            updateIntent.putExtra("content_updated",content);
//            setResult(Activity.RESULT_OK, updateIntent);
//            finish();
//        }

        try {
            getHelper().getNoteDao().update(note);
            boolean toast = preferences.getBoolean("allow_toast",false);
            if (toast) Toast.makeText(this, "Note Updated Successfully", Toast.LENGTH_SHORT).show();
            finish();
        } catch (SQLException e) {
            e.printStackTrace();
        }

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
