package com.example.boom.essaysimple;

import android.app.Notification;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.widget.Toast;

import com.boom.aidl.ProcessConnection;

/**
 * Created by Boom on 2017/6/7.
 */

public class GuardService extends Service {
    private final int GuardId = 1;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //提高进程优先级
        startForeground(GuardId,new Notification());
        //绑定建立连接
        bindService(new Intent(this,Service.class),mserviceConnection, Context.BIND_IMPORTANT);
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
            Toast.makeText(GuardService.this, "建立连接", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            //断开连接  重新启动，重新绑定
            startService(new Intent(GuardService.this,MessgaeService.class));
            bindService(new Intent(GuardService.this,MessgaeService.class),mserviceConnection,Context.BIND_IMPORTANT);
        }
    };
}
