package com.example.filepersistence;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

//文件存储 会存储到/data/data/包名/files/下
public class MainActivity extends AppCompatActivity {

    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("data", "onCreate");
        setContentView(R.layout.activity_main);
        editText=(EditText)findViewById(R.id.edit);
        String content=load();
        if(!content.isEmpty()){
            editText.setText(content);
            editText.setSelection(content.length());//将光标移到最后
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("data", "onDestroy");
        String inputText=editText.getText().toString();
        save(inputText);
    }
    private void save(String inputText ){
        try{
            FileOutputStream outputStream=openFileOutput("editText",MODE_PRIVATE);
            BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(outputStream));
            writer.write(inputText);
            if(writer!=null) writer.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private String load(){
        StringBuilder content=new StringBuilder();
        try{
            FileInputStream inputStream=openFileInput("editText");
            BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream));
            String line="";
            while ((line=reader.readLine())!=null){
                content.append(line);
            }
            if(reader!=null) reader.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return content.toString();
    }
}