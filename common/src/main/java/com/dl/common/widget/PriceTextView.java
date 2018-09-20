package com.dl.common.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.util.AttributeSet;

import java.text.DecimalFormat;


/**
 * created by dalang at 2018/9/20
 * 金额文字格式化
 */
public class PriceTextView extends AppCompatTextView {

    //变大字体倍数
    private float bigTimes = 1.3f;
    private String showPrice = "";

    public PriceTextView(Context context) {
        super(context);
    }

    public PriceTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PriceTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    /**
     * 格式化显示
     *
     * @param text price
     * @return 0.00
     */
    public PriceTextView parsePrice(String text) {

        double price;
        try {
            price = Double.parseDouble(text);
        } catch (Exception e) {
            price = 0.00;
        }
        showPrice = double2String(price);

        return this;
    }

    /**
     * 显示常态
     */
    public void show() {
        super.setText(showPrice);
    }

    /**
     * 显示符号
     */
    public PriceTextView showSymbol(String symbol) {
        if (TextUtils.isEmpty(symbol))
            symbol = "";
        super.setText(symbol + showPrice);

        return this;
    }

    /**
     * 显示单位
     */
    public PriceTextView showUnit(String unit) {
        if (TextUtils.isEmpty(unit))
            unit = "";
        super.setText(showPrice + unit);

        return this;
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        if (TextUtils.isEmpty(text)) {
            super.setText(text, type);
            return;
        }

        showPrice = text.toString();
        int dotIndex = showPrice.indexOf(".");
        int startIndex = 0;

        //去掉首位单位符号
        int chrStart = showPrice.charAt(0);
        if (chrStart < 48 || chrStart > 57)
            startIndex = 1;
        //末尾变小判断
        if (dotIndex == -1) {
            for (int i = 0; i < showPrice.length(); i++) {
                int cgrEnd = showPrice.charAt(showPrice.length() - (i + 1));
                if (cgrEnd < 48 || cgrEnd > 57) {
                    dotIndex = showPrice.length() - (i + 1);
                }
            }
            dotIndex = dotIndex == -1 ? showPrice.length() : dotIndex;
        }

        Spannable span = new SpannableString(showPrice);
        span.setSpan(new RelativeSizeSpan(bigTimes), startIndex, dotIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        super.setText(span, type);
    }

    /**
     * 获取价格
     *
     * @return
     */
    public double getPrice() {
        try {
            return Double.parseDouble(getStringPrice());
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 获取价格
     *
     * @return
     */
    public String getStringPrice() {
        String value = showPrice;
        //去除前面的单位字符
        value = formatStartPrice(value);
        //去除末尾的单位字符
        value = formatEndPrice(value);

        return value;
    }

    /**
     * double类型转两位小数字符
     *
     * @param d
     * @return
     */
    private String double2String(double d) {
        return new DecimalFormat("##0.00").format(d);
    }

    /**
     * 去除头部单位
     *
     * @param value
     * @return
     */
    private String formatStartPrice(String value) {
        String newValue = value;
        for (int i = 0; i < value.length(); i++) {
            int cgrStart = value.charAt(i);
            if (cgrStart < 48 || cgrStart > 57) {
                newValue = value.substring(i + 1, value.length());
            } else
                break;
        }
        return newValue;
    }

    /**
     * 去除末尾单位
     *
     * @param value
     * @return
     */
    private String formatEndPrice(String value) {
        String newValue = value;
        for (int i = 0; i < value.length(); i++) {
            int cgrEnd = value.charAt(value.length() - (i + 1));
            if (cgrEnd < 48 || cgrEnd > 57) {
                newValue = value.substring(0, value.length() - (i + 1));
            } else
                break;
        }
        return newValue;
    }
}
