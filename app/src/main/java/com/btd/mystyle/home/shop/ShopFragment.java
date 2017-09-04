package com.btd.mystyle.home.shop;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.btd.mystyle.data.Favorite;
import com.btd.mystyle.data.Shop;
import com.btd.mystyle.data.User;
import com.btd.mystyle.data.source.local.AppSettings;
import com.btd.mystyle.databinding.FragmentShopBinding;
import com.btd.mystyle.home.shop.detail.ShopDetailActivity;
import com.btd.mystyle.utils.Constants;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by dattien on 3/26/17.
 */

public class ShopFragment extends Fragment implements ShopContract.View {

    FragmentShopBinding fragmentShopBinding;
    private ShopContract.Presenter mPresenter;
    private LinearLayoutManager mLayout;
    private ShopAdapter adapter;
    private ArrayList<Favorite> listFavorite = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentShopBinding = FragmentShopBinding.inflate(inflater, container, false);
        initView();
        return fragmentShopBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.start();
    }

    private void initView() {
        new ShopPresenter(getContext(), this);
        mLayout = new LinearLayoutManager(getContext());
        fragmentShopBinding.rcvShop.setLayoutManager(mLayout);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            Log.d("DAT", "onStart ProfileFragment");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void showShop(ArrayList<Shop> list) {
        adapter = new ShopAdapter(getContext(), list);
        adapter.setListFavorite(listFavorite, -1);
        fragmentShopBinding.rcvShop.setAdapter(adapter);
        adapter.setOnItemClickListener((position, v) -> {
            Intent intent = new Intent(getContext(), ShopDetailActivity.class);
            intent.putExtra(Constants.EXTRA_SHOP_DETAIL, list.get(position));
            startActivity(intent);
        });
        adapter.setOnItemClickListenerFavorite((position, v) -> {

            if (adapter.isFavorite(adapter.getmListShop().get(position))) {
                mPresenter.deleteFavorite(listFavorite.get(adapter.getPosition()), position);
            } else {
                Favorite favorite = new Favorite();
                favorite.setShopid(adapter.getmListShop().get(position).getId());
                favorite.setUserid(User.getInstance().getId());
                favorite.setLevel(1);
                mPresenter.createFavorite(favorite, position);
            }
        });
    }

    @Override
    public void showFavorite(ArrayList<Favorite> list) {
        listFavorite = list;
    }

    @Override
    public void showCreateFavorite(Favorite favorite, int id) {
        listFavorite.add(favorite);
        adapter.setListFavorite(listFavorite, id);
    }

    @Override
    public void showDeleteFavorite(Favorite favorite, int id) {
        listFavorite.remove(favorite);
        adapter.setListFavorite(listFavorite, id);
    }

    @Override
    public void showError() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void setPresenter(ShopContract.Presenter presenter) {
        mPresenter = presenter;
    }
}