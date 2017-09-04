package com.btd.mystyle.login;

import android.content.Intent;

import com.btd.mystyle.BasePresenter;
import com.btd.mystyle.BaseView;
import com.btd.mystyle.data.User;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by dattien on 2/13/17.
 */

public interface LoginContract {

    enum Provider {Facebook, Anonymous, Google, Listener}

    interface View extends BaseView<Presenter> {

        void showLoginSuccessfull(Provider provider, FirebaseUser user);

        void showLoginError();

        void showLoading();

        void showCreateUserSuccessfull(User user);

        void showCreateUserError();

    }

    interface Presenter extends BasePresenter {

        boolean isShowLoading();

        void loginAnonymous();

        void loginFacebook();

        void loginGoogle();

        void loginListener();

        void addAuthStateListener();

        void onClickLoginAnonymous();

        void onClickLoginFacebook();

        void onClickLoginGoogle();

        void onActivityResult(int requestCode, int resultCode, Intent data);

        void createUser(FirebaseUser user);

        void getUser(String userid);

    }
}
