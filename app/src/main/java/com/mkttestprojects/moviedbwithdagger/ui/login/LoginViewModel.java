package com.mkttestprojects.moviedbwithdagger.ui.login;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.mkttestprojects.moviedbwithdagger.models.LoginModel;
import com.mkttestprojects.moviedbwithdagger.models.LoginRequestBody;
import com.mkttestprojects.moviedbwithdagger.models.RequestTokenBody;
import com.mkttestprojects.moviedbwithdagger.network.login.LoginApi;
import com.mkttestprojects.moviedbwithdagger.ui.main.Resource;

import javax.inject.Inject;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import static com.mkttestprojects.moviedbwithdagger.util.AppConstant.DEVELOPER_KEY;


public class LoginViewModel extends ViewModel {

    private static final String TAG = "LoginViewModel";

    private LoginApi loginApi;

    private MediatorLiveData<Resource<LoginModel>> requestToken;
    private MediatorLiveData<Resource<LoginModel>> loginvalidate;
    private MediatorLiveData<Resource<LoginModel>> sessionId;

    @Inject
    public LoginViewModel(LoginApi loginApi) {
        this.loginApi = loginApi;
        Log.e(TAG, "LoginViewModel: view model is working");
    }

   public LiveData<Resource<LoginModel>> observeRequestToken(String username,String pwd){
        requestToken = new MediatorLiveData<>();
        requestToken.setValue(Resource.loading(null));
        final LiveData<Resource<LoginModel>> resourceLiveData = LiveDataReactiveStreams.fromPublisher(
                loginApi.getRequestToken(DEVELOPER_KEY)
                .onErrorReturn(new Function<Throwable, LoginModel>() {
                    @Override
                    public LoginModel apply(Throwable throwable) throws Exception {
                        return null;
                    }
                })
                .map(new Function<LoginModel, Resource<LoginModel>>() {
                    @Override
                    public Resource<LoginModel> apply(LoginModel loginModel) throws Exception {
                        if(loginModel != null){
                            return Resource.success(loginModel);
                        }
                        return Resource.error("Something went wrong",null);
                    }
                })
                .subscribeOn(Schedulers.io())
        );
        requestToken.addSource(resourceLiveData, new Observer<Resource<LoginModel>>() {
            @Override
            public void onChanged(Resource<LoginModel> loginModelResource) {
                requestToken.setValue(loginModelResource);
                requestToken.removeSource(resourceLiveData);
            }
        });
        return requestToken;
   }

   public LiveData<Resource<LoginModel>> observeValidateLogin(LoginRequestBody loginRequestBody){
        loginvalidate = new MediatorLiveData<>();
        loginvalidate.setValue(Resource.loading(null));
        final LiveData<Resource<LoginModel>> resourceLiveData = LiveDataReactiveStreams.fromPublisher(
                loginApi.getLoginValidate(DEVELOPER_KEY,loginRequestBody)
                .onErrorReturn(new Function<Throwable, LoginModel>() {
                    @Override
                    public LoginModel apply(Throwable throwable) throws Exception {
                        return null;
                    }
                })
                .map(new Function<LoginModel, Resource<LoginModel>>() {
                    @Override
                    public Resource<LoginModel> apply(LoginModel loginModel) throws Exception {
                        if(loginModel != null){
                            return Resource.success(loginModel);
                        }
                        return Resource.error("Something went wrong",loginModel);
                    }
                })
                .subscribeOn(Schedulers.io())
        );
        loginvalidate.addSource(resourceLiveData, new Observer<Resource<LoginModel>>() {
            @Override
            public void onChanged(Resource<LoginModel> loginModelResource) {
                loginvalidate.setValue(loginModelResource);
                loginvalidate.removeSource(resourceLiveData);
            }
        });
        return loginvalidate;
   }

   public LiveData<Resource<LoginModel>> observeSessionId(RequestTokenBody requestTokenBody){
        sessionId = new MediatorLiveData<>();
        sessionId.setValue(Resource.loading(null));
        final LiveData<Resource<LoginModel>> resourceLiveData = LiveDataReactiveStreams.fromPublisher(
                loginApi.getSession(DEVELOPER_KEY,requestTokenBody)
                .onErrorReturn(new Function<Throwable, LoginModel>() {
                    @Override
                    public LoginModel apply(Throwable throwable) throws Exception {
                        return null;
                    }
                })
                .map(new Function<LoginModel, Resource<LoginModel>>() {
                    @Override
                    public Resource<LoginModel> apply(LoginModel loginModel) throws Exception {
                        if(loginModel != null){
                            return Resource.success(loginModel);
                        }
                        return Resource.error("Something went wrong",loginModel);
                    }
                })
                .subscribeOn(Schedulers.io())
        );
        sessionId.addSource(resourceLiveData, new Observer<Resource<LoginModel>>() {
            @Override
            public void onChanged(Resource<LoginModel> loginModelResource) {
                sessionId.setValue(loginModelResource);
                sessionId.removeSource(resourceLiveData);
            }
        });
        return sessionId;
   }

}
