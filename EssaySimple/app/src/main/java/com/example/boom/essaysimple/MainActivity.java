package com.example.boom.essaysimple;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.example.boom.baselibrary.BaseApp;
import com.example.boom.baselibrary.ExceptionCrashHandler;
import com.example.boom.framelibrary.BaseSkinActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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
        //启动服务
        startService(new Intent(this,MessgaeService.class));
        startService(new Intent(this,GuardService.class));
        //必须大于5.0  ，不然会崩掉
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            startService(new Intent(this,JobWakeUpService.class));
        }

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
        File fixFile = new File(Environment.getExternalStorageDirectory(), "fix.aptach");
        if (fixFile.exists()) {
            //修复Bug
            try {
                BaseApp.patchManager.addPatch(fixFile.getAbsolutePath());
                Toast.makeText(this, "修复成功", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Toast.makeText(this, "修复失败", Toast.LENGTH_SHORT).show();
            }
        }


    }
}
