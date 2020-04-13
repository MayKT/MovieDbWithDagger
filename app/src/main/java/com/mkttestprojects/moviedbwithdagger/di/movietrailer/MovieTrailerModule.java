package com.mkttestprojects.moviedbwithdagger.di.movietrailer;

import com.mkttestprojects.moviedbwithdagger.di.moviedetail.MovieDetailScope;
import com.mkttestprojects.moviedbwithdagger.network.moviedetail.MovieTrailerApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class MovieTrailerModule {

    @MovieTrailerScope
    @Provides
    static MovieTrailerApi provideMovieTrailerApi(Retrofit retrofit){
        return retrofit.create(MovieTrailerApi.class);
    }
}
