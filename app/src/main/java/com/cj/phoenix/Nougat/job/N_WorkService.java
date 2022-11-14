package com.cj.phoenix.Nougat.job;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.Nullable;
import com.cj.phoenix.Nougat.N_Strategy;
import com.cj.phoenix.util.PhoenixUtil;
import com.cj.phoenix.config.Config;


/**
 * Package:com.cj.phoenix.Nougat.job
 * 针对 7.0以上系统 后台保活服务
 */
public class N_WorkService extends JobService {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public boolean onStartJob(final JobParameters jobParameters) {
        sendMessage(Config.MSG_ON_START, jobParameters);

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                N_Strategy.getInstance().timePlus(System.currentTimeMillis());
                //重启service
                if(N_Strategy.getInstance().isNeedReschedule()){
                    N_Strategy.getInstance().initScheduler();
                }

                jobFinished(jobParameters,false);
            }
        });

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        sendMessage(Config.MSG_ON_STOP, jobParameters);
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private Handler mJobHandler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(final Message msg) {
            //JobParameters p = (JobParameters) msg.obj;
            int w = msg.what;

            switch (w) {
                case Config.MSG_ON_START:
                    PhoenixUtil.log_w( "service on start");
                    break;
                case Config.MSG_ON_STOP:
                    PhoenixUtil.log_w("service on stop");
                    break;
            }

            return true;
        }

    });


    private void sendMessage(int what, @Nullable Object params) {
        Message m = Message.obtain();
        m.what = what;
        m.obj = params;
        mJobHandler.sendMessage(m);
    }





}
