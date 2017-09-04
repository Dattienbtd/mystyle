package com.btd.mystyle.home.shop;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.btd.mystyle.R;
import com.btd.mystyle.data.Favorite;
import com.btd.mystyle.data.Shop;
import com.btd.mystyle.databinding.ItemShopBinding;

import java.util.ArrayList;

/**
 * Created by dattien on 3/26/17.
 */

public class ShopAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater inflater;
    private Context mContext;
    private ArrayList<Shop> mListShop;
    private ClickListener mClickListener;
    private ClickListener mClickListenerFavorite;
    private ItemShopBinding mItemShopBinding;
    private ArrayList<Favorite> listFavorite = new ArrayList<>();
    private int position = -1;

    public ShopAdapter(Context context, ArrayList<Shop> list) {
        inflater = LayoutInflater.from(context);
        this.mContext = context;
        this.mListShop = list;
    }

    public void addItem(ArrayList<Shop> list) {
        mListShop.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mItemShopBinding = ItemShopBinding.inflate(inflater, parent, false);
        return new ShopViewHolder(mItemShopBinding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Shop shop = mListShop.get(position);
        shop.setFavorite(isFavorite(shop));
        ((ShopViewHolder) holder).getItemShopBinding()
                .setShop(shop);
        ((ShopViewHolder) holder).getItemShopBinding().tvCount.setText((position + 1) + "");
    }

    @Override
    public int getItemCount() {
        return mListShop.size();
    }

    class ShopViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ItemShopBinding itemShopBinding;

        private ShopViewHolder(ItemShopBinding itemShopBinding) {
            super(itemShopBinding.getRoot());
            this.itemShopBinding = itemShopBinding;
            this.itemShopBinding.getRoot().setOnClickListener(this);
            this.itemShopBinding.imgFavorite.setOnClickListener(this);
        }

        public ItemShopBinding getItemShopBinding() {
            return itemShopBinding;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.img_favorite:
                    if (mClickListenerFavorite != null) {
                        mClickListenerFavorite.onItemClick(getAdapterPosition(), v);
                    }
                    break;
                default:
                    if (mClickListener != null) {
                        mClickListener.onItemClick(getAdapterPosition(), v);
                    }
            }
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        mClickListener = clickListener;
    }

    public void setOnItemClickListenerFavorite(ClickListener clickListener) {
        mClickListenerFavorite = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
    }

    public ArrayList<Shop> getmListShop() {
        return mListShop;
    }

    public ArrayList<Favorite> getListFavorite() {
        return listFavorite;
    }

    public void setListFavorite(ArrayList<Favorite> listFavorite, int position) {
        this.listFavorite = listFavorite;
        if (position != -1) {
            notifyItemChanged(position);
        }
    }

    public boolean isFavorite(Shop shop) {
        for (int i = 0; i < listFavorite.size(); i++) {
            if (shop.getId() == listFavorite.get(i).getShopid()) {
                position = i;
                return true;
            }
        }
        return false;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}