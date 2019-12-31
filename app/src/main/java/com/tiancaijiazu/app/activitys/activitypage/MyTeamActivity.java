package com.tiancaijiazu.app.activitys.activitypage;

import android.Manifest;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.team.TeamFragment;
import com.tiancaijiazu.app.activitys.views.ComFragmentAdapter;
import com.tiancaijiazu.app.base.activity.BaseActivity;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.mvp.Presenter;
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
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;




public class MyTeamActivity extends BaseActivity<IView, Presenter<IView>> implements IView {


    @BindView(R.id.iv_finis)
    ImageView ivFinis;
    @BindView(R.id.a)
    TextView a;

    @BindView(R.id.magic_indicator)
    MagicIndicator mMagicIndicator;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;


    private int page = 1;
    private String[] mTitles = new String[]{"全部", "推广大使","代言人", "园长"};
    private List<String> mDataList = Arrays.asList(mTitles);
    private String[] vipLevel = new String[]{"-999", "11", "21", "31"};
    private List<String> mvipLevelList = Arrays.asList(vipLevel);

    @Override
    protected void initEventAndData() {
        ScreenStatusUtil.setFillDip(this);

        ArrayList<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < mvipLevelList.size(); i++) {
            fragments.add(TeamFragment.getInstance(mvipLevelList.get(i)));
        }
        mViewPager.setAdapter(new ComFragmentAdapter(getSupportFragmentManager(), fragments));
        mViewPager.setOffscreenPageLimit(mDataList.size());
        initTab();
    }

    private void initTab() {
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setScrollPivotX(0.65f);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setEnablePivotScroll(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mDataList == null ? 0 : mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleViewYx simplePagerTitleView = new ColorFlipPagerTitleViewYx(context);
                simplePagerTitleView.setText(mDataList.get(index));
                simplePagerTitleView.setNormalColor(ContextCompat.getColor(MyTeamActivity.this, R.color.colorTextSubhead));
                simplePagerTitleView.setSelectedColor(ContextCompat.getColor(MyTeamActivity.this, R.color.colorTextTitleHome));
                simplePagerTitleView.setSelectedSize(17);
                simplePagerTitleView.setNormalSize(15);
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
                indicator.setLineWidth(UIUtil.dip2px(context, 22));
                indicator.setRoundRadius(UIUtil.dip2px(context, 1));
                indicator.setStartInterpolator(new AccelerateInterpolator());
                indicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
                indicator.setColors(ContextCompat.getColor(MyTeamActivity.this, R.color.colorZhu));
                return indicator;
            }
        });
        mMagicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mMagicIndicator, mViewPager);
    }





    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_my_team;
    }

    @Override
    public void showError(String error) {

    }


    @Override
    protected Presenter createPresenter() {
        return new Presenter();
    }

    @OnClick(R.id.iv_finis)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_finis:
                finish();
                break;
        }
    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {

    }

}
