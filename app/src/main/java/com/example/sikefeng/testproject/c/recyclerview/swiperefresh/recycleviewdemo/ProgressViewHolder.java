package com.example.sikefeng.testproject.c.recyclerview.swiperefresh.recycleviewdemo;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.sikefeng.testproject.R;

/**
 * Created by Administrator on 2015/10/9.
 */
public class ProgressViewHolder extends RecyclerView.ViewHolder {
    public ProgressBar progressBar;
    public TextView textView;
    public ProgressViewHolder(View itemView) {
        super(itemView);
        progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
        textView = (TextView) itemView.findViewById(R.id.laodViewText);
    }
}
