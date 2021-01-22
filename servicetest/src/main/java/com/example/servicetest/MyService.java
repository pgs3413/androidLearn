package com.example.servicetest;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;


//一种服务整个应用只会存在一个服务实例
public class MyService extends Service {

    private DownloadBinder mBinder=new DownloadBinder();

    public class DownloadBinder  extends Binder{

        public void startDownload(){
            Log.d("data", "startDownload");
        }
        public int getProgress(){
            Log.d("data", "getProgress");
            return 0;
        }
    }

    public MyService() {
    }

    //与活动绑定前调用，返回一个Binder
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("data", "onBind");
       return mBinder;
    }

    //服务创建时调用
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("data", "onCreate executed");
        //使服务为前台服务
        NotificationManager manager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        //高版本需要渠道
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel=new NotificationChannel("1","name",NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel);
        }
        Notification notification=new NotificationCompat.Builder(this,"1")
                .setContentTitle("This is foreground service title")
                .setContentText("This is foreground service text")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .build();
        startForeground(1,notification);
    }

    //每次服务启动的时候调用，写具体的逻辑
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("data", "onStartCommand executed");
        return super.onStartCommand(intent, flags, startId);
    }

    //服务销毁时调用，回收资源
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("data", "onDestroy executed");
    }
}