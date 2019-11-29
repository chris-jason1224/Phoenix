package com.cj.phoenix.service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import androidx.annotation.Nullable;
import com.cj.phoenix.R;
import com.cj.phoenix.util.PhoenixUtil;
import com.cj.phoenix.config.Config;

/**
 * 后台无声音乐保活服务
 */
public class SilentMusicLoopService extends Service {

    private MediaPlayer mMediaPlayer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        PhoenixUtil.log_w("Silent Music loop service onCreate");
        mMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.silent_music);
        mMediaPlayer.setLooping(true);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Notification notification = PhoenixUtil.createNotification(this);
        if(notification!=null){
            startForeground(Config.notification_id,notification);
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                startPlayMusic();
            }
        }).start();

        return START_STICKY;
    }

    private void startPlayMusic() {
        if (mMediaPlayer != null) {
            PhoenixUtil.log_w("Silent Music start play");
            mMediaPlayer.start();
        }
    }

    private void stopPlayMusic() {
        if (mMediaPlayer != null) {
            PhoenixUtil.log_w("Silent Music stop play");
            mMediaPlayer.stop();
        }
    }

    @Override
    public void onDestroy() {
        stopPlayMusic();
        PhoenixUtil.log_w("Silent Music loop service onDestroy");
        super.onDestroy();
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        stopForeground(true);
        stopPlayMusic();
        PhoenixUtil.log_w("Silent Music loop service onTaskRemoved");
        super.onTaskRemoved(rootIntent);
    }
}
