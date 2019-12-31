package com.tiancaijiazu.app.activitys.user_fragment.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.user_fragment.activitys.FansItActivity;
import com.tiancaijiazu.app.beans.FansBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2019/6/4/004.
 */

public class RlvAdapter_it_fans extends RecyclerView.Adapter {
    private boolean mIsbo;
    private List<FansBean.ResultBean> mData;
    private FansItActivity mContext;
    private onClickLisiter mLisiter;
    private onClickLisiterUser mLisiterUser;

    public RlvAdapter_it_fans(List<FansBean.ResultBean> result, FansItActivity fansItActivity, boolean isbo) {
        this.mData = result;
        this.mContext = fansItActivity;
        this.mIsbo = isbo;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.like_list_item, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        if(mIsbo){
            Glide.with(mContext).load(mData.get(i).getAvatar()).into(holder.mIvHead);
            holder.mUserName.setText(mData.get(i).getNickname());
            if(i==mData.size()-1){
                holder.mV.setVisibility(View.GONE);
            }
            int isFollow = mData.get(i).getIsFollow();
            if(isFollow==0){
                holder.mIvHuXiang.setVisibility(View.GONE);
                holder.mGuanZu.setVisibility(View.VISIBLE);
            }else {
                holder.mIvHuXiang.setVisibility(View.VISIBLE);
                holder.mGuanZu.setVisibility(View.GONE);
            }
            holder.mIvHuXiang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mLisiter!=null){
                        mLisiter.onClicker(v,i,mData);
                    }
                    holder.mIvHuXiang.setVisibility(View.GONE);
                    holder.mGuanZu.setVisibility(View.VISIBLE);
                }
            });
            holder.mGuanZu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mLisiter!=null){
                        mLisiter.onClicker(v,i,mData);
                    }
                    holder.mIvHuXiang.setVisibility(View.VISIBLE);
                    holder.mGuanZu.setVisibility(View.GONE);
                }
            });
        }else {
            Glide.with(mContext).load(mData.get(i).getAvatar()).into(holder.mIvHead);
            holder.mUserName.setText(mData.get(i).getNickname());
            if(i==mData.size()-1){
                holder.mV.setVisibility(View.GONE);
            }
        }
        holder.mIvHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mLisiterUser!=null){
                    mLisiterUser.onClickerUser(view,i,mData);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void addData(List<FansBean.ResultBean> result, boolean b) {
        if(b){
            if(mData!=null){
                mData.clear();
            }
        }
        mData.addAll(result);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_head)
        CircleImageView mIvHead;
        @BindView(R.id.user_name)
        TextView mUserName;
        @BindView(R.id.v)
        View mV;
        @BindView(R.id.iv_hu_xiang)
        ImageView mIvHuXiang;
        @BindView(R.id.guan_zu)
        ImageView mGuanZu;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    public interface onClickLisiter{
        void onClicker(View view,int position,List<FansBean.ResultBean> mData);
    }

    public void setOnClickLisiter(onClickLisiter lisiter){
        this.mLisiter = lisiter;
    }

    public interface onClickLisiterUser{
        void onClickerUser(View view,int position,List<FansBean.ResultBean> mData);
    }

    public void setOnClickLisiterUser(onClickLisiterUser lisiterUser){
        this.mLisiterUser = lisiterUser;
    }
}
