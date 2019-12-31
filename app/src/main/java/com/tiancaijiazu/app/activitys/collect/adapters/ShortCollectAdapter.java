package com.tiancaijiazu.app.activitys.collect.adapters;

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
import com.tiancaijiazu.app.beans.MinVideoBean;
import com.tiancaijiazu.app.beans.ShortCollectListBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShortCollectAdapter extends RecyclerView.Adapter {
    private List<ShortCollectListBean.ResultBean> mData;
    private Context mContext;
    private onClickLisiter mLisiter;

    public ShortCollectAdapter(List<ShortCollectListBean.ResultBean> resultBeans, Context context) {
        this.mData = resultBeans;
        this.mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_subhead_one_layout, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        Glide.with(mContext).load(mData.get(i).getPicUri()).into(holder.mIv);
        holder.mTvA.setText(mData.get(i).getDuration()+"秒");
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

    public void addData(List<ShortCollectListBean.ResultBean> itemList, boolean b) {
        if(b){
            if(mData!=null){
                mData.clear();
            }
        }

        this.mData.addAll(itemList);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv)
        RoundedImageView mIv;
        @BindView(R.id.tv_a)
        TextView mTvA;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface onClickLisiter{
        void onClicker(View view, int position, List<ShortCollectListBean.ResultBean> mData);
    }

    public void setOnClickLisiter(onClickLisiter lisiter){
        this.mLisiter = lisiter;
    }
}
