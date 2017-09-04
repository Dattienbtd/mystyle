package com.btd.mystyle.home;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.util.Log;

import com.btd.mystyle.BaseActivity;
import com.btd.mystyle.R;
import com.btd.mystyle.data.Favorite;
import com.btd.mystyle.data.Item;
import com.btd.mystyle.data.Shop;
import com.btd.mystyle.databinding.ActivityHomeBinding;
import com.btd.mystyle.home.message.MessageFragment;
import com.btd.mystyle.home.post.PostFragment;
import com.btd.mystyle.home.profile.ProfileFragment;
import com.btd.mystyle.home.shop.ShopFragment;
import com.btd.mystyle.home.top.TopFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.appindexing.FirebaseAppIndex;
import com.google.firebase.appindexing.Indexable;

import java.util.ArrayList;

/**
 * Created by dattien on 2/20/17.
 */

public class HomeActivity extends BaseActivity implements HomeContract.View {

    private ActivityHomeBinding activityHomeBinding;
    private HomeAdapter mAdapter;
    private HomeContract.Presenter mPresenter;

    @Override
    public void showItemSuccessfull(ArrayList<Item> items) {
        Item.setItemSingleton(items);
        initView();
    }

    @Override
    public void showShopSuccessfull(ArrayList<Shop> shops) {
        Shop.setShopSingleton(shops);
    }

    @Override
    public void showFavorite(ArrayList<Favorite> favorites) {
        Favorite.setFavoriteSingleton(favorites);
    }

    @Override
    public void showItemError() {

    }

    @Override
    public void showShopError() {

    }

    @Override
    public void showFavoriteError() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {
        mPresenter = presenter;
    }

    private enum TAB {
        POST("Post"), SHOP("Shop"), TOP("Top"), PROFILE("Profile"), MESSAGE("Message");

        private String name;

        TAB(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        new HomePresenter(this, this);

        //indexRecipe();
    }

    private void indexRecipe() {
        Indexable recipeToIndex = new Indexable.Builder()
                .setName("test")
                .setUrl("http://dantri.com.vn/xa-hoi/set-danh-chet-6-con-trau-nguoi-chan-bat-tinh-20170507145553774.htm")
                .build();
        Task<Void> task = FirebaseAppIndex.getInstance().update(recipeToIndex);
        task.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.e("DAT", "App Indexing API: Successfully added " + " to " +
                        "index");
            }
        });

        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.e("DAT", "App Indexing API: Failed to add " + " to index. " +
                        "" + exception.getMessage());
            }
        });
    }

    private void initView() {
        addTab();
        activityHomeBinding.viewPager.setAdapter(mAdapter);
        activityHomeBinding.tabLayout.setupWithViewPager(activityHomeBinding.viewPager);
        activityHomeBinding.viewPager.addOnPageChangeListener(
                new TabLayout.TabLayoutOnPageChangeListener(activityHomeBinding.tabLayout));
        setupTabIcons();
    }

    private void setupTabIcons() {
        activityHomeBinding.tabLayout.getTabAt(0).setIcon(R.drawable.ic_style);
        activityHomeBinding.tabLayout.getTabAt(1).setIcon(R.drawable.ic_shop);
        activityHomeBinding.tabLayout.getTabAt(2).setIcon(R.drawable.ic_add);
        activityHomeBinding.tabLayout.getTabAt(4).setIcon(R.drawable.ic_profile);
        activityHomeBinding.tabLayout.getTabAt(3).setIcon(R.drawable.ic_message);

    }

    private void addTab() {
        mAdapter = new HomeAdapter(getSupportFragmentManager());
        mAdapter.addFragment(new TopFragment(), TAB.TOP.toString());
        mAdapter.addFragment(new ShopFragment(), TAB.SHOP.toString());
        mAdapter.addFragment(new PostFragment(), TAB.POST.toString());
        mAdapter.addFragment(new MessageFragment(), TAB.MESSAGE.toString());
        mAdapter.addFragment(new ProfileFragment(), TAB.PROFILE.toString());
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
