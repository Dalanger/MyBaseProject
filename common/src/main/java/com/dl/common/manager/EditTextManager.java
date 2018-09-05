package com.dl.common.manager;

import android.text.InputFilter;
import android.text.Spanned;
import android.widget.EditText;


/**
 * created by dalang at 2018/9/3
 *
 * editText 管理器
 */
public class EditTextManager {


    /**
     * EditText防止输入空格、换行、限制输入字符长度
     * @param editText
     * @param len 长度限制
     */
    public static void setEditTextInhibitInputSpace(EditText editText,int len){
        InputFilter filter=new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                if(" ".equals(source) || "\n".equals(source)){
                    //空格和换行都转换为""
                    return "";
                }else{
                    return null;
                }
            }
        };
        editText.setFilters(new InputFilter[]{filter, new InputFilter.LengthFilter(len)});
    }


    /**
     * EditText金额输入  自动补0和限制输入两位小数
     * @param editText
     */
    public static void setMoneyEditText(EditText editText) {
        editText.setFilters(new InputFilter[]{new MoneyValueFilter()});
    }


    /**
     * EditText金额输入  自动补0和限制输入len位小数
     * @param editText
     * @param len 小数点位数
     */
    public static void setMoneyEditText(EditText editText,int len) {
        editText.setFilters(new InputFilter[]{new MoneyValueFilter().setDigits(len)});
    }
}
