package com.dl.common.base;


/**
 * Created by dalang on 2018/8/30.
 * IView 顶级接口
 */
public interface IView {
    void showMessage(String msg); //toast
    void showProgress();//dialog形式的加载
    void hideProgress();//隐藏dialog
    void showLoading();//整个页面显示加载页
    void dismissLoading();//显示页面隐藏加载页
    void showErrorUI();//整个页面网络错误页
    void showErrorDialog();//dialog形式显示错误
}
