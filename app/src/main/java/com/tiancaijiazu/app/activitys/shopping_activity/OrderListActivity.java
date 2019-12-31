package com.tiancaijiazu.app.activitys.shopping_activity;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.shopping_activity.fragments.AccountPaidFragment;
import com.tiancaijiazu.app.activitys.shopping_activity.fragments.NonPaymentFragment;
import com.tiancaijiazu.app.activitys.views.ColorFlipPagerTitleView;
import com.tiancaijiazu.app.activitys.views.ComFragmentAdapter;
import com.tiancaijiazu.app.base.activity.SimpleActivity;
import com.tiancaijiazu.app.utils.tabmin.ColorFlipPagerTitleViewYx;
import com.tiancaijiazu.app.utils.tabmin.SimplePagerTitleViewYx;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderListActivity extends SimpleActivity {

    @BindView(R.id.iv_finis)
    ImageView mIvFinis;@BindView(R.id.a)
    TextView a;
    @BindView(R.id.rela)
    RelativeLayout mRela;
    @BindView(R.id.vp)
    ViewPager mVp;
    @BindView(R.id.magic_indicator)
    MagicIndicator mMagicIndicator;
    private String[] mTitles = new String[]{"已付款", "待付款"};
    private List<String> mDataList = Arrays.asList(mTitles);

    @Override
    protected void initEventAndData() {
        initSett();
        mVp.setAdapter(new ComFragmentAdapter(getSupportFragmentManager(), getFragments()));
        mVp.setOffscreenPageLimit(10);
        initTab();
    }

    private List<Fragment> getFragments() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(AccountPaidFragment.getInstance());
        fragments.add(NonPaymentFragment.getInstance());
        return fragments;
    }

    private void initTab() {
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setScrollPivotX(0.65f);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mDataList == null ? 0 : mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleViewYx simplePagerTitleView = new ColorFlipPagerTitleViewYx(context);
                simplePagerTitleView.setText(mDataList.get(index));
                simplePagerTitleView.setNormalColor(ContextCompat.getColor(OrderListActivity.this, R.color.colorTextTitle));
                simplePagerTitleView.setSelectedColor(ContextCompat.getColor(OrderListActivity.this, R.color.shopping));
                simplePagerTitleView.setNormalSize(14);
                simplePagerTitleView.setSelectedSize(14);
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mVp.setCurrentItem(index, false);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setLineHeight(UIUtil.dip2px(context, 2));
                indicator.setLineWidth(UIUtil.dip2px(context, 27));
                indicator.setRoundRadius(UIUtil.dip2px(context, 1));
                indicator.setStartInterpolator(new AccelerateInterpolator());
                indicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
                indicator.setColors(ContextCompat.getColor(OrderListActivity.this, R.color.shopping));
                return indicator;
            }
        });
        mMagicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mMagicIndicator, mVp);
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_order_list;
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
                            | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR//设置显示为白色背景，黑色字体
            );
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }


    @OnClick(R.id.iv_finis)
    public void onViewClicked() {
        finish();
    }

}
