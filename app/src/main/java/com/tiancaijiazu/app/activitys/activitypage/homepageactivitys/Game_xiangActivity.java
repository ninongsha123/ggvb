package com.tiancaijiazu.app.activitys.activitypage.homepageactivitys;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.base.activity.SimpleActivity;
import com.tiancaijiazu.app.utils.ScreenStatusUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 *
 * 首页-亲子小游戏时间-详情
 */
public class Game_xiangActivity extends SimpleActivity {


    @BindView(R.id.iv_finis)
    ImageView ivFinis;

    @Override
    protected void initEventAndData() {
        ScreenStatusUtil.setFillDip(this);
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_game_xiang;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.iv_finis)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_finis:
                finish();
                break;
        }
    }
}
