package com.dl.common.uitils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.util.Base64;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * created by dalang at 2018/9/1
 *
 * 图片 工具类
 */
public class ImgUtil {

    /**
     * @param urlString
     * @return Bitmap
     * 根据图片url获取图片对象
     */
    public static Bitmap url2Bitmap(String urlString) {
        Bitmap bitmap;
        InputStream in = null;
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            in = new BufferedInputStream(connection.getInputStream());
            bitmap = BitmapFactory.decodeStream(in);
            connection.disconnect();
            in.close();
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    /**
     * 将Bitmap转换成Base64字符串 压缩为100kb
     * @param
     * @return
     */
    public static String Bitmap2Base64(Bitmap bitmap){
        int maxSize=100;
        int options=100;
        int ratio = getRatioSize(bitmap.getWidth(), bitmap.getHeight());
        Bitmap result = Bitmap.createBitmap(bitmap.getWidth() / ratio, bitmap.getHeight() /ratio, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        Rect rect = new Rect(0, 0, bitmap.getWidth() / ratio, bitmap.getHeight() / ratio);
        canvas.drawBitmap(bitmap,null,rect,null);
        ByteArrayOutputStream bos=new ByteArrayOutputStream();
        result.compress(Bitmap.CompressFormat.JPEG, options, bos);
        while (bos.toByteArray().length / 1024 > maxSize) {
            bos.reset();
            options-=10;
            result.compress(Bitmap.CompressFormat.JPEG, options, bos);
        }
        byte[] bytes=bos.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }


    public static int getRatioSize(int bitWidth, int bitHeight) {
        // 图片最大分辨率
        int imageHeight = 1280;
        int imageWidth = 960;
        int ratio = 1;
        if (bitWidth > bitHeight && bitWidth > imageWidth) {
            ratio = bitWidth / imageWidth;
        } else if (bitWidth < bitHeight && bitHeight > imageHeight) {
            ratio = bitHeight / imageHeight;
        }
        if (ratio <= 0)
            ratio = 1;
        return ratio;
    }


    public static Uri getUriForFile(Context context, File file) {
        if (context == null || file == null) {
            throw new NullPointerException();
        }
        Uri uri;
        if (Build.VERSION.SDK_INT >= 24) {
            uri = FileProvider.getUriForFile(context.getApplicationContext(), context.getApplicationContext().getPackageName(), file);
        } else {
            uri = Uri.fromFile(file);
        }
        return uri;
    }


}
