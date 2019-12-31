package com.tiancaijiazu.app.fragments.outermostlayer.college_adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.fragments.beans.CollegeParentBean;
import com.tiancaijiazu.app.utils.MediumBoldTextViewStandard;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/6/5/005.
 */

public class RlvAdapter_parent extends RecyclerView.Adapter {
    private Context mContext;
    private List<CollegeParentBean.ResultBean> mData;
    private OnItemClick onItemClick;

    public RlvAdapter_parent(Context context, List<CollegeParentBean.ResultBean> list) {
        this.mContext  = context;
        this.mData = list;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.parent_vip, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.mText.setText(mData.get(i).getName());
        //Picasso.with(context).load(dataList.get(position).getImage()).into(holder.image);
        Glide.with(mContext).load(mData.get(i).getIco()).into(holder.mImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onClick(v, i, mData);
            }
        });
        holder.mV.setVisibility(View.VISIBLE);
        holder.mShuV.setVisibility(View.VISIBLE);
        if(i%3==2){
            holder.mShuV.setVisibility(View.GONE);
        }
        if(mData.size()%3==0){
            if(i>mData.size()-4){
                holder.mV.setVisibility(View.GONE);
            }
        }else if(mData.size()%3==1){
            if(i>mData.size()-2){
                holder.mV.setVisibility(View.GONE);
            }
        }else if(mData.size()%3==2){
            if(i>mData.size()-3){
                holder.mV.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void addData(List<CollegeParentBean.ResultBean> result) {
        if (mData != null) {
            mData.clear();
        }
        this.mData.addAll(result);
        notifyDataSetChanged();
    }


    public interface OnItemClick {
        void onClick(View view, int position, List<CollegeParentBean.ResultBean> dataList);
    }

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.image)
        ImageView mImage;
        @BindView(R.id.v)
        View mV;
        @BindView(R.id.shu_v)
        View mShuV;
        @BindView(R.id.text)
        MediumBoldTextViewStandard mText;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
