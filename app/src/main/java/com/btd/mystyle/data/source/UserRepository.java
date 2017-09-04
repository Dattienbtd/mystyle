package com.btd.mystyle.data.source;

import android.content.Context;

import com.btd.mystyle.data.User;
import com.btd.mystyle.data.source.remote.UserRemoteDataSource;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by dattien on 2/19/17.
 */

public class UserRepository implements UserDataSource {

    private static UserRepository userRepository;

    private UserRemoteDataSource userRemoteDataSource;

    private UserRepository(Context context) {
        userRemoteDataSource = UserRemoteDataSource.getInstance(context);
    }

    public static UserRepository getInstance(Context context) {
        if (userRepository == null) {
            userRepository = new UserRepository(context);
        }
        return userRepository;
    }

    @Override
    public void createUser(FirebaseUser user,String url, OnCallBack onCallBack) {
        userRemoteDataSource.createUser(user,url, onCallBack);
    }

    @Override
    public void getUser(String userId, OnCallBack onCallBack) {
        userRemoteDataSource.getUser(userId, onCallBack);
    }

    @Override
    public void updateUser(User user) {
        userRemoteDataSource.updateUser(user);
    }

    @Override
    public void getListItem(OnCallBackItem onCallBackItem) {
        userRemoteDataSource.getListItem(onCallBackItem);
    }

    @Override
    public void getListShop(OnCallBackShop onCallBackShop) {
        userRemoteDataSource.getListShop(onCallBackShop);
    }

    @Override
    public void uploadImage(FirebaseUser user, OnCallBackUploadImage onCallBackUploadImage) {
        userRemoteDataSource.uploadImage(user, onCallBackUploadImage);
    }

    @Override
    public void destroy() {
        userRemoteDataSource.destroy();
        userRepository = null;
    }

}
