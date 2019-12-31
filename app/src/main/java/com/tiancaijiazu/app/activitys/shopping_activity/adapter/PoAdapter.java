package com.tiancaijiazu.app.activitys.shopping_activity.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.bean.CouponBean;
import com.tiancaijiazu.app.activitys.shopping_activity.POActivity;
import com.tiancaijiazu.app.beans.AtOnceBean;
import com.tiancaijiazu.app.beans.CourseBean;
import com.tiancaijiazu.app.beans.Shopping_carBean;
import com.tiancaijiazu.app.beans.SiteBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/5/18/018.
 */

public class PoAdapter extends RecyclerView.Adapter {

    public CourseBean mCourseBean;
    private AtOnceBean mDataAT;
    private String mBiao;
    public ArrayList<Shopping_carBean.ResultBean> mData;
    private POActivity mContext;
    private setOnClickLisiter mLisiter;
    public List<SiteBean.ResultBean> list;
    private onClickLisiterCoupon mLisiterCoupon;
    private int mTradeType;
    private RlvAdapter_op_data mRlvAdapterOpData;

    public PoAdapter(POActivity poActivity, List<SiteBean.ResultBean> result, ArrayList<Shopping_carBean.ResultBean> mBeans, String biao, AtOnceBean atOnceBean, CourseBean courseBean) {
        this.mContext = poActivity;
        this.list = result;
        this.mData = mBeans;
        this.mBiao = biao;
        this.mDataAT = atOnceBean;
        this.mCourseBean = courseBean;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == 0) {
            View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.op_item, viewGroup, false);
            return new ViewHolder_One(inflate);
        } else {
            View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.op_item_data, viewGroup, false);
            return new ViewHolder_two(inflate);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        int viewType = getItemViewType(i);
        if (viewType == 0) {
            ViewHolder_One holderOne = (ViewHolder_One) viewHolder;
            if (list.size() == 0) {
                holderOne.tishi.setVisibility(View.VISIBLE);
                holderOne.mAddress.setVisibility(View.GONE);
                holderOne.mTitleName.setVisibility(View.GONE);
                holderOne.mPhone.setVisibility(View.GONE);
                holderOne.mAddress.setText("");
                holderOne.mTitleName.setText("");
                holderOne.mPhone.setText("");
                holderOne.mLine.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mLisiter != null) {
                            mLisiter.onClickDiZhi(v, i);
                        }
                    }
                });
            } else {
                holderOne.mAddress.setText(list.get(0).getArea() + list.get(0).getAddress());
                holderOne.mTitleName.setText(list.get(0).getName());
                holderOne.mPhone.setText(list.get(0).getMobile());
                holderOne.tishi.setVisibility(View.GONE);
                holderOne.mAddress.setVisibility(View.VISIBLE);
                holderOne.mTitleName.setVisibility(View.VISIBLE);
                holderOne.mPhone.setVisibility(View.VISIBLE);
                holderOne.mLine.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mLisiter != null) {
                            mLisiter.onClickDiZhi(v, i);
                        }
                    }
                });
            }
        } else {
            ViewHolder_two holderTwo = (ViewHolder_two) viewHolder;
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
            holderTwo.mRecylerView.setLayoutManager(linearLayoutManager);
            CouponBean.ResultBean resultBean = new CouponBean.ResultBean();
            mRlvAdapterOpData = new RlvAdapter_op_data(mData,mContext,mBiao,mDataAT,mCourseBean,mTradeType,resultBean);
            holderTwo.mRecylerView.setAdapter(mRlvAdapterOpData);
            mRlvAdapterOpData.setOnClickLisiterCoupon(new RlvAdapter_op_data.onClickLisiterCoupon() {
                @Override
                public void onClickCoupon(View view, int position, TextView coupons) {
                    if(mLisiterCoupon!=null){
                        mLisiterCoupon.onClickerCoupon(view,position,coupons);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    public void addData(List<SiteBean.ResultBean> result) {
        if(list!=null){
            list.clear();
        }
        this.list.addAll(result);
        notifyDataSetChanged();
    }

    public void setData(int result1) {
        this.mTradeType=result1;
        notifyDataSetChanged();
       // mRlvAdapterOpData.setData(result1);
    }

    public void addUpTv(CouponBean.ResultBean resultBean) {
        mRlvAdapterOpData.addUpTv(resultBean);
    }

    class ViewHolder_One extends RecyclerView.ViewHolder {
        @BindView(R.id.address_ic)
        ImageView mAddressIc;
        @BindView(R.id.title_name)
        TextView mTitleName;
        @BindView(R.id.phone)
        TextView mPhone;
        @BindView(R.id.address)
        TextView mAddress;
        @BindView(R.id.line)
        LinearLayout mLine;
        @BindView(R.id.tishi)
        TextView tishi;

        ViewHolder_One(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    class ViewHolder_two extends RecyclerView.ViewHolder {
        @BindView(R.id.recylerView)
        RecyclerView mRecylerView;

        ViewHolder_two(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface setOnClickLisiter {
        void onClickDiZhi(View view, int position);
    }

    public void onSetClickLisiter(setOnClickLisiter lisiter) {
        this.mLisiter = lisiter;
    }

    public interface onClickLisiterCoupon{
        void onClickerCoupon(View view,int position,TextView coupons);
    }

    public void setOnClickLisiterCoupon(onClickLisiterCoupon lisiterCoupon){
        this.mLisiterCoupon = lisiterCoupon;
    }
}
