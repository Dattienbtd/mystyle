package com.btd.mystyle.home;

import android.content.Context;

import com.btd.mystyle.data.Favorite;
import com.btd.mystyle.data.Item;
import com.btd.mystyle.data.Shop;
import com.btd.mystyle.data.User;
import com.btd.mystyle.data.source.FavoriteDataSource;
import com.btd.mystyle.data.source.FavoriteRepository;
import com.btd.mystyle.data.source.UserDataSource;
import com.btd.mystyle.data.source.UserRepository;

import java.util.ArrayList;

/**
 * Created by dattien on 2/20/17.
 */

public class HomePresenter implements HomeContract.Presenter {
    private Context mContext;
    private HomeContract.View mHomeView;
    private UserRepository userRepository;
    private FavoriteRepository favoriteRepository;


    public HomePresenter(Context context, HomeContract.View view) {
        mContext = context;
        mHomeView = view;
        userRepository = UserRepository.getInstance(mContext);
        favoriteRepository = FavoriteRepository.getInstance(mContext);
        mHomeView.setPresenter(this);
        start();
    }

    @Override
    public boolean isShowLoading() {
        return false;
    }

    @Override
    public void getItem() {
        userRepository.getListItem(new UserDataSource.OnCallBackItem() {
            @Override
            public void onSuccessful(ArrayList<Item> items) {
                mHomeView.showItemSuccessfull(items);
                getFavorite();
            }

            @Override
            public void onError() {
                mHomeView.showItemError();
                getFavorite();
            }
        });
    }

    @Override
    public void getShop() {
        userRepository.getListShop(new UserDataSource.OnCallBackShop() {
            @Override
            public void onSuccessful(ArrayList<Shop> shops) {
                getItem();
                mHomeView.showShopSuccessfull(shops);
            }

            @Override
            public void onError() {
                getItem();
                mHomeView.showShopError();
            }
        });
    }

    @Override
    public void getFavorite() {
        favoriteRepository.getListFavorite(new FavoriteDataSource.OnCallBackFavorite() {
            @Override
            public void onSuccessful(ArrayList<Favorite> favorite) {
                mHomeView.showFavorite(favorite);
            }

            @Override
            public void onError() {
                mHomeView.showFavoriteError();
            }
        }, User.getInstance().getId());
    }

    @Override
    public void start() {
        getShop();
    }

    @Override
    public void destroy() {
        userRepository.destroy();
    }
}
