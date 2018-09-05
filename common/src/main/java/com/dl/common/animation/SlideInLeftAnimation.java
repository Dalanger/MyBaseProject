package com.dl.common.animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;


/**
 * Created by dalang on 2018/9/4.
 *
 * 左侧滑入动画
 */
public class SlideInLeftAnimation implements BaseAnimation {
    @Override
    public Animator[] getAnimators(View view) {
        return new Animator[]{
                ObjectAnimator.ofFloat(view, "translationX", -view.getRootView().getWidth(), 0)
        };
    }
}