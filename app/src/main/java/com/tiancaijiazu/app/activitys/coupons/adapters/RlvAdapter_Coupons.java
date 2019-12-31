package com.tiancaijiazu.app.activitys.coupons.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.bean.CouponBean;
import com.tiancaijiazu.app.activitys.coupons.CouponsActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/8/19/019.
 */

public class RlvAdapter_Coupons extends RecyclerView.Adapter {

    private CouponsActivity context;
    private List<CouponBean.ResultBean> mData;
    private onClickLisiterSelect mLisiterSelect;

    public RlvAdapter_Coupons(CouponsActivity context, List<CouponBean.ResultBean> da) {
        this.context = context;
        this.mData = da;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_coupons_layout, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        double couponFee = mData.get(i).getCouponFee();
        holder.coupnfee.setText(couponFee+"");
        holder.mName.setText(mData.get(i).getTitle());
        String kaishi = mData.get(i).getEffectiveIn().substring(0, 10);
        String jiehsu = mData.get(i).getExpiresIn().substring(0, 10);
        holder.da.setText(kaishi + "至" + jiehsu);
        holder.xianzhi.setText("满" + mData.get(i).getFeeMin() + "元可用");
        holder.tiaojian.setText(mData.get(i).getTradeSummary());
        if (mData.get(i).getStatus() == 1) {
            holder.mIv.setImageResource(R.mipmap.uses);
            holder.mIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mLisiterSelect!=null){
                        mLisiterSelect.onClickerSelect(view,i,mData);
                        context.setResult(220);
                        context.finish();
                    }
                }
            });
        } else if (mData.get(i).getStatus() == 2) {
            holder.mIv.setImageResource(R.mipmap.bytes_use);
            holder.mIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "此优惠券已使用!", Toast.LENGTH_SHORT).show();
                }
            });
        } else if (mData.get(i).getStatus() == 3) {
            holder.mIv.setImageResource(R.mipmap.lose_effi);
            holder.mIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "此优惠券已失效!", Toast.LENGTH_SHORT).show();
                }
            });
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mLisiterSelect!=null){
                    mLisiterSelect.onClickerSelect(view,i,mData);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void addData(List<CouponBean.ResultBean> result, boolean b) {
        if(b){
            if (mData != null) {
                mData.clear();
            }
        }
        mData.addAll(result);
        //notifyDataSetChanged();
        if(b){
            notifyDataSetChanged();
        }else {
            notifyItemRangeInserted(mData.size() - result.size(), result.size());
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name)
        TextView mName;
        @BindView(R.id.iv)
        ImageView mIv;
        @BindView(R.id.coupnfee)
        TextView coupnfee;
        @BindView(R.id.da)
        TextView da;
        @BindView(R.id.xianzhi)
        TextView xianzhi;
        @BindView(R.id.tiaojian)
        TextView tiaojian;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface onClickLisiterSelect{
        void onClickerSelect(View view,int position,List<CouponBean.ResultBean> mData);
    }

    public void setOnClickLisiterSelect(onClickLisiterSelect lisiterSelect){
        this.mLisiterSelect = lisiterSelect;
    }
}
