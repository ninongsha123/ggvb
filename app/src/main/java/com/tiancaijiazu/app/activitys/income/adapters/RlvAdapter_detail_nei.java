package com.tiancaijiazu.app.activitys.income.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.beans.DetailBean;
import com.tiancaijiazu.app.utils.TimeUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RlvAdapter_detail_nei extends RecyclerView.Adapter {
    private List<DetailBean.ResultBean> mData;

    public RlvAdapter_detail_nei(List<DetailBean.ResultBean> resultBeans) {
        this.mData = resultBeans;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_detail_data_layout, viewGroup, false);
        return new ViewHolder(inflate);
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder= (ViewHolder) viewHolder;
        String tradeTime = mData.get(i).getTradeTime();
        long l = TimeUtil.dataOne(tradeTime);
        String s = TimeUtil.QQFormatTime(l);
        holder.mTime.setText(s);
        String tradeTitle = mData.get(i).getTradeTitle();
        String nickname = mData.get(i).getNickname();
        String mobile = mData.get(i).getMobile();
        holder.mName.setText(nickname);
        holder.mMobile.setText(mobile);
        holder.mTitle.setText(tradeTitle);
        holder.mMoney.setText("+ ¥" + mData.get(i).getIncome());
        String sourceOfIncome = mData.get(i).getSourceOfIncome();
        if("1".equals(sourceOfIncome)){
            holder.mIv.setImageResource(R.mipmap.personage);
        }else if("2".equals(sourceOfIncome)){
            holder.mIv.setImageResource(R.mipmap.organization);
        }
        if(i == mData.size()-1){
            holder.mV.setVisibility(View.GONE);
        }
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
        @BindView(R.id.name)
        TextView mName;
        @BindView(R.id.mobiles)
        TextView mMobile;
        @BindView(R.id.titlest)
        TextView mTitle;
        @BindView(R.id.time)
        TextView mTime;
        @BindView(R.id.money)
        TextView mMoney;
        @BindView(R.id.iv)
        ImageView mIv;
        @BindView(R.id.v)
        View mV;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
