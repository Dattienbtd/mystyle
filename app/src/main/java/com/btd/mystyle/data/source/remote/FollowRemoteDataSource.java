package com.btd.mystyle.data.source.remote;

import android.content.Context;
import android.util.Log;

import com.btd.mystyle.App;
import com.btd.mystyle.data.Favorite;
import com.btd.mystyle.data.Follow;
import com.btd.mystyle.data.User;
import com.btd.mystyle.data.source.FollowDataSource;
import com.btd.mystyle.data.source.local.AppSettings;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dattien on 4/24/17.
 */

public class FollowRemoteDataSource implements FollowDataSource {
    private static FollowRemoteDataSource favoriteRemoteDataSource;
    private Context mContext;
    private DatabaseReference mDatabase;

    private enum TYPE {CREATE, GET}

    private TYPE type;

    private FollowRemoteDataSource(Context context) {
        mContext = context;
        mDatabase = App.getDatabase();
    }

    public static FollowRemoteDataSource getInstance(Context context) {
        if (favoriteRemoteDataSource == null) {
            favoriteRemoteDataSource = new FollowRemoteDataSource(context);
        }
        return favoriteRemoteDataSource;
    }

    @Override
    public void createFollow(Follow follow, OnCallBack onCallBack) {
        type = TYPE.CREATE;
        mDatabase.child("follow").child(User.getInstance().getId())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (type.equals(TYPE.CREATE)) {
                            Log.e("DAT", "creater follow");
                            mDatabase.child("follow").child(User.getInstance().getId()).push().setValue(follow);
                            onCallBack.onSuccessful(follow);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        if (type.equals(TYPE.CREATE)) {
                            Log.e("DAT", "cancel creater favorite");
                            onCallBack.onError();
                        }
                    }
                });
    }

    @Override
    public void deleteFollow() {

    }

    @Override
    public void updateFollow() {

    }

    @Override
    public void getListFollow(OnCallBackFollow onCallBackFollow) {
        type = TYPE.GET;
        mDatabase.child("follow").child(User.getInstance().getId())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (type.equals(TYPE.GET)) {
                            try {
                                Map<String, Follow> favoriteMap = (HashMap<String, Follow>) dataSnapshot.getValue();
                                ArrayList<Follow> valueFollow = new Gson().fromJson(new Gson().toJson(favoriteMap.values()),
                                        new TypeToken<ArrayList<Follow>>() {
                                        }.getType());
                                onCallBackFollow.onSuccessful(valueFollow);
                                Log.e("DAT", "getlistFollow ok:");
                            } catch (Exception e) {
                                onCallBackFollow.onError();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        if (type.equals(TYPE.GET)) {
                            Log.e("DAT", "getlistFollow error");
                            onCallBackFollow.onError();
                        }
                    }
                });
    }
}
