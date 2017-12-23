package com.mtw.movie_poc_screen.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.mtw.movie_poc_screen.R;
import com.mtw.movie_poc_screen.data.vo.MoviePopularVO;
import com.mtw.movie_poc_screen.viewholders.MovieTrailerViewHolder;

/**
 * Created by Aspire-V5 on 12/18/2017.
 */

public class MovieTrailerAdapter extends BaseRecyclerAdapter<MovieTrailerViewHolder, MoviePopularVO> {
    public MovieTrailerAdapter(Context context) {
        super(context);
    }

    @Override
    public MovieTrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.view_item_movie_trailer, parent, false);
        return new MovieTrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieTrailerViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
