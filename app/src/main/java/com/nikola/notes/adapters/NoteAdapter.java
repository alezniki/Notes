package com.nikola.notes.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nikola.notes.R;
import com.nikola.notes.model.Note;

import java.util.List;

/**
 * Created by nikola on 5/19/17.
 */

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    private Context context;
    private List<Note> list;

//    private ItemClick
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView title;
        public TextView text;

        public ViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.tv_note_title);
            text = (TextView) v.findViewById(R.id.tv_note_text);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public NoteAdapter(Context context, List<Note> list) {
        this.context = context;
        this.list = list;
    }

    public NoteAdapter() {}

     // Create new views (invoked by the layout manager)
    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.list_items, parent, false);

        //ViewHolder vh = new ViewHolder(v);
        //return vh;
        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        //holder.mTextView.setText(mDataset[position]);
        Note note = list.get(position);
        holder.title.setText(note.getTitle()); // Note Title
        holder.text.setText(note.getText()); // Note Text

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return list.size();
        //return mDataset.length;
    }
}