package com.tiancaijiazu.app.fragments.adapters;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.beans.HomeBBSbean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2019/4/30/030.
 */

public class RlvAdapter_home extends RecyclerView.Adapter {

    private List<HomeBBSbean.ResultBean> mData;
    private Context mContext;
    private onClickLisiter mLisiter;
    private onClickLisiterUser mLisiterUser;

    public RlvAdapter_home(List<HomeBBSbean.ResultBean> resultBeans, Context context) {
        this.mData = resultBeans;
        this.mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_community, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder holder1 = (ViewHolder) holder;
        holder1.mCommunityTitle.setText(mData.get(position).getTitle());
        holder1.mCommunityData.setText(mData.get(position).getSummary());
        Glide.with(mContext).load(mData.get(position).getUserAvatar()).into(holder1.mCirCle);
        holder1.mName.setText(mData.get(position).getNickname());
        String picList = mData.get(position).getPicList();
        holder1.mCirCle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mLisiterUser!=null){
                    mLisiterUser.onClickLisiterUser(view,position,mData);
                }
            }
        });
        if(picList.contains("|")){
            String[] split = picList.split("[|]");
             if(split.length==2){
                 Glide.with(mContext).load(split[0]).into(holder1.mIvOne);
                 Glide.with(mContext).load(split[1]).into(holder1.mIvTwo);
            }else if(split.length>2){
                 Glide.with(mContext).load(split[0]).into(holder1.mIvOne);
                 Glide.with(mContext).load(split[1]).into(holder1.mIvTwo);
                 Glide.with(mContext).load(split[2]).into(holder1.mIvThree);
            }
        }else {
            Glide.with(mContext).load(picList).into(holder1.mIvOne);
        }
        holder1.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mLisiter!=null){
                    mLisiter.onClicker(view,position,mData);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void addData(List<HomeBBSbean.ResultBean> result2) {
        if (mData != null) {
            mData.clear();
        }
        this.mData.addAll(result2);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.community_title)
        TextView mCommunityTitle;
        @BindView(R.id.community_data)
        TextView mCommunityData;
        @BindView(R.id.iv_one)
        ImageView mIvOne;
        @BindView(R.id.iv_two)
        ImageView mIvTwo;
        @BindView(R.id.iv_three)
        ImageView mIvThree;
        @BindView(R.id.cir_cle)
        CircleImageView mCirCle;
        @BindView(R.id.name)
        TextView mName;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface onClickLisiter{
        void onClicker(View view,int position,List<HomeBBSbean.ResultBean> mData);
    }

    public void setOnClickLisiter(onClickLisiter lisiter){
        this.mLisiter = lisiter;
    }

    public interface onClickLisiterUser{
        void onClickLisiterUser(View view,int position,List<HomeBBSbean.ResultBean> mData);
    }

    public void setOnClickLisiterUser(onClickLisiterUser lisiterUser){
        this.mLisiterUser = lisiterUser;
    }
}
