package com.dragonflythicket.moviedb.utils;

import android.os.AsyncTask;
import android.util.Log;

import com.dragonflythicket.moviedb.BuildConfig;
import com.dragonflythicket.moviedb.Movie.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Rae on 1/16/2016.
 */
public class FetchMoviePosterTask extends AsyncTask<Void, Void, ArrayList<Movie>> {
    static final String TAG=FetchMoviePosterTask.class.getSimpleName();
    private MoviePosterTaskCallback callback;

    public FetchMoviePosterTask(MoviePosterTaskCallback callback) {
        super();
        this.callback = callback;
    }

    public FetchMoviePosterTask() {

    }

    public static FetchMoviePosterTask setUpFetchMoviePosterTask(MoviePosterTaskCallback callback) {
        FetchMoviePosterTask fetchMoviePosterTask = new FetchMoviePosterTask();
        fetchMoviePosterTask.callback = callback;
        return fetchMoviePosterTask;
    }

    @Override
    public ArrayList<Movie> doInBackground(Void ... params) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String movieJsonStr = "";
        String format = "json";

        ArrayList<Movie> movies = null;

        try {
            final String MOVIE_BASE_URL = "http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key="
                    + BuildConfig.MOVIE_DB_API_KEY;
            URL url = new URL(MOVIE_BASE_URL);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream input = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (input == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(input));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // stream was empty -- nothing to parse
                return null;
            }
            movieJsonStr = buffer.toString();
            Log.d(TAG, movieJsonStr);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(TAG, e.getMessage());
                }
            }
        }
        try {
            movies = getMovieDataFromJson(movieJsonStr);
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }
        return movies;
    }

    private ArrayList<Movie> getMovieDataFromJson(String movieJsonStr) throws JSONException {
        final String TMDB_RESULTS = "results";
        final String TMDB_POSTER_PATH = "poster_path";
        final String TMDB_ID = "id";
        final String TMDB_MOVIE_DETAIL = "overview";
        final String TMDB_RELEASE_DATE = "release_date";
        final String TMDB_AVERAGE_RATING = "vote_average";
        final String TMDB_TITLE = "title";


        JSONObject moviesJson = new JSONObject(movieJsonStr);
        JSONArray movieArray = moviesJson.getJSONArray(TMDB_RESULTS);
        ArrayList<Movie> movies = new ArrayList<Movie>();
        for (int i = 0; i < movieArray.length(); i++) {
            Movie m = new Movie();
            JSONObject movieDetail = movieArray.getJSONObject(i);
            m.posterPath = movieDetail.getString(TMDB_POSTER_PATH);
            m.movieId = movieDetail.getInt(TMDB_ID);
            m.overview = movieDetail.getString(TMDB_MOVIE_DETAIL);
            m.title = movieDetail.getString(TMDB_TITLE);
            m.averageRating = movieDetail.getDouble(TMDB_AVERAGE_RATING);
            m.releaseDate = movieDetail.getString(TMDB_RELEASE_DATE);
            movies.add(m);
        }
        return movies;
    }

    @Override
    protected void onPostExecute(ArrayList<Movie> movies) {
        super.onPostExecute(movies);
        if(movies != null) {
            callback.onMovieDataReceived(movies);
        }
    }
}
