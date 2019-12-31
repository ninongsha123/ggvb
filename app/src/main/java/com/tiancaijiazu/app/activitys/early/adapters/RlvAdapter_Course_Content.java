package com.tiancaijiazu.app.activitys.early.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.beans.FormalCurriculumBean;
import com.tiancaijiazu.app.beans.SubmitBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RlvAdapter_Course_Content extends RecyclerView.Adapter {

    private List<SubmitBean.ResultBean> mDataGame;
    private List<FormalCurriculumBean.ResultBean.ChapterListBean.ContentsListBean> mData;
    private Context mContext;
    private onClickLisiterCustom mLisiterCustom;
    private boolean isbo;
    private onClickLisiterCourse mLisiterCourse;
    private onClickLisiterGameOne mLisiterGameOne;
    private onClickLisiterGameTwo mLisiterGameTwo;

    public RlvAdapter_Course_Content(List<FormalCurriculumBean.ResultBean.ChapterListBean.ContentsListBean> contentsList, Context context, List<SubmitBean.ResultBean> resultBeans) {
        this.mData = contentsList;
        this.mContext = context;
        this.mDataGame = resultBeans;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == 0) {
            View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.trial_two_layout, viewGroup, false);
            return new ViewHolder(inflate);
        } else if (i == 1) {
            View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_end_couple_back_layout, viewGroup, false);
            return new ViewHolderDi(inflate);
        } else {
            View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_ke_seven_day_layout, viewGroup, false);
            return new ViewHolderDaySeven(inflate);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        int viewType = getItemViewType(i);
        if (viewType == 0) {
            ViewHolder holder = (ViewHolder) viewHolder;
            Glide.with(mContext).load(mData.get(i).getPicUri()).into(holder.mIvNice);
            holder.mData1.setText(mData.get(i).getTitle());
            String ability = mData.get(i).getAbility();
            String replace = ability.replace(",", "  ");
            holder.mData2.setText(replace);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mLisiterCourse != null) {
                        mLisiterCourse.onClickerCourse(view, i, mData);
                    }
                }
            });
        } else if (viewType == 1) {
            ViewHolderDi holderDi = (ViewHolderDi) viewHolder;
            if(mData.get(i).getGameList().size()!=0){
                List<FormalCurriculumBean.ResultBean.ChapterListBean.ContentsListBean.GameListBean> gameList = mData.get(i).getGameList();
                holderDi.mSubmitLine.setVisibility(View.GONE);
                holderDi.mCustomMade.setVisibility(View.VISIBLE);
                holderDi.mGameNameOne.setText(gameList.get(0).getTitle());
                holderDi.mGameNameTwo.setText(gameList.get(1).getTitle());
                Glide.with(mContext).load(gameList.get(0).getPicUri()).into(holderDi.mIvOne);
                Glide.with(mContext).load(gameList.get(1).getPicUri()).into(holderDi.mIvTwo);
            }else {
                if (isbo) {
                    holderDi.mSubmitLine.setVisibility(View.GONE);
                    holderDi.mCustomMade.setVisibility(View.VISIBLE);
                    holderDi.mGameNameOne.setText(mDataGame.get(0).getTitle());
                    holderDi.mGameNameTwo.setText(mDataGame.get(1).getTitle());
                    Glide.with(mContext).load(mDataGame.get(0).getPicUri()).into(holderDi.mIvOne);
                    Glide.with(mContext).load(mDataGame.get(1).getPicUri()).into(holderDi.mIvTwo);
                } else {
                    holderDi.mSubmitLine.setVisibility(View.VISIBLE);
                    holderDi.mCustomMade.setVisibility(View.GONE);
                    holderDi.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (mLisiterCustom != null) {
                                mLisiterCustom.onClickerCustom(view, i, mData);
                            }
                        }
                    });
                }
            }
            holderDi.mLineOne.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mLisiterGameOne!=null){
                        mLisiterGameOne.onClickerGameOne(view,i,mData,mDataGame);
                    }
                }
            });

            holderDi.mLineTwo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mLisiterGameTwo!=null){
                        mLisiterGameTwo.onClickerGameTwo(view,i,mData,mDataGame);
                    }
                }
            });

        } else {
            ViewHolderDaySeven holderDaySeven = (ViewHolderDaySeven) viewHolder;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mData.size() != 0) {
            if (mData.get(position).getIsGame() == 0) {
                return 0;
            } else {
                return 1;
            }
        } else {
            return 2;
        }
    }

    @Override
    public int getItemCount() {
        if (mData.size() != 0) {
            return mData.size();
        } else {
            return 1;
        }
    }

    public void addSubmit(int position, List<SubmitBean.ResultBean> result1) {
        isbo = true;
        if(mDataGame!=null){
            mDataGame.clear();
        }

        this.mDataGame.addAll(result1);
        notifyItemChanged(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.label)
        TextView mLabel;
        @BindView(R.id.iv_nice)
        RoundedImageView mIvNice;
        @BindView(R.id.rela)
        RelativeLayout mRela;
        @BindView(R.id.data1)
        TextView mData1;
        @BindView(R.id.data2)
        TextView mData2;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    class ViewHolderDi extends RecyclerView.ViewHolder {
        @BindView(R.id.submit)
        TextView mSubmit;
        @BindView(R.id.submit_line)
        LinearLayout mSubmitLine;
        @BindView(R.id.label)
        TextView mLabel;
        @BindView(R.id.iv_one)
        ImageView mIvOne;
        @BindView(R.id.game_name_one)
        TextView mGameNameOne;
        @BindView(R.id.iv_two)
        ImageView mIvTwo;
        @BindView(R.id.game_name_two)
        TextView mGameNameTwo;
        @BindView(R.id.custom_made)
        LinearLayout mCustomMade;
        @BindView(R.id.line_one)
        LinearLayout mLineOne;
        @BindView(R.id.line_two)
        LinearLayout mLineTwo;

        ViewHolderDi(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    class ViewHolderDaySeven extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_data)
        TextView mTvData;

        ViewHolderDaySeven(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface onClickLisiterCustom {
        void onClickerCustom(View view, int position, List<FormalCurriculumBean.ResultBean.ChapterListBean.ContentsListBean> mData);
    }

    public void setOnClickLisiterCustom(onClickLisiterCustom lisiterCustom) {
        this.mLisiterCustom = lisiterCustom;
    }

    public interface onClickLisiterCourse {
        void onClickerCourse(View view, int position, List<FormalCurriculumBean.ResultBean.ChapterListBean.ContentsListBean> mData);
    }

    public void setOnClickLisiterCourse(onClickLisiterCourse lisiterCourse) {
        this.mLisiterCourse = lisiterCourse;
    }

    public interface onClickLisiterGameOne{
        void onClickerGameOne(View view,int position,List<FormalCurriculumBean.ResultBean.ChapterListBean.ContentsListBean> mData,List<SubmitBean.ResultBean> mDataGame);
    }

    public void setOnClickLisiterGameOne(onClickLisiterGameOne lisiterGameOne){
        this.mLisiterGameOne = lisiterGameOne;
    }

    public interface onClickLisiterGameTwo{
        void onClickerGameTwo(View view,int position,List<FormalCurriculumBean.ResultBean.ChapterListBean.ContentsListBean> mData,List<SubmitBean.ResultBean> mDataGame);
    }

    public void setOnClickLisiterGameTwo(onClickLisiterGameTwo lisiterGameTwo){
        this.mLisiterGameTwo = lisiterGameTwo;
    }
}
