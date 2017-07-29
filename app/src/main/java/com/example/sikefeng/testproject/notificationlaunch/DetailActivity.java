package com.example.sikefeng.testproject.notificationlaunch;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.sikefeng.testproject.R;

/**
 * Created by liangzili on 15/8/3.
 */
public class DetailActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        getSupportActionBar().setTitle("DetailActivity");
        String name = getIntent().getStringExtra("name");
        String price = getIntent().getStringExtra("price");
        String detail = getIntent().getStringExtra("detail");

        ((TextView)findViewById(R.id.name)).setText(name);
        ((TextView)findViewById(R.id.price)).setText(price);
        ((TextView)findViewById(R.id.detail)).setText(detail);
    }
}
