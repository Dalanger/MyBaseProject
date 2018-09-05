package com.dl.common.manager;


import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dl.common.R;

/**
 * created by dalang at 2018/9/1
 *
 * Glide图片加载管理器
 */
public class GlideManager {

    //----------------------------------------------------------加载头像------------------------------------------------------//
    public static void loadHead(Context context, Object path, ImageView targetView) {
        Glide.with(context).load(path)
                .asBitmap()
                .error(R.mipmap.user_default_img).into(targetView);
    }


    public static void loadHead(Context context, Object path, ImageView targetView,int errorRes) {
        Glide.with(context).load(path)
                .asBitmap()
                .error(errorRes).into(targetView);
    }

    //-----------------------------------------------加载图片 ------------------------------------------------------//

    //比例1:1
    public static void loadImage1_1(Context context, Object path, ImageView targetView) {
        Glide.with(context).load(path)
                .asBitmap()
                .placeholder(R.mipmap.glide_place_1_1)
                .error(R.mipmap.glide_error_1_1).into(targetView);
    }



    //比例4:3
    public static void loadImage4_3(Context context, Object path, ImageView targetView) {
        Glide.with(context).load(path)
                .asBitmap()
                .placeholder(R.mipmap.glide_place_4_3)
                .error(R.mipmap.glide_error_4_3).into(targetView);
    }


    //比例16:9
    public static void loadImage16_9(Context context, Object path, ImageView targetView) {
        Glide.with(context).load(path)
                .asBitmap()
                .placeholder(R.mipmap.glide_place_16_9)
                .error(R.mipmap.glide_error_16_9).into(targetView);
    }
}
