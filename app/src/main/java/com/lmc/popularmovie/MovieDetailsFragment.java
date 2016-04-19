package com.lmc.popularmovie;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.lmc.popularmovie.db.DBHelper;
import com.lmc.popularmovie.ui.helper.ReviewAdapter;
import com.lmc.popularmovie.ui.helper.TrailorAdapter;
import com.lmc.popularmovie.ui.helper.UIConstants;
import com.lmc.popularmovie.utility.MovieApplication;
import com.lmc.popularmovie.utility.MovieTrailorDetails;
import com.lmc.popularmovie.utility.ParseJsonReview;
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
    GetTrailorsTask trailorsTask = null;
    GetReviewsTask reviewTask = null;
    RecyclerView recyclerViewTrailor=null;
    RecyclerView recyclerViewReview=null;
    TextView textViewUserRating=null;
    TextView textViewReleaseDate=null;
    TextView textViewOverview=null;
    TextView textViewTitle=null;
    TextView textViewTrailor=null;
    TextView textViewReview=null;
    Button buttonFavourite=null;
    ImageView imagePoster=null;

    public MovieDetailsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        textViewUserRating = (TextView) view.findViewById(R.id.textView_userRating);
        textViewReleaseDate = (TextView) view.findViewById(R.id.textView_releaseDate);
        textViewOverview = (TextView) view.findViewById(R.id.textView_overview);
        imagePoster=(ImageView)view.findViewById(R.id.imageView_poster);
        textViewTitle = (TextView) view.findViewById(R.id.textView_title);
        textViewTrailor = (TextView) view.findViewById(R.id.textView_trailor);
        buttonFavourite = (Button) view.findViewById(R.id.button_favourite);
        buttonFavourite.setVisibility(View.INVISIBLE);
        textViewReview = (TextView) view.findViewById(R.id.textView_review);
        recyclerViewTrailor=(RecyclerView) view.findViewById(R.id.recyclerView_trailor);
        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(MovieApplication.context);
        recyclerViewTrailor.setHasFixedSize(true);
        recyclerViewTrailor.setLayoutManager(layoutManager);

        recyclerViewReview=(RecyclerView) view.findViewById(R.id.recyclerView_review);
        recyclerViewReview.setHasFixedSize(true);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(MovieApplication.context);
        recyclerViewReview.setLayoutManager(layoutManager1);

        if (list != null) {

            textViewTitle.setText(list.get(0));
            Picasso.with(MovieApplication.context).load(UIConstants.IMAGE_SIZE_POSETR + list.get(1)).into(imagePoster);
            textViewOverview.setText(list.get(2));
            textViewUserRating.setText(list.get(3)+" / 10");
            textViewReleaseDate.setText(list.get(4));
            buttonFavourite.setVisibility(View.VISIBLE);
            buttonFavourite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    {

                        DBHelper dbHelper=new DBHelper(MovieApplication.context);

                        if(list!=null&& list.size()>0){

                            dbHelper.insertMovieDetails(list);
                        }

                    }

                }
            });

            textViewTrailor.setText("Trailors below");

            try {
                trailorsTask = (GetTrailorsTask) new GetTrailorsTask().execute(new URL("http://api.themoviedb.org/3/movie/"+list.get(5)+"/videos?api_key="+ UIConstants.API_KEY));
            } catch (MalformedURLException ex) {
                Log.e("Lamchith", ex.getMessage());

            }


            textViewReview.setText("Reviews below");
            try {
                reviewTask = (GetReviewsTask) new GetReviewsTask().execute(new URL("http://api.themoviedb.org/3/movie/"+list.get(5)+"/reviews?api_key="+ UIConstants.API_KEY));
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
        }

    public void updateContent(final ArrayList<String> movieDetailsList) {
        if (movieDetailsList != null) {
            textViewTitle.setText(movieDetailsList.get(0));
            Picasso.with(MovieApplication.context).load(UIConstants.IMAGE_SIZE_POSETR + movieDetailsList.get(1)).into(imagePoster);
            textViewOverview.setText(movieDetailsList.get(2));
            textViewUserRating.setText(movieDetailsList.get(3)+" / 10");
            textViewReleaseDate.setText(movieDetailsList.get(4));
            buttonFavourite.setVisibility(View.VISIBLE);
            buttonFavourite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    {

                        DBHelper dbHelper=new DBHelper(MovieApplication.context);

                        if(list!=null&& list.size()>0){

                            dbHelper.insertMovieDetails(movieDetailsList);
                        }

                    }

                }
            });
            textViewTrailor.setText("Trailors below");


            try {
                // this is getting executed multiple times
                // keep the movie api key here
                trailorsTask = (GetTrailorsTask) new GetTrailorsTask().execute(new URL("http://api.themoviedb.org/3/movie/"+movieDetailsList.get(5)+"/videos?api_key="+ UIConstants.API_KEY));
            } catch (MalformedURLException ex) {
                Log.e("Lamchith", ex.getMessage());

            }

            textViewReview.setText("Reviews below");
            try {
                // this is getting executed multiple times
                // keep the movie api key here
                reviewTask = (GetReviewsTask) new GetReviewsTask().execute(new URL("http://api.themoviedb.org/3/movie/"+movieDetailsList.get(5)+"/reviews?api_key="+ UIConstants.API_KEY));
            } catch (MalformedURLException ex) {
                Log.e("Lamchith", ex.getMessage());

            }

        }



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
                    Log.i("Lamchith", "got the trailor list" + list.size());

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
            recyclerViewTrailor.setAdapter(adapter);

            };


        }


    private class GetReviewsTask extends AsyncTask<URL, Integer, ArrayList<String>> {
        public ArrayList<String> list = null;


        protected ArrayList<String> doInBackground(URL... urls) {

            ParseJsonReview parseJson = new ParseJsonReview();
            ArrayList<String> list = null;
            URL url = null;
            try {
                url = urls[0];
                if (parseJson.isOnline(MovieApplication.context)) {
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    list = parseJson.parsejson(in);

                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return list;
        }


        protected void onPostExecute(final ArrayList<String> list) {

            ReviewAdapter adapter = new ReviewAdapter(list);
            recyclerViewReview.setAdapter(adapter);

        };


    }

    }
