package com.btd.mystyle.data.source.remote;

import android.content.Context;
import android.util.Log;

import com.btd.mystyle.App;
import com.btd.mystyle.data.Favorite;
import com.btd.mystyle.data.User;
import com.btd.mystyle.data.source.FavoriteDataSource;
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

public class FavoriteRemoteDataSource implements FavoriteDataSource {
    private static FavoriteRemoteDataSource favoriteRemoteDataSource;
    private Context mContext;
    private DatabaseReference mDatabase;

    enum TYPE {GET, UPDATE, DELETE, CREATE}

    private TYPE type;

    private FavoriteRemoteDataSource(Context context) {
        mContext = context;
        mDatabase = App.getDatabase();
    }

    public static FavoriteRemoteDataSource getInstance(Context context) {
        if (favoriteRemoteDataSource == null) {
            favoriteRemoteDataSource = new FavoriteRemoteDataSource(context);
        }
        return favoriteRemoteDataSource;
    }

    @Override
    public void createFavorite(Favorite favorite, OnCallBack onCallBack) {
        type = TYPE.CREATE;
        if (favorite == null) {
            onCallBack.onError();
        }
        mDatabase.child("favorite").child(User.getInstance().getId())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (type.equals(TYPE.CREATE)) {
                            Log.e("DAT", "creater favorite");
                            DatabaseReference databaseReference = mDatabase.child("favorite")
                                    .child(User.getInstance().getId()).push();
                            favorite.setId(databaseReference.getKey());
                            databaseReference.setValue(favorite);
                            onCallBack.onSuccessful(favorite);
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
    public void deleteFavorite(Favorite favorite, OnCallBack onCallBack) {
        type = TYPE.DELETE;
        mDatabase.child("favorite").child(User.getInstance().getId())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        mDatabase.child("favorite")
                                .child(User.getInstance().getId())
                                .child(favorite.getId())
                                .removeValue((databaseError, databaseReference) -> {
                                    if (databaseError == null)
                                        onCallBack.onSuccessful(favorite);

                                });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        onCallBack.onError();
                    }
                });
    }

    @Override
    public void updateFavorite(Favorite favorite, OnCallBack onCallBack) {

    }

    @Override
    public void getListFavorite(OnCallBackFavorite onCallBackFavorite, String userid) {
        type = TYPE.GET;
        mDatabase.child("favorite").child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (type.equals(TYPE.GET)) {
                    try {
                        Map<String, Favorite> favoriteMap = (HashMap<String, Favorite>) dataSnapshot.getValue();
                        ArrayList<Favorite> valueFavorite = new Gson().fromJson(new Gson().toJson(favoriteMap.values()),
                                new TypeToken<ArrayList<Favorite>>() {
                                }.getType());
                        onCallBackFavorite.onSuccessful(valueFavorite);
                    } catch (Exception e) {
                        onCallBackFavorite.onError();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                if (type.equals(TYPE.GET)) {
                    Log.e("DAT", "getlistPost error");
                    onCallBackFavorite.onError();
                }
            }
        });
    }
}
