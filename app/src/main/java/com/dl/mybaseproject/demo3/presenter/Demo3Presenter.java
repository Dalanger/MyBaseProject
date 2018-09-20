package com.dl.mybaseproject.demo3.presenter;

import com.dl.common.base.BasePresenter;
import com.dl.common.constant.Params;
import com.dl.mybaseproject.demo3.bean.TestBean1;
import com.dl.mybaseproject.demo3.bean.TestBean2;
import com.dl.mybaseproject.demo3.bean.TestBean3;
import com.dl.mybaseproject.demo3.bean.TestBean5;
import com.dl.mybaseproject.demo3.contract.IDemo3Contract;
import com.dl.mybaseproject.demo3.model.Demo3Model;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Description :
 *
 * @author dalang
 * @date 2018/9/17  16:59
 * - generate by MvpAutoCodePlus plugin.
 */

public class Demo3Presenter extends BasePresenter<IDemo3Contract.View, IDemo3Contract.Model> implements IDemo3Contract.Presenter {

    @Override
    protected IDemo3Contract.Model createModel() {
        return new Demo3Model();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void getMergeData() {
        mView.showDialogLoading();
        mModel.getMergeData(new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Object o) {
                mView.dismissDialogLoading();
                if (o instanceof TestBean1) {
                    if (((TestBean1) o).getMsg().equals(Params.SUCCESS_MSG)) {
                        mView.setData1(((TestBean1) o).getDate().getProportion());
                    } else {
                        mView.showMessage(((TestBean1) o).getDescribe());
                    }
                } else if (o instanceof TestBean2) {
                    if (((TestBean2) o).getMsg().equals(Params.SUCCESS_MSG)) {

                        mView.setData2(((TestBean2) o).getDate());
                    } else {
                        mView.showMessage(((TestBean2) o).getDescribe());
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                mView.dismissDialogLoading();
                mView.errorDialog();
            }

            @Override
            public void onComplete() {
                mView.dismissDialogLoading();
            }
        });
    }

    @Override
    public void getFlatMapData(String type) {
        mView.showDialogLoading();
        mModel.getFlatMapData(type, new Observer<TestBean3>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(TestBean3 testBean3) {
                mView.dismissDialogLoading();
                if (testBean3.getMsg().equals(Params.SUCCESS_MSG)) {
                    mView.setData3(testBean3.getData().toString());
                }else {
                    mView.showMessage(testBean3.getDescribe());
                }
            }

            @Override
            public void onError(Throwable e) {
                mView.dismissDialogLoading();
                mView.errorDialog();
            }

            @Override
            public void onComplete() {
                mView.dismissDialogLoading();
            }
        });
    }

    @Override
    public void getZipData() {
        mView.showDialogLoading();
        mModel.getZipData(new Observer<List<TestBean5>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<TestBean5> testBean5s) {
                mView.dismissDialogLoading();
               mView.setData4(testBean5s);
            }

            @Override
            public void onError(Throwable e) {
                mView.dismissDialogLoading();
                mView.errorDialog();
            }

            @Override
            public void onComplete() {
                mView.dismissDialogLoading();
            }
        });
    }



}

