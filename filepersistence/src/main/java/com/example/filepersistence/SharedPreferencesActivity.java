package com.example.filepersistence;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/*
* SharedPreferences存储 键值对 存储在/data/data/包名/shared_prefs/下
* 1.Context类中的getSharePreferences(fileName,MODE_PRIVATE)
* 2.Activity中的getPreferences(MODE_PRIVATE) 文件名为类名
* 3.PreferenceManager中的getDefaultSharePreferences(context)方法
* */

public class SharedPreferencesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_preferences);
        Button save=(Button)findViewById(R.id.save_btn);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor=getSharedPreferences("data",MODE_PRIVATE).edit();
                editor.putString("name","Tom");
                editor.putInt("age",18);
                editor.putBoolean("married",false);
                editor.apply();
            }
        });
        Button load=(Button)findViewById(R.id.load_btn);
        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pre=getSharedPreferences("data",MODE_PRIVATE);
                String name=pre.getString("name","");
                int age=pre.getInt("age",0);
                boolean married=pre.getBoolean("married",false);
                Log.d("data", "name:"+name);
                Log.d("data", "age:"+age);
                Log.d("data", "married:"+married);
            }
        });
    }
}