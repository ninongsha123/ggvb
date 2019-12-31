package com.tiancaijiazu.app.activitys.income;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.base.activity.BaseActivity;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.mvp.IView;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.MediumBoldTextViewStandard;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TiDeitlActivity extends BaseActivity<IView, Presenter<IView>> implements IView {


    @BindView(R.id.iv_finis)
    ImageView mIvFinis;
    @BindView(R.id.a)
    MediumBoldTextViewStandard mA;
    @BindView(R.id.relative)
    RelativeLayout mRelative;
    @BindView(R.id.yuans)
    ImageView mYuans;
    @BindView(R.id.qian_shu)
    TextView mQianShu;
    @BindView(R.id.my_card)
    TextView mMyCard;
    @BindView(R.id.finish)
    ImageView mFinish;

    @Override
    protected Presenter<IView> createPresenter() {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void initEventAndData() {
        Spanned spanned = Html.fromHtml("&yen;", Html.FROM_HTML_MODE_LEGACY);
        Intent intent = getIntent();
        String money = intent.getStringExtra("money");
        String card = intent.getStringExtra("cards");
        String bank = intent.getStringExtra("bank");
        mQianShu.setText(spanned+money);
        mMyCard.setText(bank+" 尾号"+card);
        mFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mIvFinis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_ti_deitl;
    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {

    }

    @Override
    public void showError(String error) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }
}
