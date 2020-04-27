package com.mkttestprojects.moviedbwithdagger.di.main.mylist;

import com.mkttestprojects.moviedbwithdagger.network.main.mylist.MyListApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class MyListModule {

    @MyListScope
    @Provides
    static MyListApi provideMyListApi(Retrofit retrofit){
        return retrofit.create(MyListApi.class);
    }
}
