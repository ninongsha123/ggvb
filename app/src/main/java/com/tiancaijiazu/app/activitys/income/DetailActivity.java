package com.tiancaijiazu.app.activitys.income;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.views.ComFragmentAdapter;
import com.tiancaijiazu.app.base.activity.BaseActivity;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.fragments.adapters.VpAdapter;
import com.tiancaijiazu.app.mvp.IView;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.MediumBoldTextViewTitle;
import com.tiancaijiazu.app.utils.ScreenStatusUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailActivity extends BaseActivity<IView, Presenter<IView>> implements IView {

    @BindView(R.id.iv_finis)
    ImageView mIvFinis;
    @BindView(R.id.a)
    MediumBoldTextViewTitle mA;
    @BindView(R.id.relative)
    RelativeLayout mRelative;
    @BindView(R.id.vps)
    ViewPager mVps;
    @BindView(R.id.tab)
    TabLayout mTab;
    private ArrayList<String> mTitles;


    @Override
    protected void initEventAndData() {
        ScreenStatusUtil.setFillDip(this);
        initTab();
    }

    private void initTab() {
        mTitles = new ArrayList<>();
        mTitles.add("课程推荐");
        mTitles.add("购买商品");
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new CommonsFragment());
        fragments.add(new RepostFragment());
        VpAdapter vpAdapter = new VpAdapter(getSupportFragmentManager(), mTitles, fragments);
        mVps.setAdapter(vpAdapter);
        mTab.setupWithViewPager(mVps);
        mTab.setTabMode(TabLayout.MODE_FIXED);
    }




    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_detail;
    }

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
    }

    @Override
    public void showError(String error) {

    }

    @OnClick({R.id.iv_finis})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_finis:
                finish();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }
}
