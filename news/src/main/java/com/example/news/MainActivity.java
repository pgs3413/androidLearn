package com.example.news;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public static boolean isTwoPag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View view=findViewById(R.id.flag_view);
        if(view==null) isTwoPag=true;
        else isTwoPag=false;
        Log.d("data", "isTwoPage: "+isTwoPag);
    }
}