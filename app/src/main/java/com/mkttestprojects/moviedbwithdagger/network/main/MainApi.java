package com.mkttestprojects.moviedbwithdagger.network.main;

import com.mkttestprojects.moviedbwithdagger.models.AccountModel;
import com.mkttestprojects.moviedbwithdagger.models.MovieListModel;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MainApi {
    @GET("movie/popular")
    Flowable<MovieListModel> getTrendingMovies(@Query("api_key") String apiKey,
                                               @Query("language") String language,
                                               @Query("page") int page);

    @GET("movie/upcoming")
    Flowable<MovieListModel> getUpComingMovies(@Query("api_key") String apiKey,
                                               @Query("language") String language,
                                               @Query("page") int page);

    @GET("movie/top_rated")
    Flowable<MovieListModel> getTopRatedMovies(@Query("api_key") String apiKey,
                                               @Query("language") String language,
                                               @Query("page") int page);

    @GET("movie/now_playing")
    Flowable<MovieListModel> getNowPlayingMovies(@Query("api_key") String apiKey,
                                                 @Query("language") String language,
                                                 @Query("page") int page);

    @GET("search/movie")
    Flowable<MovieListModel> getSearchResults(@Query("api_key") String apiKey,
                                              @Query("language") String language,
                                              @Query("query") String query,
                                              @Query("page") int page);
    @GET("account")
    Flowable<AccountModel> getAccountBySessionId(@Query("api_key") String apiKey,
                                                 @Query("session_id")String sessionId);

}
