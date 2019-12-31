package com.tiancaijiazu.app.activitys.shopping_activity.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.bean.Bean;
import com.tiancaijiazu.app.beans.SiteBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/5/18/018.
 */

public class RlvAdapter_DiZhi extends RecyclerView.Adapter {
    public ArrayList<SiteBean.ResultBean> mData;
    private setOnClickLisiter mLisiter;
    private onClickLisiterDele mLisiterDele;
    private onClickLisiterChclikbox mLisiterChclikbox;
    private onClickLisiterBian mLisiterBian;

    public RlvAdapter_DiZhi(ArrayList<SiteBean.ResultBean> resultBeans) {
        this.mData = resultBeans;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == 0) {
            View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dizhi_item, viewGroup, false);
            return new ViewHolder_di(inflate);
        } else {
            View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dizhi_data, viewGroup, false);
            return new ViewHolder_dizhi(inflate);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        int viewType = getItemViewType(i);
        if(viewType==0){
            ViewHolder_di holderDi = (ViewHolder_di) viewHolder;
            holderDi.mLine.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mLisiter != null) {
                        mLisiter.onClickLine(v, i);
                    }
                }
            });
        }else {
            ViewHolder_dizhi holderDizhi = (ViewHolder_dizhi) viewHolder;
            holderDizhi.mName.setText(mData.get(i).getName());
            holderDizhi.mPhone.setText(mData.get(i).getMobile());
            holderDizhi.mDiqu.setText(mData.get(i).getArea()+mData.get(i).getAddress());
            holderDizhi.mShan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mLisiterDele!=null){
                        mLisiterDele.onClickerDele(v,i,mData);
                    }

                    mData.remove(i);
                    notifyDataSetChanged();
                }
            });
            int isDefault = mData.get(i).getIsDefault();
            if(0 == isDefault){
                holderDizhi.mCheckbox.setChecked(false);
            }else {
                holderDizhi.mCheckbox.setChecked(true);
            }
            holderDizhi.mCheckbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(mLisiterChclikbox!=null){
                        mLisiterChclikbox.onClickerChclikbox(v,i,mData);
                    }
                    for (int j = 0; j < mData.size(); j++) {
                        if(i == j){
                            mData.get(j).setIsDefault(1);
                        }else {
                            mData.get(j).setIsDefault(0);
                        }
                    }
                    notifyDataSetChanged();
                }
            });

            holderDizhi.mBian.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mLisiterBian!=null){
                        mLisiterBian.onClickerBian(v,i,mData);
                    }
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mData.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == mData.size()) {
            return 0;
        } else {
            return 1;
        }
    }

    public void addData(List<SiteBean.ResultBean> result) {
        if(mData!=null){
            mData.clear();
        }
        this.mData.addAll(result);
        notifyDataSetChanged();
    }

    class ViewHolder_di extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_add)
        ImageView mIvAdd;
        @BindView(R.id.line)
        LinearLayout mLine;

        ViewHolder_di(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface setOnClickLisiter {
        void onClickLine(View view, int position);
    }

    public void onSetClickLisiterLine(setOnClickLisiter lisiter) {
        this.mLisiter = lisiter;
    }

    class ViewHolder_dizhi extends RecyclerView.ViewHolder{
        @BindView(R.id.name)
        TextView mName;
        @BindView(R.id.phone)
        TextView mPhone;
        @BindView(R.id.diqu)
        TextView mDiqu;
        @BindView(R.id.v)
        View mV;
        @BindView(R.id.checkbox)
        CheckBox mCheckbox;
        @BindView(R.id.moren)
        TextView mMoren;
        @BindView(R.id.tv_shan)
        TextView mTvShan;
        @BindView(R.id.iv_shan)
        ImageView mIvShan;
        @BindView(R.id.tv_bian)
        TextView mTvBian;
        @BindView(R.id.bian)
        LinearLayout mBian;
        @BindView(R.id.shan)
        LinearLayout mShan;

        ViewHolder_dizhi(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface onClickLisiterDele{
        void onClickerDele(View view,int position,ArrayList<SiteBean.ResultBean> mData);
    }

    public void setOnClickLisiterDele(onClickLisiterDele lisiterDele){
        this.mLisiterDele = lisiterDele;
    }

    public interface onClickLisiterChclikbox{
        void onClickerChclikbox(View view,int position,ArrayList<SiteBean.ResultBean> mData);
    }

    public void setOnClickLisiterChclikbox(onClickLisiterChclikbox lisiterChclikbox){
        this.mLisiterChclikbox = lisiterChclikbox;
    }

    public interface onClickLisiterBian{
        void onClickerBian(View view,int position,ArrayList<SiteBean.ResultBean> mData);
    }

    public void setOnClickLisiterBian(onClickLisiterBian lisiterBian){
        this.mLisiterBian = lisiterBian;
    }
}
