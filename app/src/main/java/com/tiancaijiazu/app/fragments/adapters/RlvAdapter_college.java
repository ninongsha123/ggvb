package com.tiancaijiazu.app.fragments.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tiancaijiazu.app.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/4/29/029.
 */

public class RlvAdapter_college extends RecyclerView.Adapter {

    private final static int ZERO = 0;
    private final static int ONE = 1;
    private int mA;
    private int mB;

    public RlvAdapter_college(int a) {
        this.mA = a;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ZERO) {
            View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.college_tou, parent, false);
            return new ViewHolder_tou(inflate);
        } else {
            View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.college_data, parent, false);
            return new ViewHolder(inflate);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == ZERO) {
            ViewHolder_tou holderTou = (ViewHolder_tou) holder;
        } else {
            ViewHolder holder1 = (ViewHolder) holder;
            if(mB==1){
            }else if(mB==2){
            }else if(mB==3){
            }else {
            }
        }
    }

    @Override
    public int getItemCount() {
        return mA;
    }

    public void addData(int a) {
        this.mA = a;
        notifyDataSetChanged();
    }

    public void notImg(int i) {
        this.mB = i;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tou_iv)
        ImageView mTouIv;
        @BindView(R.id.title)
        TextView mTitle;
        @BindView(R.id.tv_age)
        TextView mTvAge;
        @BindView(R.id.tv_data)
        TextView mTvData;
        @BindView(R.id.symbol)
        TextView mSymbol;
        @BindView(R.id.price)
        TextView mPrice;
        @BindView(R.id.qi_tv)
        TextView mQiTv;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == ZERO) {
            return ZERO;
        } else {
            return ONE;
        }
    }

    class ViewHolder_tou extends RecyclerView.ViewHolder {
        @BindView(R.id.tou_iv)
        ImageView mTouIv;

        ViewHolder_tou(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
