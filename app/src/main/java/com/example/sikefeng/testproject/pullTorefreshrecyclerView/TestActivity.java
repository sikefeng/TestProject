package com.example.sikefeng.testproject.pullTorefreshrecyclerView;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.example.sikefeng.testproject.R;
import com.lhh.ptrrv.library.PullToRefreshRecyclerView;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        PullToRefreshRecyclerView mPtrrv = (PullToRefreshRecyclerView) this.findViewById(R.id.ptrrv);
        // custom own load-more-view and add it into ptrrv
        DemoLoadMoreView loadMoreView = new DemoLoadMoreView(this, mPtrrv.getRecyclerView());
        loadMoreView.setLoadmoreString("demo_loadmore");
        loadMoreView.setLoadMorePadding(100);

        mPtrrv.setLoadMoreFooter(loadMoreView);

//remove header
        mPtrrv.removeHeader();

// set true to open swipe(pull to refresh, default is true)
        mPtrrv.setSwipeEnable(true);

// set the layoutManager which to use
        mPtrrv.setLayoutManager(new LinearLayoutManager(this));

// set PagingableListener
        mPtrrv.setPagingableListener(new PullToRefreshRecyclerView.PagingableListener() {
            @Override
            public void onLoadMoreItems() {
                //do loadmore here
            }
        });

// set OnRefreshListener
        mPtrrv.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // do refresh here
            }
        });

// add item divider to recyclerView
        mPtrrv.getRecyclerView().addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));

// add headerView
        mPtrrv.addHeaderView(View.inflate(this, R.layout.header, null));

//set EmptyVIEW
        mPtrrv.setEmptyView(View.inflate(this,R.layout.empty_view, null));

// set loadmore String
        mPtrrv.setLoadmoreString("loading");

// set loadmore enable, onFinishLoading(can load more? , select before item)
        mPtrrv.onFinishLoading(true, false);
//        PtrrvAdapter mAdapter = new PtrrvAdapter(this);
//        mPtrrv.setAdapter(mAdapter);
    }
}
