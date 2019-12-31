package com.tiancaijiazu.app.activitys.early.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.early.RecordCommentActivity;
import com.tiancaijiazu.app.beans.OneCommentBean;
import com.tiancaijiazu.app.utils.TimeUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2019/8/26/026.
 */

public class RlvAdapter_record_comment extends RecyclerView.Adapter {

    private boolean mIsbo;
    //    private  List<ArticleDatas.ResultBean.DiscussListBean> mData;
    private int mDiscuss;
    public List<OneCommentBean.ResultBean> mData;
    private RecordCommentActivity mContext;
    private onClickLisiterCirUser mLisiterCirUser;
    private int page = 2;
    private onClickLisiterItem mLisiterItem;
    private onClickLisiterDi mLisiterDi;
    private boolean mIsboo;


    public RlvAdapter_record_comment(List<OneCommentBean.ResultBean> resultBeans, RecordCommentActivity recordCommentActivity, int discuss) {
        this.mData = resultBeans;
        this.mContext = recordCommentActivity;
        this.mDiscuss = discuss;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == 0) {
            View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_record_comment_layout, viewGroup, false);
            return new ViewHolder(inflate);
        } else {
            View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.one_item_comm_di, viewGroup, false);
            return new ViewHolder_di(inflate);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        int itemViewType = getItemViewType(i);
        if (itemViewType == 0) {
            ViewHolder holder = (ViewHolder) viewHolder;
            Glide.with(mContext).load(mData.get(i).getUserAvatar()).into(holder.mCirUser);
            holder.mName.setText(mData.get(i).getUserNickname());
            holder.mTitle.setText(mData.get(i).getContent());
            String publishTime = mData.get(i).getPublishTime();
            long l = TimeUtil.dataOne(publishTime);
            String s = TimeUtil.QQFormatTime(l);
            holder.mTime.setText(s);
            holder.mCirUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mLisiterCirUser != null) {
                        mLisiterCirUser.onClickerUserOne(v, i, mData);
                    }
                }
            });
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mLisiterItem != null) {
                        mLisiterItem.onClickerItem(v, i, mData);
                    }
                }
            });

        } else {
            ViewHolder_di holderDi = (ViewHolder_di) viewHolder;

                if(mIsboo){
                    holderDi.mV.setVisibility(View.GONE);
                    int i1 = mDiscuss - mData.size();
                    if(i1 == 0){
                        holderDi.mDiSum.setVisibility(View.GONE);
                    }else {
                        holderDi.mDiSum.setText("展开" + i1 + "条评论");
                        holderDi.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (mLisiterDi != null) {
                                    mLisiterDi.onClickDi(v, page);
                                }
                            }
                        });
                    }

                }else {
                    holderDi.mV.setVisibility(View.GONE);
                    holderDi.mDiSum.setVisibility(View.GONE);
                }
        }

    }

    @Override
    public int getItemCount() {
        if(mIsboo){
            return mData.size()+1;
        }else {
            return mData.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == mData.size()) {
            return 1;
        } else {
            return 0;
        }
    }

    public void addData(List<OneCommentBean.ResultBean> result2, int discuss, boolean isbo, boolean b) {
        if(b){
            if (mData != null) {
                mData.clear();
            }
        }
        this.mIsboo =  isbo;
        this.mDiscuss  = discuss;
        mData.addAll(result2);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cir_user)
        CircleImageView mCirUser;
        @BindView(R.id.name)
        TextView mName;
        @BindView(R.id.title)
        TextView mTitle;
        @BindView(R.id.time)
        TextView mTime;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
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

    public interface onClickLisiterCirUser {
        void onClickerUserOne(View view, int position, List<OneCommentBean.ResultBean> mData);
    }

    public void setOnClickLisiterUserOne(onClickLisiterCirUser lisiterUserOne) {
        this.mLisiterCirUser = lisiterUserOne;
    }

    public interface onClickLisiterItem {
        void onClickerItem(View view, int position, List<OneCommentBean.ResultBean> mData);
    }

    public void setOnClickLisiterItem(onClickLisiterItem lisiterUserOne) {
        this.mLisiterItem = lisiterUserOne;
    }
    public interface onClickLisiterDi {
        void onClickDi(View view, int page);
    }

    public void setOnClickLisiterDi(onClickLisiterDi lisiterDi) {
        this.mLisiterDi = lisiterDi;
    }
}
