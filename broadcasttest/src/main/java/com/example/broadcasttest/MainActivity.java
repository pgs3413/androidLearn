package com.example.broadcasttest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/*
本地广播：只能动态注册
LocalBroadcastManger manager=LocalBroadcastManager.getInstance(this)
发送广播：manager.sendBroadcast(intent)
注册广播接受器：manager.registerReceiver(receiver,intentFilter)
取消：manager.unregisterReceiver(receiver)
*/
public class MainActivity extends BaseActivity {

    private IntentFilter intentFilter;
    private NetworkChangeReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("data", "onCreate: 1");
        //动态注册一个广播接受器接受系统广播
//        intentFilter=new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
//        receiver=new NetworkChangeReceiver();
//        registerReceiver(receiver,intentFilter);
//        Log.d("data", "onCreate: 2");
        Button button=(Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("data", "onClick");
                Intent intent=new Intent("com.example.broadcasttest.MY_BROADCAST");
                intent.setPackage("com.example.broadcasttest");
                //发送自定义标准广播
//                sendBroadcast(intent);
                //发送自定义有序广播
                sendOrderedBroadcast(intent,null);
            }
        });
        Button forceOffline = (Button)findViewById(R.id.force_offline);
        forceOffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("data", "onClick: forceOffline");
                Intent intent=new Intent("com.example.broadcasttest.FORCE_OFFLINE");
                intent.setPackage("com.example.broadcasttest");
                sendBroadcast(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unregisterReceiver(receiver);
//        Log.d("data", "onDestroy");
    }

    class NetworkChangeReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
//            Log.d("data", "onReceive");
//            Toast.makeText(context,"network change",Toast.LENGTH_SHORT).show();
            //准确告诉用户当前是有网络还是没有网络
            ConnectivityManager manager =(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo =manager.getActiveNetworkInfo();
            if(networkInfo!=null && networkInfo.isAvailable()){
                Toast.makeText(context,"network is available",Toast.LENGTH_LONG).show();
//                Log.d("data", "onReceive:network is available");
            }else {
                Toast.makeText(context,"network is unavailable",Toast.LENGTH_LONG).show();
//                Log.d("data", "onReceive:network is unavailable");
            }
        }
    }
}