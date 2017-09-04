package com.btd.mystyle.home.profile.edit;

import com.btd.mystyle.BasePresenter;
import com.btd.mystyle.BaseView;
import com.btd.mystyle.data.Favorite;
import com.btd.mystyle.data.Post;
import com.btd.mystyle.home.profile.ProfileContract;

import java.util.ArrayList;

/**
 * Created by dattien on 5/20/17.
 */

public class ProfileEditContract {

    interface View extends BaseView<ProfileContract.Presenter> {

        void showLoading();

        void showListPost(ArrayList<Post> posts);

        void showListFavorite(ArrayList<Favorite> favorites);

        void showError();

    }

    interface Presenter extends BasePresenter {

        void getListPost();

        void getListFavorite();
    }
}
