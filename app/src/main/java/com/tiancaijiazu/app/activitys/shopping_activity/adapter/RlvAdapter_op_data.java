package com.tiancaijiazu.app.activitys.shopping_activity.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.bean.CouponBean;
import com.tiancaijiazu.app.activitys.shopping_activity.POActivity;
import com.tiancaijiazu.app.beans.AtOnceBean;
import com.tiancaijiazu.app.beans.CourseBean;
import com.tiancaijiazu.app.beans.Shopping_carBean;
import com.tiancaijiazu.app.fragments.views.NiceImageView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/5/18/018.
 */

public class RlvAdapter_op_data extends RecyclerView.Adapter {
    private int mTradeType = 0;
    private CouponBean.ResultBean mUpTv;
    private CourseBean mCourseBean;
    private AtOnceBean mDataAT;
    private String mBiao;
    private ArrayList<Shopping_carBean.ResultBean> mData;
    private POActivity mContext;
    private onClickLisiterCoupon mLisiterCoupon;
    private boolean isbo;

    public RlvAdapter_op_data(ArrayList<Shopping_carBean.ResultBean> data, POActivity context, String biao, AtOnceBean dataAT, CourseBean courseBean, int mTradeType, CouponBean.ResultBean resultBean) {
        this.mData = data;
        this.mContext = context;
        this.mBiao = biao;
        this.mDataAT = dataAT;
        this.mTradeType = mTradeType;
        this.mCourseBean = courseBean;
        this.mUpTv = resultBean;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == 0) {
            View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_data_po, viewGroup, false);
            return new ViewHolder(inflate);
        } else {
            View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_data_po_di, viewGroup, false);
            return new ViewHolder_di(inflate);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        int viewType = getItemViewType(i);
        if(viewType == 0){
            ViewHolder holder = (ViewHolder) viewHolder;
            if(mBiao.equals("1")){
                Glide.with(mContext).load(mData.get(i).getPicUri()).into(holder.mIv);
                holder.mTitleName.setText(mData.get(i).getProductName());
                holder.mGuiGe.setText(mData.get(i).getSpecName());
                holder.mMoney.setText(mData.get(i).getOldPrice()+"");
                holder.mSum.setText("x"+mData.get(i).getQuantity());
            }else if(mBiao.equals("2")){
                Glide.with(mContext).load(mDataAT.getImgurl()).into(holder.mIv);
                holder.mTitleName.setText(mDataAT.getName());
                holder.mGuiGe.setText(mDataAT.getColour());
                holder.mMoney.setText(mDataAT.getPrice()+"");
                holder.mSum.setText("x"+mDataAT.getSum());
            }else if(mBiao.equals("course")){
                Glide.with(mContext).load(mCourseBean.getPicUri()).into(holder.mIv);
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) holder.mTitleName.getLayoutParams();
                int top = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 11, mContext.getResources().getDisplayMetrics());
                layoutParams.topMargin = top;
                holder.mTitleName.setLayoutParams(layoutParams);
                holder.mTitleName.setText(mCourseBean.getTitle());
                holder.mMoney.setText(mCourseBean.getPromoPrice()+"");
                holder.mSum.setVisibility(View.GONE);
                holder.mGuiGe.setVisibility(View.GONE);
            }

        }else {
            ViewHolder_di viewHolderDi = (ViewHolder_di) viewHolder;
            if(isbo){
                viewHolderDi.mCoupons.setText("-¥"+mUpTv.getCouponFee());
            }else {
                Log.i("yx465", "onBindViewHolder: "+mTradeType);
                if (mTradeType>0){
                    viewHolderDi.mCoupons.setText("选择优惠券");
                }else if (mTradeType==0){
                    viewHolderDi.mCoupons.setText("无可用优惠券");
                    viewHolderDi.mCoupons.setTextColor(Color.parseColor("#BBBBBB"));
                    viewHolderDi.mLineDiscountCoupon.setClickable(false);
                }
            }

            viewHolderDi.mLineDiscountCoupon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mLisiterCoupon!=null){
                        mLisiterCoupon.onClickCoupon(view,i,viewHolderDi.mCoupons);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if(mBiao.equals("1")){
            return mData.size()+1;
        }else{
            return 2;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(mBiao.equals("1")){
            if (position == mData.size()) {
                return 1;
            } else {
                return 0;
            }
        }else {
            if(position == 0){
                return 0;
            }else {
                return 1;
            }
        }

    }

    public void addUpTv(CouponBean.ResultBean resultBean) {
        this.mUpTv = resultBean;
        isbo = true;
        if(mBiao.equals("1")){
            notifyItemChanged(mData.size());
        }else {
            notifyItemChanged(1);
        }
    }

  /*  public void setData(int result1) {
        this.mTradeType = result1;
        Log.i("yx465", mTradeType+"setData: "+result1);
        notifyItemChanged(1);
    }*/

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv)
        NiceImageView mIv;
        @BindView(R.id.title_name)
        TextView mTitleName;
        @BindView(R.id.fu_hao)
        TextView mFuHao;
        @BindView(R.id.money)
        TextView mMoney;
        @BindView(R.id.guige)
        TextView mGuiGe;
        @BindView(R.id.sum)
        TextView mSum;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    class ViewHolder_di extends RecyclerView.ViewHolder{
        @BindView(R.id.money)
        TextView mMoney;
        @BindView(R.id.coupons)
        TextView mCoupons;
        @BindView(R.id.line_discount_coupon)
        LinearLayout mLineDiscountCoupon;

        ViewHolder_di(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface onClickLisiterCoupon{
        void onClickCoupon(View view,int position,TextView coupons);
    }

    public void setOnClickLisiterCoupon(onClickLisiterCoupon lisiterCoupon){
        this.mLisiterCoupon = lisiterCoupon;
    }
}
