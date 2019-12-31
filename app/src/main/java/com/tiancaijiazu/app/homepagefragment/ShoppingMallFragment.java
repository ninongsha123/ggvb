package com.tiancaijiazu.app.homepagefragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.issue.MOPP1Activity;
import com.tiancaijiazu.app.activitys.issue.MOPPActivity;
import com.tiancaijiazu.app.activitys.views.ComFragmentAdapter;
import com.tiancaijiazu.app.base.fragment.SimpleFragment;
import com.tiancaijiazu.app.fragments.outermostlayer.shoppingmallfragment.GroupFragment;
import com.tiancaijiazu.app.fragments.outermostlayer.shoppingmallfragment.HOT_SPOTFragment;
import com.tiancaijiazu.app.fragments.outermostlayer.shoppingmallfragment.RecommendFragment;
import com.tiancaijiazu.app.utils.ScreenStatusUtil;
import com.tiancaijiazu.app.utils.TimeUtil;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 *
 *  家族圈
 */
public class ShoppingMallFragment extends SimpleFragment {


    @BindView(R.id.magic_indicator)
    MagicIndicator mTab;
    @BindView(R.id.vp)
    ViewPager mVp;
    @BindView(R.id.issue)
    ImageView mIssue;
    Unbinder unbinder1;
    @BindView(R.id.relative)
    RelativeLayout mRelative;
    Unbinder unbinder;
    private String[] mTitles = new String[]{"推荐", "关注","成长"};
    private List<String> mDataList = Arrays.asList(mTitles);
    private View mInflate1;
    private PopupWindow PopupWindows;
    private PopupWindow mPopupWindow;
    private View mInflate;

    public static ShoppingMallFragment newInstance() {
        ShoppingMallFragment fragment = new ShoppingMallFragment();
        return fragment;
    }

    public static boolean isFirstStart(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                "SHARE_APP_TAG", 0);
        Boolean isFirst = preferences.getBoolean("FIRSTStarts", true);
        if (isFirst) {// 第一次
            preferences.edit().putBoolean("FIRSTStarts", false).commit();
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.fragment_shopping_mall;
    }

    @SuppressLint("ResourceType")
    @Override
    protected void initData() {
        // 判断是否是第一次开启应用
        if(isFirstStart(getContext())==true){
            // 如果是第一次启动
            showHint();
        }else {
            creatrLayoutId();
        }
        ScreenStatusUtil.setNotStatus(getContext(),mRelative);
        mVp.setAdapter(new ComFragmentAdapter(getChildFragmentManager(), getFragments()));
        mVp.setOffscreenPageLimit(mDataList.size());
        initTab();
    }

    private void showHint() {
        mInflate1 = LayoutInflater.from(getContext()).inflate(R.layout.pop_hint_family, null);
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

    private void initTab() {
        CommonNavigator commonNavigator = new CommonNavigator(getContext());
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
                simplePagerTitleView.setNormalColor(ContextCompat.getColor(getContext(), R.color.colorTextSubhead));
                simplePagerTitleView.setSelectedColor(ContextCompat.getColor(getContext(), R.color.colorTextTitleHome));
                simplePagerTitleView.setSelectedSize(17);
                simplePagerTitleView.setNormalSize(15);
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
                indicator.setLineWidth(UIUtil.dip2px(context, 28));
                indicator.setRoundRadius(UIUtil.dip2px(context, 1));
                indicator.setStartInterpolator(new AccelerateInterpolator());
                indicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
                indicator.setColors(ContextCompat.getColor(getContext(), R.color.colorZhu));
                return indicator;
            }
        });
        mTab.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mTab, mVp);
    }

    private List<Fragment> getFragments() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add( new RecommendFragment());
        fragments.add(new HOT_SPOTFragment());
        fragments.add(new GroupFragment());
        return fragments;
    }

    @OnClick(R.id.issue)
    public void onViewClicked() {
        WindowManager.LayoutParams attributes = getActivity().getWindow().getAttributes();
        attributes.alpha=0.5f;
        getActivity().getWindow().setAttributes(attributes);
        showPop();
    }
    private void showPop() {
        mInflate = LayoutInflater.from(getContext()).inflate(R.layout.pop_issue_layout, null);
        mPopupWindow = new PopupWindow(mInflate,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setFocusable(true);// 取得焦点
        mPopupWindow.setClippingEnabled(false);
        //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        //点击外部消失
        mPopupWindow.setOutsideTouchable(true);
        //设置可以点击
        mPopupWindow.setTouchable(true);
        //进入退出的动画，指定刚才定义的style
        mPopupWindow.setAnimationStyle(R.style.popwin_anim_style);

        ImageView one = mInflate.findViewById(R.id.issueone);
        ImageView closes = mInflate.findViewById(R.id.closes);

        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MOPPActivity.class);
                startActivityForResult(intent, 111);
                mPopupWindow.dismiss();
            }
        });
        ImageView two = mInflate.findViewById(R.id.issuetwo);
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MOPP1Activity.class);
                startActivityForResult(intent, 111);
                mPopupWindow.dismiss();
            }
        });
        closes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindow.dismiss();
            }
        });
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams attributes = getActivity().getWindow().getAttributes();
                attributes.alpha=1;
                getActivity().getWindow().setAttributes(attributes);
            }
        });
        mPopupWindow.showAtLocation(mInflate, Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 111 && resultCode == 112) {
            initData();
        }else if (resultCode == 113){
            mVp.setCurrentItem(2);
        }
    }
}