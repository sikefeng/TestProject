package com.example.sikefeng.testproject.e.swiperefresh.recyclerview;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.sikefeng.testproject.R;
import com.example.sikefeng.testproject.pullTorefreshrecyclerView.DemoLoadMoreView;
import com.lhh.ptrrv.library.PullToRefreshRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author Leo
 */
public class RefreshActivity extends AppCompatActivity {



    RecyclerView recyclerView;

    SwipeRefreshLayout swipeRefreshLayout;

    boolean isLoading;
    private List<Map<String, Object>> data = new ArrayList<>();
    private RecyclerViewAdapter adapter;
    private Handler handler = new Handler();
    LinearLayoutManager layoutManager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh);
//        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
//        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipeRefreshLayout);

//        initView();
//        initData();
        getData();
        adapter = new RecyclerViewAdapter(this, data);
        PullToRefreshRecyclerView mPtrrv = (PullToRefreshRecyclerView) this.findViewById(R.id.ptrrv);
        mPtrrv.setAdapter(adapter);
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
                System.out.println("1111111111111111111111111111111111111111111111111111111");
            }
        });

// set OnRefreshListener
        mPtrrv.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // do refresh here
                System.out.println("2222222222222222222222222222222222222");
            }
        });

// add item divider to recyclerView
//        mPtrrv.getRecyclerView().addItemDecoration(new DividerItemDecoration(this,
//                DividerItemDecoration.VERTICAL));
//
//// add headerView
//        mPtrrv.addHeaderView(View.inflate(this, R.layout.header, null));
//
////set EmptyVIEW
//        mPtrrv.setEmptyView(View.inflate(this,R.layout.empty_view, null));
//
//// set loadmore String
//        mPtrrv.setLoadmoreString("loading");
//
//// set loadmore enable, onFinishLoading(can load more? , select before item)
//        mPtrrv.onFinishLoading(true, false);
    }

    public void initView() {

        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_blue_bright);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        data.clear();
                        getData();
                    }
                }, 2000);
            }
        });
        layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.d("test", "StateChanged = " + newState);

                System.out.println("111111111111111111111111111111111111111111111111");
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.d("test", "onScrolled");
                int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                if (lastVisibleItemPosition + 1 == adapter.getItemCount()) {
                    Log.d("test", "loading executed");

                    boolean isRefreshing = swipeRefreshLayout.isRefreshing();
                    if (isRefreshing) {
                        adapter.notifyItemRemoved(adapter.getItemCount());
                        return;
                    }
                    if (!isLoading) {
                        isLoading = true;
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                getData();
                                Log.d("test", "load more completed");
                                isLoading = false;
                            }
                        }, 1000);
                    }
                }
            }

        });

        //添加点击事件
        adapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.d("test", "item position = " + position);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }


    public void initData() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getData();
            }
        }, 1500);

    }

    /**
     * 获取测试数据
     */
    private void getData() {
        for (int i = 0; i < 2; i++) {
            Map<String, Object> map = new HashMap<>();
            data.add(map);
        }
//        adapter.notifyDataSetChanged();
//        swipeRefreshLayout.setRefreshing(false);
//        adapter.notifyItemRemoved(adapter.getItemCount());


    }


}
