package com.btd.mystyle.home.post.add.web;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.btd.mystyle.BaseActivity;
import com.btd.mystyle.R;
import com.btd.mystyle.databinding.ActivitySelectWebBinding;
import com.btd.mystyle.utils.Constants;

/**
 * Created by dattien on 4/6/17.
 */

public class SelectWebActivity extends BaseActivity implements SelectWebContract.View {

    private ActivitySelectWebBinding activitySelectWebBinding;
    private SelectWebContract.Presenter mPresenter;
    private SelectWebViewModel mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySelectWebBinding = DataBindingUtil.setContentView(this, R.layout.activity_select_web);
        initView();
    }

    private void initView() {
        new SelectWebPresenter(this, this);
        mViewModel = new SelectWebViewModel(this, mPresenter);
        activitySelectWebBinding.setViewmodel(mViewModel);
        activitySelectWebBinding.setPresenter(mPresenter);
        WebSettings websettings = activitySelectWebBinding.webviewDetail.getSettings();
        websettings.setJavaScriptEnabled(true);
        websettings.setDomStorageEnabled(true);
        websettings.setUserAgentString("Android");
        websettings.setJavaScriptCanOpenWindowsAutomatically(true);
        activitySelectWebBinding.webviewDetail.setWebViewClient(new DetailWebViewClient());
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String valueUrl = extras.getString(Constants.EXTRA_URL);
            if (!TextUtils.isEmpty(valueUrl)) {
                activitySelectWebBinding.webviewDetail.loadUrl(valueUrl);
            }
        }
    }

    @Override
    public void setPresenter(SelectWebContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showSearch() {
        finish();
    }

    @Override
    public void showDone() {
        Intent intent = new Intent();
        intent.setAction(Constants.ACTION_SELECT_URL);
        intent.putExtra(Constants.EXTRA_URL, activitySelectWebBinding.webviewDetail.getUrl());
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        finish();
    }

    @Override
    public void showReset() {
        activitySelectWebBinding.webviewDetail.reload();
    }

    @Override
    public void showNext() {
        if (activitySelectWebBinding.webviewDetail.canGoForward()) {
            activitySelectWebBinding.webviewDetail.goForward();
        }
    }

    @Override
    public void showBack() {
        if (activitySelectWebBinding.webviewDetail.canGoBack()) {
            activitySelectWebBinding.webviewDetail.goBack();
        }
    }

    @Override
    public void showBrowser() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(activitySelectWebBinding.webviewDetail.getUrl()));
        startActivity(browserIntent);
    }

    @Override
    public void onBackPressed() {
        if (activitySelectWebBinding.webviewDetail.canGoBack()) {
            activitySelectWebBinding.webviewDetail.goBack();
        } else
            super.onBackPressed();
    }

    private class DetailWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            mViewModel.setLoading(true);
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            mViewModel.setLoading(false);
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedError(
                WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            mViewModel.setLoading(false);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);

        }
    }
}
