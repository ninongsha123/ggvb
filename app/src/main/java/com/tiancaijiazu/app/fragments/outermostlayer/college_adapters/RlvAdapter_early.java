package com.tiancaijiazu.app.fragments.outermostlayer.college_adapters;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.beans.MallAdBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/6/7/007.
 */

public class RlvAdapter_early extends RecyclerView.Adapter<RlvAdapter_early.MyViewHolder> {
    private List<MallAdBean.ResultBean> dataList;
    private Context context;
    private onClickLisiter mLisiter;

    public RlvAdapter_early(Context context, List<MallAdBean.ResultBean> dataList) {
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.erarly_education, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //添加横线
        holder.mM.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);
        Glide.with(context).load(dataList.get(position).getPicUri()).into(holder.mImage);
        holder.mText.setText(dataList.get(position).getTitle());
        holder.mM.setText("¥"+dataList.get(position).getPrice());
        holder.mMoney.setText(dataList.get(position).getPromoPrice()+"");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mLisiter!=null){
                    mLisiter.onClicker(view,position,dataList);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void addData(List<MallAdBean.ResultBean> result) {
        if(dataList!=null){
            dataList.clear();
        }

        this.dataList.addAll(result);
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image)
        RoundedImageView mImage;
        @BindView(R.id.text)
        TextView mText;
        @BindView(R.id.tv_fu)
        TextView mTvFu;
        @BindView(R.id.money)
        TextView mMoney;
        @BindView(R.id.m)
        TextView mM;

        MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface onClickLisiter{
        void onClicker(View view,int position,List<MallAdBean.ResultBean> dataList);
    }

    public void setOnClickLisiter(onClickLisiter lisiter){
        this.mLisiter = lisiter;
    }
}
