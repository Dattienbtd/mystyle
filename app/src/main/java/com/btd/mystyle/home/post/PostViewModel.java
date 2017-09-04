package com.btd.mystyle.home.post;

import android.content.Context;
import android.databinding.BaseObservable;

/**
 * Created by dattien on 2/26/17.
 */

public class PostViewModel extends BaseObservable {
    private Context mContext;
    private PostContract.Presenter mPresenter;

    public PostViewModel(Context context, PostContract.Presenter presenter) {
        mContext = context;
        mPresenter = presenter;
    }
}
