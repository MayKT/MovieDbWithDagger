package com.mkttestprojects.moviedbwithdagger.network.moviedetail;

import com.mkttestprojects.moviedbwithdagger.models.MovieListModel;
import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieDetailApi {
    @GET("movie/{movie_id}")
    Flowable<MovieListModel> getMovieDetails(@Path("movie_id") int movieId,
                                             @Query("api_key") String apiKey);

    @GET("movie/{movie_id}/similar")
    Flowable<MovieListModel> getSimilarMoives(@Path("movie_id") int movieId,
                                                @Query("api_key") String apiKey);

}
