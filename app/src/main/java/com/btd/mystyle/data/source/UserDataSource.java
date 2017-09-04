package com.btd.mystyle.data.source;

import android.net.Uri;

import com.btd.mystyle.data.Item;
import com.btd.mystyle.data.Shop;
import com.btd.mystyle.data.User;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

/**
 * Created by dattien on 2/19/17.
 */

public interface UserDataSource {

    interface OnCallBack {
        void onSuccessful(User user);

        void onError();
    }

    interface OnCallBackItem {
        void onSuccessful(ArrayList<Item> items);

        void onError();
    }

    interface OnCallBackShop {
        void onSuccessful(ArrayList<Shop> shops);

        void onError();
    }

    interface OnCallBackUploadImage {
        void onSuccessful(String url);

        void onError();
    }

    void createUser(FirebaseUser user,String url, OnCallBack onCallBack);

    void getUser(String userId, OnCallBack onCallBack);

    void updateUser(User user);

    void getListItem(OnCallBackItem onCallBackItem);

    void getListShop(OnCallBackShop onCallBackShop);

    void uploadImage(FirebaseUser user, OnCallBackUploadImage onCallBackUploadImage);

    void destroy();

}
