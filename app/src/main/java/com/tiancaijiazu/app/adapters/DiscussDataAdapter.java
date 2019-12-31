package com.tiancaijiazu.app.adapters;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.activitypage.ParentingEncyclopediaactivitys.UserCenterActivity;
import com.tiancaijiazu.app.activitys.adapters.RlvAdapter_comment_two;
import com.tiancaijiazu.app.activitys.adapters.Rlvadapter_discuss_two;
import com.tiancaijiazu.app.activitys.issue.GroupDeitlsActivity;
import com.tiancaijiazu.app.activitys.shopping_activity.WrapContentLinearLayoutManager;
import com.tiancaijiazu.app.beans.ArticleDatas;
import com.tiancaijiazu.app.beans.OneCommentBean;
import com.tiancaijiazu.app.beans.TwoCommentBean;
import com.tiancaijiazu.app.utils.PreUtils;
import com.tiancaijiazu.app.utils.TimeUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2019/5/11/011.
 */

public class DiscussDataAdapter extends RecyclerView.Adapter {


    private List<OneCommentBean.ResultBean> mData;
    private List<TwoCommentBean.ResultBean> mData1;
    private GroupDeitlsActivity mContext;
    private Rlvadapter_discuss_two mRlvadapter_discuss_two;


    public DiscussDataAdapter(GroupDeitlsActivity context, List<OneCommentBean.ResultBean> data) {
        this.mData = data;
//        this.mData1 = data;
        this.mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rec_comment_discuss, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.mZhuoZe.setVisibility(View.GONE);
        holder.mUserName.setText(mData.get(i).getUserNickname());
        Glide.with(mContext).load(mData.get(i).getUserAvatar()).into(holder.mCirCle);
        String publishTime = mData.get(i).getPublishTime();
        long l = TimeUtil.dataOne(publishTime);
        String s = TimeUtil.QQFormatTime(l);
        String textSource = "<font color='#333333'>" + mData.get(i).getContent() + "</font>";
        holder.mTvData.setText(Html.fromHtml(textSource));
        holder.mTime.setText(s);


//        List<OneCommentBean.ResultBean.SubDiscussBean> subDiscuss = mData.get(i).getSubDiscuss();
//        Log.i("yx==", "onBindViewHolder: " + subDiscuss.size());
//        if (subDiscuss.size() != 0) {
//            long discussId = mData.get(i).getDiscussId();
//            WrapContentLinearLayoutManager linearLayoutManager = new WrapContentLinearLayoutManager(mContext);
//            holder.mRlv.setLayoutManager(linearLayoutManager);
//
//            int replyCount = mData.get(i).getReplyCount();
//            mRlvadapter_discuss_two = new Rlvadapter_discuss_two(subDiscuss, mContext, replyCount);
////            mList.add(mRlvAdapterCommentTwo);
//            ((SimpleItemAnimator) holder.mRlv.getItemAnimator()).setSupportsChangeAnimations(false);
//            holder.mRlv.setAdapter(mRlvadapter_discuss_two);
//            mRlvadapter_discuss_two.setOnClickLisiterOne(new RlvAdapter_comment_two.onClickLisiter() {
//                @Override
//                public void onClicker(View view, int position, List<ArticleDatas.ResultBean.DiscussListBean.SubDiscussBean> mData) {
//                    if (mLisiter2 != null) {
//                        mLisiter2.onClickZan(view, position, mData);
//                    }
//                }
//            });

//            mRlvAdapterCommentTwo.setOnClickLisiterUser(new RlvAdapter_comment_two.onClickLisiterUser() {
//                @Override
//                public void onClickerUser(View view, int position, List<ArticleDatas.ResultBean.DiscussListBean.SubDiscussBean> mData) {
//                    if(mLisiterUserTwo!=null){
//                        mLisiterUserTwo.onClickerUserTwo(view,position,mData);
//                    }
//                }
//            });
//
//            mRlvAdapterCommentTwo.setOnClickLisiterTwoHui(new RlvAdapter_comment_two.onClickLisiterTwoHui() {
//                @Override
//                public void onClickTwoHui(View view, int position, List<ArticleDatas.ResultBean.DiscussListBean.SubDiscussBean> mData) {
//                    if (mLisiter3 != null) {
//                        mLisiter3.onClickerTwo(view, position, mData);
//                    }
//                }
//            });
//            mRlvAdapterCommentTwo.setOnClickLisiterTwoDi(new RlvAdapter_comment_two.onClickLisiterTwoDi() {
//                @Override
//                public void onClickTwoDi(View view, List<ArticleDatas.ResultBean.DiscussListBean.SubDiscussBean> mData, int page) {
//                    if (mLisiterTwoDi != null) {
//                        mLisiterTwoDi.onClickTwoDi(view, mData, page);
//                    }
//                }
//            });
//        }

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mLisiter != null) {
//                    mLisiter.onClicker(v, i, mData);
//                }
//            }
//        });

        holder.mCirCle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, UserCenterActivity.class);
                intent.putExtra("userId", mData.get(i).getUserId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void addData(List<OneCommentBean.ResultBean> beans) {
        this.mData = beans;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cir_cle)
        CircleImageView mCirCle;
        @BindView(R.id.user_name)
        TextView mUserName;
        @BindView(R.id.zhuo_ze)
        TextView mZhuoZe;
        @BindView(R.id.tv_data)
        TextView mTvData;
        @BindView(R.id.time)
        TextView mTime;
        @BindView(R.id.line)
        LinearLayout mLine;
//        @BindView(R.id.rlv)
//        RecyclerView mRlv;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
