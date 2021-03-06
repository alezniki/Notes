package com.nikola.notes.activities;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.nikola.notes.R;
import com.nikola.notes.adapters.NoteAdapter;
import com.nikola.notes.db.DataBaseHelper;
import com.nikola.notes.db.model.Note;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
//    private Context context;

    public final int REQUEST_SAVE_ACTIVITY = 1;
    //public final int REQUEST_NOTE_DETAIL_ACTIVITY = 2;

    private DataBaseHelper dataBaseHelper = null;
    private SharedPreferences preferences;

    private Toolbar toolbar;
    private FloatingActionButton fabAdd;
    private TextView tvNoteTitle;
    private TextView tvNoteContent;

    Note note;


    private NoteAdapter adapter;
    private ListView listView;
    private List<Note> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Add Toolbar
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tvNoteTitle = (TextView)findViewById(R.id.tv_note_title);
        tvNoteContent = (TextView)findViewById(R.id.tv_note_content);
        fabAdd = (FloatingActionButton) findViewById(R.id.btn_add_note);


        // Construct the data source
        list = new ArrayList<>();
        // Create the adapter to convert the array to views
        adapter = new NoteAdapter(this, list);
        // Attach the adapter to a ListView
        listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        try {
            list = getHelper().getNoteDao().queryForAll();
            adapter.addAll(list); // Note Collection
            adapter.notifyDataSetChanged();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // This holds the value of the note position, which user has selected for further action
                if (position > -1) {
//                    Toast.makeText(MainActivity.this,"Positon: " + list.get(position).getId(), Toast.LENGTH_SHORT).show();

                    Note n = (Note) listView.getItemAtPosition(position);
                    Intent intent = new Intent(MainActivity.this, NoteDetailActivity.class);
//                    intent.putExtra("details", list.get(position).getId());
                    intent.putExtra("details", n.getId());
//                    startActivityForResult(intent,REQUEST_NOTE_DETAIL_ACTIVITY);
                    startActivity(intent);
                }
            }
        });

        // Handle the ACTION_SEARCH intent by checking for it in your onCreate() method.
        handleIntent(getIntent());


        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivityForResult(intent,REQUEST_SAVE_ACTIVITY);
            }
        });
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);

        if (listView != null && adapter != null) {
            adapter.clear();

            try {
                list = getHelper().getNoteDao().queryForAll();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            adapter.addAll(list);
            adapter.notifyDataSetChanged();

        }
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search your data somehow

            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case REQUEST_SAVE_ACTIVITY:
                if (resultCode == Activity.RESULT_OK) {

                    String title = data.getStringExtra("note_title");
                    String content = data.getStringExtra("note_content");

                    note = new Note(title,content);

                    try {
                        // Create note into database
                        getHelper().getNoteDao().create(note);

                        adapter.add(note);
                        adapter.notifyDataSetChanged();

                        boolean toast = preferences.getBoolean("allow_toast",false);
                        if (toast) Toast.makeText(this, "Note Added Successfully", Toast.LENGTH_SHORT).show();
                    } catch (SQLException e) {
                        e.printStackTrace();
                        Log.v("TAG", "ERROR ON CREATE QUERY: " + e.toString());
                    }


                } else if (requestCode == Activity.RESULT_CANCELED) {
                    tvNoteTitle.getText();
                    tvNoteContent.getText();
                }

                break;

//            case REQUEST_NOTE_DETAIL_ACTIVITY:
//                if (resultCode == Activity.RESULT_OK) {
//                    String titleUpdated = data.getStringExtra("title_updated");
//                    String contentUpdated = data.getStringExtra("content_updated");
//
//                    note = new Note(titleUpdated,contentUpdated);
//
//                    try {
//                        // Update note into database
//                        getHelper().getNoteDao().update(note);
//                        boolean toast = preferences.getBoolean("allow_toast",false);
//                        if (toast) Toast.makeText(this, "Note Updated Successfully", Toast.LENGTH_SHORT).show();
//
//                        adapter.add(note);
//                        adapter.notifyDataSetChanged();
//                    } catch (SQLException e) {
//                        e.printStackTrace();
//
//                        Log.v("TAG", "ERROR ON UPDATE QUERY: " + e.toString());
//                    }
//                } else if (requestCode == Activity.RESULT_CANCELED) {
//                    tvNoteTitle.getText();
//                    tvNoteContent.getText();
//                }
//
//                break;
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

        searchView.setBackgroundColor(Color.TRANSPARENT);
//        setStatusBarColor(this, Color.parseColor("#4CAF50"));


        searchView.setSubmitButtonEnabled(false);
        searchView.setOnQueryTextListener(this);

//        if (searchView.isShown()) {
//            searchView.onActionViewCollapsed();
//        }
        return true;
    }

    // Respond to Actions
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        switch (item.getItemId()){
//            case R.id.search:
//                return true;
            case R.id.settings:
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }

//    public void btnAddNote(View view) {
//        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
//        startActivityForResult(intent,REQUEST_SAVE_ACTIVITY);
//    }

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

//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        ListView listView = (ListView) findViewById(R.id.list_view);
//
//        if (listView != null) {
//            ArrayAdapter<Note> adapter = (ArrayAdapter<Note>) listView.getAdapter();
//
//            if (adapter != null) {
//                try {
//                    adapter.clear();
//                    List<Note> notes = getHelper().getNoteDao().queryForAll();
//
//                    adapter.addAll(notes);
//                    adapter.notifyDataSetChanged();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }


    @Override
    protected void onResume() {
        super.onResume();
        if (listView != null && adapter != null) {
            adapter.clear();

            try {
                list = getHelper().getNoteDao().queryForAll();
            } catch (SQLException e) {
                e.printStackTrace();
            }


            adapter.addAll(list);
            adapter.notifyDataSetChanged();

            handleIntent(getIntent());

        }
    }

//    This interface listen to text change events in SearchView

    @Override
    public boolean onQueryTextSubmit(String query) {
        // Triggered when the search is pressed
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        // Called when the user types each character in the text field
        adapter.getFilter().filter(newText);

        return true;
    }
    
}
