package com.dragonflythicket.moviedb;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.dragonflythicket.moviedb.Movie.Movie;
import com.dragonflythicket.moviedb.ui.DetailFragment;
import com.dragonflythicket.moviedb.ui.PosterGridFragment;

public class MainActivity extends AppCompatActivity
implements PosterGridFragment.OnFragmentInteractionListener {

    private final String TAG=MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.main, new PosterGridFragment(), PosterGridFragment.class.getSimpleName())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Movie movie) {
        DetailFragment fragment = DetailFragment.newInstance(movie);

        replaceFragment(fragment);
    }

    private void replaceFragment(Fragment fragment) {
        String backStateName = fragment.getClass().getSimpleName();
        FragmentManager manager = getFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate(backStateName, 0);

        if (!fragmentPopped) {
            FragmentTransaction fragmentTransaction = manager.beginTransaction();
            Fragment current = manager.findFragmentByTag(PosterGridFragment.class.getSimpleName());
            fragmentTransaction.add(R.id.main, fragment);
            fragmentTransaction.hide(current);
            //fragmentTransaction.replace(R.id.main, fragment);
            fragmentTransaction.addToBackStack(backStateName);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onBackPressed() {
        // this was the magic formula to return to post grid rather than the device's home screen
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }


}
