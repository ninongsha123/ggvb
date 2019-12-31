package com.tiancaijiazu.app.activitys.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.activitypage.myactivity.AlreadyBoughtActivity;
import com.tiancaijiazu.app.beans.AlreadyBoughtCourse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import www.linwg.org.lib.LCardView;

/**
 * Created by Administrator on 2019/6/27/027.
 */

public class RlvAdapter_already_bounght extends RecyclerView.Adapter {
    private List<AlreadyBoughtCourse.ResultBean> mData;
    private AlreadyBoughtActivity mContext;
    private onClickLisiter mLisiter;

    public RlvAdapter_already_bounght(List<AlreadyBoughtCourse.ResultBean> resultBeans, AlreadyBoughtActivity alreadyBoughtActivity) {
        this.mData = resultBeans;
        this.mContext = alreadyBoughtActivity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.already_bounght_layout, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        if (i < 2) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) holder.mCard.getLayoutParams();
            int top = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, mContext.getResources().getDisplayMetrics());
            layoutParams.topMargin = top;
            holder.mCard.setLayoutParams(layoutParams);
        }
        Glide.with(mContext).load(mData.get(i).getPicList()).into(holder.mIv);
        holder.mTv.setText(mData.get(i).getCourseTitle());

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

    public void addData(List<AlreadyBoughtCourse.ResultBean> result, boolean b) {
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
        ImageView mIv;
        @BindView(R.id.tv)
        TextView mTv;
        @BindView(R.id.card)
        LCardView mCard;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface onClickLisiter{
        void onClicker(View view,int position,List<AlreadyBoughtCourse.ResultBean> mData);
    }

    public void setOnClickLisiter(onClickLisiter lisiter){
        this.mLisiter = lisiter;
    }
}
