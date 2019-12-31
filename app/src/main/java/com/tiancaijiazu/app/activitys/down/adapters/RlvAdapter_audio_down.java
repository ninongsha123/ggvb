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
import com.tiancaijiazu.app.activitys.down.beans.AudioBean;
import com.tiancaijiazu.app.dao.DownAudioBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/8/22/022.
 */

public class RlvAdapter_audio_down extends RecyclerView.Adapter {
    public List<DownAudioBean> mDataDown;
    public ArrayList<AudioBean> mData;
    private Context mContext;
    private onClickLisiter mLisiter;
    private boolean mIsbo;
    private boolean mStatus;

    public RlvAdapter_audio_down(ArrayList<AudioBean> audioBeans, Context context, List<DownAudioBean> downAudioBeans) {
        this.mData = audioBeans;
        this.mContext = context;
        this.mDataDown = downAudioBeans;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_audio_down_layout, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.mAudioName.setText(mData.get(i).getName());
        holder.mAudioSize.setText(mData.get(i).getFileSize());
        Log.i("yx555", "onBindViewHolder: "+mData.get(i).getAbsolutePath());
        Glide.with(mContext).load(mDataDown.get(i).getSongCover()).into(holder.mRounded);
        holder.mAudioDuration.setText("时长 "+mData.get(i).getTime());
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
                    mLisiter.onClicker(view,i,mData,true,mDataDown);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void addData(List<AudioBean> audioBeans, List<DownAudioBean> downAudioBeans) {
        if(mData!=null){
            mData.clear();
        }
        if(mDataDown!=null){
            mDataDown.clear();
        }
        this.mDataDown.addAll(downAudioBeans);
        this.mData.addAll(audioBeans);
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
        @BindView(R.id.audio_name)
        TextView mAudioName;
        @BindView(R.id.audio_duration)
        TextView mAudioDuration;
        @BindView(R.id.audio_size)
        TextView mAudioSize;
        @BindView(R.id.checkbox)
        CheckBox mCheckbox;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface onClickLisiter{
        void onClicker(View view, int position, List<AudioBean> mData, boolean isbo,List<DownAudioBean> mDataDown);
    }

    public void setOnClickLisiter(onClickLisiter lisiter){
        this.mLisiter = lisiter;
    }
}
