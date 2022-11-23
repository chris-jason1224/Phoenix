# Phoenix

#new
        支持5s以下周期任务

#Useage
    
      Phoenix.getInstance().keepAlive();
      
      Phoenix.getInstance().abadon();
      
#remark
        
      若手动结束掉应用进程后，仍需通知栏常驻，请注释掉RunningForegroundService和SilentMusicLoopService中的onTaskMoved()方法
