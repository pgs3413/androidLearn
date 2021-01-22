package com.example.downloadtest;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DownLoadTask extends AsyncTask<String,Integer,Integer> {

    private DownloadListener listener;

    public static final int TYPE_SUCCESS=0;
    public static final int TYPE_FAILED=1;
    public static final int TYPE_PAUSED=2;
    public static final int TYPE_CANCELED=3;

    private boolean isCanceled=false;
    private boolean isPaused=false;

    private int lastProgress;


    public DownLoadTask(DownloadListener listener) {
        this.listener=listener;
    }

    @Override
    protected Integer doInBackground(String... strings) {
        InputStream is=null;
        RandomAccessFile savedFile=null;
        File file=null;
        try{
            long downloadedLength=0;//记录已下载的文件的数量
            String downloadUrl=strings[0];
            Log.d("data", "downloadUrl: "+downloadUrl);
            String fileName=downloadUrl.substring(downloadUrl.lastIndexOf("/"));
            String dir= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
            file=new File(dir+fileName);
            if(file.exists()) downloadedLength=file.length();
            Log.d("data", "downloadedLength: "+downloadedLength);
            long contentLength=getContentLength(downloadUrl);
            Log.d("data", "contentLength: "+contentLength);
            if(contentLength==0) return TYPE_FAILED;
            else if(contentLength==downloadedLength) return TYPE_SUCCESS;
            OkHttpClient client=new OkHttpClient();
            Request request=new Request.Builder()
                    //断点下载，指定从哪个字节开始下载
                    .addHeader("RANGE","bytes="+downloadedLength+"-")
                    .url(downloadUrl)
                    .build();
            Response response=client.newCall(request).execute();
            Log.d("data", "contentLength2: "+response.body().contentLength());
            if(response!=null){
                is=response.body().byteStream();
                savedFile=new RandomAccessFile(file,"rw");
                savedFile.seek(downloadedLength);//跳过🕐下载的字节
                byte[] buf=new byte[1024];
                int total=0;
                int len;
                while ((len=is.read(buf))!=-1){
                    if(isCanceled) return TYPE_CANCELED;
                    if(isPaused) return TYPE_PAUSED;
                    total+=len;
                    savedFile.write(buf,0,len);
                    int progress=(int)((total+downloadedLength)*100/contentLength);
                    publishProgress(progress);
                }
                response.body().close();
                return TYPE_SUCCESS;
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(is!=null) is.close();
                if(savedFile!=null) savedFile.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return TYPE_FAILED;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        int progress=values[0];
        if(progress>lastProgress){
            listener.onProgress(progress);
            lastProgress=progress;
        }
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        switch (integer){
            case TYPE_SUCCESS:
                listener.onSuccess();
                break;
            case TYPE_FAILED:
                listener.onFailed();
                break;
            case TYPE_PAUSED:
                listener.onPaused();
                break;
            case TYPE_CANCELED:
                listener.onCanceled();
                break;
            default:break;
        }
    }

    public void pauseDownLoad(){
        isPaused=true;
    }
    public void cancelDownload(){
        isCanceled=true;
    }

    private long getContentLength(String downloadUrl) throws IOException {
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder()
                .url(downloadUrl)
                .build();
        Response response=client.newCall(request).execute();
        if(response!=null && response.isSuccessful()){
            long contentLength=response.body().contentLength();
            response.close();
            return contentLength;
        }
        return 0;
    }
}
