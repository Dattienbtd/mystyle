package com.btd.mystyle.home.post;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.btd.mystyle.R;
import com.btd.mystyle.data.Grallerry;
import com.btd.mystyle.view.CustomImageView;
import com.btd.mystyle.view.CustomRoundImageView;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by root on 23/09/2016.
 */
public class ImageGralleryAdapter extends RecyclerView.Adapter<ImageGralleryAdapter.ViewHolder>
        implements View.OnClickListener {

    private List<Grallerry> items;
    private OnItemClickListener onItemClickListener;
    private Context context;

    public ImageGralleryAdapter(Context context, List<Grallerry> items) {
        this.items = items;
        this.context = context;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grallery, parent, false);
        v.setOnClickListener(this);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Grallerry item = items.get(position);
        Glide.with(context)
                .load(item.getUrl())
                .centerCrop()
                .placeholder(R.color.color_black3)
                .into(holder.image);
        holder.itemView.setTag(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onClick(final View v) {
        if (onItemClickListener != null) onItemClickListener.onItemClick(v, (Grallerry) v.getTag());
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        public CustomRoundImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (CustomRoundImageView) itemView.findViewById(R.id.img_grallery);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, Grallerry viewModel);
    }
}
