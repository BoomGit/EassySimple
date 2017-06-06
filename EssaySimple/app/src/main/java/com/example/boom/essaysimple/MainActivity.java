package com.example.boom.essaysimple;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.example.boom.baselibrary.ExceptionCrashHandler;
import com.example.boom.framelibrary.BaseSkinActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

public class MainActivity extends BaseSkinActivity {
    private static final String TAG = "MainActivity";

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    public void initTitle() {
        //初始化头部
    }

    @Override
    public void initView() {
        //初始化View
    }

    @Override
    public void initData() {
        //初始化数据
        //获取上次的奔溃信息上传到服务器
        File crashFile = ExceptionCrashHandler.getMyInstance().getCrashFile();

        /*        if (!crashFile.exists()){
         //上传到服务器
            try {
                InputStreamReader fileReader = new InputStreamReader(new FileInputStream(crashFile));
                char[] buffer =new char[1024];
                int len = 0;
                while((len=fileReader.read(buffer))!=-1){
                 String str  = new String(buffer,0,len);
                    Log.d(TAG, str);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/

        //每次启动的时候  去后台获取差分包  fix.apatch 然后修复本地BUG

        //测试  直接获取本地内存卡里面的 fix.apatch


    }
}
