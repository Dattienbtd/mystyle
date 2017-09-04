package com.btd.mystyle.data.source;

import com.btd.mystyle.data.Favorite;
import com.btd.mystyle.data.Follow;

import java.util.ArrayList;

/**
 * Created by dattien on 4/24/17.
 */

public interface FollowDataSource {

    interface OnCallBack {
        void onSuccessful(Follow follow);

        void onError();
    }

    interface OnCallBackFollow {
        void onSuccessful(ArrayList<Follow> follow);

        void onError();
    }

    void createFollow(Follow favorite, OnCallBack onCallBack);

    void deleteFollow();

    void updateFollow();

    void getListFollow(OnCallBackFollow onCallBackFollow);
}
