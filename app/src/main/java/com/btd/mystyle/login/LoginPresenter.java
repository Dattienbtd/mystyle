package com.btd.mystyle.login;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.btd.mystyle.data.User;
import com.btd.mystyle.data.source.LoginDataSource;
import com.btd.mystyle.data.source.LoginRepository;
import com.btd.mystyle.data.source.UserDataSource;
import com.btd.mystyle.data.source.UserRepository;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by dattien on 2/13/17.
 */

public class LoginPresenter implements LoginContract.Presenter {


    private Context mContext;
    private LoginContract.View mLoginView;
    private LoginRepository mLoginRepository;
    private boolean isLoading = false;
    private UserRepository mUserRepository;

    public LoginPresenter(Context context, LoginContract.View view) {
        mContext = context;
        mLoginRepository = LoginRepository.getInstance(mContext);
        mUserRepository = UserRepository.getInstance(mContext);
        mLoginView = view;
        mLoginView.setPresenter(this);

        start();
    }

    @Override
    public boolean isShowLoading() {
        return isLoading;
    }

    public void setLoading(boolean isLoading) {
        this.isLoading = isLoading;
        mLoginView.showLoading();
    }

    @Override
    public void loginAnonymous() {
        setLoading(true);
        mLoginRepository.loginAnonymous(new LoginDataSource.OnCallBack() {
            @Override
            public void onSuccessful(FirebaseUser user) {
                setLoading(false);
                mLoginView.showLoginSuccessfull(LoginContract.Provider.Anonymous, user);
            }

            @Override
            public void onError() {
                setLoading(false);
                mLoginView.showLoginError();
            }
        });
    }

    @Override
    public void loginFacebook() {
        setLoading(true);
        mLoginRepository.loginFacebook(new LoginDataSource.OnCallBack() {
            @Override
            public void onSuccessful(FirebaseUser user) {
                mLoginView.showLoginSuccessfull(LoginContract.Provider.Facebook, user);
            }

            @Override
            public void onError() {
                Log.e("DAT",">>>>2>>>>");
                setLoading(false);
                mLoginView.showLoginError();
            }
        });
    }

    @Override
    public void loginGoogle() {
        setLoading(true);
        mLoginRepository.loginGoogle(new LoginDataSource.OnCallBack() {
            @Override
            public void onSuccessful(FirebaseUser user) {
                mLoginView.showLoginSuccessfull(LoginContract.Provider.Google, user);
            }

            @Override
            public void onError() {
                setLoading(false);
                mLoginView.showLoginError();
            }
        });
    }

    @Override
    public void loginListener() {
        mLoginRepository.loginListener(new LoginDataSource.OnCallBack() {
            @Override
            public void onSuccessful(FirebaseUser user) {
                mLoginView.showLoginSuccessfull(LoginContract.Provider.Listener, user);
            }

            @Override
            public void onError() {
                mLoginView.showLoginError();
            }
        });
    }

    @Override
    public void addAuthStateListener() {
        mLoginRepository.addAuthStateListener();
    }


    @Override
    public void onClickLoginAnonymous() {
        loginAnonymous();
    }

    @Override
    public void onClickLoginFacebook() {
        loginFacebook();
    }

    @Override
    public void onClickLoginGoogle() {
        loginGoogle();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mLoginRepository.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void createUser(FirebaseUser user) {
        requestCreateUser(user, "");
//        mUserRepository.uploadImage(user, new UserDataSource.OnCallBackUploadImage() {
//            @Override
//            public void onSuccessful(String url) {
//                requestCreateUser(user, url);
//            }
//
//            @Override
//            public void onError() {
//                requestCreateUser(user, "");
//            }
//        });

    }

    public void requestCreateUser(FirebaseUser user, String url) {
        mUserRepository.createUser(user, url, new UserDataSource.OnCallBack() {
            @Override
            public void onSuccessful(User user) {
                setLoading(false);
                mLoginView.showCreateUserSuccessfull(user);
            }

            @Override
            public void onError() {
                setLoading(false);
                mLoginView.showCreateUserError();
            }
        });
    }

    @Override
    public void getUser(String userid) {
        mUserRepository.getUser(userid, new UserDataSource.OnCallBack() {
            @Override
            public void onSuccessful(User user) {
                mLoginView.showCreateUserSuccessfull(user);
            }

            @Override
            public void onError() {
                mLoginView.showLoginError();
            }
        });
    }

    @Override
    public void start() {
        loginListener();
    }

    @Override
    public void destroy() {
        mLoginRepository.removeAuthStateListener();
        mLoginRepository.destroyInstance();
    }
}
