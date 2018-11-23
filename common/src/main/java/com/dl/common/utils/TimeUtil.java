package com.dl.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * created by dalang at 2018/9/1
 * <p>
 * 时间相关处理工具类
 */
public class TimeUtil {


    public static final String DEFAULT_TIME_PATTERN = "yyyy-MM-dd HH:mm";
    /**
     * 日期格式：yyyy-MM-dd HH:mm:ss
     **/
    public static final String DF_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    /**
     * 日期格式：yyyy-MM-dd HH:mm
     **/
    public static final String DF_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";

    /**
     * 日期格式：yyyy-MM-dd
     **/
    public static final String DF_YYYY_MM_DD = "yyyy-MM-dd";

    /**
     * 日期格式：HH:mm:ss
     **/
    public static final String DF_HH_MM_SS = "HH:mm:ss";

    /**
     * 日期格式：HH:mm
     **/
    public static final String DF_HH_MM = "HH:mm";

    private final static long minute = 60 * 1000;// 1分钟
    private final static long halfHour = 30 * minute;// 半小时
    private final static long hour = 60 * minute;// 1小时
    private final static long day = 24 * hour;// 1天
    private final static long month = 31 * day;// 月
    private final static long year = 12 * month;// 年

    /**
     * 星期格式
     */
    public static final String WEEK_PATTERN = "EEEE";//星期X
    public static final String WEEK_PATTERN_1 = "E"; //周X


    /**
     * 格式化发布时间 类似微信朋友圈
     * 距离当前时间几分钟前，几小时前，几天前
     *
     * @return
     */
    public static String timeToWxTime(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_TIME_PATTERN);
        try {
            Date startDate = sdf.parse(time);

            Date endDate = new Date();
            long diff = endDate.getTime() - startDate.getTime();
            long r = 0;
            if (diff > year) {
                r = (diff / year);
                return r + "年前";
            }
            if (diff > month) {
                r = (diff / month);
                return r + "个月前";
            }
            if (diff > day) {
                r = (diff / day);
                if (r < 2) {
                    return "昨天";
                }
                return r + "天前";
            }
            if (diff > hour) {
                r = (diff / hour);
                return r + "个小时前";
            }
            if (diff > minute) {
                r = (diff / minute);
                return r + "分钟前";
            }
            return "刚刚";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "时间格式化错误";
    }

    /**
     * 当前时间转默认时间格式
     * @return
     */

    public static String getCurrentTimeInString() {

        return timeFormat(System.currentTimeMillis());

    }


    /**
     * 时间戳转可选时间格式
     *
     * @param date
     * @param formatPattern
     * @return
     */

    public static String timeFormat(Date date, String formatPattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_TIME_PATTERN);
        return sdf.format(date);

    }

    /**
     * 时间戳转默认格式
     *
     * @param timeInMillis
     * @return
     */

    public static String timeFormat(long timeInMillis) {
        return timeFormat(new Date(timeInMillis), DEFAULT_TIME_PATTERN);
    }

    /**
     *  两个时间格式间转换
     * @param oldString
     * @param oldPattern
     * @param replacePattern
     * @return
     */
    public static String timeFormat(String oldString,String oldPattern,String replacePattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(oldPattern);
        Date date=null;
        try {
             date= dateFormat.parse(oldString);// 获取现在的时间
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeFormat(date,replacePattern);
    }

    /**
     * 两个时间格式间转换 ---默认时间格式
     * @param oldString
     * @param oldPattern
     * @return
     */
    public static String timeFormat(String oldString,String oldPattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(oldPattern);
        Date date=null;
        try {
            date= dateFormat.parse(oldString);// 获取现在的时间
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeFormat(date,DEFAULT_TIME_PATTERN);
    }


    /**
     * 两个日期的时间差
     * @param bDate
     * @param eDate
     * @return 单位秒
     */
    public static long getInterval(String bDate, String eDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DEFAULT_TIME_PATTERN);
        long interval = 0;
        try {
            Date endTime = dateFormat.parse(eDate);// 获取现在的时间
            Date beginTime = dateFormat.parse(bDate);
            interval =(endTime.getTime() - beginTime.getTime());// 时间差
            // 单位秒
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return interval;
    }



    /**
     * 某日期和当前时间的时间差
     * @param bDate
     * @return 单位秒
     */
    public static long getInterval(String bDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DEFAULT_TIME_PATTERN);
        long interval = 0;
        try {
            Date date=new Date();
            Date beginTime = dateFormat.parse(bDate);
            interval =(date.getTime() - beginTime.getTime());// 时间差
            // 单位秒
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return interval;
    }


    /**
     * 获取剩余时间---多用于计算剩余支付时间或配送时间
     * @param timeAll  总时长
     * @param timePass 已过时长
     * @return
     */
    public static long getRemainTime(long timeAll,long timePass) {
        long remainTime=0;
        if (timeAll>timePass) {
            remainTime=timeAll-timePass;//秒
        }
        return remainTime;
    }


}
