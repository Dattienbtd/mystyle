package com.btd.mystyle.home.post;

import android.graphics.Bitmap;
import android.net.Uri;

import com.btd.mystyle.BasePresenter;
import com.btd.mystyle.BaseView;
import com.btd.mystyle.data.Grallerry;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by dattien on 2/26/17.
 */

public interface PostContract {

    interface View extends BaseView<Presenter> {

        void showImageGrallery(ArrayList<Grallerry> list);

        void showCamera();

        void showCropView(Bitmap bitmap);

        void showRotateView();

        void showSnapView(boolean isSnap);

        void showAddPost();

        void showError();
    }

    public interface Presenter extends BasePresenter {

        void getImageGrallerry();

        File createImageFile();

        void onClickCamera();

        void onClickRotate();

        void onClickSnap();

        void onClickNext();

        Bitmap getBitmapImage();

        void setBitmapImage(Bitmap bitmap);

        void createBitmapCamera();

        void createBitmapGrallery(Uri uri);

        void compressImage();
    }

}
