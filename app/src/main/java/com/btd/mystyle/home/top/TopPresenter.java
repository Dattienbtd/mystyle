package com.btd.mystyle.home.top;

import android.content.Context;

import com.btd.mystyle.data.Post;
import com.btd.mystyle.data.source.PostDataSource;
import com.btd.mystyle.data.source.PostRepository;

import java.util.ArrayList;

/**
 * Created by dattien on 3/29/17.
 */

public class TopPresenter implements TopContract.Presenter {

    private Context mContext;
    private TopContract.View mTopView;
    private PostRepository postRepository;

    public TopPresenter(Context context, TopContract.View view) {
        mContext = context;
        mTopView = view;
        mTopView.setPresenter(this);
        postRepository = PostRepository.getInstance(mContext);
        start();
    }

    @Override
    public void getListPostStyle() {
        postRepository.getListPost(new PostDataSource.OnCallBackItem() {
            @Override
            public void onSuccessful(ArrayList<Post> posts) {
                mTopView.showPostStyle(posts);
            }

            @Override
            public void onError() {
                mTopView.showError();
            }
        });

    }

    @Override
    public void start() {
        getListPostStyle();
    }

    @Override
    public void destroy() {

    }
}
