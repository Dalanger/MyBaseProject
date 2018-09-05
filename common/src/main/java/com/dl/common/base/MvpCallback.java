package com.dl.common.base;

/**
 * Created by dalang on 2018/8/30.
 * mvp回调
 */
public interface MvpCallback <V extends IView,P extends IPresenter<V>> {
    //创建Presenter  调用在init中
    P createPresenter();

    //创建View
    V createView();


}
