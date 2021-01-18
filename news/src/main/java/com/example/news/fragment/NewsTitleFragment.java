package com.example.news.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.news.R;
import com.example.news.adapter.NewsAdapter;
import com.example.news.bean.News;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NewsTitleFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.news_title_frag,container,false);
        RecyclerView recyclerView=(RecyclerView)view.findViewById(R.id.news_title_recycle_view);
        LinearLayoutManager manager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        NewsAdapter adapter=new NewsAdapter(getNews());
        recyclerView.setAdapter(adapter);
        return view;
    }
    private List<News> getNews(){
        List<News> newsList=new ArrayList<>();
        for(int i=0;i<50;i++){
            News news=new News();
            news.setTitle("This is news title"+i);
            news.setContent(getRandomLengthContent("this is news content"+i+"."));
            newsList.add(news);
        }
        return newsList;
    }
    private String getRandomLengthContent(String content){
        Random random=new Random();
        int length=random.nextInt(20)+1;
        StringBuilder builder=new StringBuilder();
        for(int i=0;i<length;i++){
            builder.append(content+" ");
        }
        return builder.toString();
    }
}
