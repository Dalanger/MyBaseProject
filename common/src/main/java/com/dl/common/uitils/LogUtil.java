package com.dl.common.uitils;

import android.util.Log;

/**
 * created by dalang at 2018/8/30
 * 自定义的日志工具类
 */
public class LogUtil {
    public static final int VERBOSE=1;
    public static final int DEBUG=2;
    public static final int INFO=3;
    public static final int WARN=4;
    public static final int ERROR=5;
    public static final int NOTHING=6;
    public static final String TAG="dalang";

    public static  int level=DEBUG; //调试debug  发布时关闭log ->Nothing


    public static void v( String msg) {
        if (level <= VERBOSE) {
            Log.v(TAG, msg);
        }
    }

    public static void d(String msg) {
        if (level <= DEBUG) {
            Log.d(TAG, msg);
        }
    }

    public static void i(String msg) {
        if (level <= INFO) {
            Log.i(TAG, msg);
        }
    }

    public static void w(String msg) {
        if (level <= WARN) {
            Log.w(TAG, msg);
        }
    }

    public static void e(String msg) {
        if (level <= ERROR) {
            Log.e(TAG, msg);
        }
    }

}
