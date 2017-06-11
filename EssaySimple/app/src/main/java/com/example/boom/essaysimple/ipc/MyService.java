package com.example.boom.essaysimple.ipc;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.example.boom.essaysimple.UserAidl;

/** IPC服务端
 * Created by Boom on 2017/6/9.
 */

public class MyService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    //mBinder的实例
    private final UserAidl.Stub mBinder = new UserAidl.Stub() {
        @Override
        public String getUserName() throws RemoteException {
            return "Boom";
        }

        @Override
        public String getUserPassword() throws RemoteException {
            return "123456";
        }
    };
}
