package com.jiufang.interviewsystem.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;


import java.util.LinkedList;
import java.util.List;


public class MyApplication extends Application {
    public static MyApplication instance;
    private static List<Activity> activityList = new LinkedList<Activity>();
    public static Context applicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        applicationContext = this;
        /*----------------------------------*/

    }

    // 单例实现返回MyApplication实例
    public static MyApplication getInstance() {
        if (null == instance) {
            instance = new MyApplication();
        }
        return instance;
    }

    // Activity加入到List
    public static void addActivity(Activity activity) {
        activityList.add(activity);
    }


}
