package com.mkttestprojects.moviedbwithdagger.di.moviedetail;

import com.mkttestprojects.moviedbwithdagger.network.moviedetail.MovieDetailApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class MovieDetailModule {

    @MovieDetailScope
    @Provides
    static MovieDetailApi provideMovieDetailApi(Retrofit retrofit){
        return retrofit.create(MovieDetailApi.class);
    }

}
