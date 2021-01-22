package com.example.lbstest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public LocationClient mLocationClient;
    private TextView positionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        positionText=(TextView)findViewById(R.id.position_text_view);
        //设置LocationClient
        mLocationClient=new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());//获得定位后回调listener
        //获得权限
        List<String> permissionList=new ArrayList<>();
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED)
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED)
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(!permissionList.isEmpty()){
            String[] permissions=permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(this,permissions,1);
        }else {
            requestLocation();
        }

    }

    private void requestLocation(){
        initLocation();
        mLocationClient.start();
    }

    private void initLocation(){
        LocationClientOption option=new LocationClientOption();
//        option.setLocationMode(LocationClientOption.LocationMode.Device_Sensors);
        option.setScanSpan(2000);
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==1){
            if(grantResults.length>0){
                for(int result:grantResults){
                    if(result!=PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(this,"必须同意所有权限才能使用本程序",Toast.LENGTH_SHORT).show();
                        finish();
                        return;
                    }
                }
                requestLocation();
            }
        }
    }

    public class MyLocationListener implements BDLocationListener{

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
        StringBuilder currentPosition=new StringBuilder();
        currentPosition.append("纬度：").append(bdLocation.getLatitude()).append("\n");
        currentPosition.append("经度：").append(bdLocation.getLongitude()).append("\n");
        currentPosition.append("国家：").append(bdLocation.getCountry()).append("\n");
        currentPosition.append("省：").append(bdLocation.getProvince()).append("\n");
        currentPosition.append("市：").append(bdLocation.getCity()).append("\n");
        currentPosition.append("区：").append(bdLocation.getDistrict()).append("\n");
        currentPosition.append("街道：").append(bdLocation.getStreet()).append("\n");
        currentPosition.append("定位方式：");
        if(bdLocation.getLocType()==BDLocation.TypeGpsLocation){
            currentPosition.append("GPS");
        }else if(bdLocation.getLocType()==BDLocation.TypeNetWorkLocation){
            currentPosition.append("网络");
        }else {
            currentPosition.append(bdLocation.getLocType());
        }
        positionText.setText(currentPosition);
        }
    }
}