package com.dl.common.manager;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.DigitsKeyListener;

/**
 * Created by dalang on 2018/6/12.
 *
 */

public class MoneyValueFilter extends DigitsKeyListener {
    private static final String TAG = "MoneyValueFilter";
    public MoneyValueFilter() {
        super(false, true);
    }

    private int digits = 2;

    public MoneyValueFilter setDigits(int d) {
        digits = d;
        return this;
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end,
                               Spanned dest, int dstart, int dend) {
        CharSequence out = super.filter(source, start, end, dest, dstart, dend);


        // if changed, replace the source
        if (out != null) {
            source = out;
            start = 0;
            end = out.length();
        }

        int len = end - start;

        // if deleting, source is empty
        // and deleting can't break anything
        if (len == 0) {
            return source;
        }

        //以点开始的时候，自动在前面添加0
        if(source.toString().equals(".") && dstart == 0){
            return "0.";
        }
        //如果起始位置为0,且第二位跟的不是".",则无法后续输入
        if(!source.toString().equals(".") && dest.toString().equals("0")){
            return "";
        }

        int dlen = dest.length();

        // Find the position of the decimal .
        for (int i = 0; i < dstart; i++) {
            if (dest.charAt(i) == '.') {
                // being here means, that a number has
                // been inserted after the dot
                // check if the amount of digits is right
                return (dlen-(i+1) + len > digits) ?
                        "" :
                        new SpannableStringBuilder(source, start, end);
            }
        }

        for (int i = start; i < end; ++i) {
            if (source.charAt(i) == '.') {
                // being here means, dot has been inserted
                // check if the amount of digits is right
                if ((dlen-dend) + (end-(i + 1)) > digits)
                    return "";
                else
                    break;  // return new SpannableStringBuilder(source, start, end);
            }
        }


        // if the dot is after the inserted part,
        // nothing can break
        return new SpannableStringBuilder(source, start, end);
    }

}
