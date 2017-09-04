package com.btd.mystyle.home.shop;

import android.content.Context;

import com.btd.mystyle.data.Favorite;
import com.btd.mystyle.data.Shop;
import com.btd.mystyle.data.source.FavoriteDataSource;
import com.btd.mystyle.data.source.FavoriteRepository;

/**
 * Created by dattien on 3/26/17.
 */

public class ShopPresenter implements ShopContract.Presenter {

    private Context mContext;
    private ShopContract.View mShopView;
    private FavoriteRepository favoriteRepository;

    public ShopPresenter(Context context, ShopContract.View view) {
        mContext = context;
        mShopView = view;
        mShopView.setPresenter(this);
        mShopView.showShop(Shop.getInstance());
        favoriteRepository = FavoriteRepository.getInstance(mContext);
    }

    @Override
    public void getListFavorite() {
        mShopView.showFavorite(Favorite.getInstance());
    }

    @Override
    public void createFavorite(Favorite favorite, int id) {
        favoriteRepository.createFavorite(favorite, new FavoriteDataSource.OnCallBack() {
            @Override
            public void onSuccessful(Favorite favorite) {
                mShopView.showCreateFavorite(favorite, id);
            }

            @Override
            public void onError() {
                mShopView.showError();
            }
        });
    }

    @Override
    public void deleteFavorite(Favorite favorite, int id) {
        favoriteRepository.deleteFavorite(favorite, new FavoriteDataSource.OnCallBack() {
            @Override
            public void onSuccessful(Favorite favorite) {
                mShopView.showDeleteFavorite(favorite, id);
            }

            @Override
            public void onError() {
                mShopView.showError();
            }
        });
    }

    @Override
    public void start() {
        getListFavorite();
    }

    @Override
    public void destroy() {

    }
}
