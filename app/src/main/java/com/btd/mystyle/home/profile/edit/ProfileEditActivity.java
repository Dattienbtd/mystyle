package com.btd.mystyle.home.profile.edit;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.btd.mystyle.BaseActivity;
import com.btd.mystyle.R;
import com.btd.mystyle.databinding.ActivityEditPostBinding;
import com.btd.mystyle.databinding.ActivityEditProfileBinding;
import com.btd.mystyle.home.HomePresenter;


/**
 * Created by dattien on 5/20/17.
 */

public class ProfileEditActivity extends BaseActivity {
    private ActivityEditProfileBinding activityEditProfileBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityEditProfileBinding = DataBindingUtil.setContentView(this, R.layout.activity_edit_profile);
    }

}
