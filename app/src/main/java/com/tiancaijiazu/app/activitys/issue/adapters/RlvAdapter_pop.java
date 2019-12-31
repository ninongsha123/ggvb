package com.tiancaijiazu.app.activitys.issue.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.beans.TopicListsBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/5/22/022.
 */

public class RlvAdapter_pop extends RecyclerView.Adapter {
    private ArrayList<TopicListsBean.ResultBean> mData;
    private onClickLisiter mLisiter;

    public RlvAdapter_pop(ArrayList<TopicListsBean.ResultBean> resultBeans) {
        this.mData = resultBeans;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.topic_data, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.mName.setText(mData.get(i).getSubjectName());
        if(i==mData.size()-1){
            holder.mV.setVisibility(View.GONE);
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

    public void addData(List<TopicListsBean.ResultBean> result) {
        if(mData!=null){
            mData.clear();
        }
        this.mData.addAll(result);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.jing)
        ImageView mJing;
        @BindView(R.id.name)
        TextView mName;
        @BindView(R.id.v)
        View mV;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface onClickLisiter{
        void onClicker(View view,int position,ArrayList<TopicListsBean.ResultBean> mData);
    }

    public void setOnClickLisiter(onClickLisiter lisiter){
        this.mLisiter = lisiter;
    }
}
