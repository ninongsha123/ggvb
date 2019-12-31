package com.tiancaijiazu.app.fragments.outermostlayer.college_adapters;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.beans.CourseListBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/6/7/007.
 */

public class RlvAdapter_vip extends RecyclerView.Adapter<RlvAdapter_vip.MyViewHolder> {
    private List<CourseListBean.ResultBean> dataList;
    private Context mContext;
    private OnItemClick onItemClick;

    public RlvAdapter_vip(Context context, List<CourseListBean.ResultBean> resultBeans) {
        this.dataList = resultBeans;
        this.mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_vipclass, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (position == 0) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) holder.mRela.getLayoutParams();
            int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, mContext.getResources().getDisplayMetrics());
            layoutParams.topMargin = left;
            holder.mRela.setLayoutParams(layoutParams);
        }
        Glide.with(mContext).load(dataList.get(position).getPicUri()).into(holder.mNiceIv);
        holder.mYuanjia.setText("¥"+dataList.get(position).getPrice()+"");
        holder.mTitleone.setText(dataList.get(position).getTitle());
        holder.mContent.setText(dataList.get(position).getSummary());
        holder.mTeacher.setText("讲师："+dataList.get(position).getInstructor());
        holder.mMoney.setText(dataList.get(position).getPromoPrice()+"");
        holder.mSum.setText("共"+dataList.get(position).getClassCount()+"课时");
        //添加横线
        holder.mYuanjia.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onClick(v,position,dataList);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void addData(List<CourseListBean.ResultBean> result, boolean b) {
        if(b){
            if(dataList!=null){
                dataList.clear();
            }
        }
        this.dataList.addAll(result);
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.rtl)
        RelativeLayout mRtl;
        @BindView(R.id.rela)
        RelativeLayout mRela;
        @BindView(R.id.titleone)
        TextView mTitleone;
        @BindView(R.id.content)
        TextView mContent;
        @BindView(R.id.number_icon)
        ImageView mNumberIcon;
        @BindView(R.id.teacher)
        TextView mTeacher;
        @BindView(R.id.fu_hao)
        TextView mFuHao;
        @BindView(R.id.money)
        TextView mMoney;
        @BindView(R.id.sum)
        TextView mSum;
        @BindView(R.id.yuan_jia)
        TextView mYuanjia;
        @BindView(R.id.nice_iv)
        RoundedImageView mNiceIv;

        MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClick {
        void onClick(View view,int position,List<CourseListBean.ResultBean> dataList);
    }

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }


}
