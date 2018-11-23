package com.dl.common.base;


import com.dl.common.bean.BaseListModel;
import com.dl.common.constant.Params;
import com.dl.common.manager.ExceptionManager;
import com.dl.common.utils.ToastUtil;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * created by dalang at 2018/11/7
 */
public abstract class BaseListObserver<T> implements Observer<BaseListModel<T>>{
    //处理
    public abstract void onRequestStart();
    /**
     * 根据具体的Api 业务逻辑去重写 onSuccess 方法！
     * @param t
     * @param pageCount 总页码
     */
    public abstract void onSuccess(String pageCount,List<T> t);


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
    public void onNext(BaseListModel<T> tBaseDataModel) {
        onRequestEnd();
        if (tBaseDataModel.getCode()==Params.SUCCESS_CODE) {
            onSuccess(tBaseDataModel.getPageCount(),tBaseDataModel.getData());
        } else {
            ToastUtil.warn(tBaseDataModel.getMsg());
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
