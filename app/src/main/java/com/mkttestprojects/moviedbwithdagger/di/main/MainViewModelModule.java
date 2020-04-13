package com.mkttestprojects.moviedbwithdagger.di.main;

import androidx.lifecycle.ViewModel;

import com.mkttestprojects.moviedbwithdagger.di.ViewModelKey;
import com.mkttestprojects.moviedbwithdagger.ui.login.LoginViewModel;
import com.mkttestprojects.moviedbwithdagger.ui.main.home.HomeFragmentViewModel;
import com.mkttestprojects.moviedbwithdagger.ui.main.profile.ProfileFragmentViewModel;
import com.mkttestprojects.moviedbwithdagger.ui.main.search.SearchFragmentViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class MainViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeFragmentViewModel.class)
    public abstract ViewModel bindHomeFragmentViewModel(HomeFragmentViewModel homeFragmentViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SearchFragmentViewModel.class)
    public abstract ViewModel bindSearchFragmentViewModel(SearchFragmentViewModel homeFragmentViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ProfileFragmentViewModel.class)
    public abstract ViewModel bindProfileFragmentViewModel(ProfileFragmentViewModel profileFragmentViewModel);


}
