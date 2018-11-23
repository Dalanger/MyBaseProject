package com.dl.common.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.File;

/**
 * created by dalang at 2018/8/30
 * 本地存储工具类
 */
public class PreferencesUtil {
    //根据项目名称替换
    public static final String PREFERENCES_NAME = "dalang";

    public static void putString(Context context, String key, String value) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getString(Context context, String key) {
        return getString(context, key, null);
    }

    public static String getString(Context context, String key, String defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE );
        return settings.getString(key, defaultValue);
    }


    public static void putBoolean(Context context, String key, boolean value) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static boolean getBoolean(Context context, String key) {
        return getBoolean(context, key, false);
    }

    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        return sp.getBoolean(key, defaultValue);
    }


    public static void putInt(Context context, String key, int value) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static int getInt(Context context, String key) {
        return getInt(context, key, 404);
    }

    public static int getInt(Context context, String key, int defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        return sp.getInt(key, 0);
    }

    public static void clearData(Context context) {
        SharedPreferences sp = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        sp.edit().clear().commit();
        cleanSharedPreference(context);

    }

    public static void cleanSharedPreference(Context context) {
        deleteFilesByDirectory(new File("/data/data/"
                + context.getPackageName() + "/shared_prefs"));
    }

    /**
     * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理 * * @param directory
     */
    private static void deleteFilesByDirectory(File directory) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            for (File item : directory.listFiles()) {
                item.delete();
            }
        }
    }
}
