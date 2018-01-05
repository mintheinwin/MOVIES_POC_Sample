package com.mtw.movie_poc_screen.data.persistence;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Aspire-V5 on 12/25/2017.
 */

public class MoviesProvider extends ContentProvider {

    public static final int MOVIES = 10;
    public static final int GENER = 20;
    public static final int MOVIE_GENER = 30;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private MovieDBHelper mMovieDBHelper;

    @Override
    public boolean onCreate() {
        mMovieDBHelper = new MovieDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        Cursor queryCursor = mMovieDBHelper.getReadableDatabase().query(getTableName(uri),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder);
        if (getContext() != null) {
            queryCursor.setNotificationUri(getContext().getContentResolver(), uri);
        }

        return queryCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        switch (sUriMatcher.match(uri)) {
            case MOVIES:
                return MovieContract.MovieEntry.DIR_TYPE;
            case GENER:
                return MovieContract.GenreEntry.DIR_TYPE;
            case MOVIE_GENER:
                return MovieContract.MovieGenreEntry.DIR_TYPE;
        }

        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        SQLiteDatabase db = mMovieDBHelper.getWritableDatabase();
        String tableName = getTableName(uri);
        long _id = db.insert(tableName, null, values);

        if (_id > 0) {
            Uri contentUri = getContentUri(uri);
            Uri insertedUri = ContentUris.withAppendedId(contentUri, _id);

            if (getContext() != null) {
                getContext().getContentResolver().notifyChange(uri, null);
            }

            return insertedUri;
        }

        return null;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {

        final SQLiteDatabase db = mMovieDBHelper.getWritableDatabase();
        String tableName = getTableName(uri);
        int insertedCount = 0;

        try {
            db.beginTransaction();
            for (ContentValues cv : values) {
                long _id = db.insert(tableName, null, cv);
                if (_id > 0) {
                    insertedCount++;
                }
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            //db.close();
        }

        Context context = getContext();
        if (context != null) {
            context.getContentResolver().notifyChange(uri, null);
        }
        return insertedCount;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        final SQLiteDatabase db = mMovieDBHelper.getWritableDatabase();
        int rowDeleted;
        String tableName = getTableName(uri);

        rowDeleted = db.delete(tableName, selection, selectionArgs);

        if (getContext() != null && rowDeleted > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values,
                      @Nullable String selection, @Nullable String[] selectionArgs) {

        final SQLiteDatabase db = mMovieDBHelper.getWritableDatabase();
        int rowUpdated;
        String tableName = getTableName(uri);

        rowUpdated = db.update(tableName, values, selection, selectionArgs);
        Context context = getContext();
        if (context != null && rowUpdated > 0) {
            context.getContentResolver().notifyChange(uri, null);
        }
        return rowUpdated;
    }

    private static UriMatcher buildUriMatcher() {

        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_MOVIES, MOVIES);
        uriMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_GENRE, GENER);
        uriMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_MOVIE_GENRE, MOVIE_GENER);

        return uriMatcher;
    }

    private String getTableName(Uri uri) {

        switch (sUriMatcher.match(uri)) {
            case MOVIES:
                return MovieContract.MovieEntry.TABLE_NAME;
            case GENER:
                return MovieContract.GenreEntry.TABLE_NAME;
            case MOVIE_GENER:
                return MovieContract.MovieGenreEntry.TABLE_NAME;
        }

        return null;
    }

    private Uri getContentUri(Uri uri) {

        switch (sUriMatcher.match(uri)) {
            case MOVIES:
                return MovieContract.MovieEntry.CONTENT_URI;
            case GENER:
                return MovieContract.GenreEntry.CONTENT_URI;
            case MOVIE_GENER:
                return MovieContract.MovieGenreEntry.CONTENT_URI;
        }

        return null;
    }
}
