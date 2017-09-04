package com.btd.mystyle.data.source.remote;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import com.btd.mystyle.App;
import com.btd.mystyle.data.Post;
import com.btd.mystyle.data.source.PostDataSource;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dattien on 4/8/17.
 */

public class PostRemoteDataSource implements PostDataSource {
    private static PostRemoteDataSource postRemoteDataSource;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabase;
    private Context mContext;

    enum TYPE {GET, GET_ID, CREATE}

    private TYPE type;

    private PostRemoteDataSource(Context context) {
        mContext = context;
        mDatabase = App.getDatabase();
        mStorageRef = App.getStore();
    }

    public static PostRemoteDataSource getInstance(Context context) {
        if (postRemoteDataSource == null) {
            postRemoteDataSource = new PostRemoteDataSource(context);
        }
        return postRemoteDataSource;
    }

    @Override
    public void createPost(Post post, OnCallBack onCallBack) {
        type = TYPE.CREATE;
        if (post == null) {
            onCallBack.onError();
        }
        mDatabase.child("posts").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (type.equals(TYPE.CREATE)) {
                    Log.e("DAT", "creater posts");
                    DatabaseReference databaseReference = mDatabase.child("posts").push();
                    post.setId(databaseReference.getKey());
                    databaseReference.setValue(post);
                    onCallBack.onSuccessful(post);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                if (type.equals(TYPE.CREATE)) {
                    Log.e("DAT", "cancel creater posts");
                    onCallBack.onError();
                }
            }
        });
    }

    @Override
    public void getListPost(OnCallBackItem onCallBackItem) {
        type = TYPE.GET;
        mDatabase.child("posts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (type.equals(TYPE.GET)) {
                    try {
                        Map<String, Post> postMap = (HashMap<String, Post>) dataSnapshot.getValue();
                        ArrayList<Post> valuePost = new Gson().fromJson(new Gson().toJson(postMap.values()),
                                new TypeToken<ArrayList<Post>>() {
                                }.getType());
                        onCallBackItem.onSuccessful(valuePost);
                        Log.e("DAT", "getlistPost ok:");
                    } catch (Exception e) {
                        onCallBackItem.onError();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                if (type.equals(TYPE.GET)) {
                    Log.e("DAT", "getlistPost error");
                    onCallBackItem.onError();
                }
            }
        });
    }

    public void getListPost(OnCallBackItem onCallBackItem, String userid) {
        type = TYPE.GET_ID;
        mDatabase.child("posts").orderByChild("userid")
                .equalTo(userid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (type.equals(TYPE.GET_ID)) {
                    try {
                        Map<String, Post> postMap = (HashMap<String, Post>) dataSnapshot.getValue();
                        ArrayList<Post> valuePost = new Gson().fromJson(new Gson().toJson(postMap.values()),
                                new TypeToken<ArrayList<Post>>() {
                                }.getType());
                        onCallBackItem.onSuccessful(valuePost);
                        Log.e("DAT", "getlistPost profile ok:" + valuePost.size());
                    } catch (Exception e) {
                        onCallBackItem.onError();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                if (type.equals(TYPE.GET_ID)) {
                    Log.e("DAT", "getlistPost error");
                    onCallBackItem.onError();
                }
            }
        });
    }

    public void uploadImage(Bitmap bitmap, OnCallBackUploadImage onCallBackUploadImage) {
        String filename = new Date().getTime() + "";
        StorageReference riversRef = mStorageRef.child("posts/" + filename + ".jpg");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        riversRef.putBytes(data)
                .addOnSuccessListener(taskSnapshot -> {
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    onCallBackUploadImage.onSuccessful(downloadUrl.toString());
                })
                .addOnFailureListener(command -> {
                    onCallBackUploadImage.onError();
                });
    }

    @Override
    public void destroyInstance() {
        postRemoteDataSource = null;
    }
}
