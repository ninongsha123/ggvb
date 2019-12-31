package com.tiancaijiazu.app.activitys.early.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.beans.FormalCurriculumBean;
import com.tiancaijiazu.app.beans.SubmitBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RlvAdapter_Course_List extends RecyclerView.Adapter {
    private ArrayList<FormalCurriculumBean.ResultBean.ChapterListBean> mData;
    private Context mContext;
    private onClickLisiterCustom mLisiterCustom;
    private RlvAdapter_Course_Content mRlvAdapterCourseContent;
    private ArrayList<RlvAdapter_Course_Content> mList = new ArrayList<>();
    private onClickLisiterCourse mLisiterCourse;
    private onClickLisiterGameOne mLisiterGameOne;
    private onClickLisiterGameTwo mLisiterGameTwo;

    public RlvAdapter_Course_List(ArrayList<FormalCurriculumBean.ResultBean.ChapterListBean> chapterListBeans, Context context) {
        this.mData = chapterListBeans;
        this.mContext = context;
        if(mList!=null){
            mList.clear();
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.trial_one_layout, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.mAlarmClockFatherTv.setText(mData.get(i).getTitle());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        holder.mRecylerView.setLayoutManager(linearLayoutManager);
        List<FormalCurriculumBean.ResultBean.ChapterListBean.ContentsListBean> contentsList = mData.get(i).getContentsList();
        List<SubmitBean.ResultBean> resultBeans = new ArrayList<>();
        mRlvAdapterCourseContent = new RlvAdapter_Course_Content(contentsList,mContext,resultBeans);
        mList.add(mRlvAdapterCourseContent);
        holder.mRecylerView.setAdapter(mRlvAdapterCourseContent);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = holder.mGroupState.isChecked();
                if(checked){
                    holder.mRecylerView.setVisibility(View.GONE);
                    holder.mGroupState.setChecked(false);
                }else {
                    holder.mRecylerView.setVisibility(View.VISIBLE);
                    holder.mGroupState.setChecked(true);
                }
            }
        });


        mRlvAdapterCourseContent.setOnClickLisiterCustom(new RlvAdapter_Course_Content.onClickLisiterCustom() {
            @Override
            public void onClickerCustom(View view, int position, List<FormalCurriculumBean.ResultBean.ChapterListBean.ContentsListBean> mData) {
                if(mLisiterCustom!=null){
                    mLisiterCustom.onClickerCustom(view,position,mData,i);
                }
            }
        });

        mRlvAdapterCourseContent.setOnClickLisiterCourse(new RlvAdapter_Course_Content.onClickLisiterCourse() {
            @Override
            public void onClickerCourse(View view, int position, List<FormalCurriculumBean.ResultBean.ChapterListBean.ContentsListBean> data) {
                if(mLisiterCourse!=null){
                    mLisiterCourse.onClickerCourse(view,position,data,i,mData);
                }
            }
        });

        mRlvAdapterCourseContent.setOnClickLisiterGameOne(new RlvAdapter_Course_Content.onClickLisiterGameOne() {
            @Override
            public void onClickerGameOne(View view, int position, List<FormalCurriculumBean.ResultBean.ChapterListBean.ContentsListBean> mData, List<SubmitBean.ResultBean> mDataGame) {
                if(mLisiterGameOne!=null){
                    mLisiterGameOne.onClickerGameOne(view,position,mData,mDataGame);
                }
            }
        });

        mRlvAdapterCourseContent.setOnClickLisiterGameTwo(new RlvAdapter_Course_Content.onClickLisiterGameTwo() {
            @Override
            public void onClickerGameTwo(View view, int position, List<FormalCurriculumBean.ResultBean.ChapterListBean.ContentsListBean> mData, List<SubmitBean.ResultBean> mDataGame) {
                if(mLisiterGameTwo!=null){
                    mLisiterGameTwo.onClickerGameTwo(view,position,mData,mDataGame);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void addData(List<FormalCurriculumBean.ResultBean.ChapterListBean> chapterList1) {
        if(mData!=null){
            mData.clear();
        }
        this.mData.addAll(chapterList1);
        notifyDataSetChanged();
    }

    public void addSubmit(int wai, int position, List<SubmitBean.ResultBean> result1) {
        mList.get(wai).addSubmit(position,result1);
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.biao)
        ImageView mBiao;
        @BindView(R.id.alarm_clock_father_tv)
        TextView mAlarmClockFatherTv;
        @BindView(R.id.group_state)
        CheckBox mGroupState;
        @BindView(R.id.v)
        View mV;
        @BindView(R.id.recylerView)
        RecyclerView mRecylerView;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface onClickLisiterCustom{
        void onClickerCustom(View view,int position,List<FormalCurriculumBean.ResultBean.ChapterListBean.ContentsListBean> mData,int wai);
    }

    public void setOnClickLisiterCustom(onClickLisiterCustom lisiterCustom){
        this.mLisiterCustom = lisiterCustom;
    }

    public interface onClickLisiterCourse{
        void onClickerCourse(View view,int position,List<FormalCurriculumBean.ResultBean.ChapterListBean.ContentsListBean> mData,int wai,ArrayList<FormalCurriculumBean.ResultBean.ChapterListBean> mWaiData);
    }

    public void setOnClickLisiterCourse(onClickLisiterCourse lisiterCourse){
        this.mLisiterCourse = lisiterCourse;
    }

    public interface onClickLisiterGameOne{
        void onClickerGameOne(View view,int position,List<FormalCurriculumBean.ResultBean.ChapterListBean.ContentsListBean> mData,List<SubmitBean.ResultBean> mDataGame);
    }

    public void setOnClickLisiterGameOne(onClickLisiterGameOne lisiterGameOne){
        this.mLisiterGameOne = lisiterGameOne;
    }

    public interface onClickLisiterGameTwo{
        void onClickerGameTwo(View view,int position,List<FormalCurriculumBean.ResultBean.ChapterListBean.ContentsListBean> mData,List<SubmitBean.ResultBean> mDataGame);
    }

    public void setOnClickLisiterGameTwo(onClickLisiterGameTwo lisiterGameTwo){
        this.mLisiterGameTwo = lisiterGameTwo;
    }
}
