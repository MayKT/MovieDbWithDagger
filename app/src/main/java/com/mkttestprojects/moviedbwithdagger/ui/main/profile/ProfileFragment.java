package com.mkttestprojects.moviedbwithdagger.ui.main.profile;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.mkttestprojects.moviedbwithdagger.common.BaseFragment;
import com.mkttestprojects.moviedbwithdagger.R;
import com.mkttestprojects.moviedbwithdagger.models.AccountModel;
import com.mkttestprojects.moviedbwithdagger.ui.firebaselogin.FirebaseLoginActivity;
import com.mkttestprojects.moviedbwithdagger.ui.login.LoginActivity;
import com.mkttestprojects.moviedbwithdagger.ui.main.MainActivity;
import com.mkttestprojects.moviedbwithdagger.ui.main.MapsActivity;
import com.mkttestprojects.moviedbwithdagger.ui.main.Resource;
import com.mkttestprojects.moviedbwithdagger.util.SharePreferenceHelper;
import com.mkttestprojects.moviedbwithdagger.viewmodels.ViewModelProviderFactory;

import javax.inject.Inject;

import butterknife.BindView;

public class ProfileFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = "ProfileFragment";

    @BindView(R.id.item_gmail)
    CardView cvGmail;

    @BindView(R.id.item_map)
    CardView cvMap;

    @BindView(R.id.btn_logout)
    Button btnLogout;

    @BindView(R.id.tv_name)
    TextView tvName;

    @BindView(R.id.btn_singin)
    Button btnSignin;

    @BindView(R.id.layout_already_signin)
    ConstraintLayout layoutAlreadySignin;

    @Inject
    ViewModelProviderFactory providerFactory;

    private ProfileFragmentViewModel viewModel;

    private SharePreferenceHelper mSharePreference;

    private String sessionId;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_profile;
    }

    @Override
    protected void setUpContents(Bundle savedInstanceState) {

        viewModel = new ViewModelProvider(getActivity(),providerFactory).get(ProfileFragmentViewModel.class);

        mSharePreference = new SharePreferenceHelper(getContext());

        sessionId = mSharePreference.getSessionId();

        init();

        cvGmail.setOnClickListener(this);
        cvMap.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
        btnSignin.setOnClickListener(this);
    }

    private void init() {

        if(mSharePreference.isLogin()){

            btnSignin.setVisibility(View.GONE);
            layoutAlreadySignin.setVisibility(View.VISIBLE);
            subscribeAccount(sessionId);
        }
        else {
            btnSignin.setVisibility(View.VISIBLE);
            layoutAlreadySignin.setVisibility(View.GONE);
        }
    }

    private void subscribeAccount(String sessionId) {
        viewModel.observeAccountBySessionId(sessionId).observe(this, new Observer<Resource<AccountModel>>() {
            @Override
            public void onChanged(Resource<AccountModel> accountModelResource) {
                if(accountModelResource != null){
                    switch (accountModelResource.status){
                        case LOADING:

                            break;
                        case SUCCESS:
                            tvName.setText(accountModelResource.data.getUsername());
                            break;
                        case ERROR:

                            break;
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.item_gmail:
                startActivity(FirebaseLoginActivity.FirebaseLoginActivityIntent(getContext()));
                break;
            case R.id.item_map:
                startActivity(MapsActivity.MapsActivityIntent(getContext()));
                break;
            case R.id.btn_singin :
                startActivity(LoginActivity.LoginActivityIntent(getContext()));
                break;
            case R.id.btn_logout:
                mSharePreference.logoutSharePreference();
                startActivity(MainActivity.MainActivityIntent(getContext()));
                break;
        }

    }
}
