package com.tiancaijiazu.app.activitys.consisting.pop_adapters;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tiancaijiazu.app.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/6/17/017.
 */

public class RlvAdapter_age_pop extends RecyclerView.Adapter {
    private ArrayList<String> mDataHor;
    private ArrayList<String> mData;
    private onClickLisiter mLisiter;

    public RlvAdapter_age_pop(ArrayList<String> strings, ArrayList<String> list) {
        this.mData = strings;
        this.mDataHor = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pop_divide_layout, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.mTv.setText(mData.get(i));
        if(mDataHor.size()!=0){
            for (int j = 0; j < mDataHor.size(); j++) {
                if(mDataHor.get(j).equals(mData.get(i))){
                    holder.mTv.setTextColor(Color.parseColor("#4080FC"));
                }
            }
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mLisiter!=null){
                    mLisiter.onClicker(v,i,mData);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void addData(ArrayList<String> list) {
        if(mData!=null){
            mData.clear();
        }
        mData.addAll(list);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv)
        TextView mTv;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface onClickLisiter{
        void onClicker(View view,int position,ArrayList<String> mData);
    }

    public void setOnClickLisiter(onClickLisiter lisiter){
        this.mLisiter = lisiter;
    }
}
