package com.dl.common.widget.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.dl.common.R;

/**
 * created by dalang at 2018/8/31
 * 通用提示弹框
 *
 */
public class DialogNormal extends BaseDialog {

    private TextView mTvContent;
    private TextView mTvSure;
    private TextView mTvCancel;
    private TextView mTvTitle;


    public DialogNormal(@NonNull Context context) {
        super(context);
        initView();
    }

    /**
     * @param context
     * @param alpha   透明度 0.0f--1f(不透明)
     * @param gravity 方向(Gravity.BOTTOM,Gravity.TOP,Gravity.LEFT,Gravity.RIGHT)
     */
    public DialogNormal(@NonNull Context context, float alpha, int gravity) {
        super(context, alpha, gravity);
        initView();
    }

    private void initView() {
        View dialog_view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_normal, null);
        mTvSure = (TextView) dialog_view.findViewById(R.id.tv_sure);
        mTvCancel = (TextView) dialog_view.findViewById(R.id.tv_cancel);
        mTvContent = (TextView) dialog_view.findViewById(R.id.tv_content);
        mTvTitle = (TextView) dialog_view.findViewById(R.id.tv_title);
        mTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        setContentView(dialog_view);
    }

    //设置标题
    public void setTitle(String title) {
        mTvTitle.setText(title);
    }

    public TextView getTvTitle() {
        return mTvTitle;
    }

    //设置内容
    public void setContent(String content) {
        this.mTvContent.setText(content);
    }

    public TextView getTvContent() {
        return mTvContent;
    }

    //设置确定按钮字样
    public void setSure(String strSure) {
        this.mTvSure.setText(strSure);
    }

    public TextView getTvSure() {
        return mTvSure;
    }
    //设置取消按钮字样
    public void setCancel(String strCancel) {
        this.mTvCancel.setText(strCancel);
    }

    public TextView getTvCancel() {
        return mTvCancel;
    }
    //设置取消事件监听
    public void setSureListener(View.OnClickListener sureListener) {
        mTvSure.setOnClickListener(sureListener);
    }
    //设置确定事件监听
    public void setCancelListener(View.OnClickListener cancelListener) {
        mTvCancel.setOnClickListener(cancelListener);
    }


}
