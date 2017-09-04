package com.btd.mystyle.home.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.btd.mystyle.data.Favorite;
import com.btd.mystyle.data.Post;
import com.btd.mystyle.data.User;
import com.btd.mystyle.databinding.FragmentProfileBinding;
import com.btd.mystyle.home.profile.edit.ProfileEditActivity;
import com.btd.mystyle.home.top.TopAdapter;

import java.util.ArrayList;

/**
 * Created by dattien on 2/20/17.
 */

public class ProfileFragment extends Fragment implements ProfileContract.View {

    private FragmentProfileBinding fragmentProfileBinding;
    private ProfileContract.Presenter mPresenter;
    private boolean isStart = false;
    private boolean isStop = false;
    private LinearLayoutManager layoutManager;
    private TopAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentProfileBinding = FragmentProfileBinding.inflate(inflater, container, false);
        new ProfilePresenter(getContext(), this);
        fragmentProfileBinding.setUser(User.getInstance());
        fragmentProfileBinding.setPresenter(mPresenter);
        initView();
        return fragmentProfileBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (isStart) {
            mPresenter.start();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isStart = isVisibleToUser;
        if (isStop && isStart) {
            isStop = false;
            mPresenter.start();
        }
    }

    private void initView() {
        isStop = true;
        new ProfilePresenter(getContext(), this);
        layoutManager = new LinearLayoutManager(getContext());
        fragmentProfileBinding.rcvPosts.setLayoutManager(layoutManager);
        adapter = new TopAdapter(getContext());
        fragmentProfileBinding.rcvPosts.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e("DAT", "ProfileFragment stop");
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showListPost(ArrayList<Post> posts) {
        adapter.addItem(posts);
    }

    @Override
    public void showListFavorite(ArrayList<Favorite> favorites) {

    }

    @Override
    public void showError() {

    }

    @Override
    public void showEditProfile() {
        Intent intent = new Intent(getContext(), ProfileEditActivity.class);
        getContext().startActivity(intent);
    }

    @Override
    public void setPresenter(ProfileContract.Presenter presenter) {
        mPresenter = presenter;
    }

}