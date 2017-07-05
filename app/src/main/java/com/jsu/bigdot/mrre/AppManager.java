package com.jsu.bigdot.mrre;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.Stack;

/**
 * Created by Bigdot on 2017/7/2.
 */

/*
    堆栈式管理activity
 */
public class AppManager {

    public static String BaseUrl="http://192.168.137.1:8080/Solidarity/";

    private static Stack<Activity> activityStack;

    private static AppManager instance;

    private AppManager(){

    }

    public static AppManager getAppManager(){

        if(instance == null){
            instance = new AppManager();
        }
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        return instance;
    }

    /*
        获得指定Activity
     */
    public static Activity getActivity(Class<?> clazz){

        if(activityStack != null){
            for(Activity activity : activityStack){
                if(activity.getClass().equals(clazz)){
                    return activity;
                }
            }
        }
        return null;
    }

    /*
        获取当前Activity
     */
    public static Activity currentActivity(){
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /*
        添加指定Activity
     */
    public void addActivity(Activity activity){
        activityStack.add(activity);
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null && activityStack.contains(activity)) {
            activityStack.remove(activity);
            activity.finish();
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
                break;
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                finishActivity(activityStack.get(i));
            }
        }
        activityStack.clear();
    }

    /*
        退出App
     */

    public void AppExit(Context context){

        try {
            finishAllActivity();
            // System.exit(0);
        } catch (Exception e) {

        }

        // 获取packagemanager的实例
        try {
            ActivityManager activityMgr = (ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.killBackgroundProcesses(context.getPackageName());
            activityStack = null;
            instance=null;
            System.exit(0);
        } catch (Exception e) {
            System.exit(0);
        }
    }
}
