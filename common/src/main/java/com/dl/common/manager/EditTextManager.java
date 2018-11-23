package com.dl.common.manager;

import android.text.InputFilter;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * created by dalang at 2018/9/3
 *
 * editText 管理器
 */
public class EditTextManager {




    /**
     * EditText防止输入空格、换行、颜文字
     * @param editText
     */
    public static void setInputRule(EditText editText){

        Pattern pattern = Pattern.compile("[^a-zA-Z0-9\\u4E00-\\u9FA5_,.?!:;…~_\\-\"\"/@*+'()<>{}/[/]()<>{}\\[\\]=%&$|\\/♀♂#¥£¢€\"^` ，。？！：；……～“”、“（）”、（——）‘’＠‘·’＆＊＃《》￥《〈〉》〈＄〉［］￡［］｛｝｛｝￠【】【】％〖〗〖〗／〔〕〔〕＼『』『』＾「」「」｜﹁﹂｀．]");

        InputFilter filter= (source, start, end, dest, dstart, dend) -> {
            Matcher matcher=  pattern.matcher(source);
            if(" ".equals(source) || "\n".equals(source)){
                //空格和换行都转换为""
                return "";
            }else if (matcher.find()){
                //颜文字都转换为""
                return "";
            } else{
                return null;
            }
        };

            editText.setFilters(new InputFilter[]{filter});

    }


    /**
     * EditText防止输入空格、换行、颜文字.限制输入字符长度
     * @param editText
     * @param len 长度限制
     */
    public static void setInputRule(EditText editText, int len){

        Pattern pattern = Pattern.compile("[^a-zA-Z0-9\\u4E00-\\u9FA5_,.?!:;…~_\\-\"\"/@*+'()<>{}/[/]()<>{}\\[\\]=%&$|\\/♀♂#¥£¢€\"^` ，。？！：；……～“”、“（）”、（——）‘’＠‘·’＆＊＃《》￥《〈〉》〈＄〉［］￡［］｛｝｛｝￠【】【】％〖〗〖〗／〔〕〔〕＼『』『』＾「」「」｜﹁﹂｀．]");

        InputFilter filter= (source, start, end, dest, dstart, dend) -> {
            Matcher matcher=  pattern.matcher(source);
            if(" ".equals(source) || "\n".equals(source)){
                //空格和换行都转换为""
                return "";
            }else if (matcher.find()){
                //颜文字都转换为""
                return "";
            } else{
                return null;
            }
        };
        if (len > 0) {
            editText.setFilters(new InputFilter[]{filter, new InputFilter.LengthFilter(len)});
        } else {
            editText.setFilters(new InputFilter[]{filter});
        }
    }



    /**
     * EditText只防止输入颜文字
     * @param editText
     */
    public static void setInputWithoutWords(EditText editText){

        Pattern pattern = Pattern.compile("[^a-zA-Z0-9\\u4E00-\\u9FA5_,.?!:;…~_\\-\"\"/@*+'()<>{}/[/]()<>{}\\[\\]=%&$|\\/♀♂#¥£¢€\"^` ，。？！：；……～“”、“（）”、（——）‘’＠‘·’＆＊＃《》￥《〈〉》〈＄〉［］￡［］｛｝｛｝￠【】【】％〖〗〖〗／〔〕〔〕＼『』『』＾「」「」｜﹁﹂｀．]");

        InputFilter filter= (source, start, end, dest, dstart, dend) -> {
            Matcher matcher=  pattern.matcher(source);
           if (matcher.find()){
                //颜文字都转换为""
                return "";
            } else{
                return null;
            }
        };


    }


    /**
     * EditText只防止输入颜文字
     * @param editText
     */
    public static void setInputWithoutWords(EditText editText,int len){

        Pattern pattern = Pattern.compile("[^a-zA-Z0-9\\u4E00-\\u9FA5_,.?!:;…~_\\-\"\"/@*+'()<>{}/[/]()<>{}\\[\\]=%&$|\\/♀♂#¥£¢€\"^` ，。？！：；……～“”、“（）”、（——）‘’＠‘·’＆＊＃《》￥《〈〉》〈＄〉［］￡［］｛｝｛｝￠【】【】％〖〗〖〗／〔〕〔〕＼『』『』＾「」「」｜﹁﹂｀．]");

        InputFilter filter= (source, start, end, dest, dstart, dend) -> {
            Matcher matcher=  pattern.matcher(source);
            if (matcher.find()){
                //颜文字都转换为""
                return "";
            } else{
                return null;
            }
        };


        if (len > 0) {
            editText.setFilters(new InputFilter[]{filter, new InputFilter.LengthFilter(len)});
        } else {
            editText.setFilters(new InputFilter[]{filter});
        }

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
