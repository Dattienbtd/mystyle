package com.btd.mystyle.splash;

import com.btd.mystyle.BasePresenter;
import com.btd.mystyle.BaseView;

/**
 * Created by dattien on 2/4/17.
 */

public interface SplashContract {

    interface View extends BaseView<Presenter> {

        void showMain();

    }

    interface Presenter extends BasePresenter {

        void openMain();

    }
}
