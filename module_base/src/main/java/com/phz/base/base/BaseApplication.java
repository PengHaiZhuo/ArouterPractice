package com.phz.base.base;

import android.app.Application;

/**
 * @author haizhuo
 * @introduction 基类的工程类
 */
public class BaseApplication extends Application {
    private static volatile BaseApplication application;

    /**
     * 获取application实例
     * @return
     */
    public static BaseApplication getInstance(){
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application=this;
    }
}
