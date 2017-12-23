package com.mtw.movie_poc_screen.viewholders;

import android.support.v7.widget.AppCompatRatingBar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.mtw.movie_poc_screen.R;
import com.mtw.movie_poc_screen.data.vo.MoviePopularVO;
import com.mtw.movie_poc_screen.deligates.MovieItemDeligate;
import com.mtw.movie_poc_screen.utils.APIConstants;

/**
 * Created by Aspire-V5 on 12/6/2017.
 */

public class MovieViewHolder extends BaseViewHolder<MoviePopularVO> {

    @BindView(R.id.tv_movie_name)
    TextView tvMovieName;

    @BindView(R.id.tv_rate)
    TextView tvRate;

    @BindView(R.id.iv_movie)
    ImageView ivMovie;

    @BindView(R.id.rb_movie)
    AppCompatRatingBar rbMovie;

    private MovieItemDeligate movieItemDeligate;
    private MoviePopularVO mMovie;
    public MovieViewHolder(View itemView, MovieItemDeligate movieItemDeligate) {
        super(itemView);
        itemView.setOnClickListener(this);
        ButterKnife.bind(this, itemView);
        this.movieItemDeligate = movieItemDeligate;
    }

    @Override
    public void setData(MoviePopularVO movie) {

        mMovie = movie;
        tvMovieName.setText(movie.getTitle());
        tvRate.setText(movie.getVoteAverage() + "");
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_image_null_24dp)
                .centerCrop();
        Glide.with(itemView.getRootView().getContext()).load(APIConstants.IMAGE_API + movie.getPosterPath()).apply(requestOptions).into(ivMovie);
        float popularity = movie.getPopularity() / 200;
        rbMovie.setRating(popularity);
    }

    @Override
    public void onClick(View view) {
       movieItemDeligate.onItemTap(mMovie);
       movieItemDeligate.onMovieOverviewTap(mMovie);
    }
}

