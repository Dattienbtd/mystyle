package com.btd.mystyle.home.post.add;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.btd.mystyle.data.Item;
import com.btd.mystyle.databinding.ItemAddPostBinding;
import com.btd.mystyle.home.top.viewstyle.ItemAdapter;

import java.util.ArrayList;

/**
 * Created by dattien on 4/2/17.
 */

public class AddPostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater inflater;
    private Context mContext;
    private ArrayList<Item> mListItem;
    private ClickListener mClickListener;
    private ItemAddPostBinding mItemAddPostBinding;

    public AddPostAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.mContext = context;
        mListItem = new ArrayList<>();
    }

    public void addItem(ArrayList<Item> list) {
        mListItem.clear();
        mListItem.addAll(list);
        notifyDataSetChanged();
    }

    public void addItem(Item item) {
        if (item == null) {
            return;
        }
        mListItem.add(item);
        notifyItemInserted(mListItem.size() - 1);
    }

    public void removeItem(Item item) {
        mListItem.remove(item);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mItemAddPostBinding = ItemAddPostBinding.inflate(inflater, parent, false);
        return new AddPostViewHolder(mItemAddPostBinding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Item item = mListItem.get(position);
        ((AddPostViewHolder) holder).getItemAddPostBinding().setItem(item);
    }

    @Override
    public int getItemCount() {
        return mListItem.size();
    }

    class AddPostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ItemAddPostBinding itemAddPostBinding;

        private AddPostViewHolder(ItemAddPostBinding itemAddPostBinding) {
            super(itemAddPostBinding.getRoot());
            this.itemAddPostBinding = itemAddPostBinding;
            this.itemAddPostBinding.getRoot().setOnClickListener(this);
        }

        public ItemAddPostBinding getItemAddPostBinding() {
            return itemAddPostBinding;
        }

        @Override
        public void onClick(View v) {
            if (mClickListener != null) {
                mClickListener.onItemClick(getAdapterPosition(), v);
            }
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        mClickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
    }

    public ArrayList<Item> getmListItem() {
        return mListItem;
    }
}