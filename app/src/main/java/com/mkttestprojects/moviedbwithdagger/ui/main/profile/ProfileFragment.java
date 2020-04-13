package com.mkttestprojects.moviedbwithdagger.ui.main.profile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mkttestprojects.moviedbwithdagger.BaseFragment;
import com.mkttestprojects.moviedbwithdagger.R;
import com.mkttestprojects.moviedbwithdagger.ui.firebaselogin.FirebaseLoginActivity;
import com.mkttestprojects.moviedbwithdagger.viewmodels.ViewModelProviderFactory;

import java.util.concurrent.Executor;

import javax.inject.Inject;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = "ProfileFragment";

    @BindView(R.id.item_gmail)
    CardView cvGmail;

    @BindView(R.id.item_map)
    CardView cvMap;

    @Inject
    ViewModelProviderFactory providerFactory;

    private ProfileFragmentViewModel viewModel;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_profile;
    }

    @Override
    protected void setUpContents(Bundle savedInstanceState) {

        viewModel = new ViewModelProvider(getActivity(),providerFactory).get(ProfileFragmentViewModel.class);

        init();

        cvGmail.setOnClickListener(this);
        cvMap.setOnClickListener(this);

    }

    private void init() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.item_gmail:
                startActivity(FirebaseLoginActivity.FirebaseLoginActivityIntent(getContext()));
                break;
            case R.id.item_map:

                break;
        }

    }
}
