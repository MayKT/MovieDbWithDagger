package com.mkttestprojects.moviedbwithdagger.network.main.mylist;

import com.mkttestprojects.moviedbwithdagger.models.MovieListModel;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MyListApi {

    @GET("account/{account_id/watchlist/movies")
    Flowable<MovieListModel> getWatchListMovies(
            @Query("api_key") String apiKey,
            @Query("session_id") String sessionId);

    @GET("account/{account_id/rated/movies")
    Flowable<MovieListModel> getRatedMovies(
            @Query("api_key") String apiKey,
            @Query("session_id") String sessionId);
}
