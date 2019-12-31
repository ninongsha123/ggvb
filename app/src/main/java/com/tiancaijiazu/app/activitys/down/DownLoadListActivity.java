package com.tiancaijiazu.app.activitys.down;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.down.fragments.DownAudioFragment;
import com.tiancaijiazu.app.activitys.down.fragments.DownVideoFragment;
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

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DownLoadListActivity extends BaseActivity<IView, Presenter<IView>> implements IView {

    @BindView(R.id.iv_finis)
    ImageView mIvFinis;
    @BindView(R.id.magic_indicator)
    MagicIndicator mMagicIndicator;
    @BindView(R.id.relative)
    RelativeLayout mRelative;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.bianji)
    TextView mBianji;
    @BindView(R.id.all)
    TextView mAll;
    @BindView(R.id.delete)
    TextView mDelete;
    @BindView(R.id.bian)
    LinearLayout mBian;
    private String[] mTitles = new String[]{"视频", "音频"};
    private List<String> mDataList = Arrays.asList(mTitles);

    @Override
    protected void initEventAndData() {
        ScreenStatusUtil.setFillDip(this);
        mViewPager.setAdapter(new ComFragmentAdapter(getSupportFragmentManager(), getFragments()));
        mViewPager.setOffscreenPageLimit(10);
        initTab();
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
                simplePagerTitleView.setNormalColor(ContextCompat.getColor(DownLoadListActivity.this, R.color.colorTextSubhead));
                simplePagerTitleView.setSelectedColor(ContextCompat.getColor(DownLoadListActivity.this, R.color.colorTextTitleHome));
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
                indicator.setColors(ContextCompat.getColor(DownLoadListActivity.this, R.color.colorZhu));
                return indicator;
            }
        });
        mMagicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mMagicIndicator, mViewPager);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                String s = mBianji.getText().toString();
                if("取消".equals(s)){
                    mBianji.setText("编辑");
                    mBian.setVisibility(View.GONE);
                    EventBus.getDefault().post("cancel");
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private List<Fragment> getFragments() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new DownVideoFragment());
        fragments.add(new DownAudioFragment());
        return fragments;
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_down_load_list;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_finis, R.id.bianji,R.id.all,R.id.delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_finis:
                finish();
                break;
            case R.id.bianji:
                String s = mBianji.getText().toString();
                if("编辑".equals(s)){
                    mBianji.setText("取消");
                    mBian.setVisibility(View.VISIBLE);
                    EventBus.getDefault().post("biaoji");
                }else if("取消".equals(s)){
                    mBianji.setText("编辑");
                    mBian.setVisibility(View.GONE);
                    EventBus.getDefault().post("cancel");
                }
                break;
            case R.id.all:
                EventBus.getDefault().post("all");
                break;
            case R.id.delete:
                EventBus.getDefault().post("delete");
                mBian.setVisibility(View.GONE);
                mBianji.setText("编辑");
                break;
        }
    }
}
