package com.dl.mybaseproject.demo3.model;

import com.dl.common.base.BaseModel;
import com.dl.mybaseproject.demo3.bean.TestBean2;
import com.dl.mybaseproject.demo3.bean.TestBean3;
import com.dl.mybaseproject.demo3.bean.TestBean4;
import com.dl.mybaseproject.demo3.bean.TestBean5;
import com.dl.mybaseproject.demo3.contract.IDemo3Contract;
import com.dl.mybaseproject.net.NetApi;
import com.dl.mybaseproject.net.NetClient;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


/**
 * Description :
 *
 * @author dalang
 * @date 2018/9/17  16:59
 * - generate by MvpAutoCodePlus plugin.
 */

public class Demo3Model extends BaseModel implements IDemo3Contract.Model {

    @Override
    public void getMergeData(Observer<Object> observer) {
        NetApi netApi= NetClient.getInstance().getNetApi();
        Observable.merge(netApi.getInsureRate(),netApi.getGoodsType())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    @Override
    public void getFlatMapData(String type,Observer<TestBean3> observer) {
        final NetApi netApi= NetClient.getInstance().getNetApi();
        netApi.getAgreementList(type)
                .flatMap((Function<TestBean4, ObservableSource<TestBean3>>) testBean4 -> netApi.getAgreementDetail(testBean4.getDate().get(0).getId())).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    @Override
    public void getZipData(Observer<List<TestBean5>> observer) {
        NetApi netApi= NetClient.getInstance().getNetApi();
        Observable.zip(netApi.getAgreementList("员工须知"), netApi.getGoodsType(), (testBean4, testBean2) -> {
            //结合数据为一体
            List<TestBean5> list=new ArrayList<>();
            List<TestBean4.DateBean> data1=testBean4.getDate();
            List<TestBean2.DateBean> data2=testBean2.getDate();
            for (int i = 0; i < data1.size(); i++) {
                list.add(new TestBean5(data2.get(i).getName(),data1.get(i).getTitle(),data1.get(i).getAddtime()));
            }
            return list;
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }


}

