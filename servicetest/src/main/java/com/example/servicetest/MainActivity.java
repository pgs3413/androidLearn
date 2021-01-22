package com.example.servicetest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ServiceConnection connection=new ServiceConnection() {
        //活动与服务成功绑定后调用，service为从onBind中获得的Binder类
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyService.DownloadBinder binder=(MyService.DownloadBinder)service;
            binder.startDownload();
            binder.getProgress();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("data", "onServiceDisconnected");
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button start=(Button)findViewById(R.id.start_service);
        Button stop=(Button)findViewById(R.id.stop_service);
        Button bind=(Button)findViewById(R.id.bind_service);
        Button unbind=(Button)findViewById(R.id.unbind_service);
        Button intent_btn=(Button)findViewById(R.id.intent_service);
        start.setOnClickListener(this);
        stop.setOnClickListener(this);
        bind.setOnClickListener(this);
        unbind.setOnClickListener(this);
        intent_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start_service:
                Intent startIntent=new Intent(this,MyService.class);
//                startService(startIntent);//回调服务的onStartCommand方法，服务若未创建，则调用onCreate
                //开启一个前台服务
                if(Build.VERSION.SDK_INT>=26){
                    startForegroundService (startIntent);
                }else{
                    startService (startIntent);
                }
                break;
            case R.id.stop_service:
                Intent stopIntent=new Intent(this,MyService.class);
                stopService(stopIntent);//回调服务的onDestroy方法
                break;
            case R.id.bind_service:
                Intent bindIntent=new Intent(this,MyService.class);
                //BIND_AUTO_CREATE表示活动和服务进行绑定后自动创建服务。onCreate得到执行 onStartCommand()不会执行
                bindService(bindIntent,connection,BIND_AUTO_CREATE);//回调服务的onBind方法,服务若未创建，则调用onCreate
                break;
            case R.id.unbind_service:
                unbindService(connection);//回调服务的onDestroy方法
                //如果同时调用了startService和bindService，要同时调用了stopService和unbindService才能销毁服务
                break;
            case R.id.intent_service:
                Log.d("data", "MainActivity: Thread id is"+Thread.currentThread().getId());
                Intent intentService=new Intent(this,MyIntentService.class);
                startService(intentService);
                break;
            default:break;
        }
    }
}