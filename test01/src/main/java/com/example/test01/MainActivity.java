package com.example.test01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn=(Button)findViewById(R.id.test);
        btn.setOnClickListener(v->{
            Log.d("data", "startService");
            Intent intent=new Intent(this,MyService.class);
            startService(intent);
            Log.d("data", "FinishStartService");
        });
    }
}