package com.tiancaijiazu.app.activitys.activitypage.myactivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tfedu.update.utils.PackageUtils;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.base.activity.SimpleActivity;
import com.tiancaijiazu.app.utils.ScreenStatusUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *
 *   设置--关于我们--版本升级
 */

public class AboutUsActivity extends SimpleActivity {


    @BindView(R.id.iv_finis)
    ImageView mIvFinis;
    @BindView(R.id.a)
    TextView a;
    @BindView(R.id.ban)
    TextView mBan;

    @Override
    protected void initEventAndData() {
        ScreenStatusUtil.setFillDip(this);
        String versionName = PackageUtils.getVersionName(this);
        mBan.setText("版本 V "+versionName);
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_about_us;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
