package com.mkttestprojects.moviedbwithdagger.ui.firebaselogin;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.mkttestprojects.moviedbwithdagger.common.BaseActivity;
import com.mkttestprojects.moviedbwithdagger.R;
import com.mkttestprojects.moviedbwithdagger.custom_control.MyanProgressDialog;
import com.mkttestprojects.moviedbwithdagger.ui.firebasemain.FireBaseMainActivity;
import com.mkttestprojects.moviedbwithdagger.util.SharePreferenceHelper;

import butterknife.BindView;

public class FirebaseLoginActivity extends BaseActivity implements GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "FirebaseLoginActivity";

    @BindView(R.id.fblogin_button)
    LoginButton fblogin;

    @BindView(R.id.btn_google_signin)
    SignInButton btngoogleSignin;

    private SharePreferenceHelper sharePreferenceHelper;

    private MyanProgressDialog myCustomProgressDialog;

    private FirebaseAuth mAuth;
    private CallbackManager mCallbackManager;
    private GoogleSignInClient mGoogleSignInClient;

    private static final int RC_SIGN_IN = 9001;

    public static Intent FirebaseLoginActivityIntent(Context context) {
        Intent intent = new Intent(context, FirebaseLoginActivity.class);
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
        mAuth = FirebaseAuth.getInstance();

        myCustomProgressDialog = new MyanProgressDialog(this);

        sharePreferenceHelper = new SharePreferenceHelper(this);

        btngoogleSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSigninWithGoogle();
            }
        });
        fblogin.performClick();

        signinWithFacebook();

        logoutFacebook();

        logoutFirebase();
    }

    private void signinWithFacebook() {

        mCallbackManager = CallbackManager.Factory.create();
        fblogin.setPermissions("email", "public_profile");
        fblogin.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.e(TAG, "onCancel: facebook" );
            }

            @Override
            public void onError(FacebookException error) {
                Log.e(TAG, "onError: facebook" + error.toString());
            }
        });

    }

    private void handleFacebookAccessToken(AccessToken accessToken) {

        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUser(user);
                        }
                        else {
                            updateUser(null);
                            Log.e(TAG, "onComplete: handlefacebookaccesstoken error" + task.getException() );
                        }
                    }
                });
    }

    private void updateUser(FirebaseUser user) {
        if(user!= null){
            sharePreferenceHelper.setFBLogin(user.getDisplayName(),user.getEmail());
            startActivity(FireBaseMainActivity.FirebaseMainActivityIntent(this));
            finish();
            Toast.makeText(this, "Login success", Toast.LENGTH_SHORT).show();
        }
        else {
            Log.e(TAG, "updateUser: user null" );
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user  = mAuth.getCurrentUser();
        updateUser(user);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode,resultCode,data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // [START_EXCLUDE]
                updateUser(null);
                // [END_EXCLUDE]
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUser(user);

                            if (credential.getProvider().equals("facebook.com")) {

                                /*
                                Facebook account exist with same gmail logining in with gmail on firebase. Trying to link the accounts
                                 */
                                performSigninWithGoogle();
                            } else {

                                fblogin.performClick();
                                signinWithFacebook();

                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            updateUser(null);
                        }
                    }
                });
    }

    private void logoutFirebase() {
        FirebaseAuth.getInstance().signOut();
    }

    private void logoutFacebook() {

//        FacebookSdk.sdkInitialize(this);
        LoginManager.getInstance().logOut();

    }


    private void performSigninWithGoogle() {
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();

        startActivityForResult(signInIntent, RC_SIGN_IN);

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

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
