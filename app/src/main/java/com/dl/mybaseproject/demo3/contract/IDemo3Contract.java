package com.dl.mybaseproject.demo3.contract;

import com.dl.common.base.IModel;
import com.dl.common.base.IPresenter;
import com.dl.common.base.IView;
import com.dl.mybaseproject.demo3.bean.TestBean2;
import com.dl.mybaseproject.demo3.bean.TestBean3;
import com.dl.mybaseproject.demo3.bean.TestBean5;

import java.util.List;

import io.reactivex.Observer;

/**
 * Description :
 *
 * @author dalang
 * @date 2018/9/17  16:59
 * - generate by MvpAutoCodePlus plugin.
 */

public interface IDemo3Contract {
    interface View extends IView {
        void setData1(String rate);
        void setData2(List<TestBean2.DateBean> dataList);
        void setData3(String data3);
        void setData4(List<TestBean5> dataList);

    }

    interface Presenter extends IPresenter<View> {
        void getMergeData();
        void getFlatMapData(String type);
        void getZipData();

    }

    interface Model extends IModel {
        void getMergeData(Observer<Object> observer);
        void getFlatMapData(String type,Observer<TestBean3> observer);
        void getZipData(Observer<List<TestBean5>> observer);

    }
}
