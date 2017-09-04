package com.btd.mystyle.home.post.add.web;

import android.content.Context;
import android.databinding.BaseObservable;

import com.android.databinding.library.baseAdapters.BR;

/**
 * Created by dattien on 4/6/17.
 */

public class SelectWebViewModel extends BaseObservable {
    private Context mContext;
    private SelectWebContract.Presenter mPresenter;
    private boolean isShowLoading;

    public SelectWebViewModel(Context mContext, SelectWebContract.Presenter mPresenter) {
        this.mContext = mContext;
        this.mPresenter = mPresenter;
    }

    public boolean isShowLoading() {
        return isShowLoading;
    }

    public void setLoading(boolean isLoading) {
        isShowLoading = isLoading;
        notifyPropertyChanged(BR._all);
    }
}
