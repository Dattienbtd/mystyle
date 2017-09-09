package com.btd.mystyle.home.post;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.btd.mystyle.data.Grallerry;
import com.btd.mystyle.utils.BitmapUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import id.zelory.compressor.Compressor;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dattien on 2/26/17.
 */

public class PostPresenter implements PostContract.Presenter {
    private Context mContext;
    private PostContract.View mPostView;
    private String mPhotoPath;
    private Bitmap mBitmap;
    private boolean isSnapView = false;
    private File mActualImage;

    public PostPresenter(Context context, PostContract.View view) {
        mContext = context;
        mPostView = view;
        mPostView.setPresenter(this);
        start();
    }

    @Override
    public void start() {
        getImageGrallerry();
    }

    @Override
    public void destroy() {
        mPostView = null;
    }

    @Override
    public void getImageGrallerry() {
        final String[] columns = {
                MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID
        };
        final String orderBy = MediaStore.Images.Media._ID;
        Cursor imagecursor = ((Activity) mContext).managedQuery(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null, orderBy);
        int columnIndex = imagecursor.getColumnIndex(MediaStore.Images.Media._ID);
        ArrayList<Grallerry> list = new ArrayList<>();
        for (int i = 0; i < imagecursor.getCount(); i++) {
            imagecursor.moveToPosition(i);
            int id = imagecursor.getInt(columnIndex);
            int dataColumnIndex = imagecursor.getColumnIndex(MediaStore.Images.Media.DATA);
            list.add(new Grallerry(imagecursor.getString(dataColumnIndex),
                    MediaStore.Images.Thumbnails.getThumbnail(
                            mContext.getApplicationContext().getContentResolver(), id,
                            MediaStore.Images.Thumbnails.MICRO_KIND, null)));
        }
        mPostView.showImageGrallery(list);
    }

    @Override
    public File createImageFile() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = null;
        try {
            image = File.createTempFile(imageFileName, ".jpg", storageDir);
        } catch (IOException e) {
            mPostView.showError();
            e.printStackTrace();
        }
        mPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    @Override
    public void onClickCamera() {
        mPostView.showCamera();
    }

    @Override
    public void onClickRotate() {
        mPostView.showRotateView();
    }

    @Override
    public void onClickSnap() {
        isSnapView = !isSnapView;
        mPostView.showSnapView(isSnapView);
    }

    @Override
    public void onClickNext() {
        mPostView.showAddPost();
    }

    @Override
    public Bitmap getBitmapImage() {
        return mBitmap;
    }

    @Override
    public void setBitmapImage(Bitmap bitmap) {
        mBitmap = bitmap;
    }

    @Override
    public void createBitmapCamera() {
        try {
            mActualImage = BitmapUtils.from(mContext, Uri.parse(mPhotoPath));
            compressImage();
        } catch (IOException e) {
            mPostView.showError();
            e.printStackTrace();
        }
    }

    @Override
    public void createBitmapGrallery(Uri uri) {
        try {
            mActualImage = BitmapUtils.from(mContext, uri);
            compressImage();
        } catch (IOException e) {
            mPostView.showError();
            e.printStackTrace();
        }
    }

    @Override
    public void compressImage() {
        if (mActualImage == null) {
            mPostView.showError();
            return;
        }
        {
            new Compressor(mContext)
                    .setMaxWidth(640)
                    .setMaxHeight(640)
                    .compressToBitmapAsFlowable(mActualImage)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(bitmap -> {
                        mBitmap = bitmap;
                        mPostView.showCropView(mBitmap);
                    }, throwable -> throwable.printStackTrace());
        }
    }
}
