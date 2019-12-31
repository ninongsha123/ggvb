package com.tiancaijiazu.app.activitys.evalua.adapters;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.evalua.AssessmentRecordsActivity;
import com.tiancaijiazu.app.beans.BabyAgeBean;
import com.tiancaijiazu.app.beans.BabyMessageBean;
import com.tiancaijiazu.app.beans.ReviewedListBean;
import com.tiancaijiazu.app.utils.TimeUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/8/21/021.
 */

public class RlvAdapter_Record extends RecyclerView.Adapter {
    private List<BabyMessageBean.ResultBean> mDataBaby;
    private ArrayList<ReviewedListBean.ResultBean> mDataQu;
    private List<BabyAgeBean.ResultBean> mData;
    private AssessmentRecordsActivity mContext;
    private boolean isbo = true;
    private onClickLisiterLook mLisiterLook;
    private onClickLisiterBegin mLisiterBegin;

    public RlvAdapter_Record(List<BabyAgeBean.ResultBean> resultBeans, AssessmentRecordsActivity assessmentRecordsActivity, ArrayList<ReviewedListBean.ResultBean> resultBeans1, List<BabyMessageBean.ResultBean> result) {
        this.mData = resultBeans;
        this.mContext = assessmentRecordsActivity;
        this.mDataQu = resultBeans1;
        this.mDataBaby = result;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_record_list, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        if (i == 0) {
            holder.mVLineTop.setBackgroundColor(Color.TRANSPARENT);
            holder.mVLineButtom.setBackgroundColor(Color.parseColor("#00DEFF"));
        } else if (i == mData.size() - 1) {
            holder.mVLineTop.setBackgroundColor(Color.parseColor("#00DEFF"));
            holder.mVLineButtom.setBackgroundColor(Color.TRANSPARENT);
        } else {
            holder.mVLineTop.setBackgroundColor(Color.parseColor("#00DEFF"));
            holder.mVLineButtom.setBackgroundColor(Color.parseColor("#00DEFF"));
        }
        Log.i("ceping", "onBindViewHolder: " + mData.get(i).getTitle());
        holder.mMonth.setText(mData.get(i).getTitle());
        isbo = true;
        for (int j = 0; j < mDataQu.size(); j++) {
            long subjectId = mDataQu.get(j).getSubjectId();
            if (mData.get(i).getSubjectId() == subjectId) {
                isbo = false;
                holder.mDateImg.setBackgroundResource(R.mipmap.look_over_back);
                holder.mDateTv.setVisibility(View.GONE);
                holder.mDateImg.setVisibility(View.VISIBLE);
                String reportTime = mDataQu.get(j).getReportTime();
                for (int k = 0; k < mDataBaby.size(); k++) {
                    if (mDataQu.get(j).getBabyId() == mDataBaby.get(k).getBabyId()) {
                        String age = TimeUtil.getAge(mDataBaby.get(k).getBirthday(), reportTime);
                        holder.mBabyTime.setText("测评于宝宝" + age);
                    }
                }
            }
        }
        holder.mDateImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mLisiterLook != null) {
                    mLisiterLook.onClikerLook(view, i, mData);
                }
            }
        });
        holder.mDateTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = holder.mDateTv.getText().toString();
                if (mLisiterBegin != null) {
                    mLisiterBegin.onClickerBegin(view, i, mData, s);
                }
            }
        });
        if (isbo) {
            for (int k = 0; k < mDataBaby.size(); k++) {
                if (mDataQu.size() != 0) {
                    if (mDataQu.get(0).getBabyId() == mDataBaby.get(k).getBabyId()) {
                        String birthday = mDataBaby.get(k).getBirthday();
                        String nowTime = TimeUtil.getNowTime();
                        int month = TimeUtil.getMonth(birthday, nowTime);
                        int dayAge = TimeUtil.getDayAge(birthday, nowTime);
                        holder.mBabyTime.setText("");
                        if (month < 12) {
                            if ((month > mData.get(i).getMonthMin()) || ((mData.get(i).getMonthMin() - month) == 1 && dayAge > 15)) {
                                holder.mDateTv.setBackgroundResource(R.drawable.bg_begin_appraisal);
                                holder.mDateImg.setVisibility(View.GONE);
                                holder.mDateTv.setVisibility(View.VISIBLE);
                                holder.mDateTv.setText("开始测评");
                                holder.mDateTv.setTextColor(Color.parseColor("#00DEFF"));
                            } else {
                                holder.mDateTv.setBackgroundResource(R.drawable.bg_begin_appraisal_not);
                                holder.mDateImg.setVisibility(View.GONE);
                                holder.mDateTv.setVisibility(View.VISIBLE);
                                holder.mDateTv.setText("未开启测评");
                                holder.mDateTv.setTextColor(Color.parseColor("#999999"));
                                holder.mBabyTime.setText("宝宝满" + mData.get(i).getTitle() + "后开启测评");
                            }
                        } else {
                            if (month > mData.get(i).getMonthMin() ||(month == mData.get(i).getMonthMin() && dayAge > 15) ) {
                                holder.mDateTv.setBackgroundResource(R.drawable.bg_begin_appraisal);
                                holder.mDateImg.setVisibility(View.GONE);
                                holder.mDateTv.setVisibility(View.VISIBLE);
                                holder.mDateTv.setText("开始测评");
                                holder.mDateTv.setTextColor(Color.parseColor("#00DEFF"));
                            } else {
                                holder.mDateTv.setBackgroundResource(R.drawable.bg_begin_appraisal_not);
                                holder.mDateImg.setVisibility(View.GONE);
                                holder.mDateTv.setVisibility(View.VISIBLE);
                                holder.mDateTv.setText("未开启测评");
                                holder.mDateTv.setTextColor(Color.parseColor("#999999"));
                                holder.mBabyTime.setText("宝宝满" + mData.get(i).getTitle() + "后开启测评");
                            }
                        }

                    }
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void addData(List<BabyAgeBean.ResultBean> result) {
        if(mData!=null){
            mData.clear();
        }
        this.mData.addAll(result);
        notifyDataSetChanged();
    }

    public void addQu(List<ReviewedListBean.ResultBean> result1) {
        if(mDataQu!=null){
            mDataQu.clear();
        }
        this.mDataQu.addAll(result1);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.month)
        TextView mMonth;
        @BindView(R.id.v_line_top)
        View mVLineTop;
        @BindView(R.id.circle)
        ImageView mCircle;
        @BindView(R.id.v_line_buttom)
        View mVLineButtom;
        @BindView(R.id.line1)
        LinearLayout mLine1;
        @BindView(R.id.heng)
        View mHeng;
        @BindView(R.id.date_tv)
        TextView mDateTv;
        @BindView(R.id.date_img)
        ImageView mDateImg;
        @BindView(R.id.baby_time)
        TextView mBabyTime;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface onClickLisiterLook{
        void onClikerLook(View view,int position,List<BabyAgeBean.ResultBean> mData);
    }

    public void setOnClickLisiter(onClickLisiterLook lisiter){
        this.mLisiterLook = lisiter;
    }

    public interface onClickLisiterBegin{
        void onClickerBegin(View view,int position,List<BabyAgeBean.ResultBean> mData,String biao);
    }

    public void setOnClickLisiterBegin(onClickLisiterBegin lisiterBegin){
        this.mLisiterBegin = lisiterBegin;
    }
}
