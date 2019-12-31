package com.tiancaijiazu.app.activitys.shopping_activity.adapter;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.beans.ShangpinBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/8/15/015.
 */

public class RlvAdapter_goods_type extends RecyclerView.Adapter {
    private List<ShangpinBean.ResultBean.ItemListBean> mData;
    private Context mContext;
    private setClickLisiter mLisiter;

    public RlvAdapter_goods_type(List<ShangpinBean.ResultBean.ItemListBean> itemListBeans, Context context) {
        this.mData = itemListBeans;
        this.mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_goods_type, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.mYuanJia.setText("¥"+mData.get(i).getPrice());
        holder.mYuanJia.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        holder.mMoney.setText(mData.get(i).getPromoPrice()+"");
        holder.mTitle.setText(mData.get(i).getName());
        Glide.with(mContext).load(mData.get(i).getPicUri()).into(holder.mIv);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mLisiter!=null){
                    mLisiter.onClickComm(view,i,mData);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void addData(List<ShangpinBean.ResultBean.ItemListBean> itemList, boolean b) {
        if(b){
            if(mData!=null){
                mData.clear();
            }
        }

        this.mData.addAll(itemList);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv)
        ImageView mIv;
        @BindView(R.id.title)
        TextView mTitle;
        @BindView(R.id.fuhao)
        TextView mFuhao;
        @BindView(R.id.money)
        TextView mMoney;
        @BindView(R.id.yuan_jia)
        TextView mYuanJia;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
    public interface setClickLisiter {
        void onClickComm(View view, int position, List<ShangpinBean.ResultBean.ItemListBean> mData);
    }

    public void onSetClickLisiter(setClickLisiter lisiter) {
        this.mLisiter = lisiter;
    }
}
