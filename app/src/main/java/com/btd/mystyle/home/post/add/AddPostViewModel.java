package com.btd.mystyle.home.post.add;

import android.content.Context;
import android.databinding.BaseObservable;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Toast;

import com.android.databinding.library.baseAdapters.BR;
import com.btd.mystyle.data.Post;
import com.btd.mystyle.utils.AppUtils;

/**
 * Created by dattien on 3/4/17.
 */

public class AddPostViewModel extends BaseObservable {
    private Context mContext;
    private AddPostContract.Presenter mPresenter;
    private Bitmap bitmap;
    private Post post;
    private String comment;

    private boolean isShowShop = false;

    public AddPostViewModel(Context context, AddPostContract.Presenter presenter) {
        mContext = context;
        mPresenter = presenter;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
        notifyPropertyChanged(BR._all);
    }

    public boolean isShowShop() {
        return isShowShop;
    }

    public void setShowShop(boolean showShop) {
        isShowShop = showShop;
        notifyPropertyChanged(BR._all);
    }

    public void onClickPost() {
        if (!TextUtils.isEmpty(comment) && post != null) {
            post.setDescription(comment);
            mPresenter.onCreatePost(getPost());
        } else {
            Toast.makeText(mContext, "You input text", Toast.LENGTH_SHORT).show();
        }
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
        notifyPropertyChanged(BR._all);
    }

    public boolean isShowLoading() {
        if (mPresenter == null)
            return false;
        return mPresenter.isShowLoading();
    }

    public TextWatcher getEditTextComment() {
        return AppUtils.getText(charSequence -> {
            setComment(charSequence + "");
        });
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
