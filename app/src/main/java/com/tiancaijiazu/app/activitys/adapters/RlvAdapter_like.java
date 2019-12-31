package com.tiancaijiazu.app.activitys.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.activitypage.ParentingEncyclopediaactivitys.LikeListActivity;
import com.tiancaijiazu.app.beans.LikeListsBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2019/5/23/023.
 */

public class RlvAdapter_like extends RecyclerView.Adapter {
    private ArrayList<LikeListsBean.ResultBean> mData;
    private LikeListActivity mContext;
    private onClickLisiterUser mLisiterUser;

    public RlvAdapter_like(ArrayList<LikeListsBean.ResultBean> resultBeans, LikeListActivity likeListActivity) {
        this.mData = resultBeans;
        this.mContext = likeListActivity;
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
        Glide.with(mContext).load(mData.get(i).getAvatar()).into(holder.mIvHead);
        holder.mUserName.setText(mData.get(i).getNickname());
        if(i==mData.size()-1){
            holder.mV.setVisibility(View.GONE);
        }
        String summary = mData.get(i).getSummary();
        if("".equals(summary)){

        }else {
            holder.mSummary.setText(summary);
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

    public void addData(List<LikeListsBean.ResultBean> result, boolean b) {
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
        @BindView(R.id.v)
        View mV;
        @BindView(R.id.user_name)
        TextView mUserName;
        @BindView(R.id.summary)
        TextView mSummary;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface onClickLisiterUser{
        void onClickerUser(View view,int position,ArrayList<LikeListsBean.ResultBean> mData);
    }

    public void setOnClickLisiterUser(onClickLisiterUser lisiterUser){
        this.mLisiterUser = lisiterUser;
    }
}
