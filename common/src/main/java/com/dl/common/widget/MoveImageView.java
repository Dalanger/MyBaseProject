package com.dl.common.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.dl.common.R;

/**
 * created by dalang at 2018/11/20
 */
@SuppressLint("AppCompatCustomView")
public class MoveImageView extends ImageView {

    private Drawable mDrawable;
    private int mLeft = 0;
    private int mTop = 0;
    private int mSpeed = 2;
    private boolean isSetVerticalMove;
    private boolean isMoveLeft;
    private boolean isMoveUp;
    private Handler mHandler;
    private int mCanvasBgSize;



    public MoveImageView(Context context) {
        super(context);
    }

    public MoveImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setUp(context, attrs);
    }

    public MoveImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setUp(context, attrs);
    }

    private void setUp(Context context, AttributeSet attrs) {
        TypedArray a =context.obtainStyledAttributes(attrs, R.styleable.MoveImage);
        int direction=a.getInteger(R.styleable.MoveImage_direction,0);
        mSpeed=a.getInteger(R.styleable.MoveImage_speed,2);

        if (direction == 0) {
            isSetVerticalMove = true;
        } else {
            isSetVerticalMove = false;
        }

        mDrawable = getDrawable();
        mHandler = new MoveHandler();
        mHandler.sendEmptyMessageDelayed(1, 220L);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isSetVerticalMove) {
            canvas.translate(0.0F, mTop);
        } else {
            canvas.translate(mLeft, 0.0F);
        }
        mDrawable.draw(canvas);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if (isSetVerticalMove) {
            mCanvasBgSize = getMeasuredHeight() * 3 / 2;
            mDrawable.setBounds(0, 0, getMeasuredWidth(), mCanvasBgSize);
        } else {
            mCanvasBgSize = getMeasuredWidth() * 3 / 2;
            mDrawable.setBounds(0, 0, mCanvasBgSize, getMaxHeight());
        }
    }

    private class MoveHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (isSetVerticalMove) {
                if (isMoveUp) {
                    if (mTop <= getMeasuredHeight() - mCanvasBgSize)//此时表示移到了最up的位置
                    {
                        mTop += mSpeed;
                        isMoveUp = false;
                    } else//继续下移
                    {
                        mTop -= mSpeed;
                    }
                } else {
                    if (mTop == 0)//此时表示移动到了最down，此时图片的up侧应该与屏幕up侧对齐，即坐标值为0
                    {
                        mTop -= mSpeed;
                        isMoveUp = true;//图片已经移动到了最down侧，需要修改其移动方向为up
                    } else {

                        mTop += mSpeed;//继续下移
                    }
                }
            } else {
                if (isMoveLeft)//向左移动
                {

                    if (mLeft <= getMeasuredWidth() - mCanvasBgSize)//此时表示移到了最左侧的位置
                    {
                        mLeft += mSpeed;
                        isMoveLeft = false;
                    } else//继续左移
                    {
                        mLeft -= mSpeed;
                    }

                } else {
                    if (mLeft == 0)//此时表示移动到了最右侧，此时图片的左侧应该与屏幕左侧对齐，即坐标值为0
                    {
                        mLeft -= mSpeed;
                        isMoveLeft = true;//图片已经移动到了最右侧，需要修改其移动方向为向左
                    } else {
                        mLeft += mSpeed;//继续右移
                    }
                }
            }
            invalidate();
            mHandler.sendEmptyMessageDelayed(1, 22);
        }
    }
}
