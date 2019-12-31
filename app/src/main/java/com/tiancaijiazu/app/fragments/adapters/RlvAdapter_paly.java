package com.tiancaijiazu.app.fragments.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.beans.FormalCurriculumBean;
import com.tiancaijiazu.app.fragments.views.NiceImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/4/26/026.
 */

public class RlvAdapter_paly extends RecyclerView.Adapter {
    private Context mContext;
    private onClickLiniser mLisiter;
    private final int NORMAL = 0;
    private final int THELASTONE = 1;
    private int mI;
    private onClickLisiterVideo mLisiterVideo;
    public List<FormalCurriculumBean.ResultBean.ChapterListBean> mDataVideo;
    private onClickLisiterCourse mLisiterCourse;

    public RlvAdapter_paly(Context mContext, List<FormalCurriculumBean.ResultBean.ChapterListBean> chapterList1) {
        this.mDataVideo=chapterList1;
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == NORMAL) {
            View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_paly, parent, false);
            return new ViewHolder(inflate);
        } else {
            View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_paly_finally, parent, false);
            return new ViewHolder_finally(inflate);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        int viewType = getItemViewType(position);
        if (viewType == 0) {
            final ViewHolder holder1 = (ViewHolder) holder;
            if(mDataVideo.get(position).getContentsList().size()!=0){
                Glide.with(mContext).load(mDataVideo.get(position).getContentsList().get(0).getPicUri()).into(holder1.mIv);
            }
            if(mDataVideo.get(position).getContentsList().get(0).getIsFree() == 0){
                holder1.mIvPlay.setImageResource(R.mipmap.video_min_suo);
                holder1.mIv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(mLisiterCourse!=null){
                            mLisiterCourse.onClickerCourse(view,position);
                        }
                    }
                });
            }else {
                holder1.mIvPlay.setImageResource(R.mipmap.video_playing_min);
                holder1.mIv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mLisiter != null) {
                            mLisiter.onClick(v, position, mDataVideo,holder1.mIv);
                        }
                    }
                });
            }

            if(mI==position){
                holder1.mIv.setBorderColor(Color.parseColor("#00DEFF"));
                holder1.mIv.setBorderWidth(2);
                holder1.mIv.setMaskColor(Color.parseColor("#00000000"));
            }else {
                holder1.mIv.setBorderColor(Color.parseColor("#00000000"));
                holder1.mIv.setBorderWidth(0);
                holder1.mIv.setMaskColor(Color.parseColor("#7f000000"));
            }
        holder1.mName.setText(mDataVideo.get(position).getTitle());
        } else {
            ViewHolder_finally holderFinally = (ViewHolder_finally) holder;
            holderFinally.mLine.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mLisiterVideo!=null){
                        mLisiterVideo.onClickVideo(v,position);
                    }
                }
            });
        }
    }

    public void setSty(int position){
        this.mI = position;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mDataVideo.size();
    }

    public void setData(List<FormalCurriculumBean.ResultBean.ChapterListBean> chapterList1) {
        if (mDataVideo!=null){
            mDataVideo.clear();
        }
        mDataVideo.addAll(chapterList1);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv)
        NiceImageView mIv;
        @BindView(R.id.iv_play)
        ImageView mIvPlay;
        @BindView(R.id.name)
        TextView mName;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface onClickLiniser {
        void onClick(View view, int position, List<FormalCurriculumBean.ResultBean.ChapterListBean> mDataVideo,NiceImageView mIv);
    }

    public void setonClickLiniser(onClickLiniser liniser) {
        this.mLisiter = liniser;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == mDataVideo.size()-1) {
            return THELASTONE;
        } else {
            return NORMAL;
        }
    }

    class ViewHolder_finally extends RecyclerView.ViewHolder {
        @BindView(R.id.tv)
        TextView mTv;
        @BindView(R.id.line)
        LinearLayout mLine;

        ViewHolder_finally(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface onClickLisiterVideo{
        void onClickVideo(View view,int position);
    }

    public void setOnClickLisiterVideo(onClickLisiterVideo lisiterVideo){
        this.mLisiterVideo = lisiterVideo;
    }

    public interface onClickLisiterCourse{
        void onClickerCourse(View view,int position);
    }

    public void setOnClickLisiterCourse(onClickLisiterCourse lisiterCourse){
        this.mLisiterCourse = lisiterCourse;
    }
}
