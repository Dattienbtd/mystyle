package com.btd.mystyle.home.post.edit;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.btd.mystyle.databinding.ItemFilterImageBinding;
import com.btd.mystyle.home.post.edit.filter.ImageBlurRender;
import com.btd.mystyle.home.post.edit.filter.ImageBlurUtils;
import com.btd.mystyle.home.post.edit.filter.PixelBuffer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 23/09/2016.
 */
public class FilterImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private Bitmap bitmap;
    private int selectedItem = 0;
    private Map<String, Bitmap> previewImageMap = new HashMap<String, Bitmap>();
    private ImageBlurRender imageBlurRender;
    private PixelBuffer pixelBuffer;
    private LayoutInflater inflater;
    private ClickListener mClickListener;


    public FilterImageAdapter(Context context, Bitmap imageBitmap) {
        this.context = context;
        this.bitmap = scaleDown(imageBitmap, 250, false);
        imageBlurRender = new ImageBlurRender(context, bitmap);
        inflater = LayoutInflater.from(context);
    }

    public void setBitmap(Bitmap bitmap1) {
        this.selectedItem = 0;
        this.bitmap = scaleDown(bitmap1, 250, false);
        this.imageBlurRender = new ImageBlurRender(context, bitmap);
        previewImageMap.clear();
        notifyDataSetChanged();
    }

    @Override
    public FilterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemFilterImageBinding itemFilterImageBinding = ItemFilterImageBinding.inflate(inflater, parent, false);
        return new FilterViewHolder(itemFilterImageBinding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        String effectName = ImageBlurUtils.getEffectName().get(position);
        Bitmap preBitmap = previewImageMap.get(effectName);
        FilterItemActionHandler filterItemActionHandler = new FilterItemActionHandler(context);
        ((FilterViewHolder) holder).getItemFilterImageBinding().setActionHandler(filterItemActionHandler);
        filterItemActionHandler.setSelect(selectedItem == position);
        filterItemActionHandler.setText(ImageBlurUtils.getEffectName().get(position));
        if (preBitmap == null) {
            if (position == 0) {
                filterItemActionHandler.setBitmap(bitmap);
            } else {
                ((FilterViewHolder) holder).getItemFilterImageBinding().imgFilter.setTag(effectName);
                new BlurImagesTask(position, effectName, ((FilterViewHolder) holder).getItemFilterImageBinding().imgFilter).execute(bitmap);
            }
        } else {
            filterItemActionHandler.setBitmap(preBitmap);
        }
    }


    @Override
    public int getItemCount() {
        return ImageBlurUtils.getEffectName().size();
    }

    public class BlurImagesTask extends AsyncTask<Bitmap, Void, Bitmap> {

        private int position;
        private String strEffectName;
        private ImageView roundedImageView;

        public BlurImagesTask(int pos, String name, ImageView imageView) {
            this.position = pos;
            this.strEffectName = name;
            this.roundedImageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(Bitmap... param) {
            Bitmap bitmap = param[0];
            pixelBuffer = new PixelBuffer(bitmap.getWidth(), bitmap.getHeight());
            pixelBuffer.setRenderer(imageBlurRender);
            imageBlurRender.mCurrentEffect = position;

            return pixelBuffer.getBitmap(null, null);
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            if (((String) roundedImageView.getTag()).equals(strEffectName)) {
                roundedImageView.setImageBitmap(result);
            }
            previewImageMap.put(strEffectName, result);
        }
    }

    public static Bitmap scaleDown(Bitmap realImage, float maxImageSize, boolean filter) {
        float ratio = Math.min((float) maxImageSize / realImage.getWidth(),
                (float) maxImageSize / realImage.getHeight());
        int width = Math.round((float) ratio * realImage.getWidth());
        int height = Math.round((float) ratio * realImage.getHeight());

        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width, height, filter);
        return newBitmap;
    }

    class FilterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ItemFilterImageBinding itemFilterImageBinding;

        private FilterViewHolder(ItemFilterImageBinding itemFilterImageBinding) {
            super(itemFilterImageBinding.getRoot());
            this.itemFilterImageBinding = itemFilterImageBinding;
            this.itemFilterImageBinding.getRoot().setOnClickListener(this);
        }

        public ItemFilterImageBinding getItemFilterImageBinding() {
            return itemFilterImageBinding;
        }

        @Override
        public void onClick(View v) {
            if (mClickListener != null) {
                mClickListener.onItemClick(getAdapterPosition(), v);
                setSelectedItem(getAdapterPosition());
            }
        }
    }

    public void setSelectedItem(int selectItemid) {
        if (this.selectedItem != selectItemid) {
            notifyItemChanged(selectedItem);
            this.selectedItem = selectItemid;
            notifyItemChanged(selectItemid);
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        mClickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
    }

}
