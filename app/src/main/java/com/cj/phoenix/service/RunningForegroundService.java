package com.cj.phoenix.service;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import com.cj.phoenix.util.PhoenixUtil;
import com.cj.phoenix.config.Config;

/**
 * 正在运行时前台服务
 */
public class RunningForegroundService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
        PhoenixUtil.log_w("RunningForegroundService onCreate");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //生成通知栏，提示正在运行状态
        Notification notification = PhoenixUtil.createNotification(this);
        if(notification!=null){
            startForeground(Config.notification_id,notification);
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        stopForeground(true);
        PhoenixUtil.log_w("RunningForegroundService onDestroy");
        super.onDestroy();
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        stopForeground(true);
        PhoenixUtil.log_w("RunningForegroundService onTaskRemoved");
        super.onTaskRemoved(rootIntent);
    }


}
