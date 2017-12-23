package com.mtw.movie_poc_screen.fragments;

import android.support.v4.app.Fragment;
import android.view.View;

import com.mtw.movie_poc_screen.data.vo.MoviePopularVO;
import com.mtw.movie_poc_screen.deligates.MovieItemDeligate;

/**
 * Created by Min Thein Win on 11/8/2017.
 */

public class BaseFragment extends Fragment implements MovieItemDeligate {
    @Override
    public void onItemTap(MoviePopularVO movie) {

    }

    @Override
    public void onImageTap() {

    }

    @Override
    public void onMovieOverviewTap(MoviePopularVO movie) {

    }
}
