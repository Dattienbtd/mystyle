package com.btd.mystyle.data.source;

import android.content.Intent;

import com.google.firebase.auth.FirebaseUser;

/**
 * Created by dattien on 2/4/17.
 */

public interface LoginDataSource {

    interface OnCallBack {
        void onSuccessful(FirebaseUser user);

        void onError();
    }

    void loginAnonymous(OnCallBack onCallBack);

    void loginFacebook(OnCallBack onCallBack);

    void loginGoogle(OnCallBack onCallBack);

    void loginListener(OnCallBack onCallBack);

    void addAuthStateListener();

    void removeAuthStateListener();

    void destroyInstance();

    void onActivityResult(int requestCode, int resultCode, Intent data);
}
