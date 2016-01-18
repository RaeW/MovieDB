package com.dragonflythicket.moviedb.ui;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dragonflythicket.moviedb.Movie.Movie;
import com.dragonflythicket.moviedb.R;
import com.dragonflythicket.moviedb.utils.AdapterHelper;
import com.dragonflythicket.moviedb.utils.MovieDetailCallback;

import java.util.List;

/**
 * Created by Rae on 1/15/2016.
 */

public class PosterGridAdapter extends ArrayAdapter<Movie> {
    private final String TAG = PosterGridAdapter.class.getSimpleName();

    private MovieDetailCallback callback;

    public PosterGridAdapter(Activity context, List<Movie> movies) {
        super(context, 0, movies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AdapterHelper.ViewHolder holder;
        Movie movie = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.poster_thumbnail, parent, false);
            holder = AdapterHelper.setViewForPoster(convertView);
        } else {
            holder = (AdapterHelper.ViewHolder) convertView.getTag();
        }
        // TODO : before I can make the Glide call, I need to parse the json to get the image path
        String baseUri = "http://image.tmdb.org/t/p/";
        String size = "w185";
        String posterPath = movie.posterPath;
        ImageView image = (ImageView) convertView.findViewById(R.id.thumbnail);
        Glide.with(getContext()).load(baseUri + size + posterPath).into(holder.moviePoster);
        Log.d(TAG, posterPath);
        return convertView;
    }

}
