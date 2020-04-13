package com.mkttestprojects.moviedbwithdagger.ui.login;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

import com.mkttestprojects.moviedbwithdagger.R;
import com.mkttestprojects.moviedbwithdagger.BaseActivity;
import com.mkttestprojects.moviedbwithdagger.custom_control.MyanButton;
import com.mkttestprojects.moviedbwithdagger.custom_control.MyanEditText;
import com.mkttestprojects.moviedbwithdagger.custom_control.MyanTextView;
import com.mkttestprojects.moviedbwithdagger.viewmodels.ViewModelProviderFactory;

import javax.inject.Inject;

import butterknife.BindView;

public class LoginActivity extends BaseActivity {

    private LoginViewModel loginViewModel;

    @BindView(R.id.et_email)
    MyanEditText etUserName;

    @BindView(R.id.et_pwd)
    MyanEditText etPassword;

    @BindView(R.id.btn_login)
    MyanButton btnLogin;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_login;
    }

    @Override
    protected void setUpContents(Bundle savedInstanceState) {
//        setupToolbar(false);
//        setupToolbarText("Login");

        loginViewModel = new ViewModelProvider(this,providerFactory).get(LoginViewModel.class);

        btnLogin.setOnClickListener(v ->
                loginViewModel.onClickLogin(etUserName.getMyanmarText().toString(),
                        etPassword.getMyanmarText().toString()));


    }

    @Override
    protected void showLoading() {

    }

    @Override
    protected void hideloading() {

    }

    @Override
    protected void showDialogMsg(String msg) {

    }
}

