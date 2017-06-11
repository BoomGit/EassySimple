package com.example.boom.essaysimple;

import android.content.Intent;
import android.os.Build;

import com.example.boom.framelibrary.BaseSkinActivity;

/**
 * Created by Boom on 2017/6/9.
 */

public class ThirdActivity extends BaseSkinActivity {
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_third);
    }

    @Override
    public void initTitle() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
              /**/
        //启动服务
        startService(new Intent(this,MessgaeService.class));
        startService(new Intent(this,GuardService.class));
        //必须大于5.0  ，不然会崩掉
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            startService(new Intent(this,JobWakeUpService.class));
        }
    }
}
