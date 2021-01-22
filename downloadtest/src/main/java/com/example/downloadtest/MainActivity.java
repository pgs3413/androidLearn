package com.example.downloadtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText input;
    DownloadService.DownLoadBinder downLoadBinder;

    private ServiceConnection connection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("data", "onServiceConnected");
            downLoadBinder=(DownloadService.DownLoadBinder)service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        input=(EditText)findViewById(R.id.file_name);
        Button start=(Button)findViewById(R.id.start_download);
        Button cancel=(Button)findViewById(R.id.cancel_download);
        Button pause=(Button)findViewById(R.id.pause_download);
        Intent intent=new Intent(this,DownloadService.class);
        boolean b=bindService(intent,connection,BIND_AUTO_CREATE);
        Log.d("data", "bindService: "+b);
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }
        start.setOnClickListener(this);
        cancel.setOnClickListener(this);
        pause.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start_download:
                String fileName=input.getText().toString();
                String url="http://192.168.1.81/"+fileName;
                Intent intent=new Intent(this,DownloadService.class);
                if(Build.VERSION.SDK_INT>=26){
                    startForegroundService (intent);
                }else{
                    startService (intent);
                }
                downLoadBinder.startDownload(url);
                break;
            case R.id.pause_download:
                downLoadBinder.pauseDownload();
                break;
            case R.id.cancel_download:
                downLoadBinder.cancelDownLoad();
                unbindService(connection);
                Intent intent2=new Intent(this,DownloadService.class);
                stopService(intent2);
                break;
            default:break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length>0 && grantResults[0]!=PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this,"拒绝权限将无法使用程序！",Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
            default:break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }
}