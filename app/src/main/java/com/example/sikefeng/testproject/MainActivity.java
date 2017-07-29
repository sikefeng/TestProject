package com.example.sikefeng.testproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.example.sikefeng.testproject.a.coordinatorLayout.CoordinatorLayoutActivity;
import com.example.sikefeng.testproject.a.coordinatorLayout.CoordinatorLayoutActivity2;
import com.example.sikefeng.testproject.b.topbar.TopBarBaseActivity;
import com.example.sikefeng.testproject.e.swiperefresh.recyclerview.RefreshActivity;
import com.example.sikefeng.testproject.f.pickerphoto.PickerPhotoActivity;
import com.example.sikefeng.testproject.imageshowpicker.PickerImageActivity;
import com.example.sikefeng.testproject.mooncoordinatorlayout.CollapsingToolbarActivity;
import com.example.sikefeng.testproject.mooncoordinatorlayout.CoordinatorLayoutActivityy;
import com.example.sikefeng.testproject.notificationdemo.NotificationDemoActivity;
import com.example.sikefeng.testproject.notificationlaunch.SplashActivity;

public class MainActivity extends TopBarBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent=this.getIntent();
        String content=intent.getStringExtra("content");
        if (content!=null){
            System.out.println("------------------------content="+content);
            startActivity(new Intent(this,NotificationDemoActivity.class));
        }
    }
    /**
     * 再按一次退出
     */
    private long exitTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                //按返回键返回桌面
//                moveTaskToBack(true);
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    public void onClick1(View view) {
        startActivity(new Intent(MainActivity.this, CoordinatorLayoutActivity.class));
    }

    public void onClick2(View view) {
        startActivity(new Intent(MainActivity.this, CoordinatorLayoutActivity2.class));
    }

    public void onClick3(View view) {
        startActivity(new Intent(MainActivity.this, CollapsingToolbarActivity.class));
    }

    public void onClick4(View view) {
        startActivity(new Intent(MainActivity.this, CoordinatorLayoutActivityy.class));
    }
    public void onClick5(View view) {
        startActivity(new Intent(MainActivity.this, PickerImageActivity.class));
    }
    public void onClick6(View view) {
        startActivity(new Intent(MainActivity.this, com.example.sikefeng.testproject.c.recyclerview.swiperefresh.recycleviewdemo.MainActivity.class));
    }
    public void onClick7(View view) {
        startActivity(new Intent(MainActivity.this, RefreshActivity.class));
    }
    public void onClick8(View view) {
        startActivity(new Intent(MainActivity.this, NotificationDemoActivity.class));
    }
    public void onClick9(View view) {
        startActivity(new Intent(MainActivity.this, SplashActivity.class));
    }
    public void onClick10(View view) {
        startActivity(new Intent(MainActivity.this, PickerPhotoActivity.class));
    }
}
