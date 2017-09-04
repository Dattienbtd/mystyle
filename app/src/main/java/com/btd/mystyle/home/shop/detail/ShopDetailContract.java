package com.btd.mystyle.home.shop.detail;

import com.btd.mystyle.BasePresenter;
import com.btd.mystyle.BaseView;
import com.btd.mystyle.data.Post;

import java.util.ArrayList;

/**
 * Created by dattien on 3/29/17.
 */

public interface ShopDetailContract {

    interface View extends BaseView<Presenter> {

        void showPostShop(ArrayList<Post> list);

        void showLoading();

    }

    public interface Presenter extends BasePresenter {

        void getListPostShop();
    }
}
