package com.dl.common.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;

/**
 * created by dalang at 2018/9/3
 * <p>
 * 数据处理 工具类
 */
public class DataUtil {

    //----------------------------------------------格式化卡号--------------------------------------------------------//

    /**
     * 隐藏手机中间4位号码
     *
     * @param mobile_phone 手机号码
     * @return 130****0000
     */
    public static String hideMobilePhone4(String mobile_phone) {
        if (mobile_phone.length() != 11) {
            return mobile_phone;
        }
        return mobile_phone.substring(0, 3) + "****" + mobile_phone.substring(7, 11);
    }


    /**
     * 格式化银行卡 加*
     *
     * @param cardNo 银行卡
     * @return 3749 **** **** 330
     */
    public static String formatCard(String cardNo) {
        if (cardNo.length() < 9) {
            return cardNo;
        }
        String card = "";
        card = cardNo.substring(0, 4) + " **** **** ";
        card += cardNo.substring(cardNo.length() - 4);
        return card;
    }


    /**
     * 取银行卡前四位
     *
     * @param cardNo
     * @return
     */
    public static String formatCardStart4(String cardNo) {
        if (cardNo.length() < 5) {
            return cardNo;
        }
        return cardNo.substring(0, 4);
    }

    /**
     * 取银行卡/手机号后四位
     *
     * @param cardNo
     * @return
     */
    public static String formatCardEnd4(String cardNo) {
        if (cardNo.length() < 5) {
            return cardNo;
        }
        return cardNo.substring(cardNo.length() - 4);
    }

    //----------------------------------------------格式化字符串--------------------------------------------------------//

    /**
     * 字符串转换成double ,转换失败将会 return 0
     *
     * @param str 字符串
     * @return
     */
    public static double stringToDouble(String str) {
        if (TextUtils.isEmpty(str)) {
            return 0;
        } else {
            try {
                return Double.parseDouble(str);
            } catch (NumberFormatException e) {
                return 0;
            }
        }
    }


    /**
     * 10
     * 将字符串格式化为带两位小数的字符串
     *
     * @param str 字符串
     * @return 10.00
     */
    public static String format2Decimals(String str) {
        return parseMoney("0.00",stringToDouble(str));

    }

    /**
     * 10  10.01
     * 将字符串格式化  整数则转整数  有小数则转化为最多两位小数
     *
     * @param str 字符串
     * @return 10  10.01
     */

    public static String format2DecimalsWithout(String str) {
        return parseMoney("0.##",stringToDouble(str));
    }


    private static String parseMoney(String pattern, double money) {
        DecimalFormat df = new DecimalFormat(pattern);
        return df.format(money);
    }


    //----------------------------------------------其他工具--------------------------------------------------------//

    public static String getMD5(String info) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            if (!TextUtils.isEmpty(info)) {
                md5.update(info.getBytes("UTF-8"));
            }
            byte[] encryption = md5.digest();

            StringBuffer strBuf = new StringBuffer();
            for (int i = 0; i < encryption.length; i++) {
                if (Integer.toHexString(0xff & encryption[i]).length() == 1) {
                    strBuf.append("0").append(Integer.toHexString(0xff & encryption[i]));
                } else {
                    strBuf.append(Integer.toHexString(0xff & encryption[i]));
                }
            }

            return strBuf.toString();
        } catch (NoSuchAlgorithmException e) {
            return "";
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    public static void copyToClipboard(Context context, String text) {
        ClipboardManager systemService = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        systemService.setPrimaryClip(ClipData.newPlainText("text", text));
        ToastUtil.success("已复制到粘贴板");
    }
}
