package com.example.androidlearn;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;

import com.example.androidlearn.adapter.Fruit2Adapter;
import com.example.androidlearn.bean.Fruit;

import java.util.ArrayList;
import java.util.List;

//增强版ListView RecyclerView
public class RecyclerViewActivity extends BaseActivity {

    private List<Fruit> fruits=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view_layout);
        initFruits();
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recycle_view);
        //线性布局 默认为纵列布局
//        LinearLayoutManager manager=new LinearLayoutManager(this);
        //改为横列布局
//        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        //GridLayoutManager 网格布局
        //StaggeredGridLayoutManager 瀑布流布局
        StaggeredGridLayoutManager manager=new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        Fruit2Adapter adapter=new Fruit2Adapter(fruits);
        recyclerView.setAdapter(adapter);
    }

    private void initFruits(){
        for(int i=0;i<5;i++){
            Fruit fruit1=new Fruit("apple",R.drawable.apple);
            fruits.add(fruit1);
            Fruit fruit2=new Fruit("banana",R.drawable.banana);
            fruits.add(fruit2);
            Fruit fruit3=new Fruit("cherry",R.drawable.cherry);
            fruits.add(fruit3);
        }
    }

}