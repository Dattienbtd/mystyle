package com.btd.mystyle.data.source;

import android.content.Context;

import com.btd.mystyle.data.Favorite;
import com.btd.mystyle.data.Follow;
import com.btd.mystyle.data.source.remote.FavoriteRemoteDataSource;
import com.btd.mystyle.data.source.remote.FollowRemoteDataSource;

/**
 * Created by dattien on 4/24/17.
 */

public class FollowRepository implements FollowDataSource {

    private static FollowRepository followRepository;

    private FollowRemoteDataSource followRemoteDataSource;

    private FollowRepository(Context context) {
        followRemoteDataSource = FollowRemoteDataSource.getInstance(context);
    }

    public static FollowRepository getInstance(Context context) {
        if (followRepository == null) {
            followRepository = new FollowRepository(context);
        }
        return followRepository;
    }

    @Override
    public void createFollow(Follow follow, OnCallBack onCallBack) {
        followRemoteDataSource.createFollow(follow, onCallBack);
    }

    @Override
    public void deleteFollow() {
        followRemoteDataSource.deleteFollow();
    }

    @Override
    public void updateFollow() {
        followRemoteDataSource.updateFollow();
    }

    @Override
    public void getListFollow(OnCallBackFollow onCallBackFollow) {
        followRemoteDataSource.getListFollow(onCallBackFollow);
    }
}
