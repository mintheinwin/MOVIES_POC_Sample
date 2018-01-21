package com.mtw.movie_poc_screen.data.vo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.google.gson.annotations.SerializedName;
import com.mtw.movie_poc_screen.persistence.MovieContract;

import java.util.ArrayList;

/**
 * Created by Aspire-V5 on 12/6/2017.
 */

public class MoviePopularVO {
    @SerializedName("vote_count")
    private int voteCount;
    @SerializedName("id")
    private int id;
    @SerializedName("video")
    private boolean video;
    @SerializedName("vote_average")
    private Double voteAverage;
    @SerializedName("title")
    private String title;
    @SerializedName("popularity")
    private Double popularity;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("original_language")
    private String originalLanguage;
    @SerializedName("original_title")
    private String originalTitle;
    @SerializedName("genre_ids")
    private ArrayList<Integer> genreIds;
    @SerializedName("backdrop_path")
    private String backDropPath;
    @SerializedName("adult")
    private boolean adult;
    @SerializedName("overview")
    private String overview;
    @SerializedName("release_date")
    private String releasedDate;

    public int getVoteCount() {
        return voteCount;
    }

    public int getId() {
        return id;
    }

    public boolean isVideo() {
        return video;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public String getTitle() {
        return title;
    }

    public Double getPopularity() {
        return popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public ArrayList<Integer> getGenreIds() {
        return genreIds;
    }

    public String getBackDropPath() {
        return backDropPath;
    }

    public boolean isAdult() {
        return adult;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleasedDate() {
        return releasedDate;
    }


    public ContentValues parseToContentValues() {

        ContentValues contentValues = new ContentValues();

        contentValues.put(MovieContract.MovieEntry.COLUMN_VOTE_COUNT, voteCount);
        contentValues.put(MovieContract.MovieEntry.COLUMN_ID, id);
        contentValues.put(MovieContract.MovieEntry.COLUMN_VIDEO, video);
        contentValues.put(MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE, voteAverage);
        contentValues.put(MovieContract.MovieEntry.COLUMN_TITLE, title);
        contentValues.put(MovieContract.MovieEntry.COLUMN_POPULARITY, popularity);
        contentValues.put(MovieContract.MovieEntry.COLUMN_POSTER_PATH, posterPath);
        contentValues.put(MovieContract.MovieEntry.COLUMN_ORIGINAL_LANGUAGE, originalLanguage);
        contentValues.put(MovieContract.MovieEntry.COLUMN_ORIGINAL_TITLE, originalTitle);
        contentValues.put(MovieContract.MovieEntry.COLUMN_BACKDROP_PATH, backDropPath);
        contentValues.put(MovieContract.MovieEntry.COLUMN_ADULT, adult);
        contentValues.put(MovieContract.MovieEntry.COLUMN_OVERVIEW, overview);
        contentValues.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE, releasedDate);

        return contentValues;
    }

    public static MoviePopularVO parseFromCursor(Context context, Cursor cursor) {

        MoviePopularVO movie = new MoviePopularVO();

        movie.voteCount = cursor.getInt(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_VOTE_COUNT));
        movie.id = cursor.getInt(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_ID));
        if (cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_VIDEO) == 1) {
            movie.video = true;
        } else if (cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_VIDEO) == 0) {
            movie.video = false;
        }
        movie.voteAverage = cursor.getDouble(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE));
        movie.title = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_TITLE));
        movie.popularity = cursor.getDouble(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_POPULARITY));
        movie.posterPath = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_POSTER_PATH));
        movie.originalLanguage = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_ORIGINAL_LANGUAGE));
        movie.originalTitle = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_ORIGINAL_TITLE));
        movie.backDropPath = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_BACKDROP_PATH));
        if (cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_ADULT) == 1) {
            movie.adult = true;
        } else if (cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_ADULT) == 0) {
            movie.adult = false;
        }
        movie.overview = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_OVERVIEW));
        movie.releasedDate = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_RELEASE_DATE));

        movie.genreIds = loadGenreInMovie(context, String.valueOf(movie.getId()));

        return movie;
    }

    private static ArrayList<Integer> loadGenreInMovie(Context context, String movieId) {
        Cursor genreInMovieCursor = context.getContentResolver().query(MovieContract.MovieGenreEntry.CONTENT_URI,
                null,
                MovieContract.MovieGenreEntry.COLUMN_MOVIE_ID + " = ?", new String[]{movieId},
                null);

        if (genreInMovieCursor != null && genreInMovieCursor.moveToFirst()) {
            ArrayList<Integer> genreInMovie = new ArrayList<>();
            do {
                genreInMovie.add(
                        genreInMovieCursor.getInt(
                                genreInMovieCursor.getColumnIndex(MovieContract.MovieGenreEntry.COLUMN_GENRE_ID)
                        )
                );
            } while (genreInMovieCursor.moveToNext());
            genreInMovieCursor.close();
            return genreInMovie;
        }
        return null;
    }

}
