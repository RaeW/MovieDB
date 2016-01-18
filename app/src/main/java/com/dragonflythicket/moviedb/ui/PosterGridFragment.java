package com.dragonflythicket.moviedb.ui;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dragonflythicket.moviedb.Movie.Movie;
import com.dragonflythicket.moviedb.R;
import com.dragonflythicket.moviedb.utils.FetchMoviePosterTask;
import com.dragonflythicket.moviedb.utils.MoviePosterTaskCallback;
import com.dragonflythicket.moviedb.utils.MovieDetailCallback;

import java.util.ArrayList;
import java.util.Arrays;

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
        Movie temp = new Movie();
        temp.posterPath = "/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg";
        temp.movieId = "1500";
        Movie[] movies = {temp};
        mImageAdapter = new PosterGridAdapter(
                getActivity(),
                Arrays.asList(movies));

        View rootView = inflater.inflate(R.layout.fragment_poster_grid, container, false);

        GridView posterView = (GridView) rootView.findViewById(R.id.posterGrid);
        posterView.setAdapter(mImageAdapter);

        //runFetchMoviePosterTask();

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    private void runFetchMoviePosterTask() {
        FetchMoviePosterTask task = FetchMoviePosterTask.setUpFetchMoviePosterTask(this);
        //FetchMoviePosterTask task = new FetchMoviePosterTask(this);
        task.execute();
    }

    @Override
    public void posterClicked(String clipId) {

    }

    public void onMovieDataReceived(ArrayList<Movie> result) {
        if (result != null) {
            mImageAdapter.clear();
            mImageAdapter.addAll(result);
            mImageAdapter.notifyDataSetChanged();
            Log.d(TAG, "in onMovieDataReceived");
            for (int i = 0; i < result.size(); i++) {
                Log.d(TAG, result.get(i).movieId);
            }
        }
    }
}
