package com.tiancaijiazu.app.activitys.issue;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.issue.adapters.PhotoVpAdapter;
import com.tiancaijiazu.app.base.activity.SimpleActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotoChaActivity extends SimpleActivity {


    @BindView(R.id.viewPager)
    ViewPager mViewPager;


    @Override
    protected void initEventAndData() {
        initSett();
        Intent intent = getIntent();
        String image = intent.getStringExtra("image");
        PhotoVpAdapter photoVpAdapter = new PhotoVpAdapter(image, this);
        mViewPager.setAdapter(photoVpAdapter);
        photoVpAdapter.setOnClickLisiter(new PhotoVpAdapter.onClickLisiter() {
            @Override
            public void onClicker() {
                finish();
            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_photo_cha;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
