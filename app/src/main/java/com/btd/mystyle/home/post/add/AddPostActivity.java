package com.btd.mystyle.home.post.add;

import android.app.AlertDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.android.databinding.library.baseAdapters.BR;
import com.btd.mystyle.BaseActivity;
import com.btd.mystyle.R;
import com.btd.mystyle.data.Item;
import com.btd.mystyle.data.Post;
import com.btd.mystyle.data.Shop;
import com.btd.mystyle.data.User;
import com.btd.mystyle.databinding.ActivityAddPostBinding;
import com.btd.mystyle.databinding.DialogShopBinding;
import com.btd.mystyle.home.post.add.web.SelectWebActivity;
import com.btd.mystyle.utils.Constants;

import java.util.ArrayList;

/**
 * Created by dattien on 3/4/17.
 */

public class AddPostActivity extends BaseActivity implements AddPostContract.View {
    public static Bitmap mBitmap = null;
    private ActivityAddPostBinding activityAddPostBinding;
    private AddPostContract.Presenter mPresenter;
    private AddPostViewModel mViewModel;
    private AddPostAdapter adapterItem;
    private AddPostShopAdapter adapterShop;
    private GridLayoutManager mLayout;
    private GridLayoutManager mLayoutShop;
    private DialogShopBinding dialogShopBinding;
    private ArrayList<Shop> listShop = new ArrayList<>();
    private ArrayList<Item> listItem = new ArrayList<>();
    private Post mPost;
    private Shop mShop;
    private int position = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAddPostBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_post);
        initView();
    }

    private void initView() {
        new AddPostPresenter(this, this);
        mViewModel = new AddPostViewModel(this, mPresenter);
        mPresenter.start();
        activityAddPostBinding.setViewModel(mViewModel);
        activityAddPostBinding.viewStylePost.setBackground(mBitmap);
        mPost = new Post();
        mPost.setImage(Constants.CANCEL_IMAGE);
        mPost.setUserid(User.getInstance().getId());
        mPost.setUser(User.getInstance());
        activityAddPostBinding.viewStylePost.setPost(mPost);
        mViewModel.setBitmap(mBitmap);
        mLayout = new GridLayoutManager(this, 6);
        mLayoutShop = new GridLayoutManager(this, 6);
        activityAddPostBinding.rcvItemPost.setLayoutManager(mLayout);
        activityAddPostBinding.rcvShopPost.setLayoutManager(mLayoutShop);
        adapterItem = new AddPostAdapter(this);
        adapterShop = new AddPostShopAdapter(this);
        activityAddPostBinding.rcvItemPost.setAdapter(adapterItem);
        activityAddPostBinding.rcvShopPost.setAdapter(adapterShop);
        adapterItem.setOnItemClickListener((position, v) -> {
            mViewModel.setShowShop(true);
            adapterShop.setPositionNow(-1);
            listItem.add(adapterItem.getmListItem().get(position));
            mPost.setListItem(listItem);
        });
        adapterShop.setOnItemClickListener((position, v) -> {
            mShop = adapterShop.getmListItem().get(position);
            showSelectWeb(mShop.getLink());
        });
        activityAddPostBinding.viewStylePost.setItemClickListener((position, v) -> {
            mViewModel.setShowShop(true);
            this.position = position;
            for (int i = 0; i < adapterShop.getmListItem().size(); i++) {
                if (adapterShop.getmListItem().get(i).getName().equalsIgnoreCase(listShop.get(position).getName())) {
                    adapterShop.setPositionNow(i);
                    return;
                }
            }
        });
        adapterItem.addItem(Item.getInstance());
        adapterShop.addItem(Shop.getInstance());
    }

    @Override
    public void setPresenter(AddPostContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showErrorItem() {
    }

    @Override
    public void showErrorShop() {
    }

    @Override
    public void showDialogShop(String link) {
        AlertDialog.Builder mDialog = new AlertDialog.Builder(this);
        dialogShopBinding =
                DataBindingUtil.inflate(LayoutInflater.from(mDialog.getContext()),
                        R.layout.dialog_shop, null, false);
        dialogShopBinding.webviewShop.getSettings().setJavaScriptEnabled(true);
        dialogShopBinding.webviewShop.loadUrl(link);
        mDialog.setView(dialogShopBinding.getRoot());
        mDialog.show();
    }

    @Override
    public void showSelectWeb(String url) {
        Intent intent = new Intent(this, SelectWebActivity.class);
        intent.putExtra(Constants.EXTRA_URL, url);
        startActivity(intent);
    }

    @Override
    public void showUrlSelect(String url) {
        adapterShop.setPositionNow(-1);
        mShop.setLink(url);
        if (position != -1) {
            listShop.remove(position);
            listShop.add(position, mShop);
            position = -1;
        } else {
            listShop.add(mShop);
        }
        mPost.setListShop(listShop);
        activityAddPostBinding.viewStylePost.setPost(mPost);
        mViewModel.setPost(mPost);
        mViewModel.setShowShop(false);
    }

    @Override
    public void showCreatePostSuccessfull(Post post) {
        finish();
    }

    @Override
    public void showCreatePostError() {
        Toast.makeText(this, getString(R.string.add_post_error), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        mViewModel.notifyPropertyChanged(BR._all);
    }

    @Override
    public void onBackPressed() {
        if (mViewModel != null && mViewModel.isShowShop()) {
            mViewModel.setShowShop(false);
            position = -1;
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        mBitmap = null;
        super.onDestroy();
        mPresenter.unRegisterReceiverLocal();
    }
}
