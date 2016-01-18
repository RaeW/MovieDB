package com.dragonflythicket.moviedb.utils;

import android.view.View;
import android.widget.ImageView;

import com.dragonflythicket.moviedb.R;

/**
 * Created by Rae on 1/16/2016.
 */
public class AdapterHelper {
    public static class ViewHolder {
        public ImageView moviePoster;
    }

    public static ViewHolder setViewForPoster(final View view) {
        ViewHolder holder = new ViewHolder();
        holder.moviePoster = (ImageView) view.findViewById(R.id.thumbnail);
        view.setTag(holder);
        return holder;
    }
}
