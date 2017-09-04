package com.btd.mystyle.home.shop.detail;

import android.content.Context;

/**
 * Created by dattien on 3/29/17.
 */

public class ShopDetailPresenter implements ShopDetailContract.Presenter {

    private Context mContext;
    private ShopDetailContract.View mShopDetailView;

    public ShopDetailPresenter(Context context, ShopDetailContract.View view) {
        mContext = context;
        mShopDetailView = view;
        mShopDetailView.setPresenter(this);
    }


    @Override
    public void getListPostShop() {

    }

    @Override
    public void start() {

    }

    @Override
    public void destroy() {

    }
}
