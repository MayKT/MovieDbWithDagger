package com.mkttestprojects.moviedbwithdagger.network.moviedetail;

import com.mkttestprojects.moviedbwithdagger.models.MovieListModel;
import com.mkttestprojects.moviedbwithdagger.models.RateRequestBody;
import com.mkttestprojects.moviedbwithdagger.models.RatingModel;
import com.mkttestprojects.moviedbwithdagger.models.WatchListModel;
import com.mkttestprojects.moviedbwithdagger.models.WatchListRequestBody;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieDetailApi {
    @GET("movie/{movie_id}")
    Flowable<MovieListModel> getMovieDetails(@Path("movie_id") int movieId,
                                             @Query("api_key") String apiKey);

    @GET("movie/{movie_id}/similar")
    Flowable<MovieListModel> getSimilarMoives(@Path("movie_id") int movieId,
                                                @Query("api_key") String apiKey);

    @POST("account/{account_id}/watchlist")
    Flowable<WatchListModel> addMoiveToWatchList(
            @Query("api_key") String apiKey,
            @Query("session_id") String sessionId,
            @Body WatchListRequestBody watchListRequestBody
    );

    @POST("movie/{movie_id}/rating")
    Flowable<RatingModel> rateMovie(@Path("movie_id")int movieId,
                                    @Query("api_key") String apiKey,
                                    @Query("session_id")String sessionId,
                                    @Body RateRequestBody ratingRequestBody
    );

}
