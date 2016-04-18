package com.lmc.popularmovie.utility;

/**
 * Created by lmarathchathu on 12/7/2015.
 */
public class MovieDetails {


    //title
    public String title;


    //poster_path
    public String moviePoster;

    //overview
    public String overview;
    //vote_average
    public String userRating;


    //release_date
    public String releaseDate;

    public int movieId;


    public String toString(){
      return   title+moviePoster+overview+userRating+releaseDate+" : "+movieId;
    }
}
