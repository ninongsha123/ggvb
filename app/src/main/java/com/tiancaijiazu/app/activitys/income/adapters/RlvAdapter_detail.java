package com.tiancaijiazu.app.activitys.income.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.income.DetailActivity;
import com.tiancaijiazu.app.beans.DetailBean;
import com.tiancaijiazu.app.utils.TimeUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RlvAdapter_detail extends RecyclerView.Adapter {
    private Context mContext;
    private List<DetailBean.ResultBean> mData;

    public RlvAdapter_detail(Context detailActivity, List<DetailBean.ResultBean> resultBeans1) {
        this.mData = resultBeans1;
        this.mContext = detailActivity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_detail_title_layout, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        String tradeTime = mData.get(0).getTradeTime();
        long l = TimeUtil.dataOne(tradeTime);
        String s = TimeUtil.QQFormatTimeTwo(l);
        holder.mTime.setText(s);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        holder.mRecylerView.setLayoutManager(linearLayoutManager);
        RlvAdapter_detail_nei rlvAdapterDetailNei = new RlvAdapter_detail_nei(mData);
        holder.mRecylerView.setAdapter(rlvAdapterDetailNei);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void addData(List<DetailBean.ResultBean> result, boolean b) {
        if(b){
            if(mData!=null){
                mData.clear();
            }
        }

        this.mData.addAll(result);
        notifyDataSetChanged();

    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.time)
        TextView mTime;
        @BindView(R.id.recylerView)
        RecyclerView mRecylerView;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
