package com.nikola.notes.activities;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nikola.notes.R;
import com.nikola.notes.adapter.NoteAdapter;
import com.nikola.notes.model.Note;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public final int REQUEST_CODE  = 1;

    Toolbar toolbar;
    FloatingActionButton btnAdd;
    TextView tvNoteTitle;
    TextView tvNoteText;

    NoteAdapter adapter;
    ListView listView;
    ArrayList<Note> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Add Toolbar
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tvNoteTitle = (TextView)findViewById(R.id.tv_note_title);
        tvNoteText = (TextView)findViewById(R.id.tv_note_text);
        btnAdd = (FloatingActionButton) findViewById(R.id.btn_add_note);

        // Construct the data source
        list = new ArrayList<Note>();
        // Create the adapter to convert the array to views
        adapter = new NoteAdapter(this,list);
        // Attach the adapter to a ListView
        listView = (ListView)findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        // Handle the ACTION_SEARCH intent by checking for it in your onCreate() method.
        handleIntent(getIntent());

    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search your data somehow
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {

                String title = data.getStringExtra("note_title");
                String text = data.getStringExtra("note_text");
//
//                tvNoteTitle.setText(title);
//                tvNoteText.setText(text);
                Note note = new Note(title,text);
                adapter.add(note);

            } if (requestCode == Activity.RESULT_CANCELED) {
                tvNoteTitle.getText();
                tvNoteText.getText();
            }
        }
    }

    // Inflate Menu icons
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu_main; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);//display the SearchView in the app bar

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =(SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        // Do not iconify the icon, expand it by default
        searchView.setIconifiedByDefault(false);
        searchView.setBackgroundColor(Color.WHITE);
        return true;
    }

    // Respond to Actions
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        switch (item.getItemId()){
            case R.id.search:
                Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.edit:
                Toast.makeText(this, "Edit", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void btnAddNote(View view) {
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        startActivityForResult(intent,REQUEST_CODE);
    }
}
