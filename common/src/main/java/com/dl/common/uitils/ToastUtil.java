package com.dl.common.uitils;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.CheckResult;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dl.common.R;

/**
 * created by dalang at 2018/8/30
 *
 * toast 工具类
 */
public class ToastUtil {

    private static Context context;
    /**
     * Toast
     */
    private static Toast currentToast;


    /**
     * 初始化工具类
     *
     * @param context 上下文
     */
    public static void init(Context context) {

        ToastUtil.context = context.getApplicationContext();

    }
    /**
     * 在某种获取不到 Context 的情况下，即可以使用才方法获取 Context
     * <p>
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    public static Context getContext() {
        if (context != null) return context;
        throw new NullPointerException("请先调用init()方法");
    }
//*******************************************外调方法********************************************//

    public static void normal(@NonNull String message) {
        custom(getContext(), message, Toast.LENGTH_SHORT,  false).show();
    }

    public static void warn(@NonNull String message) {
        custom(getContext(), message, Toast.LENGTH_SHORT,  true).show();
    }

    public static void success(@NonNull String message) {
        customCenter(getContext(), message, Toast.LENGTH_SHORT,  true).show();
    }

    public static void error(@NonNull String message) {
        customCenter(getContext(), message, Toast.LENGTH_SHORT,  false).show();
    }

//*******************************************内需方法********************************************//


    @CheckResult
    private static Toast custom(@NonNull Context context, @NonNull String message, int duration, boolean withIcon) {
        if (currentToast == null) {
            currentToast = new Toast(context);
        }
        currentToast.setGravity(Gravity.CENTER, 0, 0);
        final View toastLayout = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.toast_layout, null);
        final ImageView toastIcon = (ImageView) toastLayout.findViewById(R.id.toast_icon);
        final TextView toastTextView = (TextView) toastLayout.findViewById(R.id.toast_text);

        setBackground(toastLayout, getDrawable(context, R.mipmap.toast_bg));

        if (withIcon) {
            toastIcon.setVisibility(View.VISIBLE);
            setBackground(toastIcon, getDrawable(context, R.mipmap.toast_warn));
        } else
            toastIcon.setVisibility(View.GONE);

        toastTextView.setText(message);
        toastTextView.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        currentToast.setView(toastLayout);
        currentToast.setDuration(duration);
        return currentToast;
    }


    @CheckResult
    private static Toast customCenter(@NonNull Context context, @NonNull String message ,int duration, boolean isSuccess) {
        if (currentToast == null) {
            currentToast = new Toast(context);
        }
        currentToast.setGravity(Gravity.CENTER, 0, 0);
        final View toastLayout = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.toast_center_layout, null);
        final ImageView toastIcon = (ImageView) toastLayout.findViewById(R.id.toast_icon);
        final TextView toastTextView = (TextView) toastLayout.findViewById(R.id.toast_text);
        setBackground(toastLayout, getDrawable(context, R.mipmap.toast_bg_center));

        if (isSuccess)
            setBackground(toastIcon, getDrawable(context, R.mipmap.toast_success));
        else
            setBackground(toastIcon, getDrawable(context, R.mipmap.toast_error));

        toastTextView.setText(message);
        toastTextView.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        currentToast.setView(toastLayout);
        currentToast.setDuration(duration);
        return currentToast;
    }




    //******************************************系统 Toast 替代方法***************************************

    public static final void setBackground(@NonNull View view, Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            view.setBackground(drawable);
        else
            view.setBackgroundDrawable(drawable);
    }

    public static final Drawable getDrawable(@NonNull Context context, @DrawableRes int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            return context.getDrawable(id);
        else
            return context.getResources().getDrawable(id);
    }


}
