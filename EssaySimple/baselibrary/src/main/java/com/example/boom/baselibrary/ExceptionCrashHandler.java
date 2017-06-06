package com.example.boom.baselibrary;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Boom on 2017/6/5.
 * 单例设计模式的异常捕捉
 */

public class ExceptionCrashHandler implements Thread.UncaughtExceptionHandler {
    private Context mContext;
    private static final String TAG ="ExceptionCrashHandler";
    private Thread.UncaughtExceptionHandler mDefaultExceptionHandler;
    public void init(Context context){
        this.mContext=context;
        //设置全局的异常类为本类
        Thread.currentThread().setUncaughtExceptionHandler(this);

        mDefaultExceptionHandler = Thread.currentThread().getDefaultUncaughtExceptionHandler();
    }

    private static ExceptionCrashHandler myInstance;
    public static ExceptionCrashHandler getMyInstance(){
        if (myInstance==null){
            //解决多并发的问题
            synchronized (ExceptionCrashHandler.class){
                if (myInstance==null){
                myInstance =new ExceptionCrashHandler();
                }
            }

        }
        return myInstance;
    }

    @Override
    public void uncaughtException(Thread t, Throwable ex) {
        //全局异常
        Log.e(TAG, "报异常了" );
        //写入到本地文件  ex 当地版本  手机信息

        //1.奔溃的详细信息

        //2.应用信息 包名  版本号

        //3.手机信息

        //4.保存当前文件，等应用再次启动在上传 （上传文件不在这里处理）

        String crashFileName = saveInfoToSd(ex);
        Log.e(TAG, "fileName -->"+crashFileName );
        cacheCrashFile(crashFileName);
        //用系统默认处理
        //mDefaultExceptionHandler.uncaughtException(t,ex);
    }

    private HashMap<String,String> obtainSimpleInfo(Context context){
        HashMap<String,String> map =new HashMap<>();
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(),PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        map.put("versionName",packageInfo.versionName);
        map.put("versionCode",""+packageInfo.versionCode);
        map.put("MODEL", ""+Build.MODEL);
        map.put("SDK_INT",""+Build.VERSION.SDK_INT);
        map.put("PRODUCT",""+Build.PRODUCT);
        map.put("MODEL_INFO",getMoblieInfo());
        return map;
    }

    private String getMoblieInfo() {
        StringBuffer sb = new StringBuffer();
        try {
            //利用反射获取Build的所有信息
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field:fields){
            field.setAccessible(true);
            String name = field.getName();
            //为什么用null  因为是static  所以为null
            String value = field.get(null).toString();
            sb.append(name+"="+value);
            sb.append("\n");
        }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 获取系统异常的错误信息
     */
    private String obtainExceptionInfo(Throwable tw){
        //java基础异常   把Throwable 转换为 string
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        tw.printStackTrace(pw);
        pw.close();
        return sw.toString();
    }

    /**
     * 把异常输入到SD卡上
     * @return
     */
    private String saveInfoToSd(Throwable ex){
        String fileName = null;
        StringBuffer sb = new StringBuffer();
        //手机信息+应用信息
        for (Map.Entry<String,String> entry : obtainSimpleInfo(mContext).entrySet()){
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key).append("=").append(value).append("\n");
        }
        //奔溃的详细信息
        sb.append(obtainExceptionInfo(ex));
        //保存文件   手机应用的目录，并没有拿手机的sd卡目录，6.0以上  打开sd卡需要我们动态的申请权限
        if (Environment.getExternalStorageDirectory().equals(Environment.MEDIA_MOUNTED)){
            File dir = new File(mContext.getFilesDir()+File.separator+"crash"+File.separator);
            //先删除之前的异常信息
            if (dir.exists()){
                //删除该目录下的子文件
                deleteDir(dir);
            }
            //再重新创建文件
            if (!dir.exists()){
                dir.mkdir();
            }
            try {
                fileName = dir.toString()
                        + File.separator
                        + getAssignTime("yyyy_MM_dd_HH_mm")+".txt";
                FileOutputStream fos = new FileOutputStream(fileName);
                fos.write(sb.toString().getBytes());
                fos.flush();
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
        return fileName;
    }

    /**
     * 格式化日期
     * @param dateFormatStr
     * @return
     */
    private String getAssignTime(String dateFormatStr) {
        DateFormat dateFormat =new SimpleDateFormat(dateFormatStr);
        long currentTime =System.currentTimeMillis();
        return dateFormat.format(dateFormatStr);
    }

    /**
     * 删除文件
     * @param dir
     * @return
     */
    private boolean deleteDir(File dir){
        if (dir.isDirectory()){
            File[] children =dir.listFiles();
            //递归删除目录中的子目录下
            for (int i = 0;i<children.length;i++){
                children[i].delete();
            }
        }
        return true;
    }

    /**
     * 缓存奔溃日志信息
     */
    private  void cacheCrashFile(String fileName){
        SharedPreferences sp = mContext.getSharedPreferences("crash",Context.MODE_APPEND);
        sp.edit().putString("CRASH_FILE_NAME",fileName).commit();
    }

    /**
     * 获取奔溃文件名称
     */
    public File getCrashFile(){
        String crashFileName =mContext.getSharedPreferences("crash",Context.MODE_APPEND).getString("CRASH_FILE_NAME","");
        return new File(crashFileName);
    }
}
