package com.example.boom.baselibrary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;

/**
 * Created by Boom on 2017/6/4.
 */

public abstract class BaseActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        //设置布局
        setContentView();
        //一些特定的算法    比如初始化第三方

        //初始化头部
        initTitle();
        //初始化界面
        initView();
        //初始化数据
        initData();
    }

    public abstract void setContentView();

    public abstract void initTitle();

    public abstract void initView();

    public abstract void initData();

    /**
     * 复写startActivity
     *
     * @param clazz
     */
    protected void startActivity(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    /**
     * 用泛型简化findviewByid
     *
     * @param viewId
     * @param <T>
     * @return
     */
    protected <T extends View> T viewById(int viewId) {
        return (T) findViewById(viewId);
    }

}
