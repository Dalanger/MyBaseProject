package com.dl.common.manager;

import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.ConnectException;
import java.text.ParseException;

import retrofit2.HttpException;

/**
 * created by dalang at 2018/11/7
 */
public class ExceptionManager {

    public static String handleException(Throwable e) {


        if (e instanceof HttpException) {


            return "网络错误";
        }
        if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {

            return "解析错误";

        } else if (e instanceof ConnectException) {

            return "连接错误";

        } else {

            return "请求失败";          //未知错误显示不好看 通知用户请求失败

        }
    }
}
