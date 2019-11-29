package com.cj.phoenix.util;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;
import androidx.annotation.NonNull;

import com.cj.phoenix.BuildConfig;
import com.cj.phoenix.R;

import java.util.List;
import static android.content.Context.NOTIFICATION_SERVICE;
import static com.cj.phoenix.config.Config.TAG;
import static com.cj.phoenix.config.Config.phoenix_channelId;
import static com.cj.phoenix.config.Config.phoenix_channelName;

/**
 * Author:chris - jason
 * Date:2019-11-29.
 * Package:com.cj.phoenix
 */
public class PhoenixUtil {

    /**
     * 判断app是否处于后台
     *
     * @param context
     * @return
     */
    public static boolean isAppBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    //显示通知栏
    public static Notification createNotification(@NonNull Context context) {

        PackageManager packageManager = context.getPackageManager();
        String pkgName = context.getPackageName();

        Class clz = null;
        if (packageManager != null) {
            Intent intent = packageManager.getLaunchIntentForPackage(pkgName);
            ComponentName componentName = intent.getComponent();
            if (componentName != null) {
                clz = componentName.getClass();
            }
        }

        if (clz == null) {
            return null;
        }

        Notification.Builder builder = new Notification.Builder(context.getApplicationContext());
        Intent nfIntent = new Intent(context, clz);

        builder.setContentIntent(PendingIntent.getActivity(context, 0, nfIntent, 0))
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.icon_phoenix_large))
                .setSmallIcon(R.mipmap.icon_phoenix_small)
                .setContentTitle("Phoenix")
                .setContentText("Phoenix is running")
                .setWhen(System.currentTimeMillis()).
                setAutoCancel(false);

        //调用这个方法把服务设置成前台服务
        //8.0之上必须设置channel
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(phoenix_channelId, phoenix_channelName, NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(false);
            notificationChannel.enableVibration(false);
            notificationChannel.setShowBadge(false);//不显示角标
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_SECRET);
            NotificationManager manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            manager.createNotificationChannel(notificationChannel);
            builder.setChannelId(phoenix_channelId);
        }

        Notification notification = builder.build();
        notification.defaults = Notification.DEFAULT_SOUND; //设置为默认的声音
        notification.flags = Notification.FLAG_FOREGROUND_SERVICE | Notification.FLAG_ONGOING_EVENT;
        return notification;

    }

    public static void log_e(String msg){
        if(BuildConfig.DEBUG){
            Log.e(TAG,msg);
        }
    }

    public static void log_w(String msg){
        if(BuildConfig.DEBUG){
            Log.w(TAG,msg);
        }
    }

}
