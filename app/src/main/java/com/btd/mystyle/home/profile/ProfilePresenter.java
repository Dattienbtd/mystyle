package com.btd.mystyle.home.profile;

import android.content.Context;
import android.util.Log;

import com.btd.mystyle.data.Favorite;
import com.btd.mystyle.data.Post;
import com.btd.mystyle.data.User;
import com.btd.mystyle.data.source.FavoriteDataSource;
import com.btd.mystyle.data.source.FavoriteRepository;
import com.btd.mystyle.data.source.PostDataSource;
import com.btd.mystyle.data.source.PostRepository;
import com.btd.mystyle.data.source.local.AppSettings;

import java.util.ArrayList;

/**
 * Created by dattien on 4/20/17.
 */

public class ProfilePresenter implements ProfileContract.Presenter {

    private Context mContext;
    private ProfileContract.View mViewProfile;
    private PostRepository postRepository;
    private FavoriteRepository favoriteRepository;

    public ProfilePresenter(Context context, ProfileContract.View view) {
        mContext = context;
        mViewProfile = view;
        mViewProfile.setPresenter(this);
        postRepository = PostRepository.getInstance(mContext);
        favoriteRepository = FavoriteRepository.getInstance(mContext);
    }

    @Override
    public void start() {
        getListPost();
    }

    @Override
    public void destroy() {

    }

    @Override
    public void getListPost() {
        postRepository.getListPost(new PostDataSource.OnCallBackItem() {
            @Override
            public void onSuccessful(ArrayList<Post> posts) {
                mViewProfile.showListPost(posts);
            }

            @Override
            public void onError() {
                mViewProfile.showError();
            }
        }, User.getInstance().getId());
    }

    @Override
    public void getListFavorite() {
        favoriteRepository.getListFavorite(new FavoriteDataSource.OnCallBackFavorite() {
            @Override
            public void onSuccessful(ArrayList<Favorite> favorite) {
                mViewProfile.showListFavorite(favorite);
            }

            @Override
            public void onError() {
                mViewProfile.showError();
            }
        }, User.getInstance().getId());
    }

    @Override
    public void onClickEditProfile() {
        Log.e("DAT","4444444444");
        mViewProfile.showEditProfile();
    }
}
