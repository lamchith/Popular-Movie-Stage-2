package com.lmc.popularmovie.ui.helper;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.lmc.popularmovie.utility.MovieDetails;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by lmarathchathu on 1/4/2016.
 */
public class GridImageAdapter extends BaseAdapter{
    private ArrayList<MovieDetails> details;
    private Context context;
    public GridImageAdapter(Context context,ArrayList<MovieDetails> details) {
        this.details=details;
        this.context=context;
    }

    @Override
    public int getCount() {
        if(details==null)
            return 0;
        return details.size();
    }

    @Override
    public Object getItem(int i) {
        if(details==null)
            return null;
        return details.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(details==null)
            return null;
        ImageView imageView=null;
        MovieDetails entry = details.get(i);
        if (view == null) {
            imageView= new ImageView(context);
        }else{
            imageView=(ImageView)view;
        }
        //http://image.tmdb.org/t/p/w185/
        //ImageView poster = (ImageView) view.findViewById(R.id.imageView_poster);

        Picasso.with(context).load(UIConstants.IMAGE_SIZE+entry.getMoviePoster()).into(imageView);



        return imageView;
    }
}
