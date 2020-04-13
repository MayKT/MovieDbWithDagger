package com.mkttestprojects.moviedbwithdagger.ui.firebaselogin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.SignInButton;
import com.mkttestprojects.moviedbwithdagger.BaseActivity;
import com.mkttestprojects.moviedbwithdagger.R;

import butterknife.BindView;

public class FirebaseLoginActivity extends BaseActivity {

    @BindView(R.id.fblogin_button)
    LoginButton fblogin;

    @BindView(R.id.btn_google_signin)
    SignInButton googleSignin;

    public static Intent FirebaseLoginActivityIntent(Context context){
        Intent intent = new Intent(context,FirebaseLoginActivity.class);
        return intent;
    }
    @Override
    protected int getLayoutResource() {
        return R.layout.activity_firebaselogin;
    }

    @Override
    protected void setUpContents(Bundle savedInstanceState) {

        init();

    }

    private void init() {

        googleSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SigninWithGoogle();
            }
        });
        fblogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fblogin.performClick();
            }
        });

        SigninWithFacebook();
    }

    private void SigninWithFacebook() {

    }

    private void SigninWithGoogle() {
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
