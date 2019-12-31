package com.tiancaijiazu.app.activitys.income.adapters;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.income.DetailActivity;
import com.tiancaijiazu.app.activitys.income.TiDeitlActivity;
import com.tiancaijiazu.app.activitys.income.TiRecordActivity;
import com.tiancaijiazu.app.activitys.income.TixianjiluActivity;
import com.tiancaijiazu.app.beans.CashOutListBean;
import com.tiancaijiazu.app.beans.DetailBean;
import com.tiancaijiazu.app.utils.TimeUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RlvAdapter_jilu_detail extends RecyclerView.Adapter {
    private TiRecordActivity mContext;
    private List<CashOutListBean.ResultBean> mData;

    public RlvAdapter_jilu_detail(List<CashOutListBean.ResultBean> resultBeans, TiRecordActivity tiRecordActivity) {
        this.mData = resultBeans;
        this.mContext = tiRecordActivity;
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
        String tradeTime = mData.get(i).getDate();
        holder.mTime.setText(tradeTime);
        List<CashOutListBean.ResultBean.ItemsBean> items = mData.get(i).getItems();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        holder.mRecylerView.setLayoutManager(linearLayoutManager);
        RlvAdapter_jilu_detail_nei rlvAdapterDetailNei = new RlvAdapter_jilu_detail_nei(items);
        holder.mRecylerView.setAdapter(rlvAdapterDetailNei);
        rlvAdapterDetailNei.setOnClickLisener(new RlvAdapter_jilu_detail_nei.OnClickLisener() {
            @Override
            public void onClicks(String applyTime,String grantTime, String money, String bank, String cards) {
                if (!grantTime.equals("")) {
                    Intent intent = new Intent(mContext, TixianjiluActivity.class);
                    String time = grantTime.substring(0, grantTime.length() - 4);
                    String times = applyTime.substring(0, applyTime.length() - 4);
                    intent.putExtra("grantTime",time);
                    intent.putExtra("applyTime",times);
                    intent.putExtra("money",money);
                    intent.putExtra("bank",bank);
                    intent.putExtra("cards",cards);
                    mContext.startActivity(intent);
                }else {
                    Intent intent = new Intent(mContext, TiDeitlActivity.class);
                    intent.putExtra("money",money);
                    intent.putExtra("bank",bank);
                    intent.putExtra("cards",cards);
                    mContext.startActivity(intent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void addData( List<CashOutListBean.ResultBean> result, boolean b) {
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
