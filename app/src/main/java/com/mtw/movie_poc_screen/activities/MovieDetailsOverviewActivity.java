package com.mtw.movie_poc_screen.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mtw.movie_poc_screen.R;
import com.mtw.movie_poc_screen.adapters.MovieTrailerAdapter;
import com.mtw.movie_poc_screen.components.EmptyViewPod;
import com.mtw.movie_poc_screen.components.SmartHorizontalScrollListener;
import com.mtw.movie_poc_screen.components.SmartRecyclerView;
import com.mtw.movie_poc_screen.data.models.MovieModel;
import com.mtw.movie_poc_screen.data.vo.MoviePopularVO;
import com.mtw.movie_poc_screen.utils.APIConstants;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aspire-V5 on 9/6/2017.
 */
public class MovieDetailsOverviewActivity extends BaseActivity {

    @BindView(R.id.rv_movie_trailers)
    SmartRecyclerView rvMovieTrailers;

    @BindView(R.id.vp_empty_movie)
    EmptyViewPod vpEmptyMovie;

    @BindView(R.id.movieTitle)
    TextView movieTitle;

    @BindView(R.id.iv_movieoverview_background)
    ImageView iv_Overviewbackground;

    @BindView(R.id.iv_movie)
    ImageView imgTitle;

    @BindView(R.id.tv_releaseDate)
    TextView tv_releaseDate;

    @BindView(R.id.tv_movieOverViewDescription)
    TextView tv_movieOverViewDescription;

    private static final String tap_movie_id = "fragment_id";
    private MoviePopularVO mMovie;

    public static Intent newIntent(Context context,MoviePopularVO movie) {
        Intent intent = new Intent(context, MovieDetailsOverviewActivity.class);
        intent.putExtra(tap_movie_id, movie.getId());
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_item_movie_overview_image);
        ButterKnife.bind(this, this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        MovieTrailerAdapter movieTrailerAdapter = new MovieTrailerAdapter(getApplicationContext());
        rvMovieTrailers.setHasFixedSize(true);
        rvMovieTrailers.setEmptyView(vpEmptyMovie);
        rvMovieTrailers.setAdapter(movieTrailerAdapter);
        rvMovieTrailers.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

        SmartHorizontalScrollListener scrollListener = new SmartHorizontalScrollListener(new SmartHorizontalScrollListener.OnSmartHorizontalScrollListener() {
            @Override
            public void onListEndReached() {
                Snackbar.make(rvMovieTrailers, "No more trailers available.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        rvMovieTrailers.addOnScrollListener(scrollListener);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null){
            int movieId = bundle.getInt(tap_movie_id);
            mMovie = MovieModel.getInstance().getMovieById(movieId);
            bindData(mMovie);
        }
    }

    private void bindData(MoviePopularVO movie){
        if(movie.getBackDropPath() != null){
            List<String> images = new ArrayList<>() ;
            images.add(movie.getBackDropPath());
        }

        movieTitle.setText(movie.getTitle());
        RequestOptions bgrequestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_image_null_24dp)
                .centerCrop();
        Glide.with(iv_Overviewbackground.getRootView().getContext()).load(APIConstants.IMAGE_API + movie.getPosterPath()).apply(bgrequestOptions).into(iv_Overviewbackground);

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_image_null_24dp)
                .centerCrop();
        Glide.with(imgTitle.getRootView().getContext()).load(APIConstants.IMAGE_API + movie.getPosterPath()).apply(requestOptions).into(imgTitle);

        movieTitle.setText(movie.getTitle());
        tv_releaseDate.setText(movie.getReleasedDate());
        tv_movieOverViewDescription.setText(movie.getOverview());
    }

}
