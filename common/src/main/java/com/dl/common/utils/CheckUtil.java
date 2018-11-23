package com.dl.common.utils;

import android.text.TextUtils;

import java.util.regex.Pattern;

/**
 * created by dalang at 2018/9/3
 *
 * 检验工具类
 */
public class CheckUtil {


    //-----------------------------------------------正则表达式------------------------------------------------------------//
    /**
     * 正则表达式:验证手机号
     */
    public static final String REGEX_MOBILE="[1][3456789]\\d{9}";

    /**
     * 正则：手机号（精确）
     移动号段：

     134 135 136 137 138 139 147 148 150 151 152 157 158 159 172 178 182 183 184 187 188 198
     联通号段：

     130 131 132 145 146 155 156 166 171 175 176 185 186
     电信号段：

     133 149 153 173 174 177 180 181 189 199
     虚拟运营商:

     170
     */
    public static final String REGEX_MOBILE_EXACT  = "^((13[0-9])|(14[5,9])|(15[0-3,5-9])|(166)|(17[0,3,5-8])|(18[0-9])|(19[8-9]))\\d{8}$";


    /**
     * 正则表达式:验证身份证
     */
    public static final String REGEX_ID_CARD = "(^\\d{15}$)|(^\\d{17}([0-9]|X)$)";


    /**
     * 正则：汉字
     */
    public static final String REGEX_ZH= "^[\\u4e00-\\u9fa5]+$";



    /**
     * 匹配由数字、26个英文字母或者下划线组成的字符串 ^\w+$
     */
    public static final String REGEX_ENG_NUM_="^\\w+$";
    /**
     * 匹配由数字和26个英文字母组成的字符串 ^[A-Za-z0-9]+$
     */
    public static final String REGEX_ENG_NUM="^[A-Za-z0-9]+";
    /**
     * 匹配由26个英文字母组成的字符串  ^[A-Za-z]+$
     */
    public static final String REGEX_ENG="^[A-Za-z]+$";


    /**
     * 匹配由纯数字  ^[0-9]+$
     */
    public static final String REGEX_NUM="^[0-9]+$";

    /**
     * 过滤特殊字符串正则
     * regEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
     */
    public static final String STR_SPECIAL="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";



    //-----------------------------------------------常用的检验------------------------------------------------------------//

    /**
     * 验证手机号
     * @param mobile
     * @return  true表示无问题
     */
    public static boolean checkMobile(String mobile) {
        if (TextUtils.isEmpty(mobile)) {
            ToastUtil.warn("手机号不能为空");
            return false;
        }
        if (!Pattern.matches(REGEX_MOBILE, mobile)) {
            ToastUtil.warn("请输入正确的手机号");
            return false;
        }
        return true;
    }

    /**
     * 验证密码
     * @param password
     * @return true表示无问题
     */
    public static boolean checkPassword(String password) {
        if (TextUtils.isEmpty(password)) {
            ToastUtil.warn("密码不能为空");
            return false;
        }
        if (password.length()<6) {
            ToastUtil.warn("密码不能6位");
            return false;
        }
        if (Pattern.matches(REGEX_NUM, password)) {
            ToastUtil.warn("密码不能为纯数字");
            return false;
        }

        return true;
    }

    /**
     * 验证面
     * @param password
     * @param password2
     * @return true表示无问题
     */
    public static boolean checkPassword(String password,String password2) {
        if (TextUtils.isEmpty(password)) {
            ToastUtil.warn("密码不能为空");
            return false;
        }
        if (password.length()<6) {
            ToastUtil.warn("密码不能小于6位");
            return false;
        }
        if (Pattern.matches(REGEX_NUM, password)) {
            ToastUtil.warn("密码不能为纯数字");
            return false;
        }
        if (!password.equals(password2)) {
            ToastUtil.warn("两次密码输入不一致");
            return false;
        }

        return true;
    }


    /**
     * 验证身份证
     * @param idCard
     * @return
     */
    public static boolean checkIdCard(String idCard) {

        if (TextUtils.isEmpty(idCard)) {
            ToastUtil.warn("身份证号不能为空");
            return false;
        }
        if (Pattern.matches(REGEX_ID_CARD, idCard)) {
            ToastUtil.warn("请输入正确的身份证号");
            return false;
        }

        return true;
    }

    /**
     * 验证验证码 暂时只判空 根据需求做其他处理
     * @param code
     * @return
     */
    public static boolean checkCode(String code) {
        if (TextUtils.isEmpty(code)) {
            ToastUtil.warn("验证码不能为空");
            return false;
        }
        return true;
    }


    /**
     * 验证车牌号
     *
     1.常规车牌号：仅允许以汉字开头，后面可录入六个字符，由大写英文字母和阿拉伯数字组成。如：粤B12345；
     2.武警车牌：允许前两位为大写英文字母，后面可录入五个或六个字符，由大写英文字母和阿拉伯数字组成，其中第三位可录汉字也可录大写英文字母及阿拉伯数字，第三位也可空，如：WJ警00081、WJ京1234J、WJ1234X。
     3.最后一个为汉字的车牌：允许以汉字开头，后面可录入六个字符，前五位字符，由大写英文字母和阿拉伯数字组成，而最后一个字符为汉字，汉字包括“挂”、“学”、“警”、“军”、“港”、“澳”。如：粤Z1234港。
     4.新军车牌：以两位为大写英文字母开头，后面以5位阿拉伯数字组成。如：BA12345。
     * @param carNumber
     * @return
     */
    public static boolean isCarNumberNO(String carNumber) {

        String carnumRegex = "^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[警京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼]{0,1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}$";

        return carNumber.matches(carnumRegex);
    }


    /**
     * 判断是否为银行卡
     * @param cardId
     * @return
     */
    public static boolean checkBankCard(String cardId) {

        if (TextUtils.isEmpty(cardId)) {
            ToastUtil.warn("银行卡号不能为空");
            return false;
        }
        char bit = getBankCardCheckCode(cardId.substring(0, cardId.length() - 1));
        if(bit == 'N'){
            ToastUtil.warn("请输入正确的银行卡号");
            return false;
        }

        if (!(cardId.charAt(cardId.length() - 1) == bit)) {
            ToastUtil.warn("请输入正确的银行卡号");
            return false;
        }

        return true;
    }

    /**
     * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
     * @param nonCheckCodeCardId
     * @return
     */
    private static char getBankCardCheckCode(String nonCheckCodeCardId){
        if(nonCheckCodeCardId == null || nonCheckCodeCardId.trim().length() == 0
                || !nonCheckCodeCardId.matches("\\d+")) {
            //如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeCardId.trim().toCharArray();
        int luhmSum = 0;
        for(int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if(j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        //这边+'0'，不是拼接，在Java和C#中是8+0的ASCII码得到8在ASCII中的编码值，然后通过(char)转成字符'8'
        return (luhmSum % 10 == 0) ? '0' : (char)((10 - luhmSum % 10) + '0');
    }
}
