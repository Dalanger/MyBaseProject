package com.dl.common.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * created by dalang at 2018/8/30
 * 文件工具类
 */
public class FileUtil {
    private static final String APP_NAME = "dalang";
    //保存路径
    public static final String DATA_FILE_PATH = FileUtil.getRecordDir().toString() + "/";
    public static final String IMG_FILE_PATH = FileUtil.getImgDir().toString() + "/";
    public static final String VIDEO_FILE_PATH = FileUtil.getNetVideoDir().toString() + "/";
    //图片后缀
    public static final String JPG_SUFFIX = ".jpg";


    public static File getRecordDir() {
        File appDir = new File(Environment.getExternalStorageDirectory(), APP_NAME);
        if (appDir == null || !appDir.isDirectory()) {
            appDir.mkdir();
        }

        File dataDir = new File(appDir, "data");
        if (dataDir == null || !dataDir.isDirectory()) {
            dataDir.mkdir();
        }

        return dataDir;
    }


    public static File getImgDir() {
        File appDir = new File(Environment.getExternalStorageDirectory(), APP_NAME);
        if (appDir == null || !appDir.isDirectory()) {
            appDir.mkdir();
        }

        File dataDir = new File(appDir, "img");
        if (dataDir == null || !dataDir.isDirectory()) {
            dataDir.mkdir();
        }

        return dataDir;
    }


    public static File getNetVideoDir() {
        File appDir = new File(Environment.getExternalStorageDirectory(), APP_NAME);
        if (appDir == null || !appDir.isDirectory()) {
            appDir.mkdir();
        }

        File dataDir = new File(appDir, "video");
        if (dataDir == null || !dataDir.isDirectory()) {
            dataDir.mkdir();
        }

        return dataDir;
    }


    public static long getFileSizes(File f) {
        long size = 0;
        File fList[] = f.listFiles();
        if (fList == null) {
            return 0;
        }

        if (fList != null) {
            for (int i = 0; i < fList.length; i++) {
                if (fList[i].isDirectory()) {
                    size = size + getFileSizes(fList[i]);
                } else {
                    size = size + getFileSize(fList[i]);
                }
            }
        }
        return size;
    }

    public static long getFileSize(File file) {
        long size = 0;
        if (file.exists()) {
            FileInputStream fis = null;

            try {
                fis = new FileInputStream(file);
                size = fis.available();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return size;
    }

    //清楚缓存
    public static boolean clearAllCache(Context context) {
        File externalCacheDir = context.getExternalCacheDir();
        File cacheDir = context.getCacheDir();
        boolean result = false;
        if (cacheDir != null) {
            result = delAllFile(cacheDir.getAbsolutePath());
        }
        if (externalCacheDir != null) {
            result = delAllFile(externalCacheDir.getAbsolutePath()) || result ? true : false;
        }
        return result;
    }

    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);
                flag = true;
            }
        }
        return flag;
    }

    //获取缓存大小
    public static long getCacheSize(Context context) {
        File file1 = context.getCacheDir();
        File file2 = context.getExternalCacheDir();
        long fileSizes = FileUtil.getFileSizes(file1);
        long fileSizes2 = FileUtil.getFileSizes(file2);
        return fileSizes + fileSizes2;
    }


    /**
     * 格式化缓存大小单位
     *
     * @param size
     * @return
     */
    public static String getFormatSize(long size) {
        double kiloByte = size / 1024;


        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }
}
