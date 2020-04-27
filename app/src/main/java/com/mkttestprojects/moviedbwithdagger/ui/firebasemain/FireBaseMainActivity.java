package com.mkttestprojects.moviedbwithdagger.ui.firebasemain;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mkttestprojects.moviedbwithdagger.R;
import com.mkttestprojects.moviedbwithdagger.common.BaseActivity;
import com.mkttestprojects.moviedbwithdagger.util.SharePreferenceHelper;

import butterknife.BindView;

public class FireBaseMainActivity extends BaseActivity {

    @BindView(R.id.tv_user_name)
    TextView tvUserName;

    @BindView(R.id.tv_user_email)
    TextView tvUserEmail;

//    @BindView(R.id.btn_logout)
//    Button btnLogout;

    private SharePreferenceHelper mSharePreference;

    public static Intent FirebaseMainActivityIntent(Context context){
        return new Intent(context,FireBaseMainActivity.class);
    }
    @Override
    protected int getLayoutResource() {
        return R.layout.activity_firebase_main;
    }

    @Override
    protected void setUpContents(Bundle savedInstanceState) {

        mSharePreference = new SharePreferenceHelper( this);

        tvUserName.setText(mSharePreference.getUserFbName());
        tvUserEmail.setText(mSharePreference.getUserEmail());
//        btnLogout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mSharePreference.logoutSharePreference();
//                finish();
//            }
//        });
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
