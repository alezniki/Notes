package com.nikola.notes.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nikola.notes.R;
import com.nikola.notes.model.Note;

import java.util.List;

/**
 * Created by nikola on 6/8/17.
 */

public class NoteAdapter extends ArrayAdapter<Note> {

    //View lookup cache: To improve performance for faster item loading
    private static class ViewHolder {
        TextView tvTitle;
        TextView tvText;
    }

    public NoteAdapter(Context context, List<Note> notes) {

        super(context, 0, notes);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        // Get the data item for this position
        Note note = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.list_items,parent,false);

            // Lookup view for data population
            viewHolder.tvTitle = (TextView) view.findViewById(R.id.tv_note_title);
            viewHolder.tvText = (TextView) view.findViewById(R.id.tv_note_content);

            // Cache the viewHolder object inside the fresh view
            view.setTag(viewHolder);
        } else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) view.getTag();
        }

        // Populate the data from the data object via the viewHolder object into the template view.
        viewHolder.tvTitle.setText(note.getTitle());
        viewHolder.tvText.setText(note.getContent());

        // Return the completed view to render on screen
        return view;
    }
}
