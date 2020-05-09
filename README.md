# Phoenix

#Useage
    
      Phoenix.getInstance().keepAlive();
      
      Phoenix.getInstance().abadon();
      
#remark
        
      若手动结束掉应用进程后，仍需通知栏常驻，请注释掉RunningForegroundService中的stopForeground(true);
