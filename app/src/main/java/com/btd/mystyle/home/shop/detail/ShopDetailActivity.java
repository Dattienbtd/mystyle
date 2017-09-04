package com.btd.mystyle.home.shop.detail;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.btd.mystyle.BaseActivity;
import com.btd.mystyle.R;
import com.btd.mystyle.data.Post;
import com.btd.mystyle.data.Shop;
import com.btd.mystyle.databinding.ActivityShopDetailBinding;
import com.btd.mystyle.utils.Constants;

import java.util.ArrayList;

/**
 * Created by dattien on 3/29/17.
 */

public class ShopDetailActivity extends BaseActivity implements ShopDetailContract.View {

    private ActivityShopDetailBinding activityShopDetailBinding;
    private ShopDetailContract.Presenter mPresenter;
    private Shop mShop;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        activityShopDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_shop_detail);
        new ShopDetailPresenter(this, this);
        Intent intent = getIntent();
        if (intent != null) {
            mShop = (Shop) intent.getSerializableExtra(Constants.EXTRA_SHOP_DETAIL);
            if (mShop != null) {
                activityShopDetailBinding.setShop(mShop);
            }
        }
    }

    @Override
    public void showPostShop(ArrayList<Post> list) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void setPresenter(ShopDetailContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
