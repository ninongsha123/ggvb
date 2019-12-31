package com.tiancaijiazu.app.activitys.adapters;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.shopping_activity.ShopActivity;
import com.tiancaijiazu.app.beans.SpecificationBean;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.fragments.views.NiceImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/5/24/024.
 */

public class RlvAdapter_Color extends RecyclerView.Adapter {
    private List<SpecificationBean.ResultBean.SkuListBean> mData;
    private ShopActivity mContext;
    private int position ;
    public RlvAdapter_Color(List<SpecificationBean.ResultBean.SkuListBean> skuList, ShopActivity context, long skuId) {
        this.mData = skuList;
        this.mContext = context;
        for (int i = 0; i < skuList.size(); i++) {
            if(skuList.get(i).getSkuId()==skuId){
                position = i;
                break;
            }
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rlv_color_item, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        int i2 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0.5f, mContext.getResources().getDisplayMetrics());
        if(i==position){
            holder.mNiceIv.setBorderWidth(i2);
            holder.mNiceIv.setBorderColor(Color.parseColor("#FF0663"));
        }else {
            holder.mNiceIv.setBorderWidth(0);
            //holder.mNiceIv.setBorderColor(Color.parseColor("#FF0663"));
        }
        holder.mNiceIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = i;
                int a = i +1;
                long skuId = mData.get(i).getSkuId();
                mContext.mPresenter.getDataP(skuId+"", DifferentiateEnum.SPECIFICATIONOFGOODS);
                notifyDataSetChanged();
            }
        });
        Glide.with(mContext).load(mData.get(i).getPicUri()).into(holder.mNiceIv);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.nice_iv)
        NiceImageView mNiceIv;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
