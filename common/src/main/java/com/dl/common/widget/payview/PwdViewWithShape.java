package com.dl.common.widget.payview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.dl.common.R;
import com.dl.common.utils.DisplayUtil;

/**
 * created by dalang at 2018/10/25
 */
public class PwdViewWithShape extends LinearLayout implements TextWatcher, View.OnKeyListener, View.OnFocusChangeListener{

    //形状
    enum Shape{
        CIRCLE,
        RECT
    }
    private Shape shape;

    //形状为circle时的半径
    private int radius = 0;
    //形状为rect时的宽高
    private int rectW = 0;
    private int rectH = 0;
    //字体大小和颜色
    private int editTextSize = 0;
    private int editTextColor = 0;
    //背景drawable
    private int bgDrawable = 0;
    //光标drawable
    private int cursorDrawable = 0;
    //输入框数量
    private int editNum = 4;

    private Long endTime = 0l;

    public PwdViewWithShape(Context context) {
        this(context,null);
    }

    public PwdViewWithShape(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PwdViewWithShape(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.PasswordViewShape);

        int value = ta.getInteger(R.styleable.PasswordViewShape_shape,0);
        if (value == 0){
            shape = Shape.CIRCLE;
        }else{
            shape = Shape.RECT;
        }
        radius = ta.getDimensionPixelSize(R.styleable.PasswordViewShape_mRadius, DisplayUtil.dp2px(context,25f));
        rectW = ta.getDimensionPixelSize(R.styleable.PasswordViewShape_rectW, DisplayUtil.dp2px(context,50f));
        rectH = ta.getDimensionPixelSize(R.styleable.PasswordViewShape_rectH, DisplayUtil.dp2px(context,50f));
        editTextSize = ta.getDimensionPixelSize(R.styleable.PasswordViewShape_edit_text_size, DisplayUtil.dp2px(context,12f));
        editTextColor = ta.getColor(R.styleable.PasswordViewShape_edit_text_color, ContextCompat.getColor(context,R.color.black33));
        bgDrawable = ta.getResourceId(R.styleable.PasswordViewShape_uncheck_drawable, R.drawable.default_uncheck_pay_view_bg);
        cursorDrawable = ta.getResourceId(R.styleable.PasswordViewShape_checked_drawable, R.drawable.default_checked_pay_view_bg);
        editNum = ta.getDimensionPixelSize(R.styleable.PasswordViewShape_edit_num, 4);

        ta.recycle();
        init(context);
    }

    private void init(Context context){
        for (int i = 0; i < editNum; i++) {
            EditText editText = new EditText(context);
            initEditText(editText, i);
            addView(editText);
            if (i == 0) { //设置第一个editText获取焦点
                editText.setFocusable(true);
            }
        }
    }

    private void initEditText(EditText editText, int i) {
        int childHPadding = 14;
        int childVPadding = 14;

        LinearLayout.LayoutParams layoutParams = null;
        if (shape == Shape.CIRCLE){
            layoutParams = new LinearLayout.LayoutParams(radius*2, radius*2);
        }else {
            layoutParams = new LinearLayout.LayoutParams(rectW, rectH);
        }
        layoutParams.bottomMargin = childVPadding;
        layoutParams.topMargin = childVPadding;
        layoutParams.leftMargin = childHPadding;
        layoutParams.rightMargin = childHPadding;
        layoutParams.gravity = Gravity.CENTER;
        editText.setLayoutParams(layoutParams);
        editText.setGravity(Gravity.CENTER);
        editText.setId(i);
        editText.setCursorVisible(true);
        editText.setMaxEms(1);
        editText.setTextColor(editTextColor);
        editText.setTextSize(editTextSize);
        editText.setMaxLines(1);
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)});
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        editText.setPadding(0, 0, 0, 0);
        editText.setOnKeyListener(this);
        editText.setBackgroundResource(bgDrawable);
        editText.setCursorVisible(false);
        editText.addTextChangedListener(this);
        editText.setOnFocusChangeListener(this);
        editText.setOnKeyListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.length() != 0) {
            focus();
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DEL) {
            backFocus();
        }
        return false;
    }

    @Override
    public void setEnabled(boolean enabled) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            child.setEnabled(enabled);
        }
    }

    /**
     * 获取焦点
     */
    private void focus() {
        int count = getChildCount();
        EditText editText;
        //利用for循环找出还最前面那个还没被输入字符的EditText，并把焦点移交给它。
        for (int i = 0; i < count; i++) {
            editText = (EditText) getChildAt(i);
            if (editText.getText().length() < 1) {
                editText.setBackgroundResource(cursorDrawable);
                editText.requestFocus();
                return;
            } else {
                editText.setBackgroundResource(bgDrawable);
            }
        }
    }

    private void backFocus() {
        long startTime = System.currentTimeMillis();
        if (startTime - endTime > 100){
            EditText editText;
            for (int i = editNum - 1; i >= 0; i--) {
                editText = (EditText) getChildAt(i);
                if (editText.getText().length() >= 1) {
                    editText.setText("");
                    editText.setBackgroundResource(cursorDrawable);
                    editText.requestFocus();
                    endTime = startTime;
                    return;
                }else {
                    editText.setBackgroundResource(bgDrawable);
                    if (i == 0){
                        editText.setBackgroundResource(cursorDrawable);
                        editText.requestFocus();
                    }
                }
            }
        }
    }

    public String getText() {
        StringBuffer stringBuffer = new StringBuffer();
        EditText editText;
        for (int i = 0; i < editNum; i++) {
            editText = (EditText) getChildAt(i);
            stringBuffer.append(editText.getText());
        }

        return stringBuffer.toString();
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            focus();
        }
    }
}
