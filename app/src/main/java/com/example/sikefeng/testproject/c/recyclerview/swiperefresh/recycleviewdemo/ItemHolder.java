package com.example.sikefeng.testproject.c.recyclerview.swiperefresh.recycleviewdemo;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.sikefeng.testproject.R;

/**
 * Created by Administrator on 2016/1/26.
 */
public class ItemHolder extends RecyclerView.ViewHolder {
    public TextView mTextView;

    public ItemHolder(View itemView) {
        super(itemView);
        mTextView = (TextView)itemView.findViewById(R.id.item);
    }
}
