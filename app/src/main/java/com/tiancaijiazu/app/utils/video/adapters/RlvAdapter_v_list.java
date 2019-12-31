package com.tiancaijiazu.app.utils.video.adapters;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.dao.HistoryTitleBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/7/15/015.
 */

public class RlvAdapter_v_list extends RecyclerView.Adapter {
    private List<HistoryTitleBean> mData;
    private onClickLisiter mLisiter;
    private int mPosition = 0;

    public RlvAdapter_v_list(List<HistoryTitleBean> list, int position) {
        this.mData = list;
        this.mPosition = position;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_v_list_layout, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.mTvName.setText(mData.get(i).getTitle());
        if(i == mPosition){
            holder.mTvName.setTextColor(Color.parseColor("#FFB108"));
        }else {
            holder.mTvName.setTextColor(Color.parseColor("#F5F5F5"));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mLisiter!=null){
                    mLisiter.onClicker(view,i,mData);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void addData(int position) {
        this.mPosition = position;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_name)
        TextView mTvName;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface onClickLisiter{
        void onClicker(View view,int position,List<HistoryTitleBean> mData);
    }

    public void setOnClickLisiter(onClickLisiter lisiter){
        this.mLisiter = lisiter;
    }
}
