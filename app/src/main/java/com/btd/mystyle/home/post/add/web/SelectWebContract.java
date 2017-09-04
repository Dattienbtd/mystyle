package com.btd.mystyle.home.post.add.web;

import com.btd.mystyle.BasePresenter;
import com.btd.mystyle.BaseView;
import com.btd.mystyle.data.Item;
import com.btd.mystyle.data.Shop;
import com.btd.mystyle.home.post.add.AddPostContract;

import java.util.ArrayList;

/**
 * Created by dattien on 4/6/17.
 */

public interface SelectWebContract {
    interface View extends BaseView<Presenter> {
        void showSearch();

        void showDone();
    }

    interface Presenter extends BasePresenter {

        void onClickDone();

        void onClickSearch();

    }
}
