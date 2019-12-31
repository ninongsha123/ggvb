package com.tiancaijiazu.app.activitys.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.activitypage.ParentingEncyclopediaactivitys.RecDataActivity;
import com.tiancaijiazu.app.activitys.activitypage.ParentingEncyclopediaactivitys.UserCenterActivity;
import com.tiancaijiazu.app.activitys.issue.GroupDeitlsActivity;
import com.tiancaijiazu.app.beans.ArticleDatas;
import com.tiancaijiazu.app.beans.OneCommentBean;
import com.tiancaijiazu.app.beans.TwoCommentBean;
import com.tiancaijiazu.app.utils.TimeUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.annotations.NonNull;

public class Rlvadapter_discuss_two extends RecyclerView.Adapter{
    private int mReplyCount;
//    private boolean mIsboo;
    public List<OneCommentBean.ResultBean.SubDiscussBean> mData;
    private GroupDeitlsActivity mContext;
    private onClickLisiter mLisiter;
    private onClickLisiterTwoHui mLisiter1;
    private onClickLisiterTwoDi mLisiterDi;
    private int page = 1;
    private onClickLisiterUser mLisiterUser;

    public Rlvadapter_discuss_two(List<OneCommentBean.ResultBean.SubDiscussBean> discussListBeans, GroupDeitlsActivity context, int replyCount) {
        this.mData = discussListBeans;
        this.mContext = context;
        this.mReplyCount = replyCount;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == 0) {
            View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_comment_two, viewGroup, false);
            return new ViewHolder(inflate);
        } else {
            View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.two_item_comm_di, viewGroup, false);
            return new ViewHolder_di(inflate);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        int viewType = getItemViewType(i);
        if (viewType == 0) {
            ViewHolder holder = (ViewHolder) viewHolder;
            holder.mNameTwo.setText(mData.get(i).getUserNickname());
            Glide.with(mContext).load(mData.get(i).getUserAvatar()).into(holder.mIvTwo);
            String content = mData.get(i).getContent();
            String publishTime = mData.get(i).getPublishTime();
            long l = TimeUtil.dataOne(publishTime);
            String s = TimeUtil.QQFormatTime(l);
            if (mData.get(i).getReplyNickname().length() != 0) {
                String textSource =  "回复<font color='#00DEFF'>@" + mData.get(i).getReplyNickname() + "</font><font color='#333333'>：" + content+"</font>  <font color='#999999'><small>" + s + "</small></font>";
                //SpannableStringBuilder ssb = new SpannableStringBuilder("回复@" + mData.get(i).getReplyNickname() + "：" + content);
                //ssb.setSpan(new ForegroundColorSpan(Color.parseColor("#28DFE1")), 2, mData.get(i).getReplyNickname().length() + 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                holder.mDataTv.setText(Html.fromHtml(textSource));
            } else {
                String textSource1 = "<font color='#333333'>"+content+"</font>  <font color='#999999'><small>" + s + "</small></font>";
                holder.mDataTv.setText(Html.fromHtml(textSource1));
            }
            int likes = mData.get(i).getLikes();
            if (likes != 0) {
                holder.mLikeTwo.setText(likes + "");
            } else {
                holder.mLikeTwo.setText("");
            }
//            int isLikes = mData.get(i).getIsLikes();
//            if (isLikes == 0) {
//                holder.mCheckboxTwo.setChecked(false);
//            } else {
//                holder.mCheckboxTwo.setChecked(true);
//            }
//            holder.mCheckboxTwo.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (mLisiter != null) {
//                        mLisiter.onClicker(v, i, mData);
//                    }
//                }
//            });

            holder.mIvTwo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    if(mLisiterUser!=null){
//                        mLisiterUser.onClickerUser(view,i,mData);
//                    }
                    Intent intent = new Intent(mContext, UserCenterActivity.class);
                    intent.putExtra("userId", mData.get(i).getUserId());
                    mContext.startActivity(intent);
                }
            });

//            holder.mCheckboxTwo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    if(isChecked){
//                        mData.get(i).setLikes(mData.get(i).getLikes()+1);
//                        mData.get(i).setIsLikes(1);
//                        holder.mLikeTwo.setText(mData.get(i).getLikes()+ "");
//                    }else {
//                        mData.get(i).setLikes(mData.get(i).getLikes()-1);
//                        mData.get(i).setIsLikes(0);
//                        if (mData.get(i).getLikes() != 0) {
//                            holder.mLikeTwo.setText(mData.get(i).getLikes() + "");
//                        }else {
//                            holder.mLikeTwo.setText("");
//                        }
//                    }
//                }
//            });
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mLisiter1 != null) {
                        mLisiter1.onClickTwoHui(v, i, mData);
                    }
                }
            });

        } else {
            ViewHolder_di holderDi = (ViewHolder_di) viewHolder;
            int i1 = mReplyCount - mData.size();
            holderDi.mTwoDi.setText("展开"+i1+"条评论");
            holderDi.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    if(mLisiterDi!=null){
//                        mLisiterDi.onClickTwoDi(v,mData,page);
//                    }
                }
            });
        }

    }

    @Override
    public int getItemViewType(int position) {
//        if (mIsboo) {
            if (position == mData.size()) {
                return 1;
            } else {
                return 0;
            }
//        } else {
//            return 0;
//        }
    }

    @Override
    public int getItemCount() {
        if(mData.size()!=mReplyCount){
            return mData.size() + 1;
        }else {
            return mData.size();
        }
    }

    public void addTwoData(List<OneCommentBean.ResultBean> result1) {
//        if(mReplyCount>result1.size()){
//            mIsboo = true;
//        }else {
//            mIsboo = false;
//        }
        if(page == 1){
            if(mData!=null){
                mData.clear();
            }
        }

//        for (int i = 0; i < result1.size(); i++) {
//            mData.add(new OneCommentBean.ResultBean(result1.get(i).getDiscussId(),
//                    result1.get(i).getArticleId(),result1.get(i).getReplyId(),result1.get(i).getReplyCount(),
//                    result1.get(i).getUserId(),result1.get(i).getUserNickname(),result1.get(i).getUserAvatar(),
//                    result1.get(i).getContent(),result1.get(i).getPublishTime(),result1.get(i).getLikes(),
//                    result1.get(i).getIsLikes(),result1.get(i).getReplyUserId(),result1.get(i).getReplyNickname()));
//        }
        page++;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_two)
        CircleImageView mIvTwo;
        @BindView(R.id.name_two)
        TextView mNameTwo;
        @BindView(R.id.zhuo_ze)
        TextView mZhuoZe;
        @BindView(R.id.data_tv)
        TextView mDataTv;

        @BindView(R.id.checkbox_two)
        CheckBox mCheckboxTwo;
        @BindView(R.id.like_two)
        TextView mLikeTwo;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface onClickLisiterTwoDi{
        void onClickTwoDi(View view,List<OneCommentBean.ResultBean.SubDiscussBean> mData,int page);
    }

    public void setOnClickLisiterTwoDi(onClickLisiterTwoDi lisiterTwoDi){
        this.mLisiterDi = lisiterTwoDi;
    }

    public interface onClickLisiterTwoHui {
        void onClickTwoHui(View view, int position, List<OneCommentBean.ResultBean.SubDiscussBean> mData);
    }

    public void setOnClickLisiterTwoHui(onClickLisiterTwoHui lisiterTwoHui) {
        this.mLisiter1 = lisiterTwoHui;
    }

    public interface onClickLisiter {
        void onClicker(View view, int position, List<OneCommentBean.ResultBean.SubDiscussBean> mData);
    }

    public void setOnClickLisiterOne(onClickLisiter mOnClickLisiter) {
        this.mLisiter = mOnClickLisiter;
    }

    class ViewHolder_di extends RecyclerView.ViewHolder {
        @BindView(R.id.two_di)
        TextView mTwoDi;

        ViewHolder_di(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface onClickLisiterUser{
        void onClickerUser(View view,int position,List<OneCommentBean.ResultBean.SubDiscussBean> mData);
    }

    public void setOnClickLisiterUser(onClickLisiterUser lisiterUser) {
        this.mLisiterUser = lisiterUser;
    }
}
