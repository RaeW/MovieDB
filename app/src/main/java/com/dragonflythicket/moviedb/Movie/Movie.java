package com.dragonflythicket.moviedb.Movie;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Rae on 1/16/2016.
 */
public class Movie implements Parcelable {
    public String posterPath;
    public int movieId;
    public String overview;
    public String releaseDate;
    public String title;
    public double averageRating;

    public Movie() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(posterPath);
        out.writeInt(movieId);
        out.writeString(overview);
        out.writeString(releaseDate);
        out.writeString(title);
        out.writeDouble(averageRating);
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    private Movie(Parcel in) {
        posterPath = in.readString();
        movieId = in.readInt();
        overview = in.readString();
        releaseDate = in.readString();
        title = in.readString();
        averageRating = in.readDouble();
    }
}
