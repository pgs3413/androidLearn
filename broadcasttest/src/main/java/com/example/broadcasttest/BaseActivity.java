package com.example.broadcasttest;

import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    ForceOfflineReceiver receiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
/*
*在onResume注册广播接受器 在onPause取消 可以保证只有栈顶的活动能够接受到广播
*/
    @Override
    protected void onResume() {
        super.onResume();
        Log.d("data", "onResume: "+getClass());
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction("com.example.broadcasttest.FORCE_OFFLINE");
        receiver=new ForceOfflineReceiver();
        registerReceiver(receiver,intentFilter);
    }

    @Override
    protected void onPause() {
        Log.d("data", "onPause: "+getClass());
        super.onPause();
        unregisterReceiver(receiver);
    }
}
