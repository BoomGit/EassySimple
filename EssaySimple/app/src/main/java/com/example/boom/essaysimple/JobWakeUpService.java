package com.example.boom.essaysimple;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;

/**
 * Created by Boom on 2017/6/7.
 * 这个是为了防止应用被关闭，任然被杀死的问题
 * LOLLIPOP代表5.0 以上才有
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class JobWakeUpService extends JobService {
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //代码没写完
        JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);

        return START_STICKY;
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        //开启定时任务，定时轮询，看MessageService有没有被杀死
        //如果被杀死了启动轮询onStartJob


        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
