package com.tiancaijiazu.app.activitys.activitypage.collegeactivitys;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.shopping_activity.POActivity;
import com.tiancaijiazu.app.activitys.shopping_activity.PayActivity;
import com.tiancaijiazu.app.activitys.views.ComFragmentAdapter;
import com.tiancaijiazu.app.base.activity.BaseActivity;
import com.tiancaijiazu.app.beans.CourseToBuyBean;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.fragments.beans.CollegeCourseBean;
import com.tiancaijiazu.app.fragments.outermostlayer.college_fragment.Content_ListFragment;
import com.tiancaijiazu.app.fragments.outermostlayer.college_fragment.Course_decFragment;
import com.tiancaijiazu.app.globals.WechatShareTool;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.PreUtils;
import com.tiancaijiazu.app.utils.status.LoadingLayout;
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

/**
 *
 *   父母学院--课程简介  课程目录
 */

public class ClassListActivity extends BaseActivity<IView, Presenter<IView>> implements IView, AppBarLayout.OnOffsetChangedListener {

    @BindView(R.id.bei)
    ImageView mBei;
    @BindView(R.id.loadingLayout)
    LoadingLayout loadingLayout;
    @BindView(R.id.summary)
    TextView mSummary;
    @BindView(R.id.zong_sum)
    TextView mZongSum;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.iv_finis)
    ImageView mIvFinis;
    @BindView(R.id.title_top)
    TextView mTitleTop;
    @BindView(R.id.line)
    LinearLayout mLine;
    @BindView(R.id.fen_xiang)
    ImageView mFenXiang;
    @BindView(R.id.relative)
    RelativeLayout mRelative;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.app_bar)
    AppBarLayout mAppBar;
    @BindView(R.id.magic_indicator)
    MagicIndicator mMagicIndicator;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.really_sice)
    TextView mReallySice;
    @BindView(R.id.no_money)
    TextView mNoMoney;
    @BindView(R.id.rela)
    RelativeLayout mRela;
    private ArrayList<String> title;
    private ArrayList<Fragment> fragments;
    private String[] mTitles = new String[]{"课程简介", "课程目录"};
    private List<String> mDataList = Arrays.asList(mTitles);
    private CollegeCourseBean.ResultBean mResult;
    private String mTarget;
    private String mCourseId1;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bitmap bitmap = (Bitmap) msg.obj;
            String shareUrl = mResult.getCourseInfo().getShareUrl();
            String name = mResult.getCourseInfo().getTitle();
            String referrerCode = PreUtils.getString("referrerCode", "");
            if (shareUrl.contains("?")) {
                shareUrl = shareUrl + "&referrerCode=" + referrerCode;
            } else {
                shareUrl = shareUrl + "?referrerCode=" + referrerCode;
            }
            switch (msg.arg1) {
                case 1:
                    WechatShareTool.shareToWXUrl(shareUrl, bitmap, name, true,mSummary1,true);
                    break;
                case 2:
                    WechatShareTool.shareToWXUrl(shareUrl, bitmap, name, false,mSummary1,true);
                    break;
            }

        }
    };
    private View mInflate;
    private PopupWindow mPopupWindow1;
    private String mSummary1;

    @Override
    protected void initEventAndData() {
        Intent intent = getIntent();
        //String courseId = intent.getStringExtra("courseId");
        mTarget = intent.getStringExtra("target");
        if (mTarget != null) {
            mPresenter.getDataP1(mTarget, DifferentiateEnum.COURSEID,loadingLayout);
        } else {
            mCourseId1 = PreUtils.getString("courseId", "");
            Log.i("yx789", "initEventAndData: " + mCourseId1);
            mPresenter.getDataP1(mCourseId1, DifferentiateEnum.COURSEID,loadingLayout);
        }
        loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
            @Override
            public void onReload(View v) {
                mPresenter.getDataP1(mCourseId1, DifferentiateEnum.COURSEID,loadingLayout);
            }
        });
        String biao = PreUtils.getString("biao", "");
        if ("ok".equals(biao)) {
            mViewPager.setCurrentItem(1);
        }
        mAppBar.addOnOffsetChangedListener(this);
        initSett();
        initPay();
        PreUtils.putString("biao", "no");
        initPop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        String biao = PreUtils.getString("biao", "");
        if ("ok".equals(biao)) {
            initEventAndData();
        }
    }

    private void initPay() {
        mRela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mResult != null) {
                    int courseType = mResult.getCourseInfo().getCourseType();
                    if (0 == courseType) {
                        long courseId = mResult.getCourseInfo().getCourseId();
                        mPresenter.getDataP1(courseId + "", DifferentiateEnum.COURSETOBUY,loadingLayout);
                        loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
                            @Override
                            public void onReload(View v) {
                                mPresenter.getDataP1(courseId + "", DifferentiateEnum.COURSETOBUY,loadingLayout);
                            }
                        });
                    } else if (1 == courseType) {
                        String title = mResult.getCourseInfo().getTitle();
                        long courseId = mResult.getCourseInfo().getCourseId();
                        String picUri = mResult.getCourseInfo().getPicUri();
                        float promoPrice = mResult.getCourseInfo().getPromoPrice();
                        Intent intent = new Intent(ClassListActivity.this, POActivity.class);
                        intent.putExtra("biao", "course");
                        intent.putExtra("title", title);
                        intent.putExtra("courseId", courseId + "");
                        intent.putExtra("picUri", picUri);
                        intent.putExtra("promoPrice", promoPrice);
                        startActivity(intent);
                    }
                }
            }
        });
    }

    private List<Fragment> getFragments() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(Course_decFragment.getInstance());
        if (mTarget != null) {
            fragments.add(Content_ListFragment.getInstance(mTarget));
        } else {
            fragments.add(Content_ListFragment.getInstance(mCourseId1));
        }

        return fragments;
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        //顶部渐变 标题栏处理
        float percent = Float.valueOf(Math.abs(verticalOffset)) / Float.valueOf(appBarLayout.getTotalScrollRange());
        int alpha = (int) (255 * percent);
        mRelative.setBackgroundColor(Color.argb(alpha, 255, 255, 255));

        if (percent < 0.5) {
            mIvFinis.setImageResource(R.mipmap.topic_finis);
            mFenXiang.setImageResource(R.mipmap.topic_fenxiang);
            initSett();

        } else {
            mIvFinis.setImageResource(R.mipmap.rec_back);
            mFenXiang.setImageResource(R.mipmap.share);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                        | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                window.getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR//设置显示为白色背景，黑色字体
                );
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
            }

        }

        //滑动事件处理
        if (percent == 0) {
            //当完全展开时  appbar可滑动  禁止refresh(可根据需求不禁止刷新)
            //myAppBarLayoutBehavoir.setNoScroll(false);
        } else {
            //滑动中 appbar可滑动 禁止refresh(建议禁止刷新,否则会appbar影响滑动流畅)
            //myAppBarLayoutBehavoir.setNoScroll(false);
        }
    }

    private void initTab() {
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setScrollPivotX(0.65f);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mDataList == null ? 0 : mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {

                SimplePagerTitleViewYx simplePagerTitleView = new ColorFlipPagerTitleViewYx(context);
                simplePagerTitleView.setText(mDataList.get(index));
                simplePagerTitleView.setNormalColor(ContextCompat.getColor(ClassListActivity.this, R.color.colorTextSubhead));
                simplePagerTitleView.setSelectedColor(ContextCompat.getColor(ClassListActivity.this, R.color.colorTextTitleHome));
                simplePagerTitleView.setNormalSize(15);
                simplePagerTitleView.setSelectedSize(15);
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
                indicator.setLineWidth(UIUtil.dip2px(context, 60));
                indicator.setRoundRadius(UIUtil.dip2px(context, 3));
                indicator.setStartInterpolator(new AccelerateInterpolator());
                indicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
                indicator.setColors(ContextCompat.getColor(ClassListActivity.this, R.color.colorZhu));
                return indicator;
            }
        });
        mMagicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mMagicIndicator, mViewPager);
        mIvFinis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mFenXiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow1.showAtLocation(mInflate, Gravity.BOTTOM, 0, 0);
            }
        });
    }

    public void initPop() {
        mInflate = LayoutInflater.from(ClassListActivity.this).inflate(R.layout.pop_share_layout, null);
        mPopupWindow1 = new PopupWindow(mInflate, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow1.setFocusable(true);// 取得焦点
        //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
        mPopupWindow1.setBackgroundDrawable(new BitmapDrawable());
        //点击外部消失
        mPopupWindow1.setOutsideTouchable(true);
        //设置可以点击
        mPopupWindow1.setTouchable(true);
        //进入退出的动画，指定刚才定义的style
        mPopupWindow1.setAnimationStyle(R.style.popwin_anim_style);

        ImageView shareWxFriend = mInflate.findViewById(R.id.share_wx_friend);
        shareWxFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String shareUrl = mResult.getCourseInfo().getShareUrl();
                String picUri = mResult.getCourseInfo().getPicUri();
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
        ImageView shareWx = mInflate.findViewById(R.id.share_wx);
        shareWx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String shareUrl = mResult.getCourseInfo().getShareUrl();
                String picUri = mResult.getCourseInfo().getPicUri();
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
        TextView quxiao = mInflate.findViewById(R.id.quxiao);
        quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindow1.dismiss();
            }
        });
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_class_list;
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum) {
            case COURSEID:
                CollegeCourseBean courseBean = (CollegeCourseBean) o;
                mResult = courseBean.getResult();
                mSummary1 = mResult.getCourseInfo().getSummary();
                String shareUrl = mResult.getCourseInfo().getShareUrl();
                if (shareUrl.equalsIgnoreCase("")) {
                    mFenXiang.setVisibility(View.GONE);
                } else {
                    mFenXiang.setVisibility(View.VISIBLE);
                }
                if (!ClassListActivity.this.isFinishing()) {
                    Glide.with(this).load(mResult.getCourseInfo().getPicUri()).into(mBei);
                }
                PreUtils.putString("description", mResult.getCourseInfo().getDescription());
                float price = mResult.getCourseInfo().getPrice();
                float promoPrice = mResult.getCourseInfo().getPromoPrice();
                int isBought = mResult.getCourseInfo().getIsBought();
                if (isBought == 0) {
                    mRela.setVisibility(View.VISIBLE);
                } else {
                    mRela.setVisibility(View.GONE);
                }
                mReallySice.setText("¥" + promoPrice);
                //添加横线
                mNoMoney.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
                mNoMoney.setText("¥" + price);
                mViewPager.setAdapter(new ComFragmentAdapter(getSupportFragmentManager(), getFragments()));
                mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int i, float v, int i1) {

                    }

                    @Override
                    public void onPageSelected(int i) {
                        Log.i("yx123", "onPageSelected: " + i);

                    }

                    @Override
                    public void onPageScrollStateChanged(int i) {

                    }
                });
                mViewPager.setOffscreenPageLimit(mDataList.size());
                initTab();
                break;
            case COURSETOBUY:
                CourseToBuyBean courseToBuyBean = (CourseToBuyBean) o;
                CourseToBuyBean.ResultBean result = courseToBuyBean.getResult();
                Intent intent = new Intent(ClassListActivity.this, PayActivity.class);
                intent.putExtra("data", result);
                intent.putExtra("biao", "course");
                startActivity(intent);
                break;
        }
    }

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }

    //设置状态栏与状态栏字体颜色
    private void initSett() {
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