package com.btd.mystyle.data.source;

import android.content.Context;

import com.btd.mystyle.data.Favorite;
import com.btd.mystyle.data.source.remote.FavoriteRemoteDataSource;

/**
 * Created by dattien on 4/24/17.
 */

public class FavoriteRepository implements FavoriteDataSource {

    private static FavoriteRepository favoriteRepository;

    private FavoriteRemoteDataSource favoriteRemoteDataSource;

    private FavoriteRepository(Context context) {
        favoriteRemoteDataSource = FavoriteRemoteDataSource.getInstance(context);
    }

    public static FavoriteRepository getInstance(Context context) {
        if (favoriteRepository == null) {
            favoriteRepository = new FavoriteRepository(context);
        }
        return favoriteRepository;
    }

    @Override
    public void createFavorite(Favorite favorite, OnCallBack onCallBack) {
        favoriteRemoteDataSource.createFavorite(favorite, onCallBack);
    }

    @Override
    public void deleteFavorite(Favorite favorite, OnCallBack onCallBack) {
        favoriteRemoteDataSource.deleteFavorite(favorite, onCallBack);
    }

    @Override
    public void updateFavorite(Favorite favorite, OnCallBack onCallBack) {
        favoriteRemoteDataSource.updateFavorite(favorite, onCallBack);
    }

    @Override
    public void getListFavorite(OnCallBackFavorite onCallBackFavorite, String userid) {
        favoriteRemoteDataSource.getListFavorite(onCallBackFavorite, userid);
    }
}
