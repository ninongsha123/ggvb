package com.tiancaijiazu.app.activitys.shopping_activity.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.shopping_activity.CommentListActivity;
import com.tiancaijiazu.app.beans.CommentListBeans;
import com.tiancaijiazu.app.utils.TimeUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class RlvAdapter_comm_list extends RecyclerView.Adapter {
    private CommentListActivity mContext;
    private List<CommentListBeans.ResultBean.ItemListBean> mData;
    private setOnItemClick listener;
    private setOnItemClickCircle listenerCircle;

    public RlvAdapter_comm_list(CommentListActivity commentListActivity, List<CommentListBeans.ResultBean.ItemListBean> data) {
        this.mContext = commentListActivity;
        this.mData = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.shop_look_at_all, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.mUserNickname.setText(mData.get(i).getNickname());
        String commentTime = mData.get(i).getCommentTime();
        long l = TimeUtil.dataOne(commentTime);
        String s = TimeUtil.QQFormatTime(l);
        holder.mTvCommentTime.setText(s);
        holder.mTvSummary.setText(mData.get(i).getSummary());
        Glide.with(mContext).load(mData.get(i).getAvatar()).into(holder.mUsrAvatar);
        String pics = mData.get(i).getPics();
        String[] split = pics.split("[|]");
        ArrayList<String> strings = new ArrayList<>();
        if(!split[0].equals("")){
            for (int j = 0; j < split.length; j++) {
                strings.add(split[j]);
            }
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        holder.mRlvCommentlist.setLayoutManager(linearLayoutManager);
        RlvAdapter_commentlist rlvAdapter_commentlist = new RlvAdapter_commentlist(strings);
        holder.mRlvCommentlist.setAdapter(rlvAdapter_commentlist);
        rlvAdapter_commentlist.setOnItemLickListener(new RlvAdapter_commentlist.setOnItemClickListener() {
            @Override
            public void setOnItemClick(View v, int position, ArrayList<String> mDataImage) {
                if (listener!=null){
                    listener.setOnItemClickListener(v,position,strings);
                }
            }
        });
        holder.mUsrAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listenerCircle!=null){
                    listenerCircle.setOnItemClickListener(v,i,mData);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.d("yx", "getItemCsount: "+mData.size());
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.usr_avatar)
        CircleImageView mUsrAvatar;
        @BindView(R.id.user_nickname)
        TextView mUserNickname;
        @BindView(R.id.tv_commentTime)
        TextView mTvCommentTime;
        @BindView(R.id.tv_summary)
        TextView mTvSummary;
        @BindView(R.id.rlv_commentlist)
        RecyclerView mRlvCommentlist;
        @BindView(R.id.line2)
        LinearLayout mLine2;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
    public interface setOnItemClick{
        void setOnItemClickListener(View v,int position,ArrayList<String> mData);
    }
    public void setOItemClickListener(setOnItemClick listener){
        this.listener=listener;
    }
    public interface setOnItemClickCircle{
        void setOnItemClickListener(View v,int position,List<CommentListBeans.ResultBean.ItemListBean> mData);
    }
    public void setOItemClickListenerCircle(setOnItemClickCircle listener){
        this.listenerCircle=listener;
    }
}
