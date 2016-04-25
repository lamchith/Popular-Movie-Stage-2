package com.lmc.popularmovie;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import com.lmc.popularmovie.db.DBHelper;
import com.lmc.popularmovie.ui.helper.GridImageAdapter;
import com.lmc.popularmovie.ui.helper.UIConstants;
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

/**
 *
 */
public class MovieGridFragment extends Fragment {

    GridView gridView;
    //ArrayList<MovieDetails> list=null;
    DownloadTask downloadTask = null;
    String sortBy;

    OnItemSelectedListener gridItemClickedListener;


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

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sort = sp.getString("list_preference", "1");
        if (sort.equals("1")) {
            sortBy = "popular";

        }
        else if(sort.equals("2")) {
            sortBy = "top_rated";
        }
        else {
            sortBy = "DB";
        }
        Log.i("Lamchith", "Value from prefernce is :" + sort);

        if(sort.equals("1")|| sort.equals("2")) {
                downloadTask = (DownloadTask) new DownloadTask().execute(sortBy);
            }
        else{
                downloadTask = (DownloadTask) new DownloadTask().execute(sortBy);
            }
        }

    private class DownloadTask extends AsyncTask<String, Integer, ArrayList<MovieDetails>> {
        public ArrayList<MovieDetails> list = null;


        protected ArrayList<MovieDetails> doInBackground(String... types) {

            ParseJson parseJson = new ParseJson();
            ArrayList<MovieDetails> list = null;
            String type = null;
            try {
                type = types[0];
                Log.i("Lamchith", "Hit DB or Network to get the Movie grid input : Type is : " + type);

                if(type.equals("DB")){
                    DBHelper dbHelper=new DBHelper(MovieApplication.context);
                    list= dbHelper.getMovieDetails();
                    Log.i("Lamchith", "From DB" + list.size());

                }
                else {
                    if (parseJson.isOnline(MovieApplication.context)) {
                        // add the api key here
                        URL url =new URL("http://api.themoviedb.org/3/movie/" + type+"?api_key="+ UIConstants.API_KEY);
                        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                        InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                        list = parseJson.parsejson(in);
                        Log.i("Lamchith", "got the list" + list.size());
                    }
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

                MovieDetails details = list.get(i);
                ArrayList<String> arraylist = new ArrayList<String>();
                arraylist.add(details.title);
                arraylist.add(details.moviePoster);
                arraylist.add(details.overview);
                arraylist.add(details.userRating);
                arraylist.add(details.releaseDate);
                arraylist.add(""+details.movieId);

                gridItemClickedListener.onItemSelected(arraylist);


            }
        });
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getFragmentManager().beginTransaction().replace(R.id.movieGridActivityFragment, new MovieGridFragment()).commit();
        // getFragmentManager().beginTransaction().
        // getFragmentManager().beginTransaction().replace(android.R.id.content, new PrefFragment()).commit();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            Activity activity= context instanceof Activity ? (Activity)context:null;
            gridItemClickedListener=(OnItemSelectedListener) activity;

        }catch(Exception ex){

            Log.e("Lamchith",ex.toString());
        }
    }



    public interface OnItemSelectedListener{

        public void onItemSelected(ArrayList<String> movieDetailsList);
    }
}


