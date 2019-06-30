package com.dl.mybaseproject.net;

import java.util.concurrent.TimeUnit;

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
    private static NetClient mNetClient;
    //不同项目需替换
    private static String BASE_URL="http://cys.m0571.com/api/Test/";
    private static String BASE_URL_MUSIC="http://motherstorys.m0571.com/mobileapi/public/motherstory/index.php/";

    private NetApi mNetApi;
    private final Retrofit retrofit;


    private NetClient(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient=new OkHttpClient.Builder().
                connectTimeout(60, TimeUnit.SECONDS).
                readTimeout(60, TimeUnit.SECONDS).
                writeTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
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
