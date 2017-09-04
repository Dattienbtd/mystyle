package com.btd.mystyle.home.profile;

import com.btd.mystyle.BasePresenter;
import com.btd.mystyle.BaseView;
import com.btd.mystyle.data.Favorite;
import com.btd.mystyle.data.Post;

import java.util.ArrayList;

/**
 * Created by dattien on 4/20/17.
 */

public interface ProfileContract {

    interface View extends BaseView<Presenter> {

        void showLoading();

        void showListPost(ArrayList<Post> posts);

        void showListFavorite(ArrayList<Favorite> favorites);

        void showError();

        void showEditProfile();

    }

    interface Presenter extends BasePresenter {

        void getListPost();

        void getListFavorite();

        void onClickEditProfile();
    }
}
