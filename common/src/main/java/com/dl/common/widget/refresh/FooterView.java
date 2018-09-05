package com.dl.common.widget.refresh;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.dl.common.R;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;


/**
 * Created by dalang on 2017/9/19.
 */

public class FooterView extends FrameLayout implements PtrUIHandler {

    private ViewGroup footerView;
    private ImageView mImage;

    private AnimationDrawable animCat;
    private TextView load_text;

    public FooterView(@NonNull Context context) {
        this(context,null);
    }

    public FooterView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, null,0);
    }

    public FooterView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    private void init(Context context) {
       LayoutInflater.from(context).inflate(R.layout.refresh_layout_bottom,this,true);
        mImage = (ImageView) findViewById(R.id.img_pull_to_refresh);
        load_text = ((TextView) findViewById(R.id.pull_to_load_text));
        load_text.setText("上拉加载");
    }


    // 初始化到未刷新状态
    public void reset() {
        if (animCat != null) {
            animCat.stop();
            animCat = null;
        }
        mImage.setImageResource(R.mipmap.refresh01);
    }

    @Override
    public void onUIReset(PtrFrameLayout frame) {
       load_text.setText("上拉加载");
        reset();
    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {

        load_text.setText("上拉加载");
    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        load_text.setText("玩命加载中");
        if (animCat == null) {
            mImage.setImageResource(R.drawable.refresh_anim);
            animCat = (AnimationDrawable) mImage.getDrawable();
        }
        animCat.start();
    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {
        load_text.setText("加载完毕");
        reset();
    }



    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
        int mOffset=frame.getOffsetToRefresh();
        int currentPos=ptrIndicator.getCurrentPosY();
        int lastPos=ptrIndicator.getLastPosY();

//        LogUtils.i("加载过程"+mOffset+"----"+currentPos+"-----"+lastPos);
        if (currentPos<mOffset && lastPos>=mOffset) {

            if (isUnderTouch && status==PtrFrameLayout.PTR_STATUS_PREPARE) {
                load_text.setText("上拉加载");
            }
        } else if (currentPos>mOffset && lastPos<=mOffset) {
            if (isUnderTouch && status==PtrFrameLayout.PTR_STATUS_PREPARE) {
               load_text.setText("松开加载");
            }
        }
    }
}
