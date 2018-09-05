package com.dl.common.widget.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.dl.common.R;

/**
 * created by dalang at 2018/8/31
 */
public class DialogSingle extends BaseDialog {


    private TextView mTvContent;
    private TextView mTvSure;
    private TextView mTvTitle;

    public DialogSingle(@NonNull Context context) {
        super(context);
        initView();
    }

    public DialogSingle(@NonNull Context context, float alpha, int gravity) {
        super(context, alpha, gravity);
        initView();
    }

    private void initView() {
        View dialog_view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_single, null);
        mTvSure = (TextView) dialog_view.findViewById(R.id.tv_sure);
        mTvContent = (TextView) dialog_view.findViewById(R.id.tv_content);
        mTvTitle = (TextView) dialog_view.findViewById(R.id.tv_title);
        setCancelable(false);
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

    //设置确定事件监听
    public void setSureListener(View.OnClickListener sureListener) {
        mTvSure.setOnClickListener(sureListener);
    }

}
