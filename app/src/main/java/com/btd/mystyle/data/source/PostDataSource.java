package com.btd.mystyle.data.source;

import android.graphics.Bitmap;

import com.btd.mystyle.data.Post;

import java.util.ArrayList;

/**
 * Created by dattien on 4/8/17.
 */

public interface PostDataSource {

    interface OnCallBack {
        void onSuccessful(Post post);

        void onError();
    }

    interface OnCallBackItem {
        void onSuccessful(ArrayList<Post> posts);

        void onError();
    }

    interface OnCallBackUploadImage {
        void onSuccessful(String url);

        void onError();
    }

    void createPost(Post post, OnCallBack onCallBack);

    void getListPost(OnCallBackItem onCallBackItem);

    void getListPost(OnCallBackItem onCallBackItem, String userid);

    void uploadImage(Bitmap bitmap,OnCallBackUploadImage onCallBackUploadImage);

    void destroyInstance();
}
