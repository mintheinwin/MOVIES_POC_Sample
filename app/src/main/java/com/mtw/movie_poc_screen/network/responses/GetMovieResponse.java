package com.mtw.movie_poc_screen.network.responses;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import com.mtw.movie_poc_screen.data.vo.MoviePopularVO;

/**
 * Created by Aspire-V5 on 12/6/2017.
 */

public class GetMovieResponse {
    @SerializedName("code")
    private int code;
    @SerializedName("message")
    private String message;
    @SerializedName("apiVersion")
    private String apiVersion;
    @SerializedName("page")
    private int page;
    @SerializedName("popular-movies")
    private List<MoviePopularVO> popularMovies;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public int getPage() {
        return page;
    }

    public List<MoviePopularVO> getPopularMovies() {
        if (popularMovies == null) {
            popularMovies = new ArrayList<>();
        }
        return popularMovies;
    }
}
