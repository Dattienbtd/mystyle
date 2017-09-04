package com.btd.mystyle.home.top.viewstyle;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.btd.mystyle.data.Item;
import com.btd.mystyle.data.Post;
import com.btd.mystyle.data.Shop;
import com.btd.mystyle.databinding.ItemViewStyleBinding;

import java.util.ArrayList;

/**
 * Created by FRAMGIA\bui.tien.dat on 30/03/2017.
 */

public class ItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater inflater;
    private Context mContext;
    private ArrayList<Item> mListItem;
    private ArrayList<Shop> mListShop;
    private ClickListener mClickListener;
    private ItemViewStyleBinding mItemViewStyleBinding;

    public ItemAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.mContext = context;
        mListItem = new ArrayList<>();
        mListShop = new ArrayList<>();
    }

    public void addItem(Post post) {
        mListItem.clear();
        mListShop.clear();
        mListItem.addAll(post.getListItem());
        mListShop.addAll(post.getListShop());
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
        mItemViewStyleBinding = ItemViewStyleBinding.inflate(inflater, parent, false);
        return new ItemViewHolder(mItemViewStyleBinding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Item item = mListItem.get(position);
        if (mListShop != null && mListShop.size() > 0) {
            Shop shop = mListShop.get(position);
            ((ItemViewHolder) holder).getItemViewStyleBinding().setShop(shop);
        }
        ((ItemViewHolder) holder).getItemViewStyleBinding().setItem(item);

    }

    @Override
    public int getItemCount() {
        return mListItem.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ItemViewStyleBinding itemViewStyleBinding;

        private ItemViewHolder(ItemViewStyleBinding itemViewStyleBinding) {
            super(itemViewStyleBinding.getRoot());
            this.itemViewStyleBinding = itemViewStyleBinding;
            this.itemViewStyleBinding.llItem.setOnClickListener(this);
        }

        public ItemViewStyleBinding getItemViewStyleBinding() {
            return itemViewStyleBinding;
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
}