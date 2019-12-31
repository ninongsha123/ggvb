package com.tiancaijiazu.app.activitys.brand.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.brand.BrandIntroductionActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/8/5/005.
 */

public class RlvAdapter_brand_picture extends RecyclerView.Adapter {
    private String[] mData;
    private BrandIntroductionActivity mContext;
    private onClickLisiter mLisiter;

    public RlvAdapter_brand_picture(String[] split, BrandIntroductionActivity brandIntroductionActivity) {
        this.mData = split;
        this.mContext = brandIntroductionActivity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_brand_picture, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        Glide.with(mContext).load(mData[i]).into(holder.mIv);
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
        return mData.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv)
        ImageView mIv;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
    public interface onClickLisiter{
        void onClicker(View view,int position,String[] mData);
    }

    public void setOnClickLisiter(onClickLisiter lisiter){
        this.mLisiter = lisiter;
    }
}
