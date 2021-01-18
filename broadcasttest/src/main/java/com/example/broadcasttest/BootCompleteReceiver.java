package com.example.broadcasttest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class BootCompleteReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("data", "onReceive:"+context.getClass());
        Log.d("data", "onReceive: Boot Complete");
        Toast.makeText(context,"Boot Complete",Toast.LENGTH_LONG).show();
//        Intent bootIntent = new Intent(context, MainActivity.class);
        // 这里必须为FLAG_ACTIVITY_NEW_TASK
//        bootIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(bootIntent);
    }
}