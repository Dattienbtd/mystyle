package com.btd.mystyle.data.source;

import com.btd.mystyle.data.Favorite;

import java.util.ArrayList;

/**
 * Created by dattien on 4/24/17.
 */

public interface FavoriteDataSource {

    interface OnCallBack {
        void onSuccessful(Favorite favorite);

        void onError();
    }

    interface OnCallBackFavorite {
        void onSuccessful(ArrayList<Favorite> favorite);

        void onError();
    }

    void createFavorite(Favorite favorite, OnCallBack onCallBack);

    void deleteFavorite(Favorite favorite, OnCallBack onCallBack);

    void updateFavorite(Favorite favorite, OnCallBack onCallBack);

    void getListFavorite(OnCallBackFavorite onCallBackFavorite, String userid);
}
