package com.example.downloadtest;

public interface DownloadListener {

    public void onSuccess();

    public void onProgress(int progress);

    public void onFailed();

    public void onCanceled();

    public void onPaused();

}
