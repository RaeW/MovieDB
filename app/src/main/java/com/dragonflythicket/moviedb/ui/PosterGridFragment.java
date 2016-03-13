package com.dragonflythicket.moviedb.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.dragonflythicket.moviedb.Movie.Movie;
import com.dragonflythicket.moviedb.R;
import com.dragonflythicket.moviedb.utils.FetchMoviePosterTask;
import com.dragonflythicket.moviedb.utils.MoviePosterTaskCallback;
import com.dragonflythicket.moviedb.utils.MovieDetailCallback;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PosterGridFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PosterGridFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PosterGridFragment extends Fragment implements MoviePosterTaskCallback {
    private static final String TAG = PosterGridFragment.class.getSimpleName();
    private ArrayList<Movie> movies;

    private PosterGridAdapter mImageAdapter;
    private GridView mPosterView;
//    private Parcelable mGridState;

    private OnFragmentInteractionListener mListener;

    /**
     * @return A new instance of fragment PosterGridFragment.
     */
    public static PosterGridFragment newInstance() {
        return new PosterGridFragment();
    }

    public PosterGridFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setRetainInstance(true);
        Log.d(TAG, "onCreate");
        if (savedInstanceState == null || !savedInstanceState.containsKey("movies")) {
            movies = new ArrayList<Movie>();
        }
        else {
            movies = savedInstanceState.getParcelableArrayList("movies");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
//        if (savedInstanceState == null || !savedInstanceState.containsKey("movies")) {
//            movies = new ArrayList<Movie>();
//        }
//        else {
//            movies = savedInstanceState.getParcelableArrayList("movies");
//        }
        mImageAdapter = new PosterGridAdapter(getActivity(), movies);

        final View rootView = inflater.inflate(R.layout.fragment_poster_grid, container, false);

        mPosterView = (GridView) rootView.findViewById(R.id.posterGrid);
        mPosterView.setAdapter(mImageAdapter);
        if (movies.size() == 0) {
            Log.d(TAG, "running fetch movie poster task");
            runFetchMoviePosterTask();
        }
        mPosterView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie movie = mImageAdapter.getItem(position);
                onDetailPressed(movie);
            }
        });
        return rootView;
    }

    public void onDetailPressed(Movie movie) {
        if (mListener != null) {
            mListener.onFragmentInteraction(movie);
        }
    }

    @Override
    public void onPause() {
        Log.d(TAG, "onPause");
//        mGridState = mPosterView.onSaveInstanceState();
//        Log.d(TAG, "saved state on pausing " + mGridState);
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("movies", movies);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStart() {
        Log.d(TAG, "onStart");
        super.onStart();
        try {
            mListener = (OnFragmentInteractionListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        Log.d(TAG, "onDetach");
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
//        if (mGridState != null) {
//            mPosterView.requestFocus();
//            mPosterView.onRestoreInstanceState(mGridState);
//            Log.d(TAG, mGridState.toString());
//        }
    }

//    @Override
//    public void onLoadFinished(Loader<ArrayList>.setData())

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Movie movie);
    }

    private void runFetchMoviePosterTask() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sortBy = sharedPreferences.getString(getString(R.string.pref_sort_key)
                , getString(R.string.pref_sort_default));
        switch (sortBy) {
            case "Highest Rated":
                sortBy = getString(R.string.highest_rated_param);
                break;
            case "Popularity":
            default:
                sortBy = getString(R.string.most_popular_param);
                break;
        }
        FetchMoviePosterTask task = FetchMoviePosterTask.setUpFetchMoviePosterTask(this, sortBy);
        task.execute();
    }

    public void onMovieDataReceived(ArrayList<Movie> result) {
        if (result != null) {
            movies.clear();
            movies.addAll(result);
            mImageAdapter.notifyDataSetChanged();
        }
    }
}
