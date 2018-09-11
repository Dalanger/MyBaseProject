package com.dl.common.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.dl.common.bean.MsgEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;


/**
 * Created by dalang on 2018/8/30.
 * 结合之前的项目  对baseFragment进行更完善的封装
 */
public abstract class BaseFragment extends Fragment{

    public Activity mActivity;
    public Context mContext;
    private View rootView;
    //当前Fragment是否处于可见状态标志
    private boolean isFragmentVisible;
    //是否是第一次开启网络加载
    public boolean isFirst;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            managerArguments();
        }
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null)
            rootView = inflater.inflate(getLayoutRes(), container, false);
        ButterKnife.bind(this, rootView);
        mActivity = getActivity();
        mContext = getContext();
        initView();
        //可见，但是并没有加载过
        if (isFragmentVisible && !isFirst) {
            onFragmentVisibleChange(true);
        }
        return rootView;
    }

    /**
     * 是否可以加载数据
     * 可以加载数据的条件：
     * 1.视图已经初始化
     * 2.视图对用户可见
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isFragmentVisible = true;
        }
        if (rootView == null) {
            return;
        }
        //可见，并且没有加载过
        if (!isFirst&&isFragmentVisible) {
            onFragmentVisibleChange(true);
            return;
        }
        //由可见——>不可见 已经加载过
        if (isFragmentVisible) {
            onFragmentVisibleChange(false);
            isFragmentVisible = false;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }



    //--------------------------------------------------------eventBus相关-------------------------------------------------------//
    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    //在onStart调用register后，执行消息
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(MsgEvent event){

    }



//--------------------------------------------------------子类需要重写的方法-------------------------------------------------------//
    /**
     * 当前fragment可见状态发生变化时会回调该方法
     * 如果当前fragment是第一次加载，等待onCreateView后才会回调该方法，其它情况回调时机跟 {@link #setUserVisibleHint(boolean)}一致
     * 在该回调方法中你可以做一些加载数据操作，甚至是控件的操作.
     *
     * @param isVisible true  不可见 -> 可见
     *                  false 可见  -> 不可见
     */
    public void onFragmentVisibleChange(boolean isVisible) {

    }

    /**
     * 初始化布局
     * @return 布局文件的id。
     */
    public abstract int getLayoutRes();

    /**
     * 初始化视图
     */
    public abstract void initView();

    /**
     * 如果Fragment创建需要数据，在这里接收传进来的数据。
     * 如果没有这个抽象方法就空实现。
     */
    protected abstract void managerArguments();

//--------------------------------------------------------跳转的方法-------------------------------------------------------//
public void startActivity(Class<? extends Activity> clazz) {
    Activity activity = getActivity();
    if (activity == null) return;
    Intent intent = new Intent(activity, clazz);
    activity.startActivity(intent);
    BGASwipeBackHelper.executeForwardAnim(activity);
}


    //带参数的跳转 string
    public void startActivity(Class<?> targetClass, String key, String value) {
        Activity activity = getActivity();
        if (activity == null) return;
        Intent intent = new Intent(activity, targetClass);
        intent.putExtra(key,value);
        activity.startActivity(intent);
        BGASwipeBackHelper.executeForwardAnim(activity);
    }


    //带参数的跳转 int
    public void startActivity(Class<?> targetClass, String key, int value) {
        Activity activity = getActivity();
        if (activity == null) return;
        Intent intent = new Intent(activity, targetClass);
        intent.putExtra(key,value);
        activity.startActivity(intent);
        BGASwipeBackHelper.executeForwardAnim(activity);
    }

    //带参数的跳转 bundle
    public void startActivity(Class<?> targetClass, Bundle bundle) {
        Activity activity = getActivity();
        if (activity == null) return;
        Intent intent = new Intent(activity, targetClass);
        intent.putExtras(bundle);
        activity.startActivity(intent);
        BGASwipeBackHelper.executeForwardAnim(activity);
    }


//--------------------------------------------------------一些方便使用的方法---------------------------------------------------------//

    /**
     * 设置颜色
     * @param resColor
     * @return
     */
    public int color(int resColor) {
        return ContextCompat.getColor(mContext, resColor);
    }


    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        mActivity.getWindow().setAttributes(lp);
    }

}
