package com.btd.mystyle.home.profile.edit.image;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.btd.mystyle.BaseActivity;
import com.btd.mystyle.R;
import com.btd.mystyle.databinding.ActivityEditImageBinding;
import com.btd.mystyle.home.post.PostFragment;

/**
 * Created by dattien on 5/23/17.
 */

public class EditImageActivity extends BaseActivity {
    ActivityEditImageBinding activityEditImageBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityEditImageBinding = DataBindingUtil.setContentView(this, R.layout.activity_edit_image);
        showFragment();
    }

    private void showFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        PostFragment fragment = new PostFragment();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }
}
