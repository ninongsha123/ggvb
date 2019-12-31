package com.tiancaijiazu.app.activitys.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.fragments.beans.CollegeCourseBean;
import com.tiancaijiazu.app.utils.TimeUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/7/25/025.
 */

public class RlvAdapter_nei extends RecyclerView.Adapter {
    private int mIsBought;
    private List<CollegeCourseBean.ResultBean.ChapterListBean.ContentsListBean> mData;
    private Context mContext;
    private onClickLisiter mLisiter;
    private onClickLisiterTu mLisiterTu;

    public RlvAdapter_nei(List<CollegeCourseBean.ResultBean.ChapterListBean.ContentsListBean> contentsList, Context context, int isBought) {
        this.mData = contentsList;
        this.mContext = context;
        this.mIsBought = isBought;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_main_children, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        // 设置标题上的文本信息
        holder.mTitle.setText(mData.get(i).getTitle());
        // 设置副标题上的文本信息
        //秒数转化
        String time = TimeUtil.getTime(mData.get(i).getDuration());
        holder.mTimer.setText("时长 "+time);
        //判断课程是否免费
        if(mIsBought == 0){
            if (mData.get(i).getIsFree() == 1) {
                holder.mDarftIcon.setImageResource(R.mipmap.darft_true);
                if (mData.get(i).getType() == 1) {
                    holder.mFree.setText("免费试看");
                } else {
                    holder.mFree.setText("免费试听");
                }
                holder.mLock.setVisibility(View.GONE);
            } else {
                holder.mFree.setText("");
                holder.mLock.setVisibility(View.VISIBLE);
                holder.mDarftIcon.setImageResource(R.mipmap.darft_un);
            }
        }else {
            holder.mFree.setText("");
            holder.mLock.setVisibility(View.GONE);
        }

        //判断是音频还是视频
        if (mData.get(i).getType() == 1) {
            holder.mMusicIcon.setImageResource(R.mipmap.video_child);
        } else {
            holder.mMusicIcon.setImageResource(R.mipmap.music_child);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mLisiter!=null){
                    mLisiter.onClicker(view,i,mData);
                }
            }
        });
        holder.mDarftIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mLisiterTu!=null){
                    mLisiterTu.onClickerTu(view,i,mData);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.music_icon)
        ImageView mMusicIcon;
        @BindView(R.id.title)
        TextView mTitle;
        @BindView(R.id.lock)
        ImageView mLock;
        @BindView(R.id.timer)
        TextView mTimer;
        @BindView(R.id.free)
        TextView mFree;
        @BindView(R.id.darft_icon)
        ImageView mDarftIcon;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface onClickLisiter{
        void onClicker(View view,int position,List<CollegeCourseBean.ResultBean.ChapterListBean.ContentsListBean> mData);
    }

    public void setOnClickLisiter(onClickLisiter lisiter){
        this.mLisiter = lisiter;
    }

    public interface onClickLisiterTu{
        void onClickerTu(View view,int position,List<CollegeCourseBean.ResultBean.ChapterListBean.ContentsListBean> mData);
    }

    public void setOnClickLisiterTu(onClickLisiterTu lisiterTu){
        this.mLisiterTu = lisiterTu;
    }
}
