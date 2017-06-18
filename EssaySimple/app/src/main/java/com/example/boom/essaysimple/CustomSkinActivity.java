package com.example.boom.essaysimple;

import android.app.Service;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.boom.framelibrary.BaseSkinActivity;

import java.io.File;
import java.lang.reflect.Method;

/**
 * Created by Boom on 2017/6/17.
 */

public class CustomSkinActivity extends BaseSkinActivity {
    Button replace;
    ImageView img_iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
        initView();
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_skin);
    }

    @Override
    public void initTitle() {

    }

    @Override
    public void initView() {
        replace = (Button)findViewById(R.id.replace);
        img_iv = (ImageView)findViewById(R.id.img_iv);
        replace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });
    }

    private void loadData() {
        try {
            //读取本地的一个  .skin里面的资源
            Resources suoerRes = getResources();
            //创建AssetManager  因为 new AssetManager()是hide 那么我们得通过反射
            AssetManager asset = AssetManager.class.newInstance();
            //添加本地下载好的资源皮肤  native层 c和c++怎么搞的 又是hide 所以我们得用反射 "addAssetPath" 代表需要调用的方法名 String.class代表参数类型
            Method method = AssetManager.class.getDeclaredMethod("addAssetPath", String.class);
            //method.setAccessible(true);  当方法是私有的设置
            //把skin文件放在根目录下  skin文件是skinpugin生成的
            method.invoke(asset, Environment.getExternalStorageDirectory().getAbsolutePath()
                    + File.separator + "red.skin");
            Resources resource = new Resources(asset,suoerRes.getDisplayMetrics(),suoerRes.getConfiguration());
            //获取资源id
            int drawableId = resource.getIdentifier("img_src","drawable","com.example.boom.skinpugin");
            Drawable drawable = resource.getDrawable(drawableId);
            img_iv.setImageDrawable(drawable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initData() {

    }
}
