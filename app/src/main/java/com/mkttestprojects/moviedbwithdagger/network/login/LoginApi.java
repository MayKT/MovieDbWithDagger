package com.mkttestprojects.moviedbwithdagger.network.login;

import com.mkttestprojects.moviedbwithdagger.models.LoginModel;
import com.mkttestprojects.moviedbwithdagger.models.LoginRequestBody;
import com.mkttestprojects.moviedbwithdagger.models.RequestTokenBody;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface LoginApi {

    @GET("authentication/token/new")
    Flowable<LoginModel> getRequestToken(@Query("api_key") String apiKey);


    @POST("authentication/token/validate_with_login")
    Flowable<LoginModel> getLoginValidate(@Query("api_key") String apiKey,
                                                  @Body LoginRequestBody requestBody);

    @POST("authentication/session/new")
    Flowable<LoginModel> getSession(@Query("api_key") String apiKey,
                                            @Body RequestTokenBody requestTokenBody);

}
