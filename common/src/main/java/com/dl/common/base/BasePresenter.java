package com.dl.common.base;

import android.support.annotation.CallSuper;

/**
 * Created by dalang on 2018/8/30.
 * Presenter基类
 */
public abstract class BasePresenter<V extends IView,M extends IModel> {
    protected V mView;
    protected M mModel;

    public void attachView(V view) {
        mView = view;
        if (mModel == null) {
            mModel = createModel();
        }
    }

    protected abstract M createModel();

    @CallSuper
    public void detachView() {

        mModel = null;
        mView = null;
    }
    public M getModel() {
        return mModel;
    }

}

