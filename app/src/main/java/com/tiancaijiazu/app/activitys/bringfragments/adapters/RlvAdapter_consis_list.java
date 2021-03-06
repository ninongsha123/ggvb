package com.tiancaijiazu.app.activitys.bringfragments.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.beans.WebCollectListBean;
import com.tiancaijiazu.app.fragments.views.NiceImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/6/17/017.
 */

public class RlvAdapter_consis_list extends RecyclerView.Adapter {
    public List<WebCollectListBean.ResultBean> mData;
    private Context mContext;
    private onClickLisiter mLisiter;

    public RlvAdapter_consis_list(List<WebCollectListBean.ResultBean> resultBeans, Context context) {
        this.mData = resultBeans;
        this.mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_data, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        Glide.with(mContext).load(mData.get(i).getPic()).into(holder.mIv);
        holder.mName.setText(mData.get(i).getTitle());
        holder.mSummary.setText(mData.get(i).getSummary());
        if (i == mData.size()-1) {
            holder.mV.setVisibility(View.GONE);
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

    public void addData(List<WebCollectListBean.ResultBean> result, boolean b) {
        if(b){
            if(mData!=null){
                mData.clear();
            }
        }
        this.mData.addAll(result);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv)
        RoundedImageView mIv;
        @BindView(R.id.name)
        TextView mName;
        @BindView(R.id.summary)
        TextView mSummary;
        @BindView(R.id.v)
        View mV;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface onClickLisiter{
        void onClicker(View view,int position,List<WebCollectListBean.ResultBean> mData);
    }

    public void setOnClickLisiter(onClickLisiter lisiter){
        this.mLisiter = lisiter;
    }
}
