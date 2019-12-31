package com.tiancaijiazu.app.activitys.shopping_activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.shopping_activity.adapter.LookPhotoVpAdapter;
import com.tiancaijiazu.app.base.activity.SimpleActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LookPhotoActivity extends SimpleActivity {


    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.page_number)
    TextView mPageNumber;
    private int position;

    @Override
    protected void initEventAndData() {
        initSett();
        Intent intent = getIntent();
        ArrayList<String> imagesData = intent.getStringArrayListExtra("images");
        position = intent.getIntExtra("position", 0);
        Log.d("bjg", "initEventAndData: "+imagesData.size());
        mPageNumber.setText((position+1)+"/"+imagesData.size());
        LookPhotoVpAdapter lookPhotoVpAdapter = new LookPhotoVpAdapter(imagesData,this);
        mViewPager.setAdapter(lookPhotoVpAdapter);
        lookPhotoVpAdapter.setOnClickLisiter(new LookPhotoVpAdapter.onClickLisiter() {
            @Override
            public void onClicker() {
                finish();
            }
        });
        mViewPager.setCurrentItem(position);
        mViewPager.setOffscreenPageLimit(imagesData.size());
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                mPageNumber.setText((i + 1) + "/" + imagesData.size());
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_look_photo;
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP)
        {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
