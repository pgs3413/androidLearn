package com.example.news.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.news.MainActivity;
import com.example.news.NewsContentActivity;
import com.example.news.R;
import com.example.news.bean.News;
import com.example.news.fragment.NewsContentFragment;

import java.util.ArrayList;
import java.util.List;


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    List<News> newsList=new ArrayList<>();

    public NewsAdapter(List<News> newsList) {
        this.newsList=newsList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView titleView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleView=(TextView) itemView.findViewById(R.id.news_title);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item,parent,false);
    ViewHolder holder=new ViewHolder(view);
    view.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position=holder.getAdapterPosition();
            News news=newsList.get(position);
            MainActivity activity=(MainActivity)v.getContext();
            if(MainActivity.isTwoPag){
                NewsContentFragment fragment=(NewsContentFragment)activity.getSupportFragmentManager().findFragmentById(R.id.news_content_fragment);
                fragment.refresh(news.getTitle(),news.getContent());
            }else{
                Intent intent=new Intent(v.getContext(), NewsContentActivity.class);
                intent.putExtra("title",news.getTitle());
                intent.putExtra("content",news.getContent());
                v.getContext().startActivity(intent);
            }

        }
    });
    return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    News news=newsList.get(position);
    holder.titleView.setText(news.getTitle());
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }


}
