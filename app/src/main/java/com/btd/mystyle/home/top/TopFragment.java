package com.btd.mystyle.home.top;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.btd.mystyle.data.Post;
import com.btd.mystyle.databinding.FragmentTopBinding;

import java.util.ArrayList;

/**
 * Created by dattien on 2/20/17.
 */

public class TopFragment extends Fragment implements TopContract.View {

    private FragmentTopBinding fragmentTopBinding;
    private TopContract.Presenter mPresenter;
    private boolean isStart = false;
    private LinearLayoutManager layoutManager;
    private TopAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentTopBinding = FragmentTopBinding.inflate(inflater, container, false);
        initView();
        return fragmentTopBinding.getRoot();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isStart && isVisibleToUser) {
            mPresenter.start();
        }
    }

    private void initView() {
        isStart = true;
        new TopPresenter(getContext(), this);
        layoutManager = new LinearLayoutManager(getContext());
        fragmentTopBinding.rcvStyle.setLayoutManager(layoutManager);
        adapter = new TopAdapter(getContext());
        fragmentTopBinding.rcvStyle.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void showPostStyle(ArrayList<Post> list) {
        adapter.addItem(list);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showError() {

    }

    @Override
    public void setPresenter(TopContract.Presenter presenter) {
        mPresenter = presenter;
    }
}