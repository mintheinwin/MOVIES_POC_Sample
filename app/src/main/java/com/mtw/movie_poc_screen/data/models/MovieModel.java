package com.mtw.movie_poc_screen.data.models;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import com.mtw.movie_poc_screen.data.vo.MoviePopularVO;
import com.mtw.movie_poc_screen.events.RestApiEvents;
import com.mtw.movie_poc_screen.network.MovieDataAgentImpl;
import com.mtw.movie_poc_screen.utils.APIConstants;

/**
 * Created by Aspire-V5 on 12/6/2017.
 */

public class MovieModel {

    private static MovieModel objectInstance;
    private int moviePageIndex = 1;

    private List<MoviePopularVO> mMovies;

    private MovieModel() {
        EventBus.getDefault().register(this);
        mMovies = new ArrayList<>();
    }

    public static MovieModel getInstance() {
        if (objectInstance == null) {
            objectInstance = new MovieModel();
        }
        return objectInstance;
    }

    public void startLoadingPopularMovies() {
        MovieDataAgentImpl.getObjectInstance().loadPopularMovies(moviePageIndex, APIConstants.ACCESS_TOKEN);
    }

    public void loadMoreMovies() {
        MovieDataAgentImpl.getObjectInstance().loadPopularMovies(moviePageIndex, APIConstants.ACCESS_TOKEN);
    }


    public void forceRefreshMovies() {
        mMovies=new ArrayList<>();
        moviePageIndex = 1;
        startLoadingPopularMovies();
    }

    public List<MoviePopularVO> getMovies() {
        return mMovies;
    }

    @Subscribe
    public void onMoviesDataLoaded(RestApiEvents.MoviesDataLoadedEvent event) {
        mMovies.addAll(event.getLoadedMovies());
        moviePageIndex = event.getLoadedPageIndex() + 1;
    }


    public MoviePopularVO getMovieById(int id){
        for(MoviePopularVO movies : mMovies){
            if(movies.getId()== id){
                return movies;
            }
        }
        return null;
    }
}
