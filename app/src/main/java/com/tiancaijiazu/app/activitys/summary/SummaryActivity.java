package com.tiancaijiazu.app.activitys.summary;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.base.activity.BaseActivity;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.mvp.IView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SummaryActivity extends BaseActivity<IView, Presenter<IView>> {


    @BindView(R.id.iv_finis)
    ImageView ivFinis;  @BindView(R.id.a)
    TextView a;
    @BindView(R.id.edit1)
    EditText edit1;
    @BindView(R.id.edit2)
    EditText edit2;
    @BindView(R.id.edit3)
    EditText edit3;
    @BindView(R.id.comit)
    ImageView comit;
    @BindView(R.id.rl_tihuan)
    RelativeLayout rlTihuan;
    @BindView(R.id.scrl)
    ScrollView scrl;

    @Override
    protected void initEventAndData() {
        initSett();
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_summary;
    }

    @Override
    public void showError(String error) {

    }

    @Override
    protected Presenter<IView> createPresenter() {
        return null;
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

    @OnClick({R.id.iv_finis, R.id.edit1, R.id.edit2, R.id.edit3, R.id.comit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_finis:
                finish();
                break;
            case R.id.edit1:
                break;
            case R.id.edit2:
                break;
            case R.id.edit3:
                break;
            case R.id.comit:
                scrl.setVisibility(View.GONE);
                rlTihuan.setVisibility(View.VISIBLE);
                break;
        }
    }


}
