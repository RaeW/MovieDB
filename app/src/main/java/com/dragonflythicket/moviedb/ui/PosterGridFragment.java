package com.dragonflythicket.moviedb.ui;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class PosterGridFragment extends Fragment implements MovieDetailCallback, MoviePosterTaskCallback {
    private static final String TAG = PosterGridFragment.class.getSimpleName();
    private ArrayList<Movie> movies;

    private PosterGridAdapter mImageAdapter;

    private OnFragmentInteractionListener mListener;

    /**
     * @return A new instance of fragment PosterGridFragment.
     */
    public static PosterGridFragment newInstance() {
        PosterGridFragment fragment = new PosterGridFragment();
        return fragment;
    }

    public PosterGridFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        movies = new ArrayList<Movie>();
        Movie temp = new Movie();
        mImageAdapter = new PosterGridAdapter(
                getActivity(),
                movies,
                this);

        View rootView = inflater.inflate(R.layout.fragment_poster_grid, container, false);

        GridView posterView = (GridView) rootView.findViewById(R.id.posterGrid);
        posterView.setAdapter(mImageAdapter);

        runFetchMoviePosterTask();

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onDetailPressed(Movie movie) {
        if (mListener != null) {
            mListener.onFragmentInteraction(movie);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

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
        FetchMoviePosterTask task = FetchMoviePosterTask.setUpFetchMoviePosterTask(this);
        task.execute();
    }

    @Override
    public void posterClicked(Movie movie) {
        onDetailPressed(movie);
    }

    public void onMovieDataReceived(ArrayList<Movie> result) {
        if (result != null) {
            movies.clear();
            movies.addAll(result);
            mImageAdapter.notifyDataSetChanged();
        }
    }
}
