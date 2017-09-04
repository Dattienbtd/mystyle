package com.btd.mystyle.home.post.add;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import com.btd.mystyle.data.Post;
import com.btd.mystyle.data.source.PostDataSource;
import com.btd.mystyle.data.source.PostRepository;
import com.btd.mystyle.utils.Constants;

/**
 * Created by dattien on 3/4/17.
 */

public class AddPostPresenter implements AddPostContract.Presenter {
    private Context mContext;
    private AddPostContract.View mAddPostView;
    private PostRepository postRepository;
    private boolean isLoading = false;

    public AddPostPresenter(Context context, AddPostContract.View view) {
        mContext = context;
        mAddPostView = view;
        mAddPostView.setPresenter(this);
        postRepository = PostRepository.getInstance(mContext);
    }

    @Override
    public void start() {
        registerReceiverLocal();
    }

    @Override
    public void destroy() {
    }

    @Override
    public void registerReceiverLocal() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.ACTION_SELECT_URL);
        LocalBroadcastManager.getInstance(mContext).registerReceiver(mReceiverLocal, filter);
    }

    @Override
    public void unRegisterReceiverLocal() {
        LocalBroadcastManager.getInstance(mContext).unregisterReceiver(mReceiverLocal);
    }

    @Override
    public void onCreatePost(Post post) {
        isLoading = true;
        mAddPostView.showLoading();
        if (AddPostActivity.mBitmap != null && post != null) {
            postRepository.uploadImage(AddPostActivity.mBitmap, new PostDataSource.OnCallBackUploadImage() {
                @Override
                public void onSuccessful(String url) {
                    post.setImage(url);
                    postRepository.createPost(post, new PostDataSource.OnCallBack() {
                        @Override
                        public void onSuccessful(Post post) {
                            mAddPostView.showCreatePostSuccessfull(post);
                            isLoading = false;
                            mAddPostView.showLoading();
                        }

                        @Override
                        public void onError() {
                            mAddPostView.showCreatePostError();
                            isLoading = false;
                            mAddPostView.showLoading();
                        }
                    });
                }

                @Override
                public void onError() {
                    mAddPostView.showCreatePostError();
                    isLoading = false;
                    mAddPostView.showLoading();
                }
            });
        }
    }

    @Override
    public boolean isShowLoading() {
        return isLoading;
    }

    private BroadcastReceiver mReceiverLocal = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Constants.ACTION_SELECT_URL)) {
                mAddPostView.showUrlSelect(intent.getStringExtra(Constants.EXTRA_URL));
            }
        }
    };
}
