package com.btd.mystyle.home.post.add.web;

import android.content.Context;

/**
 * Created by dattien on 4/6/17.
 */

public class SelectWebPresenter implements SelectWebContract.Presenter {
    private Context mContext;
    private SelectWebContract.View mSelectView;

    public SelectWebPresenter(Context mContext, SelectWebContract.View mSelectView) {
        this.mContext = mContext;
        this.mSelectView = mSelectView;
        mSelectView.setPresenter(this);
    }

    @Override
    public void onClickDone() {
        mSelectView.showDone();
    }

    @Override
    public void onClickClose() {
        mSelectView.showSearch();
    }

    @Override
    public void onClickBack() {
        mSelectView.showBack();
    }

    @Override
    public void onClickNext() {
        mSelectView.showNext();
    }

    @Override
    public void onClickReset() {
        mSelectView.showReset();
    }

    @Override
    public void onClickOpenBrowser() {
        mSelectView.showBrowser();
    }

    @Override
    public void start() {

    }

    @Override
    public void destroy() {

    }
}
