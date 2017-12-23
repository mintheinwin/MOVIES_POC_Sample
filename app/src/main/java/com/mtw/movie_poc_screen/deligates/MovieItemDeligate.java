package com.mtw.movie_poc_screen.deligates;

import android.view.View;

import com.mtw.movie_poc_screen.data.vo.MoviePopularVO;

/**
 * Created by Aspire-V5 on 12/6/2017.
 */

public interface MovieItemDeligate {
    void onItemTap(MoviePopularVO movie);

    void onImageTap();

    void onMovieOverviewTap(MoviePopularVO movie);
}
