package com.dl.common.widget.drawerlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * 仿QQ的协同侧滑- 主界面
 * created by dalang at 2018/11/20
 */
public class MainView extends RelativeLayout {

    private DrawerMenu mDrawerMenu;

    public MainView(Context context) {
        this(context, null, 0);
    }

    public MainView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MainView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    public void setParent(DrawerMenu coordinatorMenu) {
        mDrawerMenu = coordinatorMenu;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (mDrawerMenu.isOpened()) {
            return true;//拦截事件，不往下传递
        }
        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mDrawerMenu.isOpened()) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                mDrawerMenu.closeMenu();
            }
            return true;
        }
        return super.onTouchEvent(event);
    }
}
