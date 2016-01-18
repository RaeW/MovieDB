package com.dragonflythicket.moviedb.utils;

import com.dragonflythicket.moviedb.Movie.Movie;

import java.util.ArrayList;

/**
 * Created by Rae on 1/17/2016.
 */
public interface MoviePosterTaskCallback {
    public void onMovieDataReceived(ArrayList<Movie> result);
}
