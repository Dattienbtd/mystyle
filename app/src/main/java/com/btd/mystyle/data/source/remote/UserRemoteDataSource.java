package com.btd.mystyle.data.source.remote;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.btd.mystyle.App;
import com.btd.mystyle.data.Item;
import com.btd.mystyle.data.Shop;
import com.btd.mystyle.data.User;
import com.btd.mystyle.data.source.UserDataSource;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by dattien on 2/19/17.
 */

public class UserRemoteDataSource implements UserDataSource {

    private static UserRemoteDataSource userRemoteDataSource;
    private DatabaseReference mDatabase;
    private StorageReference mStorageRef;
    private Context mContext;

    private enum TYPE {CREATE, GET_USER, GET_SHOP, GET_ITEM, UPDATE}

    private TYPE type;

    private UserRemoteDataSource(Context context) {
        mContext = context;
        mDatabase = App.getDatabase();
        mStorageRef = App.getStore();
    }

    public static UserRemoteDataSource getInstance(Context context) {
        if (userRemoteDataSource == null) {
            userRemoteDataSource = new UserRemoteDataSource(context);
        }
        return userRemoteDataSource;
    }

    @Override
    public void createUser(final FirebaseUser user, String url, final OnCallBack onCallBack) {
        type = TYPE.CREATE;
        final User mUser = new User(user.getUid(), user.getEmail(),
                user.getDisplayName(), url, "", user.getProviderId(), "");
        mDatabase.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (type.equals(TYPE.CREATE)) {
                    if (!snapshot.child(user.getUid()).exists()) {
                        mDatabase.child("users").child(user.getUid()).setValue(mUser);
                        Log.d("DAT", "creater user");
                        onCallBack.onSuccessful(mUser);
                    } else {
                        Log.d("DAT", "user exists");
                        onCallBack.onError();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                if (type.equals(TYPE.CREATE)) {
                    Log.d("DAT", "cancel creater user");
                    onCallBack.onError();
                }
            }
        });
    }

    @Override
    public void getUser(String userId, OnCallBack onCallBack) {
        type = TYPE.GET_USER;
        mDatabase.child("users").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (type.equals(TYPE.GET_USER)) {
                    User user = dataSnapshot.getValue(User.class);
                    Log.e("DAT", "getUser ok");
                    onCallBack.onSuccessful(user);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                if (type.equals(TYPE.GET_USER)) {
                    Log.e("DAT", "Failed to read value.", error.toException());
                    onCallBack.onError();
                }
            }
        });
    }

    @Override
    public void updateUser(User user) {
        type = TYPE.UPDATE;
        mDatabase.child("users").child(user.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (type.equals(TYPE.UPDATE)) {
                    mDatabase.child(user.getId()).setValue(user);
                    Log.e("DAT", "updateUser ok");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                if (type.equals(TYPE.UPDATE)) {
                    Log.e("DAT", "Failed to update value.", error.toException());
                }
            }
        });

    }

    @Override
    public void getListItem(OnCallBackItem onCallBackItem) {
        type = TYPE.GET_ITEM;
        mDatabase.child("items").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (type.equals(TYPE.GET_ITEM)) {
                    GenericTypeIndicator<ArrayList<Item>> list
                            = new GenericTypeIndicator<ArrayList<Item>>() {
                    };
                    ArrayList<Item> listItem = dataSnapshot.getValue(list);
                    onCallBackItem.onSuccessful(listItem);
                    Log.e("DAT", "getListitem ok" + listItem.size());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                if (type.equals(TYPE.GET_ITEM)) {
                    Log.e("DAT", "getListitem error");
                    onCallBackItem.onError();
                }
            }
        });
    }

    @Override
    public void getListShop(OnCallBackShop onCallBackShop) {
        type = TYPE.GET_SHOP;
        mDatabase.child("shops").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (type.equals(TYPE.GET_SHOP)) {
                    GenericTypeIndicator<ArrayList<Shop>> shops
                            = new GenericTypeIndicator<ArrayList<Shop>>() {
                    };
                    ArrayList<Shop> listShop = dataSnapshot.getValue(shops);
                    onCallBackShop.onSuccessful(listShop);
                    Log.e("DAT", "getListShop ok");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                if (type.equals(TYPE.GET_SHOP)) {
                    Log.e("DAT", "getListShops error");
                    onCallBackShop.onError();
                }
            }
        });
    }

    @Override
    public void uploadImage(FirebaseUser user, OnCallBackUploadImage onCallBackUploadImage) {
        if (TextUtils.isEmpty(user.getPhotoUrl().toString())) {
            onCallBackUploadImage.onError();
        }
        String filename = new Date().getTime() + "";
        StorageReference riversRef = mStorageRef.child("users/" + user.getUid() + "/" + filename + ".jpg");
        riversRef.putFile(user.getPhotoUrl())
                .addOnSuccessListener(taskSnapshot -> {
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    onCallBackUploadImage.onSuccessful(downloadUrl.toString());
                })
                .addOnFailureListener(command -> {
                    onCallBackUploadImage.onError();
                });
    }

    @Override
    public void destroy() {
        mDatabase.onDisconnect();
        userRemoteDataSource = null;
    }

}
