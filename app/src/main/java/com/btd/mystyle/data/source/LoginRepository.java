package com.btd.mystyle.data.source;

import android.content.Context;
import android.content.Intent;

import com.btd.mystyle.data.source.remote.LoginRemoteDataSource;

/**
 * Created by dattien on 2/4/17.
 */

public class LoginRepository implements LoginDataSource {

    private static LoginRepository loginRepository;

    private LoginRemoteDataSource loginRemoteDataSource;

    private LoginRepository(Context context) {
        loginRemoteDataSource = LoginRemoteDataSource.getInstance(context);
    }

    public static LoginRepository getInstance(Context context) {
        if (loginRepository == null) {
            loginRepository = new LoginRepository(context);
        }
        return loginRepository;
    }

    @Override
    public void loginAnonymous(OnCallBack onCallBack) {
        loginRemoteDataSource.loginAnonymous(onCallBack);
    }

    @Override
    public void loginFacebook(OnCallBack onCallBack) {
        loginRemoteDataSource.loginFacebook(onCallBack);
    }

    @Override
    public void loginGoogle(OnCallBack onCallBack) {
        loginRemoteDataSource.loginGoogle(onCallBack);
    }

    @Override
    public void loginListener(OnCallBack onCallBack) {
        loginRemoteDataSource.loginListener(onCallBack);
    }

    @Override
    public void addAuthStateListener() {
        loginRemoteDataSource.addAuthStateListener();
    }

    @Override
    public void removeAuthStateListener() {
        loginRemoteDataSource.removeAuthStateListener();
    }

    @Override
    public void destroyInstance() {
        loginRemoteDataSource.destroyInstance();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        loginRemoteDataSource.onActivityResult(requestCode, resultCode, data);
    }
}
