package com.tiancaijiazu.app.activitys.early;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.activitypage.collegeactivitys.ClockActivity;
import com.tiancaijiazu.app.activitys.early.adapters.VpAdapter_early;
import com.tiancaijiazu.app.activitys.early.fragments.CourseFragment;
import com.tiancaijiazu.app.activitys.early.fragments.FormalLessonsFragment;
import com.tiancaijiazu.app.activitys.early.fragments.RecordFragment;
import com.tiancaijiazu.app.activitys.early.fragments.TrialClassFragment;
import com.tiancaijiazu.app.activitys.views.ComFragmentAdapter;
import com.tiancaijiazu.app.base.activity.BaseActivity;
import com.tiancaijiazu.app.beans.FormalCurriculumBean;
import com.tiancaijiazu.app.beans.UserCardTypeBean;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.globals.WechatShareTool;
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

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *
 * 试听课", "正式课", "看记录
 */

public class EarlyActivity extends BaseActivity<IView, Presenter<IView>> implements IView {


    @BindView(R.id.iv_finis)
    ImageView mIvFinis;
    @BindView(R.id.magic_indicator)
    MagicIndicator mMagicIndicator;
    @BindView(R.id.share)
    ImageView mShare;
    @BindView(R.id.sharenaoling)
    ImageView sharenaoling;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    private String[] mTitles;
    private List<String> mDataList;
    private PopupWindow popupWindow;
    private VpAdapter_early mVpAdapterEarly;
    private FormalCurriculumBean.ResultBean.CourseInfoBean result1;
    private String shareUrl;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bitmap bitmap = (Bitmap) msg.obj;
            String shareUrl = result1.getShareUrl();
            String name = result1.getTitle();
            switch (msg.arg1) {
                case 1:
                    WechatShareTool.shareToWXUrl(shareUrl, bitmap, name, true,"",false);
                    break;
                case 2:
                    WechatShareTool.shareToWXUrl(shareUrl, bitmap, name, false,"",false);
                    break;
            }

        }
    };
    private Intent mIntent;

    @Override
    protected void initEventAndData() {

        ScreenStatusUtil.setFillDip(this);
        mIntent = getIntent();
        mPresenter.getDataP("", DifferentiateEnum.USERCARDTYPE);
        mPresenter.getDataP("", DifferentiateEnum.FORMALCURRICULUM);
        //ActivityManageUtils.getInstance().addActivity(this);
        mIvFinis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(24,mIntent);
                finish();
            }
        });
        initPop();
        PreUtils.putString("cards", "no");
        mShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View inflate = LayoutInflater.from(EarlyActivity.this).inflate(R.layout.pop_share_layout, null);
                PopupWindow popupWindow1 = new PopupWindow(inflate, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                popupWindow1.setFocusable(true);// 取得焦点
                //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
                popupWindow1.setBackgroundDrawable(new BitmapDrawable());
                //点击外部消失
                popupWindow1.setOutsideTouchable(true);
                //设置可以点击
                popupWindow1.setTouchable(true);
                //进入退出的动画，指定刚才定义的style
                popupWindow1.setAnimationStyle(R.style.popwin_anim_style);
                popupWindow1.showAtLocation(inflate, Gravity.BOTTOM, 0, 0);
                ImageView shareWxFriend = inflate.findViewById(R.id.share_wx_friend);
                shareWxFriend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String shareUrl = result1.getShareUrl();
                        String picUri = result1.getPicUri();
                        String name = result1.getTitle();
                        if (shareUrl != null) {
                            new Thread() {
                                @Override
                                public void run() {
                                    try {
                                        Bitmap thumb = BitmapFactory.decodeStream(new URL(picUri).openStream());
                                        Message obtain = Message.obtain();
                                        obtain.obj = thumb;
                                        obtain.arg1 = 1;
                                        handler.sendMessage(obtain);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }.start();
                        }
                    }
                });
                ImageView shareWx = inflate.findViewById(R.id.share_wx);
                shareWx.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String shareUrl = result1.getShareUrl();
                        String picUri = result1.getPicUri();
                        String name = result1.getTitle();
                        if (shareUrl != null) {
                            new Thread() {
                                @Override
                                public void run() {
                                    try {
                                        Bitmap thumb = BitmapFactory.decodeStream(new URL(picUri).openStream());
                                        Message obtain = Message.obtain();
                                        obtain.obj = thumb;
                                        obtain.arg1 = 2;
                                        handler.sendMessage(obtain);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }.start();
                        }
                    }
                });
                TextView quxiao = inflate.findViewById(R.id.quxiao);
                quxiao.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow1.dismiss();
                    }
                });
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        setResult(24,mIntent);
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        String cards = PreUtils.getString("cards", "");
        if ("ok".equals(cards)) {
            initEventAndData();
        }
        super.onNewIntent(intent);
        setIntent(intent);
        //here we can use getIntent() to get the extra data.

    }

    /**
     * 改变背景颜色
     */
    private void darkenBackground(Float bgcolor) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgcolor;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setAttributes(lp);

    }

    private void initTab() {
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setScrollPivotX(0.65f);
        commonNavigator.setAdjustMode(false);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mDataList == null ? 0 : mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleViewYx simplePagerTitleView = new ColorFlipPagerTitleViewYx(context);
                simplePagerTitleView.setText(mDataList.get(index));
                simplePagerTitleView.setNormalColor(ContextCompat.getColor(EarlyActivity.this, R.color.colorTextSubhead));
                simplePagerTitleView.setSelectedColor(ContextCompat.getColor(EarlyActivity.this, R.color.colorTextTitleHome));
                simplePagerTitleView.setNormalSize(15);
                simplePagerTitleView.setSelectedSize(17);
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(index, false);
                    }
                });
                return simplePagerTitleView;
            }
            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setLineHeight(UIUtil.dip2px(context, 2));
                indicator.setLineWidth(UIUtil.dip2px(context, 30));
                indicator.setRoundRadius(UIUtil.dip2px(context, 3));
                indicator.setStartInterpolator(new AccelerateInterpolator());
                indicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
                indicator.setColors(ContextCompat.getColor(EarlyActivity.this, R.color.viphuiyuan));
                return indicator;
            }
        });
        mMagicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mMagicIndicator, mViewPager);
    }

    private List<Fragment> getFragments() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(TrialClassFragment.getInstance());
        fragments.add(FormalLessonsFragment.getInstance());
        fragments.add(RecordFragment.getInstance());
        return fragments;
    }

    private List<Fragment> getFragmentsOfficial() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(CourseFragment.getInstance());
        fragments.add(RecordFragment.getInstance());
        return fragments;
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_early;
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum) {
            case USERCARDTYPE:
                UserCardTypeBean userCardTypeBean = (UserCardTypeBean) o;
                UserCardTypeBean.ResultBean result = userCardTypeBean.getResult();
                int cardType = result.getCardType();
                if (cardType == -1 || cardType == 0) {
                    PreUtils.putString("changed", "yes");
                    mTitles = new String[]{"试听课", "正式课", "看记录"};
                    mDataList = Arrays.asList(mTitles);
                    mVpAdapterEarly = new VpAdapter_early(getSupportFragmentManager(), mDataList, getFragments());
                    if (mViewPager != null) {
                        mViewPager.setAdapter(mVpAdapterEarly);
                        mViewPager.setOffscreenPageLimit(mTitles.length);
                    }

                } else {
                    mTitles = new String[]{"课程", "记录"};
                    mDataList = Arrays.asList(mTitles);
                    if (mViewPager != null) {
                        mViewPager.setAdapter(new ComFragmentAdapter(getSupportFragmentManager(), getFragmentsOfficial()));
                        mViewPager.setOffscreenPageLimit(mTitles.length);
                    }

                }
                initTab();
                break;
            case FORMALCURRICULUM:
                FormalCurriculumBean formalCurriculumBean = (FormalCurriculumBean) o;
                 result1 = formalCurriculumBean.getResult().getCourseInfo();
                shareUrl = result1.getShareUrl();
                if ("".equals(shareUrl)) {
                    mShare.setVisibility(View.GONE);
                } else {
                    mShare.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }

    @OnClick({R.id.share, R.id.sharenaoling})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.sharenaoling:
                Intent intent1 = new Intent(EarlyActivity.this, ClockActivity.class);
                startActivity(intent1);
                break;
        }
    }

    //购买卡片
    private void initPop() {
        popupWindow = new PopupWindow();

        popupWindow.setWidth(ActionBar.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ActionBar.LayoutParams.WRAP_CONTENT);
        View inflate = LayoutInflater.from(mActivity).inflate(R.layout.pop_car_ok, null, false);
        popupWindow.setContentView(inflate);
        popupWindow.setOutsideTouchable(true);
        //设置背景透明才能显示
        popupWindow.setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                darkenBackground(1f);
            }
        });
    }
}
