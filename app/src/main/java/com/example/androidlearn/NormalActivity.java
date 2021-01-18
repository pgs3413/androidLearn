package com.example.androidlearn;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class NormalActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.normal_layout);
        Button btn=(Button)findViewById(R.id.button);
        ImageView imageView=(ImageView)findViewById(R.id.image_view);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                imageView.setImageResource(R.drawable.img_2);
            //AlertDialog 在当前的界面弹出一个对话框
//                AlertDialog.Builder dialog=new AlertDialog.Builder(NormalActivity.this);
//                dialog.setTitle("this is a Dialog");
//                dialog.setMessage("Something important");
//                dialog.setCancelable(false);
//                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                });
//                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                });
//                dialog.show();
        //ProgressDialog 对话框中显示一个进度条
                ProgressDialog progressDialog=new ProgressDialog(NormalActivity.this);
                progressDialog.setTitle("this is ProgressDialog");
                progressDialog.setMessage("Loading...");
                progressDialog.setCancelable(true);
//                progressDialog.dismiss(); 关闭对话框
                progressDialog.show();
            }
        });
    }
}