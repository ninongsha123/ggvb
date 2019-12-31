package com.tiancaijiazu.app.activitys.video;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.zxing.activity.CaptureActivity;
import com.hpplay.sdk.source.api.IConnectListener;
import com.hpplay.sdk.source.api.ILelinkPlayerListener;
import com.hpplay.sdk.source.api.LelinkPlayer;
import com.hpplay.sdk.source.api.LelinkPlayerInfo;
import com.hpplay.sdk.source.browse.api.IBrowseListener;
import com.hpplay.sdk.source.browse.api.ILelinkServiceManager;
import com.hpplay.sdk.source.browse.api.IQRCodeListener;
import com.hpplay.sdk.source.browse.api.LelinkServiceInfo;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.down.beans.MediaBean;
import com.tiancaijiazu.app.activitys.down.utils.ScannerAnsyTask;
import com.tiancaijiazu.app.activitys.video.adapters.RlvAdapter_video;
import com.tiancaijiazu.app.activitys.video.adapters.RlvAdapter_video_two_list;
import com.tiancaijiazu.app.activitys.video.bean.ContentsBean;
import com.tiancaijiazu.app.activitys.video.bean.VideoTwoExtractBean;
import com.tiancaijiazu.app.app.App;
import com.tiancaijiazu.app.base.activity.BaseActivity;
import com.tiancaijiazu.app.beans.VideoExtractBean;
import com.tiancaijiazu.app.dao.DataBaseMannger;
import com.tiancaijiazu.app.dao.HistoryTitleBean;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.fragments.adapters.RlvAdapter_video_tv;
import com.tiancaijiazu.app.fragments.beans.CollegeCourseBean;
import com.tiancaijiazu.app.fragments.outermostlayer.expandable.ExpListViewAdapter;
import com.tiancaijiazu.app.fragments.outermostlayer.expandable.FatherData;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.CameraPermissionCompat;
import com.tiancaijiazu.app.utils.PreUtils;
import com.tiancaijiazu.app.utils.ScreenStatusUtil;
import com.tiancaijiazu.app.utils.events.CarryOutEvent;
import com.tiancaijiazu.app.utils.events.ControlEvent;
import com.tiancaijiazu.app.utils.events.DownloadEvent;
import com.tiancaijiazu.app.utils.events.MessageWrap;
import com.tiancaijiazu.app.utils.events.PositionX;
import com.tiancaijiazu.app.utils.events.ProgressTime;
import com.tiancaijiazu.app.utils.events.SpeedEvent;
import com.tiancaijiazu.app.utils.video.JZVideoPlayerList;
import com.tiancaijiazu.app.utils.video.MyJZVideoPlayerStandardList;
import com.tiancaijiazu.app.utils.video.systems.MyIJKMediaSystemList;
import com.tiancaijiazu.app.utils.video.systems.MyJZMediaSystemList;
import com.tiancaijiazu.app.mvp.IView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jzvd.JZUserAction;

public class VideoActivity extends BaseActivity<IView, Presenter<IView>> implements IView {


    @BindView(R.id.jiaozi_player)
    MyJZVideoPlayerStandardList mJiaoziPlayer;
    @BindView(R.id.title_video)
    TextView mTitle;
    @BindView(R.id.yuan_jia)
    TextView mYuanJia;
    @BindView(R.id.line_top)
    LinearLayout mLineTop;
    @BindView(R.id.rlv)
    RecyclerView mRlv;
    @BindView(R.id.iv_finis)
    ImageView mIvFinis;
    @BindView(R.id.fen_xiang)
    ImageView mFenXiang;
    @BindView(R.id.text)
    TextView mText;
    @BindView(R.id.text1)
    TextView mText1;
    @BindView(R.id.tv_sum_less)
    TextView mTvSumLess;
    @BindView(R.id.more)
    ImageView mMore;
    @BindView(R.id.money)
    TextView mMoney;
    @BindView(R.id.linear)
    LinearLayout mLinear;
    @BindView(R.id.line_list)
    LinearLayout mLineList;
    @BindView(R.id.mianfei)
    TextView mMianfei;
    @BindView(R.id.data_tv)
    TextView mDataTv;

    private int mWidth;
    private String[] mediaName = {"普通", "高清", "原画"};
    private int i = 1;
    private ILelinkServiceManager mLelinkServiceManager;
    private LelinkPlayer mLeLinkPlayer;
    private static final int REQUEST_CAMERA_PERMISSION = 2;
    private boolean isbo;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            int arg1 = msg.arg1;
            List<LelinkServiceInfo> list = (List<LelinkServiceInfo>) msg.obj;
            if (arg1 == 1) {
                if (mRlvAdapterVideoTv != null) {
                    if(list.size()!=0){
                        mRlvAdapterVideoTv.addData(list);
                        mLine.setVisibility(View.GONE);
                    }

                } else {
                    if(mLine!=null){
                        mLine.setVisibility(View.VISIBLE);
                    }
                }
            } else {
                Toast.makeText(VideoActivity.this, "搜索失败", Toast.LENGTH_SHORT).show();
            }
            super.handleMessage(msg);
        }
    };
    private RlvAdapter_video_tv mRlvAdapterVideoTv;
    private PopupWindow mPopupWindow;
    private LinearLayout mLine;
    private View mInflate;
    private PopupWindow mWindow;
    private ArrayList<FatherData> mFatherDataArrayList;
    private ExpListViewAdapter mExpListViewAdapter;
    private int free_count_video;
    private DownloadManager mDownloadManager;
    private CompleteReceiver completeReceiver;
    private String mMediaUri;
    private String mName;
    private ArrayList<VideoTwoExtractBean> mVideoTwoExtractBeans;
    private int mSize;
    private RlvAdapter_video mRlvAdapterVideo;

    private List<MediaBean> mMediaBeans;
    private OrientationEventListener mOrientationEventListener;

    private void startScanTack() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    ScannerAnsyTask scannerAnsyTask = new ScannerAnsyTask(mActivity);
                    scannerAnsyTask.execute();
                    mMediaBeans = scannerAnsyTask.get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    protected void initEventAndData() {
        initSett();
        //ScreenStatusUtil.setFillDip(this);
        mDownloadManager = (DownloadManager) getBaseContext().getSystemService(serviceString);
        /** 注册下载监听的广播 **/
        completeReceiver = new CompleteReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(DownloadManager.ACTION_NOTIFICATION_CLICKED);
        filter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        /** register download success broadcast **/
        registerReceiver(completeReceiver,
                filter);
        startScanTack();
        //判断是否是刘海屏
        boolean b = ScreenStatusUtil.hasNotchInScreen(this);
        if (b) {
            int[] notchSize = ScreenStatusUtil.getNotchSize(this);
            Log.i("yx", "initEventAndData: " + notchSize[1]);
            Log.i("yx", "initEventAndData: " + notchSize[0]);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mText.getLayoutParams();
            layoutParams.height = notchSize[1];
            mText.setLayoutParams(layoutParams);
            RelativeLayout.LayoutParams layoutParams1 = (RelativeLayout.LayoutParams) mText1.getLayoutParams();
            layoutParams1.height = notchSize[1];
            mText1.setLayoutParams(layoutParams1);
        }

        initTou();
        //LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        mRlv.setLayoutManager(gridLayoutManager);
        ArrayList<VideoExtractBean> videoExtractBeans = new ArrayList<>();
        Intent intent = getIntent();

        CollegeCourseBean.ResultBean result = (CollegeCourseBean.ResultBean) intent.getSerializableExtra("data");

        mMoney.setText("¥" + result.getCourseInfo().getPromoPrice() + "");
        mYuanJia.setText("¥" + result.getCourseInfo().getPrice() + "");
        mYuanJia.setAlpha(0.5f);
        //添加横线
        mYuanJia.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        int isBought = result.getCourseInfo().getIsBought();
        if (isBought == 0) {
            mMianfei.setVisibility(View.VISIBLE);
            mLinear.setVisibility(View.VISIBLE);
        } else {
            mMianfei.setVisibility(View.GONE);
            mLinear.setVisibility(View.GONE);
        }
        List<CollegeCourseBean.ResultBean.ChapterListBean> chapterList = result.getChapterList();
        mVideoTwoExtractBeans = new ArrayList<>();
        List<HistoryTitleBean> historyTitleBeans = DataBaseMannger.getIntrance().selectHistoryTitle();
        if(historyTitleBeans.size()!=0){
            DataBaseMannger.getIntrance().deleteHistoryTitleAll();

        }
        ArrayList<HistoryTitleBean> arrayList = new ArrayList<>();
        for (int j = 0; j < chapterList.size(); j++) {
            List<CollegeCourseBean.ResultBean.ChapterListBean.ContentsListBean> contentsList = chapterList.get(j).getContentsList();
            ArrayList<ContentsBean> contentsBeans = new ArrayList<>();
            for (int k = 0; k < contentsList.size(); k++) {
                int type = contentsList.get(k).getType();
                if (type == 1) {
                    //抽取出视频
                    CollegeCourseBean.ResultBean.ChapterListBean.ContentsListBean contentsListBean = contentsList.get(k);
                    int type1 = contentsListBean.getType();
                    int isFree = contentsListBean.getIsFree();
                    long chapterId = contentsListBean.getChapterId();
                    long contentsId = contentsListBean.getContentsId();
                    long courseId = contentsListBean.getCourseId();
                    String description = contentsListBean.getDescription();
                    int duration = contentsListBean.getDuration();
                    String mediaUri = contentsListBean.getMediaUri();
                    String picUri = contentsListBean.getPicUri();
                    String title = contentsListBean.getTitle();
                    contentsBeans.add(new ContentsBean(contentsId, title, duration, chapterId, courseId, type1, picUri, mediaUri, isFree, description));
                    videoExtractBeans.add(new VideoExtractBean(contentsId, title, duration, chapterId, courseId, type1, picUri, mediaUri, isFree, description));
                    arrayList.add(new HistoryTitleBean(null,title));
                }
            }

            if (contentsBeans.size() != 0) {
                mVideoTwoExtractBeans.add(new VideoTwoExtractBean(chapterList.get(j).getTitle(), contentsBeans));
            }
        }
        DataBaseMannger.getIntrance().insertHistoryTitle(arrayList);
        mSize = videoExtractBeans.size();
        mTvSumLess.setText("共" + mSize + "课时");
        initPopWindow(mVideoTwoExtractBeans);
        mRlvAdapterVideo = new RlvAdapter_video(videoExtractBeans, this,isBought);
        mRlv.setAdapter(mRlvAdapterVideo);
        mRlvAdapterVideo.setOnClickLisiterState(new RlvAdapter_video.onClickLisiterState() {
            @Override
            public void onClickerState(View view, int position, ArrayList<VideoExtractBean> mData) {
                if (MyJZVideoPlayerStandardList.b == 1) {
                    JZVideoPlayerList.quitFullscreenOrTinyWindow();
                }
                PreUtils.putInt("videoI",position);
                MyJZVideoPlayerStandardList.b = 0;
                mMediaUri = mData.get(position).getMediaUri();
                initPlayerUrl(mMediaUri);
                mJiaoziPlayer.startVideo();
                Glide.with(VideoActivity.this).load(mData.get(position).getPicUri()).into(mJiaoziPlayer.thumbImageView);
                mRlvAdapterVideo.addUrl(mData.get(position).getMediaUri());
                mName = mData.get(position).getTitle();
                mTitle.setText(mName);
                mDataTv.setText(mData.get(position).getDescription());
            }
        });

        mMediaUri = intent.getStringExtra("mediaUri");
        Log.i("yx666", "initEventAndData: "+mMediaUri);
        String courseId = intent.getStringExtra("courseId");
        String picUri = intent.getStringExtra("picUri");
        String description = intent.getStringExtra("description");
        if (!VideoActivity.this.isFinishing()) {
            Glide.with(VideoActivity.this).load(picUri).into(mJiaoziPlayer.thumbImageView);
        }
        mName = intent.getStringExtra("name");
        mTitle.setText(mName);
        mDataTv.setText(description);
        if (mMediaUri != null) {
            initPlayerUrl(mMediaUri);
            mRlvAdapterVideo.addUrl(mMediaUri);
        }
        mJZMediaSystem = new MyJZMediaSystemList();
        mIJKMediaSystem = new MyIJKMediaSystemList();

        mJiaoziPlayer.post(new Runnable() {
            @Override
            public void run() {
                mJiaoziPlayer.startVideo();
            }
        });

        //initPopWindow();
        mOrientationEventListener = new OrientationEventListener(this) {

            @Override
            public void onOrientationChanged(int rotation) {
                if (((rotation >= 0) && (rotation <= 30)) || (rotation >= 330)) {//rotation代表的是角度，这个时候代表的是竖屏
                    Log.i("sd", "onOrientationChanged: 竖屏");
                    isbo = true;
                }else if (rotation >= 230 && rotation <= 310) {//代表的是横屏，准确来说是正横屏
                    Log.i("sd", "onOrientationChanged: 正横屏");
                    if(isbo){
                        mJiaoziPlayer.onEvent(JZUserAction.ON_ENTER_FULLSCREEN);
                        mJiaoziPlayer.startWindowFullscreen();
                        mOrientationEventListener.disable();
                        isbo = false;
                    }

                } else if (rotation>60&&rotation<120){//也是横屏不过是反的横屏
                    Log.i("sd", "onOrientationChanged: 反的横屏");
                }

            }
        };
        mOrientationEventListener.enable();
    }

    /**
     * 单击回退
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Log.i("yx333", "onKeyDown: "+MyJZVideoPlayerStandardList.a);
            if (MyJZVideoPlayerStandardList.a == 1) {
                MyJZVideoPlayerStandardList.backButton.performClick();
                MyJZVideoPlayerStandardList.a = 0;
                mOrientationEventListener.enable();
            } else if (MyJZVideoPlayerStandardList.a == 2) {
                MyJZVideoPlayerStandardList.mRecylerView.setVisibility(View.GONE);
                MyJZVideoPlayerStandardList.a = 1;
            } else {
                return super.onKeyDown(keyCode, event);
            }
        }
        return false;
    }

    ILelinkPlayerListener playerListener = new ILelinkPlayerListener() {

        @Override
        public void onLoading() {

        }

        /**
         * 播放开始
         */
        @Override
        public void onStart() {
            Log.i("yx123", "onStart: =============================");

        }

        /**
         * 暂停
         */
        @Override
        public void onPause() {
            Log.i("yx123", "onPause: ======");

        }

        /**
         * 播放完成
         */
        @Override
        public void onCompletion() {

        }

        /**
         * 播放结束
         */
        @Override
        public void onStop() {

        }

        /**
         * 进度调节：单位为百分比
         */
        @Override
        public void onSeekComplete(int pPosition) {

        }

        /**
         * 保留接口
         */
        @Override
        public void onInfo(int what, int extra) {

        }

        /**
         * 错误回调
         */
        @Override
        public void onError(int what, int extra) {

        }

        /**
         * 音量变化回调
         */
        @Override
        public void onVolumeChanged(float percent) {
            Log.i("yx123", "onVolumeChanged: " + percent);

        }

        /**
         * 播放进度信息回调
         * @param duration 总长度：单位秒
         * @param position 当前进度：单位秒
         */
        @Override
        public void onPositionUpdate(long duration, long position) {
            Log.i("yx123", "onPositionUpdate: " + position);
        }

    };
    private IBrowseListener browserListener = new IBrowseListener() {

        @Override
        public void onBrowse(int resultCode, List<LelinkServiceInfo> list) {
            Message obtain = Message.obtain();
            obtain.obj = list;
            obtain.arg1 = resultCode;
            mHandler.sendMessage(obtain);
        }
    };
    IConnectListener connectListener = new IConnectListener() {

        @Override
        public void onConnect(LelinkServiceInfo serviceInfo, int extra) {
            // 实例化播放的媒体信息
            LelinkPlayerInfo lelinkPlayerInfo = new LelinkPlayerInfo();
            // 设置媒体类型：
            //LelinkPlayerInfo.TYPE_VIDEO：视频
            //LelinkPlayerInfo.TYPE_AUDIO：音乐
            //LelinkPlayerInfo.TYPE_IMAGE：图片
            lelinkPlayerInfo.setType(LelinkPlayerInfo.TYPE_VIDEO);
            // 设置本地文件path，支持本地推送
            /*String path = Environment.getExternalStorageDirectory().getPath();
            Log.i("yx123", "onConnect: "+path);
            lelinkPlayerInfo.setLocalPath(path+"/Music/davoide.mp4");*/
            // 设置网络url，支持网络推送，与本地推送二选一
            lelinkPlayerInfo.setUrl(mMediaUri);
            mLeLinkPlayer.setDataSource(lelinkPlayerInfo);
            mLeLinkPlayer.start();
            Toast.makeText(VideoActivity.this, "连接成功", Toast.LENGTH_SHORT).show();
            //mPopupWindow.dismiss();

        }


        @Override
        public void onDisconnect(LelinkServiceInfo serviceInfo, int what, int extra) {

        }

    };


    //设置状态栏与状态栏字体颜色
    private void initSett() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                        | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                window.getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        //| View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR//设置显示为白色背景，黑色字体
                );
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
            }
        }
    }

    private void initTou() {
        /*if (ContextCompat.checkSelfPermission(getApplication(),
                Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_DENIED
                && ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_DENIED) {

        } else {
            // 若没有授权，会弹出一个对话框（这个对话框是系统的，开发者不能自己定制），用户选择是否授权应用使用系统权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }*/
        mLelinkServiceManager = App.getApplication().getILelinkServiceManager();
        mLeLinkPlayer = new LelinkPlayer(this);
        mLeLinkPlayer.setConnectListener(connectListener);
        mLelinkServiceManager.setOnBrowseListener(browserListener);
        mLeLinkPlayer.setPlayerListener(playerListener);

    }

    /**
     * 初始化播放地址
     */
    private void initPlayerUrl(String url) {
        Object[] objects = new Object[3];
        LinkedHashMap map = new LinkedHashMap();
        for (int i = 0; i < 3; i++) {
            map.put(mediaName[i], url);
        }
        objects[0] = map;
        objects[1] = false;
        objects[2] = new HashMap<>();
        ((HashMap) objects[2]).put("key", "value");
        if (mJiaoziPlayer != null) {
            mJiaoziPlayer.setUp(objects, 0, JZVideoPlayerList.SCREEN_WINDOW_NORMAL, "");
        }
    }

    /**
     * 设置屏幕方向
     */
    private void initPlayer() {
        JZVideoPlayerList.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
        JZVideoPlayerList.NORMAL_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    }

    //系统播放器引擎
    MyJZMediaSystemList mJZMediaSystem;
    MyIJKMediaSystemList mIJKMediaSystem;

    @Override
    public void onPause() {
        super.onPause();
        JZVideoPlayerList.releaseAllVideos();
        JZVideoPlayerList.setMediaInterface(mIJKMediaSystem);
        JZVideoPlayerList.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_SENSOR;
        JZVideoPlayerList.NORMAL_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    }

    @Override
    public void onResume() {
        super.onResume();
        JZVideoPlayerList.setMediaInterface(mIJKMediaSystem);
        initPlayer();
    }


    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 倍速切换
     */
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onMessageEventPostSpeed(SpeedEvent event) {
        mJZMediaSystem.setSpeeding(event.getSpeed());
        mIJKMediaSystem.setSpeeding(event.getSpeed());
        Toast.makeText(this, "正在切换倍速:" + event.getSpeed(), Toast.LENGTH_LONG).show();
    }

    /**
     * 点击视频返回键调用且点击重试时调用
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onMessageCarryOutEvent(CarryOutEvent event) {
        String zhixing = event.getZhixing();
        Log.i("yx333", "onMessageCarryOutEvent: "+zhixing);
        if("zhixing".equals(zhixing)){
            if (MyJZVideoPlayerStandardList.a == 1) {
                MyJZVideoPlayerStandardList.a = 0;
                mOrientationEventListener.enable();
            }
        }else {
            mOrientationEventListener.enable();
        }
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onPing(MessageWrap isbo) {
        if (isbo.isIsbo()) {
            View inflate = LayoutInflater.from(this).inflate(R.layout.list_tv_layout, null);
            mPopupWindow = new PopupWindow(inflate, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            mPopupWindow.setFocusable(true);// 取得焦点
            mPopupWindow.setAnimationStyle(R.style.popwin_anim_style);
            mPopupWindow.setClippingEnabled(false);
            //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
            mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
            //点击外部消失
            mPopupWindow.setOutsideTouchable(true);
            //设置可以点击
            mPopupWindow.setTouchable(true);

            RecyclerView recylerView = inflate.findViewById(R.id.recylerView);
            ArrayList<LelinkServiceInfo> list = new ArrayList<>();
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            recylerView.setLayoutManager(linearLayoutManager);
            mRlvAdapterVideoTv = new RlvAdapter_video_tv(list);
            recylerView.setAdapter(mRlvAdapterVideoTv);
            mPopupWindow.showAtLocation(inflate, Gravity.BOTTOM, 0, 0);
            mLelinkServiceManager.browse(ILelinkServiceManager.TYPE_ALL);

            ImageView refreshIv = inflate.findViewById(R.id.refresh_iv);
            refreshIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Animation rotateAnimation = new RotateAnimation(0,360,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
                    // 1. fromDegrees ：动画开始时 视图的旋转角度(正数 = 顺时针，负数 = 逆时针)
                    // 2. toDegrees ：动画结束时 视图的旋转角度(正数 = 顺时针，负数 = 逆时针)
                    // 3. pivotXType：旋转轴点的x坐标的模式
                    // 4. pivotXValue：旋转轴点x坐标的相对值
                    // 5. pivotYType：旋转轴点的y坐标的模式
                    // 6. pivotYValue：旋转轴点y坐标的相对值
                    // pivotXType = Animation.ABSOLUTE:旋转轴点的x坐标 =  View左上角的原点 在x方向 加上 pivotXValue数值的点(y方向同理)
                    // pivotXType = Animation.RELATIVE_TO_SELF:旋转轴点的x坐标 = View左上角的原点 在x方向 加上 自身宽度乘上pivotXValue数值的值(y方向同理)
                    // pivotXType = Animation.RELATIVE_TO_PARENT:旋转轴点的x坐标 = View左上角的原点 在x方向 加上 父控件宽度乘上pivotXValue数值的值 (y方向同理)
                    rotateAnimation.setDuration(1500);
                    refreshIv.startAnimation(rotateAnimation);
                }
            });
            mRlvAdapterVideoTv.setOnClickLisiterTv(new RlvAdapter_video_tv.onClickLisiterTv() {
                @Override
                public void onClickerTv(View view, int position, ArrayList<LelinkServiceInfo> mData) {
                    LelinkServiceInfo lelinkServiceInfo = mData.get(position);
                    mLeLinkPlayer.connect(lelinkServiceInfo);
                }
            });

            mLine = inflate.findViewById(R.id.line);
            LinearLayout linear = inflate.findViewById(R.id.linear);
            ImageView ivFinis = inflate.findViewById(R.id.iv_finis);
            ivFinis.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPopupWindow.dismiss();
                }
            });
            linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CameraPermissionCompat.checkCameraPermission(VideoActivity.this, new CameraPermissionCompat.OnCameraPermissionListener() {

                        @Override
                        public void onGrantResult(boolean granted) {
                            if (granted) {
                                // 允许，打开二维码
                                // 允许，打开二维码
                                Intent intent = new Intent(VideoActivity.this, CaptureActivity.class);
                                startActivityForResult(intent, REQUEST_CAMERA_PERMISSION);
                            } else {
                                // 若没有授权，会弹出一个对话框（这个对话框是系统的，开发者不能自己定制），用户选择是否授权应用使用系统权限
                            }
                        }


                    });
                }
            });

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CAMERA_PERMISSION) {
                String scanResult = data.getStringExtra(CaptureActivity.INTENT_EXTRA_KEY_QR_SCAN);
                Log.i("yx123", "onActivityResult: " + scanResult);
                mLelinkServiceManager.addQRServiceInfo(scanResult, new IQRCodeListener() {
                    @Override
                    public void onParceResult(int i, LelinkServiceInfo lelinkServiceInfo) {
                        if (resultCode == IQRCodeListener.PARCE_SUCCESS) {
                            mLeLinkPlayer.connect(lelinkServiceInfo);
                        }
                        mLeLinkPlayer.connect(lelinkServiceInfo);
                    }
                });


            }
        }
    }

    //下载任务
    String serviceString = Context.DOWNLOAD_SERVICE;
    long reference;
    private boolean isboo = false;
    /**
     * 下载
     */
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onMessageEventPostDetail(DownloadEvent event) {
        for (int j = 0; j < mMediaBeans.size(); j++) {
            if(mMediaBeans.get(j).getName().contains(mName)){
               isboo = true;
               break;
            }
        }
        if(isboo){
            Toast.makeText(this, "已下载该视频", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "下载中", Toast.LENGTH_SHORT).show();

            Uri uri = Uri.parse(mMediaUri);
            DownloadManager.Request request = new DownloadManager.Request(uri);
            //通知栏的标题
            request.setTitle("视频下载");
            //显示通知栏的说明
            request.setDescription(mName);
            request.setVisibleInDownloadsUi(true);
            //下载到那个文件夹下，以及命名
            request.setDestinationInExternalFilesDir(getApplicationContext(), Environment.DIRECTORY_DOWNLOADS, mName + ".mp4");
            //下载的唯一标识，可以用这个标识来控制这个下载的任务enqueue（）开始执行这个任务
            reference = mDownloadManager.enqueue(request);
        }

    }

    //下载的状态广播接收
    class CompleteReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            //下载完成之后监听
            String action = intent.getAction();
            //下载完成的监听
            if (action.equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
                Toast.makeText(context, "下载完成", Toast.LENGTH_SHORT).show();
                startScanTack();
            }
            //点击通知栏，取消下载任务
            if (action.equals(DownloadManager.ACTION_NOTIFICATION_CLICKED)) {
                mDownloadManager.remove((Long) reference);
            }

        }
    }

    /**
     * 进度
     */
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onProgressTime(ProgressTime event) {
        Log.i("yx123", "onProgressTime: " + event.getProgress());
        mLeLinkPlayer.seekTo(event.getProgress());

    }
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onPositionX(PositionX event) {
        int position = event.getProgress();
        if (MyJZVideoPlayerStandardList.b == 1) {
            JZVideoPlayerList.quitFullscreenOrTinyWindow();
        }
        MyJZVideoPlayerStandardList.b = 0;
        mMediaUri = mRlvAdapterVideo.mData.get(position).getMediaUri();
        initPlayerUrl(mMediaUri);
        mJiaoziPlayer.startVideo();
        mJiaoziPlayer.onEvent(JZUserAction.ON_ENTER_FULLSCREEN);
        mJiaoziPlayer.startWindowFullscreen();
        //mJiaoziPlayer.release();
        Glide.with(VideoActivity.this).load(mRlvAdapterVideo.mData.get(position).getPicUri()).into(mJiaoziPlayer.thumbImageView);
        mRlvAdapterVideo.addUrl(mRlvAdapterVideo.mData.get(position).getMediaUri());
        mName = mRlvAdapterVideo.mData.get(position).getTitle();
        mTitle.setText(mName);
        mDataTv.setText(mRlvAdapterVideo.mData.get(position).getDescription());
    }

    /**
     * 进度
     */
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onControl(ControlEvent event) {
        if (event.isIsbo()) {
            mLeLinkPlayer.resume();
        } else {
            mLeLinkPlayer.pause();
        }
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_video;
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum) {

        }
    }

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }


    @OnClick({R.id.iv_finis, R.id.fen_xiang, R.id.line_list})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_finis:
                finish();
                break;
            case R.id.fen_xiang:
                break;
            case R.id.line_list:
                mWindow.showAtLocation(mInflate, Gravity.BOTTOM, 0, 0);
                break;
        }
    }

    private void initPopWindow(ArrayList<VideoTwoExtractBean> result) {
        mInflate = LayoutInflater.from(this).inflate(R.layout.videolist_pop, null);
        mWindow = new PopupWindow(mInflate, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mWindow.setClippingEnabled(true);
        mWindow.setFocusable(true);// 取得焦点
        //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
        mWindow.setBackgroundDrawable(new BitmapDrawable());
        //点击外部消失
        mWindow.setOutsideTouchable(true);
        //设置可以点击
        mWindow.setTouchable(true);
        //进入退出的动画，指定刚才定义的style
        mWindow.setAnimationStyle(R.style.popwin_anim_style);

        RecyclerView recylerView = mInflate.findViewById(R.id.recylerView);
        TextView content_number = mInflate.findViewById(R.id.content_number);
        TextView tv = mInflate.findViewById(R.id.tv);
        content_number.setText("内容列表("+mSize+")");
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mWindow.dismiss();
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(VideoActivity.this);
        recylerView.setLayoutManager(linearLayoutManager);

        RlvAdapter_video_two_list rlvAdapterVideoTwoList = new RlvAdapter_video_two_list(result, VideoActivity.this);
        recylerView.setAdapter(rlvAdapterVideoTwoList);
        rlvAdapterVideoTwoList.setOnClickLisiter(new RlvAdapter_video_two_list.onClickLisiter() {
            @Override
            public void onClicker(View view, int position, ArrayList<ContentsBean> mData) {
                if (MyJZVideoPlayerStandardList.b == 1) {
                    JZVideoPlayerList.quitFullscreenOrTinyWindow();
                }
                MyJZVideoPlayerStandardList.b = 0;
                mMediaUri = mData.get(position).getMediaUri();
                initPlayerUrl(mMediaUri);
                mJiaoziPlayer.startVideo();
                Glide.with(VideoActivity.this).load(mData.get(position).getPicUri()).into(mJiaoziPlayer.thumbImageView);
                mRlvAdapterVideo.addUrl(mData.get(position).getMediaUri());
                mName = mData.get(position).getTitle();
                mTitle.setText(mName);
                mDataTv.setText(mData.get(position).getDescription());
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (completeReceiver != null) {
            unregisterReceiver(completeReceiver);
        }
        mOrientationEventListener.disable();
        super.onDestroy();
    }

}
