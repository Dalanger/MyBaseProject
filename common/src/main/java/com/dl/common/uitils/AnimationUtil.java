package com.dl.common.uitils;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;

import com.dl.common.R;

/**
 * created by dalang at 2018/9/4
 *
 * view动画工具类  补间动画
 *
 * 用于任意view
 */
public class AnimationUtil {


    /**
     * 摇晃的动画
     * @param counts 左右摇摆的次数
     * @return
     */
    public static Animation shakeAnimation(int counts) {
        Animation translateAnimation = new TranslateAnimation(5, 0, 0, 0);
        //设置一个循环加速器，使用传入的次数就会出现摆动的效果。
        translateAnimation.setInterpolator(new CycleInterpolator(counts));
        translateAnimation.setDuration(500);
        return translateAnimation;
    }



    public static void goneAnimation(View view) {
        TranslateAnimation hideAnim = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 1.0f);
        hideAnim.setDuration(500);
        view.startAnimation(hideAnim);
        view.setVisibility(View.GONE);
    }


    public static void visibleAnimation(View view) {
        //弹出底部
        TranslateAnimation showAnim = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f);
        showAnim.setDuration(500);
        view.startAnimation(showAnim);
        view.setVisibility(View.VISIBLE);
    }



    /**
     * 缩放变大动画
     *
     * @param context
     * @param view 目标view
     */
    public static void startScaleInAnim(Context context, View view) {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.anim_scale_in);
        if (view != null)
            view.startAnimation(animation);
    }

    /**
     * 缩放缩小动画
     *
     * @param context
     * @param view 目标view
     */
    public static void startScaleOutAnim(Context context, View view) {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.anim_scale_out);
        if (view != null)
            view.startAnimation(animation);
    }



    /**
     * 旋转动画
     *
     * @param context
     * @param view 目标view
     */
    public static void startRotateAnim(Context context, View view) {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.animation_rotate);
        if (view != null)
            view.startAnimation(animation);
    }

}
