package com.dl.common.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.dl.common.utils.ToastUtil;

/**
 * created by dalang at 2018/12/13
 * decripetion:
 */
public class NetReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if(networkInfo!=null && networkInfo.getType()== ConnectivityManager.TYPE_WIFI){

        }
        else if(networkInfo!=null && networkInfo.getType()== ConnectivityManager.TYPE_MOBILE){

        }
        else{
            ToastUtil.error("无网络连接");
        }
    }

}
