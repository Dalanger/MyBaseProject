package com.dl.common.manager;

import android.app.Activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppManager {

    private static volatile AppManager instance = null;
    private static List<Activity> activityStack = new ArrayList<>();
    private static List<Map<String,Activity>> activityList = new ArrayList<>();

    private AppManager(){

    }

    public static AppManager getInstance(){
        if(instance == null){
            synchronized (AppManager.class){
                if(instance == null){
                    instance = new AppManager();
                }
            }
        }
        return instance;
    }




    public void addActivity(Activity activity) {
        Map<String,Activity> map =new HashMap<>();
        map.put(activity.getClass().getCanonicalName(),activity);
        activityList.add(map);
        activityStack.add(activity);
    }

    public void removeActivity(Activity activity) {
        activityStack.remove(activity);
        removeActivityByName(activity.getClass().getCanonicalName());
    }

    public void removeActivityByName(String activityName){
        for (int i=0;i<activityList.size();i++){
            Map<String,Activity> map = activityList.get(i);
            if (map.get(activityName)!=null){
                activityList.remove(i);
                activityStack.remove(map.get(activityName));
                map.get(activityName).finish();
                break;
            }
        }
    }

    public boolean isActiveByActivityName(String activityName){
        for (int i=0;i<activityList.size();i++){
            Map<String,Activity> map = activityList.get(i);
            if (map.get(activityName)!=null){
                return true;
            }
        }
        return false;
    }

    public int getActiveActivityNum(){
        return activityStack.size();
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = activityStack.size() - 1; i >= 0; i--) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
                activityStack.remove(i);
            }
        }
        activityStack.clear();
        activityList.clear();
    }

    /**
     * 结束所有Activity除了这个activity
     */
    public void finishAllActivityNoWith(String activityName) {
        for (int i = activityStack.size() - 1; i >= 0; i--) {
            if (null != activityStack.get(i) && !activityList.get(i).containsKey(activityName)) {
                activityStack.get(i).finish();
                activityStack.remove(i);
            }
        }
        activityStack.clear();
        activityList.clear();
    }
}
