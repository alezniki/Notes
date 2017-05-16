package com.nikola.notes.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.nikola.notes.R;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ImageButton btnAdd;
    TextView tvNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Add Toolbar
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tvNote = (TextView)findViewById(R.id.tv_note);
        btnAdd = (ImageButton)findViewById(R.id.btn_add_note);

        Bundle saved = getIntent().getExtras(); // From SecondActivity

        if (saved != null) {
            String note = saved.getString("note");
            //The key argument here must match that used in the other activity
            tvNote.setText(note);
        } else {
            tvNote.getText();
        }

    }


    // Inflate Menu icons
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu_main; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        startActivity(intent);
    }
}
