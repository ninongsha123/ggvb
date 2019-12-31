package com.tiancaijiazu.app.activitys.activitypage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.activitypage.homepageactivitys.Game_xiangActivity;
import com.tiancaijiazu.app.base.activity.SimpleActivity;
import com.tiancaijiazu.app.utils.ScreenStatusUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GameActivity extends SimpleActivity {


    @BindView(R.id.iv_finis)
    ImageView ivFinis; @BindView(R.id.a)
    TextView a;
    @BindView(R.id.share)
    ImageView share;
    @BindView(R.id.rl1)
    RelativeLayout rl1;
    @BindView(R.id.rl2)
    RelativeLayout rl2;
    @BindView(R.id.rl3)
    RelativeLayout rl3;

    @Override
    protected void initEventAndData() {
        ScreenStatusUtil.setFillDip(this);
        initRecler();
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_game;
    }


    private void initRecler() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.rl1, R.id.rl2, R.id.rl3, R.id.iv_finis})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl1:
                Intent in = new Intent(GameActivity.this, Game_xiangActivity.class);
                startActivity(in);
                break;
            case R.id.rl2:
                Intent in2 = new Intent(GameActivity.this, Game_xiangActivity.class);
                startActivity(in2);
                break;
            case R.id.rl3:
                Intent in3 = new Intent(GameActivity.this, Game_xiangActivity.class);
                startActivity(in3);
                break;
            case R.id.iv_finis:
                finish();
                break;
        }
    }
}
