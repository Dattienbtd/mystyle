package com.btd.mystyle.home.post.edit;

import android.content.Context;
import android.databinding.BaseObservable;

/**
 * Created by dattien on 3/5/17.
 */

public class EditPostViewModel extends BaseObservable {
    private Context mContext;
    private EditPostContract.Presenter mPresenter;

    public EditPostViewModel(Context context, EditPostContract.Presenter presenter) {
        mContext = context;
        mPresenter = presenter;
    }
}
