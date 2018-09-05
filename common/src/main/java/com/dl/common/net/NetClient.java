package com.dl.common.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dalang on 2018/8/30.
 * 网络框架
 */
public class NetClient {
    private static  NetClient mNetClient;
    //不同项目需替换
    private static String BASE_URL="http://fss.m0571.com/api/Test/";

    private NetApi mNetApi;
    private final Retrofit retrofit;

    private NetClient(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient=new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
        Gson gson=new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

    }

    public static synchronized NetClient getInstance(){
        if (mNetClient==null) {
            mNetClient=new NetClient();
        }
        return mNetClient;
    }

    public NetApi getNetApi(){
        if (mNetApi ==null) {
            mNetApi = retrofit.create(NetApi.class);
        }
        return mNetApi;
    }
}
