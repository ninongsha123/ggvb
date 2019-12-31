package com.tiancaijiazu.app.activitys.income.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.beans.CashOutListBean;
import com.tiancaijiazu.app.beans.DetailBean;
import com.tiancaijiazu.app.utils.TimeUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RlvAdapter_jilu_detail_nei extends RecyclerView.Adapter {
    private List<CashOutListBean.ResultBean.ItemsBean> mData;

    public RlvAdapter_jilu_detail_nei(List<CashOutListBean.ResultBean.ItemsBean> resultBeans) {
        this.mData = resultBeans;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_detail_jilu_layout, viewGroup, false);
        return new ViewHolder(inflate);
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder= (ViewHolder) viewHolder;
        String grantTime = mData.get(i).getGrantTime();
        String applyTime = mData.get(i).getApplyTime();
        double money = mData.get(i).getCash();
        String bank = mData.get(i).getBank();
        String cardNo = mData.get(i).getCardNo();
        String substring = cardNo.substring(cardNo.length() - 4, cardNo.length());
        if (grantTime.equals("")){
            holder.mTime.setText("业务处理中");
        }else {
            String time = grantTime.substring(0, grantTime.length() - 7);
            holder.mTime.setText("转账时间" + time);
        }
        holder.mName.setText("收入提现-到"+bank+" ("+substring+")");
        holder.mMoney.setText(mData.get(i).getCash()+"");
        if(i == mData.size()-1){
            holder.mV.setVisibility(View.GONE);
        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnClickLisener.onClicks(applyTime,grantTime,money+"",bank,substring);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.name)
        TextView mName;
        @BindView(R.id.time)
        TextView mTime;
        @BindView(R.id.money)
        TextView mMoney;
        @BindView(R.id.v)
        View mV;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public OnClickLisener mOnClickLisener;

    public void setOnClickLisener(OnClickLisener onClickLisener) {
        mOnClickLisener = onClickLisener;
    }

    public interface OnClickLisener{
        void onClicks(String applyTime,String grantTime,String money,String bank,String cards);
    }
}
