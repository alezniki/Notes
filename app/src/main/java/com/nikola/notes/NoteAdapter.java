package com.nikola.notes;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by nikola on 5/19/17.
 */

public class NoteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //1. Create new views (invoked by the layout manager)
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }
    //2. Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    //3. Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return 0;
    }
}
