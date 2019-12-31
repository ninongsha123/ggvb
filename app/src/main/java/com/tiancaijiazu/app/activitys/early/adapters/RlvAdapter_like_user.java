package com.tiancaijiazu.app.activitys.early.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.early.RecordCommentActivity;
import com.tiancaijiazu.app.beans.LikeListsBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2019/8/26/026.
 */

public class RlvAdapter_like_user extends RecyclerView.Adapter {
    private List<LikeListsBean.ResultBean> mData;
    private RecordCommentActivity mContext;
    private setOnItemClick listener;

    public RlvAdapter_like_user(List<LikeListsBean.ResultBean> resultBeans, RecordCommentActivity recordCommentActivity) {
        this.mData = resultBeans;
        this.mContext = recordCommentActivity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_like_user_layout, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        Glide.with(mContext).load(mData.get(i).getAvatar()).into(holder.mCirUser);
        holder.mCirUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    listener.setOnItemClickListener(v,i,mData);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if(mData.size()<3){
            return mData.size();
        }else {
            return 3;
        }
    }

    public void addData(List<LikeListsBean.ResultBean> result1) {
        if(mData!=null){
            mData.clear();
        }

        this.mData.addAll(result1);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.cir_user)
        CircleImageView mCirUser;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
    public interface setOnItemClick{
        void setOnItemClickListener(View v,int position,List<LikeListsBean.ResultBean> mData);
    }
    public void setOnItemClickListener(setOnItemClick listener){
        this.listener=listener;
    }
}
