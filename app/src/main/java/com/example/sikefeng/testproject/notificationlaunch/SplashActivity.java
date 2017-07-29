package com.example.sikefeng.testproject.notificationlaunch;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.sikefeng.testproject.R;

/**
 * Created by liangzili on 15/8/3.
 */
public class SplashActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                if(getIntent().getBundleExtra(Constants.EXTRA_BUNDLE) != null){
                    intent.putExtra(Constants.EXTRA_BUNDLE, getIntent().getBundleExtra(Constants.EXTRA_BUNDLE));
                }
                startActivity(intent);
                finish();
            }
        }, 3000);
    }
}
