package com.tiancaijiazu.app.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.beans.MallAdBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/5/16/016.
 */

public class RlvAdapter_shop extends RecyclerView.Adapter {
    private List<MallAdBean.ResultBean> mData;
    private Context context;
    private setOnItemClick listener;

    public RlvAdapter_shop(List<MallAdBean.ResultBean> mDataTen) {
        this.mData = mDataTen;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.shop_item, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.mYuanJia.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        Glide.with(context).load(mData.get(i).getPicUri()).into(holder.product_picture);
        holder.product_name.setText(mData.get(i).getTitle());
        holder.mMoney.setText(mData.get(i).getPromoPrice()+"");
        holder.mYuanJia.setText("¥"+mData.get(i).getPrice());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    listener.setOnItemClickListener(v,i,mData);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.fuhao)
        TextView mFuhao;
        @BindView(R.id.product_name)
        TextView product_name;
        @BindView(R.id.product_picture)
        ImageView product_picture;
        @BindView(R.id.money)
        TextView mMoney;
        @BindView(R.id.yuan_jia)
        TextView mYuanJia;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
    public interface setOnItemClick{
        void setOnItemClickListener(View view,int position,List<MallAdBean.ResultBean> mData);
    }
    public void setOnItemClickListener(setOnItemClick listener){
        this.listener=listener;
    }
}
