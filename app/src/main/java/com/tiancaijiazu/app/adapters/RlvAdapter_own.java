package com.tiancaijiazu.app.adapters;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class RlvAdapter_own extends RecyclerView.Adapter {

    private List<MallAdBean.ResultBean> mData;
    private Context mContext;
    private onClickLisiter mLisiter;

    public RlvAdapter_own(List<MallAdBean.ResultBean> resultBeans, Context ownBrandActivity) {
        this.mData = resultBeans;
        this.mContext = ownBrandActivity;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.own_item, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.mName.setText(mData.get(i).getTitle());
        holder.mMoney.setText(mData.get(i).getPromoPrice()+"");
        Glide.with(mContext).load(mData.get(i).getPicUri()).into(holder.mIvPic);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mLisiter!=null){
                    mLisiter.onClicker(view,i,mData);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void addData(List<MallAdBean.ResultBean> result) {
        if(mData!=null){
            mData.clear();
        }

        this.mData.addAll(result);
        notifyDataSetChanged();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_pic)
        ImageView mIvPic;
        @BindView(R.id.name)
        TextView mName;
        @BindView(R.id.fuhao)
        TextView mFuhao;
        @BindView(R.id.money)
        TextView mMoney;
        @BindView(R.id.login_gw)
        LinearLayout mLoginGw;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface onClickLisiter {
        void onClicker(View view, int position, List<MallAdBean.ResultBean> mData);
    }

    public void setOnClickLisiter(onClickLisiter lisiter) {
        this.mLisiter = lisiter;
    }
}
