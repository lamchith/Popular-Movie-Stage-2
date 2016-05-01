package com.lmc.popularmovie.utility;

import android.os.Parcel;
import android.os.Parcelable;

import com.lmc.popularmovie.ui.helper.UIConstants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by lmarathchathu on 12/7/2015.
 */
public class MovieDetails implements Parcelable{


    //title
    private String title;
    //overview
    private String overview;
    //vote_average
    private String userRating;
    //release_date
    private String releaseDate;

    private int movieId;

    //poster_path
    private String moviePoster;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    public String getMoviePoster() {
        return moviePoster;
    }

    public void setMoviePoster(String moviePoster) {
        this.moviePoster = moviePoster;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getUserRating() {
        return userRating;
    }

    public void setUserRating(String userRating) {
        this.userRating = userRating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }


    public String toString(){
      return   title+" : "+moviePoster+" : "+overview +" : "+userRating+" : "+releaseDate+" : "+movieId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(title);
        dest.writeString(overview);
        dest.writeString(userRating);
        dest.writeString(releaseDate);
        dest.writeInt(movieId);
        dest.writeString(moviePoster);

    }

    public static final Parcelable.Creator<MovieDetails> CREATOR
            = new Parcelable.Creator<MovieDetails>() {
        public MovieDetails createFromParcel(Parcel in) {

            return new MovieDetails(in);
        }

        public MovieDetails[] newArray(int size) {
            return new MovieDetails[size];
        }
    };

    public MovieDetails(){}

    public MovieDetails(ArrayList list){

        if(list!=null) {
            this.title = (String) list.get(0);
            this.overview = (String) list.get(2);
            this.userRating = (String) list.get(3);
            this.releaseDate = (String) list.get(4);
            this.movieId = Integer.parseInt((String)list.get(5));
            this.moviePoster = (String) list.get(1);
        }


    }

    private MovieDetails(Parcel par){
        this.title=par.readString();
        this.overview=par.readString();
        this.userRating=par.readString();
        this.releaseDate=par.readString();
        this.movieId=par.readInt();
        this.moviePoster=par.readString();

    }
}
