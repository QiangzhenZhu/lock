package com.hzdongcheng.parcellocker;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.hzdongcheng.components.toolkits.utils.Log4jUtils;

import java.lang.ref.WeakReference;

/**
 * Created by Peace on 2017/9/14.
 */
public class DBSApplication extends Application {
    private static String SDCARD_PATH = "";
    public static String HOME_PATH = "/hzdongcheng";
    private static WeakReference<Context> context;
    Log4jUtils log4jUtils;

    public static Context getContext() {
        return context.get();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = new WeakReference<>(getApplicationContext());

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) { //存在SD卡
            SDCARD_PATH = Environment.getExternalStorageDirectory().toString();
        } else {
            SDCARD_PATH = Environment.getDataDirectory().toString();
        }

        Log4jUtils.initLog4jInAndroid(SDCARD_PATH + HOME_PATH + "/logs/app/log");

        Log4jUtils log4jUtils = Log4jUtils.createInstanse(this.getClass());
        log4jUtils.info("***************************************************");
        log4jUtils.info("*                 应用程序已启动                    *");
        log4jUtils.info("***************************************************");

        // 异常处理，不需要处理时注释掉这两句即可！
        CrashHandler crashHandler = new CrashHandler(SDCARD_PATH + HOME_PATH + "/logs/app/error/");

        // 注册crashHandler
        crashHandler.init(getApplicationContext());
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (log4jUtils != null) {
            log4jUtils.warn("[Application]  **系统内存占用较高**");
        }
    }
}
