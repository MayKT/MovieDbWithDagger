package com.mkttestprojects.moviedbwithdagger.ui.login;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.ViewModel;
import com.mkttestprojects.moviedbwithdagger.SessionManager;
import com.mkttestprojects.moviedbwithdagger.models.LoginModel;
import com.mkttestprojects.moviedbwithdagger.models.LoginRequestBody;
import com.mkttestprojects.moviedbwithdagger.models.RequestTokenBody;
import com.mkttestprojects.moviedbwithdagger.network.login.LoginApi;
import javax.inject.Inject;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import static com.mkttestprojects.moviedbwithdagger.util.AppConstant.DEVELOPER_KEY;


public class LoginViewModel extends ViewModel {

    private static final String TAG = "LoginViewModel";

    private SessionManager sessionManager;

    private LoginApi loginApi;

    @Inject
    public LoginViewModel(LoginApi loginApi) {
        this.loginApi = loginApi;
        Log.e(TAG, "LoginViewModel: view model is working");
    }

    public void onClickLogin(String username, String pwd) {

        if (username.length() == 0) {
//            Toast.makeText(this,"Please fill username",Toast.LENGTH_SHORT).show();
            return;
        } else if (pwd.length() == 0) {
//            Toast.makeText(this,"Please fill password",Toast.LENGTH_SHORT).show();
            return;
        } else {
            getRequestToken(username, pwd);
        }
    }

    public LiveData<LoginResource<LoginModel>> getRequestToken(String username, String pwd) {


       return null;
    }

    public LiveData<LoginResource<LoginModel>> getSessionId(String requestToken) {
        return LiveDataReactiveStreams.fromPublisher(loginApi.getSession(DEVELOPER_KEY, new RequestTokenBody(requestToken))
                .onErrorReturn(new Function<Throwable, LoginModel>() {
                    @Override
                    public LoginModel apply(Throwable throwable) throws Exception {
                        return null;
                    }
                })
                .map(new Function<LoginModel, LoginResource<LoginModel>>() {
                    @Override
                    public LoginResource<LoginModel> apply(LoginModel loginModel) throws Exception {
                        if(loginModel != null){
                            if(loginModel.isFailure()){

                            }
                            else {
                                saveLoginData(loginModel.getSession_id());

                            }
                        }
                        else {

                        }
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io())
        );
    }

    private void saveLoginData(String session_id) {

    }



    public LiveData<LoginResource<LoginModel>> validateLogin(String username, String password, String requestTokenBody) {
        return LiveDataReactiveStreams.fromPublisher(loginApi.getLoginValidate(DEVELOPER_KEY, new LoginRequestBody(username, password, requestTokenBody))
                .onErrorReturn(new Function<Throwable, LoginModel>() {
                    @Override
                    public LoginModel apply(Throwable throwable) throws Exception {
                        LoginModel loginModel = new LoginModel();
                        loginModel.setStatus_code(30);
                        loginModel.setFailure(true);
                        return null;
                    }
                })
                .map(new Function<LoginModel, LoginResource<LoginModel>>() {
                    @Override
                    public LoginResource<LoginModel> apply(LoginModel loginModel) throws Exception {
                        if(loginModel != null){
                            if(loginModel.getStatus_code() == 30){

                            }
                            else if(loginModel.isFailure()){

                            }
                            else {
                                getSessionId(requestTokenBody);
                            }
                        }
                        else {

                        }
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io())
        );
    }


}
