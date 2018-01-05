package com.mtw.movie_poc_screen.data.models;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import com.mtw.movie_poc_screen.MovieApplication;
import com.mtw.movie_poc_screen.data.persistence.MovieContract;
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

    public void startLoadingPopularMovies(Context context) {
        MovieDataAgentImpl.getObjectInstance().loadPopularMovies(moviePageIndex,
                APIConstants.ACCESS_TOKEN, context);
    }

    public void loadMoreMovies(Context context) {
        MovieDataAgentImpl.getObjectInstance().loadPopularMovies(moviePageIndex, APIConstants.ACCESS_TOKEN, context);
    }


    public void forceRefreshMovies(Context context) {
        mMovies = new ArrayList<>();
        moviePageIndex = 1;
        startLoadingPopularMovies(context);
    }

    public List<MoviePopularVO> getMovies() {
        return mMovies;
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onMoviesDataLoaded(RestApiEvents.MoviesDataLoadedEvent event) {
        mMovies.addAll(event.getLoadedMovies());
        moviePageIndex = event.getLoadedPageIndex() + 1;


        //saving data into Persistence Layer
        ContentValues[] movieCVs = new ContentValues[event.getLoadedMovies().size()];
        List<ContentValues> genreCVList = new ArrayList<>();
        List<ContentValues> movieGenreCVList = new ArrayList<>();

        for (int index = 0; index < movieCVs.length; index++) {

            MoviePopularVO movies = event.getLoadedMovies().get(index);
            movieCVs[index] = movies.parseToContentValues();

            for (int genreId : movies.getGenreIds()) {
                ContentValues genreIdInMovieCV = new ContentValues();
                genreIdInMovieCV.put(MovieContract.GenreEntry.COLUMN_GENRE_ID, genreId);
                genreCVList.add(genreIdInMovieCV);
            }

            for (int i = 0; i < movies.getGenreIds().size(); i++) {
                ContentValues movieGenreCV = new ContentValues();
                movieGenreCV.put(MovieContract.MovieGenreEntry.COLUMN_GENRE_ID, String.valueOf(movies.getGenreIds()));
                movieGenreCV.put(MovieContract.MovieGenreEntry.COLUMN_MOVIE_ID, movies.getId());
                movieGenreCVList.add(movieGenreCV);
            }
        }

        int insertedGenre = event.getContext().getContentResolver().bulkInsert(MovieContract.GenreEntry.CONTENT_URI,
                genreCVList.toArray(new ContentValues[0]));
        Log.d(MovieApplication.LOG_TAG, "insertedGenre" + insertedGenre);

        int insertedMovieGenre = event.getContext().getContentResolver().bulkInsert(MovieContract.MovieGenreEntry.CONTENT_URI,
                movieGenreCVList.toArray(new ContentValues[0]));
        Log.d(MovieApplication.LOG_TAG, "insertedMovieGenre" + insertedMovieGenre);

        int insertedMovies = event.getContext().getContentResolver().bulkInsert(MovieContract.MovieEntry.CONTENT_URI,
                movieCVs);
        Log.d(MovieApplication.LOG_TAG, "Inserted movies" + insertedMovies);
    }


    public MoviePopularVO getMovieById(int id) {
        for (MoviePopularVO movies : mMovies) {
            if (movies.getId() == id) {
                return movies;
            }
        }
        return null;
    }
}
