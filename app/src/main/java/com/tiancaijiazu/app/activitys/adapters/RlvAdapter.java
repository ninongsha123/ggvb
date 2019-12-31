package com.tiancaijiazu.app.activitys.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.fragments.beans.CollegeCourseBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/6/21/021.
 */

public class RlvAdapter extends RecyclerView.Adapter {

    private int mIsBought;
    private List<CollegeCourseBean.ResultBean.ChapterListBean> mData;
    private Context mContext;
    private onClickLisiter mLisiter;
    private onClickLisiterTu mLisiterTu;

    public RlvAdapter(List<CollegeCourseBean.ResultBean.ChapterListBean> chapterList, Context context, int isBought) {
        this.mData = chapterList;
        this.mContext = context;
        this.mIsBought = isBought;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_main_father, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        // 设置标题上的文本信息
        holder.mAlarmClockFatherTv.setText(mData.get(i).getTitle());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        holder.mRecyler.setLayoutManager(linearLayoutManager);
        List<CollegeCourseBean.ResultBean.ChapterListBean.ContentsListBean> contentsList = mData.get(i).getContentsList();
        RlvAdapter_nei rlvAdapterNei = new RlvAdapter_nei(contentsList,mContext,mIsBought);
        holder.mRecyler.setAdapter(rlvAdapterNei);
        rlvAdapterNei.setOnClickLisiter(new RlvAdapter_nei.onClickLisiter() {
            @Override
            public void onClicker(View view, int position, List<CollegeCourseBean.ResultBean.ChapterListBean.ContentsListBean> mData) {
                if(mLisiter!=null){
                    mLisiter.onClicker(view,position,mData);
                }
            }
        });
        rlvAdapterNei.setOnClickLisiterTu(new RlvAdapter_nei.onClickLisiterTu() {
            @Override
            public void onClickerTu(View view, int position, List<CollegeCourseBean.ResultBean.ChapterListBean.ContentsListBean> mData) {
                if(mLisiterTu!=null){
                    mLisiterTu.onClickerTu(view,position,mData);
                }
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = holder.mGroupState.isChecked();
                if(checked){
                    holder.mRecyler.setVisibility(View.VISIBLE);
                    holder.mGroupState.setChecked(false);

                }else {
                    holder.mRecyler.setVisibility(View.GONE);
                    holder.mGroupState.setChecked(true);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void addData(List<CollegeCourseBean.ResultBean.ChapterListBean> chapterList, int isBought) {
        if(mData!=null){
            mData.clear();
        }

        this.mData.addAll(chapterList);
        this.mIsBought = isBought;
        notifyDataSetChanged();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.alarm_clock_father_tv)
        TextView mAlarmClockFatherTv;
        @BindView(R.id.group_state)
        CheckBox mGroupState;
        @BindView(R.id.recyler)
        RecyclerView mRecyler;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface onClickLisiter{
        void onClicker(View view,int position,List<CollegeCourseBean.ResultBean.ChapterListBean.ContentsListBean> mData);
    }

    public void setOnClickLisiter(onClickLisiter lisiter){
        this.mLisiter = lisiter;
    }

    public interface onClickLisiterTu{
        void onClickerTu(View view,int position,List<CollegeCourseBean.ResultBean.ChapterListBean.ContentsListBean> mData);
    }

    public void setOnClickLisiterTu(onClickLisiterTu lisiterTu){
        this.mLisiterTu = lisiterTu;
    }
}
