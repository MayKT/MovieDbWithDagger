package com.mkttestprojects.moviedbwithdagger.di.main.mylist;

import androidx.lifecycle.ViewModel;

import com.mkttestprojects.moviedbwithdagger.di.ViewModelKey;
import com.mkttestprojects.moviedbwithdagger.ui.main.mylist.MyListFragmentViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class MyListViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MyListFragmentViewModel.class)
    public abstract ViewModel bindMyListFragmentViewModel(MyListFragmentViewModel myListFragmentViewModel);

}
