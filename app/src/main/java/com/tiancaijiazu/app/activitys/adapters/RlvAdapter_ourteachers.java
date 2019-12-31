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
import com.tiancaijiazu.app.activitys.activitypage.homepageactivitys.OurgardenActivity;
import com.tiancaijiazu.app.beans.GardenDetailsByBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/7/15/015.
 */

public class RlvAdapter_ourteachers extends RecyclerView.Adapter {

    private List<GardenDetailsByBean.ResultBean.TeacherListBean> mData;
    private OurgardenActivity mContext;

    public RlvAdapter_ourteachers(OurgardenActivity ourgardenActivity, List<GardenDetailsByBean.ResultBean.TeacherListBean> teacherListBeans) {
        this.mContext = ourgardenActivity;
        this.mData = teacherListBeans;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_teachers, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        Glide.with(mContext).load(mData.get(i).getAvatar()).into(holder.mIv);
        holder.mName.setText(mData.get(i).getTitle());
        holder.mTeachingAge.setText(mData.get(i).getLevel());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void addData(List<GardenDetailsByBean.ResultBean.TeacherListBean> teacherList) {
        if(mData!=null){
            mData.clear();
        }
        this.mData.addAll(teacherList);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv)
        RoundedImageView mIv;
        @BindView(R.id.name)
        TextView mName;
        @BindView(R.id.teachingAge)
        TextView mTeachingAge;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
