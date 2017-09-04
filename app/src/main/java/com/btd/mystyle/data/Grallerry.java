package com.btd.mystyle.data;

import android.graphics.Bitmap;

/**
 * Created by root on 23/09/2016.
 */
public class Grallerry {
  private Bitmap bitmap;
  private String url;

  public Grallerry(String url, Bitmap bitmap) {
    this.bitmap = bitmap;
    this.url = url;
  }

  public Bitmap getBitmap() {
    return bitmap;
  }

  public void setBitmap(Bitmap bitmap) {
    this.bitmap = bitmap;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }
}
