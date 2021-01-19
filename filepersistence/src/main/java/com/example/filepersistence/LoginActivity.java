package com.example.filepersistence;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button login=(Button)findViewById(R.id.login);
        EditText accountText=(EditText)findViewById(R.id.account);
        EditText passwordText=(EditText)findViewById(R.id.password);
        CheckBox cb=(CheckBox)findViewById(R.id.remember_pass);
        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(this);
        Boolean isRemember= pref.getBoolean("isRemember",false);
        if(isRemember){
            String account=pref.getString("account","");
            String password=pref.getString("password","");
            accountText.setText(account);
            passwordText.setText(password);
            cb.setChecked(true);
        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account=accountText.getText().toString();
                String password=passwordText.getText().toString();
                if(account.equals("admin")&&password.equals("12345")){
                    SharedPreferences.Editor editor =pref.edit();
                    if(cb.isChecked()){
                        editor.putBoolean("isRemember",true);
                        editor.putString("account",account);
                        editor.putString("password",password);
                    }else {
                        editor.clear();
                    }
                    editor.apply();
                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this,"login failed",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}