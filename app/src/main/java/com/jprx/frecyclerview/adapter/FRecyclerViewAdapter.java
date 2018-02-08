package com.jprx.frecyclerview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jprx.frecyclerview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by prx on 2018/2/8 10:50
 * E-Mail：pangrongxian@gmail.com
 */

public class FRecyclerViewAdapter extends RecyclerView.Adapter<FRecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private List<String> dataList = new ArrayList<>();

    public FRecyclerViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void refresh(List<String> list) {
        dataList = list;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }

}
