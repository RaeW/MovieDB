package com.dragonflythicket.moviedb.ui;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dragonflythicket.moviedb.Movie.Movie;
import com.dragonflythicket.moviedb.R;
import com.dragonflythicket.moviedb.utils.AdapterHelper;
import com.dragonflythicket.moviedb.utils.Constants;
import com.dragonflythicket.moviedb.utils.MovieDetailCallback;

import java.util.List;

/**
 * Created by Rae on 1/15/2016.
 */

public class PosterGridAdapter extends ArrayAdapter<Movie> {
    private final String TAG = PosterGridAdapter.class.getSimpleName();

    private MovieDetailCallback callback;

    public PosterGridAdapter(Activity context, List<Movie> movies, MovieDetailCallback callback) {
        super(context, 0, movies);
        this.callback = callback;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AdapterHelper.ViewHolder holder;
        final Movie movie = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.poster_thumbnail, parent, false);
            holder = AdapterHelper.setViewForPoster(convertView);
        } else {
            holder = (AdapterHelper.ViewHolder) convertView.getTag();
        }

        String posterPath = movie.posterPath;
        ImageView image = (ImageView) convertView.findViewById(R.id.thumbnail);
        if (posterPath.equals("null") || posterPath.equals("") || posterPath== null) {
            movie.posterPath = "";
            holder.moviePoster.setImageResource(R.drawable.background);
        }
        else {
            Glide.with(getContext()).load(Constants.BASEURI + Constants.POSTER_SIZE + posterPath).into(holder.moviePoster);
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
        public void onClick(View v) {
                callback.posterClicked(movie);
            }
        });
        return convertView;
    }

}
