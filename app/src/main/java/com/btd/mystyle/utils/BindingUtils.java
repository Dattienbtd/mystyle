package com.btd.mystyle.utils;

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.widget.ImageView;

import com.btd.mystyle.App;
import com.btd.mystyle.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.BitmapEncoder;

/**
 * Created by dattien on 3/26/17.
 */

public class BindingUtils {

    @BindingAdapter("backgroundUrl")
    public static void setBackgroundUrl(ImageView imageView, String url) {
        String image = "";
        if (!TextUtils.isEmpty(url)) {
            image = url;
        }
        if (url.equals(Constants.CANCEL_IMAGE)) {
            return;
        }
        Glide.with(App.getInstance())
                .load(image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.color.color_black2)
                .into(imageView);
    }

    @BindingAdapter("backgroundResource")
    public static void setBackgroundImage(ImageView imageView, int imageId) {
        Glide.with(App.getInstance())
                .fromResource()
                .asBitmap()
                .encoder(new BitmapEncoder(Bitmap.CompressFormat.PNG, 100))
                .load(imageId)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .error(R.color.color_black2)
                .into(imageView);
    }

    @BindingAdapter("backgroundBitmap")
    public static void setBackgroundBitmap(ImageView iv, Bitmap bitmap) {
        iv.setImageBitmap(bitmap);
    }

    @BindingAdapter("backgroundApp")
    public static void setBackgroundApp(ImageView iv, Object bitmap) {
        if (bitmap instanceof Bitmap) {
            iv.setImageBitmap((Bitmap) bitmap);
        } else {
            Glide.with(App.getInstance())
                    .load(bitmap.toString())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.color.color_black2)
                    .into(iv);
        }
    }

}
