package com.btd.mystyle.home.post.add;

import com.btd.mystyle.BasePresenter;
import com.btd.mystyle.BaseView;
import com.btd.mystyle.data.Post;

/**
 * Created by dattien on 3/4/17.
 */

public interface AddPostContract {

    interface View extends BaseView<Presenter> {

        void showErrorItem();

        void showErrorShop();

        void showSelectWeb(String url);

        void showUrlSelect(String url);

        void showCreatePostSuccessfull(Post post);

        void showCreatePostError();

        void showLoading();

        void showDialogShop(String link);
    }

    public interface Presenter extends BasePresenter {

        void registerReceiverLocal();

        void unRegisterReceiverLocal();

        void onCreatePost(Post post);

        boolean isShowLoading();

    }

}
