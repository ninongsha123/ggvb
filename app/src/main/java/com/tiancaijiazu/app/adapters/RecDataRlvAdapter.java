package com.tiancaijiazu.app.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.activitypage.ParentingEncyclopediaactivitys.RecDataActivity;
import com.tiancaijiazu.app.activitys.adapters.RlvAdapter_comment_two;
import com.tiancaijiazu.app.activitys.shopping_activity.WrapContentLinearLayoutManager;
import com.tiancaijiazu.app.beans.ArticleDatas;
import com.tiancaijiazu.app.beans.OneCommentBean;
import com.tiancaijiazu.app.beans.TwoCommentBean;
import com.tiancaijiazu.app.utils.TimeUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2019/5/11/011.
 */

public class RecDataRlvAdapter extends RecyclerView.Adapter {
    private int mDiscuss;
    private boolean mIsbo;
    private long mUserId;
    private List<ArticleDatas.ResultBean.DiscussListBean> mData;
    private RecDataActivity mContext;
    private onClickLisiter mLisiter;
    private onClickLisiterOne mLisiter1;
    private onClickZan mLisiter2;
    private onClickLisiterTwo mLisiter3;
    private onClickLisiterDi mLisiterDi;
    private boolean isboo;
    private boolean isIsbo = true;
    private onClickLisiterTwoDi mLisiterTwoDi;
    private RlvAdapter_comment_two mRlvAdapterCommentTwo;
    private ArrayList<RlvAdapter_comment_two> mList = new ArrayList();
    private int page = 2;
    private List<OneCommentBean.ResultBean> mData2;
    private onClickLisiterUserOne mLisiterUserOne;
    private onClickLisiterUserTwo mLisiterUserTwo;

    public RecDataRlvAdapter(List<ArticleDatas.ResultBean.DiscussListBean> discussList, RecDataActivity recDataActivity, long userId, boolean isbo, int discuss) {
        this.mData = discussList;
        this.mContext = recDataActivity;
        this.mUserId = userId;
        this.mIsbo = isbo;
        this.mDiscuss = discuss;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == 0) {
            View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rec_comment_top, viewGroup, false);
            return new ViewHolder(inflate);
        } else {
            View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.one_item_comm_di, viewGroup, false);
            return new ViewHolder_di(inflate);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        int viewType = getItemViewType(i);
        if (viewType == 0) {
            ViewHolder holder = (ViewHolder) viewHolder;
            holder.mUserName.setText(mData.get(i).getUserNickname());
            Glide.with(mContext).load(mData.get(i).getUserAvatar()).into(holder.mCirCle);
            String publishTime = mData.get(i).getPublishTime();
            long l = TimeUtil.dataOne(publishTime);
            String s = TimeUtil.QQFormatTime(l);
            String textSource = "<font color='#333333'>"+mData.get(i).getContent() + "</font>  <font color='#999999'><small>" + s + "</small></font>";
            holder.mTvData.setText(Html.fromHtml(textSource));
            int likes = mData.get(i).getLikes();
            if (mData.get(i).getUserId() == mUserId) {
                holder.mZhuoZe.setVisibility(View.VISIBLE);
            } else {
                holder.mZhuoZe.setVisibility(View.GONE);
            }

            if (likes != 0) {
                holder.mLikeSum.setText(likes + "");
            } else {
                holder.mLikeSum.setText("");
            }

            int isLikes = mData.get(i).getIsLikes();
            if (isLikes == 0) {
                holder.mCheckbox.setChecked(false);
            } else {
                holder.mCheckbox.setChecked(true);
            }


            if (i == mData.size() - 1) {
                holder.mV.setVisibility(View.GONE);
            }

            holder.mCirCle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mLisiterUserOne!=null){
                        mLisiterUserOne.onClickerUserOne(view,i,mData);
                    }
                }
            });


            List<ArticleDatas.ResultBean.DiscussListBean.SubDiscussBean> subDiscuss = mData.get(i).getSubDiscuss();
            Log.i("yx==", "onBindViewHolder: " + subDiscuss.size());
            if (subDiscuss.size() != 0) {
                long discussId = mData.get(i).getDiscussId();
                holder.mV.setVisibility(View.GONE);
                holder.mVtwo.setVisibility(View.VISIBLE);
                WrapContentLinearLayoutManager linearLayoutManager = new WrapContentLinearLayoutManager(mContext);
                holder.mRecylerView.setLayoutManager(linearLayoutManager);

                int replyCount = mData.get(i).getReplyCount();
                if (replyCount > 1) {
                    isboo = true;
                } else {
                    isboo = false;
                }


                mRlvAdapterCommentTwo = new RlvAdapter_comment_two(subDiscuss, mContext, mUserId, discussId, isboo, replyCount);
                mList.add(mRlvAdapterCommentTwo);
                ((SimpleItemAnimator) holder.mRecylerView.getItemAnimator()).setSupportsChangeAnimations(false);
                holder.mRecylerView.setAdapter(mRlvAdapterCommentTwo);
                mRlvAdapterCommentTwo.setOnClickLisiterOne(new RlvAdapter_comment_two.onClickLisiter() {
                    @Override
                    public void onClicker(View view, int position, List<ArticleDatas.ResultBean.DiscussListBean.SubDiscussBean> mData) {
                        if (mLisiter2 != null) {
                            mLisiter2.onClickZan(view, position, mData);
                        }
                    }
                });

                mRlvAdapterCommentTwo.setOnClickLisiterUser(new RlvAdapter_comment_two.onClickLisiterUser() {
                    @Override
                    public void onClickerUser(View view, int position, List<ArticleDatas.ResultBean.DiscussListBean.SubDiscussBean> mData) {
                        if(mLisiterUserTwo!=null){
                            mLisiterUserTwo.onClickerUserTwo(view,position,mData);
                        }
                    }
                });

                mRlvAdapterCommentTwo.setOnClickLisiterTwoHui(new RlvAdapter_comment_two.onClickLisiterTwoHui() {
                    @Override
                    public void onClickTwoHui(View view, int position, List<ArticleDatas.ResultBean.DiscussListBean.SubDiscussBean> mData) {
                        if (mLisiter3 != null) {
                            mLisiter3.onClickerTwo(view, position, mData);
                        }
                    }
                });
                mRlvAdapterCommentTwo.setOnClickLisiterTwoDi(new RlvAdapter_comment_two.onClickLisiterTwoDi() {
                    @Override
                    public void onClickTwoDi(View view, List<ArticleDatas.ResultBean.DiscussListBean.SubDiscussBean> mData, int page) {
                        if (mLisiterTwoDi != null) {
                            mLisiterTwoDi.onClickTwoDi(view, mData, page);
                        }
                    }
                });
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mLisiter != null) {
                        mLisiter.onClicker(v, i, mData);
                    }
                }
            });

            holder.mCheckbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mLisiter1 != null) {
                        mLisiter1.onClickerOne(v, i, mData, holder.mCheckbox);
                    }
                }
            });
            holder.mCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        mData.get(i).setLikes(mData.get(i).getLikes()+1);
                        mData.get(i).setIsLikes(1);
                        holder.mLikeSum.setText(mData.get(i).getLikes()+ "");
                    }else {
                        mData.get(i).setLikes(mData.get(i).getLikes()-1);
                        mData.get(i).setIsLikes(0);
                        if (mData.get(i).getLikes() != 0) {
                            holder.mLikeSum.setText(mData.get(i).getLikes() + "");
                        }else {
                            holder.mLikeSum.setText("");
                        }
                    }
                }

            });

        } else {
            ViewHolder_di holderDi = (ViewHolder_di) viewHolder;
            int i1 = mDiscuss - mData.size();
            holderDi.mDiSum.setText("展开" + i1 + "条评论");
            holderDi.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mLisiterDi != null) {
                        Log.i("yx===", "onClick: "+page);
                        mLisiterDi.onClickDi(v, page);
                    }
                }
            });

        }

    }

    @Override
    public int getItemCount() {
        if (mIsbo) {
            return mData.size() + 1;
        } else {
            return mData.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mIsbo) {
            if (position == mData.size()) {
                return 1;
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    public void addTwoData(List<TwoCommentBean.ResultBean> result1) {
        for (int i = 0; i < mList.size(); i++) {
            if (mList.get(i).mData.get(0).getReplyId() == result1.get(0).getReplyId()) {
                mList.get(i).addTwoData(result1);
                break;
            }
        }
    }

    public void addOneData(List<OneCommentBean.ResultBean> result2, int discuss) {

        if(page==1){
            if (mData != null) {
                mData.clear();
            }
            if(discuss  > result2.size()){
                mIsbo = true;
            }else {
                mIsbo = false;
            }
        }else {
            if(discuss  > result2.size()+mData.size()){
                mIsbo = true;
            }else {
                mIsbo = false;
            }
        }

        for (int i = 0; i < result2.size(); i++) {
            List<OneCommentBean.ResultBean.SubDiscussBean> subDiscuss = result2.get(i).getSubDiscuss();
            if (subDiscuss.size() != 0) {
                List<ArticleDatas.ResultBean.DiscussListBean.SubDiscussBean> subDiscussBeans = new ArrayList<>();
                for (int j = 0; j < subDiscuss.size(); j++) {
                    subDiscussBeans.add(new ArticleDatas.ResultBean.DiscussListBean.SubDiscussBean(subDiscuss.get(j).getDiscussId(),
                            subDiscuss.get(j).getArticleId(), subDiscuss.get(j).getReplyId(), subDiscuss.get(j).getReplyCount(),
                            subDiscuss.get(j).getUserId(), subDiscuss.get(j).getUserNickname(), subDiscuss.get(j).getUserAvatar(),
                            subDiscuss.get(j).getContent(), subDiscuss.get(j).getPublishTime(), subDiscuss.get(j).getLikes(),
                            subDiscuss.get(j).getIsLikes(), subDiscuss.get(j).getReplyUserId(), subDiscuss.get(j).getReplyNickname()));
                }
                mData.add(new ArticleDatas.ResultBean.DiscussListBean(result2.get(i).getDiscussId(), result2.get(i).getArticleId(), result2.get(i).getReplyId(),
                        result2.get(i).getReplyCount(), result2.get(i).getUserId(), result2.get(i).getUserNickname(), result2.get(i).getUserAvatar(),
                        result2.get(i).getContent(), result2.get(i).getPublishTime(), result2.get(i).getLikes(), result2.get(i).getIsLikes(),
                        result2.get(i).getReplyUserId(), result2.get(i).getReplyNickname(), subDiscussBeans));
            } else {
                List<ArticleDatas.ResultBean.DiscussListBean.SubDiscussBean> subDiscussBeans = new ArrayList<>();
                mData.add(new ArticleDatas.ResultBean.DiscussListBean(result2.get(i).getDiscussId(), result2.get(i).getArticleId(), result2.get(i).getReplyId(),
                        result2.get(i).getReplyCount(), result2.get(i).getUserId(), result2.get(i).getUserNickname(), result2.get(i).getUserAvatar(),
                        result2.get(i).getContent(), result2.get(i).getPublishTime(), result2.get(i).getLikes(), result2.get(i).getIsLikes(),
                        result2.get(i).getReplyUserId(), result2.get(i).getReplyNickname(), subDiscussBeans));
            }

        }
        page++;
        mDiscuss = discuss;
        notifyItemRangeChanged(0, mData.size());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cir_cle)
        CircleImageView mCirCle;
        @BindView(R.id.user_name)
        TextView mUserName;
        @BindView(R.id.tv_data)
        TextView mTvData;

        @BindView(R.id.checkbox)
        CheckBox mCheckbox;
        @BindView(R.id.like_sum)
        TextView mLikeSum;
        @BindView(R.id.zhuo_ze)
        TextView mZhuoZe;
        @BindView(R.id.v)
        View mV;
        @BindView(R.id.v_two)
        View mVtwo;
        @BindView(R.id.recylerView)
        RecyclerView mRecylerView;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface onClickLisiterTwoDi {
        void onClickTwoDi(View view, List<ArticleDatas.ResultBean.DiscussListBean.SubDiscussBean> mData, int page);
    }

    public void setOnClickLisiterTwoDi(onClickLisiterTwoDi lisiterTwoDi) {
        this.mLisiterTwoDi = lisiterTwoDi;
    }

    public interface onClickLisiterDi {
        void onClickDi(View view, int page);
    }

    public void setOnClickLisiterDi(onClickLisiterDi lisiterDi) {
        this.mLisiterDi = lisiterDi;
    }

    public interface onClickLisiterTwo {
        void onClickerTwo(View view, int position, List<ArticleDatas.ResultBean.DiscussListBean.SubDiscussBean> mData);
    }

    public void setOnClickLisiterTwo(onClickLisiterTwo lisiterTwo) {
        this.mLisiter3 = lisiterTwo;
    }

    public interface onClickLisiter {
        void onClicker(View view, int position, List<ArticleDatas.ResultBean.DiscussListBean> mData);
    }

    public void setOnClickLisiter(onClickLisiter mOnClickLisiter) {
        this.mLisiter = mOnClickLisiter;
    }

    public interface onClickLisiterOne {
        void onClickerOne(View view, int position, List<ArticleDatas.ResultBean.DiscussListBean> mData, CheckBox mCheckbox);
    }

    public void setOnClickLisiterOne(onClickLisiterOne mOnClickLisiter) {
        this.mLisiter1 = mOnClickLisiter;
    }

    public interface onClickZan {
        void onClickZan(View view, int position, List<ArticleDatas.ResultBean.DiscussListBean.SubDiscussBean> mData);
    }

    public void setOnClickZan(onClickZan clickZan) {
        this.mLisiter2 = clickZan;
    }


    public interface onClickLisiterUserOne{
        void onClickerUserOne(View view,int position,List<ArticleDatas.ResultBean.DiscussListBean> mData);
    }

    public void setOnClickLisiterUserOne(onClickLisiterUserOne lisiterUserOne){
        this.mLisiterUserOne = lisiterUserOne;
    }

    public interface onClickLisiterUserTwo{
        void onClickerUserTwo(View view,int position,List<ArticleDatas.ResultBean.DiscussListBean.SubDiscussBean> mData);
    }

    public void setOnClickLisiterUserTwo(onClickLisiterUserTwo lisiterUserTwo){
        this.mLisiterUserTwo = lisiterUserTwo;
    }

    class ViewHolder_di extends RecyclerView.ViewHolder {
        @BindView(R.id.v)
        View mV;
        @BindView(R.id.di_sum)
        TextView mDiSum;

        ViewHolder_di(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
