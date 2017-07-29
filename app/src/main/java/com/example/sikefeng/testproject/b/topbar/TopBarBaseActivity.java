package com.example.sikefeng.testproject.b.topbar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.example.sikefeng.testproject.R;

/**
 * Created by Administrator on 2017/5/1.
 */

public abstract class TopBarBaseActivity extends AppCompatActivity {
    Toolbar toolbar;
    FrameLayout viewContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_top_bar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        viewContent = (FrameLayout) findViewById(R.id.viewContent);
        //初始化设置 Toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //将继承 TopBarBaseActivity 的布局解析到 FrameLayout 里面

    }

}