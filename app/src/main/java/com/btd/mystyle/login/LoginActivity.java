package com.btd.mystyle.login;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.btd.mystyle.BaseActivity;
import com.btd.mystyle.R;
import com.btd.mystyle.data.User;
import com.btd.mystyle.data.source.local.AppSettings;
import com.btd.mystyle.databinding.ActivityLoginBinding;
import com.btd.mystyle.home.HomeActivity;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;

/**
 * Created by dattien on 2/13/17.
 */

public class LoginActivity extends BaseActivity implements LoginContract.View {

    private LoginContract.Presenter mPresenter;
    private LoginViewModel mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLoginBinding activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        new LoginPresenter(this, this);
        mViewModel = new LoginViewModel(this, mPresenter);
        activityLoginBinding.setViewmodel(mViewModel);
        activityLoginBinding.setPresenter(mPresenter);
        mPresenter.addAuthStateListener();
    }

    @Override
    public void showLoginSuccessfull(LoginContract.Provider provider, FirebaseUser user) {
        if (provider.equals(LoginContract.Provider.Facebook)
                || provider.equals(LoginContract.Provider.Google)) {
            mPresenter.createUser(user);
            return;
        }
        mPresenter.getUser(user.getUid());
    }

    @Override
    public void showLoginError() {

    }

    @Override
    public void showLoading() {
        mViewModel.resetView();
    }

    @Override
    public void showCreateUserSuccessfull(User user) {
        Log.e("DAT","???"+ new Gson().toJson(user));
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        AppSettings.getInstance().setUserId(user.getId());
        User.setUserSingleton(user);
        finish();
    }

    @Override
    public void showCreateUserError() {
        Log.e("DAT", "222222");
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.onActivityResult(requestCode, resultCode, data);
    }

}
