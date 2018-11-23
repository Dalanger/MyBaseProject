package com.dl.common.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;

import com.jph.takephoto.uitl.TUriParse;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

    /**
     * File Uri转化 适配7.0后
     * @param context
     * @param file
     * @return
     */
    public static Uri getUriForFile(Context context, File file) {

        return TUriParse.getUriForFile(context,file);
    }


//-------------------------------------------高斯模糊------------------------------------------------------//


    /**
     * 根据imagepath获取bitmap
     */
    /**
     * 得到本地或者网络上的bitmap url - 网络或者本地图片的绝对路径,比如:
     * <p>
     * A.网络路径: url="http://" ;
     * <p>
     * B.本地路径:url="file://";
     * <p>
     * C.支持的图片格式 ,png, jpg,bmp,gif等等
     *
     * @param url
     * @return
     */
    public static int IO_BUFFER_SIZE = 2 * 1024;


    public static Bitmap getBitmapToBlur(String url, int scaleRatio) {

        int blurRadius = 5;//通常设置为8就行。
        if (scaleRatio <= 0) {
            scaleRatio = 10;
        }
        Bitmap originBitmap = null;
        InputStream in = null;
        BufferedOutputStream out = null;
        try {
            in = new BufferedInputStream(new URL(url).openStream(), IO_BUFFER_SIZE);
            final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
            out = new BufferedOutputStream(dataStream, IO_BUFFER_SIZE);
            copy(in, out);
            out.flush();
            byte[] data = dataStream.toByteArray();
            originBitmap = BitmapFactory.decodeByteArray(data, 0, data.length);


            Bitmap scaledBitmap = Bitmap.createScaledBitmap(originBitmap,
                    originBitmap.getWidth() / scaleRatio,
                    originBitmap.getHeight() / scaleRatio,
                    false);
            Bitmap blurBitmap = doBlur(scaledBitmap, blurRadius);
            return blurBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    private static void copy(InputStream in, OutputStream out)
            throws IOException {
        byte[] b = new byte[IO_BUFFER_SIZE];
        int read;
        while ((read = in.read(b)) != -1) {
            out.write(b, 0, read);
        }
    }




    //    把本地图片毛玻璃化
    public static Bitmap bitmapToBlur(Bitmap originBitmap, int scaleRatio) {
        //        int scaleRatio = 10;
        // 增大scaleRatio缩放比，使用一样更小的bitmap去虚化可以到更好的得模糊效果，而且有利于占用内存的减小；
        int blurRadius = 5;
        //增大blurRadius，可以得到更高程度的虚化，不过会导致CPU更加intensive


       /* 其中前三个参数很明显，其中宽高我们可以选择为原图尺寸的1/10；
        第四个filter是指缩放的效果，filter为true则会得到一个边缘平滑的bitmap，
        反之，则会得到边缘锯齿、pixelrelated的bitmap。
        这里我们要对缩放的图片进行虚化，所以无所谓边缘效果，filter=false。*/
        if (scaleRatio <= 0) {
            scaleRatio = 10;
        }
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(originBitmap,
                originBitmap.getWidth() / scaleRatio,
                originBitmap.getHeight() / scaleRatio,
                false);
        Bitmap blurBitmap = doBlur(scaledBitmap, blurRadius);
        return blurBitmap;
    }







    /**
     * 图片毛玻璃效果
     * @param sentBitmap
     * @param radius
//     * @param canReuseInBitmap
     * @return
     */
    public static Bitmap doBlur(Bitmap sentBitmap, int radius) {
        Bitmap bitmap;
//        if (canReuseInBitmap) {
            bitmap = sentBitmap;
//        } else {
//            bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);
//        }

        if (radius < 1) {
            return (null);
        }

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        int[] pix = new int[w * h];
        bitmap.getPixels(pix, 0, w, 0, 0, w, h);

        int wm = w - 1;
        int hm = h - 1;
        int wh = w * h;
        int div = radius + radius + 1;

        int r[] = new int[wh];
        int g[] = new int[wh];
        int b[] = new int[wh];
        int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
        int vmin[] = new int[Math.max(w, h)];

        int divsum = (div + 1) >> 1;
        divsum *= divsum;
        int dv[] = new int[256 * divsum];
        for (i = 0; i < 256 * divsum; i++) {
            dv[i] = (i / divsum);
        }

        yw = yi = 0;

        int[][] stack = new int[div][3];
        int stackpointer;
        int stackstart;
        int[] sir;
        int rbs;
        int r1 = radius + 1;
        int routsum, goutsum, boutsum;
        int rinsum, ginsum, binsum;

        for (y = 0; y < h; y++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            for (i = -radius; i <= radius; i++) {
                p = pix[yi + Math.min(wm, Math.max(i, 0))];
                sir = stack[i + radius];
                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);
                rbs = r1 - Math.abs(i);
                rsum += sir[0] * rbs;
                gsum += sir[1] * rbs;
                bsum += sir[2] * rbs;
                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }
            }
            stackpointer = radius;

            for (x = 0; x < w; x++) {

                r[yi] = dv[rsum];
                g[yi] = dv[gsum];
                b[yi] = dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (y == 0) {
                    vmin[x] = Math.min(x + radius + 1, wm);
                }
                p = pix[yw + vmin[x]];

                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[(stackpointer) % div];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi++;
            }
            yw += w;
        }
        for (x = 0; x < w; x++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            yp = -radius * w;
            for (i = -radius; i <= radius; i++) {
                yi = Math.max(0, yp) + x;

                sir = stack[i + radius];

                sir[0] = r[yi];
                sir[1] = g[yi];
                sir[2] = b[yi];

                rbs = r1 - Math.abs(i);

                rsum += r[yi] * rbs;
                gsum += g[yi] * rbs;
                bsum += b[yi] * rbs;

                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }

                if (i < hm) {
                    yp += w;
                }
            }
            yi = x;
            stackpointer = radius;
            for (y = 0; y < h; y++) {
                // Preserve alpha channel: ( 0xff000000 & pix[yi] )
                pix[yi] = (0xff000000 & pix[yi]) | (dv[rsum] << 16)
                        | (dv[gsum] << 8) | dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (x == 0) {
                    vmin[y] = Math.min(y + r1, hm) * w;
                }
                p = x + vmin[y];

                sir[0] = r[p];
                sir[1] = g[p];
                sir[2] = b[p];

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[stackpointer];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi += w;
            }
        }

        bitmap.setPixels(pix, 0, w, 0, 0, w, h);

        return (bitmap);
    }



    //-------------------------------------------截图分享------------------------------------------------------//

    //保存图片到手机
    public static String saveImage(Activity mActivity, Bitmap bitmap, String imgName) {

        String dir =FileUtil.IMG_FILE_PATH;
        File file = new File(dir);
        if (!file.exists()) {
            file.mkdir();
        }

        String imagePath = dir + imgName + ".jpg";
        File imageFile = new File(imagePath);
        if (!imageFile.exists()) {
            try {
                imageFile.createNewFile();
            } catch (IOException e) {
                Log.d("test", e.getMessage());
            }
        }

        saveBitmap(bitmap, imagePath);
        insertImage(mActivity, imagePath);

        return imagePath;
    }

    /**
     * 把bitmap保存到文件中
     *
     * @param bitmap
     * @param filePath
     */
    public static void saveBitmap(Bitmap bitmap, String filePath) {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(filePath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
        } catch (Exception e) {
            Log.d("test", e.getMessage());
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                Log.d("test", e.getMessage());
            }
        }
    }

    //广播插入相册
    private static MediaScannerConnection sMediaScannerConnection;

    public static void insertImage(Context context, final String filePath) {
        sMediaScannerConnection = new MediaScannerConnection(context, new MediaScannerConnection.MediaScannerConnectionClient() {
            @Override
            public void onMediaScannerConnected() {
                Log.d("test", "scannerConnected, scan local path:" + filePath);
                sMediaScannerConnection.scanFile(filePath, "image/*");
            }

            @Override
            public void onScanCompleted(String path, Uri uri) {
                Log.d("test", "scan complete");
                sMediaScannerConnection.disconnect();
            }
        });
        sMediaScannerConnection.connect();
    }


    /**
     * 截取除了导航栏之外的整个屏幕
     */
    private Bitmap screenShotWholeScreen(Activity activity) {
        View dView = activity.getWindow().getDecorView();
        dView.setDrawingCacheEnabled(true);
        dView.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(dView.getDrawingCache());
        return bitmap;
    }


    /**
     * 使用View的缓存功能，截取指定区域的View
     */
    private Bitmap screenShotView(View view) {
        //开启缓存功能
        view.setDrawingCacheEnabled(true);
        //创建缓存
        view.buildDrawingCache();
        //获取缓存Bitmap
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        return bitmap;
    }



    /**
     * 截取scrollview的屏幕
     **/
    public static Bitmap getScrollViewBitmap(ScrollView scrollView) {
        int h = 0;
        Bitmap bitmap;
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            h += scrollView.getChildAt(i).getHeight();
        }
        // 创建对应大小的bitmap
        bitmap = Bitmap.createBitmap(scrollView.getWidth(), h,
                Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        scrollView.draw(canvas);
        return bitmap;
    }

}
