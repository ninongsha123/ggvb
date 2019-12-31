package com.tiancaijiazu.app.fragments.adapters;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lzx.starrysky.manager.MusicManager;
import com.lzx.starrysky.model.SongInfo;
import com.lzx.starrysky.utils.TimerTaskManager;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.activitypage.homepageactivitys.KnowlegePlayActivity;
import com.tiancaijiazu.app.activitys.activitypage.homepageactivitys.ZaoAnActivity;
import com.tiancaijiazu.app.beans.SongBeanss;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class ZaoAdapter extends RecyclerView.Adapter {

    private List<SongBeanss.ResultBean> mData;
    private ZaoAnActivity mContext;


    ArrayList<SongInfo> mSongInfo;
    private SeekBar mSeek;
    private ImageView mStart;
    public static ImageView mCancle;
    private ImageView mHead;
    private TextView mTime;
    private TextView mTitle1;
    private Animation mAnimation;
    private ViewGroup mMView;
    private PopupWindow mPopupWindow;
    private int  flag_music=1;
    private MusicManager mInstance=MusicManager.getInstance();
    TimerTaskManager mtimerTask=new TimerTaskManager();



    public ZaoAdapter(List<SongBeanss.ResultBean> data, ZaoAnActivity context) {
        this.mData = data;
        this.mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_fen_zao, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        pop();
        ViewHolder holder = (ViewHolder) viewHolder;
        String publishTime = mData.get(i).getPublishTime();
        String year = publishTime.substring(0, 4);
        String month = publishTime.substring(5, 7);
        String date = publishTime.substring(8, 10);
        holder.mDate.setText("——  "+year+"年"+month+"月"+date+"日歌单 ——");
        holder.mTitles.setText("主题:"+mData.get(i).getTitle());
        List<SongBeanss.ResultBean.ItemListBean> mItemList = mData.get(i).getItemList();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        holder.mRlv.setLayoutManager(linearLayoutManager);
        ZaoAnAdapter adapter = new ZaoAnAdapter(mItemList);
        holder.mRlv.setAdapter(adapter);

        adapter.setOnClickLisiter(new ZaoAnAdapter.onClickLisiter() {
            @Override
            public void onClicker(View view, int position, List<SongBeanss.ResultBean.ItemListBean> data) {
                if (mPopupWindow.isShowing()){
                    mPopupWindow.dismiss();
                }
                if (mInstance.isPlaying()){
                    mInstance.stopMusic();
                    mtimerTask.stopToUpdateProgress();
                }
                mSongInfo = new ArrayList<>();
                for (int j = 0; j < mItemList.size(); j++) {
                    SongInfo s1 = new SongInfo();
                    s1.setSongId(mItemList.get(j).getItemId() + "");
                    s1.setSongUrl(mItemList.get(j).getMediaUri());
                    s1.setSongCover(mItemList.get(j).getPicUri());
                    s1.setSongName(mItemList.get(j).getTitle());
                    s1.setDownloadUrl(mItemList.get(j).getLyricUri());
                    mSongInfo.add(s1);
                }
                pop1(view);
                mTitle1.setText("当前播放:"+mData.get(i).getItemList().get(position).getTitle());
                showPopu(mSongInfo,position);
            }
        });
    }

    private void showPopu(ArrayList<SongInfo> songInfo, int position) {
        mtimerTask.startToUpdateProgress();
        mInstance.playMusic(songInfo, position);
        Glide.with(mContext).load(mData.get(position).getPicUri()).into(mHead);
        mMView.measure(0, 0);
        //在控件上方显示
        mPopupWindow.showAtLocation(mMView, Gravity.CENTER,0,650);

        mHead.startAnimation(mAnimation);

        //进度条滑动
        mSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mInstance.seekTo(seekBar.getProgress());
            }
        });

        //菜单
        //mMenu = mMView.findViewById(R.id.menu);
        RelativeLayout rela = mMView.findViewById(R.id.rela);
        mCancle.setVisibility(View.GONE);


        mTitle1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, KnowlegePlayActivity.class);
                SongInfo nowPlayingSongInfo = mInstance.getNowPlayingSongInfo();
                String songName = nowPlayingSongInfo.getSongName();
                intent.putExtra("songName", songName);
                intent.putExtra("biao", "home");
                mContext.startActivity(intent);
            }
        });
        rela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, KnowlegePlayActivity.class);
                SongInfo nowPlayingSongInfo = mInstance.getNowPlayingSongInfo();
                String songName = nowPlayingSongInfo.getSongName();
                intent.putExtra("songName", songName);
                intent.putExtra("biao", "home");
                mContext.startActivity(intent);
            }
        });

    }

    private void pop() {
        LayoutInflater mLayoutInflater = (LayoutInflater)mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
        mMView = (ViewGroup) mLayoutInflater.inflate(
                R.layout.test_popu, null);

        mPopupWindow = new PopupWindow(mMView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, false);
        mPopupWindow.setAnimationStyle(R.style.popwin_anim_style);

        //关闭popuwindow
        mCancle = mMView.findViewById(R.id.cancle);
        //头像
        mHead = mMView.findViewById(R.id.head);
        //音频文件的标题
        mTitle1 = mMView.findViewById(R.id.title);
        //final ImageView collect = mMView.findViewById(R.id.collect);//收藏
        //开始
        mStart = mMView.findViewById(R.id.start);
        //进度条
        mSeek = mMView.findViewById(R.id.seek);
        //时间
        mTime = mMView.findViewById(R.id.time);
        mAnimation = AnimationUtils.loadAnimation(mContext, R.anim.img_animation);
        LinearInterpolator lin = new LinearInterpolator();//设置动画匀速运动
        mAnimation.setInterpolator(lin);
    }

    private void pop1(View view) {
        mStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag_music == 1) {
                    mStart.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.music_start));
                    mCancle.setVisibility(View.VISIBLE);
                    mHead.clearAnimation();
                    flag_music = 2;
                    mInstance.pauseMusic();
                    mtimerTask.stopToUpdateProgress();
                } else if (flag_music == 2) {
                    mStart.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.music_pause));
                    mCancle.setVisibility(View.GONE);
                    flag_music = 1;
                    mHead.startAnimation(mAnimation);
                    mInstance.playMusic();
                    mtimerTask.startToUpdateProgress();
                }
            }
        });
        //进度更新
        mtimerTask.setUpdateProgressTask(() -> {
            long positions = MusicManager.getInstance().getPlayingPosition();
            long duration = MusicManager.getInstance().getDuration();
            long buffered = MusicManager.getInstance().getBufferedPosition();
            if (mSeek.getMax() != duration) {
                mSeek.setMax((int) duration);
            }
            mSeek.setProgress((int) positions);
            //mSeek.setSecondaryProgress((int) buffered);
            mTime.setText(formatMusicTime(duration - positions));
        });
        mCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindow.dismiss();
                mInstance.stopMusic();
            }
        });

        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                TextView viewById = view.findViewById(R.id.mname);
                viewById.setTextColor(Color.parseColor("#ff333333"));
            }
        });
    }

    public static String formatMusicTime(long duration) {
        String time = "";
        long minute = duration / 60000;
        long seconds = duration % 60000;
        long second = Math.round((int) seconds / 1000);
        if (minute < 10) {
            time += "0";
        }
        time += minute + ":";
        if (second < 10) {
            time += "0";
        }
        time += second;
        return time;
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.date)
        TextView mDate;
        @BindView(R.id.titles)
        TextView mTitles;
        @BindView(R.id.relas)
        RelativeLayout mRelas;
        @BindView(R.id.rlv)
        RecyclerView mRlv;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public void addData(List<SongBeanss.ResultBean> beans,boolean b) {
        if (b) {
            if (mData != null) {
                mData.clear();
            }
        }
        mData.addAll(beans);
        notifyDataSetChanged();
    }

}
