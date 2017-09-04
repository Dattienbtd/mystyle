package com.btd.mystyle.splash;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

/**
 * Created by dattien on 2/4/17.
 */

public class SplashPresenter implements SplashContract.Presenter {

    private SplashContract.View mSplashView;
    private Context mContext;

    public SplashPresenter(Context context, SplashContract.View view) {
        mContext = context;
        mSplashView = view;
        mSplashView.setPresenter(this);
    }

    @Override
    public void openMain() {
        mSplashView.showMain();
    }

    @Override
    public void start() {
        Handler mHandler = new Handler(Looper.myLooper());
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                openMain();
            }
        }, 3000);
    }

    @Override
    public void destroy() {

    }
}
