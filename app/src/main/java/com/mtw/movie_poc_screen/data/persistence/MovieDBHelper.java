package com.mtw.movie_poc_screen.data.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mtw.movie_poc_screen.data.persistence.MovieContract.MovieEntry;

/**
 * Created by Aspire-V5 on 12/6/2017.
 */

public class MovieDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "movies.db";


    private static final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " + MovieEntry.TABLE_NAME + " (" +
            MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            MovieEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
            MovieEntry.COLUMN_ORIGINAL_TITLE + " TEXT NOT NULL, " +
            MovieEntry.COLUMN_VOTE_COUNT + " INTEGER, " +
            MovieEntry.COLUMN_VIDEO + " INTEGER DEFAULT 0, " +
            MovieEntry.COLUMN_VOTE_AVERAGE + " REAL, " +
            MovieEntry.COLUMN_POPULARITY + " REAL, " +
            MovieEntry.COLUMN_POSTER_PATH + " TEXT NOT NULL, " +
            MovieEntry.COLUMN_ORIGINAL_LANGUAGE + " TEXT NOT NULL, " +
            MovieEntry.COLUMN_BACKDROP_PATH + " TEXT NOT NULL, " +
            MovieEntry.COLUMN_ADULT + " INTEGER DEFAULT 0, " +
            MovieEntry.COLUMN_OVERVIEW + " TEXT NOT NULL, " +
            MovieEntry.COLUMN_RELEASE_DATE + " TEXT NOT NULL, " +
            " UNIQUE (" + MovieEntry.COLUMN_TITLE + ") ON CONFLICT IGNORE" +
            " );";


    private static final String SQL_CREATE_GENRE_TABLE = "CREATE TABLE " + MovieContract.GenreEntry.TABLE_NAME + " (" +
            MovieContract.GenreEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            MovieContract.GenreEntry.COLUMN_GENRE_ID + " TEXT, " +
            MovieContract.GenreEntry.COLUMN_GENRE_NAME + " TEXT, " +
            " UNIQUE (" + MovieContract.GenreEntry.COLUMN_GENRE_ID + ") ON CONFLICT REPLACE" +
            " );";

    private static final String SQL_CREATE_MOVIE_GENRE_TABLE = "CREATE TABLE " + MovieContract.MovieGenreEntry.TABLE_NAME + " (" +
            MovieContract.MovieGenreEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            MovieContract.MovieGenreEntry.COLUMN_GENRE_ID + " TEXT, " +
            MovieContract.MovieGenreEntry.COLUMN_MOVIE_ID + " TEXT" +
            " );";

    public MovieDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_MOVIE_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_GENRE_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_MOVIE_GENRE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieContract.MovieGenreEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieContract.GenreEntry.TABLE_NAME);

        onCreate(sqLiteDatabase);
    }
}
