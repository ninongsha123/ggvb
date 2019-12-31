package com.tiancaijiazu.app.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.beans.TeamBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2019/4/26/026.
 */

public class RlvAdapter_team extends RecyclerView.Adapter {


    private List<TeamBean.ResultBean> mData;
    private Context mContext;
    private onClickLisiter mLisiter;

    public RlvAdapter_team(List<TeamBean.ResultBean> resultBeans, Context myTeamActivity) {
        this.mData = resultBeans;
        this.mContext = myTeamActivity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_team, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ViewHolder holder1 = (ViewHolder) holder;
        holder1.mNumber.setText(mData.get(position).getMobile());
        Glide.with(mContext).load(mData.get(position).getAvatar()).into(holder1.mHead);

        holder1.mTF.setText(mData.get(position).getTeamVip()+"");
        holder1.mNcikName.setText(mData.get(position).getNickname());
        String datae = mData.get(position).getSignup();
        String substring = datae.substring(0, datae.length() - 9);
        holder1.mDates.setText("注册时间:"+substring);
        holder1.mZhiSum.setText(mData.get(position).getTeam_level_11()+"");
        holder1.sPokespersonSum.setText(mData.get(position).getTeam_level_21()+"");
        holder1.pResidentSum.setText(mData.get(position).getTeam_level_31()+"");

        holder1.mPromotionCode.setText("推荐码：" + mData.get(position).getReferrerCode());
        holder1.mVip.setText(mData.get(position).getVipTitle());
        holder1.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mLisiter != null) {
                    mLisiter.onClicker(view, position, mData);
                }
            }
        });
        if (position == mData.size() - 1) {
            holder1.mV.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void addData(List<TeamBean.ResultBean> result, boolean b) {
        if (b) {
            if (mData != null) {
                mData.clear();
            }
        }

        this.mData.addAll(result);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.head)
        CircleImageView mHead;
        @BindView(R.id.number)
        TextView mNumber;
        @BindView(R.id.dates)
        TextView mDates;
        @BindView(R.id.text_flag)
        TextView mTextFlag;
        @BindView(R.id.num)
        TextView mNum;
        @BindView(R.id.t_f)
        TextView mTF;
        @BindView(R.id.vip)
        TextView mVip;
        @BindView(R.id.zhi_sum)
        TextView mZhiSum;
        @BindView(R.id.ncikName)
        TextView mNcikName;
        @BindView(R.id.spokesperson_sum)
        TextView sPokespersonSum;
        @BindView(R.id.president_sum)
        TextView pResidentSum;

        @BindView(R.id.promotion_code)
        TextView mPromotionCode;
        @BindView(R.id.v)
        View mV;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface onClickLisiter {
        void onClicker(View view, int position, List<TeamBean.ResultBean> mData);
    }

    public void setOnClickLisiter(onClickLisiter lisiter) {
        this.mLisiter = lisiter;
    }
}
