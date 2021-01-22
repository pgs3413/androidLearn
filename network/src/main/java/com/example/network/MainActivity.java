package com.example.network;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button send_request=(Button)findViewById(R.id.send_request);
        textView=(TextView)findViewById(R.id.response_text);
        textView.setText("this is a response view");
        send_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                sendRequestWithOKHttp();
                HttpUtil.get("http://www.baidu.com", new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        String content=response.body().string();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textView.setText(content);
                            }
                        });
                    }
                });
            }
        });
    }
/*
* post提交数据
* RequestBody requestBody=new FormBody.Builder().add("xx","xx").add("xx","xx").build();
* 加入到request中 .post(requestBody)
* */
    private void sendRequestWithOKHttp(){
        //开启一个线程进行网络请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                //构造一个客户端
                OkHttpClient client=new OkHttpClient();
                //构造一个请求对象
                Request request=new Request.Builder()
                        .url("http://192.168.1.81/get_data.json")
                        .build();
                try {
                    //用客户端发起请求,得到响应对象
                    Response response=client.newCall(request).execute();
                    //从请求对象中获得内容字符串
                    String content=response.body().string();
                    System.out.println(content);
                    parseJSONWithGSON(content);
//                    parseJSONWithJsonObject(content);
//                    showResponse(content);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private void parseJSONWithJsonObject(String content){
        try {
            JSONArray jsonArray=new JSONArray(content);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject object=jsonArray.getJSONObject(i);
                String id=object.getString("id");
                String name=object.getString("name");
                String version=object.getString("version");
                Log.d("data", "id: "+id);
                Log.d("data", "name: "+name);
                Log.d("data", "version: "+version);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void parseJSONWithGSON(String content){
        Gson gson=new Gson();
        List<App> apps=gson.fromJson(content,new TypeToken<List<App>>(){}.getType());
        for(App app:apps){
            Log.d("data", "id: "+app.getId());
            Log.d("data", "name: "+app.getName());
            Log.d("data", "version: "+app.getVersion());
        }
    }

    private void showResponse(final String response){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.d("data", "showResponse:run");
                textView.setText(response);
            }
        });
    }
}