package com.example.databasetest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button create_btn=(Button)findViewById(R.id.create);
        Button add_btn=(Button)findViewById(R.id.add);
        Button update_btn=(Button)findViewById(R.id.update);
        Button delete_btn=(Button)findViewById(R.id.delete);
        Button query_btn=(Button)findViewById(R.id.query);
        MyDatabaseHelper helper=new MyDatabaseHelper(this,"BookStore.db",null,2);
        create_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //数据库不存在会创建数据库
                helper.getWritableDatabase();
            }
        });
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //添加数据
                SQLiteDatabase db =helper.getWritableDatabase();
                ContentValues values=new ContentValues();
                values.put("name","射雕英雄传");
                values.put("author","金庸");
                values.put("pages",454);
                values.put("price",16.96);
                db.insert("Book",null,values);
                Toast.makeText(MainActivity.this,"succeed",Toast.LENGTH_SHORT).show();
            }
        });
        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //更新数据
                SQLiteDatabase db=helper.getWritableDatabase();
                ContentValues values=new ContentValues();
                values.put("price",10.99);
                db.update("Book",values,"id=?",new String[]{"1"});
                Toast.makeText(MainActivity.this,"succeed",Toast.LENGTH_SHORT).show();
            }
        });
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //删除数据
                SQLiteDatabase db=helper.getWritableDatabase();
                db.delete("Book","id=?",new String[]{"2"});
                Toast.makeText(MainActivity.this,"succeed",Toast.LENGTH_SHORT).show();
            }
        });
        query_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //查询数据
                //参数：table columns selection selectionArgs groupBy having orderBy
                SQLiteDatabase db=helper.getWritableDatabase();
                Cursor cursor =db.query("Book",null,null,null,null,null,null);
                if(cursor.moveToFirst()){
                do{
                    String name=cursor.getString(cursor.getColumnIndex("name"));
                    String author=cursor.getString(cursor.getColumnIndex("author"));
                    int pages=cursor.getInt(cursor.getColumnIndex("pages"));
                    double price=cursor.getDouble(cursor.getColumnIndex("price"));
                    Log.d("data", "name: "+name);
                    Log.d("data", "author: "+author);
                    Log.d("data", "pages: "+pages);
                    Log.d("data", "price: "+price);
                }while(cursor.moveToNext());
                cursor.close();
                Toast.makeText(MainActivity.this,"succeed",Toast.LENGTH_SHORT).show();
                }
            }
        });
        /*
        * 执行sql语句
        * 添加，更新，删除 db.execSQL(string sql,Object[] obj)
        * 查询 db.rawQuery(String sql,null) 返回一个Cursor
        * */
    }
}