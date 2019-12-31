package com.tiancaijiazu.app.activitys.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.activitypage.homepageactivitys.OfflineActivity;
import com.tiancaijiazu.app.activitys.views.FlowGroupView;
import com.tiancaijiazu.app.beans.ClassGardenBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/8/3/003.
 */

public class RlvAdapter_offline extends RecyclerView.Adapter {

    private ArrayList<ClassGardenBean.ResultBean> mData;
    private OfflineActivity mContext;
    private onClick onClick;

    public RlvAdapter_offline(ArrayList<ClassGardenBean.ResultBean> resultBeans, OfflineActivity offlineActivity) {
        this.mData = resultBeans;
        this.mContext = offlineActivity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_offline_layout, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        Glide.with(mContext).load(mData.get(i).getLogoUri()).into(holder.mRounded);
        holder.mTitleName.setText(mData.get(i).getCompanyName());
        holder.mSite.setText(mData.get(i).getAddress());
        holder.mJuli.setText(mData.get(i).getJuli());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onclick(v, i,mData);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void addData(List<ClassGardenBean.ResultBean> result) {
        if(mData!=null){
            mData.clear();
        }

        this.mData.addAll(result);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.rounded)
        RoundedImageView mRounded;
        @BindView(R.id.title_name)
        TextView mTitleName;
        @BindView(R.id.site)
        TextView mSite;
        @BindView(R.id.juli)
        TextView mJuli;
        @BindView(R.id.flow)
        FlowGroupView mFlow;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface onClick {
        void onclick(View view, int position,ArrayList<ClassGardenBean.ResultBean> mData);
    }

    public void setOnClick(onClick onClick) {
        this.onClick = onClick;
    }
}
