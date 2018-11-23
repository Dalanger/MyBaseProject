package com.dl.common.manager;

import android.view.View;

import com.jakewharton.rxbinding3.view.RxView;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import kotlin.Unit;

/**
 *
 * 使用rxbinding进行重复点击限制
 * created by dalang at 2018/11/4
 */
public class ClickManager {
    private static ClickManager mManger;

    public static ClickManager getInstance() {
        if (mManger == null) {
            mManger = new ClickManager();
        }
        return mManger;
    }

    public void singleClick(View view, final Callback callback) {
        RxView.clicks(view)
                .throttleFirst(1, TimeUnit.SECONDS)//在一秒内只取第一次点击
                .subscribe(new Observer<Unit>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Unit unit) {
                        callback.onCallback();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }





    public  interface Callback{
        void onCallback();
    }
}
