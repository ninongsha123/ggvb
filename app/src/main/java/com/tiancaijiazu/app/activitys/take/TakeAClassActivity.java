package com.tiancaijiazu.app.activitys.take;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
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
import com.hpplay.sdk.source.browse.api.LelinkServiceInfo;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.activitypage.WebActivity;
import com.tiancaijiazu.app.activitys.early.RecordCommentActivity;
import com.tiancaijiazu.app.activitys.early.adapters.RlvAdapter_Pop_Custom;
import com.tiancaijiazu.app.activitys.shopping_activity.LookPhotoActivity;
import com.tiancaijiazu.app.activitys.take.adapters.RlvAdapter_record_study;
import com.tiancaijiazu.app.activitys.user_fragment.utils.NoScrollBehavior;
import com.tiancaijiazu.app.app.App;
import com.tiancaijiazu.app.base.activity.BaseActivity;
import com.tiancaijiazu.app.beans.ArticleLists;
import com.tiancaijiazu.app.beans.FormalCurriculumBean;
import com.tiancaijiazu.app.beans.SubmitBean;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.fragments.adapters.RlvAdapter_video_tv;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.CameraPermissionCompat;
import com.tiancaijiazu.app.utils.ScreenStatusUtil;
import com.tiancaijiazu.app.utils.events.CarryOutEvent;
import com.tiancaijiazu.app.utils.events.DownloadEvent;
import com.tiancaijiazu.app.utils.events.MessageWrap;
import com.tiancaijiazu.app.utils.events.SpeedEvent;
import com.tiancaijiazu.app.utils.systems.MyIJKMediaSystem;
import com.tiancaijiazu.app.utils.systems.MyJZMediaSystem;
import com.tiancaijiazu.app.utils.views.JZVideoPlayer;
import com.tiancaijiazu.app.utils.views.MyJZVideoPlayerStandard;
import com.tiancaijiazu.app.mvp.IView;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class TakeAClassActivity extends BaseActivity<IView, Presenter<IView>> implements IView, AppBarLayout.OnOffsetChangedListener {

    @BindView(R.id.tv_day)
    TextView mTvDay;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.jiaozi_player)
    MyJZVideoPlayerStandard mJiaoziPlayer;
    @BindView(R.id.iv_play)
    ImageView mIvPlay;
    @BindView(R.id.tv_headerView)
    RelativeLayout mTvHeaderView;
    @BindView(R.id.iv_finis)
    ImageView mIvFinis;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.share)
    ImageView mShare;
    @BindView(R.id.ll)
    RelativeLayout mLl;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.app_bar)
    AppBarLayout mAppBar;
    @BindView(R.id.magic_indicator)
    MagicIndicator mMagicIndicator;
    @BindView(R.id.rlv)
    RecyclerView mRlv;
    @BindView(R.id.rela)
    RelativeLayout mRela;
    @BindView(R.id.note_taking)
    ImageView mNoteTaking;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    private ArrayList<Integer> integers;
    private String[] mediaName = {"普通", "高清", "原画"};
    //系统播放器引擎
    MyJZMediaSystem mJZMediaSystem;
    MyIJKMediaSystem mIJKMediaSystem;
    //用于recyclerView滑动到指定的位置
    private boolean canScroll;
    //判读是否是scrollview主动引起的滑动，true-是，false-否，由tablayout引起的
    private boolean isScroll;
    private int scrollToPosition;
    //判读是否是recyclerView主动引起的滑动，true- 是，false- 否，由tablayout引起的
    private boolean isRecyclerScroll;
    //记录上一次位置，防止在同一内容块里滑动 重复定位到tablayout
    private int lastPos;
    private LinearLayoutManager manager;
    private NoScrollBehavior myAppBarLayoutBehavoir;
    private List<FormalCurriculumBean.ResultBean.ChapterListBean.ContentsListBean.FeedbackListBean> mFeedbackList;

    private RlvAdapter_record_study mRlvAdapter_record_study;
    private FormalCurriculumBean.ResultBean.ChapterListBean.ContentsListBean contentsListBean;
    private String mContentsId;
    private Intent mIntent;
    private PopupWindow mPopupWindow1;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;
            if(what == 2){
                if (mRefreshLayout != null) {
                    mRefreshLayout.finishLoadMore(true);
                    page++;
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("page", page);
                    map.put("articleType", "2");
                    map.put("orderByType", 0);
                    mPresenter.getDataP(map, DifferentiateEnum.ARTICLELISTS);
                }
            }else {
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
                    Toast.makeText(TakeAClassActivity.this, "搜索失败", Toast.LENGTH_SHORT).show();
                }
            }

            super.handleMessage(msg);
        }
    };
    private String mMediaUri;
    private DownloadManager mDownloadManager;
    private CompleteReceiver completeReceiver;
    private String mTitle1;
    private int page = 1;


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

    long reference;
    //下载任务
    String serviceString = Context.DOWNLOAD_SERVICE;

    @Override
    protected void initEventAndData() {
        ScreenStatusUtil.setFillDip(this);
        mDownloadManager = (DownloadManager) getBaseContext().getSystemService(serviceString);
        /** 注册下载监听的广播 **/
        completeReceiver = new CompleteReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(DownloadManager.ACTION_NOTIFICATION_CLICKED);
        filter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        /** register download success broadcast **/
        registerReceiver(completeReceiver,
                filter);
        mIntent = getIntent();
        contentsListBean = (FormalCurriculumBean.ResultBean.ChapterListBean.ContentsListBean) mIntent.getSerializableExtra("data");
        String title = mIntent.getStringExtra("title");
        mTitle1 = contentsListBean.getTitle();
        mFeedbackList = (List<FormalCurriculumBean.ResultBean.ChapterListBean.ContentsListBean.FeedbackListBean>) mIntent.getSerializableExtra("game");
        List<FormalCurriculumBean.ResultBean.ChapterListBean.ContentsListBean.GameListBean> gameList = (List<FormalCurriculumBean.ResultBean.ChapterListBean.ContentsListBean.GameListBean>) mIntent.getSerializableExtra("game1");
        mContentsId = mIntent.getStringExtra("contentsId");
        Log.i("yx156", "initEventAndData: " + mContentsId);
        String substring = title.substring(0, 4);
        String substrings = title.substring(4, title.length());
        String p=substring+" "+substrings;
        mTvDay.setText(substring);
        if (substrings.contains(" ")) {
            String[] split = substrings.split(" ");
            mTvTitle.setText(split[1]);
        }else {
            mTvTitle.setText(substrings);
        }
//            String substring = title.substring(0, 4);
//            String substrings = title.substring(4, title.length());
//            String p=substring+" "+substrings;
//            String[] split = title.split(" ");
//            if (split.length > 1) {
//                mTvDay.setText(split[0]);
//                mTvTitle.setText(split[1]);
//
//        }
        initData();
        mMediaUri = contentsListBean.getMediaUri();
        Glide.with(this).load(contentsListBean.getCoverPicUri()).into(mJiaoziPlayer.thumbImageView);
        mJiaoziPlayer.setUp(mMediaUri, JZVideoPlayer.SCREEN_WINDOW_NORMAL, "");
        mJZMediaSystem = new MyJZMediaSystem();
        mIJKMediaSystem = new MyIJKMediaSystem();
        initView();

        int isGame = contentsListBean.getIsGame();
        initRlv(contentsListBean, isGame, gameList);
//        initPop();
        mAppBar.addOnOffsetChangedListener(this);
        mRela.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        mIvPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRela.setVisibility(View.GONE);
                mJiaoziPlayer.startVideo();
                mRlvAdapter_record_study.addUp();
                mMediaUri = contentsListBean.getMediaUri();
            }
        });
        initTou();
        HashMap<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("articleType", "2");
        map.put("orderByType", 0);
        mPresenter.getDataP(map, DifferentiateEnum.ARTICLELISTS);

        mNoteTaking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //提交记录
                Intent intent = new Intent(TakeAClassActivity.this, NoteTakingActivity.class);
                long contentsId = contentsListBean.getContentsId();
                long courseId = contentsListBean.getCourseId();
                intent.putExtra("contentsId", contentsId);
                intent.putExtra("courseId", courseId);
                startActivityForResult(intent,100);
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
    }

//    private void initPop() {
//        mInflate = LayoutInflater.from(this).inflate(R.layout.pop_game_select, null);
//        mPopupWindow = new PopupWindow(mInflate, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        mPopupWindow.setFocusable(true);// 取得焦点
//        mPopupWindow.setClippingEnabled(false);
//        //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
//        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
//        //点击外部消失
//        mPopupWindow.setOutsideTouchable(true);
//        //设置可以点击
//        mPopupWindow.setTouchable(true);
//        //进入退出的动画，指定刚才定义的style
//        mPopupWindow.setAnimationStyle(R.style.popwin_anim_style);
//
//        RecyclerView recylerView = mInflate.findViewById(R.id.recylerView);
//        TextView tv = mInflate.findViewById(R.id.tv);
//        mText = mInflate.findViewById(R.id.text);
//        ImageView submit = mInflate.findViewById(R.id.submit);
//
//        tv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mPopupWindow.dismiss();
//            }
//        });
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        recylerView.setLayoutManager(linearLayoutManager);
        List<FormalCurriculumBean.ResultBean.ChapterListBean.ContentsListBean.FeedbackListBean> feedbackListBeans = new ArrayList<>();
//        mRlvAdapterPopCustom = new RlvAdapter_Pop_Custom(feedbackListBeans, this);
//        recylerView.setAdapter(mRlvAdapterPopCustom);
//        submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                List<FormalCurriculumBean.ResultBean.ChapterListBean.ContentsListBean.FeedbackListBean> feedbackListBeans = mRlvAdapterPopCustom.mData;
//                String feedbackIds = "";
//                for (int i = 0; i < feedbackListBeans.size(); i++) {
//                    if (feedbackListBeans.get(i).isIsbo()) {
//                        feedbackIds += feedbackListBeans.get(i).getFeedbackId() + ",";
//                    }
//                }
//                HashMap<String, String> map = new HashMap<>();
//                map.put("contentsId", mContentsId);
//                map.put("feedbackIds", feedbackIds);
//                Log.i("yx156", mContentsId + "onClick: " + feedbackIds);
//                mPresenter.getDataP(map, DifferentiateEnum.SUBMIT);
//                //mRlvAdapterCourseList.addSubmit(mWai, mPosition);
//                mRlvAdapterPopCustom.mData.clear();
//                mPopupWindow.dismiss();
//            }
//        });
//    }

    @SuppressLint("ClickableViewAccessibility")
    private void initRlv(FormalCurriculumBean.ResultBean.ChapterListBean.ContentsListBean contentsListBean, int isGame, List<FormalCurriculumBean.ResultBean.ChapterListBean.ContentsListBean.GameListBean> gameList) {
        manager = new LinearLayoutManager(this);
        mRlv.setLayoutManager(manager);
        ArrayList<FormalCurriculumBean.ResultBean.ChapterListBean.ContentsListBean.PartListBean> partListBeans = new ArrayList<>();
        partListBeans.add(new FormalCurriculumBean.ResultBean.ChapterListBean.ContentsListBean.PartListBean(0, "游戏目的", contentsListBean.getAbility(),
                "", "", "", ""));
        partListBeans.add(new FormalCurriculumBean.ResultBean.ChapterListBean.ContentsListBean.PartListBean(0, "注意事项", contentsListBean.getNote(),
                "", "", "", ""));
        List<FormalCurriculumBean.ResultBean.ChapterListBean.ContentsListBean.PartListBean> partList = contentsListBean.getPartList();
        partListBeans.addAll(partList);
        initTab(partListBeans);
        //计算内容块所在的高度，全屏高度-状态栏高度-tablayout的高度(这里固定高度50dp)，用于recyclerView的最后一个item view填充高度
        int screenH = getScreenHeight();
        int statusBarH = getStatusBarHeight(this);
        int tabH = 50 * 3;
        int i = ScreenStatusUtil.setDp(109, this);
        int lastH = screenH - i;
        List<ArticleLists.ResultBean> resultBeans = new ArrayList<>();
        mRlvAdapter_record_study = new RlvAdapter_record_study(partListBeans, this, integers, lastH, isGame, gameList, resultBeans);
        mRlv.setAdapter(mRlvAdapter_record_study);
        mRlv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //当滑动由recyclerView触发时，isRecyclerScroll 置true
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    isRecyclerScroll = true;
                }
                return false;
            }
        });

        mRlvAdapter_record_study.setOnClickLisiterVideo(new RlvAdapter_record_study.onClickLisiterVideo() {
            @Override
            public void onClickerVideo(View view, String video, String title) {
                mMediaUri = video;
                mTitle1 = mTitle1 + title;
            }
        });

        mRlvAdapter_record_study.setOnClickLisiterGameOne(new RlvAdapter_record_study.onClickLisiterGameOne() {
            @Override
            public void onClickerGameOne(View view, int position, ArrayList<FormalCurriculumBean.ResultBean.ChapterListBean.ContentsListBean.PartListBean> mData, List<FormalCurriculumBean.ResultBean.ChapterListBean.ContentsListBean.GameListBean> mGameList) {
                if (mGameList.size() != 0) {
                    String url = mGameList.get(0).getUrl();
                    String title = mGameList.get(0).getTitle();
                    Intent intent = new Intent(TakeAClassActivity.this, WebActivity.class);
                    intent.putExtra("target", url);
                    intent.putExtra("title", title);
                    intent.putExtra("biao", "home");
                    startActivity(intent);
                } else {
                    /*if(mData.size()!=0){
                        String url = mData.get(position).getGameList().get(0).getUrl();
                        Intent intent = new Intent(getContext(), WebActivity.class);
                        intent.putExtra("target", url);
                        startActivity(intent);
                    }*/
                }
            }
        });


        mRlvAdapter_record_study.setOnClickLisiterGameTwo(new RlvAdapter_record_study.onClickLisiterGameTwo() {
            @Override
            public void onClickerGameTwo(View view, int position, ArrayList<FormalCurriculumBean.ResultBean.ChapterListBean.ContentsListBean.PartListBean> mData, List<FormalCurriculumBean.ResultBean.ChapterListBean.ContentsListBean.GameListBean> mGameList) {
                if (mGameList.size() != 0) {
                    String url = mGameList.get(1).getUrl();
                    String title = mGameList.get(1).getTitle();
                    Intent intent = new Intent(TakeAClassActivity.this, WebActivity.class);
                    intent.putExtra("target", url);
                    intent.putExtra("title", title);
                    intent.putExtra("biao", "home");
                    startActivity(intent);
                } else {
                    /*if(mData.size()!=0){
                        String url = mData.get(position).getGameList().get(1).getUrl();
                        Intent intent = new Intent(getContext(), WebActivity.class);
                        intent.putExtra("target", url);
                        startActivity(intent);
                    }*/
                }
            }
        });
        mRlvAdapter_record_study.setOnClickLisiterGameThree(new RlvAdapter_record_study.onClickLisiterGameThree() {
            @Override
            public void onClickerGameThree(View view, int position, ArrayList<FormalCurriculumBean.ResultBean.ChapterListBean.ContentsListBean.PartListBean> mData, List<FormalCurriculumBean.ResultBean.ChapterListBean.ContentsListBean.GameListBean> mGameList) {
                if (mGameList.size() !=0) {
                    String url = mGameList.get(1).getUrl();
                    String title = mGameList.get(1).getTitle();
                    Intent intent = new Intent(TakeAClassActivity.this, WebActivity.class);
                    intent.putExtra("target", url);
                    intent.putExtra("title", title);
                    intent.putExtra("biao", "home");
                    startActivity(intent);
                } else {

                }
            }
        });

        mRlvAdapter_record_study.setOnClickLisiterSubmit(new RlvAdapter_record_study.onClickLisiterSubmit() {
            @Override
            public void onClickerSubmit(View view, int position) {
                String ability = contentsListBean.getAbility();
                String replace = ability.replace(",", " ");
                String[] split = ability.split(",");
                String textStr = "今天的课程主要锻炼宝宝 <font color='#00DEFF'>" + replace + "</font> " + split.length + "项能力，请反馈宝宝的上课情况，为宝宝定制最合适的扩展游戏";
//                mText.setText(Html.fromHtml(textStr));
                int size = mFeedbackList.size();
                for (int j = 0; j < size; j++) {
                    mFeedbackList.get(j).setIsbo(false);
                }
//                mRlvAdapterPopCustom.addData(mFeedbackList);
//                mPopupWindow.showAtLocation(mInflate, Gravity.BOTTOM, 0, 0);
                HashMap<String, String> map = new HashMap<>();
                map.put("contentsId", mContentsId);
                map.put("feedbackIds", "");
                mPresenter.getDataP(map, DifferentiateEnum.SUBMIT);
            }
        });

        mRlvAdapter_record_study.setOnClickLisiterRecord(new RlvAdapter_record_study.onClickLisiterRecord() {
            @Override
            public void onClickerRecord(View view, int position,List<ArticleLists.ResultBean> mDataRec) {
                Intent intent = new Intent(TakeAClassActivity.this, RecordCommentActivity.class);
                intent.putExtra("id", mDataRec.get(position).getArticleId());
                intent.putExtra("biao", "one");
                intent.putExtra("position", position);
                intent.putExtra("data", (Serializable) mDataRec.get(position));
                startActivityForResult(intent, 16);
            }
        });

        mRlvAdapter_record_study.setOnClickLisiterPicture(new RlvAdapter_record_study.onClickLisiterPicture() {
            @Override
            public void onClickerPicture(View view, int position, ArrayList<String> strings1) {
                //查看大图
                Intent intent = new Intent(TakeAClassActivity.this, LookPhotoActivity.class);
                intent.putStringArrayListExtra("images", strings1);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });


        mRlv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (canScroll) {
                    canScroll = false;
                    moveToPosition(manager, recyclerView, scrollToPosition);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isRecyclerScroll) {
                    //第一个可见的view的位置，即tablayou需定位的位置
                    int position = manager.findFirstVisibleItemPosition();
                    if (lastPos != position) {
                        //   tabLayout.setScrollPosition(position, 0, true);
                        mMagicIndicator.onPageSelected(position);
                        mMagicIndicator.onPageScrollStateChanged(position);
                        mMagicIndicator.onPageScrolled(position, 0, 1);
                    }
                    lastPos = position;
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (completeReceiver != null) {
            unregisterReceiver(completeReceiver);
        }
        super.onDestroy();
    }

    private void initData() {
        integers = new ArrayList<>();
        integers.add(R.mipmap.game_purpose);
        integers.add(R.mipmap.game_purpose_two);
        integers.add(R.mipmap.game_purpose_there);
        integers.add(R.mipmap.game_purpose_four);
        integers.add(R.mipmap.game_purpose_five);
        integers.add(R.mipmap.game_purpose_six);
        integers.add(R.mipmap.game_purpose_seven);
        integers.add(R.mipmap.game_purpose_eight);
        integers.add(R.mipmap.game_purpose_nine);
        integers.add(R.mipmap.game_purpose_ten);
        integers.add(R.mipmap.game_purpose_eleven);
        integers.add(R.mipmap.game_purpose_twelve);
        integers.add(R.mipmap.game_purpose_thirteen);
        integers.add(R.mipmap.game_purpose_fourteen);
        integers.add(R.mipmap.game_purpose_fifteen);
        integers.add(R.mipmap.game_purpose_sixteen);
        integers.add(R.mipmap.game_purpose_seventeen);
        integers.add(R.mipmap.game_purpose_nighteen);
        integers.add(R.mipmap.game_purpose_nineteen);
        integers.add(R.mipmap.game_purpose_twenty);
    }

    private void initView() {
        myAppBarLayoutBehavoir = (NoScrollBehavior)
                ((CoordinatorLayout.LayoutParams) mAppBar.getLayoutParams()).getBehavior();
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
        /*getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_BACK) {
                    if (JZVideoPlayer.backPress()) {

                    }
                    return true;
                }
                return false;
            }
        });*/
    }

    private ILelinkServiceManager mLelinkServiceManager;
    private LelinkPlayer mLeLinkPlayer;

    private void initTou() {
        mLelinkServiceManager = App.getApplication().getILelinkServiceManager();
        mLeLinkPlayer = new LelinkPlayer(this);
        mLeLinkPlayer.setConnectListener(connectListener);
        mLelinkServiceManager.setOnBrowseListener(browserListener);
        mLeLinkPlayer.setPlayerListener(playerListener);
    }

    private IBrowseListener browserListener = new IBrowseListener() {

        @Override
        public void onBrowse(int resultCode, List<LelinkServiceInfo> list) {
            Message obtain = Message.obtain();
            obtain.obj = list;
            obtain.arg1 = resultCode;
            mHandler.sendMessage(obtain);
        }
    };
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
            Toast.makeText(TakeAClassActivity.this, "连接成功", Toast.LENGTH_SHORT).show();

        }


        @Override
        public void onDisconnect(LelinkServiceInfo serviceInfo, int what, int extra) {

        }

    };
    private RlvAdapter_video_tv mRlvAdapterVideoTv;
    private LinearLayout mLine;
    private static final int REQUEST_CAMERA_PERMISSION = 2;

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onPing(MessageWrap isbo) {
        if (isbo.isIsbo()) {
            View inflate = LayoutInflater.from(this).inflate(R.layout.list_tv_layout, null);
            mPopupWindow1 = new PopupWindow(inflate, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            mPopupWindow1.setFocusable(true);// 取得焦点
            mPopupWindow1.setAnimationStyle(R.style.popwin_anim_style);
            mPopupWindow1.setClippingEnabled(false);
            //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
            mPopupWindow1.setBackgroundDrawable(new BitmapDrawable());
            //点击外部消失
            mPopupWindow1.setOutsideTouchable(true);
            //设置可以点击
            mPopupWindow1.setTouchable(true);

            RecyclerView recylerView = inflate.findViewById(R.id.recylerView);
            ArrayList<LelinkServiceInfo> list = new ArrayList<>();
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            recylerView.setLayoutManager(linearLayoutManager);
            mRlvAdapterVideoTv = new RlvAdapter_video_tv(list);
            recylerView.setAdapter(mRlvAdapterVideoTv);
            mPopupWindow1.showAtLocation(inflate, Gravity.BOTTOM, 0, 0);
            mLelinkServiceManager.browse(ILelinkServiceManager.TYPE_ALL);
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
                    mPopupWindow1.dismiss();
                }
            });
            linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CameraPermissionCompat.checkCameraPermission(TakeAClassActivity.this, new CameraPermissionCompat.OnCameraPermissionListener() {

                        @Override
                        public void onGrantResult(boolean granted) {
                            if (granted) {
                                // 允许，打开二维码
                                // 允许，打开二维码
                                Intent intent = new Intent(TakeAClassActivity.this, CaptureActivity.class);
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
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onMessageEventPostCarryOut(CarryOutEvent event) {
        String zhixing = event.getZhixing();
        if ("zhixing".equals(zhixing)) {
            mRela.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    return true;
                }
            });
            mRela.setVisibility(View.VISIBLE);
        }
    }

    /**
     * * 倍速切换
     */
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onMessageEventPostSpeed(SpeedEvent event) {
        mJZMediaSystem.setSpeeding(event.getSpeed());
        mIJKMediaSystem.setSpeeding(event.getSpeed());
        Toast.makeText(this, "正在切换倍速:" + event.getSpeed(), Toast.LENGTH_LONG).show();
    }

    /**
     * 下载
     */
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onMessageEventPostDetail(DownloadEvent event) {
        Toast.makeText(this, "下载中", Toast.LENGTH_SHORT).show();
        Uri uri = Uri.parse(mMediaUri);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        //通知栏的标题
        request.setTitle(mTitle1 + "视频下载");
        //显示通知栏的说明
        request.setDescription(mTitle1);
        request.setVisibleInDownloadsUi(true);
        //下载到那个文件夹下，以及命名
        request.setDestinationInExternalFilesDir(mActivity.getApplicationContext(), Environment.DIRECTORY_DOWNLOADS, mTitle1 + ".mp4");
        //下载的唯一标识，可以用这个标识来控制这个下载的任务enqueue（）开始执行这个任务
        reference = mDownloadManager.enqueue(request);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initTab(ArrayList<FormalCurriculumBean.ResultBean.ChapterListBean.ContentsListBean.PartListBean> partListBeans) {
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setScrollPivotX(0.65f);
        commonNavigator.setAdjustMode(false);
        CommonNavigatorAdapter commonNavigatorAdapter = new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return partListBeans == null ? 0 : partListBeans.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new SimplePagerTitleView(context);
                simplePagerTitleView.setText(partListBeans.get(index).getTitle());
                simplePagerTitleView.setNormalColor(ContextCompat.getColor(TakeAClassActivity.this, R.color.colorTextSubhead));
                simplePagerTitleView.setSelectedColor(ContextCompat.getColor(TakeAClassActivity.this, R.color.colorTextTitleHome));
                simplePagerTitleView.setTextSize(13);
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //点击标签，使recyclerView滑动，isRecyclerScroll置false
                        int pos = index;
                        isRecyclerScroll = false;
                        moveToPosition(manager, mRlv, pos);
                        mMagicIndicator.onPageSelected(index);
                        mMagicIndicator.onPageScrollStateChanged(index);
                        mMagicIndicator.onPageScrolled(index, 0, 1);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setLineHeight(UIUtil.dip2px(context, 2));
                indicator.setLineWidth(UIUtil.dip2px(context, 25));
                indicator.setRoundRadius(UIUtil.dip2px(context, 3));
                indicator.setStartInterpolator(new AccelerateInterpolator());
                indicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
                indicator.setColors(ContextCompat.getColor(TakeAClassActivity.this, R.color.colorParenting));
                return indicator;
            }

        };
        commonNavigator.setAdapter(commonNavigatorAdapter);
        mMagicIndicator.setNavigator(commonNavigator);
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_take_aclass;
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum) {
            case SUBMIT:
                SubmitBean submitBean = (SubmitBean) o;
                List<SubmitBean.ResultBean> result = submitBean.getResult();
                Log.i("yx156", "show: ");
                mRlvAdapter_record_study.addKui(result);
                break;
            case ARTICLELISTS:
                ArticleLists articleLists = (ArticleLists) o;
                List<ArticleLists.ResultBean> result1 = articleLists.getResult();
                if(result1.size()!=0){
                    if(page == 1){
                        mRlvAdapter_record_study.addData(result1,true);
                    }else {
                        mRlvAdapter_record_study.addData(result1,false);
                    }
                }else {
                    mRlvAdapter_record_study.addData(result1,false);
                }
                break;
        }
    }

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (MyJZVideoPlayerStandard.c == 1) {
                MyJZVideoPlayerStandard.backButton.performClick();
                MyJZVideoPlayerStandard.c = 0;
            } else {
                setResult(12, mIntent);
                finish();
            }
        }
        return false;
    }

    @OnClick({R.id.iv_finis, R.id.share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_finis:
                setResult(12, mIntent);
                finish();
                break;
            case R.id.share:
                break;
            /*case R.id.note_taking:
                Intent intent = new Intent(this, NoteTakingActivity.class);
                startActivity(intent);
                break;*/
           /* case R.id.tv_game:
                mPopupWindow.showAtLocation(mInflate, Gravity.BOTTOM, 0, 0);
                break;*/
            /*case R.id.tv_english:
                Intent intent = new Intent(this, EnglishClassActivity.class);
                startActivity(intent);
                break;
            */
        }
    }


    private boolean isbo = false;

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        float percent = Float.valueOf(Math.abs(i)) / Float.valueOf(appBarLayout.getTotalScrollRange());
        Log.i("jianting", "onOffsetChanged: " + percent);

        //滑动事件处理
        if (percent == 0) {
            //当完全展开时  appbar可滑动  禁止refresh(可根据需求不禁止刷新)
            myAppBarLayoutBehavoir.setNoScroll(false);
            if (isbo) {
                mJiaoziPlayer.backPress();
                isbo = false;
            }
        } else {
            //滑动中 appbar可滑动 禁止refresh(建议禁止刷新,否则会appbar影响滑动流畅)
            myAppBarLayoutBehavoir.setNoScroll(false);
        }
        if (percent == 1) {
            mJiaoziPlayer.startWindowTiny();
            isbo = true;
        }
    }

    public void moveToPosition(LinearLayoutManager manager, RecyclerView mRecyclerView, int position) {
        // 第一个可见的view的位置
        int firstItem = manager.findFirstVisibleItemPosition();
        // 最后一个可见的view的位置
        int lastItem = manager.findLastVisibleItemPosition();
        if (position <= firstItem) {
            // 如果跳转位置firstItem 之前(滑出屏幕的情况)，就smoothScrollToPosition可以直接跳转，
            mRecyclerView.smoothScrollToPosition(position);
        } else if (position <= lastItem) {
            // 跳转位置在firstItem 之后，lastItem 之间（显示在当前屏幕），smoothScrollBy来滑动到指定位置
            int top = mRecyclerView.getChildAt(position - firstItem).getTop();
            mRecyclerView.smoothScrollBy(0, top);
        } else {
            // 如果要跳转的位置在lastItem 之后，则先调用smoothScrollToPosition将要跳转的位置滚动到可见位置
            // 再通过onScrollStateChanged控制再次调用当前moveToPosition方法，执行上一个判断中的方法
            mRecyclerView.smoothScrollToPosition(position);
            scrollToPosition = position;
            canScroll = true;
        }
    }

    private int getScreenHeight() {
        return getResources().getDisplayMetrics().heightPixels;
    }

    public int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources()
                .getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==16&&resultCode==17){
            int position = data.getIntExtra("position", 0);
            boolean checked = data.getBooleanExtra("checked", false);
            String likes = data.getStringExtra("likes");
            int discuss = data.getIntExtra("discuss", 0);
            if (!likes.equals("")) {
                int i = Integer.parseInt(likes);
                if (checked) {
                    mRlvAdapter_record_study.mRlvAdapterTakeRecord.mData.get(position).setIsLikes(1);
                    mRlvAdapter_record_study.mRlvAdapterTakeRecord.mData.get(position).setLikes(i);
                    mRlvAdapter_record_study.mRlvAdapterTakeRecord.mData.get(position).setDiscuss(discuss);
                    mRlvAdapter_record_study.mRlvAdapterTakeRecord.notifyItemChanged(position);
                } else {
                    mRlvAdapter_record_study.mRlvAdapterTakeRecord.mData.get(position).setIsLikes(0);
                    mRlvAdapter_record_study.mRlvAdapterTakeRecord.mData.get(position).setLikes(i);
                    mRlvAdapter_record_study.mRlvAdapterTakeRecord.mData.get(position).setDiscuss(discuss);
                    mRlvAdapter_record_study.mRlvAdapterTakeRecord.notifyItemChanged(position);
                }
            }
        }else if (requestCode==100&&resultCode==200){
            HashMap<String, Object> map = new HashMap<>();
            map.put("page", page);
            map.put("articleType", "2");
            map.put("orderByType", 0);
            mPresenter.getDataP(map, DifferentiateEnum.ARTICLELISTS);
        }
    }
}
