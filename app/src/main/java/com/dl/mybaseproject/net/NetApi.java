package com.dl.mybaseproject.net;

import com.dl.mybaseproject.demo3.bean.TestBean1;
import com.dl.mybaseproject.demo3.bean.TestBean2;
import com.dl.mybaseproject.demo3.bean.TestBean3;
import com.dl.mybaseproject.demo3.bean.TestBean4;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


/**
 * Created by dalang on 2018/8/30.
 * 测试接口
 */
public interface NetApi {

    //获取保价比率
    @POST("GetProtection")
    Observable<TestBean1> getInsureRate();
    //获取物品分类
    @POST("GetGoodsType")
    Observable<TestBean2> getGoodsType();

    //获取文案列表
    @FormUrlEncoded
    @POST("GetNews")
    Observable<TestBean4> getAgreementList(@Field("Type")String type);

    //获取文案详情
    @FormUrlEncoded
    @POST("GetNewsForSingle")
    Observable<TestBean3> getAgreementDetail(@Field("Id")String type_id);

}
