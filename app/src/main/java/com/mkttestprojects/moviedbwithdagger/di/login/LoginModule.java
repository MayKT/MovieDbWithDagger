package com.mkttestprojects.moviedbwithdagger.di.login;

import com.mkttestprojects.moviedbwithdagger.network.login.LoginApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class LoginModule {

    @Provides
    static LoginApi provideLoginApi(Retrofit retrofit){
        return retrofit.create(LoginApi.class);
    }
}
