package com.tiancaijiazu.app.activitys.past.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.beans.EarlyCourseBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/7/24/024.
 */

public class RlvAdapter_past_data extends RecyclerView.Adapter {
    private List<EarlyCourseBean.ResultBean.ChapterListBean> mData;

    public RlvAdapter_past_data(List<EarlyCourseBean.ResultBean.ChapterListBean> str) {
        this.mData = str;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_past_data_layout, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.mTitleName.setText(mData.get(i).getTitle());
        holder.mV.setVisibility(View.VISIBLE);
        if(i == mData.size()-1){
            holder.mV.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void addData(List<EarlyCourseBean.ResultBean.ChapterListBean> chapterList) {
        if(mData!=null){
            mData.clear();
        }

        mData.addAll(chapterList);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.title_name)
        TextView mTitleName;
        @BindView(R.id.v)
        View mV;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
