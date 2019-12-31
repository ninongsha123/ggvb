package com.tiancaijiazu.app.activitys.video.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.activitypage.collegeactivitys.VideoListActivity;
import com.tiancaijiazu.app.activitys.video.bean.ContentsBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/8/20/020.
 */

public class RlvAdapter_two_music_title extends RecyclerView.Adapter {
    private ArrayList<ContentsBean> mData;
    private VideoListActivity mContext;
    private onClickLisiter mLisiter;

    public RlvAdapter_two_music_title(ArrayList<ContentsBean> contentsList, VideoListActivity context) {
        this.mData = contentsList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_main_children_two, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.mTitle.setText(mData.get(i).getTitle());
        int isFree = mData.get(i).getIsFree();
        if(isFree == 0){
            holder.mLock.setVisibility(View.VISIBLE);
            holder.mFree.setVisibility(View.GONE);
        }else {
            holder.mLock.setVisibility(View.GONE);
            holder.mFree.setVisibility(View.VISIBLE);
            holder.mFree.setText("免费试听");
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isFree == 0){
                    Toast.makeText(mContext, "您还未购买该课程", Toast.LENGTH_SHORT).show();
                }else {
                    if(mLisiter!=null){
                        mLisiter.onClicker(view,i,mData);
                    }
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.blue)
        TextView mBlue;
        @BindView(R.id.title)
        TextView mTitle;
        @BindView(R.id.free)
        TextView mFree;
        @BindView(R.id.lock)
        ImageView mLock;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface onClickLisiter{
        void onClicker(View view, int position, ArrayList<ContentsBean> mData);
    }

    public void setOnClickLisiter(onClickLisiter lisiter){
        this.mLisiter = lisiter;
    }
}
