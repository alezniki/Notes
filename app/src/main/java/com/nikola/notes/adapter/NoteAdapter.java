package com.nikola.notes.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nikola.notes.R;
import com.nikola.notes.model.Note;

import java.util.ArrayList;

/**
 * Created by Dzoni on 5/23/2017.
 */

public class NoteAdapter extends ArrayAdapter<Note> {
    public NoteAdapter(Context context, ArrayList<Note> notes) {
        super(context, 0, notes);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        // Get the data item for this position
        Note note = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.list_items, parent, false);
        }

        // Lookup view for data population
        TextView tvTitle = (TextView) view.findViewById(R.id.tv_note_title);
        TextView tvText = (TextView) view.findViewById(R.id.tv_note_text);

        // Populate the data into the template view using the data object
        tvTitle.setText(note.getTitle());
        tvText.setText(note.getText());

        // Return the completed view to render on screen
        return view;
    }
}
