package com.tiancaijiazu.app.utils.views;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.utils.events.DownloadEvent;
import com.tiancaijiazu.app.utils.events.MessageWrap;
import com.tiancaijiazu.app.utils.events.SpeedEvent;

import org.greenrobot.eventbus.EventBus;


/**
 * Created by wapchief on 2018/1/20.
 */
public class MyJZVideoPlayerStandard extends JZVideoPlayerStandard {

    public static int b = 0;
    public static int c = 0;
    private long mSecClick;
    private long mFirClick;
    private long count;
    public static ImageView backButton;
    public ImageView startButton;
    private CheckBox mLock;
    private boolean isbo;
    private ImageView mMiracast;
    private ImageView mShare;
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
    private TextView mFenge;

    public MyJZVideoPlayerStandard(Context context) {
        super(context);
        mContext = context;
    }

    public MyJZVideoPlayerStandard(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void init(Context context) {
        super.init(context);
        video_speed = (ImageView) findViewById(R.id.video_speed);
        video_download = (ImageView) findViewById(R.id.video_download);
        startButton = (ImageView) findViewById(R.id.start);
        backButton = (ImageView) findViewById(R.id.back);
        mRetryLayout = (LinearLayout) findViewById(R.id.retry_layout);
        thumbImageView = (ImageView) findViewById(R.id.thumb);
        mLock = findViewById(R.id.lock);
        mShare = findViewById(R.id.share);
        mMiracast = findViewById(R.id.miracast);
        mFenge = findViewById(R.id.fenge);
        //clarity = (TextView) findViewById(R.id.clarity);
        //bottomProgressBar = (ProgressBar) findViewById(R.id.bottom_progress);
        mLinearLatoutTime = findViewById(R.id.linearLayout_time);
        audio = (TextView) findViewById(R.id.audio);
        video_speed.setOnClickListener(this);
        video_download.setOnClickListener(this);
        startButton.setOnClickListener(this);
        backButton.setOnClickListener(this);
        thumbImageView.setOnClickListener(this);
        mLock.setOnClickListener(this);
        mMiracast.setOnClickListener(this);
        mShare.setOnClickListener(this);
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


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        super.onClick(v);
        int i = v.getId();
        if (i == R.id.video_speed) {
            // 切换倍速
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
        }else if(i == R.id.lock) {
            boolean checked = mLock.isChecked();
            if (checked) {
                topContainer.setVisibility(GONE);
                mMiracast.setVisibility(GONE);
                startButton.setVisibility(GONE);
                fullscreenButton.setVisibility(GONE);
                currentTimeTextView.setVisibility(INVISIBLE);
                mFenge.setVisibility(INVISIBLE);
                progressBar.setVisibility(INVISIBLE);
                totalTimeTextView.setVisibility(INVISIBLE);
                video_speed.setVisibility(INVISIBLE);
            }else {
                fullscreenButton.setVisibility(VISIBLE);
                topContainer.setVisibility(VISIBLE);
                mMiracast.setVisibility(VISIBLE);
                bottomContainer.setVisibility(VISIBLE);
                startButton.setVisibility(VISIBLE);
                currentTimeTextView.setVisibility(VISIBLE);
                mFenge.setVisibility(VISIBLE);
                progressBar.setVisibility(VISIBLE);
                totalTimeTextView.setVisibility(VISIBLE);
                video_speed.setVisibility(VISIBLE);
            }
        }else if(i == R.id.miracast){
            isbo = true;
            backButton.performClick();
            EventBus.getDefault().post(new MessageWrap(isbo));
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
            video_speed.setVisibility(VISIBLE);
            video_download.setVisibility(VISIBLE);
            //mShare.setVisibility(VISIBLE);
            c = 1;
            mLock.setVisibility(VISIBLE);
            mMiracast.setVisibility(VISIBLE);
        } else if (currentScreen == SCREEN_WINDOW_NORMAL) {
            video_speed.setVisibility(GONE);
            video_download.setVisibility(GONE);
            mShare.setVisibility(GONE);
            mLock.setVisibility(GONE);
            mMiracast.setVisibility(GONE);
        } else if (currentScreen == SCREEN_WINDOW_TINY) {
            video_speed.setVisibility(GONE);
            video_download.setVisibility(GONE);
            mShare.setVisibility(GONE);
            mLock.setVisibility(GONE);
            mMiracast.setVisibility(GONE);
        }
        startButton.setVisibility(GONE);
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
        return R.layout.jiaozi_player_video;
    }


}
