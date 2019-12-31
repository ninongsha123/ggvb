package com.tiancaijiazu.app.activitys.early.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.utils.ScreenStatusUtil;
import com.tiancaijiazu.app.utils.ScreenUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/8/26/026.
 */

public class RlvAdapter_record_two extends RecyclerView.Adapter {
    private ArrayList<String> mImage;
    private  ArrayList<String> mImage1;
    private Context context;
    private onClickLisiterImage mLisiterImage;

    public RlvAdapter_record_two(ArrayList<String> strings,ArrayList<String> strings1) {
        this.mImage=strings;
        this.mImage1=strings1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_record_two_layout, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        ViewGroup.LayoutParams layoutParams = holder.mRounded.getLayoutParams();
        int screenWidth = ScreenUtil.getInstance(context).getScreenWidth();
        int i1 = ScreenStatusUtil.setDp(66, context);
        int w = (screenWidth-i1)/3;
        layoutParams.height = w;
        holder.mRounded.setLayoutParams(layoutParams);
        Glide.with(context).load(mImage.get(i)).into(holder.mRounded);
        holder.mRounded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLisiterImage!=null){
                    mLisiterImage.onClickerImage(v,i,mImage1);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mImage.size();
    }

    public void addData(ArrayList<String> coverPics, ArrayList<String> largePics) {
        if (mImage!=null){
            mImage.clear();
        }
        this.mImage.addAll(coverPics);
        if (mImage1!=null){
            mImage1.clear();
        }
        this.mImage1.addAll(largePics);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.rounded)
        RoundedImageView mRounded;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
    public interface onClickLisiterImage {
        void onClickerImage(View view, int position,ArrayList<String> mImage1);
    }

    public void setOnClickLisiterImage(onClickLisiterImage lisiterComment) {
        this.mLisiterImage = lisiterComment;
    }

}
