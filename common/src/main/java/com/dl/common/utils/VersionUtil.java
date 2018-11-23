package com.dl.common.utils;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * created by dalang at 2018/8/30
 * 版本控制工具类  后期补充静默安装等方法
 */
public class VersionUtil {
    //判断手机的app版本是否需要强制更新
    public static boolean compareVersion(String minVersion, String version) {
        String min[] = minVersion.split("\\.");
        String ver[] = version.split("\\.");
        int m1 = Integer.parseInt(min[0]);
        int m2 = Integer.parseInt(min[1]);
        int m3 = Integer.parseInt(min[2]);
        int v1 = Integer.parseInt(ver[0]);
        int v2 = Integer.parseInt(ver[1]);
        int v3 = Integer.parseInt(ver[2]);

        if (m1 > v1) {
            return true;
        } else if (v1 > m1) {
            return false;
        } else {
            if (v2 < m2) {
                return true;
            } else if (v2 >m2) {
                return false;
            } else {
                if (v3 < m3) {
                    return true;
                } else if (v3 >= m3) {
                    return false;
                }
            }
        }

        return false;
    }


    public static String getVersion(Activity activity) {
        try {
            PackageManager manager = activity.getPackageManager();
            PackageInfo info = manager.getPackageInfo(activity.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
