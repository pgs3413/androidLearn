package com.example.picturecapture;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    ImageView pictureView;
    Uri photoURI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button take_photo = (Button) findViewById(R.id.take_photo);
        Button chooseFromAlbum = (Button) findViewById(R.id.choose_from_album);
        Button chooseVideo=(Button)findViewById(R.id.choose_video);
        pictureView = (ImageView) findViewById(R.id.picture);
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
        take_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (photoFile != null) {
                        photoURI = FileProvider.getUriForFile(MainActivity.this, "com.example.picturecapture.fileprovider", photoFile);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(intent, 1);
                    }
                }
            }
        });
        chooseFromAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAlbum();
            }
        });
        chooseVideo.setOnClickListener(v->{
            openVideo();
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    Toast.makeText(MainActivity.this, "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "test_" + timeStamp + "_.jpg";

        File storageDir = getExternalCacheDir();
        File image = new File(storageDir, imageFileName);

//        String cameraPath=Environment.getExternalStorageDirectory()+File.separator+Environment.DIRECTORY_DCIM+File.separator
//                +"Camera"+File.separator;
//        File image = new File(cameraPath,imageFileName);//保存到系统相册

//        String path=Environment.getExternalStorageDirectory()+File.separator+Environment.DIRECTORY_PICTURES+File.separator
//                +"test"+File.separator;
//        File image=new File(path,imageFileName);

        return image;
    }

    //Bitmap，即位图。它本质上就是一张图片的内容在内存中的表达形式
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //获取缩略图
//        if(requestCode==1 && resultCode==RESULT_OK){
//            Bundle extras=data.getExtras();
//            Bitmap image=(Bitmap)extras.get("data");
//            pictureView.setImageBitmap(image);
//        }
        //获取完整图
        if (requestCode == 1 && resultCode == RESULT_OK) {
            try {
                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,photoURI));
                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(photoURI));
                pictureView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        if(requestCode==2 && resultCode==RESULT_OK){
            if(data!=null){
                Uri uri=data.getData();
                Bitmap bitmap=getBitmapFromUri(uri);
                pictureView.setImageBitmap(bitmap);
            }
        }
    }

    private void openAlbum() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, 2);
    }

    private void openVideo(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("video/*");
        startActivityForResult(intent, 3);
    }

    private Bitmap getBitmapFromUri(Uri uri){
        Bitmap bitmap=null;
        try{
            ParcelFileDescriptor descriptor = getContentResolver().openFileDescriptor(uri,"r");
            bitmap = BitmapFactory.decodeFileDescriptor(descriptor.getFileDescriptor());
        }catch (Exception e){
            e.printStackTrace();
        }
        return bitmap;
    }

}