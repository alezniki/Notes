package com.nikola.notes.activities;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.nikola.notes.R;
import com.nikola.notes.adapters.NoteAdapter;
import com.nikola.notes.db.DataBaseHelper;
import com.nikola.notes.model.Note;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
//    private Context context;

    public final int REQUEST_CODE  = 1;


    Toolbar toolbar;
    FloatingActionButton btnAdd;
    TextView tvNoteTitle;
    TextView tvNoteContent;


    //RecycleView
    private RecyclerView recyclerView;
    //private RecyclerView.Adapter adapter;
    private NoteAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Note> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // In any activity just pass the context and use the singleton method
        DataBaseHelper helper = DataBaseHelper.getInstance(this);

        // Add Toolbar
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tvNoteTitle = (TextView)findViewById(R.id.tv_note_title);
        tvNoteContent = (TextView)findViewById(R.id.tv_note_content);
        btnAdd = (FloatingActionButton) findViewById(R.id.btn_add_note);

        recyclerView = (RecyclerView)findViewById(R.id.recycler);

        // 1. Use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // 2. Use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        //3. Specify an adapter
//        adapter = new NoteAdapter(myDataSet);
        list = new ArrayList<>();
        adapter = new NoteAdapter(this,list);
        recyclerView.setAdapter(adapter);

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

            finish();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {

                String title = data.getStringExtra("note_title");
                String content = data.getStringExtra("note_content");

                Note note = new Note(title,content);
                list.add(note);
                adapter.notifyDataSetChanged();


            } if (requestCode == Activity.RESULT_CANCELED) {
                tvNoteTitle.getText();
                tvNoteContent.getText();
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

        searchView.setBackgroundColor(Color.TRANSPARENT);
//        setStatusBarColor(this, Color.parseColor("#4CAF50"));

        // SET STATUS BAR COLOLR snippet code
//        Window window = this.getWindow();
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorAccent));

        return true;
    }

    // Respond to Actions
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        switch (item.getItemId()){
//            case R.id.search:
//                Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();
//                return true;
            case R.id.edit:
                Toast.makeText(this, "Edit", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void btnAddNote(View view) {
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        //startActivity(intent);
        startActivityForResult(intent,REQUEST_CODE);
    }
}
