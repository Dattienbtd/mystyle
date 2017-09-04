package com.btd.mystyle.home.post.edit;

import android.content.Context;
import android.graphics.Bitmap;

/**
 * Created by dattien on 3/5/17.
 */

public class FilterItemActionHandler {
    private Context mContext;
    private boolean isSelect = false;
    private Bitmap bitmap;

    private String text;

    public FilterItemActionHandler(Context context) {
        mContext = context;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
