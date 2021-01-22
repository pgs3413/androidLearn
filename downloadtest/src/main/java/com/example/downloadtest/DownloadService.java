package com.example.downloadtest;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import java.io.File;

public class DownloadService extends Service {

    private NotificationManager manager;
    private DownLoadTask downLoadTask;
    private String downloadUrl;
    private DownLoadBinder downLoadBinder=new DownLoadBinder();

    private DownloadListener listener=new DownloadListener() {
        @Override
        public void onSuccess() {
            downLoadTask=null;
            stopForeground(true);
            manager.notify(1,getNotification("Downloading Success",-1));
            Toast.makeText(DownloadService.this.getApplicationContext(),"Downloading Success",Toast.LENGTH_SHORT).show();
            Log.d("data", "onSuccess");
        }

        @Override
        public void onProgress(int progress) {
            manager.notify(1,getNotification("Downloading...",progress));
        }

        @Override
        public void onFailed() {
            downLoadTask=null;
            stopForeground(true);
            manager.notify(1,getNotification("Downloading Failed",-1));
            Toast.makeText(DownloadService.this.getApplicationContext(),"Downloading Failed",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCanceled() {
            downLoadTask=null;
            stopForeground(true);
            Toast.makeText(DownloadService.this.getApplicationContext(),"Downloading Failed",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPaused() {
            downLoadTask=null;
            Toast.makeText(DownloadService.this.getApplicationContext(),"Paused",Toast.LENGTH_SHORT).show();
        }
    };

    public DownloadService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        manager=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel=new NotificationChannel("1","name",NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel);
        }
    }



    class DownLoadBinder extends Binder{

        public void startDownload(String url){
            if(downLoadTask==null){
                downloadUrl=url;
                downLoadTask=new DownLoadTask(listener);
                downLoadTask.execute(url);
                startForeground(1,getNotification("Downloading...",0));
                Toast.makeText(DownloadService.this.getApplicationContext(),"start Downloading..",Toast.LENGTH_SHORT).show();
            }
        }
        public void cancelDownLoad(){
            if(downLoadTask!=null) downLoadTask.cancelDownload();
            if(downloadUrl!=null) {
                String fileName=downloadUrl.substring(downloadUrl.lastIndexOf("/"));
                String dir= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
                File file=new File(dir+fileName);
                if(file.exists()) file.delete();
                manager.cancel(1);
                stopForeground(true);
                Toast.makeText(DownloadService.this.getApplicationContext(),"Canceled",Toast.LENGTH_SHORT).show();
            }



        }
        public void pauseDownload(){
            if(downLoadTask!=null) downLoadTask.pauseDownLoad();
        }

    }

    private Notification getNotification(String title,int progress){
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this,"1");
        builder.setContentTitle(title)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                .setDefaults(NotificationCompat.DEFAULT_ALL);
        if(progress>0){
            builder.setContentText(progress+"%");
            builder.setProgress(100,progress,false);
        }
        return builder.build();
    }


    @Override
    public IBinder onBind(Intent intent) {
        Log.d("data", "onBind");
        return downLoadBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("data", "DownloadService: onDestroy");
    }
}