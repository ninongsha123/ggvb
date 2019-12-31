package com.tiancaijiazu.app.fragments.outermostlayer.shoppingmallfragment.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.beans.CommTopicSwitcherBean;
import com.tiancaijiazu.app.utils.ScreenStatusUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RlvAdapter_topic_switcher extends RecyclerView.Adapter {
    private List<CommTopicSwitcherBean.ResultBean> mData;
    private Context mContext;
    private int position = 0;
    private onClickLisiter mLisiter;

    public RlvAdapter_topic_switcher(List<CommTopicSwitcherBean.ResultBean> resultBeans, Context context) {
        this.mData = resultBeans;
        this.mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_topic_switcher_layout, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        if(i==0){
            holder.mTvOne.setText("热门");
            TextPaint paint = holder.mTvOne.getPaint();
            int width = ScreenStatusUtil.setDp(22,mContext);
            float measureText = paint.measureText("热门")+width;
            ViewGroup.LayoutParams layoutParams = holder.mTvOne.getLayoutParams();
            layoutParams.width = (int) measureText;
            holder.mTvOne.setLayoutParams(layoutParams);
        }else if(i==1){
            holder.mTvOne.setText("最新");
            TextPaint paint = holder.mTvOne.getPaint();
            int width = ScreenStatusUtil.setDp(22,mContext);
            float measureText = paint.measureText("最新")+width;
            ViewGroup.LayoutParams layoutParams = holder.mTvOne.getLayoutParams();
            layoutParams.width = (int) measureText;
            holder.mTvOne.setLayoutParams(layoutParams);
        }else {
            holder.mTvOne.setText(mData.get(i-2).getSubjectName());
            TextPaint paint = holder.mTvOne.getPaint();
            int width = ScreenStatusUtil.setDp(22,mContext);
            float measureText = paint.measureText(mData.get(i-2).getSubjectName())+width;
            ViewGroup.LayoutParams layoutParams = holder.mTvOne.getLayoutParams();
            layoutParams.width = (int) measureText;
            holder.mTvOne.setLayoutParams(layoutParams);
        }

        if(i == position){
            holder.mTvOne.setBackgroundResource(R.drawable.bg_comm_topic_switcher);
            holder.mTvOne.setTextColor(Color.parseColor("#FFFFFF"));
        }else {
            holder.mTvOne.setBackgroundColor(Color.parseColor("#FFFFFF"));
            holder.mTvOne.setTextColor(Color.parseColor("#999999"));
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
        if(mData.size()==0){
            return 0;
        }else {
            return mData.size()+2;
        }
    }

    public void addData(List<CommTopicSwitcherBean.ResultBean> result2) {
        if(mData!=null){
            mData.clear();
        }

        this.mData.addAll(result2);
        notifyDataSetChanged();
    }

    public void addSwit(int position) {
        this.position = position;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_one)
        TextView mTvOne;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface onClickLisiter{
        void onClicker(View view,int position,List<CommTopicSwitcherBean.ResultBean> mData);
    }

    public void setOnClickLisiter(onClickLisiter lisiter){
        this.mLisiter = lisiter;
    }
}
