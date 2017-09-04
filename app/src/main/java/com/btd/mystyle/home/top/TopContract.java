package com.btd.mystyle.home.top;

import com.btd.mystyle.BasePresenter;
import com.btd.mystyle.BaseView;
import com.btd.mystyle.data.Post;

import java.util.ArrayList;

/**
 * Created by dattien on 3/29/17.
 */

public interface TopContract {
    interface View extends BaseView<Presenter> {

        void showPostStyle(ArrayList<Post> list);

        void showLoading();

        void showError();

    }

    public interface Presenter extends BasePresenter {

        void getListPostStyle();
    }
}
