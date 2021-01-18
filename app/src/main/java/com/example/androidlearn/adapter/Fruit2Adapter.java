package com.example.androidlearn.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidlearn.R;
import com.example.androidlearn.bean.Fruit;

import java.util.List;

public class Fruit2Adapter extends RecyclerView.Adapter<Fruit2Adapter.ViewHolder> {

    private List<Fruit> fruits;

    static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView fruitImage;
        TextView fruitName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fruitImage=(ImageView)itemView.findViewById(R.id.fruit_image);
            fruitName=(TextView)itemView.findViewById(R.id.fruit_name);
        }
    }

public Fruit2Adapter(List<Fruit> fruits){
this.fruits=fruits;
}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("data", "onCreateViewHolder");
//        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.fruit_item,parent,false);
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.fruit_item3,parent,false);
        ViewHolder viewHolder=new Fruit2Adapter.ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            int position=viewHolder.getAdapterPosition();
            Fruit fruit=fruits.get(position);
            Toast.makeText(v.getContext(),"You clicked view"+fruit.getName(),Toast.LENGTH_SHORT).show();
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    Fruit fruit=fruits.get(position);
    holder.fruitImage.setImageResource(fruit.getImageId());
    holder.fruitName.setText(fruit.getName());
        Log.d("data", "onBindViewHolder: "+position);
    }

    @Override
    public int getItemCount() {
        return fruits.size();
    }
}
