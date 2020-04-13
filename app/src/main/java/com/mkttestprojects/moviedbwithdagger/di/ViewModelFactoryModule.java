package com.mkttestprojects.moviedbwithdagger.di;

import androidx.lifecycle.ViewModelProvider;

import com.mkttestprojects.moviedbwithdagger.viewmodels.ViewModelProviderFactory;

import dagger.Binds;
import dagger.Module;

@Module
public abstract  class ViewModelFactoryModule  {

    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelProviderFactory viewModelProviderFactory);

}
