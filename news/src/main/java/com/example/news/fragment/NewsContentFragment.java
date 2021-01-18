package com.example.news.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.news.MainActivity;
import com.example.news.NewsContentActivity;
import com.example.news.R;

public class NewsContentFragment extends Fragment {

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.news_content_frag,container,false);
        Activity activity=getActivity();
        if(activity instanceof MainActivity){

        }else{
            activity=(NewsContentActivity) getActivity();
            Intent intent =activity.getIntent();
            String title=intent.getStringExtra("title");
            String content=intent.getStringExtra("content");
            refresh(title,content);
        }
        return view;
    }
    //将新闻的标题和内容显示在界面
    public void refresh(String title,String content){
        View visibilityLayout=view.findViewById(R.id.visibility_layout);
        visibilityLayout.setVisibility(View.VISIBLE);
        TextView titleText=(TextView)view.findViewById(R.id.news_title);
        TextView contentText=(TextView)view.findViewById(R.id.news_content);
        titleText.setText(title);
        contentText.setText(content);
    }
}
