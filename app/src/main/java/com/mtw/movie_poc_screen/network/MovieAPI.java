package com.mtw.movie_poc_screen.network;

import com.mtw.movie_poc_screen.network.responses.GetMovieResponse;
import com.mtw.movie_poc_screen.utils.APIConstants;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Aspire-V5 on 12/6/2017.
 */

public interface MovieAPI {

    @FormUrlEncoded
    @POST(APIConstants.GET_POPULAR_API)
    Call<GetMovieResponse> loadPopularMovies(
            @Field("page") int page
            , @Field("access_token") String accessToken);
}
