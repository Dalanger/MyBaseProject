package com.dl.common.widget.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.dl.common.R;

/**
 * created by dalang at 2018/8/31
 *  竖排dialog
 */
public class DialogVertical extends BaseDialog {



    private TextView mButton1;
    private TextView mButton2;
    private TextView mButton3;
    private TextView tv_title;

    public DialogVertical(@NonNull Context context,boolean isShow) {
        super(context);
        initView(isShow);
    }


    public DialogVertical(@NonNull Context context,boolean isShow, float alpha, int gravity) {
        super(context, alpha, gravity);
        initView(isShow);
    }


    private void initView(boolean isShow) {
        View dialog_view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_vertical, null);
        tv_title = ((TextView) dialog_view.findViewById(R.id.tv_title));
        mButton1 = ((TextView) dialog_view.findViewById(R.id.btn_1));
        mButton2 = ((TextView) dialog_view.findViewById(R.id.btn_2));
        mButton3 = ((TextView) dialog_view.findViewById(R.id.btn_3));
        if (isShow) {
            mButton3.setVisibility(View.VISIBLE);
        } else {
            mButton3.setVisibility(View.GONE);
        }
        setContentView(dialog_view);
    }


    public void setButton1(String content) {
        this.mButton1.setText(content);
    }

    public void setButton2(String content) {
        this.mButton2.setText(content);
    }

    public void setButton3(String content) {
        this.mButton3.setText(content);
    }


    public void setTitle(String content) {
        this.tv_title.setText(content);
    }

    public void setOnBtn1Listener(View.OnClickListener onBtn1Listener) {
        mButton1.setOnClickListener(onBtn1Listener);
    }

    public void setOnBtn2Listener(View.OnClickListener onBtn2Listener) {
        mButton2.setOnClickListener(onBtn2Listener);
    }

    public void setOnBtn3Listener(View.OnClickListener onBtn3Listener) {
        mButton3.setOnClickListener(onBtn3Listener);
    }


}
