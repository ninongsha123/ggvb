package com.tiancaijiazu.app.activitys.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
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
import com.makeramen.roundedimageview.RoundedImageView;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.shopping_activity.adapter.RlvAdapter_commimg;
import com.tiancaijiazu.app.beans.NonPaymentListBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import www.linwg.org.lib.LCardView;

/**
 * Created by Administrator on 2019/5/21/021.
 */

public class RlvAdapter_nonPay extends RecyclerView.Adapter {

    public List<NonPaymentListBean.ResultBean> mData;
    private Context mContext;
    private onClickLisiterJin mLisiterJin;
    private onClickLisiterFuKuan mLisiterFuKuan;
    private onClickLisiterCannel mLisiterCannel;

    public RlvAdapter_nonPay(List<NonPaymentListBean.ResultBean> resultBeans, Context context) {
        this.mData = resultBeans;
        this.mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == 1) {
            View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_unpaied, viewGroup, false);
            return new ViewHolder(inflate);
        } else {
            View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_alone_alipay_layout, viewGroup, false);
            return new ViewHolderAlone(inflate);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        int viewType = getItemViewType(i);
        if(viewType == 1){
            ViewHolder holder = (ViewHolder) viewHolder;
            if (i == 0) {
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) holder.mLcard.getLayoutParams();
                int top = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, mContext.getResources().getDisplayMetrics());
                layoutParams.topMargin = top;
                holder.mLcard.setLayoutParams(layoutParams);
            }
            holder.mTvId.setText("订单编号：" + mData.get(i).getOrderId());
            holder.mMoney.setText("¥" + mData.get(i).getTotalFee());
            holder.mMoney.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
            holder.mSum.setText("共" + mData.get(i).getItemUnits() + "件");
            String picList = mData.get(i).getPicList();
            Log.i("yx456", "onBindViewHolder: -----" + picList);
            String[] split = picList.split("[|]");
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
            holder.mRecylerView.setLayoutManager(linearLayoutManager);
            RlvAdapter_commimg rlvAdapterCommimg = new RlvAdapter_commimg(split, mContext);
            holder.mRecylerView.setAdapter(rlvAdapterCommimg);
            holder.mFuKuan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mLisiterFuKuan != null) {
                        mLisiterFuKuan.onClickerFuKuan(v, i, mData);
                    }
                }
            });
            holder.mLcard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mLisiterJin != null) {
                        mLisiterJin.onClickerJin(v, i, mData);
                    }
                }
            });
            holder.cancel_order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mLisiterCannel != null) {
                        mLisiterCannel.onClickerCannel(v, i, mData);
                    }
                }
            });
            holder.mLine.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mLisiterJin != null) {
                        mLisiterJin.onClickerJin(v, i, mData);
                    }
                }
            });
            rlvAdapterCommimg.setOnClickLisiter(new RlvAdapter_commimg.onClickLisiter() {
                @Override
                public void onClicker(View view) {
                    holder.mLcard.performClick();
                }
            });
        }else {
            ViewHolderAlone holderAlone = (ViewHolderAlone) viewHolder;
            if (i == 0) {
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) holderAlone.mLcard.getLayoutParams();
                int top = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, mContext.getResources().getDisplayMetrics());
                layoutParams.topMargin = top;
                holderAlone.mLcard.setLayoutParams(layoutParams);
            }
            holderAlone.mTvId.setText("订单编号：" + mData.get(i).getOrderId());
            holderAlone.mMoney.setText("¥" + mData.get(i).getTotalFee());
            holderAlone.mMoney.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
            holderAlone.mSum.setText("共" + mData.get(i).getItemUnits() + "件商品，合计：");
            String productName = mData.get(i).getProductName();
            String[] split = productName.split("[|]");
            holderAlone.mTitle.setText(split[0]);
            holderAlone.mChicun.setText(split[1]);
            Glide.with(mContext).load(mData.get(i).getPicList()).into(holderAlone.mRounded);
            holderAlone.mGotoEvaluate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mLisiterFuKuan != null) {
                        mLisiterFuKuan.onClickerFuKuan(v, i, mData);
                    }
                }
            });
            holderAlone.mLcard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mLisiterJin != null) {
                        mLisiterJin.onClickerJin(v, i, mData);
                    }
                }
            });
            holderAlone.mCancelOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mLisiterCannel != null) {
                        mLisiterCannel.onClickerCannel(v, i, mData);
                    }
                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        String picList = mData.get(position).getPicList();
        String[] split = picList.split("[|]");
        if (split.length == 1) {
            return 2;
        } else {
            return 1;
        }
    }

    public void addData(List<NonPaymentListBean.ResultBean> nonPaymentListBean, boolean b) {
        if (b) {
            if (mData != null) {
                mData.clear();
            }
        }
        this.mData.addAll(nonPaymentListBean);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_id)
        TextView mTvId;
        @BindView(R.id.cancel_order)
        TextView cancel_order;
        @BindView(R.id.recylerView)
        RecyclerView mRecylerView;
        @BindView(R.id.money)
        TextView mMoney;
        @BindView(R.id.sum)
        TextView mSum;
        @BindView(R.id.fu_kuan)
        TextView mFuKuan;
        @BindView(R.id.lcard)
        LCardView mLcard;
        @BindView(R.id.line)
        LinearLayout mLine;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface onClickLisiterJin {
        void onClickerJin(View view, int position, List<NonPaymentListBean.ResultBean> mData);
    }

    public void setOnClickLisiterJin(onClickLisiterJin lisiterJin) {
        this.mLisiterJin = lisiterJin;
    }

    public interface onClickLisiterFuKuan {
        void onClickerFuKuan(View view, int position, List<NonPaymentListBean.ResultBean> mData);
    }

    public void onClickLisiterFuKuan(onClickLisiterFuKuan lisiterFuKuan) {
        this.mLisiterFuKuan = lisiterFuKuan;
    }

    public interface onClickLisiterCannel {
        void onClickerCannel(View view, int position, List<NonPaymentListBean.ResultBean> mData);
    }

    public void onClickLisiterCannel(onClickLisiterCannel lisiterCannel) {
        this.mLisiterCannel = lisiterCannel;
    }

    class ViewHolderAlone extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_id)
        TextView mTvId;
        @BindView(R.id.rounded)
        RoundedImageView mRounded;
        @BindView(R.id.title)
        TextView mTitle;
        @BindView(R.id.chicun)
        TextView mChicun;
        @BindView(R.id.sum)
        TextView mSum;
        @BindView(R.id.money)
        TextView mMoney;
        @BindView(R.id.goto_evaluate)
        TextView mGotoEvaluate;
        @BindView(R.id.cancel_order)
        TextView mCancelOrder;
        @BindView(R.id.lcard)
        LCardView mLcard;

        ViewHolderAlone(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
