package com.example.network;

import java.util.Map;
import java.util.Set;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpUtil {

    public static void get(final String address,final okhttp3.Callback callback){
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder()
                .url(address)
                .build();
        client.newCall(request).enqueue(callback);
    }

    public static void post(final String address,final Map<String,String> args, final okhttp3.Callback callback ){
        OkHttpClient client=new OkHttpClient();
        FormBody.Builder builder=new FormBody.Builder();
        Set<String> keys=args.keySet();
        for(String key:keys){
            builder.add(key,args.get(key));
        }
        RequestBody requestBody=builder.build();
        Request request=new Request.Builder()
                .url(address)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(callback);
    }
}
