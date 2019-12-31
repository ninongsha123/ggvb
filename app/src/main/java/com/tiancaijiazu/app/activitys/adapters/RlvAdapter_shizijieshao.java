package com.tiancaijiazu.app.activitys.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.activitypage.homepageactivitys.OurgardenActivity;
import com.tiancaijiazu.app.beans.GardenDetailsByBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/7/15/015.
 */

public class RlvAdapter_shizijieshao extends RecyclerView.Adapter {

    private ArrayList<GardenDetailsByBean.ResultBean.CourseListBean> mData;
    private OurgardenActivity mContext;
    private onClickLisiter mLisiter;

    public RlvAdapter_shizijieshao(OurgardenActivity ourgardenActivity, ArrayList<GardenDetailsByBean.ResultBean.CourseListBean> courseListBeans) {
        this.mContext = ourgardenActivity;
        this.mData = courseListBeans;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_shizijieshao, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.mName.setText(mData.get(i).getTitle());
        holder.mLeibie.setText(mData.get(i).getCourseType()+"｜"+mData.get(i).getMonthMin()+"-"+mData.get(i).getMonthMax()+"个月");
        //holder.mAge.setText(mData.get(i).getMonthMin()+"-"+mData.get(i).getMonthMax()+"个月");
        holder.mContent.setText(mData.get(i).getSummary());
        Glide.with(mContext).load(mData.get(i).getCover()).into(holder.mIv);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mLisiter != null) {
                    mLisiter.onClicker(view, i,mData);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void addData(List<GardenDetailsByBean.ResultBean.CourseListBean> courseList) {
        if (mData != null) {
            mData.clear();
        }
        this.mData.addAll(courseList);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv)
        RoundedImageView mIv;
        @BindView(R.id.name)
        TextView mName;
        @BindView(R.id.leibie)
        TextView mLeibie;
        /*@BindView(R.id.line)
        View mLine;
        @BindView(R.id.age)
        TextView mAge;*/
        @BindView(R.id.lin)
        LinearLayout mLin;
        @BindView(R.id.content)
        TextView mContent;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface onClickLisiter {
        void onClicker(View view, int position,ArrayList<GardenDetailsByBean.ResultBean.CourseListBean> mData);
    }

    public void setOnClickLisiter(onClickLisiter lisiter) {
        this.mLisiter = lisiter;
    }
}
