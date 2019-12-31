package com.tiancaijiazu.app.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tiancaijiazu.app.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/5/15/015.
 */

public class RlvAdapter_bg extends RecyclerView.Adapter {
    private int mHe;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == 0) {
            View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.one_item, viewGroup, false);
            return new ViewHolder_one(inflate);
        } else {
            View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.two_item, viewGroup, false);
            return new ViewHolder_two(inflate);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        int viewType = getItemViewType(i);
        if(viewType==0){
            ViewHolder_one holderOne = (ViewHolder_one) viewHolder;
        }else {
            ViewHolder_two holderTwo = (ViewHolder_two) viewHolder;
            holderTwo.mTvTwo.setHeight(mHe);
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    public void addUi(int heightDifference) {
        this.mHe = heightDifference;
        notifyDataSetChanged();
    }

    class ViewHolder_one extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_one)
        TextView mTvOne;

        ViewHolder_one(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    class ViewHolder_two extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_two)
        TextView mTvTwo;

        ViewHolder_two(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
