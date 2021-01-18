package com.example.androidlearn;


import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.androidlearn.adapter.FruitAdapter;
import com.example.androidlearn.bean.Fruit;

import java.util.ArrayList;
import java.util.List;

//ListView 自定义布局和内容
public class Normal3Activity extends BaseActivity {

    private String[] data={"apple1","apple2","apple3","apple4","apple5","apple6","apple7",
            "apple8","apple9","apple10","apple11","apple12","apple13","apple14","apple15","apple16"};
    private List<Fruit> fruits=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.normal3_layout);
//        ArrayAdapter<String> adapter=new ArrayAdapter<>(Normal3Activity.this, android.R.layout.simple_list_item_1,data);
      initFruits();
        FruitAdapter adapter=new FruitAdapter(Normal3Activity.this,R.layout.fruit_item,fruits);
      ListView listView=(ListView)findViewById(R.id.list_view);
      listView.setAdapter(adapter);
      listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              Fruit fruit=fruits.get(position);
              Toast.makeText(Normal3Activity.this,fruit.getName(),Toast.LENGTH_SHORT).show();
          }
      });
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