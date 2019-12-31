package com.tiancaijiazu.app.activitys.home_game_two;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.views.FlowGroupView;
import com.tiancaijiazu.app.base.activity.BaseActivity;
import com.tiancaijiazu.app.beans.GamehomePageBeans;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.ScreenStatusUtil;
import com.tiancaijiazu.app.mvp.IView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeGameActivity extends BaseActivity<IView, Presenter<IView>> implements IView {


    @BindView(R.id.iv_finis)
    ImageView mIvFinis;
    @BindView(R.id.share)
    ImageView mShare;
    @BindView(R.id.relative)
    RelativeLayout mRelative;
    @BindView(R.id.title)
    TextView mTitle; @BindView(R.id.geme_summery)
    TextView geme_summery;
    @BindView(R.id.flow)
    FlowGroupView mFlow;
    @BindView(R.id.iv)
    ImageView mIv;

    @Override
    protected void initEventAndData() {
        ScreenStatusUtil.setFillDip(this);
        //initFlow();
        Intent intent = getIntent();
        int tab = intent.getIntExtra("tab", 0);
        List<GamehomePageBeans.ResultBean> data = (List<GamehomePageBeans.ResultBean>) intent.getSerializableExtra("data");
        if(tab == 0){
            mTitle.setText(data.get(0).getTitle());
            geme_summery.setText(data.get(0).getSummary());
            Glide.with(mActivity).load(data.get(0).getPicUri()).into(mIv);
        }else if(tab == 1){
            mTitle.setText(data.get(1).getTitle());
            geme_summery.setText(data.get(1).getSummary());
            Glide.with(mActivity).load(data.get(1).getPicUri()).into(mIv);
        }else if(tab == 2){
            mTitle.setText("吹蒲公英");
        }
    }

    private void initFlow() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 0, calculateDpToPx(10), 0);
        if (mFlow != null) {
            mFlow.removeAllViews();
        }
        for (int i = 0; i < 2; i++) {
            TextView textView = new TextView(this);
            if(i==0){
                textView.setLayoutParams(layoutParams);
                textView.setLineSpacing(1.2f, 1.2f);//设置行间距
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);
                textView.setText("认知");
                textView.setBackgroundResource(R.drawable.shape_home_game);
                textView.setTextColor(Color.parseColor("#FF94CB"));
                textView.setPadding(calculateDpToPx(12), calculateDpToPx(1), calculateDpToPx(12), calculateDpToPx(1));
                mFlow.addView(textView, i, layoutParams);
            }else {
                textView.setLayoutParams(layoutParams);
                textView.setLineSpacing(1.2f, 1.2f);//设置行间距
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);
                textView.setText("社会性");
                textView.setBackgroundResource(R.drawable.shape_home_game_two);
                textView.setTextColor(Color.parseColor("#69BEFF"));
                textView.setPadding(calculateDpToPx(12), calculateDpToPx(1), calculateDpToPx(12), calculateDpToPx(1));
                mFlow.addView(textView, i, layoutParams);
            }
        }
    }
    private int calculateDpToPx(int padding_in_dp) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (padding_in_dp * scale + 0.5f);
    }
    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_home_game;
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {

    }

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_finis, R.id.share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_finis:
                finish();
                break;
            case R.id.share:
                break;
        }
    }
}
