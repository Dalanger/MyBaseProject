package com.dl.common.widget.dialog;


import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.dl.common.R;
import com.dl.common.manager.EditTextManager;

/**
 * created by dalang at 2018/8/31
 * 输入弹框
 *
 */
public class DialogInput extends BaseDialog{

    private EditText mEtContent;
    private TextView mTvSure;
    private TextView mTvCancel;
    private TextView mTvTitle;


    public void setTitle(String title) {
        mTvTitle.setText(title);
    }

    public void setContent(String content) {
        this.mEtContent.setText(content);
    }

    public String getContent() {
        return mEtContent.getText().toString().trim();
    }

    public EditText getEditText() {
        return mEtContent;
    }

    public void setSure(String strSure) {
        this.mTvSure.setText(strSure);
    }


    public void setCancel(String strCancel) {
        this.mTvCancel.setText(strCancel);
    }

    public TextView getTvCancel() {
        return mTvCancel;
    }

    public void setSureListener(View.OnClickListener sureListener) {
        mTvSure.setOnClickListener(sureListener);
    }

    public void setCancelListener(View.OnClickListener cancelListener) {
        mTvCancel.setOnClickListener(cancelListener);
    }

    public DialogInput(@NonNull Context context) {
        super(context);
        initView();
    }

    private void initView() {
        View dialog_view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_input, null);
        mTvSure = (TextView) dialog_view.findViewById(R.id.tv_sure);
        mTvCancel = (TextView) dialog_view.findViewById(R.id.tv_cancel);
        mEtContent = (EditText) dialog_view.findViewById(R.id.et_content);
        mTvTitle = (TextView) dialog_view.findViewById(R.id.tv_title);
        EditTextManager.setInputRule(mEtContent,8);
        mEtContent.requestFocus();
        mTvCancel.setOnClickListener(v -> dismiss());
        setContentView(dialog_view);
    }
}
