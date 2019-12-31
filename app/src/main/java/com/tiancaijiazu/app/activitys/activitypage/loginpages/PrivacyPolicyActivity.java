package com.tiancaijiazu.app.activitys.activitypage.loginpages;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.base.activity.SimpleActivity;
import com.tiancaijiazu.app.utils.ScreenStatusUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 * 登录页面-隐私政策
 */
public class PrivacyPolicyActivity extends SimpleActivity {


    @BindView(R.id.iv_finis)
    ImageView mIvFinis;
    @BindView(R.id.a)
    TextView mA;

    @Override
    protected void initEventAndData() {
        ScreenStatusUtil.setFillDip(this);
        mIvFinis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_privacy_policy;
    }

}
