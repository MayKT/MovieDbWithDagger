package com.mkttestprojects.moviedbwithdagger.di.main;

import com.mkttestprojects.moviedbwithdagger.di.main.mylist.MyListScope;
import com.mkttestprojects.moviedbwithdagger.network.main.MainApi;
import com.mkttestprojects.moviedbwithdagger.network.main.mylist.MyListApi;
import com.mkttestprojects.moviedbwithdagger.ui.main.home.VerticalMoiveListAdapter;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class MainModule {

    @MainScope
    @Provides
    static VerticalMoiveListAdapter provideVerticalMovieAdapter(){
        return new VerticalMoiveListAdapter();
    }

    @MainScope
    @Provides
    static MainApi proviedMainApi(Retrofit retrofit){
        return retrofit.create(MainApi.class);
    }

    @MainScope
    @Provides
    static MyListApi provideMyListApi(Retrofit retrofit){
        return retrofit.create(MyListApi.class);
    }

}
