package com.jprx.frecyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import com.jprx.frecyclerview.adapter.FRecyclerViewAdapter;
import com.prx.frecyclerview.FRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FRecyclerViewAdapter adapter;

    private FRecyclerView mRecyclerView;
    private View emptyView;

    private List<String> mDataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        initData();

    }


    private void initView() {

        mRecyclerView = findViewById(R.id.recycler_view);
        emptyView = findViewById(R.id.empty_view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        View headerView = LayoutInflater.from(this).inflate(R.layout.recycler_header, mRecyclerView, false);
        View footerView = LayoutInflater.from(this).inflate(R.layout.recycler_footer, mRecyclerView, false);

        adapter = new FRecyclerViewAdapter(MainActivity.this);
        mRecyclerView.setAdapter(adapter);

        mRecyclerView.addHeaderView(headerView);
        mRecyclerView.addFooterView(footerView);

        /**
         * 当数据源List的size为0时候，空布局显示
         */
        mRecyclerView.setEmptyView(emptyView);
    }


    private void initData() {

//        mDataList.add("0");
//        mDataList.add("1");
//        mDataList.add("2");
//        mDataList.add("3");

        /**
         * 调用 refresh（）方法时候，且数据源List为0条，才显示空布局
         */
        adapter.refresh(mDataList);

    }


}
