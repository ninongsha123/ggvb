package com.tiancaijiazu.app.homepagefragment;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
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
import com.lzx.starrysky.manager.MediaSessionConnection;
import com.lzx.starrysky.manager.MusicManager;
import com.lzx.starrysky.manager.OnPlayerEventListener;
import com.lzx.starrysky.model.SongInfo;
import com.lzx.starrysky.utils.TimerTaskManager;
import com.makeramen.roundedimageview.RoundedImageView;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.activitypage.collegeactivitys.ClassListActivity;
import com.tiancaijiazu.app.activitys.activitypage.homepageactivitys.KnowlegePlayActivity;
import com.tiancaijiazu.app.activitys.activitypage.LordActivity;
import com.tiancaijiazu.app.activitys.activitypage.homepageactivitys.OfflineActivity;
import com.tiancaijiazu.app.activitys.activitypage.ParentingEncyclopediaactivitys.RecDataActivity;
import com.tiancaijiazu.app.activitys.activitypage.ShortVideoActivity;
import com.tiancaijiazu.app.activitys.activitypage.homepageactivitys.SubheadOneActivity;
import com.tiancaijiazu.app.activitys.activitypage.homepageactivitys.SubheadTwoActivity;
import com.tiancaijiazu.app.activitys.activitypage.TryListenerActivity;
import com.tiancaijiazu.app.activitys.activitypage.ParentingEncyclopediaactivitys.UserCenterActivity;
import com.tiancaijiazu.app.activitys.activitypage.WebActivity;
import com.tiancaijiazu.app.activitys.activitypage.homepageactivitys.WanAnActivity;
import com.tiancaijiazu.app.activitys.activitypage.homepageactivitys.ZaoAnActivity;
import com.tiancaijiazu.app.activitys.adapters.RlvAdapter_songlist;
import com.tiancaijiazu.app.activitys.early.EarlyActivity;
import com.tiancaijiazu.app.activitys.evalua.GuidePageActivity;
import com.tiancaijiazu.app.activitys.qi_activitys.BaoActivity;
import com.tiancaijiazu.app.activitys.shopping_activity.ShopActivity;
import com.tiancaijiazu.app.app.App;
import com.tiancaijiazu.app.base.fragment.BaseFragment;
import com.tiancaijiazu.app.beans.AdPositionIdBean;
import com.tiancaijiazu.app.beans.BabyMessageBean;
import com.tiancaijiazu.app.beans.BringBean;
import com.tiancaijiazu.app.beans.FormalCurriculumBean;
import com.tiancaijiazu.app.beans.GamehomePageBeans;
import com.tiancaijiazu.app.beans.HomeBBSbean;
import com.tiancaijiazu.app.beans.HomeBannerBean;
import com.tiancaijiazu.app.beans.SetBabyBean;
import com.tiancaijiazu.app.beans.ShortVideoBean;
import com.tiancaijiazu.app.beans.SongBean;
import com.tiancaijiazu.app.beans.UserCardTypeBean;
import com.tiancaijiazu.app.beans.UserInfoBean;
import com.tiancaijiazu.app.dao.DataBaseMannger;
import com.tiancaijiazu.app.dao.UserInFo;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.fragments.adapters.RlvAdapter_home;
import com.tiancaijiazu.app.fragments.adapters.RlvAdapter_paly;
import com.tiancaijiazu.app.fragments.adapters.RlvAdapter_video_tv;
import com.tiancaijiazu.app.fragments.views.NiceImageView;
import com.tiancaijiazu.app.http.JmupPerssionMagent;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.CameraPermissionCompat;
import com.tiancaijiazu.app.utils.CustomFragmeLayout;
import com.tiancaijiazu.app.utils.MediumBoldTextViewTitle;
import com.tiancaijiazu.app.utils.PreUtils;
import com.tiancaijiazu.app.utils.ScreenStatusUtil;
import com.tiancaijiazu.app.utils.TimeUtil;
import com.tiancaijiazu.app.utils.cache.FileMusicUtils;
import com.tiancaijiazu.app.utils.city.ToastUtils;
import com.tiancaijiazu.app.utils.events.DownloadEvent;
import com.tiancaijiazu.app.utils.events.MessageWrap;
import com.tiancaijiazu.app.utils.events.SpeedEvent;
import com.tiancaijiazu.app.utils.status.LoadingLayout;
import com.tiancaijiazu.app.utils.systems.MyIJKMediaSystem;
import com.tiancaijiazu.app.utils.systems.MyJZMediaSystem;
import com.tiancaijiazu.app.utils.views.JZVideoPlayer;
import com.tiancaijiazu.app.utils.views.MyJZVideoPlayerStandard;
import com.tiancaijiazu.app.mvp.IView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.refactor.lib.colordialog.util.DisplayUtil;
import de.hdodenhof.circleimageview.CircleImageView;
import www.linwg.org.lib.LCardView;

import static com.tiancaijiazu.app.activitys.activitypage.LordActivity.changePageSelect;
import static com.tiancaijiazu.app.activitys.activitypage.LordActivity.fragment_now;
import static com.tiancaijiazu.app.activitys.activitypage.LordActivity.mCollegeFragment;
import static com.tiancaijiazu.app.activitys.activitypage.LordActivity.mParentingEncyclopediaFragment;
import static com.tiancaijiazu.app.activitys.activitypage.LordActivity.mShoppingMallFragment;
import static com.tiancaijiazu.app.utils.ScreenUtil.getScreenWidth;

/**
 * A simple {@link Fragment} subclass.
 *   首页
 */
public class HomePageFragment extends BaseFragment<IView, Presenter<IView>> implements IView, OnPlayerEventListener {

    @BindView(R.id.banner)
    Banner mBanner;
    @BindView(R.id.loadinglayout)
    LoadingLayout loadinglayout;
    @BindView(R.id.more_very)
    RelativeLayout mMores;
    //育儿百科图片
    @BindView(R.id.bring_up_iv)
    ImageView mBringUpIv;
    //育儿百科字体
    @BindView(R.id.bring_up_tv)
    TextView mBringUpTv;
    //线上课堂图片
    @BindView(R.id.online_college_iv)
    ImageView mOnlineCollegeIv;
    //线上课堂字体
    @BindView(R.id.online_college_tv)
    TextView mOnlineCollegeTv;
    //育儿百科图片
    @BindView(R.id.offline_class_iv)
    ImageView mOfflineClassIv;
    //育儿百科字体
    @BindView(R.id.offline_class_tv)
    TextView mOfflineClassTv;
    //家族商城图片
    @BindView(R.id.family_mall_iv)
    ImageView mFamilyMallIv;
    //家族商城字体
    @BindView(R.id.family_mall_tv)
    TextView mFamilyMallTv;
    //视频播放器控件
    @BindView(R.id.jiaozi_player)
    MyJZVideoPlayerStandard mJiaoziPlayer;
    //视频列表recylerView id
    @BindView(R.id.recylerView)
    RecyclerView mRecylerView;
    //NestedScrollView滑动控件
    @BindView(R.id.nested)
    CustomFragmeLayout mNested;
    //头布局 id
    @BindView(R.id.relative)
    RelativeLayout mRelative;
    //头布局上的音乐图标
    /*@BindView(R.id.video_tu)
    ImageView mVideoTu;*/
    //前第一张图片的控件 id
    @BindView(R.id.iv_a)
    RoundedImageView mIvA;
    @BindView(R.id.iv_b)
    RoundedImageView mIvB;
    @BindView(R.id.iv_c)
    RoundedImageView mIvC;
    //配合NestedScrollView 使用的CoordinatorLayout使用
    @BindView(R.id.coordinator)
    CoordinatorLayout mCoordinator;
    //天才家族的logo
    /*@BindView(R.id.iv_logo)
    CircleImageView mIvLogo;*/
    //头布局Title
    @BindView(R.id.title_name)
    MediumBoldTextViewTitle mTitle;
    //视频上方的罩子布局
    public RelativeLayout mRela;
    //视频的播放图标
    @BindView(R.id.iv_play)
    ImageView mIvPlay;
    //上层约束布局控件 id
    @BindView(R.id.constraint)
    ConstraintLayout mConstraint;
    @BindView(R.id.card)
    LCardView mCard;
    @BindView(R.id.short_video_one)
    FrameLayout mShortVideoOne;
    @BindView(R.id.short_video_two)
    FrameLayout mShortVideoTwo;
    @BindView(R.id.short_video_three)
    FrameLayout mShortVideoThree;
    @BindView(R.id.bring)
    LinearLayout mBring;
    @BindView(R.id.online)
    LinearLayout mOnline;
    @BindView(R.id.offline)
    LinearLayout mOffline;
    @BindView(R.id.family)
    LinearLayout mFamily;
    @BindView(R.id.subhead_one)
    TextView mSubheadOne;
    @BindView(R.id.subhead_two)
    TextView mSubheadTwo;
    @BindView(R.id.subhead_three)
    TextView mSubheadThree;
    @BindView(R.id.game_title_one)
    TextView mGameTitleOne;
    @BindView(R.id.title_one)
    TextView title_one;
    @BindView(R.id.title_two)
    TextView title_two;
    @BindView(R.id.morning_show)
    TextView morning_show;
    @BindView(R.id.title_three)
    TextView title_three;
    @BindView(R.id.game)
    TextView game;
    @BindView(R.id.game_title_two)
    TextView mGameTitleTwo;
    @BindView(R.id.game_iamge_one)
    ImageView mGameImageOne;
    @BindView(R.id.game_iamge_two)
    ImageView mGameImageTwo;
    @BindView(R.id.subhead_four)
    TextView mSubheadFour;
    @BindView(R.id.title_four)
    TextView title_four;
    @BindView(R.id.bon_soir_time)
    TextView bon_soir_time;
    @BindView(R.id.frame)
    FrameLayout mFrame;
    @BindView(R.id.card3)
    LCardView mCard3;
    @BindView(R.id.cons)
    LinearLayout mCons;
    @BindView(R.id.wan_shan)
    LinearLayout mWanShan;
    @BindView(R.id.havenet)
    FrameLayout mHavenet;
    @BindView(R.id.note_four)
    TextView mNoteFour;
    @BindView(R.id.note_one)
    TextView mNoteOne;
    @BindView(R.id.note_two)
    TextView mNoteTwo;
    @BindView(R.id.note_three)
    TextView mNoteThree;
    @BindView(R.id.tv_baby_one)
    TextView mTvBabyOne;
    @BindView(R.id.tv_baby_two)
    TextView mTvBabyTwo;
    @BindView(R.id.tv_baby_name)
    TextView mTvBabyName;
    @BindView(R.id.tv_baby_age)
    TextView mTvBabyAge;
    @BindView(R.id.huan_baby)
    ImageView mHuanBaby;
    @BindView(R.id.more_one)
    ImageView mMoreOne;
    @BindView(R.id.more_two)
    ImageView mMoreTwo;
    @BindView(R.id.more_three)
    ImageView mMoreThree;
    @BindView(R.id.more_four)
    ImageView mMoreFour;
    //家族社区热议的recyclerView
    @BindView(R.id.rlv)
    RecyclerView mRlv;
    @BindView(R.id.tv_a)
    TextView mTvA;
    @BindView(R.id.tv_b)
    TextView mTvB;
    @BindView(R.id.tv_c)
    TextView mTvC;
    @BindView(R.id.lcard_game_one)
    LCardView mLcardGameOne;
    @BindView(R.id.lcard_game_two)
    LCardView mLcardGameTwo;
    /*  @BindView(R.id.lcard_game_three)
      LCardView mLcardGameThree;*/
    @BindView(R.id.iv_d)
    RoundedImageView mIvD;
    @BindView(R.id.line1)
    LinearLayout mLine1;
    @BindView(R.id.recylerView_song)
    RecyclerView mRecylerViewSong;
    @BindView(R.id.cir_cle)
    CircleImageView mCirCle;
    @BindView(R.id.checkbox_night)
    CheckBox mCheckboxNight;
    @BindView(R.id.bianji)
    ImageView mBianji;
    @BindView(R.id.appraisal)
    LinearLayout mAppraisal;
    @BindView(R.id.lin)
    LinearLayout mLin;
    @BindView(R.id.line2)
    LinearLayout mLine2;
    @BindView(R.id.line3)
    LinearLayout mLine3;
    @BindView(R.id.card2)
    LCardView mCard2;
    @BindView(R.id.sun)
    ImageView mSun;
    @BindView(R.id.sun_line)
    LinearLayout mSunLine;
    @BindView(R.id.today)
    TextView mToday;
    @BindView(R.id.note_line)
    LinearLayout mNoteLine;
    @BindView(R.id.card_as_note)
    LCardView mCardAsNote;
    @BindView(R.id.line6)
    LinearLayout mLine6;
    @BindView(R.id.game_line)
    LinearLayout mGameLine;
    @BindView(R.id.line_late)
    LinearLayout mLineLate;
    @BindView(R.id.name_late)
    TextView mNameLate;
    @BindView(R.id.late_paly)
    FrameLayout mLatePaly;
    @BindView(R.id.card_bon_soir)
    LCardView mCardBonSoir;
    @BindView(R.id.line7)
    LinearLayout mLine7;

    @BindView(R.id.iv_logo)
    CircleImageView mIvLogo;
    @BindView(R.id.description)
    TextView mDescription;
    @BindView(R.id.video_title)
    TextView mVideoTitle;
    @BindView(R.id.age_group)
    LinearLayout mAgeGroup;
    @BindView(R.id.tv_morning)
    TextView mTvMorning;
    @BindView(R.id.tv_late)
    TextView mTvLate;
    @BindView(R.id.slogan)
    TextView mSlogan;
    @BindView(R.id.baby_pic)
    CircleImageView mBabyPic;
    Unbinder unbinder;
    private PopupWindow PopupWindows;
    private View mInflate1;

    private ArrayList<String> bannarList = new ArrayList<>();
    private float height = 540;// 滑动开始变色的高,此高度是由广告轮播或其他首页view高度决定
    private int overallXScroll = 0;
    private int flag_music = 1;
    private int flag_collect = 1;
    private boolean isbo;
    private boolean isboo = false;
    private String[] mediaName = {"普通", "高清", "原画"};
    private String[] url = {"http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4",
            "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4",
            "http://mirror.aarnet.edu.au/pub/TED-talks/911Mothers_2010W-480p.mp4",
            "http://vjs.zencdn.net/v/oceans.mp4"};
    private int i = 0;
    private ArrayList<Bitmap> mList_paly;
    private RlvAdapter_paly mRlvAdapter_paly;
    private boolean isBottomShow = true;
    private ViewGroup mMView;
    private SeekBar mSeek;
    private ImageView mStart;
    public static ImageView mCancle;
    private ImageView mHead;
    private PopupWindow mPopupWindow;
    private PopupWindow mPopupWindow2;
    TimerTaskManager mTimerTask;
    MediaSessionConnection mMediaSessionConnection;
    private List<SongInfo> mSongInfos;
    private TextView mTime;
    private TextView mTitle1;
    private MusicManager mInstance;
    private int mTop;
    private ILelinkServiceManager mLelinkServiceManager;
    private LelinkPlayer mLeLinkPlayer;
    private int page = 1;
    private boolean sboo;
    private String musicId = "";

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            int arg1 = msg.arg1;
            List<LelinkServiceInfo> list = (List<LelinkServiceInfo>) msg.obj;
            if (arg1 == 1) {
                if (mRlvAdapterVideoTv != null) {
                    if (list.size() != 0) {
                        mRlvAdapterVideoTv.addData(list);
                        mLine.setVisibility(View.GONE);
                    }
                } else {
                    if (mLine != null) {
                        mLine.setVisibility(View.VISIBLE);
                    }
                }
            } else {
                //Toast.makeText(getContext(), "搜索失败", Toast.LENGTH_SHORT).show();
            }
        }
    };
    private List<BabyMessageBean.ResultBean> mResult;
    private List<BabyMessageBean.ResultBean> babyList = new ArrayList<>();
    private List<BabyMessageBean.ResultBean> addbabyList = new ArrayList<>();
    private int mA;
    private RlvAdapter_songlist mRlvAdapterSonglist;
    private ArrayList<SongInfo> mSongInfos1;
    private Animation mAnimation;
    private ArrayList<SongInfo> mSongInfos2;
    private boolean mBoo;
    private RlvAdapter_home mRlvAdapterHome;
    private int record = 0;
    private String mMediaUri;
    private String mTitle2;
    private int mIsFree;
    private List<GamehomePageBeans.ResultBean> result5;
    private List<ShortVideoBean.ResultBean.ItemListBean> mItemList3;
    private int mCardType;
    private String mH5_fenLingYangYu;
    private String mH5_yuErBaiKe;
    private RlvAdapter_video_tv mRlvAdapterVideoTv;
    private LinearLayout mLine;
    private static final int REQUEST_CAMERA_PERMISSION = 2;
    private String mName;
    private View mInflate;
    private PopupWindow mPopupWindow1;
    private App mApplication;
    private String mAge;

    public void GbPlay() {
        if (mInstance != null && mPopupWindow != null && sboo == true) {
            //Log.i("yxx", "GbPlay: ");
            //mInstance.pauseMusic();
            //mStart.setImageDrawable(getResources().getDrawable(R.mipmap.music_start));
            //mCancle.setVisibility(View.VISIBLE);
            //mHead.clearAnimation();
            flag_music = 2;
            //mMView.setVisibility(View.GONE);

            mPopupWindow.dismiss();
        }
    }

    public void bao() {
        mPresenter.getDataP1("", DifferentiateEnum.BABYMESSAGELIST, loadinglayout);
        mPresenter.getDataP1("", DifferentiateEnum.SHORTVIDEO, loadinglayout);
        mPresenter.getDataP1("", DifferentiateEnum.ADPOSITION, loadinglayout);
        mPresenter.getDataP1("", DifferentiateEnum.SONG, loadinglayout);
        mPresenter.getDataP1("", DifferentiateEnum.SONGMORN, loadinglayout);
        mPresenter.getDataP1("", DifferentiateEnum.SONGNIGHT, loadinglayout);
        mPresenter.getDataP1("", DifferentiateEnum.HOMEBSS, loadinglayout);
        mPresenter.getDataP1("", DifferentiateEnum.FORMALCURRICULUM, loadinglayout);
        mPresenter.getDataP1("", DifferentiateEnum.GAMEHOMEPAGE, loadinglayout);
        mPresenter.getDataP1("", DifferentiateEnum.USERCARDTYPE, loadinglayout);
        mPresenter.getDataP1("", DifferentiateEnum.BRING, loadinglayout);
        mPresenter.getDataP1("", DifferentiateEnum.HOMEBANNAR, loadinglayout);
        mRela.setVisibility(View.VISIBLE);
    }

    public void KaiPlay() {
        if (mMView != null && mPopupWindow != null && sboo == true) {
            mMView.setVisibility(View.VISIBLE);
            //获取需要在其上方显示的控件的位置信息
            int[] location = new int[2];
            LordActivity.mLine.getLocationOnScreen(location);
            mMView.measure(0, 0);
            int popupHeight = mMView.getMeasuredHeight();
            int popupWidth = mMView.getMeasuredWidth();
            //在控件上方显示
            mPopupWindow.showAtLocation(LordActivity.mLine, Gravity.NO_GRAVITY, (location[0] + LordActivity.mLine.getWidth() / 2) - popupWidth / 2, (location[1] - popupHeight - 20));
        }
    }

    public static HomePageFragment newInstance() {
        HomePageFragment fragment = new HomePageFragment();
        return fragment;
    }

    /**
     * 需要进行检测的权限数组
     */
    protected String[] needPermissions = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.SYSTEM_ALERT_WINDOW
    };

    @Override
    protected int creatrLayoutId() {
        return R.layout.fragment_home_page;
    }

    private DownloadManager mDownloadManager;
    private CompleteReceiver completeReceiver;
    //下载任务
    String serviceString = Context.DOWNLOAD_SERVICE;
    long reference;

    @SuppressLint({"ClickableViewAccessibility", "NewApi"})
    @Override
    protected void initData() {
        ScreenStatusUtil.setNotStatus(getContext(), mRelative);
        /*String avatar = PreUtils.getString("avatar", "");
         */
        mDownloadManager = (DownloadManager) mActivity.getBaseContext().getSystemService(serviceString);
        /** 注册下载监听的广播 **/
        completeReceiver = new CompleteReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(DownloadManager.ACTION_NOTIFICATION_CLICKED);
        filter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        /** register download success broadcast **/
        mContext.registerReceiver(completeReceiver,
                filter);


        if (isNeedCheck) {
            checkPermissions(needPermissions);
        }
        //popwindow();
        initRequest();
        mInstance = MusicManager.getInstance();
        //添加监听
        MusicManager.getInstance().addPlayerEventListener(this);
        mHuanBaby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TimeUtil.isInvalidClick(mHuanBaby, 300))
                    return;
                if (addbabyList != null) {
                    if (addbabyList.size() == 1) {
                        ToastUtils.showShortToast(mContext,"只有一位宝宝信息");
                    } else {
                        mPresenter.getDataP1(addbabyList.get(addbabyList.size() - 1).getBabyId() + "", DifferentiateEnum.SETBABY, loadinglayout);

                        Animation rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                        // 1. fromDegrees ：动画开始时 视图的旋转角度(正数 = 顺时针，负数 = 逆时针)
                        // 2. toDegrees ：动画结束时 视图的旋转角度(正数 = 顺时针，负数 = 逆时针)
                        // 3. pivotXType：旋转轴点的x坐标的模式
                        // 4. pivotXValue：旋转轴点x坐标的相对值
                        // 5. pivotYType：旋转轴点的y坐标的模式
                        // 6. pivotYValue：旋转轴点y坐标的相对值
                        // pivotXType = Animation.ABSOLUTE:旋转轴点的x坐标 =  View左上角的原点 在x方向 加上 pivotXValue数值的点(y方向同理)
                        // pivotXType = Animation.RELATIVE_TO_SELF:旋转轴点的x坐标 = View左上角的原点 在x方向 加上 自身宽度乘上pivotXValue数值的值(y方向同理)
                        // pivotXType = Animation.RELATIVE_TO_PARENT:旋转轴点的x坐标 = View左上角的原点 在x方向 加上 父控件宽度乘上pivotXValue数值的值 (y方向同理)
                        rotateAnimation.setDuration(1000);
                        mHuanBaby.startAnimation(rotateAnimation);
                    }
                }
            }
        });
        initTou();
        mRela = getActivity().findViewById(R.id.rela);
        initList();
        initRecyclerSong();
        //视频下方列表recylerView
        initRecy();
        //家族社区热议recylerView
        initRlv();
        //NestedScrollView滑动逻辑判断
        initScroll();
        initKong();
        pop();
        showPopListTv();
        mNoteOne.setSelected(false);
        mNoteTwo.setSelected(false);
        mNoteThree.setSelected(false);
        mNoteFour.setSelected(false);

    }

    private void initRecyclerSong() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecylerViewSong.setLayoutManager(linearLayoutManager);
        List<SongBean.ResultBean.ItemListBean> resultBeans = new ArrayList<>();
        mRlvAdapterSonglist = new RlvAdapter_songlist(resultBeans, getContext());
        mRecylerViewSong.setAdapter(mRlvAdapterSonglist);

        mRlvAdapterSonglist.setOnClickLisiter(new RlvAdapter_songlist.onClickLisiter() {
            @Override
            public void onClicker(View view, int position, List<SongBean.ResultBean.ItemListBean> mData) {
                mNoteOne.setTextColor(Color.parseColor("#333333"));
                mNoteTwo.setTextColor(Color.parseColor("#333333"));
                mNoteThree.setTextColor(Color.parseColor("#333333"));
                mNoteFour.setTextColor(Color.parseColor("#333333"));
                mNoteOne.setSelected(false);
                mNoteTwo.setSelected(false);
                mNoteThree.setSelected(false);
                mNoteFour.setSelected(false);
                mCheckboxNight.setChecked(false);
                if (mSongInfos.size() != 0) {
                    record = 1;
                    showPopu(mSongInfos, position);
                    PreUtils.putString("music", "radio");
                    isBottomShow = true;
                    mMView.animate().translationY(0);
                    isPlay();
                }
                mRlvAdapterSonglist.addPos(position);
            }
        });
    }

    private void initRequest() {
        mPresenter.getDataP1(null, DifferentiateEnum.USERINFO, loadinglayout);
        mPresenter.getDataP1("", DifferentiateEnum.BABYMESSAGELIST, loadinglayout);
        mPresenter.getDataP1("", DifferentiateEnum.HOMEBANNAR, loadinglayout);
        mPresenter.getDataP1("", DifferentiateEnum.SHORTVIDEO, loadinglayout);
        mPresenter.getDataP1("", DifferentiateEnum.ADPOSITION, loadinglayout);
        mPresenter.getDataP1("", DifferentiateEnum.SONG, loadinglayout);
        mPresenter.getDataP1("", DifferentiateEnum.SONGMORN, loadinglayout);
        mPresenter.getDataP1("", DifferentiateEnum.SONGNIGHT, loadinglayout);
        mPresenter.getDataP1("", DifferentiateEnum.HOMEBSS, loadinglayout);
        mPresenter.getDataP1("", DifferentiateEnum.FORMALCURRICULUM, loadinglayout);
        mPresenter.getDataP1("", DifferentiateEnum.GAMEHOMEPAGE, loadinglayout);
        mPresenter.getDataP1("", DifferentiateEnum.USERCARDTYPE, loadinglayout);
        mPresenter.getDataP1("", DifferentiateEnum.BRING, loadinglayout);
        loadinglayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
            @Override
            public void onReload(View v) {
                mPresenter.getDataP1(null, DifferentiateEnum.USERINFO, loadinglayout);
                mPresenter.getDataP1("", DifferentiateEnum.BABYMESSAGELIST, loadinglayout);
                mPresenter.getDataP1("", DifferentiateEnum.HOMEBANNAR, loadinglayout);
                mPresenter.getDataP1("", DifferentiateEnum.SHORTVIDEO, loadinglayout);
                mPresenter.getDataP1("", DifferentiateEnum.ADPOSITION, loadinglayout);
                mPresenter.getDataP1("", DifferentiateEnum.SONG, loadinglayout);
                mPresenter.getDataP1("", DifferentiateEnum.SONGMORN, loadinglayout);
                mPresenter.getDataP1("", DifferentiateEnum.SONGNIGHT, loadinglayout);
                mPresenter.getDataP1("", DifferentiateEnum.HOMEBSS, loadinglayout);
                mPresenter.getDataP1("", DifferentiateEnum.FORMALCURRICULUM, loadinglayout);
                mPresenter.getDataP1("", DifferentiateEnum.GAMEHOMEPAGE, loadinglayout);
                mPresenter.getDataP1("", DifferentiateEnum.USERCARDTYPE, loadinglayout);
                mPresenter.getDataP1("", DifferentiateEnum.BRING, loadinglayout);
            }
        });
    }

    private void initTou() {
        mLelinkServiceManager = App.getApplication().getILelinkServiceManager();
        mLeLinkPlayer = new LelinkPlayer(getContext());
        mLeLinkPlayer.setConnectListener(connectListener);
        mLelinkServiceManager.setOnBrowseListener(browserListener);
        mLeLinkPlayer.setPlayerListener(playerListener);
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

        }

        /**
         * 暂停
         */
        @Override
        public void onPause() {

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

        }

        /**
         * 播放进度信息回调
         * @param duration 总长度：单位秒
         * @param position 当前进度：单位秒
         */
        @Override
        public void onPositionUpdate(long duration, long position) {

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
            // 设置网络url，支持网络推送，与本地推送二选一
            lelinkPlayerInfo.setUrl(mMediaUri);
            mLeLinkPlayer.setDataSource(lelinkPlayerInfo);
            mLeLinkPlayer.start();
            Toast.makeText(getContext(), "连接成功", Toast.LENGTH_SHORT).show();

        }


        @Override
        public void onDisconnect(LelinkServiceInfo serviceInfo, int what, int extra) {

        }

    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 10) {
            //用户授予了权限
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(mContext, "授予权限成功", Toast.LENGTH_SHORT).show();
            } else {
                //权限被用户拒绝了，但是并没有选择不再提示，也就是说还可以继续申请
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permissions[0])) {
                    Toast.makeText(mContext, "请授予权限", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "没有权限", Toast.LENGTH_SHORT).show();
                }
            }
        }
        if (requestCode == PERMISSON_REQUESTCODE) {
            if (!verifyPermissions(grantResults)) {      //没有授权
                isNeedCheck = false;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 123 && resultCode == 124) {
            mPresenter.getDataP1("", DifferentiateEnum.BABYMESSAGELIST, loadinglayout);
        }

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

    private void initList() {
        mTimerTask = new TimerTaskManager();
        mMediaSessionConnection = MediaSessionConnection.getInstance();

    }

    @SuppressLint("ClickableViewAccessibility")
    private void initKong() {

        mRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitBy2Click();
            }
        });
        mJiaoziPlayer.startButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (mInstance != null) {
                    boolean playing = mInstance.isPlaying();
                    if (playing) {
                        mStart.setImageDrawable(getResources().getDrawable(R.mipmap.music_start));
                        mCancle.setVisibility(View.VISIBLE);
                        mHead.clearAnimation();
                        flag_music = 2;
                        mInstance.pauseMusic();
                    }
                }
                return false;
            }
        });

        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        mRelative.measure(w, h);
        final int he = mRelative.getMeasuredHeight();

        mCons.getViewTreeObserver().addOnWindowFocusChangeListener(new ViewTreeObserver.OnWindowFocusChangeListener() {
            @Override
            public void onWindowFocusChanged(final boolean hasFocus) {
                Log.i("yx===", "onWindowFocusChanged: " + hasFocus);
                String changed = PreUtils.getString("changed", "");
                // do your stuff here
                if ("yes".equals(changed)) {
                    float y = mCons.getTop();
                    mTop = mCard.getTop();
                    mNested.addSua(mTop);
                    height = y - he;
                    PreUtils.putString("changed", "no");
                }
            }
        });

    }


    private void exitBy2Click() {
        Timer tExit = null;
        if (isboo == false) {
            isboo = true; // 准备退出
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isboo = false; // 取消退出
                }
            }, 500); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            mNested.fling(0);
            mNested.smoothScrollTo(0, 0);
        }


    }

    //家族社区热议recylerView
    private void initRlv() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRlv.setLayoutManager(layoutManager);
        List<HomeBBSbean.ResultBean> resultBeans = new ArrayList<>();
        mRlvAdapterHome = new RlvAdapter_home(resultBeans, getContext());
        mRlv.setAdapter(mRlvAdapterHome);

        mRlvAdapterHome.setOnClickLisiterUser(new RlvAdapter_home.onClickLisiterUser() {
            @Override
            public void onClickLisiterUser(View view, int position, List<HomeBBSbean.ResultBean> mData) {
                if (TimeUtil.isInvalidClick(view, 300))
                    return;
                long userId = mData.get(position).getUserId();
                Intent intent1 = new Intent(getContext(), UserCenterActivity.class);
                intent1.putExtra("userId", userId);
                startActivity(intent1);
            }
        });

        mRlvAdapterHome.setOnClickLisiter(new RlvAdapter_home.onClickLisiter() {
            @Override
            public void onClicker(View view, int position, List<HomeBBSbean.ResultBean> mData) {
                long userId = mData.get(position).getUserId();
                long articleId = mData.get(position).getArticleId();
                String nickname = mData.get(position).getNickname();
                String userAvatar = mData.get(position).getUserAvatar();
                Intent intent = new Intent(getContext(), RecDataActivity.class);
                intent.putExtra("biao", "home");
                intent.putExtra("userId", userId);
                intent.putExtra("articleId", articleId);
                intent.putExtra("nickname", nickname);
                intent.putExtra("userAvatar", userAvatar);
                startActivity(intent);
            }
        });
    }

    //NestedScrollView滑动逻辑判断
    private void initScroll() {
        mNested.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                overallXScroll = overallXScroll + (scrollY - oldScrollY);// 累加y值 解决滑动一半y值为0
                int top = mCard.getTop();
                mA = top - overallXScroll;
                mNested.addSua(mA);
                //Log.i("yx", "onScrollChange: " + a);
                // Log.i("yx", "onScrollChange: "+overallXScroll);
                if (overallXScroll <= 0) {  //未滑动时，设置透明度为0
                    mRelative.setBackgroundColor(Color.argb((int) 0, 255, 255, 255));
                    //Glide.with(getContext()).load(R.mipmap.music_top).into(mVideoTu);
                    //mIvLogo.setVisibility(View.GONE);
                    mTitle.setVisibility(View.GONE);


                } else if (overallXScroll > 0 && overallXScroll <= height) { //确定一个渐变区域，背景颜色透明度渐变
                    //设置渐变比例
                    float scale = (float) overallXScroll / height;
                    float alpha = (255 * scale);
                    mRelative.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
                    //Glide.with(getContext()).load(R.mipmap.music_top).into(mVideoTu);
                    //mIvLogo.setVisibility(View.GONE);
                    mTitle.setVisibility(View.GONE);
                } else {//超过渐变区域，透明度都是满的
                    //mNested.addSua(true);
                    mRelative.setBackgroundColor(Color.argb((int) 255, 255, 255, 255));
                    //Glide.with(getContext()).load(R.mipmap.music_max).into(mVideoTu);
                    //mIvLogo.setVisibility(View.VISIBLE);
                    mTitle.setVisibility(View.VISIBLE);
                    mTitle.setText("天才家族，让每个儿童接受早期教育 !");
                    //mNested.addSua(true);
                }
                if (mMView != null) {
                    if (scrollY - oldScrollY > 0 && isBottomShow) {//下移隐藏
                        isBottomShow = false;
                        mMView.animate().translationY(mMView.getHeight());
                    } else if (scrollY - oldScrollY < 0 && !isBottomShow) {//上移出现
                        isBottomShow = true;
                        mMView.animate().translationY(0);
                    }
                }

                boolean b = mCard3.getLocalVisibleRect(new Rect());

                if (b == false) {
                    if (MyJZVideoPlayerStandard.b == 1) {
                        JZVideoPlayer.quitFullscreenOrTinyWindow();
                    }
                    MyJZVideoPlayerStandard.b = 0;
                }
            }
        });
    }


    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onPing(MessageWrap isbo) {
        if (isbo.isIsbo()) {
            mPopupWindow2.showAtLocation(mAgeGroup, Gravity.BOTTOM, 0, 0);
        }
    }

    private void showPopListTv() {
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.list_tv_layout, null);
        mPopupWindow2 = new PopupWindow(inflate, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mPopupWindow2.setFocusable(true);// 取得焦点
        mPopupWindow2.setAnimationStyle(R.style.popwin_anim_style);
        mPopupWindow2.setClippingEnabled(false);
        //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
        mPopupWindow2.setBackgroundDrawable(new BitmapDrawable());
        //点击外部消失
        mPopupWindow2.setOutsideTouchable(true);
        //设置可以点击
        mPopupWindow2.setTouchable(true);

        RecyclerView recylerView = inflate.findViewById(R.id.recylerView);
        ArrayList<LelinkServiceInfo> list = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recylerView.setLayoutManager(linearLayoutManager);
        mRlvAdapterVideoTv = new RlvAdapter_video_tv(list);
        recylerView.setAdapter(mRlvAdapterVideoTv);
        ImageView refreshIv = inflate.findViewById(R.id.refresh_iv);
        refreshIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
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
        mLelinkServiceManager.browse(ILelinkServiceManager.TYPE_ALL);


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
                mPopupWindow2.dismiss();
            }
        });
        linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CameraPermissionCompat.checkCameraPermission(getContext(), new CameraPermissionCompat.OnCameraPermissionListener() {

                    @Override
                    public void onGrantResult(boolean granted) {
                        if (granted) {
                            // 允许，打开二维码
                            // 允许，打开二维码
                            Intent intent = new Intent(getContext(), CaptureActivity.class);
                            startActivityForResult(intent, REQUEST_CAMERA_PERMISSION);
                        } else {
                            // 若没有授权，会弹出一个对话框（这个对话框是系统的，开发者不能自己定制），用户选择是否授权应用使用系统权限
                        }
                    }


                });
            }
        });
    }

    //视频下方列表recylerView
    private void initRecy() {
        mJZMediaSystem = new MyJZMediaSystem();
        mIJKMediaSystem = new MyIJKMediaSystem();
        LinearLayoutManager layoutManager_paly = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        mRecylerView.setLayoutManager(layoutManager_paly);
        mRecylerView.setNestedScrollingEnabled(false);
        mRecylerView.setHasFixedSize(true);
        List<FormalCurriculumBean.ResultBean.ChapterListBean> chapterListBeans = new ArrayList<>();
        mRlvAdapter_paly = new RlvAdapter_paly(mContext, chapterListBeans);
        mRecylerView.setAdapter(mRlvAdapter_paly);
        mIvPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (TimeUtil.isInvalidClick(mIvPlay, 300))
                    return;*/
                if (mIsFree == 0) {
                    ToastUtils.showShortToast(getActivity(), "还未购买课程");
                } else {
                    mJiaoziPlayer.startVideo();
                    mRela.setVisibility(View.GONE);
                }
            }
        });

        mRlvAdapter_paly.setOnClickLisiterCourse(new RlvAdapter_paly.onClickLisiterCourse() {
            @Override
            public void onClickerCourse(View view, int position) {
                if (TimeUtil.isInvalidClick(view, 300))
                    return;
                if (mCardType == -1) {
                    Intent intent = new Intent(getContext(), TryListenerActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getContext(), EarlyActivity.class);
                    startActivity(intent);
                }
            }
        });

        mRlvAdapter_paly.setOnClickLisiterVideo(new RlvAdapter_paly.onClickLisiterVideo() {
            @Override
            public void onClickVideo(View view, int position) {
                if (TimeUtil.isInvalidClick(view, 300))
                    return;
                if (mCardType == -1) {
                    Intent intent = new Intent(getContext(), TryListenerActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getContext(), EarlyActivity.class);
                    startActivity(intent);
                }
            }
        });
        mRela.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        mRlvAdapter_paly.setonClickLiniser(new RlvAdapter_paly.onClickLiniser() {
            @Override
            public void onClick(View view, int position, List<FormalCurriculumBean.ResultBean.ChapterListBean> data, NiceImageView mIv) {
                if (TimeUtil.isInvalidClick(view, 300))
                    return;
                i = position;
                if (MyJZVideoPlayerStandard.b == 1) {
                    JZVideoPlayer.quitFullscreenOrTinyWindow();
                }
                mMediaUri = data.get(position).getContentsList().get(0).getMediaUri();
                mTitle2 = data.get(position).getContentsList().get(0).getTitle();
                mVideoTitle.setText(mTitle2);
                mIsFree = data.get(position).getContentsList().get(0).getIsFree();
                initPlayerUrl(mMediaUri);
                MyJZVideoPlayerStandard.b = 0;
                mRela.setVisibility(View.VISIBLE);
                mRlvAdapter_paly.setSty(position);
                String picUri = data.get(position).getContentsList().get(0).getPicUri();

                WindowManager wm = (WindowManager) getContext()
                        .getSystemService(Context.WINDOW_SERVICE);
                int width;
                int ivHeight = DisplayUtil.dp2px(getContext(),100);//给高度设置一个默认值100dp
                try {
                    if (wm != null) {
                        width = wm.getDefaultDisplay().getWidth(); //获取屏幕宽度
                        Uri uri = Uri.parse(picUri);
                        String size = uri.getQueryParameter("size"); //Url中size的格式为：size=1080x420
                        if (!TextUtils.isEmpty(size) && size.contains("x")) {
                            String[] point = size.split("x");
                            if (TextUtils.isDigitsOnly(point[0]) && TextUtils.isDigitsOnly(point[1])) {
                                if (width > 0) {
                                    ivHeight = (int) ((Float.parseFloat(point[1]) / Float.parseFloat(point[0])) * width);//利用宽高比计算出ImageView的合适高度
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ivHeight);//可根据具体的父布局选择不同的内部类LayoutParams
                mJiaoziPlayer.thumbImageView.setLayoutParams(params);

//                Glide.with(mContext).load().into(mJiaoziPlayer.thumbImageView);
            }
        });
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
            mJiaoziPlayer.setUp(objects, 0, JZVideoPlayer.SCREEN_WINDOW_NORMAL, "");
        }
    }

    /**
     * 设置屏幕方向
     */
    private void initPlayer() {
        JZVideoPlayer.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
        JZVideoPlayer.NORMAL_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    }

    //系统播放器引擎
    MyJZMediaSystem mJZMediaSystem;
    MyIJKMediaSystem mIJKMediaSystem;

    @Override
    public void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
        JZVideoPlayer.setMediaInterface(mIJKMediaSystem);
        JZVideoPlayer.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_SENSOR;
        JZVideoPlayer.NORMAL_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    }

    @Override
    public void onResume() {
        super.onResume();
        JZVideoPlayer.setMediaInterface(mIJKMediaSystem);
        initPlayer();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_BACK) {
                    if (JZVideoPlayer.backPress()) {

                    }
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("yx----", "onStart: " + mA);
        mNested.addSua(mA);
        //连接音频服务
        mMediaSessionConnection.connect();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        //断开音频服务
        // mMediaSessionConnection.disconnect();
        EventBus.getDefault().unregister(this);
    }


    /**
     * * 倍速切换
     */
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onMessageEventPostSpeed(SpeedEvent event) {
        mJZMediaSystem.setSpeeding(event.getSpeed());
        mIJKMediaSystem.setSpeeding(event.getSpeed());
        Toast.makeText(mContext, "正在切换倍速:" + event.getSpeed(), Toast.LENGTH_LONG).show();
    }

    /**
     * 下载
     */
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onMessageEventPostDetail(DownloadEvent event) {
        Toast.makeText(getContext(), "下载中", Toast.LENGTH_SHORT).show();

        Uri uri = Uri.parse(mMediaUri);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        //通知栏的标题
        request.setTitle(mTitle2 + "视频下载");
        //显示通知栏的说明
        request.setDescription(mTitle2);
        request.setVisibleInDownloadsUi(true);
        //下载到那个文件夹下，以及命名
        request.setDestinationInExternalFilesDir(mActivity.getApplicationContext(), Environment.DIRECTORY_DOWNLOADS, mTitle2 + ".mp4");
        //下载的唯一标识，可以用这个标识来控制这个下载的任务enqueue（）开始执行这个任务
        reference = mDownloadManager.enqueue(request);
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
            }
            //点击通知栏，取消下载任务
            if (action.equals(DownloadManager.ACTION_NOTIFICATION_CLICKED)) {
                mDownloadManager.remove((Long) reference);
            }

        }
    }

    @SuppressLint("ResourceAsColor")
    @OnClick({R.id.short_video_one, R.id.short_video_two, R.id.short_video_three, R.id.note_one, R.id.note_two, R.id.note_three, R.id.note_four
            , R.id.bring, R.id.online, R.id.offline, R.id.family, R.id.subhead_one, R.id.subhead_two, R.id.subhead_three, R.id.subhead_four
            , R.id.bianji, R.id.more_one, R.id.more_two, R.id.more_three, R.id.more_four, R.id.lcard_game_one, R.id.lcard_game_two
            , R.id.appraisal, R.id.age_group,R.id.more_very,R.id.wan_an})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.short_video_one:
                if (TimeUtil.isInvalidClick(mShortVideoOne, 300))
                    return;
                jumpIntent(0);
                break;
            case R.id.short_video_two:
                if (TimeUtil.isInvalidClick(mShortVideoTwo, 300))
                    return;
                jumpIntent(1);
                break;
            case R.id.short_video_three:
                if (TimeUtil.isInvalidClick(mShortVideoThree, 300))
                    return;
                jumpIntent(2);
                break;
            case R.id.bring:
                if (TimeUtil.isInvalidClick(mBring, 300))
                    return;
                if (mH5_yuErBaiKe != null) {
                    if (!"".equals(mH5_yuErBaiKe)) {
                        Intent intent = new Intent(getContext(), WebActivity.class);
                        intent.putExtra("target", mH5_yuErBaiKe);
                        intent.putExtra("biao", "home");
                        startActivity(intent);
                    }
                }
                //intoActivity(BringActivity.class);
                break;
            case R.id.age_group:
                if (TimeUtil.isInvalidClick(mAgeGroup, 300))
                    return;
                if (mH5_fenLingYangYu != null) {
                    if (!"".equals(mH5_fenLingYangYu)) {
                        Intent intent = new Intent(getContext(), WebActivity.class);
                        intent.putExtra("target", mH5_fenLingYangYu);
                        intent.putExtra("biao", "home");
                        startActivity(intent);
                    }
                }
                break;
            case R.id.online:
                if (TimeUtil.isInvalidClick(mOnline, 300))
                    return;
                //intoActivity(OnlineActivity.class);
                if (mCollegeFragment == null) {
                    mCollegeFragment = CollegeFragment.newInstance();
                }
                changePageSelect(1);
                switchFragment(fragment_now, mCollegeFragment);
                break;
            case R.id.offline:
                if (TimeUtil.isInvalidClick(mOffline, 300))
                    return;
                intoActivity(OfflineActivity.class);
                break;
            case R.id.family:
                if (TimeUtil.isInvalidClick(mFamily, 300))
                    return;
                if (mParentingEncyclopediaFragment == null) {
                    mParentingEncyclopediaFragment = ParentingEncyclopediaFragment.newInstance();
                }
                changePageSelect(3);
                switchFragment(fragment_now, mParentingEncyclopediaFragment);
                isPlay();
                GbPlay();
                LordActivity.isbo = true;
                break;
            case R.id.subhead_one:
            case R.id.more_one:
                if (TimeUtil.isInvalidClick(mSubheadOne, 300))
                    return;
                intoActivity(SubheadOneActivity.class);
                break;
            case R.id.subhead_two:
            case R.id.more_two:
                if (TimeUtil.isInvalidClick(mShortVideoTwo, 300))
                    return;
                intoActivity(SubheadTwoActivity.class);
                break;
            case R.id.subhead_three:
            case R.id.more_three:
                if (TimeUtil.isInvalidClick(mSubheadThree, 300))
                    return;
                if (mCardType == -1) {
                    Intent intent = new Intent(getContext(), TryListenerActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getContext(), EarlyActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.subhead_four:
            case R.id.more_four:
                if (TimeUtil.isInvalidClick(mSubheadFour, 300))
                    return;
                if (mShoppingMallFragment == null) {
                    mShoppingMallFragment = ShoppingMallFragment.newInstance();
                }
                changePageSelect(2);
                switchFragment(fragment_now, mShoppingMallFragment);
                isPlay();
                GbPlay();
                LordActivity.isbo = true;
                break;
            /*case R.id.video_tu:
                if (TimeUtil.isInvalidClick(mVideoTu, 300))
                    return;
                //intoActivity(VideoListActivity.class);
                WechatShareTool.shareText("测试。。。", true);
                break;*/
            case R.id.bianji:
                if (TimeUtil.isInvalidClick(mBianji, 300))
                    return;
                Intent intent = new Intent(getContext(), BaoActivity.class);
                intent.putExtra("biao", "home");
                startActivityForResult(intent, 123);
                break;
            case R.id.lcard_game_one:
                if (TimeUtil.isInvalidClick(mLcardGameOne, 300))
                    return;
                initAct(0, result5);
                break;
            case R.id.lcard_game_two:
                if (TimeUtil.isInvalidClick(mLcardGameTwo, 300))
                    return;
                initAct(1, result5);
                break;
            /*case R.id.lcard_game_three:
                if (TimeUtil.isInvalidClick(mLcardGameThree, 300))
                    return;
                initAct(2);
                break;*/
            case R.id.note_one:
                if (mSongInfos1.size() != 0 && mSongInfos1.size() >= 1) {
                    record = 2;
                    showPopu(mSongInfos1, 0);
                    mNoteOne.setTextColor(Color.parseColor("#00DEFF"));
                    mNoteTwo.setTextColor(Color.parseColor("#333333"));
                    mNoteThree.setTextColor(Color.parseColor("#333333"));
                    mNoteFour.setTextColor(Color.parseColor("#333333"));
                    mCheckboxNight.setChecked(false);
                    PreUtils.putString("music", "morn");
                    isBottomShow = true;
                    mMView.animate().translationY(0);
                    mNoteOne.setSelected(true);
                    mNoteTwo.setSelected(false);
                    mNoteThree.setSelected(false);
                    mNoteFour.setSelected(false);
                    mRlvAdapterSonglist.addPos(4);
                }
                break;
            case R.id.note_two:
                if (mSongInfos1.size() != 0 && mSongInfos1.size() >= 2) {
                    record = 2;
                    showPopu(mSongInfos1, 1);
                    mNoteOne.setTextColor(Color.parseColor("#333333"));
                    mNoteTwo.setTextColor(Color.parseColor("#00DEFF"));
                    mNoteThree.setTextColor(Color.parseColor("#333333"));
                    mNoteFour.setTextColor(Color.parseColor("#333333"));
                    mCheckboxNight.setChecked(false);
                    PreUtils.putString("music", "morn");
                    isBottomShow = true;
                    mMView.animate().translationY(0);
                    mNoteTwo.setSelected(true);
                    mNoteOne.setSelected(false);
                    mNoteThree.setSelected(false);
                    mNoteFour.setSelected(false);
                    mRlvAdapterSonglist.addPos(4);
                }
                break;
            case R.id.note_three:
                if (mSongInfos1.size() != 0 && mSongInfos1.size() >= 3) {
                    showPopu(mSongInfos1, 2);
                    record = 2;
                    mNoteOne.setTextColor(Color.parseColor("#333333"));
                    mNoteTwo.setTextColor(Color.parseColor("#333333"));
                    mNoteThree.setTextColor(Color.parseColor("#00DEFF"));
                    mNoteFour.setTextColor(Color.parseColor("#333333"));
                    mCheckboxNight.setChecked(false);
                    PreUtils.putString("music", "morn");
                    isBottomShow = true;
                    mMView.animate().translationY(0);
                    mNoteThree.setSelected(true);
                    mNoteTwo.setSelected(false);
                    mNoteOne.setSelected(false);
                    mNoteFour.setSelected(false);
                    mRlvAdapterSonglist.addPos(4);
                }
                break;
            case R.id.note_four:
                if (mSongInfos1.size() != 0 && mSongInfos1.size() >= 4) {
                    showPopu(mSongInfos1, 3);
                    record = 2;
                    mNoteOne.setTextColor(Color.parseColor("#333333"));
                    mNoteTwo.setTextColor(Color.parseColor("#333333"));
                    mNoteThree.setTextColor(Color.parseColor("#333333"));
                    mNoteFour.setTextColor(Color.parseColor("#00DEFF"));
                    mCheckboxNight.setChecked(false);
                    PreUtils.putString("music", "morn");
                    isBottomShow = true;
                    mMView.animate().translationY(0);
                    mNoteFour.setSelected(true);
                    mNoteTwo.setSelected(false);
                    mNoteThree.setSelected(false);
                    mNoteOne.setSelected(false);
                    mRlvAdapterSonglist.addPos(4);
                }
                break;
            case R.id.appraisal:
                if (TimeUtil.isInvalidClick(mAppraisal, 300))
                    return;
                if (mResult.size() != 0) {
                    Intent intent1 = new Intent(getContext(), GuidePageActivity.class);
                    intent1.putExtra("data", (Serializable) mResult);
                    startActivity(intent1);
                } else {
                    WindowManager.LayoutParams attributes = getActivity().getWindow().getAttributes();
                    attributes.alpha = 0.5f;
                    getActivity().getWindow().setAttributes(attributes);
                    showPop();
                }
                break;
            case R.id.more_very:
                Intent intent1 = new Intent(getActivity(), ZaoAnActivity.class);
                intent1.putExtra("name",mName);
                startActivity(intent1);
                break;
            case R.id.wan_an:
                Intent intent2 = new Intent(getActivity(), WanAnActivity.class);
                intent2.putExtra("name",mName);
                startActivity(intent2);
                break;
        }
    }

    private void showPop() {
        mInflate = LayoutInflater.from(getContext()).inflate(R.layout.layout_pop_addbaby, null);
        mPopupWindow1 = new PopupWindow(mInflate, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mPopupWindow1.setFocusable(true);// 取得焦点

        mPopupWindow1.setClippingEnabled(false);
        //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
        mPopupWindow1.setBackgroundDrawable(new BitmapDrawable());
        //点击外部消失
        mPopupWindow1.setOutsideTouchable(true);
        //设置可以点击
        mPopupWindow1.setTouchable(true);
        //进入退出的动画，指定刚才定义的style
        mPopupWindow1.setAnimationStyle(R.style.popwin_anim_style);
        mInflate.findViewById(R.id.go_ons).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), BaoActivity.class);
                startActivity(intent);
                mPopupWindow1.dismiss();
            }
        });
        mInflate.findViewById(R.id.guan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindow1.dismiss();
            }
        });
        mPopupWindow1.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams attributes = getActivity().getWindow().getAttributes();
                attributes.alpha = 1;
                getActivity().getWindow().setAttributes(attributes);
            }
        });
        mPopupWindow1.showAtLocation(mInflate, Gravity.CENTER, 0, 0);
    }

    public void initAct(int i, List<GamehomePageBeans.ResultBean> result5) {
        Intent intent = new Intent(getContext(), WebActivity.class);
        String url = result5.get(0).getUrl();
        String title = result5.get(0).getTitle();
        String url1 = result5.get(1).getUrl();
        String title1 = result5.get(1).getTitle();
        Log.i("yx789", "initAct: " + url);
        if (i == 0) {
            intent.putExtra("target", url);
            intent.putExtra("title", title);
            intent.putExtra("biao", "home");
        } else if (i == 1) {
            intent.putExtra("target", url1);
            intent.putExtra("title", title1);
            intent.putExtra("biao", "home");
        }
        startActivity(intent);
    }

    /**
     * 判断是否需要检测，防止不停的弹框
     */
    private boolean isNeedCheck = true;
    private static final int PERMISSON_REQUESTCODE = 0;

    private List<String> findDeniedPermissions(String[] permissions) {
        List<String> needRequestPermissonList = new ArrayList<String>();
        //for (循环变量类型 循环变量名称 : 要被遍历的对象)
        for (String perm : permissions) {
            if (ContextCompat.checkSelfPermission(getContext(),
                    perm) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.shouldShowRequestPermissionRationale(
                    mActivity, perm)) {
                needRequestPermissonList.add(perm);
            }
        }
        return needRequestPermissonList;
    }

    /**
     * 检查权限
     *
     * @param
     * @since 2.5.0
     */
    private void checkPermissions(String... permissions) {
        //获取权限列表
        List<String> needRequestPermissonList = findDeniedPermissions(permissions);
        if (null != needRequestPermissonList
                && needRequestPermissonList.size() > 0) {
            //list.toarray将集合转化为数组
            ActivityCompat.requestPermissions(mActivity,
                    needRequestPermissonList.toArray(new String[needRequestPermissonList.size()]),
                    PERMISSON_REQUESTCODE);
        }
    }

    /**
     * 检测是否说有的权限都已经授权
     *
     * @param grantResults
     * @return
     * @since 2.5.0
     */
    private boolean verifyPermissions(int[] grantResults) {
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }


    /**
     * 隐藏显示fragment
     *
     * @param from 需要隐藏的fragment
     * @param to   需要显示的fragment
     */
    public void switchFragment(Fragment from, Fragment to) {
        if (to == null)
            return;
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        if (!to.isAdded()) {
            if (from == null) {
                transaction.add(R.id.fragment_group, to).show(to).commit();
            } else {
                // 隐藏当前的fragment，add下一个fragment到Activity中并显示
                transaction.hide(from).add(R.id.fragment_group, to).show(to).commitAllowingStateLoss();
            }
        } else {
            // 隐藏当前的fragment，显示下一个
            transaction.hide(from).show(to).commit();
        }
        fragment_now = to;
    }

    private void isPlay() {
        if (MyJZVideoPlayerStandard.b == 1) {
            JZVideoPlayer.quitFullscreenOrTinyWindow();
        }
        MyJZVideoPlayerStandard.b = 0;
    }

    private void pop() {
        LayoutInflater mLayoutInflater = (LayoutInflater) getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
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
       /* collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag_collect == 1) {
                    collect.setImageDrawable(getResources().getDrawable(R.mipmap.collect_true));
                    Toast.makeText(mContext, "收藏成功", Toast.LENGTH_SHORT).show();
                    flag_collect = 2;
                } else if (flag_collect == 2) {
                    collect.setImageDrawable(getResources().getDrawable(R.mipmap.collect_not));
                    Toast.makeText(mContext, "取消收藏", Toast.LENGTH_SHORT).show();
                    flag_collect = 1;
                }
            }
        });*/
        mStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String music = PreUtils.getString("music", "");
                if (flag_music == 1) {
                    if ("night".equals(music)) {
                        mCheckboxNight.setChecked(false);
                    }
                    mStart.setImageDrawable(getResources().getDrawable(R.mipmap.music_start));
                    mCancle.setVisibility(View.VISIBLE);
                    mHead.clearAnimation();
                    flag_music = 2;
                    mInstance.pauseMusic();
                } else if (flag_music == 2) {
                    if ("night".equals(music)) {
                        mCheckboxNight.setChecked(true);
                    }
                    mStart.setImageDrawable(getResources().getDrawable(R.mipmap.music_pause));
                    mCancle.setVisibility(View.GONE);
                    flag_music = 1;
                    mHead.startAnimation(mAnimation);
                    mInstance.playMusic();
                    isPlay();
                }

            }
        });
        //进度更新
        mTimerTask.setUpdateProgressTask(() -> {
            long position = MusicManager.getInstance().getPlayingPosition();
            long duration = MusicManager.getInstance().getDuration();
            long buffered = MusicManager.getInstance().getBufferedPosition();
            if (mSeek.getMax() != duration) {
                mSeek.setMax((int) duration);
            }
            mSeek.setProgress((int) position);
            //mSeek.setSecondaryProgress((int) buffered);
            mTime.setText(formatMusicTime(duration - position));
        });
        mCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRlvAdapterSonglist.addPos(4);
                sboo = false;
                String music = PreUtils.getString("music", "");
                Log.i("yx456", "onClick: " + music);
                mNoteOne.setTextColor(Color.parseColor("#333333"));
                mNoteTwo.setTextColor(Color.parseColor("#333333"));
                mNoteThree.setTextColor(Color.parseColor("#333333"));
                mNoteFour.setTextColor(Color.parseColor("#333333"));
                mNoteOne.setSelected(false);
                mNoteTwo.setSelected(false);
                mNoteThree.setSelected(false);
                mNoteFour.setSelected(false);
                PreUtils.putString("music", "");
                mCheckboxNight.setChecked(false);
                mBoo = true;
                mPopupWindow.dismiss();
                mInstance.stopMusic();
            }
        });
    }

    private void showPopu(List<SongInfo> songInfos, int pos) {
        flag_music = 1;
        mInstance.playMusic(songInfos, pos);
        Glide.with(getContext()).load(songInfos.get(pos).getSongCover()).into(mHead);
        sboo = true;
        //获取需要在其上方显示的控件的位置信息
        int[] location = new int[2];
        LordActivity.mLine.getLocationOnScreen(location);
        mMView.measure(0, 0);
        int popupHeight = mMView.getMeasuredHeight();
        int popupWidth = mMView.getMeasuredWidth();
        //在控件上方显示
        mPopupWindow.showAtLocation(LordActivity.mLine, Gravity.NO_GRAVITY, (location[0] + LordActivity.mLine.getWidth() / 2) - popupWidth / 2, (location[1] - popupHeight - 20));

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
                Intent intent = new Intent(getContext(), KnowlegePlayActivity.class);
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
                Intent intent = new Intent(getContext(), KnowlegePlayActivity.class);
                SongInfo nowPlayingSongInfo = mInstance.getNowPlayingSongInfo();
                String songName = nowPlayingSongInfo.getSongName();
                intent.putExtra("songName", songName);
                intent.putExtra("biao", "home");
                startActivity(intent);
            }
        });
    }

    public void intoActivity(Class mClass) {
        Intent intent = new Intent(getContext(), mClass);
        startActivity(intent);
    }

    public void jumpIntent(int i) {
        if (mItemList3.size() > i) {
            Intent intent = new Intent(getContext(), ShortVideoActivity.class);
            intent.putExtra("position", i);
            intent.putExtra("biao", "home");
            intent.putExtra("data", (Serializable) mItemList3);
            startActivity(intent);
        }
    }


    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }

    @Override
    public void onDestroyView() {
        if (completeReceiver != null) {
            mContext.unregisterReceiver(completeReceiver);
        }
        /*if (mConnectionReceiver != null) {
            mContext.unregisterReceiver(mConnectionReceiver);
        }*/
        MusicManager.getInstance().stopMusic();
        //回收资源
        MusicManager.getInstance().removePlayerEventListener(this);
        mTimerTask.removeUpdateProgressTask();
        Log.i("onDestroyView", "onDestroyView: ");
        super.onDestroyView();
    }

    @Override
    public void showError(String error) {
        //Toast.makeText(mContext, error, Toast.LENGTH_SHORT).show();
        //mHavenet.setVisibility(View.GONE);
    }

    private FileMusicUtils mFileMusicUtils;

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        /*boolean networkConnected = SystemUtil.isNetworkConnected();
        if (networkConnected) {
            mHavenet.setVisibility(View.VISIBLE);
        } else {
            mHavenet.setVisibility(View.GONE);
        }*/
        switch (differentiateEnum) {
            case BABYMESSAGELIST:
                BabyMessageBean babyMessageBean = (BabyMessageBean) o;
                mResult = babyMessageBean.getResult();
                for (int j = 0; j < mResult.size(); j++) {
                    int isDefault = mResult.get(j).getIsDefault();
                    if (isDefault == 1) {
                        PreUtils.putString("babyBirthday", mResult.get(j).getBirthday());
                        break;
                    }
                }
                if (babyList != null) {
                    babyList.clear();
                }
                if (addbabyList != null) {
                    addbabyList.clear();
                }
                if (mResult.size() >= 2) {
                    for (int j = 0; j < 2; j++) {
                        babyList.add(mResult.get(j));
                        addbabyList.add(mResult.get(j));
                    }
                } else {
                    babyList.addAll(mResult);
                    addbabyList.addAll(mResult);
                }
                if (mResult.size() != 0) {
                    String nowTime = TimeUtil.getNowTime();
                    mTvBabyOne.setVisibility(View.GONE);
                    mTvBabyTwo.setVisibility(View.GONE);
                    mTvBabyName.setVisibility(View.VISIBLE);
                    mTvBabyAge.setVisibility(View.VISIBLE);
                    mHuanBaby.setVisibility(View.VISIBLE);
                    mName = mResult.get(0).getName();
                    PreUtils.putString("babyName", mName);
                    mTvBabyName.setText(Html.fromHtml(mName));
                    mTvLate.setText("让和缓的音乐伴随" + mName + "进入梦乡");
                    mTvMorning.setText("用音乐开启" + mName + "的美好一天");
                    mSlogan.setText("为" + mName + "定制的今日早教计划");
                    mAge = TimeUtil.getAge(mResult.get(0).getBirthday(), nowTime);
                    mApplication = (App) getActivity().getApplication();
                    String birthday = mResult.get(0).getBirthday();
                    mApplication.setBirtoday(birthday);
                    mTvBabyAge.setText(mAge);
                    PreUtils.putString("bage", mAge);
                    Log.i("homeAvatar", "show: "+mResult.get(0).getAvatar());
                    Glide.with(getContext()).load(mResult.get(0).getAvatar()).into(mBabyPic);
                } else {
                    mTvBabyOne.setVisibility(View.VISIBLE);
                    mTvBabyTwo.setVisibility(View.VISIBLE);
                    mTvBabyName.setVisibility(View.GONE);
                    mTvBabyAge.setVisibility(View.GONE);
                    mHuanBaby.setVisibility(View.GONE);
                    mTvLate.setText("让和缓的音乐伴随宝宝进入梦乡");
                    mTvMorning.setText("用音乐开启宝宝的美好一天");
                    mSlogan.setText("为宝宝定制的今日早教计划");
                    PreUtils.putString("babyName", "陌陌");
                    mBabyPic.setImageResource(R.mipmap.baby_pic);
                }
                break;
            case SETBABY:
                SetBabyBean setBabyBean = (SetBabyBean) o;
                Log.i("yx", "show: " + setBabyBean.getResult());

                if (addbabyList != null) {
                    addbabyList.clear();
                }
                for (int j = 0; j < babyList.size(); j++) {
                    if (j == 0) {
                        addbabyList.add(babyList.get(babyList.size() - 1));
                    } else {
                        addbabyList.add(babyList.get(j - 1));
                    }
                }
                if (babyList != null) {
                    babyList.clear();
                }
                babyList.addAll(addbabyList);
                if (addbabyList != null) {
                    String nowTime = TimeUtil.getNowTime();
                    mTvBabyOne.setVisibility(View.GONE);
                    mTvBabyTwo.setVisibility(View.GONE);
                    mTvBabyName.setVisibility(View.VISIBLE);
                    mTvBabyAge.setVisibility(View.VISIBLE);
                    mHuanBaby.setVisibility(View.VISIBLE);
                    String name = addbabyList.get(0).getName();
                    mTvBabyName.setText(Html.fromHtml(name));
                    mAge= TimeUtil.getAge(addbabyList.get(0).getBirthday(), nowTime);
                    mTvBabyAge.setText(mAge);
                    PreUtils.putString("bage", mAge);
                    mTvLate.setText("让和缓的音乐伴随" + name + "进入梦乡");
                    mTvMorning.setText("用音乐开启" + name + "的美好一天");
                    mSlogan.setText("为" + name + "定制的今日早教计划");
                    if(!addbabyList.get(0).getAvatar().equals("")){
                        Glide.with(getContext()).load(addbabyList.get(0).getAvatar()).into(mBabyPic);
                    }else {
                        mBabyPic.setImageResource(R.mipmap.baby_pic);
                    }
                }
                break;
            case HOMEBANNAR:
                HomeBannerBean homeBannerBean = (HomeBannerBean) o;
                List<HomeBannerBean.ResultBean> result = homeBannerBean.getResult();
                if (bannarList != null) {
                    bannarList.clear();
                }
                for (int j = 0; j < result.size(); j++) {
                    bannarList.add(result.get(j).getPicUri());
                }

                mBanner.setBannerStyle(BannerConfig.NOT_INDICATOR);
                mBanner.setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {
                        String route = result.get(position).getRoute();
                        String target = result.get(position).getTarget();
                        if ("STUDY_COURSE".equals(route)) {
                            Intent intent = new Intent(getContext(), ClassListActivity.class);
                            intent.putExtra("target", target);
                            startActivity(intent);
                        } else if ("MALL_SKU".equals(route)) {
                            Intent intent = new Intent(getContext(), ShopActivity.class);
                            intent.putExtra("target", target);
                            startActivity(intent);
                        } else if ("WEB".equals(route)) {
                            Intent intent = new Intent(getContext(), WebActivity.class);
                            intent.putExtra("target", target);
                            startActivity(intent);
                        }
                    }
                }).setImages(bannarList).setImageLoader(new ImageLoader() {
                    @Override
                    public void displayImage(Context context, Object path, ImageView imageView) {
                        Glide.with(context).load(path).into(imageView);
                    }
                }).start();
                break;
            case SHORTVIDEO:
                ShortVideoBean shortVideoBean = (ShortVideoBean) o;
                ShortVideoBean.ResultBean result1 = shortVideoBean.getResult();
                mItemList3 = result1.getItemList();
                if(mItemList3.size()==1){
                    Glide.with(getContext()).load(result1.getItemList().get(0).getPicUri()).into(mIvA);
                    mTvA.setText(result1.getItemList().get(0).getDuration() + "秒");
                }else if(mItemList3.size() == 2){
                    Glide.with(getContext()).load(result1.getItemList().get(0).getPicUri()).into(mIvA);
                    Glide.with(getContext()).load(result1.getItemList().get(1).getPicUri()).into(mIvB);
                    mTvA.setText(result1.getItemList().get(0).getDuration() + "秒");
                    mTvB.setText(result1.getItemList().get(1).getDuration() + "秒");
                }else if (mItemList3.size() == 3) {
                    Glide.with(getContext()).load(result1.getItemList().get(0).getPicUri()).into(mIvA);
                    Glide.with(getContext()).load(result1.getItemList().get(1).getPicUri()).into(mIvB);
                    Glide.with(getContext()).load(result1.getItemList().get(2).getPicUri()).into(mIvC);
                    mTvA.setText(result1.getItemList().get(0).getDuration() + "秒");
                    mTvB.setText(result1.getItemList().get(1).getDuration() + "秒");
                    mTvC.setText(result1.getItemList().get(2).getDuration() + "秒");
                }

                break;
            case ADPOSITION:
                AdPositionIdBean adPositionIdBean = (AdPositionIdBean) o;
                String picUri = adPositionIdBean.getResult().get(0).getPicUri();
                Glide.with(getContext()).load(picUri).into(mIvD);
                mIvD.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String target = adPositionIdBean.getResult().get(0).getTarget();
                        String route = adPositionIdBean.getResult().get(0).getRoute();
                        if ("STUDY_COURSE".equals(route)) {
                            if (!target.equals("")) {
                                Intent intent = new Intent(getContext(), ClassListActivity.class);
                                intent.putExtra("target", target);
                                startActivity(intent);
                            }
                        } else if ("MALL_SKU".equals(route)) {
                            if (!target.equals("")) {
                                Intent intent = new Intent(getContext(), ShopActivity.class);
                                intent.putExtra("target", target);
                                startActivity(intent);
                            }
                        } else if ("WEB".equals(route)) {
                            if (!target.equals("")) {
                                Intent intent = new Intent(getContext(), WebActivity.class);
                                intent.putExtra("target", target);
                                startActivity(intent);
                            }
                        }
                    }
                });
                break;
            case SONG:
                SongBean songBean = (SongBean) o;
                musicId = songBean.getResult().getMusicId() + "";
                List<SongBean.ResultBean.ItemListBean> itemList = songBean.getResult().getItemList();
                mSongInfos = new ArrayList<>();
                if (itemList.size() >= 3) {
                    for (int j = 0; j < 3; j++) {
                        SongInfo s1 = new SongInfo();
                        s1.setSongId(itemList.get(j).getItemId() + "");
                        s1.setSongUrl(itemList.get(j).getMediaUri());
                        s1.setSongCover(itemList.get(j).getPicUri());
                        s1.setSongName(itemList.get(j).getTitle());
                        s1.setDownloadUrl(itemList.get(j).getLyricUri());
                        Log.i("asdf", "show: " + itemList.get(j).getLyricUri());
                        mSongInfos.add(s1);
                    }
                } else {
                    for (int j = 0; j < itemList.size(); j++) {
                        SongInfo s1 = new SongInfo();
                        s1.setSongId(itemList.get(j).getItemId() + "");
                        s1.setSongUrl(itemList.get(j).getMediaUri());
                        s1.setSongCover(itemList.get(j).getPicUri());
                        s1.setSongName(itemList.get(j).getTitle());
                        s1.setDownloadUrl(itemList.get(j).getLyricUri());
                        mSongInfos.add(s1);
                    }
                }

                mRlvAdapterSonglist.addData(itemList);
                break;
            case SONGMORN:
                SongBean songBean1 = (SongBean) o;
                musicId = songBean1.getResult().getMusicId() + "";
                List<SongBean.ResultBean.ItemListBean> itemList1 = songBean1.getResult().getItemList();
                mSongInfos1 = new ArrayList<>();
                if (itemList1.size() >= 4) {
                    for (int j = 0; j < 4; j++) {
                        SongInfo s1 = new SongInfo();
                        s1.setSongId(itemList1.get(j).getItemId() + "");
                        s1.setSongUrl(itemList1.get(j).getMediaUri());
                        s1.setSongCover(itemList1.get(j).getPicUri());
                        s1.setSongName(itemList1.get(j).getTitle());
                        s1.setDownloadUrl(itemList1.get(j).getLyricUri());
                        mSongInfos1.add(s1);
                    }
                } else {
                    for (int j = 0; j < itemList1.size(); j++) {
                        SongInfo s1 = new SongInfo();
                        s1.setSongId(itemList1.get(j).getItemId() + "");
                        s1.setSongUrl(itemList1.get(j).getMediaUri());
                        s1.setSongCover(itemList1.get(j).getPicUri());
                        s1.setSongName(itemList1.get(j).getTitle());
                        s1.setDownloadUrl(itemList1.get(j).getLyricUri());
                        mSongInfos1.add(s1);
                    }
                }
                if (itemList1.size() == 1) {
                    mNoteOne.setText(itemList1.get(0).getTitle());
                } else if (itemList1.size() == 2) {
                    mNoteOne.setText(itemList1.get(0).getTitle());
                    mNoteTwo.setText(itemList1.get(1).getTitle());
                } else if (itemList1.size() == 3) {
                    mNoteOne.setText(itemList1.get(0).getTitle());
                    mNoteTwo.setText(itemList1.get(1).getTitle());
                    mNoteThree.setText(itemList1.get(2).getTitle());
                } else if (itemList1.size() == 4) {
                    mNoteOne.setText(itemList1.get(0).getTitle());
                    mNoteTwo.setText(itemList1.get(1).getTitle());
                    mNoteThree.setText(itemList1.get(2).getTitle());
                    mNoteFour.setText(itemList1.get(3).getTitle());
                }

                break;
            case SONGNIGHT:
                SongBean songBean2 = (SongBean) o;
                musicId = songBean2.getResult().getMusicId() + "";
                List<SongBean.ResultBean.ItemListBean> itemList2 = songBean2.getResult().getItemList();


                mSongInfos2 = new ArrayList<>();
                if (itemList2.size() > 0) {
                    mDescription.setText(itemList2.get(0).getDescription());
                    SongInfo s1 = new SongInfo();
                    s1.setSongId(itemList2.get(0).getItemId() + "");
                    s1.setSongUrl(itemList2.get(0).getMediaUri());
                    s1.setSongCover(itemList2.get(0).getPicUri());
                    s1.setSongName(itemList2.get(0).getTitle());
                    mNameLate.setText(itemList2.get(0).getTitle());
                    s1.setDownloadUrl(itemList2.get(0).getLyricUri());
                    mSongInfos2.add(s1);
                    Glide.with(getContext()).load(itemList2.get(0).getPicUri()).into(mCirCle);
                }

                //new MyThread(1,0).start();
                mBoo = true;
                mCheckboxNight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean checked = mCheckboxNight.isChecked();
                        mNoteOne.setTextColor(Color.parseColor("#333333"));
                        mNoteTwo.setTextColor(Color.parseColor("#333333"));
                        mNoteThree.setTextColor(Color.parseColor("#333333"));
                        mNoteFour.setTextColor(Color.parseColor("#333333"));
                        mNoteOne.setSelected(false);
                        mNoteTwo.setSelected(false);
                        mNoteThree.setSelected(false);
                        mNoteFour.setSelected(false);
                        String string = PreUtils.getString("music", "");
                        if (!"night".equals(string)) {
                            mBoo = true;
                        }
                        record = 3;
                        if (checked) {
                            PreUtils.putString("music", "night");
                            if (mBoo) {
                                if (mSongInfos2.size() != 0) {
                                    showPopu(mSongInfos2, 0);
                                    mBoo = false;
                                }
                            } else {
                                mStart.setImageDrawable(getResources().getDrawable(R.mipmap.music_pause));
                                mCancle.setVisibility(View.GONE);
                                flag_music = 1;
                                mHead.startAnimation(mAnimation);
                                mInstance.playMusic();
                                isPlay();
                            }
                            isBottomShow = true;
                            mMView.animate().translationY(0);
                        } else {
                            mStart.setImageDrawable(getResources().getDrawable(R.mipmap.music_start));
                            mCancle.setVisibility(View.VISIBLE);
                            mHead.clearAnimation();
                            flag_music = 2;
                            mInstance.pauseMusic();
                        }
                    }
                });
                break;
            case HOMEBSS:
                HomeBBSbean homeBBSbean = (HomeBBSbean) o;
                List<HomeBBSbean.ResultBean> result2 = homeBBSbean.getResult();
                mRlvAdapterHome.addData(result2);
                break;
            case USERINFO:
                UserInfoBean userInfoBean = (UserInfoBean) o;
                long userid = userInfoBean.getResult().getUserid();
                String nickname = userInfoBean.getResult().getNickname();
                String avatar = userInfoBean.getResult().getAvatar();
                List<UserInFo> userInFos = DataBaseMannger.getIntrance().selectUserInfo();
                for (int j = 0; j < userInFos.size(); j++) {
                    if (userInFos.get(j).getUserId().equals(userid + "")) {
                        isbo = true;
                        break;
                    }
                }
                if (isbo) {


                } else {
                    fistPopuwindow();
                    ArrayList<UserInFo> userInFos1 = new ArrayList<>();
                    userInFos1.add(new UserInFo(null, userid + ""));
                    DataBaseMannger.getIntrance().insertUserInfo(userInFos1);
                    isbo = false;
                }
                PreUtils.putString("userId", userid + "");
                PreUtils.putString("userName", nickname);
                PreUtils.putString("avatar", avatar);
                PreUtils.putString("rongcloudToken", userInfoBean.getResult().getRongcloudToken());
                //Glide.with(getContext()).load(avatar).into(mIvLogo);
                break;
            case FORMALCURRICULUM:
                FormalCurriculumBean formalCurriculumBean = (FormalCurriculumBean) o;
                FormalCurriculumBean.ResultBean result3 = formalCurriculumBean.getResult();
                if (result3.getChapterList().size() != 0) {
                    String picUri1 = result3.getChapterList().get(0).getContentsList().get(0).getPicUri();
                    Glide.with(mContext).load(picUri1).into(mJiaoziPlayer.thumbImageView);
                    List<FormalCurriculumBean.ResultBean.ChapterListBean> chapterList = result3.getChapterList();
                    mMediaUri = chapterList.get(0).getContentsList().get(0).getMediaUri();
                    mTitle2 = chapterList.get(0).getContentsList().get(0).getTitle();
                    mVideoTitle.setText(mTitle2);
                    mIsFree = chapterList.get(0).getContentsList().get(0).getIsFree();
                    initPlayerUrl(mMediaUri);
                    mRlvAdapter_paly.setData(chapterList);
                }

                break;
            /*case GETCOUPONONLYATHOME:
                AthomeBean athomeBean = (AthomeBean) o;
                String result4 = athomeBean.getResult();
                Toast.makeText(mContext, "" + result4, Toast.LENGTH_SHORT).show();
                break;*/
            case GAMEHOMEPAGE:
                GamehomePageBeans gamehomePageBeans = (GamehomePageBeans) o;
                result5 = gamehomePageBeans.getResult();
                if (result5.size() != 0) {
                    String title = result5.get(0).getTitle();
                    String picUri2 = result5.get(0).getPicUri();
                    mGameTitleOne.setText(title);
                    Glide.with(getContext()).load(picUri2).into(mGameImageOne);
                    String title1 = result5.get(1).getTitle();
                    mGameTitleTwo.setText(title1);
                    String picUri21 = result5.get(1).getPicUri();
                    Glide.with(getContext()).load(picUri21).into(mGameImageTwo);
                }

                break;
            case USERCARDTYPE:
                UserCardTypeBean userCardTypeBean = (UserCardTypeBean) o;
                mCardType = userCardTypeBean.getResult().getCardType();
                break;
            case BRING:
                BringBean bringBean = (BringBean) o;
                mH5_fenLingYangYu = bringBean.getResult().getH5_FenLingYangYu();
                mH5_yuErBaiKe = bringBean.getResult().getH5_YuErBaiKe();
                break;
        }
    }

    private void showHint() {
        mInflate1 = LayoutInflater.from(getContext()).inflate(R.layout.pop_hint_home, null);
        PopupWindows = new PopupWindow(mInflate1,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        PopupWindows.setFocusable(true);// 取得焦点
        PopupWindows.setClippingEnabled(false);
        //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
        PopupWindows.setBackgroundDrawable(new BitmapDrawable());
        //点击外部消失
        PopupWindows.setOutsideTouchable(true);
        //设置可以点击
        PopupWindows.setTouchable(true);
        //进入退出的动画，指定刚才定义的style
        PopupWindows.setAnimationStyle(R.style.popwin_anim_style);

        Button my_locations = mInflate1.findViewById(R.id.my_locations);


        my_locations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupWindows.dismiss();
            }
        });

        PopupWindows.showAtLocation(mInflate1, Gravity.BOTTOM, 0, 0);
    }


    @Override
    public void onMusicSwitch(SongInfo songInfo) {
        Log.i("ffff", "onMusicSwitch: ");
        if (songInfo == null) {
            return;
        }
        int nowPlayingIndex = mInstance.getNowPlayingIndex();
        Log.i("ffff", "onMusicSwitch: " + nowPlayingIndex);
        String music = PreUtils.getString("music", "");
        if ("morn".equals(music)) {
            if (nowPlayingIndex == 0) {
                mNoteOne.setTextColor(Color.parseColor("#00DEFF"));
                mNoteTwo.setTextColor(Color.parseColor("#333333"));
                mNoteThree.setTextColor(Color.parseColor("#333333"));
                mNoteFour.setTextColor(Color.parseColor("#333333"));
            } else if (nowPlayingIndex == 1) {
                mNoteOne.setTextColor(Color.parseColor("#333333"));
                mNoteTwo.setTextColor(Color.parseColor("#00DEFF"));
                mNoteThree.setTextColor(Color.parseColor("#333333"));
                mNoteFour.setTextColor(Color.parseColor("#333333"));
            } else if (nowPlayingIndex == 2) {
                mNoteOne.setTextColor(Color.parseColor("#333333"));
                mNoteTwo.setTextColor(Color.parseColor("#333333"));
                mNoteThree.setTextColor(Color.parseColor("#00DEFF"));
                mNoteFour.setTextColor(Color.parseColor("#333333"));
            } else if (nowPlayingIndex == 3) {
                mNoteOne.setTextColor(Color.parseColor("#333333"));
                mNoteTwo.setTextColor(Color.parseColor("#333333"));
                mNoteThree.setTextColor(Color.parseColor("#333333"));
                mNoteFour.setTextColor(Color.parseColor("#00DEFF"));
            }
        } else if ("radio".equals(music)) {
            mRlvAdapterSonglist.addPos(nowPlayingIndex);
            Log.i("feifeif", "onMusicSwitch: ");
        } else if ("night".equals(music)) {

        } else if ("kecheng".equals(music)) {
            List<SongInfo> playList = mInstance.getPlayList();
            SongInfo nowPlayingSongInfo = mInstance.getNowPlayingSongInfo();
            Log.i("kecheng", "onMusicSwitch: +kecheng");
            sboo = true;
            int[] location = new int[2];
            LordActivity.mLine.getLocationOnScreen(location);
            mMView.measure(0, 0);
            int popupHeight = mMView.getMeasuredHeight();
            int popupWidth = mMView.getMeasuredWidth();
            //在控件上方显示
            mPopupWindow.showAtLocation(LordActivity.mLine, Gravity.NO_GRAVITY, (location[0] + LordActivity.mLine.getWidth() / 2) - popupWidth / 2, (location[1] - popupHeight - 20));
            Glide.with(getContext()).load(nowPlayingSongInfo.getSongCover()).into(mHead);
        } else if ("know".equals(music)) {
            SongInfo nowPlayingSongInfo = mInstance.getNowPlayingSongInfo();
            Log.i("know", "onMusicSwitch: +know");
            sboo = true;
            int[] location = new int[2];
            LordActivity.mLine.getLocationOnScreen(location);
            mMView.measure(0, 0);
            int popupHeight = mMView.getMeasuredHeight();
            int popupWidth = mMView.getMeasuredWidth();
            //在控件上方显示
            mPopupWindow.showAtLocation(LordActivity.mLine, Gravity.NO_GRAVITY, (location[0] + LordActivity.mLine.getWidth() / 2) - popupWidth / 2, (location[1] - popupHeight - 20));
            Glide.with(getContext()).load(nowPlayingSongInfo.getSongCover()).into(mHead);
        }

        if (mTitle1 != null) {
            mTitle1.setText("当前播放：" + songInfo.getSongName());
        }
    }

    @Override
    public void onPlayerStart() {
        Log.i("kecheng", "onPlayerStart: +kecheng");
        String music = PreUtils.getString("music", "");
        if ("night".equals(music)) {
            mCheckboxNight.setChecked(true);
        }
        mStart.setImageDrawable(getResources().getDrawable(R.mipmap.music_pause));
        mCancle.setVisibility(View.GONE);
        flag_music = 1;
        mHead.startAnimation(mAnimation);
        mInstance.playMusic();
        isPlay();
        //开始更新进度条
        mTimerTask.startToUpdateProgress();
    }

    /*public void popwindow() {
        mPopupWindow1 = new PopupWindow();
        mPopupWindow1.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        mPopupWindow1.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        mInflate = LayoutInflater.from(getContext()).inflate(R.layout.pop_userinfo, null);
        ImageView canale = mInflate.findViewById(R.id.cancle);
        ImageView lingqu = mInflate.findViewById(R.id.lingqu);
        mPopupWindow1.setContentView(mInflate);
        mPopupWindow1.setOutsideTouchable(true);
        mPopupWindow1.setBackgroundDrawable(new BitmapDrawable());
        canale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getDataP(null, DifferentiateEnum.GETCOUPONONLYATHOME);
                mPopupWindow1.dismiss();
            }
        });
        lingqu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getDataP(null, DifferentiateEnum.GETCOUPONONLYATHOME);
                mPopupWindow1.dismiss();
            }
        });


        Intent intent10 = new Intent(getContext(), TanchuActivity.class);
        startActivity(intent10);
    }*/

    //弹出广告界面
    private void fistPopuwindow() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            View view = View.inflate(getContext(), R.layout.first_popuwindow, null);
                            ImageView cancle = view.findViewById(R.id.cancle);
                            ImageView lingqu = view.findViewById(R.id.lingqu);
                            final PopupWindow mPopu = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.MATCH_PARENT);
                            mPopu.setClippingEnabled(false);
                            mPopu.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
                            cancle.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //mPresenter.getDataP1(null, DifferentiateEnum.GETCOUPONONLYATHOME,loadinglayout);
                                    ToastUtils.showShortToast(getContext(), "已取消");
                                    mPopu.dismiss();
                                    showHint();
                                }
                            });
                            lingqu.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //mPresenter.getDataP1(null, DifferentiateEnum.GETCOUPONONLYATHOME,loadinglayout);
                                    ToastUtils.showShortToast(getContext(), "领取成功");
                                    mPopu.dismiss();
                                }
                            });
                            mPopu.setOutsideTouchable(false);//判断在外面点击是否有效
                            mPopu.setFocusable(true);
                            mPopu.showAsDropDown(view);
                            mPopu.isShowing();
                        }
                    });


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onPlayerPause() {
        String music = PreUtils.getString("music", "");
        if ("night".equals(music)) {
            mCheckboxNight.setChecked(false);
        }
        mStart.setImageDrawable(getResources().getDrawable(R.mipmap.music_start));
        mCancle.setVisibility(View.VISIBLE);
        mHead.clearAnimation();
        flag_music = 2;
        mInstance.pauseMusic();
        //停止更新进度条
        mTimerTask.stopToUpdateProgress();
    }

    @Override
    public void onPlayerStop() {
        //停止更新进度条
        mTimerTask.stopToUpdateProgress();
        mSeek.setProgress(0);
        if (mTime != null) {
            mTime.setText("00:00");
        }
        Log.i("onPlayerStop", "onPlayerStop: ");
        if (LordActivity.obo) {
            getActivity().finishAffinity();
            System.exit(0);
        }
    }

    @Override
    public void onPlayCompletion(SongInfo songInfo) {
        //songInfo maybe null
        if (songInfo == null) {
            return;
        }
        //停止更新进度条
        mTimerTask.stopToUpdateProgress();
        Log.i("onPlayCompletion", "onPlayCompletion: ");
    }

    @Override
    public void onBuffering() {
        Log.i("onBuffering", "onBuffering: ");
    }

    @Override
    public void onError(int errorCode, String errorMsg) {
        //停止更新进度条
        mTimerTask.stopToUpdateProgress();
        Log.i("onError", "onError: ");
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

}
