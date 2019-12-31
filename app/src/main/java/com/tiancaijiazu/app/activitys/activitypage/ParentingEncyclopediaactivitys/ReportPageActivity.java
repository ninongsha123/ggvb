package com.tiancaijiazu.app.activitys.activitypage.ParentingEncyclopediaactivitys;

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

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.views.FlowGroupView;
import com.tiancaijiazu.app.base.activity.BaseActivity;
import com.tiancaijiazu.app.beans.ArticleReportBeans;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.MediumBoldTextViewTitle;
import com.tiancaijiazu.app.utils.ScreenStatusUtil;
import com.tiancaijiazu.app.utils.city.ToastUtils;
import com.tiancaijiazu.app.mvp.IView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 * 家族圈-列表-详情-举报（关注右边的三个小点）
 */

public class ReportPageActivity extends BaseActivity<IView, Presenter<IView>> implements IView {

    @BindView(R.id.iv_finis)
    ImageView mIvFinis;
    @BindView(R.id.a)
    MediumBoldTextViewTitle mA;
    @BindView(R.id.relative)
    RelativeLayout mRelative;
    @BindView(R.id.v)
    View mV;
    @BindView(R.id.flow)
    FlowGroupView mFlow;
    @BindView(R.id.ok)
    TextView mOk;
    private String[] mTitles = new String[]{"全部", "有害信息", "污秽色情", "违法信息", "垃圾营销"};
    private List<String> mDataList = Arrays.asList(mTitles);
    private String mXuan;

    @Override
    protected void initEventAndData() {
        ScreenStatusUtil.setFillDip(this);
        Intent intent = getIntent();
        long articleId = intent.getLongExtra("articleId", 0);
        if (mFlow != null) {
            mFlow.removeAllViews();
        }
        for (int i = 0; i < mDataList.size(); i++) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(8, 15, 8, 0);
            TextView textView = new TextView(ReportPageActivity.this);
            textView.setLayoutParams(layoutParams);
            textView.setLineSpacing(1.2f, 1.2f);//设置行间距
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            textView.setText(mDataList.get(i));
            textView.setBackgroundResource(R.drawable.report_mo);
            textView.setTextColor(Color.parseColor("#333333"));
            initEvents(textView);
            textView.setPadding(calculateDpToPx(10), calculateDpToPx(5), calculateDpToPx(10), calculateDpToPx(5));
            mFlow.addView(textView, layoutParams);
        }

        mIvFinis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("articleId", articleId);
                if("全部".equals(mXuan)){
                    map.put("summary", "有害信息,污秽色情,违法信息,垃圾营销");
                }else {
                    map.put("summary", mXuan);
                }
                mPresenter.getDataP(map, DifferentiateEnum.ARYICLEREPORT);
            }
        });
    }

    private void initEvents(TextView textView) {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mXuan = textView.getText().toString();
                for (int i = 0; i < mDataList.size(); i++) {
                    if (textView.getText().toString().equals(mDataList.get(i))) {
                        TextView childAt = (TextView) mFlow.getChildAt(i);
                        childAt.setTextColor(Color.parseColor("#FFFFFF"));
                        childAt.setBackgroundResource(R.drawable.report_xuan);
                    } else {
                        TextView childAt = (TextView) mFlow.getChildAt(i);
                        childAt.setTextColor(Color.parseColor("#333333"));
                        childAt.setBackgroundResource(R.drawable.report_mo);
                    }
                }
            }
        });
    }

    private int calculateDpToPx(int padding_in_dp) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (padding_in_dp * scale + 0.5f);
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_report_page;
    }

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum) {
            case ARYICLEREPORT:
                ArticleReportBeans articleReportBeans = (ArticleReportBeans) o;
                String result = articleReportBeans.getResult();
                if (result.equalsIgnoreCase("提交成功")) {
                    ToastUtils.showShortToast(ReportPageActivity.this, "举报成功");
                    finish();
                }
                break;
        }
    }

    @Override
    public void showError(String error) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
