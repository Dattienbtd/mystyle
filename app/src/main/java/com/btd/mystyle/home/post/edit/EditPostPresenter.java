package com.btd.mystyle.home.post.edit;

import android.content.Context;

/**
 * Created by dattien on 3/5/17.
 */

public class EditPostPresenter implements EditPostContract.Presenter {
    private Context mContext;
    private EditPostContract.View mEditView;

    public EditPostPresenter(Context context, EditPostContract.View view) {
        mContext = context;
        mEditView = view;
        mEditView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void destroy() {
        mEditView = null;
    }

    @Override
    public void onClickDone() {
        mEditView.showAddPost();

    }

}
