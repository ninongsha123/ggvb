package com.tiancaijiazu.app.activitys.adapters;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lzx.starrysky.model.SongInfo;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.activitypage.homepageactivitys.KnowlegePlayActivity;
import com.tiancaijiazu.app.utils.TimeUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class RlvAdapter_audio_list extends RecyclerView.Adapter {
    private List<SongInfo> mData;
    private KnowlegePlayActivity mContext;
    private int mPosition;
    private onClickLisiter mLisiter;

    public RlvAdapter_audio_list(List<SongInfo> songInfos, KnowlegePlayActivity knowlegePlayActivity) {
        this.mData = songInfos;
        this.mContext = knowlegePlayActivity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_song_title_layout, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.mName.setText(mData.get(i).getSongName());
        Glide.with(mContext).load(mData.get(i).getSongCover()).into(holder.mCirCle);
        if(i == mPosition){
            holder.mName.setTextColor(Color.parseColor("#00DEFF"));
        }else {
            holder.mName.setTextColor(Color.parseColor("#333333"));
        }

        int playCount = mData.get(i).getPlayCount();
        if(playCount == 0){
            String songUrl = mData.get(i).getSongUrl();
            String[] split = songUrl.split("\\?");
            if(split.length>1){
                int i1 = Integer.parseInt(split[1]);
                String time = TimeUtil.getTime(i1);
                holder.mTime.setText(time);
            }

        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mLisiter!=null){
                    mLisiter.onClicker(view,i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void addData(ArrayList<SongInfo> songInfos) {
        if(mData!=null){
            mData.clear();
        }

        this.mData.addAll(songInfos);
        notifyDataSetChanged();
    }

    public void addPos(int nowPlayingIndex) {
        this.mPosition = nowPlayingIndex;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.cir_cle)
        CircleImageView mCirCle;
        @BindView(R.id.name)
        TextView mName;
        @BindView(R.id.time)
        TextView mTime;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface onClickLisiter{
        void onClicker(View view,int position);
    }

    public void setOnClickLisiter(onClickLisiter lisiter){
        this.mLisiter = lisiter;
    }
}
