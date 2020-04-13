package com.mkttestprojects.moviedbwithdagger;

import com.mkttestprojects.moviedbwithdagger.custom_control.AndroidCommonSetup;
import com.mkttestprojects.moviedbwithdagger.di.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

public class BaseApplication extends dagger.android.support.DaggerApplication {

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {

        AndroidCommonSetup.getInstance().init(getApplicationContext());

        return DaggerAppComponent.builder().application(this).build();
//        return null;
    }
}
