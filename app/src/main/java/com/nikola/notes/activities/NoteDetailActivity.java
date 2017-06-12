package com.nikola.notes.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.nikola.notes.R;
import com.nikola.notes.db.DataBaseHelper;
import com.nikola.notes.model.Note;

/**
 * Created by nikola on 6/12/17.
 */

public class NoteDetailActivity extends AppCompatActivity implements View.OnClickListener {

    // Reference of DatabaseHelper class to access its DAOs and other components
    private DataBaseHelper dataBaseHelper = null;

    private TextView tvDetailNoteTitle;
    private TextView tvDetailNoteContent;
    private Dao<Note,Integer> noteDao;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);

        tvDetailNoteTitle = (TextView)findViewById(R.id.tv_detail_note_title);
        tvDetailNoteContent = (TextView)findViewById(R.id.tv_detail_note_content);


        //Receive the NoteDetails object which has sent through Intent from MainActivity
        Note details = (Note) getIntent().getExtras().getSerializable("details");
        tvDetailNoteTitle.setText(details.getTitle().toString());
        tvDetailNoteContent.setText(details.getContent().toString());
    }

    @Override
    public void onClick(View v) {

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
