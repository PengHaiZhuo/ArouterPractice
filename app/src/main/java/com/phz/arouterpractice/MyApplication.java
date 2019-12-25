package com.phz.arouterpractice;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * @author haizhuo
 * @introduction 自定义工程类
 */
public class MyApplication extends Application {

    private static volatile MyApplication myApplication;

    /**
     * 获取application实例
     * @return
     */
    public static MyApplication getInstance(){
        return myApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication=this;
        // 这两行必须写在init之前，否则这些配置在init过程中将无效
        if (BuildConfig.DEBUG) {
            // 打印日志
            ARouter.openLog();
            // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
            ARouter.openDebug();
            // 打印日志的时候打印线程堆栈
            ARouter.printStackTrace();
        }
        // 尽可能早，推荐在Application中初始化
        ARouter.init(myApplication);
    }

    /**
     * 注：onTerminate只会在模拟器上调用，商用设备上不会执行
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
