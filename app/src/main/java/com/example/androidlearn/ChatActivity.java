package com.example.androidlearn;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.androidlearn.adapter.MsgAdapter;
import com.example.androidlearn.bean.Msg;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends BaseActivity {

    private List<Msg> msgList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_layout);
        initMsg();
        EditText input=(EditText)findViewById(R.id.input_text);
        Button send=(Button)findViewById(R.id.send_btn);
        RecyclerView msgView=(RecyclerView)findViewById(R.id.msg_view);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        msgView.setLayoutManager(manager);
        MsgAdapter adapter=new MsgAdapter(msgList);
        msgView.setAdapter(adapter);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content=input.getText().toString();
                if(!"".equals(content)){
                    Msg msg=new Msg(content,Msg.TYPE_SEND);
                    msgList.add(msg);
                    //通知adapter有新内容 刷新RecyclerView
                    adapter.notifyItemInserted(msgList.size()-1);
                    //使RecyclerView定位到最后一行
                    msgView.scrollToPosition(msgList.size()-1);
                    //清空输入框
                    input.setText("");
                }
            }
        });
    }

    private void initMsg(){
        Msg msg1=new Msg("你好！",Msg.TYPE_RECEIVED);
        msgList.add(msg1);
        Msg msg2=new Msg("你好呀！",Msg.TYPE_SEND);
        msgList.add(msg2);
        Msg msg3=new Msg("吃饭了吗！",Msg.TYPE_RECEIVED);
        msgList.add(msg3);
        Msg msg4=new Msg("再见！",Msg.TYPE_RECEIVED);
        msgList.add(msg4);
    }
}