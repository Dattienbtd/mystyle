package com.btd.mystyle.home.shop;

import android.graphics.Bitmap;

import com.btd.mystyle.BasePresenter;
import com.btd.mystyle.BaseView;
import com.btd.mystyle.data.Favorite;
import com.btd.mystyle.data.Grallerry;
import com.btd.mystyle.data.Shop;
import com.btd.mystyle.home.post.PostContract;

import java.util.ArrayList;

/**
 * Created by dattien on 3/26/17.
 */

public interface ShopContract {

    interface View extends BaseView<Presenter> {

        void showShop(ArrayList<Shop> list);

        void showFavorite(ArrayList<Favorite> list);

        void showCreateFavorite(Favorite favorite, int id);

        void showDeleteFavorite(Favorite favorite, int id);

        void showError();

        void showLoading();

    }

    interface Presenter extends BasePresenter {

        void getListFavorite();

        void createFavorite(Favorite favorite, int id);

        void deleteFavorite(Favorite favorite, int ids);
    }
}
