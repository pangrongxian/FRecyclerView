package com.prx.frecyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by prx on 2017/12/11 16:59
 * E-Mail：pangrongxian@gmail.com
 */

public class FRecyclerView extends RecyclerView {

    private FRecyclerAdapter mFRecyclerAdapter;
    private Adapter mAdapter;

    private View emptyView;

    //重写观察者类
    private AdapterDataObserver mDataObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            if (mAdapter == null) return;
            if (mFRecyclerAdapter != mAdapter)
                mFRecyclerAdapter.notifyDataSetChanged();
            checkIfEmpty();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            if (mAdapter == null) return;
            if (mFRecyclerAdapter != mAdapter)
                mFRecyclerAdapter.notifyItemRemoved(positionStart);
            checkIfEmpty();
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            if (mAdapter == null) return;
            if (mFRecyclerAdapter != mAdapter)
                mFRecyclerAdapter.notifyItemMoved(fromPosition, toPosition);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            if (mAdapter == null) return;
            if (mFRecyclerAdapter != mAdapter)
                mFRecyclerAdapter.notifyItemChanged(positionStart);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            if (mAdapter == null) return;
            if (mFRecyclerAdapter != mAdapter)
                mFRecyclerAdapter.notifyItemChanged(positionStart, payload);
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            if (mAdapter == null) return;
            if (mFRecyclerAdapter != mAdapter)
                mFRecyclerAdapter.notifyItemInserted(positionStart);
            checkIfEmpty();
        }
    };


    public FRecyclerView(Context context) {
        this(context, null);
    }

    public FRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setAdapter(Adapter adapter) {


        //防止多次设置Adapter
        if (mAdapter != null) {//注销观察者
            mAdapter.unregisterAdapterDataObserver(mDataObserver);
            mAdapter = null;
        }

        this.mAdapter = adapter;

        if (adapter instanceof FRecyclerAdapter) {
            mFRecyclerAdapter = (FRecyclerAdapter) adapter;
        } else {
            mFRecyclerAdapter = new FRecyclerAdapter(adapter);
        }

        //解决GridLayout添加头部和底部也要占据一行
        mFRecyclerAdapter.adjustSpanSize(this);

        //删除的问题是列表的 Adapter 改变，但 WrapRecyclerAdapter没有改，观察者模式
        super.setAdapter(mFRecyclerAdapter);

        //注册观察者模式
        mAdapter.registerAdapterDataObserver(mDataObserver);



    }

    //添加头部，底部
    public void addHeaderView(View view) {

        //如果没有Adapter就不添加，也可以抛异常
        //必须先设置Adapter才能添加，仿照ListView的处理方式
        if (mFRecyclerAdapter != null) {
            mFRecyclerAdapter.addHeaderView(view);
        }
    }

    public void addFooterView(View view) {
        if (mFRecyclerAdapter != null) {
            mFRecyclerAdapter.addFooterView(view);
        }
    }

    //移除头部,底部
    public void removeHeaderView(View view) {

        if (mFRecyclerAdapter != null) {
            mFRecyclerAdapter.removeHeaderView(view);
        }
    }

    public void removeFooterView(View view) {
        if (mFRecyclerAdapter != null) {
            mFRecyclerAdapter.removeFooterView(view);
        }
    }

    public int getHeaderSize(){
        return mFRecyclerAdapter.mHeaderViews.size();
    }

    public int getFooterSize(){
        return mFRecyclerAdapter.mFooterViews.size();
    }


    //判断是否显示空视图
    public void checkIfEmpty() {
        if (emptyView != null && getAdapter() != null) {
            boolean emptyVisible = getAdapter().getItemCount() - getHeaderSize() - getFooterSize() == 0;
            emptyView.setVisibility(emptyVisible ? VISIBLE : GONE);
            setVisibility(emptyVisible ? GONE : VISIBLE);
        }
    }

    //设置空视图
    public void setEmptyView(View emptyView) {
        this.emptyView = emptyView;
    }
}
