package com.example.boom.essaysimple;

import android.app.Notification;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.boom.aidl.ProcessConnection;

/**
 * Created by Boom on 2017/6/7.
 */

public class MessgaeService extends Service {
    private final int MessageId = 1;

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    Log.e("", "等待接收消息" );
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //提高进程的优先级
        startForeground(MessageId,new Notification());
        //绑定建立连接
        bindService(new Intent(this,GuardService.class),mserviceConnection,Context.BIND_IMPORTANT );
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new ProcessConnection.Stub(){};
    }

    private ServiceConnection mserviceConnection= new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //连接上
            Toast.makeText(MessgaeService.this, "建立连接", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            //断开连接  重新启动，重新绑定
            startService(new Intent(MessgaeService.this,GuardService.class));
            bindService(new Intent(MessgaeService.this,GuardService.class),mserviceConnection,Context.BIND_IMPORTANT);
        }
    };
}
