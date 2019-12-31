package com.tiancaijiazu.app.activitys.first_into;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.first_into.adapter.GuideViewPagerAdapter;
import com.tiancaijiazu.app.globals.AppConstants;
import com.tiancaijiazu.app.utils.SpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class WelcomeGuideActivity extends Activity implements View.OnClickListener {
    private GuideViewPagerAdapter adapter;
    private List<View> views;

    // 引导页图片资源
    private static final int[] pics = {R.layout.guid_view1,
            R.layout.guid_view2, R.layout.guid_view3};

    // 底部小点图片
    private ImageView[] dots;

    // 记录当前选中位置
    private int currentIndex;
    private Button mBtnEnter;
    private ViewPager mVpGuide;
    private LinearLayout mLl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_guide);
        initView();
        views = new ArrayList<View>();

        // 初始化引导页视图列表
        for (int i = 0; i < pics.length; i++) {
            View view = LayoutInflater.from(getApplicationContext()).inflate(pics[i], null, false);
            if (i == pics.length - 1) {
                mBtnEnter = (Button) view.findViewById(R.id.btn_login);
                mBtnEnter.setTag("enter");
                mBtnEnter.setOnClickListener(this);
            }

            views.add(view);

        }
        adapter = new GuideViewPagerAdapter(views);
        mVpGuide.setAdapter(adapter);
        mVpGuide.setOnPageChangeListener(new PageChangeListener());

        initDots();
    }


    private void initDots() {
        dots = new ImageView[pics.length];

        // 循环取得小点图片
        for (int i = 0; i < pics.length; i++) {
            // 得到一个LinearLayout下面的每一个子元素
            dots[i] = (ImageView) mLl.getChildAt(i);
            dots[i].setEnabled(false);// 都设为灰色
            dots[i].setOnClickListener(this);
            dots[i].setTag(i);// 设置位置tag，方便取出与当前位置对应
        }

        currentIndex = 0;
        dots[currentIndex].setEnabled(true); // 设置为白色，即选中状态

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 如果切换到后台，就设置下次不进入功能引导页
        SpUtils.putBoolean(WelcomeGuideActivity.this, AppConstants.FIRST_OPEN, true);
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 设置当前view
     *
     * @param position
     */
    private void setCurView(int position) {
        if (position < 0 || position >= pics.length) {
            return;
        }
        mVpGuide.setCurrentItem(position);
    }

    /**
     * 设置当前指示点
     *
     * @param position
     */
    private void setCurDot(int position) {
        if (position < 0 || position > pics.length || currentIndex == position) {
            return;
        }
        dots[position].setEnabled(true);
        dots[currentIndex].setEnabled(false);
        currentIndex = position;
    }

    @Override
    public void onClick(View v) {
        if (v.getTag().equals("enter")) {
            enterMainActivity();
            return;
        }

        int position = (Integer) v.getTag();
        setCurView(position);
        setCurDot(position);
    }


    private void enterMainActivity() {
        Intent intent = new Intent(WelcomeGuideActivity.this,
                SplashActivity.class);
        startActivity(intent);
        SpUtils.putBoolean(WelcomeGuideActivity.this, AppConstants.FIRST_OPEN, true);
        finish();
    }

    private void initView() {
        mVpGuide = (ViewPager) findViewById(R.id.vp_guide);
        mLl = (LinearLayout) findViewById(R.id.ll);
    }

    private class PageChangeListener implements ViewPager.OnPageChangeListener {
        // 当滑动状态改变时调用
        @Override
        public void onPageScrollStateChanged(int position) {
            // arg0 ==1的时辰默示正在滑动，arg0==2的时辰默示滑动完毕了，arg0==0的时辰默示什么都没做。

        }

        // 当前页面被滑动时调用
        @Override
        public void onPageScrolled(int position, float arg1, int arg2) {
            // arg0 :当前页面，及你点击滑动的页面
            // arg1:当前页面偏移的百分比
            // arg2:当前页面偏移的像素位置

        }

        // 当新的页面被选中时调用
        @Override
        public void onPageSelected(int position) {
            // 设置底部小点选中状态
            setCurDot(position);
        }

    }
}
