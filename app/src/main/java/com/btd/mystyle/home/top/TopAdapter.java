package com.btd.mystyle.home.top;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.btd.mystyle.data.Post;
import com.btd.mystyle.databinding.ItemTopBinding;
import com.btd.mystyle.home.top.viewstyle.ItemAdapter;

import java.util.ArrayList;


/**
 * Created by FRAMGIA\bui.tien.dat on 30/03/2017.
 */

public class TopAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<Post> mListPost = new ArrayList<>();
    private ItemAdapter.ClickListener mClickListener;
    private ItemTopBinding mItemPostBinding;

    public TopAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    public void addItem(ArrayList<Post> list) {
        mListPost.clear();
        mListPost = list;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mItemPostBinding = ItemTopBinding.inflate(inflater, parent, false);
        return new PostViewHolder(mItemPostBinding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Post post = mListPost.get(position);
        ((PostViewHolder) holder).getItemPostBinding().styleView.setPost(post);
        ((PostViewHolder) holder).getItemPostBinding().setPost(post);
    }

    @Override
    public int getItemCount() {
        return mListPost.size();
    }

    class PostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ItemTopBinding itemPostBinding;

        private PostViewHolder(ItemTopBinding itemPostBinding) {
            super(itemPostBinding.getRoot());
            this.itemPostBinding = itemPostBinding;
            this.itemPostBinding.getRoot().setOnClickListener(this);
        }

        public ItemTopBinding getItemPostBinding() {
            return itemPostBinding;
        }

        @Override
        public void onClick(View v) {
            if (mClickListener != null) {
                mClickListener.onItemClick(getAdapterPosition(), v);
            }
        }
    }

    public void setOnItemClickListener(ItemAdapter.ClickListener clickListener) {
        mClickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
    }
}