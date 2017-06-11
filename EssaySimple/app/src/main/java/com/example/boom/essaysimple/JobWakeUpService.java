package com.example.boom.essaysimple;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import java.util.List;

/**
 * Created by Boom on 2017/6/7.
 * 这个是为了防止应用被关闭，任然被杀死的问题
 * LOLLIPOP代表5.0 以上才有
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class JobWakeUpService extends JobService {
    private final int jobWakeUpId = 1;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //开始一个轮寻
        JobInfo.Builder jobBuilder =new JobInfo.Builder(jobWakeUpId,new ComponentName(this,JobWakeUpService.class));
        //设置多久一次轮询
        jobBuilder.setPeriodic(2000);
        //参照电量优化
        JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(jobBuilder.build());
        return START_STICKY;
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        //开启定时任务，定时轮询，看MessageService有没有被杀死
        //如果被杀死了启动轮询onStartJob

        //判断服务有没有在运行
        boolean messageServiceAlive = serviceAlive(MessgaeService.class.getName());
        if (!messageServiceAlive){
            startService(new Intent(this,MessgaeService.class));
        }
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }

    /**
     * 判断某个服务是否正在运行的方法
     * @param serviceName
     * @return
     */
    private boolean serviceAlive(String serviceName){
        boolean isWork = false;
        ActivityManager myAM = (ActivityManager) getSystemService(Context.ACCESSIBILITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> myList = myAM.getRunningServices(100);
        if (myList.size()<=0){
            return false;
        }
        for (int i=0;i<myList.size();i++){
            String mName = myList.get(i).service.getClassName().toString();
            if (mName.equals(serviceName)){
                isWork = true;
                break;
            }
        }
        return isWork;
    }

}
