package com.example.broadcasttest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyBroadcast2Receiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("data", "onReceive2: "+context.getClass());
        Toast.makeText(context,"received in another MyBroadcastReceiver",Toast.LENGTH_SHORT).show();
    }
}