package com.tiancaijiazu.app.activitys.video.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.activitypage.collegeactivitys.VideoListActivity;
import com.tiancaijiazu.app.activitys.video.bean.ContentsBean;
import com.tiancaijiazu.app.activitys.video.bean.VideoTwoExtractBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/8/20/020.
 */

public class RlvAdapter_music_two_list extends RecyclerView.Adapter {
    private ArrayList<VideoTwoExtractBean> mData;
    private VideoListActivity mContext;
    private onClickLisiter mLisiter;

    public RlvAdapter_music_two_list(ArrayList<VideoTwoExtractBean> result, VideoListActivity videoActivity) {
            this.mData = result;
            this.mContext = videoActivity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_main_father, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.mAlarmClockFatherTv.setText(mData.get(i).getTitle());
        ArrayList<ContentsBean> contentsList = mData.get(i).getContentsList();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        holder.mRecyler.setLayoutManager(linearLayoutManager);
        RlvAdapter_two_music_title rlvAdapterTwoVideoTitle = new RlvAdapter_two_music_title(contentsList,mContext);
        holder.mRecyler.setAdapter(rlvAdapterTwoVideoTitle);
        rlvAdapterTwoVideoTitle.setOnClickLisiter(new RlvAdapter_two_music_title.onClickLisiter() {
            @Override
            public void onClicker(View view, int position, ArrayList<ContentsBean> mData) {
                if(mLisiter!=null){
                    mLisiter.onClicker(view,position,mData);
                }
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = holder.mGroupState.isChecked();
                if(checked){
                    holder.mRecyler.setVisibility(View.VISIBLE);
                    holder.mGroupState.setChecked(false);

                }else {
                    holder.mRecyler.setVisibility(View.GONE);
                    holder.mGroupState.setChecked(true);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.alarm_clock_father_tv)
        TextView mAlarmClockFatherTv;
        @BindView(R.id.group_state)
        CheckBox mGroupState;
        @BindView(R.id.recyler)
        RecyclerView mRecyler;

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
