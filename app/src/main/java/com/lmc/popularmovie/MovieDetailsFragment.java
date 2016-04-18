package com.lmc.popularmovie;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lmc.popularmovie.db.DBHelper;
import com.lmc.popularmovie.ui.helper.GridImageAdapter;
import com.lmc.popularmovie.ui.helper.TrailorAdapter;
import com.lmc.popularmovie.ui.helper.UIConstants;
import com.lmc.popularmovie.utility.MovieApplication;
import com.lmc.popularmovie.utility.MovieDetails;
import com.lmc.popularmovie.utility.MovieTrailorDetails;
import com.lmc.popularmovie.utility.ParseJson;
import com.lmc.popularmovie.utility.ParseJsonTrailer;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailsFragment extends Fragment {

    // following is the api key for movie DB api


    ArrayList<String> list = null;
    GetTrailorsTask downloadTask = null;
    RecyclerView recyclerViewTrailor=null;

    public MovieDetailsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        ImageView imagePoster = (ImageView) view.findViewById(R.id.imageView_poster);

        TextView textViewUserRating = (TextView) view.findViewById(R.id.textView_userRating);
        TextView textViewReleaseDate = (TextView) view.findViewById(R.id.textView_releaseDate);
        TextView textViewOverview = (TextView) view.findViewById(R.id.textView_overview);
        TextView textViewTitle = (TextView) view.findViewById(R.id.textView_title);
        TextView textViewTrailor = (TextView) view.findViewById(R.id.textView_trailor);
        Button buttonFavourite = (Button) view.findViewById(R.id.button_favourite);
        buttonFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DBHelper dbHelper=new DBHelper(MovieApplication.context);

                if(list!=null&& list.size()>0){

                    dbHelper.insertMovieDetails(list);
                }

            }
        });

        recyclerViewTrailor=(RecyclerView) view.findViewById(R.id.recyclerView_trailor);
        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(MovieApplication.context);
        recyclerViewTrailor.setLayoutManager(layoutManager);

        if (list != null) {
            textViewTitle.setText(list.get(0));
            Picasso.with(MovieApplication.context).load(UIConstants.IMAGE_SIZE + list.get(1)).into(imagePoster);
            textViewOverview.setText(list.get(2));
            textViewUserRating.setText(list.get(3));
            textViewReleaseDate.setText(list.get(4));
            textViewTrailor.setText("Trailors below");

            try {
                // this is getting executed multiple times
                // keep the movie api key here
                downloadTask = (GetTrailorsTask) new GetTrailorsTask().execute(new URL("http://api.themoviedb.org/3/movie/"+list.get(5)+"/videos?api_key="));
            } catch (MalformedURLException ex) {
                Log.e("Lamchith", ex.getMessage());

            }


        }


        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
            list = getArguments().getStringArrayList("com.lmc.popularmovie.details");

        // get the movie id and git the new api end point
        //http://api.themoviedb.org/3/movie/140607/videos?api_key=c1b3f30b9499e9b6dbfabec386d13288




        }




    private class GetTrailorsTask extends AsyncTask<URL, Integer, ArrayList<MovieTrailorDetails>> {
        public ArrayList<MovieTrailorDetails> list = null;


        protected ArrayList<MovieTrailorDetails> doInBackground(URL... urls) {

            ParseJsonTrailer parseJson = new ParseJsonTrailer();
            ArrayList<MovieTrailorDetails> list = null;
            URL url = null;
            try {
                url = urls[0];
                if (parseJson.isOnline(MovieApplication.context)) {
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    list = parseJson.parsejson(in);
                    Log.i("Lamchith", "got the list" + list.size());

                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return list;
        }


        protected void onPostExecute(final ArrayList<MovieTrailorDetails> list) {

            Log.d("Lamchith",list.get(0).toString());


            TrailorAdapter adapter = new TrailorAdapter( list);
            // this.list=list;
            recyclerViewTrailor.setAdapter(adapter);
           /* recyclerViewTrailor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(view.getContext(), MovieDetailsActivity.class);
                    // String detailsArray[]=new String[5];
                    MovieDetails details = list.get(i);
               *//**//* detailsArray[0]=details.title;
                detailsArray[1]=details.moviePoster;
                detailsArray[2]=details.overview;
                detailsArray[3]=details.userRating;
                detailsArray[4]=details.releaseDate;*//**//*
                    ArrayList<String> arraylist = new ArrayList<String>();
                    arraylist.add(details.title);
                    arraylist.add(details.moviePoster);
                    arraylist.add(details.overview);
                    arraylist.add(details.userRating);
                    arraylist.add(details.releaseDate);
                    arraylist.add(""+details.movieId);
                    intent.putStringArrayListExtra("com.lmc.popularmovie.details", arraylist);

                    //intent.putExtra("Details", detailsArray);
                    startActivity(intent);
                }*/
            };


        }

    }
