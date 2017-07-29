package com.example.sikefeng.testproject.a.coordinatorLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sikefeng.testproject.R;

public class CoordinatorLayoutActivity2 extends AppCompatActivity {
    private Toolbar toolbar;
    private ImageView image;
    private ViewPager viewpager;
    private TabLayout tabLayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_layout2);
        initView();
        initData();
    }
    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        image = (ImageView) findViewById(R.id.image);
        viewpager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.ctb);
    }
    private void initData() {
        toolbar.setLogo(R.mipmap.ic_launcher);
        setSupportActionBar(toolbar);
        //设置返回按钮
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //设置收缩展开toolbar字体颜色
        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.BLACK);

        //设置tablayout与viewPager
        viewpager.setAdapter(new TestViewPageAdapter());
        tabLayout.setupWithViewPager(viewpager);
    }
    class TestViewPageAdapter extends PagerAdapter {
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TextView textView = new TextView(CoordinatorLayoutActivity2.this);
            textView.setGravity(Gravity.CENTER);
            textView.setText("pager "+(position+1));
            textView.setTextSize(30);
            textView.setTextColor(Color.BLUE);
            container.addView(textView);
            return textView;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }
        @Override
        public int getCount() {
            return 5;
        }
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
        /*获得标题*/
        /*该方法必须写,不然tablayout不能显示标题*/
        @Override
        public CharSequence getPageTitle(int position) {
            return "TAB"+(position+1);
        }
    }
}