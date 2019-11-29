package com.cj.phoenix.Lollipop_marshmallow;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import com.cj.phoenix.Lollipop_marshmallow.job.L_M_WorkService;
import com.cj.phoenix.config.Config;
import java.util.List;

/**
 * Package:com.cj.phoenix.Lollipop_marshmallow
 */
public class L_M_WorkStrategy implements I_L_M_Strategy {

    private Context mContext;
    private JobScheduler mScheduler;
    private JobInfo jobInfo;

    private L_M_WorkStrategy(){

    }

    private static class Holder{
        private static final L_M_WorkStrategy instance = new L_M_WorkStrategy();
    }

    public static L_M_WorkStrategy getInstance(){
        return Holder.instance;
    }

    @Override
    public void work_on_Lollipop(Context context) {
        mContext = context;
        initScheduler();
        mScheduler.cancelAll();
        mScheduler.schedule(jobInfo);
    }

    @Override
    public void work_on_Marshmallow(Context context) {
        mContext = context;
        initScheduler();
        mScheduler.cancelAll();
        mScheduler.schedule(jobInfo);
    }

    @Override
    public void stopWork() {
        List<JobInfo> infos = mScheduler.getAllPendingJobs();
        if(infos!=null){
            for (JobInfo info:infos){
                if(info.getId() == Config.jobID ){
                    mScheduler.cancel(info.getId());
                }
            }
        }
    }

    private void initScheduler(){

        if(mScheduler ==null){
            mScheduler = (JobScheduler) mContext.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        }

        JobInfo.Builder jBuilder = new JobInfo.Builder(Config.jobID,new ComponentName(mContext.getPackageName(), L_M_WorkService.class.getName()));
        //任务执行间隔
        jBuilder.setPeriodic(Config.period);
        //当你的设备重启之后你的任务是否还要继续执行
        jBuilder.setPersisted(true);
        //它表明需要任意一种网络才使得任务可以执行
        jBuilder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
        //不需要接通电源
        jBuilder.setRequiresCharging(false);
        //任务只有当用户没有在使用该设备且有一段时间没有使用时才会启动该任务
        jBuilder.setRequiresDeviceIdle(false);

        jobInfo = jBuilder.build();

    }



}
