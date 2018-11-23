package com.dl.common.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

import com.mylhyl.acp.Acp;
import com.mylhyl.acp.AcpListener;
import com.mylhyl.acp.AcpOptions;

import java.util.List;

import static android.content.Context.TELEPHONY_SERVICE;

/**
 * Created by dalang on 2018/8/30.
 * 所有接口  不同项目需替换
 */
public class PhoneUtil {

    public static int getScreenWidth(Activity activity) {

        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    public static int getScreenHeight(Activity activity) {
        if (activity == null) return 0;

        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.heightPixels;
    }


    /**
     * 将px值转换为dp值
     *
     * @param pxValue
     * @param pxValue
     *            （DisplayMetrics类中属性density）
     * @return
     */
    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将dp值转换为px值，
     *
     * @param dipValue
     * @param dipValue
     *            （DisplayMetrics类中属性density）
     * @return
     */
    public static int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 获取手机DPI
     * @param context
     * @return
     */
    public static int getPhoneDPI(Context context) {
        DisplayMetrics metric = context.getResources().getDisplayMetrics();
        int densityDpi = metric.densityDpi;  // 屏幕密度DPI（120 / 160 / 240,小米4的是480）
        return densityDpi;
    }


    /**
     * @throws ，有SIM卡则调用付费SDK
     * @author CX-
     * @判断 是否含有sim卡
     **/
    private boolean readSIMCard(Activity activity) {

        TelephonyManager manager = (TelephonyManager) activity.getSystemService(TELEPHONY_SERVICE);// 取得相关系统服务
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        String imsi = manager.getSubscriberId(); // 取出IMSI

        if (imsi == null || imsi.length() <= 0) {
            return false;
        } else {
            return true;

        }

    }

    public static int getStatusBarHeight(Context context) {
        // 获得状态栏高度
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId);
    }

    public void callPhone(final Activity activity, final String phone) {
        Acp.getInstance(activity).request(new AcpOptions.Builder()
                        .setPermissions(Manifest.permission.CALL_PHONE)
                        .build(),
                new AcpListener() {
                    @Override
                    public void onGranted() {
                        if (readSIMCard(activity)) {

                            Intent intentPhone = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
                            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                return;
                            }
                            activity.startActivity(intentPhone);
                        } else {
                            ToastUtil.warn("请插入SIM卡");
                        }
                    }

                    @Override
                    public void onDenied(List<String> permissions) {
                    }
                });
    }
}
