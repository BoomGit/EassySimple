package com.example.boom.essaysimple.badge.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.boom.essaysimple.R;

/**
 * Created by Boom on 2017/6/13.
 */

public class BadgeViewTestActivity extends AppCompatActivity {
    //明确消息数需要放在哪个view上
    TextView tv;
     RedTipView number;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_badge);
        tv= (TextView) findViewById(R.id.tv);
        //2.new BadgeView对象
        number = new RedTipView(this);
 /*       //后台获取的
        number.setBadgeCount(12);*/
        //设置到对应的控件上
        number.setTargetView(tv);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number.setVisibility(View.GONE);
            }
        });
    }
}
