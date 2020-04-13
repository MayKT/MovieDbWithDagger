package com.mkttestprojects.moviedbwithdagger.di.moviedetail;

import androidx.lifecycle.ViewModel;

import com.mkttestprojects.moviedbwithdagger.di.ViewModelKey;
import com.mkttestprojects.moviedbwithdagger.ui.login.LoginViewModel;
import com.mkttestprojects.moviedbwithdagger.ui.moviedetail.MovieDetailViewModel;
import com.mkttestprojects.moviedbwithdagger.ui.movietrailer.MovieTrailerViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class MovieDetailViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailViewModel.class)
    public abstract ViewModel bindMovieDetailViewModel(MovieDetailViewModel movieDetailViewModel);

}
