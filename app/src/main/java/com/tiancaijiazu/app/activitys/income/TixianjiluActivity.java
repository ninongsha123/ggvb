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

public class TixianjiluActivity extends BaseActivity<IView, Presenter<IView>> implements IView {


    @BindView(R.id.iv_finis)
    ImageView mIvFinis;
    @BindView(R.id.a)
    MediumBoldTextViewStandard mA;
    @BindView(R.id.relative)
    RelativeLayout mRelative;
    @BindView(R.id.ti_jin_e)
    TextView mTiJinE;
    @BindView(R.id.ti_card)
    TextView mTiCard;
    @BindView(R.id.ti_date)
    TextView mTiDate;
    @BindView(R.id.dao_date)
    TextView mDaoDate;
    @BindView(R.id.success)
    ImageView mSuccess;
    @BindView(R.id.ti_jin_es)
    TextView mTiJinEs;
    @BindView(R.id.ti_cards)
    TextView mTiCards;
    @BindView(R.id.ti_dates)
    TextView mTiDates;
    @BindView(R.id.dao_dates)
    TextView mDaoDates;

    @Override
    protected Presenter<IView> createPresenter() {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void initEventAndData() {
        Spanned spanned = Html.fromHtml("&yen;", Html.FROM_HTML_MODE_LEGACY);
        Intent intent = getIntent();
        String grantTime = intent.getStringExtra("grantTime");
        String applyTime = intent.getStringExtra("applyTime");
        String money = intent.getStringExtra("money");
        String bank = intent.getStringExtra("bank");
        String cards = intent.getStringExtra("cards");
        mTiJinE.setText(money);
        mTiJinEs.setText(spanned+" "+money);
        mTiCard.setText("收入提现-到"+bank+" ("+cards+")");
        mTiCards.setText(bank+" 尾号"+cards);
        mDaoDate.setText(grantTime);
        mDaoDates.setText(grantTime);
        mTiDate.setText(applyTime);
        mTiDates.setText(applyTime);

        mIvFinis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_tixianjilu;
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
