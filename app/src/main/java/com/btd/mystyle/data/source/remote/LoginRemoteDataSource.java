package com.btd.mystyle.data.source.remote;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.btd.mystyle.R;
import com.btd.mystyle.data.source.LoginDataSource;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

import static com.facebook.login.widget.ProfilePictureView.TAG;

/**
 * Created by dattien on 2/4/17.
 */

public class LoginRemoteDataSource implements LoginDataSource, GoogleApiClient.OnConnectionFailedListener {

    private static LoginRemoteDataSource loginRemoteDataSource;
    private static final int RC_SIGN_IN = 1202;
    private Context mContext;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private GoogleSignInOptions mGoogleSignInOptions;
    private GoogleApiClient mGoogleApiClient;
    private OnCallBack mCallBackGoogle;

    private static final String FIELDS = "fields";
    private static final String DATA_FIELDS = "picture.type(large),quotes,email,id,name,link,"
            + "age_range,first_name,last_name,gender,locale,timezone,verified,updated_time";
    private List<String> mListPermission = Arrays.asList("public_profile", "email");
    private CallbackManager mCallbackManager;
    private LoginManager mLoginManager;
    private OnCallBack mCallBackFacebook;

    private LoginRemoteDataSource(Context context) {
        mContext = context;
        mGoogleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(mContext.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                .enableAutoManage((FragmentActivity) mContext, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, mGoogleSignInOptions)
                .build();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mCallbackManager = CallbackManager.Factory.create();
    }

    public static LoginRemoteDataSource getInstance(Context context) {
        if (loginRemoteDataSource == null) {
            loginRemoteDataSource = new LoginRemoteDataSource(context);
        }
        return loginRemoteDataSource;
    }

    @Override
    public void loginAnonymous(final OnCallBack onCallBack) {
        mFirebaseAuth.signInAnonymously()
                .addOnCompleteListener((Activity) mContext, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            onCallBack.onError();
                            return;
                        }
                        onCallBack.onSuccessful(task.getResult().getUser());
                    }
                });
    }

    @Override
    public void loginFacebook(OnCallBack onCallBack) {
        mCallBackFacebook = onCallBack;
        checkAccessToken();
        mLoginManager.logInWithReadPermissions((Activity) mContext, mListPermission);
    }

    @Override
    public void loginGoogle(OnCallBack onCallBack) {
        mCallBackGoogle = onCallBack;
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        ((Activity) mContext).startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void loginListener(final OnCallBack onCallBack) {
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    onCallBack.onSuccessful(user);
                } else {
                    onCallBack.onError();
                }
            }
        };
    }

    @Override
    public void addAuthStateListener() {
        if (mAuthListener != null) {
            mFirebaseAuth.addAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void removeAuthStateListener() {
        if (mFirebaseAuth != null && mAuthListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void destroyInstance() {
        mFirebaseAuth = null;
        mAuthListener = null;
        mGoogleSignInOptions = null;
        mCallBackFacebook = null;
        loginRemoteDataSource = null;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (mCallBackGoogle != null) {
            mCallBackGoogle.onError();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                if (mCallBackGoogle != null) {
                    mCallBackGoogle.onError();
                }
            }
        }
        if (requestCode == CallbackManagerImpl.RequestCodeOffset.Login.toRequestCode()) {
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount googleSignInAccount) {
        AuthCredential credential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener((Activity) mContext, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()
                                && task.getResult() != null
                                && task.getResult().getUser() != null
                                && mCallBackGoogle != null) {
                            mCallBackGoogle.onSuccessful(task.getResult().getUser());
                        } else {
                            if (mCallBackGoogle != null) {
                                mCallBackGoogle.onError();
                            }
                        }
                    }
                });
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener((Activity) mContext, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()
                                && task.getResult() != null
                                && task.getResult().getUser() != null
                                && mCallBackFacebook != null) {
                            mCallBackFacebook.onSuccessful(task.getResult().getUser());
                        } else {
                            if (mCallBackFacebook != null) {
                                mCallBackFacebook.onError();
                            }
                        }
                    }
                });
    }

    public void checkAccessToken() {
        mLoginManager = LoginManager.getInstance();
        AccessToken token = AccessToken.getCurrentAccessToken();
        if (token != null && token.isExpired()) {
            mLoginManager.logOut();
        }
        mLoginManager.registerCallback(mCallbackManager, new LoginResultFacebookCallback());
    }

    private class LoginResultFacebookCallback
            implements com.facebook.FacebookCallback<LoginResult> {
        @Override
        public void onSuccess(final LoginResult loginResult) {
            GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject jsonObject,
                                                GraphResponse graphResponse) {
                            if (graphResponse.getError() != null) {
                                String errorMessage = graphResponse.getError().getErrorMessage();
                                Log.e("LoginRemoteDataSource", errorMessage);
                                if (mCallBackFacebook != null) {
                                    mCallBackFacebook.onError();
                                }
                                return;
                            }
                            handleFacebookAccessToken(loginResult.getAccessToken());
                        }
                    });

            Bundle parameter = new Bundle();
            parameter.putString(FIELDS, DATA_FIELDS);
            request.setParameters(parameter);
            request.executeAsync();
        }

        @Override
        public void onCancel() {
            Log.d("LoginRemoteDataSource", "Login facebook cancelled");
            if (mCallBackFacebook != null) {
                mCallBackFacebook.onError();
            }
        }

        @Override
        public void onError(FacebookException exception) {
            Log.d("LoginRemoteDataSource", "Login facebook Error: " + exception.getMessage());
            if (mCallBackFacebook != null) {
                mCallBackFacebook.onError();
            }
        }
    }
}
