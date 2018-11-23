package com.dl.common.base;

import com.dl.common.bean.NoDataModel;
import com.dl.common.constant.Params;
import com.dl.common.manager.ExceptionManager;
import com.dl.common.utils.ToastUtil;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * created by dalang at 2018/11/7
 */
public abstract class BaseNoDataObserver implements Observer<NoDataModel> {
    //处理
    public abstract void onRequestStart();
    /**
     * 根据具体的Api 业务逻辑去重写 onSuccess 方法！
     */
    public abstract void onSuccess(NoDataModel noDataModel);


    public abstract void onRequestEnd();

    /**
     * Error 可选择重写
     * @param e
     */
    public  void onFailure(Throwable e) {
        ToastUtil.error(ExceptionManager.handleException(e));
    }



    @Override
    public void onSubscribe(Disposable d) {
        onRequestStart();
    }


    @Override
    public void onNext(NoDataModel noDataModel) {
        onRequestEnd();
        if (noDataModel.getCode()==Params.SUCCESS_CODE) {
            onSuccess(noDataModel);
        } else {
            ToastUtil.warn(noDataModel.getMsg());
        }
    }

    @Override
    public void onError(Throwable e) {
        onRequestEnd();
        onFailure(e);
    }

    @Override
    public void onComplete() {

    }
}
