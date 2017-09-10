package com.btd.mystyle.home.post.edit;

import com.btd.mystyle.BasePresenter;
import com.btd.mystyle.BaseView;

/**
 * Created by dattien on 3/5/17.
 */

public interface EditPostContract {

    interface View extends BaseView<Presenter> {

        void showAddPost();

    }

    interface Presenter extends BasePresenter {

        void onClickDone();

    }
}
