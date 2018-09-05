package com.dl.common.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

/**
 * created by dalang at 2018/8/31
 * 弹窗dialog 基类
 */
public class BaseDialog extends Dialog{


    protected Context mContext;

    protected WindowManager.LayoutParams mLayoutParams;

    public BaseDialog(@NonNull Context context) {
        super(context);
        initView(context);
    }


    /**
     * @param context
     * @param alpha   透明度 0.0f--1f(不透明)
     * @param gravity 方向(Gravity.BOTTOM,Gravity.TOP,Gravity.LEFT,Gravity.RIGHT)
     */
    public BaseDialog(@NonNull Context context, float alpha, int gravity) {
        super(context);
        initView(context,alpha,gravity);
    }

    private void initView(Context context) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mContext = context;
        Window window = this.getWindow();
        mLayoutParams = window.getAttributes();
        mLayoutParams.alpha = 1f;
        window.setAttributes(mLayoutParams);
        if (mLayoutParams != null) {
            mLayoutParams.height = android.view.ViewGroup.LayoutParams.MATCH_PARENT;
            mLayoutParams.gravity = Gravity.CENTER;
        }
    }




    private void initView(Context context, float alpha, int gravity) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mContext = context;
        Window window = this.getWindow();
        mLayoutParams = window.getAttributes();
        mLayoutParams.alpha = alpha;
        window.setAttributes(mLayoutParams);
        if (mLayoutParams != null) {
            mLayoutParams.height = android.view.ViewGroup.LayoutParams.MATCH_PARENT;
            mLayoutParams.gravity = gravity;
        }
    }


    /**
     * 隐藏头部导航栏状态栏
     */
    public void skipTools() {
        if (Build.VERSION.SDK_INT < 19) {
            return;
        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}
