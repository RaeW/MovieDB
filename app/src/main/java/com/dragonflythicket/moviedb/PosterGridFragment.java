package com.dragonflythicket.moviedb;

import android.media.Image;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class PosterGridFragment extends Fragment {

    private final String TAG = "PosterGridFragment";
    private ArrayAdapter<Image> mImageAdapter;

    public PosterGridFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mImageAdapter = new ArrayAdapter<Image> (
                getActivity(),
                R.layout.poster_thumbnail,
                new ArrayList<Image>()
        );
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        GridView posterView = (GridView) rootView.findViewById(R.id.posterGrid);
        posterView.setAdapter(mImageAdapter);

        return rootView;
    }

    private void runFetchMovieTask() {
        FetchMovieTask fetchMovieTask = new FetchMovieTask();
    }

    public class FetchMovieTask extends AsyncTask<Void, Void, String[]> {
        private static final String LOG_TAG = "FetchMovieTask";
        private final String TAG = FetchMovieTask.class.getSimpleName();

        @Override
        protected String[] doInBackground(Void... params) {
            return null;
        }
    }
}
