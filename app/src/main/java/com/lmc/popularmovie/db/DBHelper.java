package com.lmc.popularmovie.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.lmc.popularmovie.utility.MovieDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lmarathchathu on 4/17/2016.
 */
public class DBHelper extends SQLiteOpenHelper {

    private final static String DB_NAME="moviedb";

    private final static String TABLE_NAME="moviedetails";
    private final static String COLUMN_POSTER="moviePoster";
    private final static String COLUMN_OVERVIEW="overview";
    private final static String COLUMN_RATING="userRating";
    private final static String COLUMN_DATE="releaseDate";
    private final static String COLUMN_ID="movieId";
    private final static String COLUMN_TITLE="title";



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_NAME+" (id integer primary key, "+COLUMN_TITLE +" text,"+COLUMN_POSTER+" text,"+COLUMN_OVERVIEW+" text, "+COLUMN_RATING+" text, "+COLUMN_DATE+" text,"+COLUMN_ID+" text)"
        );


    }

    public void insertMovieDetails(List<String> movieList){

        SQLiteDatabase db = this.getWritableDatabase();
        String title=movieList.get(0);
        String moviePoster=movieList.get(1);
        String  overview=movieList.get(2);
        String userRating=movieList.get(3);
        String releaseDate=movieList.get(4);
        String movieId=movieList.get(5);

        ContentValues values= new ContentValues();
        values.put(COLUMN_TITLE,title);
        values.put(COLUMN_TITLE,moviePoster);
        values.put(COLUMN_OVERVIEW,overview);
        values.put(COLUMN_RATING,userRating);
        values.put(COLUMN_DATE,releaseDate);
        values.put(COLUMN_ID,movieId);

        db.insert(TABLE_NAME,null,values);

    }


    public  ArrayList<MovieDetails> getMovieDetails(){
        ArrayList<MovieDetails> list =null;
        MovieDetails details=null;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res =  db.rawQuery( "select * from moviedetails", null );

        res.moveToFirst();
        if(res.getCount()>0){
            list=new ArrayList<MovieDetails>();

        }
        while(!res.isAfterLast ()){

            details=new MovieDetails();
            details.movieId=res.getInt(res.getColumnIndex(COLUMN_ID));
            details.moviePoster=res.getString(res.getColumnIndex(COLUMN_POSTER));
            details.overview=res.getString(res.getColumnIndex(COLUMN_OVERVIEW));
            details.releaseDate=res.getString(res.getColumnIndex(COLUMN_DATE));
            details.title=res.getString(res.getColumnIndex(COLUMN_TITLE));
            details.userRating=res.getString(res.getColumnIndex(COLUMN_RATING));

            list.add(details);
        }

        return list;
        }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public DBHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }



}
