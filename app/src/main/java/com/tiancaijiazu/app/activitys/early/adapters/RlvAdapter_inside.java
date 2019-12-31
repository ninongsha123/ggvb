package com.tiancaijiazu.app.activitys.early.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.early.BeanShi;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/7/5/005.
 */

public class RlvAdapter_inside extends RecyclerView.Adapter {
    private ArrayList<BeanShi> mData;

    public RlvAdapter_inside(ArrayList<BeanShi> beanShis) {
        this.mData = beanShis;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_week_layout, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.mWeek.setText(mData.get(i).getTitle());
        holder.mWeek.setTextColor(mData.get(i).getTitleColor());
        holder.mTitle1.setText(mData.get(i).getData1());
        holder.mData1.setText(mData.get(i).getData2());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void addData(ArrayList<BeanShi> dataShi) {
        if(mData!=null){
            mData.clear();
        }
        this.mData.addAll(dataShi);
        notifyDataSetChanged();
    }

    public void claerData() {
        if(mData!=null){
            mData.clear();
        }
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.week)
        TextView mWeek;
        @BindView(R.id.title1)
        TextView mTitle1;
        @BindView(R.id.data1)
        TextView mData1;
        @BindView(R.id.line1)
        LinearLayout mLine1;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


}
