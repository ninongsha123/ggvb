package com.tiancaijiazu.app.activitys.activitypage.homepageactivitys;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.base.activity.BaseActivity;
import com.tiancaijiazu.app.beans.MusicBean;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.fragments.adapters.WanAnAdapter;
import com.tiancaijiazu.app.mvp.IView;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.status.LoadingLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WanAnActivity extends BaseActivity<IView, Presenter<IView>> implements IView {
    @BindView(R.id.tuis)
    ImageView mTuis;
    @BindView(R.id.fan)
    ImageView mFan;
    @BindView(R.id.bo)
    TextView mBo;
    @BindView(R.id.begins)
    TextView mBegins;
    @BindView(R.id.myRecyclerview)
    RecyclerView mMyRecyclerview;
    @BindView(R.id.line2)
    View mLine2;
    @BindView(R.id.loadingLayout)
    LoadingLayout mLoadingLayout;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    private ViewGroup mMView;
    private SeekBar mSeek;
    private ImageView mStart;
    public static ImageView mCancle;
    private ImageView mHead;
    private TextView mTime;
    private TextView mTitle1;
    private Animation mAnimation;
    TimerTaskManager mtimerTask;
    private int flag_music = 1;
    private WanAnAdapter mWanAnAdapter;
    private MusicManager mInstance;
    private ArrayList<SongInfo> mSongInfos = new ArrayList<>();
    private PopupWindow mPopupWindow;
    private List<MusicBean.ResultBean> mItemListBeans;
    private int page = 1;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:         //刷新加载
                    if (mRefreshLayout != null) {
                        mRefreshLayout.finishRefresh(true);
                        page = 1;
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("catalogId","36673932866228224");
                        map.put("page", page);
                        mPresenter.getDataP1(map, DifferentiateEnum.SONGMUSIC, mLoadingLayout);
                    }
                    break;
                case 2:         //加载更多
                    if (mRefreshLayout != null) {
                        mRefreshLayout.finishLoadMore(true);
                        page++;
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("catalogId","36673932866228224");
                        map.put("page", page);
                        mPresenter.getDataP1(map, DifferentiateEnum.SONGMUSIC, mLoadingLayout);
                    }
                    break;
            }
            return false;
        }
    });

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }

    @Override
    protected void initEventAndData() {
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        if (name!=null) {
            mBegins.setText("让缓和的音乐伴随" + name + "进入梦乡");
        }
        mInstance = MusicManager.getInstance();
        mtimerTask = new TimerTaskManager();
        pop();
        HashMap<String, Object> map = new HashMap<>();
        map.put("catalogId", "36673932866228224");
        map.put("page", page);
        mPresenter.getDataP1(map, DifferentiateEnum.SONGMUSIC,mLoadingLayout);
        mItemListBeans = new ArrayList<>();
        mWanAnAdapter = new WanAnAdapter(mItemListBeans, this);
        mMyRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mMyRecyclerview.setAdapter(mWanAnAdapter);

        mLoadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
            @Override
            public void onReload(View v) {
                mPresenter.getDataP1(map, DifferentiateEnum.SONGMUSIC, mLoadingLayout);
            }
        });


        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                //List<String> data = initDatas();
                Message message = new Message();
                message.what = 1;
                //message.obj = data ;
                mHandler.sendMessageDelayed(message, 1500);
            }
        });

        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                Message message = new Message();
                message.what = 2;
                mHandler.sendMessageDelayed(message, 1500);
            }
        });


        mFan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mInstance.isPlaying()) {
                    mInstance.stopMusic();
                    mPopupWindow.dismiss();
                    mtimerTask.stopToUpdateProgress();
                }
                finish();
            }
        });
        mWanAnAdapter.setOnClickLisiter(new WanAnAdapter.onClickLisiter() {
            @Override
            public void onClicker(View view, int position, List<MusicBean.ResultBean> mData) {
                if (mPopupWindow.isShowing()) {
                    mPopupWindow.dismiss();
                }
                if (mInstance.isPlaying()) {
                    mInstance.stopMusic();
                    mtimerTask.stopToUpdateProgress();
                }

                mBegins.setVisibility(View.GONE);
                mBo.setVisibility(View.VISIBLE);
                mBo.setText("正在播放:" + mData.get(position).getTitle());
                showPopu(mSongInfos, position,mData);
            }
        });
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_wan_an;
    }

    private void pop() {
        LayoutInflater mLayoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
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
        mAnimation = AnimationUtils.loadAnimation(this, R.anim.img_animation);
        LinearInterpolator lin = new LinearInterpolator();//设置动画匀速运动
        mAnimation.setInterpolator(lin);
        mStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag_music == 1) {
                    mStart.setImageDrawable(getResources().getDrawable(R.mipmap.music_start));
                    mCancle.setVisibility(View.VISIBLE);
                    mHead.clearAnimation();
                    flag_music = 2;
                    mInstance.pauseMusic();
                    mtimerTask.stopToUpdateProgress();
                } else if (flag_music == 2) {
                    mStart.setImageDrawable(getResources().getDrawable(R.mipmap.music_pause));
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
                mBo.setVisibility(View.GONE);
                mBegins.setVisibility(View.VISIBLE);
            }
        });
    }

    private void showPopu(List<SongInfo> songInfos, int pos, List<MusicBean.ResultBean> mData) {
        mtimerTask.startToUpdateProgress();
        mInstance.playMusic(songInfos, pos);
        Glide.with(this).load(mData.get(pos).getPicUri()).into(mHead);
        mMView.measure(0, 0);
        //在控件上方显示
        mPopupWindow.showAtLocation(mMyRecyclerview, Gravity.CENTER, 0, 650);

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

        mTitle1.setText("当前播放:" + mData.get(pos).getTitle());
        mTitle1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WanAnActivity.this, KnowlegePlayActivity.class);
                SongInfo nowPlayingSongInfo = mInstance.getNowPlayingSongInfo();
                String songName = nowPlayingSongInfo.getSongName();
                intent.putExtra("songName", songName);
                intent.putExtra("biao", "home");
                startActivity(intent);
            }
        });
        rela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WanAnActivity.this, KnowlegePlayActivity.class);
                SongInfo nowPlayingSongInfo = mInstance.getNowPlayingSongInfo();
                String songName = nowPlayingSongInfo.getSongName();
                intent.putExtra("songName", songName);
                intent.putExtra("biao", "home");
                startActivity(intent);
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
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum) {
            case SONGMUSIC:
                MusicBean musicBean = (MusicBean) o;
                mItemListBeans = musicBean.getResult();
                if (mItemListBeans.size()>0) {
                    for (int i = 0; i < mItemListBeans.size(); i++) {
                        SongInfo s1 = new SongInfo();
                        s1.setSongId(mItemListBeans.get(i).getItemId() + "");
                        s1.setSongUrl(mItemListBeans.get(i).getMediaUri());
                        s1.setSongCover(mItemListBeans.get(i).getPicUri());
                        s1.setSongName(mItemListBeans.get(i).getTitle());
                        s1.setDownloadUrl(mItemListBeans.get(i).getLyricUri());
                        mSongInfos.add(s1);
                    }
                }
                if (mItemListBeans.size() != 0) {
                    if (page == 1) {
                        mWanAnAdapter.addData(mItemListBeans, true);
                    } else {
                        mWanAnAdapter.addData(mItemListBeans, false);
                    }
                } else {
                    mWanAnAdapter.addData(mItemListBeans, false);
                }
                break;
        }
    }

    @Override
    public void showError(String error) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }
}
