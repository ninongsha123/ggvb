package com.tiancaijiazu.app.utils.shopvideo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.utils.events.MessageWrap;

import org.greenrobot.eventbus.EventBus;


/**
 * Created by wapchief on 2018/1/20.
 */
public class MyJZVideoPlayerStandardShop extends JZVideoPlayerStandardShop {
    public static int a = 0;
    public static int b = 0;

    private long mSecClick;
    private long mFirClick;
    private long count;
    private boolean isbo;
    public static ImageView backButton;
    public ImageView startButton;
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
    private ImageView mShare;
    public static RecyclerView mRecylerView;

    public MyJZVideoPlayerStandardShop(Context context) {
        super(context);
        mContext = context;
    }

    public MyJZVideoPlayerStandardShop(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @SuppressLint("WrongViewCast")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void init(Context context) {
        super.init(context);
        startButton = (ImageView) findViewById(R.id.start);
        backButton = (ImageView) findViewById(R.id.back);
        mRetryLayout = (LinearLayout) findViewById(R.id.retry_layout);
        thumbImageView = (ImageView) findViewById(R.id.thumb);
        //clarity = (TextView) findViewById(R.id.clarity);
        //bottomProgressBar = (ProgressBar) findViewById(R.id.bottom_progress);
        //batteryTimeLayout = (LinearLayout) findViewById(R.id.battery_time_layout);
        mLinearLatoutTime = findViewById(R.id.linearLayout_time);
        mShare = findViewById(R.id.share);
        //电量
        //batteryLevel = (ImageView) findViewById(R.id.battery_level);
        //videoCurrentTime = (TextView) findViewById(R.id.video_current_time);
        audio = (TextView) findViewById(R.id.audio);
        mRecylerView = findViewById(R.id.recylerView);
        startButton.setOnClickListener(this);
        backButton.setOnClickListener(this);
        thumbImageView.setOnClickListener(this);
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

    @Override
    public void initData() {

        /*ArrayList<String> list = new ArrayList<>();
        list.add("【第13期】孩子与家长应考手册");
        list.add("【第12期】如何给孩子选择合适的...");
        list.add("【第11期】如何给孩子选择合适的...");
        list.add("【第10期】如何给孩子选择合适的...");
        list.add("【第9期】如何给孩子选择合适的...");
        list.add("【第8期】如何给孩子选择合适的...");
        list.add("【第7期】如何给孩子选择合适的...");
        list.add("【第6期】如何给孩子选择合适的...");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        mRecylerView.setLayoutManager(linearLayoutManager);
        RlvAdapter_v_list rlvAdapterVList = new RlvAdapter_v_list(list);
        mRecylerView.setAdapter(rlvAdapterVList);
        //popupWindow.showAtLocation(inflate, Gravity.RIGHT, 0, 0);
        rlvAdapterVList.setOnClickLisiter(new RlvAdapter_v_list.onClickLisiter() {
            @Override
            public void onClicker(View view, int position, ArrayList<String> mData) {
                rlvAdapterVList.addData(position);
            }
        });*/
        super.initData();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        super.onClick(v);
        int i = v.getId();
         if(i == R.id.share){

        } else if (i == R.id.miracast) {
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
            //video_speed.setVisibility(VISIBLE);
            //batteryTimeLayout.setVisibility(VISIBLE);
            //videoCurrentTime.setVisibility(VISIBLE);
            //batteryLevel.setVisibility(VISIBLE);
            if(mRecylerView.getVisibility() == VISIBLE){
                a = 2;
            }else {
                a = 1;
            }
        } else if (currentScreen == SCREEN_WINDOW_NORMAL) {

        } else if (currentScreen == SCREEN_WINDOW_TINY) {

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
        return R.layout.video_shop_layout;
    }

}
