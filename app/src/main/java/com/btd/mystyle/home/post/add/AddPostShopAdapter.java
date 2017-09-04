package com.btd.mystyle.home.post.add;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.btd.mystyle.data.Shop;
import com.btd.mystyle.databinding.ItemAddPostShopBinding;

import java.util.ArrayList;

/**
 * Created by dattien on 4/3/17.
 */

public class AddPostShopAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater inflater;
    private Context mContext;
    private ArrayList<Shop> mListItem;
    private ClickListener mClickListener;
    private ItemAddPostShopBinding mItemAddPostBinding;

    private int positionNow = -1;

    public AddPostShopAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.mContext = context;
        mListItem = new ArrayList<>();
    }

    public void addItem(ArrayList<Shop> list) {
        mListItem.clear();
        mListItem.addAll(list);
        notifyDataSetChanged();
    }

    public void addItem(Shop item) {
        if (item == null) {
            return;
        }
        mListItem.add(item);
        notifyItemInserted(mListItem.size() - 1);
    }

    public void removeItem(Shop item) {
        mListItem.remove(item);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mItemAddPostBinding = ItemAddPostShopBinding.inflate(inflater, parent, false);
        return new DialogViewHolder(mItemAddPostBinding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Shop item = mListItem.get(position);
        ((DialogViewHolder) holder).getItemDialogBinding().setShop(item);
        ((DialogViewHolder) holder).getItemDialogBinding()
                .setItemHandler(new ItemHandler(positionNow == position));
    }

    @Override
    public int getItemCount() {
        return mListItem.size();
    }

    class DialogViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ItemAddPostShopBinding itemAddPostBinding;

        private DialogViewHolder(ItemAddPostShopBinding itemAddPostBinding) {
            super(itemAddPostBinding.getRoot());
            this.itemAddPostBinding = itemAddPostBinding;
            this.itemAddPostBinding.getRoot().setOnClickListener(this);
        }

        public ItemAddPostShopBinding getItemDialogBinding() {
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

    public ArrayList<Shop> getmListItem() {
        return mListItem;
    }

    public void setPositionNow(int positionNow) {
        this.positionNow = positionNow;
        notifyDataSetChanged();
    }

    public class ItemHandler {
        boolean isNow = false;

        ItemHandler(boolean isNow) {
            this.isNow = isNow;
        }

        public boolean isNow() {
            return isNow;
        }

        public void setNow(boolean now) {
            isNow = now;
        }
    }

}