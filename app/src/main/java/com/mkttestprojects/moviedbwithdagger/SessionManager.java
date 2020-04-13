package com.mkttestprojects.moviedbwithdagger;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import com.mkttestprojects.moviedbwithdagger.models.LoginModel;
import com.mkttestprojects.moviedbwithdagger.ui.login.LoginResource;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SessionManager {

    private static final String TAG = "SessionManager";

    private MediatorLiveData<LoginResource<LoginModel>> cacheUser = new MediatorLiveData<>();

    @Inject
    public SessionManager() {
    }

    public void authenticateWithSessionId(final LiveData<LoginResource<LoginModel>> source){
        if(cacheUser != null){
            cacheUser.setValue(LoginResource.loading((LoginModel) null));
            cacheUser.addSource(source, new Observer<LoginResource<LoginModel>>() {
                @Override
                public void onChanged(LoginResource<LoginModel> userModelAuthResource) {
                    cacheUser.setValue(userModelAuthResource);
                    cacheUser.removeSource(source);

                    if(userModelAuthResource.status.equals(LoginResource.AuthStatus.ERROR)){
                        cacheUser.setValue(LoginResource.<LoginModel>logout());
                    }
                }
            });
        }
        else {
            Log.e(TAG, "authenticateWithId: previous session detected.Retriving user from cache");
        }
    }
    public void logout(){
        Log.e(TAG, "logout: logging out" );
        cacheUser.setValue(LoginResource.<LoginModel>logout());
    }
    public LiveData<LoginResource<LoginModel>> getAuthUser(){
        return cacheUser;
    }
}
