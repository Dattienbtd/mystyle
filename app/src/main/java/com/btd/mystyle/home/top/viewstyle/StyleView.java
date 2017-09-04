package com.btd.mystyle.home.top.viewstyle;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.btd.mystyle.data.Item;
import com.btd.mystyle.data.Post;
import com.btd.mystyle.databinding.ViewStyleBinding;


/**
 * Created by FRAMGIA\bui.tien.dat on 30/03/2017.
 */

public class StyleView extends LinearLayout {
    private Context mContext;
    private ViewStyleBinding mViewStyleBinding;
    private ItemAdapter adapter;

    public StyleView(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public StyleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    public void initView() {
        LayoutInflater mInflater =
                (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewStyleBinding = ViewStyleBinding.inflate(mInflater, this, true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        layoutManager.setReverseLayout(true);
        mViewStyleBinding.rcvItem.setLayoutManager(layoutManager);
        mViewStyleBinding.rcvItem.addItemDecoration(new VerticalSpaceItemDecoration(40));
        adapter = new ItemAdapter(mContext);
        mViewStyleBinding.rcvItem.setAdapter(adapter);
    }

    public void setPost(Post post) {
        if (post != null && post.getListItem() != null) {
            adapter.addItem(post);
        }
        mViewStyleBinding.setPost(post);
    }

    public void addItem(Item item) {
        adapter.addItem(item);
    }

    public void setBackground(Bitmap bitmap) {
        mViewStyleBinding.imgViewStyle.setImageBitmap(bitmap);
    }

    public void setItemClickListener(OnClickItem onClickItem) {
        if (adapter != null)
            adapter.setOnItemClickListener((position, v) -> {
                onClickItem.onItem(position, v);
            });
    }

    public interface OnClickItem {
        void onItem(int position, View v);
    }

}
