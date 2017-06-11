package com.example.boom.essaysimple;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.boom.essaysimple.ipc.MyService;
import com.example.boom.framelibrary.BaseSkinActivity;

/**
 * Created by Boom on 2017/6/9.
 */

public class SecondActivity extends BaseSkinActivity {
    private UserAidl mUserAidl;
    Button getName;
    Button getPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
        initView();
        initData();
    }

    private ServiceConnection conn =new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //连接好了
            mUserAidl = UserAidl.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            //断开连接
        }
    };
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_second);
    }

    @Override
    public void initTitle() {

    }

    @Override
    public void initView() {
        getName=(Button)findViewById(R.id.getName);
        getName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Toast.makeText(SecondActivity.this,mUserAidl.getUserName(), Toast.LENGTH_SHORT).show();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        getPassword=(Button)findViewById(R.id.getPassword);
        getPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Toast.makeText(SecondActivity.this, mUserAidl.getUserPassword(), Toast.LENGTH_SHORT).show();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void initData() {
        //启动一个服务，等待A应用过来连接 （服务端）
        startService(new Intent(this, MyService.class));

        //隐士意图 放在A应用中写  （客户端）
        Intent intent = new Intent(this,MyService.class);
        bindService(intent,conn, Context.BIND_AUTO_CREATE);
    }
}
