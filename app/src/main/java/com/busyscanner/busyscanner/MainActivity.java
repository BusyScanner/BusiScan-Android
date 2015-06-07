package com.busyscanner.busyscanner;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        addDummyData();
        HomeFragment fragment = HomeFragment.newInstance();
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment, HomeFragment.TAG)
                .addToBackStack(HomeFragment.TAG)
                .commit();

        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
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
    public void onBackPressed() {
        Fragment fragment = getTopOfBackStackFragment(0);
        if (fragment instanceof ImageUploadFragment) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, HomeFragment.newInstance())
                    .commit();
        } else {
            super.onBackPressed();
        }
    }

    protected Fragment getTopOfBackStackFragment(final int pendingPopCount) {
        final FragmentManager fragmentManager = getFragmentManager();
        final int entryCount = fragmentManager.getBackStackEntryCount();
        if (entryCount > pendingPopCount) {
            final String tag = fragmentManager
                    .getBackStackEntryAt(entryCount - 1 - pendingPopCount)
                    .getName();
            Log.d(TAG, "Top of back stack tag: " + tag);
            return fragmentManager.findFragmentByTag(tag);
        }
        return null;
    }

    private void addDummyData() {
        new BizCardResponse("Hogwarts Inc", "thechosenone@hogwarts.com", "Harry Potter", "(123) 456-7890").save();
        new BizCardResponse("Hogwarts Inc", "thechosenone@hogwarts.com", "Harry Potter", "(123) 456-7890").save();
        new BizCardResponse("Hogwarts Inc", "thechosenone@hogwarts.com", "Harry Potter", "(123) 456-7890").save();
        new BizCardResponse("Hogwarts Inc", "thechosenone@hogwarts.com", "Harry Potter", "(123) 456-7890").save();
        new BizCardResponse("Hogwarts Inc", "thechosenone@hogwarts.com", "Harry Potter", "(123) 456-7890").save();
    }
}
