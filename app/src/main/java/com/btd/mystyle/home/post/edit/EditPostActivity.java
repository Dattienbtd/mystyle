package com.btd.mystyle.home.post.edit;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;

import com.btd.mystyle.BaseActivity;
import com.btd.mystyle.R;
import com.btd.mystyle.databinding.ActivityEditPostBinding;
import com.btd.mystyle.home.post.add.AddPostActivity;
import com.btd.mystyle.utils.Constants;

/**
 * Created by dattien on 3/5/17.
 */

public class EditPostActivity extends BaseActivity implements EditPostContract.View {
    private EditPostContract.Presenter mPresenter;
    private EditPostViewModel mViewModel;
    private ActivityEditPostBinding activityEditPostBinding;
    private Bitmap mBitmap;
    private Bitmap mNewBitmap;
    private FilterImageAdapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        setListFilter();
    }

    private void initView() {
        activityEditPostBinding = DataBindingUtil.setContentView(this, R.layout.activity_edit_post);
        new EditPostPresenter(this, this);
        activityEditPostBinding.setPresenter(mPresenter);
        mViewModel = new EditPostViewModel(this, mPresenter);
        activityEditPostBinding.setViewModel(mViewModel);
        Intent intent = getIntent();
        if (intent != null) {
            byte[] byteArray = getIntent().getByteArrayExtra(Constants.EXTRA_BITMAP);
            mBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            activityEditPostBinding.effectsView.setImageSource(mBitmap);
            mNewBitmap = mBitmap;
        }
    }

    @Override
    public void setPresenter(EditPostContract.Presenter presenter) {
        mPresenter = presenter;
    }

    private void setListFilter() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 5);
        activityEditPostBinding.rcvFilter.setLayoutManager(gridLayoutManager);
        adapter = new FilterImageAdapter(this, mNewBitmap);
        activityEditPostBinding.rcvFilter.setAdapter(adapter);
        adapter.setOnItemClickListener((position, v) -> {
            activityEditPostBinding.effectsView.setCurrentEffectId(position);
            activityEditPostBinding.effectsView.setCount(100);
            activityEditPostBinding.effectsView.requestRender();
        });
    }

    @Override
    public void showAddPost() {
        AddPostActivity.mBitmap = activityEditPostBinding.effectsView.getBitmap();
        Intent intent = new Intent(this, AddPostActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        mBitmap = null;
        mNewBitmap = null;
        super.onDestroy();
    }
}
