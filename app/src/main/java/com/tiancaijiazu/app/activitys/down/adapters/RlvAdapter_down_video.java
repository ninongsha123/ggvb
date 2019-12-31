package com.tiancaijiazu.app.activitys.down.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.down.beans.MediaBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/8/20/020.
 */

public class RlvAdapter_down_video extends RecyclerView.Adapter {
    public List<MediaBean> mData;
    private Context mContext;
    private onClickLisiter mLisiter;
    private boolean mIsbo;
    private boolean mStatus;

    public RlvAdapter_down_video(List<MediaBean> mediaBeans, Context context) {
        this.mData = mediaBeans;
        this.mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_down_video_layout, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.mVideoName.setText(mData.get(i).getName());
        holder.mVideoSize.setText(mData.get(i).getFileSize());
        Glide.with(mContext).load(mData.get(i).getAbsolutePath()).into(holder.mRounded);
        Log.i("yx555", "onBindViewHolder: "+mData.get(i).getAbsolutePath());
        if(mIsbo){
            holder.mCheckbox.setChecked(true);
            mData.get(i).setIsbo(true);
        }
        if(mStatus){
            holder.mCheckbox.setVisibility(View.VISIBLE);
        }else {
            holder.mCheckbox.setVisibility(View.GONE);
        }
        holder.mCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = holder.mCheckbox.isChecked();

                mData.get(i).setIsbo(checked);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mLisiter!=null){
                    mLisiter.onClicker(view,i,mData,true);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void addData(List<MediaBean> mediaBeans) {
        if(mData!=null){
            mData.clear();
        }
        this.mData.addAll(mediaBeans);
        this.mIsbo = false;
        this.mStatus = false;
        notifyDataSetChanged();
    }

    public void addSelect(boolean b) {
        this.mIsbo = b;
        notifyDataSetChanged();
    }

    public void addStatus(boolean b) {
        this.mStatus = b;
        this.mIsbo = false;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.rounded)
        RoundedImageView mRounded;
        @BindView(R.id.video_name)
        TextView mVideoName;
        @BindView(R.id.video_size)
        TextView mVideoSize;
        @BindView(R.id.checkbox)
        CheckBox mCheckbox;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface onClickLisiter{
        void onClicker(View view,int position,List<MediaBean> mData,boolean isbo);
    }

    public void setOnClickLisiter(onClickLisiter lisiter){
        this.mLisiter = lisiter;
    }
}
