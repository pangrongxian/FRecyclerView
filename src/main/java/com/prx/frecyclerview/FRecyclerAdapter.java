package com.prx.frecyclerview;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by prx on 2017/12/11 16:58
 * E-Mail：pangrongxian@gmail.com
 */

public class FRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //不包含头部和底部
    private RecyclerView.Adapter mAdapter;

    //由于头部和底部可能有多个，需要用标识来识别
    private int BASE_HEADER_KEY = 5500000;
    private int BASE_Footer_KEY = 6600000;

    //头部和底部集合 必须要用map集合进行标识 key->int  value->object
    SparseArray<View> mHeaderViews;
    SparseArray<View> mFooterViews;

    public FRecyclerAdapter(RecyclerView.Adapter adapter) {
        mAdapter = adapter;
        mHeaderViews = new SparseArray<>();
        mFooterViews = new SparseArray<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //区分头部和底部，根据ViewType来
        //ViewType可能有三部分  头部 底部 Adapter

        //判断是否为头部
        if (isHeaderViewType(viewType)) {
            View headerView = mHeaderViews.get(viewType);
            //header
            return createHeaderOrFooterViewHolder(headerView);
        }

        //判断是否为底部
        if (isFooterViewType(viewType)) {
            View footerView = mFooterViews.get(viewType);
            //footer
            return createHeaderOrFooterViewHolder(footerView);
        }

        //列表
        return mAdapter.onCreateViewHolder(parent, viewType);
    }


    /**
     * 创建头部和底部ViewHolder
     * @param view
     * @return
     */
    private RecyclerView.ViewHolder createHeaderOrFooterViewHolder(View view) {
        return new RecyclerView.ViewHolder(view) {};
    }

    @Override
    public int getItemViewType(int position) {
        //position -> viewType  头部 底部 adapter  必须要用map集合进行标识
        //if(头部）return 头部 key
        //if(中间位置）return mAdapter.getItemViewType(position);
        //if(底部) return 底部 key

        //header
        if (isHeaderPosition(position)) {
            return mHeaderViews.keyAt(position);
        }

        // footer
        if (isFooterPosition(position)) {
            position = position - mHeaderViews.size() - mAdapter.getItemCount();
            return mFooterViews.keyAt(position);
        }

        //adapter
        position = position - mHeaderViews.size();
        return mAdapter.getItemViewType(position);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        //头部，底部不需要绑定数据
        if (isHeaderPosition(position) || isFooterPosition(position)) {
            return;
        }

        // Adapter
        position = position - mHeaderViews.size();
        mAdapter.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return mAdapter.getItemCount() + mHeaderViews.size() + mFooterViews.size();
    }

    //添加头部，底部
    public void addHeaderView(View view) {
        //没有包含头部
        int position = mHeaderViews.indexOfValue(view);
        if (position < 0) {
            //集合没有就添加，不能重复添加
            mHeaderViews.put(BASE_HEADER_KEY++, view);
        }
        notifyDataSetChanged();
    }

    public void addFooterView(View view) {
        //没有包含头部
        int position = mFooterViews.indexOfValue(view);
        if (position < 0) {
            //集合没有就添加，不能重复添加
            mFooterViews.put(BASE_Footer_KEY++, view);
        }
        notifyDataSetChanged();
    }

    //移除头部,底部
    public void removeHeaderView(View view) {
        //没有包含头部
        int index = mHeaderViews.indexOfValue(view);
        if (index < 0) return;
        //集合没有就添加，不能重复添加
        mHeaderViews.removeAt(index);
        notifyDataSetChanged();
    }

    public void removeFooterView(View view) {
        //没有包含底部
        int index = mFooterViews.indexOfValue(view);
        if (index < 0) return;
        //集合没有就添加，不能重复添加
        mFooterViews.removeAt(index);
        notifyDataSetChanged();
    }

    //是否为底部
    private boolean isFooterViewType(int viewType) {
        int footerPosition = mFooterViews.indexOfKey(viewType);
        return footerPosition >= 0;
    }

    //是否为头部
    private boolean isHeaderViewType(int viewType) {
        int headerPosition = mHeaderViews.indexOfKey(viewType);
        return headerPosition >= 0;
    }

    //是否为底部位置
    private boolean isFooterPosition(int position) {
        return position >= (mHeaderViews.size() + mAdapter.getItemCount());
    }

    //是否为头部位置
    private boolean isHeaderPosition(int position) {
        return position < mHeaderViews.size();
    }


    /**
     * 解决GridLayoutManager添加头部和底部不占用一行的问题
     * @param recycler
     */
    public void adjustSpanSize(RecyclerView recycler) {

        if (recycler.getLayoutManager() instanceof GridLayoutManager) {
            final GridLayoutManager layoutManager = (GridLayoutManager) recycler.getLayoutManager();
            layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    boolean isHeaderOrFooter = isHeaderPosition(position) || isFooterPosition(position);
                    return isHeaderOrFooter ? layoutManager.getSpanCount() : 1;
                }
            });
        }
    }

}
