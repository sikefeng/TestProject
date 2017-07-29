package com.example.sikefeng.testproject.f.pickerphoto;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.sikefeng.testproject.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class PickerPhotoActivity extends AppCompatActivity {
    ImageUtils imageUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picker_photo);
        imageUtils=new ImageUtils(PickerPhotoActivity.this,true);
        Button btn_show=(Button)findViewById(R.id.btn_show);
        btn_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageUtils.selectPhoto(v);
            }
        });
        imageUtils.setOnPictureSelectedListener(new ImageUtils.OnPictureSelectedListener() {
            @Override
            public void onPictureSelected(String result_path) {
                System.out.println("-----------------------------result_path="+result_path);
                Bitmap photo = BitmapFactory.decodeFile(result_path);
                CircleImageView imageView1 = (CircleImageView) findViewById(R.id.imageView1);
                CircleImageView imageView2 = (CircleImageView) findViewById(R.id.imageView2);
                imageView1.setImageBitmap(photo);
                imageView2.setImageBitmap(photo);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        imageUtils.setOnActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        imageUtils.setOnRequestPermissionsResult(requestCode,permissions,grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
