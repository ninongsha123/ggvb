package com.tiancaijiazu.app.fragments.outermostlayer.college_child;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lzx.starrysky.manager.MusicManager;
import com.lzx.starrysky.manager.OnPlayerEventListener;
import com.lzx.starrysky.model.SongInfo;
import com.lzx.starrysky.utils.TimerTaskManager;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.base.activity.BaseActivity;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.fragments.beans.CollegeParentBean;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.PreUtils;
import com.tiancaijiazu.app.utils.ScreenStatusUtil;
import com.tiancaijiazu.app.utils.tabmin.ColorFlipPagerTitleViewYx;
import com.tiancaijiazu.app.utils.tabmin.SimplePagerTitleViewYx;
import com.tiancaijiazu.app.mvp.IView;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class Vip_classList extends BaseActivity<IView, Presenter<IView>> implements IView, OnPlayerEventListener {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title_music)
    TextView titleMusic;
    @BindView(R.id.fl)
    FrameLayout fl;
    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.tab)
    MagicIndicator mTab;
    @BindView(R.id.relative)
    RelativeLayout mRelative;
    private List<CollegeParentBean.ResultBean> mData;

    @Override
    protected void initEventAndData() {
        ScreenStatusUtil.setFillDip(this);
        ScreenStatusUtil.setNotStatus(this,mRelative);
        Intent intent = getIntent();
        mData = (List<CollegeParentBean.ResultBean>) intent.getSerializableExtra("data");
        int position = intent.getIntExtra("position", 0);
        String biao = intent.getStringExtra("biao");
        /*String substring = biao.substring(0, 2);
        String substring1 = biao.substring(2, biao.length());*/
        titleMusic.setText(biao);
        ArrayList<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < mData.size(); i++) {
            fragments.add(Vip_classFragment.getInstance(mData.get(i).getCatalogId()+"",mData.get(i).getName()));
        }
        VpAdapter_vip adapter = new VpAdapter_vip(getSupportFragmentManager(), mData, fragments);
        vp.setAdapter(adapter);
        //vp.setOffscreenPageLimit(mData.size());
        initTab();
        vp.setCurrentItem(position);
        mTimerTask = new TimerTaskManager();
        mInstance = MusicManager.getInstance();
        //添加监听
        /*MusicManager.getInstance().addPlayerEventListener(this);
        pop();*/
    }

    private void initTab() {
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setScrollPivotX(0.65f);
        commonNavigator.setAdjustMode(false);
        commonNavigator.setEnablePivotScroll(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mData == null ? 0 : mData.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleViewYx simplePagerTitleView = new ColorFlipPagerTitleViewYx(context);
                simplePagerTitleView.setText(mData.get(index).getName());
                simplePagerTitleView.setNormalColor(ContextCompat.getColor(Vip_classList.this, R.color.colorTextSubhead));
                simplePagerTitleView.setSelectedColor(ContextCompat.getColor(Vip_classList.this, R.color.colorTextTitleHome));
                simplePagerTitleView.setNormalSize(15);
                simplePagerTitleView.setSelectedSize(17);
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        vp.setCurrentItem(index, false);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setLineHeight(UIUtil.dip2px(context, 2));
                indicator.setLineWidth(UIUtil.dip2px(context, 60));
                indicator.setRoundRadius(UIUtil.dip2px(context, 3));
                indicator.setStartInterpolator(new AccelerateInterpolator());
                indicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
                indicator.setColors(ContextCompat.getColor(Vip_classList.this, R.color.colorLightRed));
                return indicator;
            }

        });
        mTab.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mTab, vp);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_vip_class_list;
    }


    @Override
    public void showError(String error) {

    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {

    }


    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }

    private ViewGroup mMView;
    private SeekBar mSeek;
    private ImageView mStart;
    private ImageView mCancle;
    private ImageView mHead;
    private PopupWindow mPopupWindow;
    private TextView mTitle1;
    private TextView mTime;
    private Animation mAnimation;
    private int flag_music = 1;
    TimerTaskManager mTimerTask;
    private MusicManager mInstance;

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
                    /*if ("night".equals(music)) {
                        mCheckboxNight.setChecked(false);
                    }*/
                    mStart.setImageDrawable(getResources().getDrawable(R.mipmap.music_start));
                    mCancle.setVisibility(View.VISIBLE);
                    mHead.clearAnimation();
                    flag_music = 2;
                    mInstance.pauseMusic();
                } else if (flag_music == 2) {
                    /*if ("night".equals(music)) {
                        mCheckboxNight.setChecked(true);
                    }*/
                    mStart.setImageDrawable(getResources().getDrawable(R.mipmap.music_pause));
                    mCancle.setVisibility(View.GONE);
                    flag_music = 1;
                    mHead.startAnimation(mAnimation);
                    mInstance.playMusic();
                    //isPlay();
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
                String music = PreUtils.getString("music", "");
                Log.i("yx456", "onClick: " + music);
                if ("morn".equals(music)) {

                }
                mPopupWindow.dismiss();
                mInstance.stopMusic();
            }
        });
    }

    @Override
    protected void onDestroy() {
        //回收资源
        MusicManager.getInstance().removePlayerEventListener(this);
        super.onDestroy();
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
    public void onMusicSwitch(SongInfo songInfo) {
        if (songInfo == null) {
            return;
        }
        SongInfo nowPlayingSongInfo = mInstance.getNowPlayingSongInfo();
        //在控件上方显示
        mPopupWindow.showAtLocation(mMView, Gravity.BOTTOM, 0, 0);
        Glide.with(this).load(nowPlayingSongInfo.getSongCover()).into(mHead);
        if (mTitle1 != null) {
            mTitle1.setText("当前播放：" + songInfo.getSongName());
        }
    }

    @Override
    public void onPlayerStart() {
        mStart.setImageDrawable(getResources().getDrawable(R.mipmap.music_pause));
        mCancle.setVisibility(View.GONE);
        flag_music = 1;
        mHead.startAnimation(mAnimation);
        mInstance.playMusic();
        //开始更新进度条
        mTimerTask.startToUpdateProgress();
    }

    @Override
    public void onPlayerPause() {
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
    }

    @Override
    public void onPlayCompletion(SongInfo songInfo) {
        if (songInfo == null) {
            return;
        }
        //停止更新进度条
        mTimerTask.stopToUpdateProgress();
    }

    @Override
    public void onBuffering() {

    }

    @Override
    public void onError(int errorCode, String errorMsg) {
        //停止更新进度条
        mTimerTask.stopToUpdateProgress();
    }
}
