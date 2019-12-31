package com.tiancaijiazu.app.activitys.video.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.video.VideoActivity;
import com.tiancaijiazu.app.beans.VideoExtractBean;
import com.tiancaijiazu.app.utils.PreUtils;
import com.tiancaijiazu.app.utils.TimeUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/6/12/012.
 */

public class RlvAdapter_video extends RecyclerView.Adapter {

    private int mIsBought;
    private VideoActivity mContext;
    public ArrayList<VideoExtractBean> mData;
    private onClickLisiterState mLisiterState;
    private int mPosition;
    private String mMediaUri;

    public RlvAdapter_video(ArrayList<VideoExtractBean> videoExtractBeans, VideoActivity videoActivity, int isBought) {
        this.mData = videoExtractBeans;
        this.mContext = videoActivity;
        this.mIsBought = isBought;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.video_buttom_list, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        if (mData.get(i).getMediaUri().equals(mMediaUri)) {
            holder.mRela.setBackgroundResource(R.drawable.shape_video_list_yes);
            holder.mMinPlay.setVisibility(View.VISIBLE);
            holder.mTvState.setText("正在播放");
            PreUtils.putInt("videoI",i);
        } else {
            holder.mRela.setBackgroundResource(R.drawable.shape_video_list_no);
            holder.mMinPlay.setVisibility(View.GONE);
            String time = TimeUtil.getTime(mData.get(i).getDuration());
            holder.mTvState.setText(time);
        }

        holder.mTitleVideo.setText(mData.get(i).getTitle());
        int isFree = mData.get(i).getIsFree();
        if(mIsBought == 0){
            if(isFree == 0){
                holder.mMianFei.setVisibility(View.GONE);
                holder.mSuo.setVisibility(View.VISIBLE);
            }else {
                holder.mMianFei.setVisibility(View.VISIBLE);
                holder.mSuo.setVisibility(View.GONE);
            }
        }else {
            holder.mMianFei.setVisibility(View.GONE);
            holder.mSuo.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFree == 0){
                    Toast.makeText(mContext, "您还未购买该课程", Toast.LENGTH_SHORT).show();
                }else {
                    if (mLisiterState != null) {
                        mLisiterState.onClickerState(v, i,mData);
                    }
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void addState(int position) {
        this.mPosition = position;
        notifyDataSetChanged();
    }

    public void addData(ArrayList<VideoExtractBean> videoExtractBeans) {

    }

    public void addUrl(String mediaUri) {
        this.mMediaUri = mediaUri;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title_video)
        TextView mTitleVideo;
        @BindView(R.id.tv_state)
        TextView mTvState;
        @BindView(R.id.min_play)
        ImageView mMinPlay;
        @BindView(R.id.mianfei)
        ImageView mMianFei;
        @BindView(R.id.suo)
        ImageView mSuo;
        @BindView(R.id.rela)
        RelativeLayout mRela;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface onClickLisiterState {
        void onClickerState(View view, int position,ArrayList<VideoExtractBean> mData);
    }

    public void setOnClickLisiterState(onClickLisiterState lisiterState) {
        this.mLisiterState = lisiterState;
    }
}
