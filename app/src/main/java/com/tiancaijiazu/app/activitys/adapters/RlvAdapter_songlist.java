package com.tiancaijiazu.app.activitys.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.beans.SongBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/8/13/013.
 */

public class RlvAdapter_songlist extends RecyclerView.Adapter {
    private List<SongBean.ResultBean.ItemListBean> mData;
    private Context mContext;
    private onClickLisiter mLisiter;
    private int position = 4;
    private boolean isbo = false;

    public RlvAdapter_songlist(List<SongBean.ResultBean.ItemListBean> resultBeans, Context context) {
        this.mData = resultBeans;
        this.mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_text_song_layout, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.mHearTextTwo.setText(mData.get(i).getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mLisiter!=null){
                    mLisiter.onClicker(view,i,mData);
                }
            }
        });
        if(i == position){
            holder.mVoiceTwo.setImageResource(R.mipmap.paly_voice);
            holder.mHearTextTwo.setTextColor(Color.parseColor("#00DEFF"));
        }else {
            holder.mVoiceTwo.setImageResource(R.mipmap.voice);
            holder.mHearTextTwo.setTextColor(Color.parseColor("#333333"));
        }
    }

    @Override
    public int getItemCount() {
        if(isbo){
            if(mData.size()>=3){
                return 3;
            }else {
                return mData.size();
            }
        }else {
            return 0;
        }
    }

    public void addData(List<SongBean.ResultBean.ItemListBean> result2) {
        isbo = true;
        if(mData!=null){
            mData.clear();
        }
        this.mData.addAll(result2);
        notifyDataSetChanged();
    }

    public void addPos(int position) {
        isbo = true;
        this.position = position;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.voice_two)
        ImageView mVoiceTwo;
        @BindView(R.id.hear_text_two)
        TextView mHearTextTwo;
        @BindView(R.id.second_line)
        LinearLayout mSecondLine;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface onClickLisiter{
        void onClicker(View view,int position,List<SongBean.ResultBean.ItemListBean> mData);
    }

    public void setOnClickLisiter(onClickLisiter lisiter){
        this.mLisiter = lisiter;
    }
}
