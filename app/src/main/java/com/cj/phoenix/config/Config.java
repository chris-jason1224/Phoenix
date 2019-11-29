package com.cj.phoenix.config;

/**
 * Author:chris - jason
 * Date:2019-11-28.
 * Package:com.cj.phoenix.config
 * 配置类
 */
public class Config {

    public static String TAG = "PHOENIX";

    public static final int MSG_ON_START = 10;

    public static final int MSG_ON_STOP = 20;

    //保活服务后台运行周期
    public static long period = 5 * 1000;

    //JobService id
    public static int jobID = 9001;

    //前台服务 通知栏id
    public static final int notification_id = 1001;

    public static final String phoenix_channelId = "channel_phoenix_foreground_service";

    public static final String phoenix_channelName = "phoenix_foreground_service";



}
