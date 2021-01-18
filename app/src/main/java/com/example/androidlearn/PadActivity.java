package com.example.androidlearn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.androidlearn.fragment.AnotherRightFragment;
import com.example.androidlearn.fragment.RightFragment;

/*
* 活动中获得碎片的实例：
* RightFragment rightFragment = (RightFragment)getFragmentManager().findFragmentBiId(R.id.right_fragment)
* 碎片中获得活动的实例：
* PadActivity a=(PadActivity)getActivity()
*/
public class PadActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pad);
//        Button btn=(Button)findViewById(R.id.fragment_btn);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                replaceFragment(new AnotherRightFragment());
//            }
//        });
//        replaceFragment(new RightFragment());
    }
    //动态添加碎片到活动中
//    private void replaceFragment(Fragment fragment){
//        FragmentManager manager=getSupportFragmentManager();
//        FragmentTransaction transaction=manager.beginTransaction();
//        transaction.replace(R.id.right_layout,fragment);
//        //按下BACK返回到上一个碎片
//        transaction.addToBackStack(null);
//        transaction.commit();
//    }
}