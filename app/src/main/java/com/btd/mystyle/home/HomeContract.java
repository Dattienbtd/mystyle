package com.btd.mystyle.home;

import com.btd.mystyle.BasePresenter;
import com.btd.mystyle.BaseView;
import com.btd.mystyle.data.Favorite;
import com.btd.mystyle.data.Item;
import com.btd.mystyle.data.Shop;
import com.btd.mystyle.data.User;
import com.btd.mystyle.login.LoginContract;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

/**
 * Created by dattien on 2/20/17.
 */

public interface HomeContract {

    interface View extends BaseView<Presenter> {

        void showItemSuccessfull(ArrayList<Item> items);

        void showShopSuccessfull(ArrayList<Shop> shops);

        void showFavorite(ArrayList<Favorite> favorites);

        void showItemError();

        void showShopError();
        void showFavoriteError();

        void showLoading();
    }

    interface Presenter extends BasePresenter {

        boolean isShowLoading();

        void getItem();

        void getShop();

        void getFavorite();
    }
}
