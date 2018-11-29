package com.dl.common.utils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by dalang on 2018/8/8.
 */

public class TimeTaskUtil {
    private Timer timer;
    private TimerTask task;
    private long time;
    private long startTime;

    public TimeTaskUtil(long startTime, long time, TimerTask task) {
        this.task = task;
        this.time = time;
        this.startTime = startTime;
        if (timer == null){
            timer=new Timer();
        }
    }

    public void start(){
        timer.schedule(task, startTime, time);//每隔time时间段就执行一次 startTime第一次开始隔多久 单位毫秒
    }

    public void stop(){
        if (timer != null) {
            timer.cancel();
            if (task != null) {
                task.cancel();  //将原任务从队列中移除
            }
        }
    }

}
