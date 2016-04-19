package com.lmc.popularmovie.utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.JsonReader;
import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by lmarathchathu on 4/19/2016.
 */
public class ParseJsonReview {

    public ArrayList<String> parsejson(InputStream in) {
        ArrayList<String> list = new ArrayList<String>();

        try {
            JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
            try {
                reader.beginObject();

                while (reader.hasNext()) {
                    String name = reader.nextName();
                    if (name.equals("results")) {
                        reader.beginArray();
                        while (reader.hasNext()) {
                            readMovieReviewDetails(reader, list);

                        }
                        reader.endArray();


                    } else {
                        reader.skipValue();
                    }
                }

                reader.endObject();
            } catch (IOException e) {
                e.printStackTrace();
            }


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return list;
    }


    static ArrayList<String> readMovieReviewDetails(JsonReader reader, ArrayList<String> details) {
        try {
            reader.beginObject();
            String url=null;
            while (reader.hasNext()) {
                try {

                    String name = reader.nextName();

                    if (name.equals("url")) {
                        url = reader.nextString();
                    } /* else if (name.equals("id")) {
                        movie.movieId = reader.nextInt();
                    }*/
                    else {
                        reader.skipValue();
                    }
                } catch (IllegalStateException ex) {
                    Log.e("Lamchith", ex.toString());
                }
                //release_date


            }
            details.add(url);
            Log.d("Lamchith",url);
            reader.endObject();

        } catch (IOException io) {
        }

        return details;
    }


    public boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        return isConnected;
    }


}

