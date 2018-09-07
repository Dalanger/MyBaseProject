package com.dl.mybaseproject;

import android.app.Application;
import android.content.Context;

import com.dl.common.uitils.ToastUtil;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;

public class MyApp extends Application {
    private static MyApp mApplication;
    public static Context mContext;


    @Override
    public void onCreate() {
        super.onCreate();
        BGASwipeBackHelper.init(this,null);
        mContext = getApplicationContext();
        ToastUtil.init(mContext);
    }

    public static Context getmContext() {
        return mContext;
    }

    public static MyApp getInstance() {
        if (mApplication == null) {
            mApplication = new MyApp();
        }
        return mApplication;
    }
}
