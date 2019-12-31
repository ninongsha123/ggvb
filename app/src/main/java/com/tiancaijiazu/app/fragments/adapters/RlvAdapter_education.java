package com.tiancaijiazu.app.fragments.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.fragments.beans.CollegeParentBean;
import com.tiancaijiazu.app.utils.ScreenStatusUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RlvAdapter_education extends RecyclerView.Adapter {
    private List<CollegeParentBean.ResultBean> mData;
    private Context mContext;
    private onClickLisiter mLisiter;

    public RlvAdapter_education(List<CollegeParentBean.ResultBean> resultBeans, Context context) {
        this.mData = resultBeans;
        this.mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_education_layout, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.mTvTitle.setText(mData.get(i).getName());
        TextPaint paint = holder.mTvTitle.getPaint();
        int width = ScreenStatusUtil.setDp(22,mContext);
        float measureText = paint.measureText(mData.get(i).getName())+width;
        ViewGroup.LayoutParams layoutParams = holder.mTvTitle.getLayoutParams();
        layoutParams.width = (int) measureText;
        holder.mTvTitle.setLayoutParams(layoutParams);

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

    public void addData(List<CollegeParentBean.ResultBean> result2) {
        if(mData!=null){
            mData.clear();
        }

        this.mData.addAll(result2);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_title)
        TextView mTvTitle;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface onClickLisiter{
        void onClicker(View view,int position,List<CollegeParentBean.ResultBean> mData);
    }

    public void setOnClickLisiter(onClickLisiter lisiter){
        this.mLisiter = lisiter;
    }
}
