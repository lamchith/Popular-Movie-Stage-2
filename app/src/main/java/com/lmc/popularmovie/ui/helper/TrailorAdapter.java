package com.lmc.popularmovie.ui.helper;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.lmc.popularmovie.R;
import com.lmc.popularmovie.utility.MovieTrailorDetails;
import java.util.ArrayList;

/**
 * Created by lmarathchathu on 4/17/2016.
 */
public class TrailorAdapter extends RecyclerView.Adapter<TrailorAdapter.ViewHolder> {
    private ArrayList<MovieTrailorDetails> mTrailorList;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public TextView mTextView;
        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView)v.findViewById(R.id.trailor);
            mTextView.setOnClickListener(this);
        }

        public void onClick(View v){
            final String YOUTUBE = "vnd.youtube://";
            v.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(YOUTUBE + mTextView.getHint())));

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public TrailorAdapter(ArrayList<MovieTrailorDetails> myDataset) {
        mTrailorList = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.reclycler_view_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder( v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

       MovieTrailorDetails dt= mTrailorList.get(position);
        holder.mTextView.setText("Trailor "+(position+1));
        holder.mTextView.setHint(dt.key);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mTrailorList.size();
    }

}