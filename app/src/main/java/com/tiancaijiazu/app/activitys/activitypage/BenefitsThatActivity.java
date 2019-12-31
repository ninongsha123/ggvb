package com.tiancaijiazu.app.activitys.activitypage;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.base.activity.SimpleActivity;
import com.tiancaijiazu.app.utils.MediumBoldTextViewStandard;
import com.tiancaijiazu.app.utils.ScreenStatusUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BenefitsThatActivity extends SimpleActivity {


    @BindView(R.id.iv_finis)
    ImageView mIvFinis;
    @BindView(R.id.a)
    MediumBoldTextViewStandard mA;
    @BindView(R.id.relative)
    RelativeLayout mRelative;

    @Override
    protected void initEventAndData() {
        ScreenStatusUtil.setFillDip(this);
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_benefits_that;
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
