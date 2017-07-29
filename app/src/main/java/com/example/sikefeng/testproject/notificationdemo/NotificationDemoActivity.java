package com.example.sikefeng.testproject.notificationdemo;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.sikefeng.testproject.R;

public class NotificationDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_demo);
        IntentFilter intentFilter = new IntentFilter("notification");
        registerReceiver(new NotificationBroadcast(), intentFilter);
        Button button=(Button)findViewById(R.id.btn_send);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent=new Intent("notification");
                        intent.putExtra("msg","我是推送内容");
                        sendBroadcast(intent);
                    }
                }, 3000);

            }
        });
    }



}
