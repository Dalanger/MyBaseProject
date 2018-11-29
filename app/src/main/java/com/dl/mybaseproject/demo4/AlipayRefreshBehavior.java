package com.dl.mybaseproject.demo4;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import com.dl.common.utils.LogUtil;


/**
 * Created by dalang on 2018/10/11.
 * 用作处理中间刷新布局开、合以及刷新中三个状态的布局参数
 * 借鉴stafen的easybehavior
 */
public class AlipayRefreshBehavior extends AppBarLayout.Behavior {
    private static final String TAG = "refresh";
    private static final float HARD_RATIO = 200;//阻尼系数
    private static final float MAX_HEIGHT = 600;//最大高度
    private static int TARGET_REFESH_HEIGHT = -1;//刷新高度
    private int mTargetViewHeight = -1;
    private View mTargetView;//这里指的使我们的SmileView
    private int mParentHeight;//AppBarLayout的高度
    private float mFingerMoveDy;//实际手指移动的Y轴差量
    private int mViewMoveDy;//实际target移动的Y轴差量

    private float mLastScale;
    private int mLastBottom;
    private boolean isAnimate;//是否进行了动画
    private boolean isRecovering = false;//是否正在自动回弹中
    private boolean isPining = false;//是否正在刷新中

    private AppBarLayout mAppBarLayout;
    private onRefrehViewActionListener onRefrehViewActionListener;
    private boolean isScaling;


    public AlipayRefreshBehavior() {
    }

    public AlipayRefreshBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, AppBarLayout abl, int layoutDirection) {
        boolean handled = super.onLayoutChild(parent, abl, layoutDirection);

        // 需要在调用过super.onLayoutChild()方法之后获取
        if (mTargetView == null) {
            mTargetView = parent.findViewWithTag(TAG);
            mTargetView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    mTargetView.getViewTreeObserver().removeOnPreDrawListener(this);
                    //初始化刷新高度以及控件高度
                    TARGET_REFESH_HEIGHT = getCalHeight(mTargetView);
                    mTargetViewHeight = TARGET_REFESH_HEIGHT;
                    return false;
                }
            });
            if (mTargetView != null) {
                initial(abl);
            }
        }

        return handled;
    }

    private void initial(AppBarLayout abl) {
        this.mAppBarLayout = abl;
        abl.setClipChildren(false);
        mParentHeight = abl.getHeight();
        LogUtil.d("parent"+mParentHeight);
    }



    @Override
    public boolean onStartNestedScroll(CoordinatorLayout parent, AppBarLayout child, View directTargetChild, View target, int nestedScrollAxes, int type) {

        if ((target instanceof RecyclerView) || target instanceof NestedScrollView) {//这里判断下拉的对象是否符合条件

            isAnimate = true;
            return true;
        } else {
            return super.onStartNestedScroll(parent, child, directTargetChild, target, nestedScrollAxes,type);
        }

    }


    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target, int dx, int dy, int[] consumed, int type) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
        if (!isRecovering) {
            //返回1时，表示当前target处于非touch的滑动，
            //该bug的引起是因为appbar在滑动时，CoordinatorLayout内的实现NestedScrollingChild2接口的滑动子类还未结束其自身的fling
            // 所以这里监听子类的非touch时的滑动，然后block掉滑动事件传递给AppBarLayout|| (dy > 0 && child.getBottom() > mParentHeight))

            if (mTargetView != null && !isPining && type != 1
                    && (dy < 0 && child.getBottom() >= mParentHeight)) {
//                super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
                scale(child, dy, target);//执行下拉展开动画

            }
        }
    }

    /**
     * 判断第一个可视item为第一个位置方可进行下拉放大
     *
     * @param target
     * @return
     */
    private boolean canScale(View target) {
        //当target为RecyclerView,并且manager是LinearLayoutManager，需要判断第一个item可见的位置
        if (target instanceof RecyclerView && ((RecyclerView) target).getLayoutManager() instanceof LinearLayoutManager) {
            LinearLayoutManager linearManager = (LinearLayoutManager) ((RecyclerView) target).getLayoutManager();
            //获取第一个可见view的位置
            int firstItemPosition = linearManager.findFirstVisibleItemPosition();
            if (firstItemPosition != 0) {//不是第一个不做处理
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target, float velocityX, float velocityY) {


        if (velocityY > 100) {//当y速度>300,就秒弹回
            isAnimate = false;
        }
        return super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY);
    }



    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout abl, View target, int type) {

        if (checkPin()) {//检测是否达到刷新临界值
            startPin();//开始刷新动画
        } else {
//            if (!isScaling) {
//                recovery(abl);
//            }
            recovery(abl);
        }
        super.onStopNestedScroll(coordinatorLayout, abl, target, type);
    }

    private void scale(AppBarLayout abl, int dy, View target) {
        if (dy < 0 &&!canScale(target)) return;
        isScaling = true;
        mFingerMoveDy += -dy;
        mFingerMoveDy = Math.min(mFingerMoveDy, MAX_HEIGHT);//最大高度
        mLastScale = Math.max(1f, 1f + mFingerMoveDy / HARD_RATIO);//放大倍数
        mLastBottom = mParentHeight + (int) (mTargetViewHeight / 2 * (mLastScale - 1));
        mViewMoveDy = mLastBottom - mParentHeight;
        setTargetHeight(mViewMoveDy);
        abl.setBottom(mLastBottom);
        Log.i("dalang","执行了吗"+mFingerMoveDy+"==="+mLastScale+"==="+mLastBottom+"==="+mViewMoveDy+"==="+dy);

    }

    /**
     * 根据子view的高度以及margin计算我们的动画视图的高度
     *
     * @param view
     * @return
     */
    public int getCalHeight(View view) {
        int height = 0;
        if (view == null) return height;
        if (view instanceof LinearLayout) {
            LinearLayout parent = (LinearLayout) view;
            for (int i = 0; i < parent.getChildCount(); i++) {
                View child = parent.getChildAt(i);
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) child.getLayoutParams();
                height += child.getHeight() + lp.topMargin + lp.bottomMargin;

            }
        } else if (view instanceof ViewGroup) {
            ViewGroup parent = (ViewGroup) view;
            for (int i = 0; i < parent.getChildCount(); i++) {
                View child = parent.getChildAt(i);
                height += child.getHeight();
            }
        } else {
            height = view.getHeight();
        }
        return height;
    }


    private void recovery(final AppBarLayout abl) {
        if (isRecovering) return;

        if (mFingerMoveDy > 0) {

            isRecovering = true;
            if (isAnimate) {
                ValueAnimator anim = ValueAnimator.ofFloat(mLastScale, 1f).setDuration(200);
                anim.addUpdateListener(
                        new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                setTargetHeight((int) (mViewMoveDy * (1 - animation.getAnimatedFraction())));
                                abl.setBottom((int) (mLastBottom - mViewMoveDy * animation.getAnimatedFraction()));
//                                Log.e("dalang",  "setTargetHeight"+(int) (mViewMoveDy * (1 - animation.getAnimatedFraction())));
//                                Log.e("dalang",  "setBottom"+(int) (mLastBottom - mViewMoveDy * animation.getAnimatedFraction()));
                            }
                        }
                );
                anim.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {

                        isRecovering = false;
                        mViewMoveDy = 0;
                        mFingerMoveDy = 0;
                        isScaling=false;

                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        isRecovering = false;
                        mViewMoveDy = 0;
                        mFingerMoveDy = 0;
                        isScaling=false;
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                    }
                });
                anim.start();
            } else {
                setTargetHeight(0);
                abl.setBottom(mParentHeight);
                isRecovering = false;
                mViewMoveDy = 0;
                mFingerMoveDy = 0;
                isScaling=false;

            }
        }
    }

    /**
     * 开始刷新
     */
    public void startPin() {

        if (isPining) return;
        isPining = true;

        //这个位置，很可能Target视图（即SmileView）的高度是大于刷新临界值的，因此需要先移动到我们刷新需要的位置，来，走一个
        animToRefreshHeihgt();

        if (onRefrehViewActionListener != null) {
            onRefrehViewActionListener.onRefresh();
        }
    }

    /**
     * 将Target视图的高度移动到我们刷新需要的高度
     */
    private void animToRefreshHeihgt() {
        final ValueAnimator anim = ValueAnimator.ofInt(mViewMoveDy, TARGET_REFESH_HEIGHT).setDuration(200);
        anim.addUpdateListener(
                new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        setTargetHeight((int) animation.getAnimatedValue());
                        mViewMoveDy = (int) animation.getAnimatedValue();
                        mAppBarLayout.setBottom((int) (mLastBottom - mViewMoveDy * animation.getAnimatedFraction()));
                    }
                }
        );
        anim.start();
    }

    public void stopPin() {

        isPining = false;
        recovery(mAppBarLayout);
    }

    /**
     * 判断是否达到刷新的条件
     *
     * @return
     */
    public boolean checkPin() {
//        LogUtil.d("刷新位移"+mViewMoveDy+"条件"+TARGET_REFESH_HEIGHT);
        if (mViewMoveDy >= TARGET_REFESH_HEIGHT) {
            return true;
        }
        return false;
    }

    /**
     * 设置动画视图的高度
     *
     * @param mViewMoveDy
     */
    private void setTargetHeight(int mViewMoveDy) {
        ViewGroup.LayoutParams lp = mTargetView.getLayoutParams();
        lp.height = mViewMoveDy;
        mTargetView.setLayoutParams(lp);
    }


    public void setOnRefrehViewActionListener(onRefrehViewActionListener onRefrehViewActionListener) {
        this.onRefrehViewActionListener = onRefrehViewActionListener;
    }

    public interface onRefrehViewActionListener {
        void onRefresh();

    }

}