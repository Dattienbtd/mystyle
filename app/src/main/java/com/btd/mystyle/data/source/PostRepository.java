package com.btd.mystyle.data.source;

import android.content.Context;
import android.graphics.Bitmap;

import com.btd.mystyle.data.Post;
import com.btd.mystyle.data.source.remote.PostRemoteDataSource;

/**
 * Created by dattien on 4/8/17.
 */

public class PostRepository implements PostDataSource {


    private static PostRepository postRepository;

    private PostRemoteDataSource postRemoteDataSource;

    private PostRepository(Context context) {
        postRemoteDataSource = PostRemoteDataSource.getInstance(context);
    }

    public static PostRepository getInstance(Context context) {
        if (postRepository == null) {
            postRepository = new PostRepository(context);
        }
        return postRepository;
    }

    @Override
    public void createPost(Post post, OnCallBack onCallBack) {
        postRemoteDataSource.createPost(post, onCallBack);
    }

    @Override
    public void getListPost(OnCallBackItem onCallBackItem) {
        postRemoteDataSource.getListPost(onCallBackItem);
    }

    @Override
    public void getListPost(OnCallBackItem onCallBackItem, String userid) {
        postRemoteDataSource.getListPost(onCallBackItem, userid);
    }

    @Override
    public void uploadImage(Bitmap bitmap, OnCallBackUploadImage onCallBackUploadImage) {
        postRemoteDataSource.uploadImage(bitmap, onCallBackUploadImage);
    }

    @Override
    public void destroyInstance() {
        postRemoteDataSource.destroyInstance();
        postRepository = null;
    }
}
