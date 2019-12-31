package com.tiancaijiazu.app.activitys.income;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.activitypage.BenefitsThatActivity;
import com.tiancaijiazu.app.base.activity.BaseActivity;
import com.tiancaijiazu.app.beans.IncomeBean;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.ScreenStatusUtil;
import com.tiancaijiazu.app.utils.status.LoadingLayout;
import com.tiancaijiazu.app.mvp.IView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MonthIncomeActivity extends BaseActivity<IView, Presenter<IView>> implements IView {


    @BindView(R.id.iv_finis)
    ImageView mIvFinis;
    @BindView(R.id.loadingLayout)
    LoadingLayout loadingLayout;
    @BindView(R.id.relative)
    RelativeLayout mRelative;
    @BindView(R.id.bank_cards_line)
    LinearLayout mBankCardsLine;
    @BindView(R.id.cord_line)
    LinearLayout cord_line;
    @BindView(R.id.ti_xian)
    LinearLayout mTiXian;
    @BindView(R.id.ti_jilu)
    LinearLayout mTiJilu;
    @BindView(R.id.income_total)
    TextView mIncomeTotal;
    @BindView(R.id.income_last_month)
    TextView mIncomeLastMonth;
    @BindView(R.id.income_current_month)
    TextView mIncomeCurrentMonth;
    @BindView(R.id.a)
    TextView a;
    @BindView(R.id.detail_line)
    LinearLayout mDetailLine;

    @Override
    protected void initEventAndData() {
        ScreenStatusUtil.setFillDip(this);
        mPresenter.getDataP1("", DifferentiateEnum.INCOME, loadingLayout);
        loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
            @Override
            public void onReload(View v) {
                mPresenter.getDataP1("", DifferentiateEnum.INCOME, loadingLayout);
            }
        });
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_month_income;
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum) {
            case INCOME:
                IncomeBean incomeBean = (IncomeBean) o;
                IncomeBean.ResultBean result = incomeBean.getResult();
                mIncomeTotal.setText(result.getIncome_Total() + "");
                mIncomeCurrentMonth.setText(result.getIncome_CurrentMonth() + "");
                mIncomeLastMonth.setText(result.getIncome_LastMonth() + "");
                break;
        }
    }

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }


    @OnClick({R.id.iv_finis, R.id.bank_cards_line, R.id.detail_line, R.id.cord_line,R.id.ti_xian,R.id.ti_jilu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_finis:
                finish();
                break;
            case R.id.cord_line:
                startActivity(new Intent(this, BenefitsThatActivity.class));
                break;
            case R.id.bank_cards_line:
                Intent intent = new Intent(this, AddBankCardsActivity.class);
                intent.putExtra("message","no");
                startActivity(intent);
                break;
            case R.id.detail_line:
                Intent intent1 = new Intent(this, DetailActivity.class);
                startActivity(intent1);
                break;
            case R.id.ti_xian:
                Intent intent2 = new Intent(this, TiInComeActivity.class);
                intent2.putExtra("jin_e",mIncomeCurrentMonth.getText().toString()+"");
                startActivity(intent2);
                break;
            case R.id.ti_jilu:
                Intent intent3 = new Intent(this, TiRecordActivity.class);
                startActivity(intent3);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }
}
