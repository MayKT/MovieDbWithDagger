package com.mkttestprojects.moviedbwithdagger.di.movietrailer;

import androidx.lifecycle.ViewModel;

import com.mkttestprojects.moviedbwithdagger.di.ViewModelKey;
import com.mkttestprojects.moviedbwithdagger.ui.movietrailer.MovieTrailerViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class MovieTrailerViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MovieTrailerViewModel.class)
    public abstract ViewModel bindMovieTrailerViewModel(MovieTrailerViewModel movieTrailerViewModel);

}
