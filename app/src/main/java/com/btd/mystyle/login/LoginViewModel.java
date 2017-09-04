package com.btd.mystyle.login;

import android.content.Context;
import android.databinding.BaseObservable;

import com.android.databinding.library.baseAdapters.BR;

/**
 * Created by dattien on 2/18/17.
 */

public class LoginViewModel extends BaseObservable {

    private Context mContext;
    private LoginContract.Presenter mPresenter;

    public LoginViewModel(Context context, LoginContract.Presenter presenter) {
        mContext = context;
        mPresenter = presenter;
    }

    public boolean isShowLoading() {
        if (mPresenter == null)
            return false;
        return mPresenter.isShowLoading();
    }

    public void resetView() {
        notifyPropertyChanged(BR._all);
    }
}
