package com.dl.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.dl.common.R;

/**
 * created by dalang at 2018/10/15
 * 弧形纯色背景view  弧线凹形
 */
public class ArcView extends View {
    private int mWidth;
    private int mHeight;
    /**
     * 弧形高度
     */
    private int mArcHeight;
    /**
     * 背景颜色
     */
    private int mBgColor;
    private Paint mPaint;
    private Context mContext;

    public ArcView(Context context) {
        this(context, null);
    }

    public ArcView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ArcView);
        mArcHeight = typedArray.getDimensionPixelSize(R.styleable.ArcView_arcHeight, 0);
        mBgColor=typedArray.getColor(R.styleable.ArcView_bgColor, Color.parseColor("#303F9F"));

        mContext = context;
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mBgColor);

        Rect rect = new Rect(0, 0, mWidth, mHeight - mArcHeight);
        canvas.drawRect(rect, mPaint);


        Path path = new Path();
        path.moveTo(0, mHeight - mArcHeight);
        path.quadTo(mWidth / 2, mHeight, mWidth, mHeight - mArcHeight);
        canvas.drawPath(path, mPaint);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize;
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize;
        }
        setMeasuredDimension(mWidth, mHeight);
    }

}
