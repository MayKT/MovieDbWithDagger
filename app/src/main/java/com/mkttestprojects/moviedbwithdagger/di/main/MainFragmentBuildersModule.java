package com.mkttestprojects.moviedbwithdagger.di.main;

import com.mkttestprojects.moviedbwithdagger.ui.main.home.HomeFragment;
import com.mkttestprojects.moviedbwithdagger.ui.main.mylist.MyListFragment;
import com.mkttestprojects.moviedbwithdagger.ui.main.profile.ProfileFragment;
import com.mkttestprojects.moviedbwithdagger.ui.main.search.SearchFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainFragmentBuildersModule  {

    @ContributesAndroidInjector
    abstract HomeFragment contributeHomeFragment();

    @ContributesAndroidInjector
    abstract SearchFragment contributeSearchFragment();

    @ContributesAndroidInjector
    abstract ProfileFragment contributeProfileFragment();

    @ContributesAndroidInjector
    abstract MyListFragment contributeMyListFragment();


}
