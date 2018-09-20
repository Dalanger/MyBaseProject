package com.dl.common.base;


/**
 * Created by dalang on 2018/8/30.
 * IView 顶级接口
 */
public interface IView {
    void showMessage(String msg); //toast
    void showDialogLoading();//dialog形式的加载
    void dismissDialogLoading();//隐藏dialog
    void showUILoading();//整个页面显示加载页
    void dismissUILoading();//显示页面隐藏加载页
    void errorUI();//整个页面网络错误页
    void errorDialog();//dialog形式显示错误
}
