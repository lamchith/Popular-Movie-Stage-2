package com.lmc.popularmovie;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.lmc.popularmovie.db.DBHelper;
import com.lmc.popularmovie.ui.helper.GridImageAdapter;
import com.lmc.popularmovie.utility.MovieApplication;
import com.lmc.popularmovie.utility.MovieDetails;
import com.lmc.popularmovie.utility.ParseJson;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class MovieGridFragment extends Fragment {

    GridView gridView;
    //ArrayList<MovieDetails> list=null;
    DownloadTask downloadTask = null;

    public MovieGridFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_grid, container, false);
        gridView = (GridView) view.findViewById(R.id.gridview);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    @Override
    public void onResume() {
        super.onResume();
        String sortBy;
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sort = sp.getString("list_preference", "1");
        if (sort.equals("1")) {
            sortBy = "popularity.desc";

        }
        else if(sort.equals("2")) {
            sortBy = "vote_average.desc";
        }
        else {
            sortBy = "3";
        }

        if(!sortBy.equals("3")) {
            Log.i("Lamchith", "Value from prefernce is :" + sort);
            try {
                // keep the movie api key here
                downloadTask = (DownloadTask) new DownloadTask().execute(new URL("http://api.themoviedb.org/3/discover/movie?sort_by=" + sortBy + "&api_key="));
            } catch (MalformedURLException ex) {
                Log.e("Lamchith", ex.getMessage());

            }
        }
        else{
            // get the value from DB

            DBHelper dbHelper=new DBHelper(MovieApplication.context);
            ArrayList<MovieDetails> list= dbHelper.getMovieDetails();

            populateImageGrid(list);



        }
    }

    private class DownloadTask extends AsyncTask<URL, Integer, ArrayList<MovieDetails>> {
        public ArrayList<MovieDetails> list = null;


        protected ArrayList<MovieDetails> doInBackground(URL... urls) {

            ParseJson parseJson = new ParseJson();
            ArrayList<MovieDetails> list = null;
            URL url = null;
            try {
                url = urls[0];
                if(parseJson.isOnline(MovieApplication.context)) {
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


        protected void onPostExecute(final ArrayList<MovieDetails> list) {

            populateImageGrid(list);


        }
    }

    private void populateImageGrid(final ArrayList<MovieDetails> list) {
        GridImageAdapter adapter = new GridImageAdapter(MovieApplication.context, list);
        // this.list=list;
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(view.getContext(), MovieDetailsActivity.class);
                // String detailsArray[]=new String[5];
                MovieDetails details = list.get(i);
           /* detailsArray[0]=details.title;
            detailsArray[1]=details.moviePoster;
            detailsArray[2]=details.overview;
            detailsArray[3]=details.userRating;
            detailsArray[4]=details.releaseDate;*/
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
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getFragmentManager().beginTransaction().replace(R.id.movieGridActivityFragment, new MovieGridFragment()).commit();
        // getFragmentManager().beginTransaction().
        // getFragmentManager().beginTransaction().replace(android.R.id.content, new PrefFragment()).commit();
    }
}
