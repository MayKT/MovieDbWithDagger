package com.mkttestprojects.moviedbwithdagger.di;
import com.mkttestprojects.moviedbwithdagger.di.login.LoginModule;
import com.mkttestprojects.moviedbwithdagger.di.login.LoginViewModelModule;
import com.mkttestprojects.moviedbwithdagger.di.main.MainFragmentBuildersModule;
import com.mkttestprojects.moviedbwithdagger.di.main.MainModule;
import com.mkttestprojects.moviedbwithdagger.di.main.MainScope;
import com.mkttestprojects.moviedbwithdagger.di.main.MainViewModelModule;
import com.mkttestprojects.moviedbwithdagger.di.moviedetail.MovieDetailModule;
import com.mkttestprojects.moviedbwithdagger.di.moviedetail.MovieDetailScope;
import com.mkttestprojects.moviedbwithdagger.di.moviedetail.MovieDetailViewModelModule;
import com.mkttestprojects.moviedbwithdagger.di.movietrailer.MovieTrailerModule;
import com.mkttestprojects.moviedbwithdagger.di.movietrailer.MovieTrailerScope;
import com.mkttestprojects.moviedbwithdagger.di.movietrailer.MovieTrailerViewModelModule;
import com.mkttestprojects.moviedbwithdagger.ui.firebaselogin.FirebaseLoginActivity;
import com.mkttestprojects.moviedbwithdagger.ui.firebasemain.FireBaseMainActivity;
import com.mkttestprojects.moviedbwithdagger.ui.login.LoginActivity;
import com.mkttestprojects.moviedbwithdagger.ui.main.MainActivity;
import com.mkttestprojects.moviedbwithdagger.ui.main.MapsActivity;
import com.mkttestprojects.moviedbwithdagger.ui.moviedetail.MovieDetailActivity;
import com.mkttestprojects.moviedbwithdagger.ui.moviedetail.MovieDetailViewModel;
import com.mkttestprojects.moviedbwithdagger.ui.movietrailer.PlayMovieTrailerActivity;

import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract  class ActivityBuildersModule {

    @MainScope
    @ContributesAndroidInjector(
            modules = {
                    MainModule.class, MainViewModelModule.class, MainFragmentBuildersModule.class,
            }
    )
    abstract MainActivity contributeMainActivity();

    @ContributesAndroidInjector(
            modules = {
                    LoginViewModelModule.class, LoginModule.class,
            }
    )
    abstract LoginActivity contributeLoginActivity();

    @MovieDetailScope
    @ContributesAndroidInjector(
            modules = {
                    MovieDetailModule.class, MovieDetailViewModelModule.class
            }
    )
    abstract MovieDetailActivity contributeMovieDetailActivity();

    @MovieTrailerScope
    @ContributesAndroidInjector(
            modules = {
                    MovieTrailerModule.class, MovieTrailerViewModelModule.class
            }
    )
    abstract PlayMovieTrailerActivity contributePlayMovieTrailerActivity();

    @ContributesAndroidInjector
    abstract FirebaseLoginActivity contributeFirebaseLoginActivity();

    @ContributesAndroidInjector
    abstract FireBaseMainActivity contributeFirebaseMainActivity();

    @ContributesAndroidInjector
    abstract MapsActivity contributeMapsActivity();
}
