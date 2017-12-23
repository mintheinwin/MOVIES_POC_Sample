package com.mtw.movie_poc_screen.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.mtw.movie_poc_screen.fragments.MoviestListFragment;

/**
 * Created by Aspire-V5 on 12/8/2017.
 */

public class MoviesPagerAdapter extends FragmentPagerAdapter {

    public MoviesPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    Fragment fragment;

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                fragment = MoviestListFragment.newInstance();
                break;
            case 1:
                fragment = MoviestListFragment.newInstance();
                break;
            case 2:
                fragment = MoviestListFragment.newInstance();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }
}
