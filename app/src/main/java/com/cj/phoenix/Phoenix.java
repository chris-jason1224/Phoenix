package com.cj.phoenix;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Parcel;

import androidx.annotation.NonNull;

import com.cj.phoenix.Lollipop_marshmallow.L_M_WorkStrategy;
import com.cj.phoenix.Nougat.N_Strategy;
import com.cj.phoenix.core.IExecute;
import com.cj.phoenix.util.PhoenixUtil;
import com.cj.phoenix.service.RunningForegroundService;
import com.cj.phoenix.service.SilentMusicLoopService;

/**
 * Package:com.cj.phoenix.core
 */
public class Phoenix {

    private Context mContext;
    private Intent runningIntent;
    private Intent silentMusicIntent;
    private int v = Build.VERSION.SDK_INT;

    private Phoenix() {
    }

    private static class Holder {
        private static final Phoenix instance = new Phoenix();
    }

    public static Phoenix getInstance() {
        return Holder.instance;
    }


    /**
     * 开启保活
     * 禁止在app进入后台之后再调用该方法
     *
     * @param context
     */
    public void keepAlive(@NonNull Context context) {

        if (PhoenixUtil.isAppBackground(context.getApplicationContext())) {
            throw new RuntimeException("cant start PHOENIX after enter background");
        }

        this.mContext = context.getApplicationContext();

        if (runningIntent == null) {
            runningIntent = new Intent(mContext, RunningForegroundService.class);
        }
        if (silentMusicIntent == null) {
            silentMusicIntent = new Intent(mContext, SilentMusicLoopService.class);
        }

        /**启动前台服务**/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mContext.startForegroundService(runningIntent);
            mContext.startForegroundService(silentMusicIntent);
        } else {
            mContext.startService(runningIntent);
            mContext.startService(silentMusicIntent);
        }

        //5.x 21、22
        if (v == Build.VERSION_CODES.LOLLIPOP || v == Build.VERSION_CODES.LOLLIPOP_MR1) {
            L_M_WorkStrategy.getInstance().work_on_Lollipop(context);
            return;
        }

        //6.0 23
        if (v == Build.VERSION_CODES.M) {
            L_M_WorkStrategy.getInstance().work_on_Marshmallow(context);
            return;
        }

        //7.x 24、25
        if (v == Build.VERSION_CODES.N || v == Build.VERSION_CODES.N_MR1) {
            N_Strategy.getInstance().work_on_Nougat(context);
            return;
        }

        //8.x 26、27
        if (v == Build.VERSION_CODES.O || v == Build.VERSION_CODES.O_MR1) {
            N_Strategy.getInstance().work_on_Nougat(context);
            return;
        }

        //9.0 28
        if (v == Build.VERSION_CODES.P) {
            N_Strategy.getInstance().work_on_Nougat(context);
            return;
        }

        //10.0 29
        if (v == Build.VERSION_CODES.Q) {
            N_Strategy.getInstance().work_on_Nougat(context);
            return;
        }

    }

    /**
     * 停止保活
     */
    public void abandon() {
        if (mContext != null) {
            if (runningIntent != null) {
                mContext.stopService(runningIntent);
            }
            if (silentMusicIntent != null) {
                mContext.stopService(silentMusicIntent);
            }
        }

        //5.x 21、22
        if (v == Build.VERSION_CODES.LOLLIPOP || v == Build.VERSION_CODES.LOLLIPOP_MR1) {
            L_M_WorkStrategy.getInstance().stopWork();
            return;
        }

        //6.0 23
        if (v == Build.VERSION_CODES.M) {
            L_M_WorkStrategy.getInstance().stopWork();
            return;
        }

        //7.x 24、25
        if (v == Build.VERSION_CODES.N || v == Build.VERSION_CODES.N_MR1) {
            N_Strategy.getInstance().stopWork();
            return;
        }

        //8.x 26、27
        if (v == Build.VERSION_CODES.O || v == Build.VERSION_CODES.O_MR1) {
            N_Strategy.getInstance().stopWork();
            return;
        }

        //9.0 28
        if (v == Build.VERSION_CODES.P) {
            N_Strategy.getInstance().stopWork();
            return;
        }

        //10.0 29
        if (v == Build.VERSION_CODES.Q) {
            N_Strategy.getInstance().stopWork();
            return;
        }

    }

    private IExecute execute = new IExecute() {
        @Override
        public void exec() {

        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {

        }
    };



}
