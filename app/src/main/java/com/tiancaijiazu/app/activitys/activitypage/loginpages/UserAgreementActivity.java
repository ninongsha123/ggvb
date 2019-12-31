package com.tiancaijiazu.app.activitys.activitypage.loginpages;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.base.activity.SimpleActivity;
import com.tiancaijiazu.app.utils.ScreenStatusUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *
 * 登录入口页面-用户协议
 */

public class UserAgreementActivity extends SimpleActivity {


    @BindView(R.id.iv_finis)
    ImageView mIvFinis;
    @BindView(R.id.a)
    TextView mA;

    @Override
    protected void initEventAndData() {
        ScreenStatusUtil.setFillDip(this);
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_user_agreement;
    }

    @OnClick(R.id.iv_finis)
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.iv_finis:
                finish();
                break;
        }
    }
}
