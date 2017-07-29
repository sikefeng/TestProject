package com.example.sikefeng.testproject.zhy.clippic;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.sikefeng.testproject.R;
import com.example.sikefeng.testproject.zhy.view.ClipImageLayout;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * http://blog.csdn.net/lmj623565791/article/details/39761281
 *
 * @author zhy
 */
public class MainActivity extends Activity {
    private ClipImageLayout mClipImageLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhy);

        mClipImageLayout = (ClipImageLayout) findViewById(R.id.id_clipImageLayout);
        mClipImageLayout.setImageView(new File("/sdcard/photo.jpeg"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.id_action_clip:
                Bitmap bitmap = mClipImageLayout.clip();

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] datas = baos.toByteArray();

                Intent intent = new Intent(this, ShowImageActivity.class);
                intent.putExtra("bitmap", datas);
                startActivity(intent);

                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
