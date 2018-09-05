package com.dl.common.animation;

import android.animation.Animator;
import android.view.View;

/**
 * Created by dalang on 2018/9/4.
 * 属性动画 父类
 * 用于recyclerview item的动效
 */

public interface BaseAnimation {
    Animator[] getAnimators(View view);
}
