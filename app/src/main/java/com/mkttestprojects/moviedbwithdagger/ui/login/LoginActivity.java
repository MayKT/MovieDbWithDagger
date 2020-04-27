package com.mkttestprojects.moviedbwithdagger.ui.login;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.mkttestprojects.moviedbwithdagger.R;
import com.mkttestprojects.moviedbwithdagger.common.BaseActivity;
import com.mkttestprojects.moviedbwithdagger.custom_control.MyanButton;
import com.mkttestprojects.moviedbwithdagger.custom_control.MyanEditText;
import com.mkttestprojects.moviedbwithdagger.custom_control.MyanProgressDialog;
import com.mkttestprojects.moviedbwithdagger.models.LoginModel;
import com.mkttestprojects.moviedbwithdagger.models.LoginRequestBody;
import com.mkttestprojects.moviedbwithdagger.models.RequestTokenBody;
import com.mkttestprojects.moviedbwithdagger.ui.main.MainActivity;
import com.mkttestprojects.moviedbwithdagger.ui.main.Resource;
import com.mkttestprojects.moviedbwithdagger.util.SharePreferenceHelper;
import com.mkttestprojects.moviedbwithdagger.viewmodels.ViewModelProviderFactory;

import javax.inject.Inject;

import butterknife.BindView;

public class LoginActivity extends BaseActivity {

    private static final String TAG = "LoginActivity";

    private LoginViewModel loginViewModel;

    private String userName;

    private String password;

    private LoginRequestBody loginRequestBody;

    private String sessionID;

    private SharePreferenceHelper msharePreference;

    private MyanProgressDialog myanProgressDialog;

    @BindView(R.id.et_email)
    EditText etUserName;

    @BindView(R.id.et_pwd)
    EditText etPassword;

    @BindView(R.id.btn_login)
    Button btnLogin;

    @Inject
    ViewModelProviderFactory providerFactory;

    public static Intent LoginActivityIntent(Context context){
        return new Intent(context,LoginActivity.class);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_login;
    }

    @Override
    protected void setUpContents(Bundle savedInstanceState) {
//        setupToolbar(false);
//        setupToolbarText("Login");
        init();
    }

    private void init() {
        loginViewModel = new ViewModelProvider(this, providerFactory).get(LoginViewModel.class);

        msharePreference = new SharePreferenceHelper(this);

        myanProgressDialog = new MyanProgressDialog(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName = etUserName.getText().toString();

                password = etPassword.getText().toString();

                if (userName.length() == 0) {
                    Log.e(TAG, "setUpContents: username password " + userName + password );
                    showDialogMsg(getString(R.string.please_fill_username));
                }
                else if (password.length() == 0) {
                    showDialogMsg(getString(R.string.please_fill_password));
                }
                else {
                    getRequestToken(userName, password);
                }
            }
        });
    }

    private void getRequestToken(String userName, String password) {
        loginViewModel.observeRequestToken(userName,password).observe(this, new Observer<Resource<LoginModel>>() {
            @Override
            public void onChanged(Resource<LoginModel> loginModelResource) {
                if(loginModelResource != null){
                    switch (loginModelResource.status){
                        case LOADING:
                            showLoading();
                            break;
                        case SUCCESS:
                            hideloading();
                            subscribeValidateLogin(userName,password,loginModelResource.data.getRequest_token());
                            break;
                        case ERROR:
                            hideloading();
                            showDialogMsg("Connection Problem");
                            break;
                    }
                }
            }
        });
    }

    private void subscribeValidateLogin(String userName, String password, String request_token) {
        loginRequestBody = new LoginRequestBody(userName,password,request_token);
        loginViewModel.observeValidateLogin(loginRequestBody).observe(this, new Observer<Resource<LoginModel>>() {
            @Override
            public void onChanged(Resource<LoginModel> loginModelResource) {
                if(loginModelResource != null){
                    switch (loginModelResource.status){
                        case LOADING:
                            showLoading();
                            break;

                        case SUCCESS:
                            hideloading();
                            if(loginModelResource.data.getStatus_code() == 30){
                                showDialogMsg("Error incorrect username or password");
                            }
                            else if(loginModelResource.data.isFailure()){
                                showDialogMsg("Error retriving data");
                            }
                            else {
                                subscribeSessionId(request_token);
                            }

                            break;
                        case ERROR:
                            hideloading();
                            showDialogMsg("Connection problem");
                            break;
                    }
                }
            }
        });
    }

    private void subscribeSessionId(String request_token) {
        loginViewModel.observeSessionId(new RequestTokenBody(request_token)).observe(this, new Observer<Resource<LoginModel>>() {
            @Override
            public void onChanged(Resource<LoginModel> loginModelResource) {
                if(loginModelResource != null){
                    switch (loginModelResource.status){
                        case LOADING:
                            showLoading();
                            break;
                        case SUCCESS:
                            hideloading();
                            sessionID = loginModelResource.data.getSession_id();
                            saveLoginData(sessionID);

                            onLoginComplete();
                            break;
                        case ERROR:
                            hideloading();
                            showDialogMsg("Connection Problem");
                    }
                }
            }
        });
    }

    private void saveLoginData(String sessionID) {
        msharePreference.setLogin(sessionID);
    }
    private void onLoginComplete() {
        if(msharePreference.isLogin()){
            startActivity(MainActivity.MainActivityIntent(this));
            finish();
        }
    }


    @Override
    protected void showLoading() {
        myanProgressDialog.showDialog();
    }

    @Override
    protected void hideloading() {
        myanProgressDialog.hideDialog();
    }

    @Override
    protected void showDialogMsg(String msg) {
        new AlertDialog.Builder(this)
                .setMessage(msg)
                .setPositiveButton(
                        "Ok",
                        (dialog, whichButton) -> {
                            dialog.dismiss();
                        })
               .show();
    }
}

