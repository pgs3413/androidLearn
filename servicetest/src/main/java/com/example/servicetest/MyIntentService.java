package com.example.servicetest;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;

public class MyIntentService extends IntentService {

    public MyIntentService(){
        super("MyIntentService");
    }

    //此方法会异步执行，且执行完后会结束服务
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d("data", "MyIntentService: onHandleIntent: Thread id is"+Thread.currentThread().getId());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("data", "MyIntentService:onDestroy: executed");
    }
}
