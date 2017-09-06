package com.btd.mystyle.home.post;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.btd.mystyle.data.Grallerry;
import com.btd.mystyle.databinding.FragmentPostBinding;
import com.btd.mystyle.home.post.edit.EditPostActivity;
import com.btd.mystyle.home.post.edit.FilterImageAdapter;
import com.btd.mystyle.utils.BitmapUtils;
import com.btd.mystyle.utils.Constants;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * Created by dattien on 2/20/17.
 */
@RuntimePermissions
public class PostFragment extends Fragment implements PostContract.View {
    private static final int REQUEST_CAMERA = 1009;
    private static final int REQUEST_GALLERY = 1008;

    private FragmentPostBinding fragmentPostBinding;
    private PostContract.Presenter mPresenter;
    private ImageGralleryAdapter mAdapter;
    private boolean isStart = false;
    private boolean isStop = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentPostBinding = FragmentPostBinding.inflate(inflater, container, false);
        isStop = true;
        return fragmentPostBinding.getRoot();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (isStart) {
            PostFragmentPermissionsDispatcher.initPermissionWithCheck(this);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isStart = isVisibleToUser;
        if (isStop && isStart) {
            isStop = false;
            PostFragmentPermissionsDispatcher.initPermissionWithCheck(this);
        }
    }

    @Override
    public void showCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            File photoFile = mPresenter.createImageFile();
            if (photoFile != null) {
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(cameraIntent, REQUEST_CAMERA);
            }
        }
    }

    @Override
    public void showCropView(Bitmap bitmap) {
        if (bitmap != null) {
            fragmentPostBinding.imgCropper.setImageBitmap(bitmap);
        }
    }

    @Override
    public void showRotateView() {
        Bitmap bitmap = mPresenter.getBitmapImage();
        if (bitmap == null)
            return;
        bitmap = BitmapUtils.rotateBitmap(bitmap, 90);
        mPresenter.setBitmapImage(bitmap);
        showCropView(bitmap);
    }

    @Override
    public void showSnapView(boolean isSnap) {
        if (isSnap) {
            fragmentPostBinding.imgCropper.cropToCenter();
        } else {
            fragmentPostBinding.imgCropper.fitToCenter();
        }
    }

    @Override
    public void showAddPost() {
        Bitmap bitmap = fragmentPostBinding.imgCropper.getCroppedBitmap();
        if (bitmap != null) {
            bitmap = FilterImageAdapter.scaleDown(bitmap, 512, true);
            Intent intent = new Intent(getContext(), EditPostActivity.class);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            intent.putExtra(Constants.EXTRA_BITMAP, byteArray);
            getContext().startActivity(intent);
        }
    }

    @Override
    public void showImageGrallery(ArrayList<Grallerry> list) {
        mAdapter = new ImageGralleryAdapter(getContext(), list);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 5);
        fragmentPostBinding.rcvImage.setLayoutManager(gridLayoutManager);
        fragmentPostBinding.rcvImage.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((view, viewModel) -> {
            Bitmap bitmap = BitmapFactory.decodeFile(viewModel.getUrl());
            mPresenter.setBitmapImage(bitmap);
            showCropView(bitmap);
        });
    }

    @Override
    public void setPresenter(PostContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CAMERA && resultCode == getActivity().RESULT_OK) {
            mPresenter.createBitmapCamera();
        }
        if (resultCode == getActivity().RESULT_OK && requestCode == REQUEST_GALLERY) {
            mPresenter.createBitmapGrallery(data.getData());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @NeedsPermission({
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    })
    void initPermission() {
        if (mPresenter == null) {
            new PostPresenter(getContext(), this);
            fragmentPostBinding.setPresenter(mPresenter);
        }
    }

    @OnShowRationale({
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_WIFI_STATE
    })
    void showRationaleForLogin(final PermissionRequest request) {
        new AlertDialog.Builder(getContext())
                .setMessage("Phone state and wifi state are need to login function")
                .setPositiveButton("Allow", (dialog, button) -> request.proceed())
                .setNegativeButton("Deny", (dialog, button) -> request.cancel())
                .setCancelable(false)
                .show();
    }

    @OnPermissionDenied({
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    })
    void showDeniedForLogin() {
        Toast.makeText(getContext(), "Phone state or wifi state was denied." +
                        " Please consider granting it in order to access the login function",
                Toast.LENGTH_SHORT).show();
    }

    @OnNeverAskAgain({
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    })
    void showNeverAskForLogin() {
        Toast.makeText(getContext(), "Phone state or wifi state was denied with never ask again.",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PostFragmentPermissionsDispatcher.onRequestPermissionsResult(this,
                requestCode, grantResults);

    }

}