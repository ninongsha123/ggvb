package com.tiancaijiazu.app.activitys.shopping_activity.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.shopping_activity.LineItemActivity;
import com.tiancaijiazu.app.beans.OrderFormBean;
import com.tiancaijiazu.app.fragments.views.NiceImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/6/22/022.
 */

public class RlvAdapter_lineitem extends RecyclerView.Adapter {
    private  List<OrderFormBean.ResultBean.ItemListBean> mData;
    private  LineItemActivity mContext;

    public RlvAdapter_lineitem(List<OrderFormBean.ResultBean.ItemListBean> itemList, LineItemActivity lineItemActivity) {
        this.mData = itemList;
        this.mContext = lineItemActivity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.line_item_layout, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.mNameComm.setText(mData.get(i).getProductName());
        holder.mFormat.setText(mData.get(i).getProductName()+"/"+mData.get(i).getSpecName());
        holder.mMoney.setText("¥"+mData.get(i).getPrice());
        holder.mSum.setText("x"+mData.get(i).getQuantity());
        int i1 = (int) (mData.get(i).getPrice() * mData.get(i).getQuantity());
        holder.mZongMenoy.setText("¥"+i1);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_commodity)
        NiceImageView mIvCommodity;
        @BindView(R.id.name_comm)
        TextView mNameComm;
        @BindView(R.id.format)
        TextView mFormat;
        @BindView(R.id.money)
        TextView mMoney;
        @BindView(R.id.sum)
        TextView mSum;
        @BindView(R.id.yun_fei)
        TextView mYunFei;
        @BindView(R.id.fei_yong)
        TextView mFeiYong;
        @BindView(R.id.zong_menoy)
        TextView mZongMenoy;
        @BindView(R.id.relative)
        RelativeLayout mRelative;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
