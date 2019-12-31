package com.tiancaijiazu.app.activitys.topic;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.issue.MOPPActivity;
import com.tiancaijiazu.app.activitys.topic.fragments.HotFragment;
import com.tiancaijiazu.app.activitys.topic.fragments.NewestFragment;
import com.tiancaijiazu.app.activitys.views.ComFragmentAdapter;
import com.tiancaijiazu.app.base.activity.BaseActivity;
import com.tiancaijiazu.app.beans.TopicDataBean;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.mvp.Presenter;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class TopicCentrectivity extends BaseActivity<IView, Presenter<IView>> implements IView, AppBarLayout.OnOffsetChangedListener {

    @BindView(R.id.bei)
    ImageView mBei;
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
    @BindView(R.id.can_yu)
    TextView mCanYu;
    private String[] mTitles = new String[]{"热门", "最新"};
    private List<String> mDataList = Arrays.asList(mTitles);
    private int page = 1;
    private HotFragment mInstance;
    private String mSubjectId;
    // private NoScrollBehavior myAppBarLayoutBehavoir;

    @Override
    protected void initEventAndData() {
        Intent intent = getIntent();
        mSubjectId = intent.getStringExtra("subjectId");
        HashMap<String, Object> map = new HashMap<>();
        map.put("subjectId", mSubjectId);
        map.put("page", page);
        map.put("orderBy", 1);
        mPresenter.getDataP(map, DifferentiateEnum.TOPICDATAS);
        /*CoordinatorLayout.LayoutParams params = new CoordinatorLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setBehavior(new AppBarLayoutSpringBehavior());
        mAppBar.setLayoutParams(params);*/
        initSett();
        //initView();
        mViewPager.setAdapter(new ComFragmentAdapter(getSupportFragmentManager(), getFragments()));
        mViewPager.setOffscreenPageLimit(10);
        initTab();
        mAppBar.addOnOffsetChangedListener(this);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        //顶部渐变 标题栏处理
        float percent = Float.valueOf(Math.abs(verticalOffset)) / Float.valueOf(appBarLayout.getTotalScrollRange());
        int alpha = (int) (255 * percent);
        mRelative.setBackgroundColor(Color.argb(alpha, 255, 255, 255));
        mLine.setAlpha(alpha);

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

    private List<Fragment> getFragments() {
        List<Fragment> fragments = new ArrayList<>();
        mInstance = HotFragment.getInstance();
        NewestFragment instance = NewestFragment.getInstance();
        fragments.add(mInstance);
        fragments.add(instance);
        Bundle bundle = new Bundle();
        bundle.putString("data", mSubjectId + "");
        mInstance.setArguments(bundle);
        instance.setArguments(bundle);
        return fragments;
    }

    /*private void initView() {
        myAppBarLayoutBehavoir =  mAppBar.getLayoutParams().getBehavior();
    }*/

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
                simplePagerTitleView.setNormalColor(ContextCompat.getColor(TopicCentrectivity.this, R.color.colorTextSubhead));
                simplePagerTitleView.setSelectedColor(ContextCompat.getColor(TopicCentrectivity.this, R.color.colorLightRed));
                simplePagerTitleView.setNormalSize(16);
                simplePagerTitleView.setSelectedSize(16);
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
                indicator.setLineWidth(UIUtil.dip2px(context, 20));
                indicator.setRoundRadius(UIUtil.dip2px(context, 3));
                indicator.setStartInterpolator(new AccelerateInterpolator());
                indicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
                indicator.setColors(ContextCompat.getColor(TopicCentrectivity.this, R.color.colorLightRed));
                return indicator;
            }
        });
        mMagicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mMagicIndicator, mViewPager);
    }


    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_topic_centrectivity;
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum) {
            case TOPICDATAS:
                TopicDataBean topicDataBean = (TopicDataBean) o;
                TopicDataBean.ResultBean result = topicDataBean.getResult();
                Glide.with(this).load(result.getSubject().getPicUri()).into(mBei);
                mTitleTop.setText(result.getSubject().getSubjectName());
                mTitle.setText(result.getSubject().getSubjectName());
                mZongSum.setText(result.getSubject().getArticleCount() + "篇笔记");

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


    @OnClick({R.id.iv_finis,R.id.can_yu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_finis:
                finish();
                break;
            case R.id.can_yu:
                Intent intent = new Intent(this, MOPPActivity.class);
                startActivity(intent);
                break;
        }
    }
}
