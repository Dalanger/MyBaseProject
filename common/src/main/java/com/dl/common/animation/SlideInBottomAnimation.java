package com.dl.common.animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;

/**
 * Created by dalang on 2018/9/4.
 *
 * 底部滑入动画
 */
public class SlideInBottomAnimation implements BaseAnimation {
    @Override
    public Animator[] getAnimators(View view) {
        return new Animator[]{
                ObjectAnimator.ofFloat(view, "translationY", view.getMeasuredHeight(), 0)
        };
    }
}
