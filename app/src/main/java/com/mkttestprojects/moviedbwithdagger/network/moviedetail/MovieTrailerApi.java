package com.mkttestprojects.moviedbwithdagger.network.moviedetail;

import com.mkttestprojects.moviedbwithdagger.models.MovieTrailerModel;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieTrailerApi {

    @GET("movie/{movie_id}/videos")
    Flowable<MovieTrailerModel> getMovieTrailer(@Path("movie_id") int movieId,
                                                @Query("api_key") String apiKey);

}
