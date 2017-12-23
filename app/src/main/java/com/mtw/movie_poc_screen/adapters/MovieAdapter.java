package com.mtw.movie_poc_screen.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.mtw.movie_poc_screen.R;
import com.mtw.movie_poc_screen.data.vo.MoviePopularVO;
import com.mtw.movie_poc_screen.deligates.MovieItemDeligate;
import com.mtw.movie_poc_screen.viewholders.MovieViewHolder;

/**
 * Created by Aspire-V5 on 12/6/2017.
 */

public class MovieAdapter extends BaseRecyclerAdapter<MovieViewHolder, MoviePopularVO> {

    private MovieItemDeligate movieItemDeligate;

    public MovieAdapter(Context context, MovieItemDeligate movieItemDeligate) {
        super(context);
        this.movieItemDeligate = movieItemDeligate;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.view_item_movie, parent, false);
        return new MovieViewHolder(view,movieItemDeligate);
    }
}
