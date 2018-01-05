package com.mtw.movie_poc_screen.fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.util.Pair;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.mtw.movie_poc_screen.R;
import com.mtw.movie_poc_screen.activities.MovieDetailsOverviewActivity;
import com.mtw.movie_poc_screen.adapters.MovieAdapter;
import com.mtw.movie_poc_screen.components.EmptyViewPod;
import com.mtw.movie_poc_screen.components.SmartRecyclerView;
import com.mtw.movie_poc_screen.components.SmartVerticalScrollListener;
import com.mtw.movie_poc_screen.data.models.MovieModel;
import com.mtw.movie_poc_screen.data.persistence.MovieContract;
import com.mtw.movie_poc_screen.data.vo.MoviePopularVO;
import com.mtw.movie_poc_screen.events.RestApiEvents;

/**
 * Created by Aspire-V5 on 12/6/2017.
 */
public class MoviestListFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<Cursor> {
    @BindView(R.id.rv_most_popular)
    SmartRecyclerView rvMostPopular;

    MovieAdapter adapter;

    @BindView(R.id.vp_empty_movie)
    EmptyViewPod vpEmptyMovie;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    public MoviestListFragment() {
    }

    public static MoviestListFragment newInstance() {
        MoviestListFragment fragment = new MoviestListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_most_popular, container, false);
        ButterKnife.bind(this, view);
        rvMostPopular.setHasFixedSize(true);
        adapter = new MovieAdapter(getContext(), this);
        rvMostPopular.setEmptyView(vpEmptyMovie);
        rvMostPopular.setAdapter(adapter);
        rvMostPopular.setLayoutManager(new LinearLayoutManager(container.getContext()));

        SmartVerticalScrollListener scrollListener = new SmartVerticalScrollListener(new SmartVerticalScrollListener.OnSmartVerticalScrollListener() {
            @Override
            public void onListEndReached() {
                MovieModel.getInstance().loadMoreMovies(getActivity());
            }
        });

        rvMostPopular.addOnScrollListener(scrollListener);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                MovieModel.getInstance().forceRefreshMovies(getActivity());
            }
        });

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        List<MoviePopularVO> newsList = MovieModel.getInstance().getMovies();
        if (!newsList.isEmpty()) {
            adapter.setNewData(newsList);
        } else {
            swipeRefreshLayout.setRefreshing(true);
        }
    }

    /*@Subscribe(threadMode = ThreadMode.MAIN)
    public void onMovieDataLoaded(RestApiEvents.MoviesDataLoadedEvent event) {
        adapter.appendNewData(event.getLoadedMovies());
        swipeRefreshLayout.setRefreshing(false);
    }*/

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onErrorInvokingAPI(RestApiEvents.ErrorInvokingAPIEvent event) {
        Snackbar.make(rvMostPopular, event.getErrorMsg(), Snackbar.LENGTH_INDEFINITE).show();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        MovieModel.getInstance().startLoadingPopularMovies(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onItemTap(MoviePopularVO movie) {
        super.onItemTap(movie);
        Intent intent = MovieDetailsOverviewActivity.newIntent(getActivity().getApplicationContext(),movie);
        startActivity(intent);
    }

    @Override
    public void onMovieOverviewTap(MoviePopularVO movie) {
        super.onMovieOverviewTap(movie);
        Intent intent = MovieDetailsOverviewActivity.newIntent(getActivity().getApplicationContext(),movie);
        startActivity(intent);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(),
                MovieContract.MovieEntry.CONTENT_URI,
                null,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null && data.moveToFirst()) {
            List<MoviePopularVO> movieList = new ArrayList<>();

            do {
                MoviePopularVO movie = MoviePopularVO.parseFromCursor(getActivity(), data);
                movieList.add(movie);
            } while (data.moveToNext());

            adapter.setNewData(movieList);
            swipeRefreshLayout.setRefreshing(false);
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
