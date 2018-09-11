package com.dl.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.dl.common.R;
import com.dl.common.uitils.PhoneUtil;


public class PageIndicator extends View {

    private int count;

    private int selection = 0;

    private Paint mSelectedPaint;
    private Paint mUnSelectedPaint;

    private int selectedW;
    private int unSelectedW;
    private int height;
    private int gapW;
    private int unSelectedColor;
    private int selectedColor;

    public PageIndicator(Context context) {
        this(context,null);
    }

    public PageIndicator(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PageIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.PageIndicator);
        unSelectedColor = ta.getColor(R.styleable.PageIndicator_unselected_color, ContextCompat.getColor(context,R.color.grayCC));
        selectedColor = ta.getColor(R.styleable.PageIndicator_selected_color, ContextCompat.getColor(context,R.color.black));
        count=ta.getInt(R.styleable.PageIndicator_count,3);
        ta.recycle();
        init(context);
    }

    private void init(Context context){
        mSelectedPaint = new Paint();
        mSelectedPaint.setColor(selectedColor);
        mSelectedPaint.setAntiAlias(true);
        mSelectedPaint.setStyle(Paint.Style.FILL);

        mUnSelectedPaint = new Paint();
        mUnSelectedPaint.setColor(unSelectedColor);
        mUnSelectedPaint.setAntiAlias(true);
        mUnSelectedPaint.setStyle(Paint.Style.FILL);

        selectedW = PhoneUtil.dp2px(context,20f);
        unSelectedW = PhoneUtil.dp2px(context,10f);
        height = PhoneUtil.dp2px(context,2f);
        gapW = PhoneUtil.dp2px(context,5f);
    }

    public void setCount(int count){
        this.count = count;
        requestLayout();
        invalidate();
    }

    public void setSelection(int position){
        selection = position;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = (count - 1)*(unSelectedW + gapW) + selectedW;
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        boolean isHasSelect = false;
        for (int i = 0; i < count ; i ++){
            if (i == selection){
                isHasSelect = true;
                RectF rectF = new RectF(i*(unSelectedW + gapW),0,i*(unSelectedW + gapW) + selectedW,height);
                canvas.drawRoundRect(rectF,200,200,mSelectedPaint);
            }else {
                if (isHasSelect){
                    RectF rectF = new RectF((i-1)*(unSelectedW + gapW)+(selectedW + gapW),0,(i-1)*(unSelectedW + gapW)+(selectedW + gapW) + unSelectedW,height);
                    canvas.drawRoundRect(rectF,200,200,mUnSelectedPaint);
                }else {
                    RectF rectF = new RectF(i*(unSelectedW + gapW),0,i*(unSelectedW + gapW) + unSelectedW,height);
                    canvas.drawRoundRect(rectF,200,200,mUnSelectedPaint);
                }
            }
        }
    }


}
