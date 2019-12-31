package com.tiancaijiazu.app.utils.video;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.dao.DataBaseMannger;
import com.tiancaijiazu.app.dao.HistoryTitleBean;
import com.tiancaijiazu.app.utils.PreUtils;
import com.tiancaijiazu.app.utils.events.DownloadEvent;
import com.tiancaijiazu.app.utils.events.MessageWrap;
import com.tiancaijiazu.app.utils.events.PositionX;
import com.tiancaijiazu.app.utils.events.ProgressTime;
import com.tiancaijiazu.app.utils.events.SpeedEvent;
import com.tiancaijiazu.app.utils.video.adapters.RlvAdapter_v_list;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by wapchief on 2018/1/20.
 */
public class MyJZVideoPlayerStandardList extends JZVideoPlayerStandardList {
    public static int a = 0;
    public static int b = 0;
    private long mSecClick;
    private long mFirClick;
    private long count;
    private boolean isbo;
    public static ImageView backButton;
    public ImageView startButton;
    //倍速
    ImageView video_speed;
    //下载
    ImageView video_download;
    //音频
    TextView audio;
    //弹幕开关
    //Switch mSwitch;
    //弹幕
    //DanmakuView mDanmakuView;
    //MediaController mMediaController;
    //倍速
    float mFloat = 1;
    private Context mContext;
    private LinearLayout mLinearLatoutTime;
    private CheckBox mLock;
    private ImageView mShare;
    private ImageView mMiracast;
    public static RecyclerView mRecylerView;
    private TextView mSelections;
    private int position1 = 0;

    public MyJZVideoPlayerStandardList(Context context) {
        super(context);
        mContext = context;
    }

    public MyJZVideoPlayerStandardList(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @SuppressLint("WrongViewCast")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void init(Context context) {
        super.init(context);
        video_speed =  findViewById(R.id.video_speed);
        video_download =  findViewById(R.id.video_download);
        startButton = (ImageView) findViewById(R.id.start);
        backButton = (ImageView) findViewById(R.id.back);
        mRetryLayout = (LinearLayout) findViewById(R.id.retry_layout);
        thumbImageView = (ImageView) findViewById(R.id.thumb);
        //clarity = (TextView) findViewById(R.id.clarity);
        //bottomProgressBar = (ProgressBar) findViewById(R.id.bottom_progress);
        //batteryTimeLayout = (LinearLayout) findViewById(R.id.battery_time_layout);
        mLinearLatoutTime = findViewById(R.id.linearLayout_time);
        mLock = findViewById(R.id.lock);
        mShare = findViewById(R.id.share);
        //电量
        //batteryLevel = (ImageView) findViewById(R.id.battery_level);
        //videoCurrentTime = (TextView) findViewById(R.id.video_current_time);
        audio = (TextView) findViewById(R.id.audio);
        mMiracast = findViewById(R.id.miracast);
        mRecylerView = findViewById(R.id.recylerView);
        mSelections = findViewById(R.id.selections);
        mSelections.setOnClickListener(this);
        video_speed.setOnClickListener(this);
        video_download.setOnClickListener(this);
        startButton.setOnClickListener(this);
        backButton.setOnClickListener(this);
        thumbImageView.setOnClickListener(this);
        mLock.setOnClickListener(this);
        mShare.setOnClickListener(this);
        mMiracast.setOnClickListener(this);
        //clarity.setOnClickListener(this);
        startButton.setVisibility(VISIBLE);
        backButton.setVisibility(View.VISIBLE);
        //加载失败的布局
        mRetryLayout.setVisibility(View.VISIBLE);
        //播放按钮
        startButton.setVisibility(View.VISIBLE);
        //禁止点击缩略图播放
//        thumbImageView.setFocusable(false);
//        thumbImageView.setEnabled(false);
//         mDetailClassPalyer
//         mDetailClassPalyer.
        //隐藏电池电量
//        batteryTimeLayout.setVisibility(View.GONE);
//        batteryLevel.setVisibility(View.GONE);
        //清晰度
        //clarity.setVisibility(View.VISIBLE);
        //bottomProgressBar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        //bottomProgressBar.setVisibility(View.GONE);

    }

    @Override
    public void initData() {
        int videoI = PreUtils.getInt("videoI", 0);
        List<HistoryTitleBean> historyTitleBeans = DataBaseMannger.getIntrance().selectHistoryTitle();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        mRecylerView.setLayoutManager(linearLayoutManager);
        RlvAdapter_v_list rlvAdapterVList = new RlvAdapter_v_list(historyTitleBeans,videoI);
        mRecylerView.setAdapter(rlvAdapterVList);
        //popupWindow.showAtLocation(inflate, Gravity.RIGHT, 0, 0);
        rlvAdapterVList.setOnClickLisiter(new RlvAdapter_v_list.onClickLisiter() {
            @Override
            public void onClicker(View view, int position, List<HistoryTitleBean> mData) {
                PreUtils.putInt("videoI",position);
                rlvAdapterVList.addData(position);
                EventBus.getDefault().post(new PositionX(position));
            }
        });
        super.initData();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        super.onClick(v);
        int i = v.getId();
        if (i == R.id.video_speed) {
            // 切换倍速
            //video_speed.setText(resolveTypeUI(mFloat) + "X");
            if(mFloat == 1){
                video_speed.setImageResource(R.mipmap.speed_one_two_five);
            }else if(mFloat == 1.25){
                video_speed.setImageResource(R.mipmap.speed_one_five);
            }else if(mFloat == 1.5){
                video_speed.setImageResource(R.mipmap.speed_one_zero);
            }
            mFloat = resolveTypeUI(mFloat);

            EventBus.getDefault().post(new SpeedEvent(mFloat));
            // 更新播放状态
            onStatePreparingChangingUrl(0, getCurrentPositionWhenPlaying());
        }else if (i == R.id.video_download) {
            // 下载
            EventBus.getDefault().post(new DownloadEvent(true));
        }else if(i == R.id.lock){
            boolean checked = mLock.isChecked();
            if(checked){
                topContainer.setVisibility(GONE);
                //bottomContainer.setVisibility(GONE);
                //mLinearLayoutTime.setVisibility(bottomCon);
                //startButton.setVisibility(startBtn);
                //mFenXiang.setVisibility(startBtn);
                mMiracast.setVisibility(GONE);
                startButton.setVisibility(INVISIBLE);
                currentTimeTextView.setVisibility(INVISIBLE);
                progressBar.setVisibility(INVISIBLE);
                totalTimeTextView.setVisibility(INVISIBLE);
                mSelections.setVisibility(GONE);
                video_speed.setVisibility(INVISIBLE);
                //bottomContainer.setVisibility(INVISIBLE);
                //loadingProgressBar.setVisibility(GONE);
                //thumbImageView.setVisibility(GONE);
                //bottomProgressBar.setVisibility(GONE);
                //mRetryLayout.setVisibility(GONE);
            }else {
                topContainer.setVisibility(VISIBLE);
                mMiracast.setVisibility(VISIBLE);
                bottomContainer.setVisibility(VISIBLE);
                startButton.setVisibility(VISIBLE);
                currentTimeTextView.setVisibility(VISIBLE);
                progressBar.setVisibility(VISIBLE);
                totalTimeTextView.setVisibility(VISIBLE);
                mSelections.setVisibility(VISIBLE);
                video_speed.setVisibility(VISIBLE);
                //loadingProgressBar.setVisibility(VISIBLE);
                //thumbImageView.setVisibility(VISIBLE);
                //bottomProgressBar.setVisibility(VISIBLE);
                //mRetryLayout.setVisibility(VISIBLE);
            }

            Log.i(TAG, "onClick: "+checked);
        }else if(i == R.id.share){

        } else if (i == R.id.miracast) {
            isbo = true;
            backButton.performClick();
            EventBus.getDefault().post(new MessageWrap(isbo));
        }else if(i == R.id.selections){
            initData();
            mRecylerView.setVisibility(VISIBLE);
            a = 2;
        }
    }



    /**开始播放*/
    @Override
    public void startVideo() {
        super.startVideo();
        b = 1;
    }

    @Override
    public void onAutoCompletion() {
        super.onAutoCompletion();
    }



    @Override
    public void setUp(Object[] dataSourceObjects, int defaultUrlMapIndex, int screen, Object... objects) {
        super.setUp(dataSourceObjects, defaultUrlMapIndex, screen, objects);
        //如果是全屏才显示相关按钮
        Log.e("data========:", dataSourceObjects.length+"");

        if (currentScreen == SCREEN_WINDOW_FULLSCREEN) {
            //video_speed.setVisibility(VISIBLE);
            //batteryTimeLayout.setVisibility(VISIBLE);
            //videoCurrentTime.setVisibility(VISIBLE);
            //batteryLevel.setVisibility(VISIBLE);
            if(mRecylerView.getVisibility() == VISIBLE){
                a = 2;
            }else {
                a = 1;
            }
            mLock.setVisibility(VISIBLE);
            mMiracast.setVisibility(VISIBLE);
            video_download.setVisibility(VISIBLE);
        } else if (currentScreen == SCREEN_WINDOW_NORMAL) {
            //video_speed.setVisibility(GONE);
            //batteryTimeLayout.setVisibility(GONE);
            //videoCurrentTime.setVisibility(GONE);
            //batteryLevel.setVisibility(GONE);
            mLock.setVisibility(GONE);
            video_download.setVisibility(VISIBLE);
            mMiracast.setVisibility(GONE);
        } else if (currentScreen == SCREEN_WINDOW_TINY) {
            //video_speed.setVisibility(GONE);
            //batteryTimeLayout.setVisibility(GONE);
            //videoCurrentTime.setVisibility(GONE);
            //batteryLevel.setVisibility(GONE);
            video_download.setVisibility(VISIBLE);
            mMiracast.setVisibility(GONE);
            mLock.setVisibility(GONE);
        }
        //startButton.setVisibility(GONE);
    }


    @Override
    public void setProgressAndText(int progress, long position, long duration) {
        super.setProgressAndText(progress, position, duration);
    }

    /**播放、暂停按钮状态*/
    @Override
    public void updateStartImage() {
        super.updateStartImage();
    }

    @Override
    public void onStateAutoComplete() {
        super.onStateAutoComplete();
    }

    /*显示倍速比例*/
    public static float resolveTypeUI(float speed) {
        if (speed == 1) {
            speed = 1.25f;
        } else if (speed == 1.25f) {
            speed = 1.5f;
        } else if (speed == 1.5f) {
            speed = 1f;
        }
        return speed;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return super.onTouch(v, event);
    }

    @Override
    public void onStateNormal() {
        super.onStateNormal();
    }

    @Override
    public void onStatePreparing() {
        super.onStatePreparing();
    }

    @Override
    public void onStatePlaying() {
        super.onStatePlaying();
    }

    @Override
    public void onStatePause() {
        Log.i(TAG, "onStatePause: --------");
        super.onStatePause();
    }

    @Override
    public void onStateError() {
        super.onStateError();
    }

    @Override
    public void onInfo(int what, int extra) {
        super.onInfo(what, extra);
    }

    @Override
    public void onError(int what, int extra) {
        super.onError(what, extra);
    }

    @Override
    public void startWindowFullscreen() {
        super.startWindowFullscreen();
    }

    @Override
    public void startWindowTiny() {
        super.startWindowTiny();
    }

    @Override
    public int getLayoutId() {
        return R.layout.video_list_layout;
    }

}
