package com.example.sikefeng.testproject.notificationlaunch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.sikefeng.testproject.R;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainn);
        Intent intent = new Intent(this, PushService.class);
        startService(intent);
        setTitle("MainActivity");
        Bundle bundle = getIntent().getBundleExtra(Constants.EXTRA_BUNDLE);
        if(bundle != null){
            String name = bundle.getString("name");
            String price = bundle.getString("price");
            String detail = bundle.getString("detail");
            SystemUtils.startDetailActivity(this, name, price, detail);
            Log.i(TAG, "launchParam exists, redirect to DetailActivity");
        }
    }


}
