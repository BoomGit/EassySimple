package com.example.boom.baselibrary;

import android.app.Application;
import android.content.pm.PackageManager;

import com.alipay.euler.andfix.patch.PatchManager;

/**
 * Created by Boom on 2017/6/5.
 */

public class BaseApp extends Application {
    //我们需要添加依赖库 用andfix
    public static PatchManager patchManager;

    @Override
    public void onCreate() {
        super.onCreate();
        //设置全局异常捕捉类
        ExceptionCrashHandler.getMyInstance().init(this);

        //初始化阿里的热修复
        patchManager = new PatchManager(this);
        //初始化版本，获取当前应用的版本\
        double versionCode = 1.0;
        try {
            versionCode = this.getPackageManager().getPackageInfo(this.getPackageName(), PackageManager.GET_ACTIVITIES).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            versionCode = 1.0;
        }
        patchManager.init(String.valueOf(versionCode));
        //patchManager.init("1.0");
        //加载之前的apatch包
        patchManager.loadPatch();

    }

}
