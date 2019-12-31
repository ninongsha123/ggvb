package com.tiancaijiazu.app.activitys.evalua;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.TextPaint;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lwj.widget.viewpagerindicator.ViewPagerIndicator;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.evalua.adapters.LCardAdapter;
import com.tiancaijiazu.app.activitys.evalua.views.HeightViewPager;
import com.tiancaijiazu.app.activitys.evalua.views.RadarView;
import com.tiancaijiazu.app.base.activity.BaseActivity;
import com.tiancaijiazu.app.beans.BabyMessageBean;
import com.tiancaijiazu.app.beans.EvaluationResultsBean;
import com.tiancaijiazu.app.beans.ReviewedListBean;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.DestroyActivityUtil;
import com.tiancaijiazu.app.utils.ScreenStatusUtil;
import com.tiancaijiazu.app.utils.ScreenUtil;
import com.tiancaijiazu.app.utils.city.ToastUtils;
import com.tiancaijiazu.app.utils.status.LoadingLayout;
import com.tiancaijiazu.app.mvp.IView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class TestReportActivity extends BaseActivity<IView, Presenter<IView>> implements IView {


    @BindView(R.id.iv_finis)
    ImageView mIvFinis;
    @BindView(R.id.a)
    TextView a;
    @BindView(R.id.relative)
    RelativeLayout mRelative;
    @BindView(R.id.radarView)
    RadarView mRadarView;
    @BindView(R.id.indicator_line)
    ViewPagerIndicator mIndicatorLine;
    @BindView(R.id.viewPager)
    HeightViewPager mViewPager;
    @BindView(R.id.baby_name_tv)
    TextView mBabyNameTv;
    @BindView(R.id.record_line)
    LinearLayout mRecordLine;
    @BindView(R.id.to_measure)
    ImageView mToMeasure;
    @BindView(R.id.loadingLayout)
    LoadingLayout loadingLayout;
    private PopupWindow mPopupWindow;
    private LCardAdapter mLCardAdapter;
    private float mTextLength;
    private List<BabyMessageBean.ResultBean> mResult;
    private String mNametwo;
    private String mBabyId;
    private long mSubjectId;
    private long mBabyIdtwo;
    private int page = 1;
    private int size = 20;
    private boolean isbo;
    private long mReportId;
    private TextView mBabyName;
    private boolean isboo;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.fontScale != 1)//非默认值
            getResources();
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        if (res.getConfiguration().fontScale != 1) {//非默认值
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();//设置默认
            res.updateConfiguration(newConfig, res.getDisplayMetrics());
        }
        return res;
    }

    @Override
    protected void initEventAndData() {
        ScreenStatusUtil.setFillDip(this);
        Intent intent = getIntent();
        mBabyId = intent.getStringExtra("babyId");
        mResult = (List<BabyMessageBean.ResultBean>) intent.getSerializableExtra("data");
        final Drawable drawable = getResources().getDrawable(R.mipmap.baby_name_shangla);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        if (mResult.size() == 1) {
            mBabyNameTv.setCompoundDrawables(null, null, null, null);
            int i = ScreenStatusUtil.setDp(15, this);
            mBabyNameTv.setGravity(Gravity.CENTER);
            mBabyNameTv.setPadding(i, 0, i, 0);
            isboo = false;
        } else {
            isboo = true;
            int i = ScreenStatusUtil.setDp(22, TestReportActivity.this);
            TextPaint paint = mBabyNameTv.getPaint();
            for (int j = 0; j < mResult.size(); j++) {
                if (mBabyId.equals(mResult.get(j).getBabyId() + "")) {
                    // 得到使用该paint写上text的时候,像素为多少
                    mTextLength = paint.measureText(mResult.get(j).getName()) + i;
                    mBabyNameTv.setText(Html.fromHtml(mResult.get(j).getName()));
                    mBabyNameTv.setGravity(Gravity.CENTER);
                } else {
                    mNametwo = mResult.get(j).getName();
                    mBabyIdtwo = mResult.get(j).getBabyId();
                }
            }
            initPop();
            mBabyNameTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mBabyNameTv.setCompoundDrawables(null, null, drawable, null);
                    int[] location = new int[2];
                    mBabyNameTv.getLocationOnScreen(location);
                    int i = ScreenStatusUtil.setDp(10, TestReportActivity.this);
                    mPopupWindow.showAtLocation(mBabyNameTv, Gravity.NO_GRAVITY, location[0], location[1] + mBabyNameTv.getHeight() + i);
                }
            });
        }
        String reportId = intent.getStringExtra("reportId");
        Log.i("wowo", "initEventAndData: " + reportId);
        mPresenter.getDataP1(reportId, DifferentiateEnum.EVALUATIONRESULTS, loadingLayout);
        initViewPager();
        loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
            @Override
            public void onReload(View v) {
                mPresenter.getDataP1(reportId, DifferentiateEnum.EVALUATIONRESULTS, loadingLayout);
            }
        });
    }

    public void reName() {
        int i = ScreenStatusUtil.setDp(22, TestReportActivity.this);
        TextPaint paint = mBabyNameTv.getPaint();
        for (int j = 0; j < mResult.size(); j++) {
            if (mBabyId.equals(mResult.get(j).getBabyId() + "")) {
                // 得到使用该paint写上text的时候,像素为多少
                mTextLength = paint.measureText(mResult.get(j).getName()) + i;
                mBabyNameTv.setText(Html.fromHtml(mResult.get(j).getName()));
            } else {
                mNametwo = mResult.get(j).getName();
                mBabyIdtwo = mResult.get(j).getBabyId();
            }
        }
        if (isboo) {
            TextPaint paint1 = mBabyName.getPaint();
            // 得到使用该paint写上text的时候,像素为多少
            float poptextsize = paint1.measureText(mNametwo);
            if (mTextLength > poptextsize) {
                int i1 = ScreenStatusUtil.setDp(30, TestReportActivity.this);
                ViewGroup.LayoutParams layoutParams = mBabyName.getLayoutParams();
                layoutParams.width = (int) (mTextLength + i1);
                mBabyName.setLayoutParams(layoutParams);
                ViewGroup.LayoutParams layoutParams1 = mBabyNameTv.getLayoutParams();
                layoutParams1.width = (int) (mTextLength + i1);
                mBabyNameTv.setLayoutParams(layoutParams1);
            } else {
                int i1 = ScreenStatusUtil.setDp(30, TestReportActivity.this);
                ViewGroup.LayoutParams layoutParams = mBabyNameTv.getLayoutParams();
                layoutParams.width = (int) (poptextsize + i1);
                mBabyNameTv.setLayoutParams(layoutParams);
                ViewGroup.LayoutParams layoutParams1 = mBabyName.getLayoutParams();
                layoutParams1.width = (int) (poptextsize + i1);
                mBabyName.setLayoutParams(layoutParams1);
            }
            mBabyName.setText(Html.fromHtml(mNametwo));
        }

    }

    private void initPop() {
        View inflate = LayoutInflater.from(this).inflate(R.layout.pop_baby_name, null);
        mPopupWindow = new PopupWindow(inflate, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setFocusable(true);// 取得焦点
        mPopupWindow.setClippingEnabled(false);
        //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        //点击外部消失
        mPopupWindow.setOutsideTouchable(true);
        //设置可以点击
        mPopupWindow.setTouchable(true);
        //进入退出的动画，指定刚才定义的style
        mPopupWindow.setAnimationStyle(R.style.popwin_anim_style);
        mBabyName = inflate.findViewById(R.id.baby_name);
        /*TextPaint paint = mBabyName.getPaint();
        // 得到使用该paint写上text的时候,像素为多少
        float poptextsize = paint.measureText(mNametwo);
        if (mTextLength > poptextsize) {
            int i = ScreenStatusUtil.setDp(30, TestReportActivity.this);
            ViewGroup.LayoutParams layoutParams = mBabyName.getLayoutParams();
            layoutParams.width = (int) (mTextLength + i);
            mBabyName.setLayoutParams(layoutParams);
        } else {
            int i = ScreenStatusUtil.setDp(30, TestReportActivity.this);
            ViewGroup.LayoutParams layoutParams = mBabyNameTv.getLayoutParams();
            layoutParams.width = (int) (poptextsize + i);
            mBabyNameTv.setLayoutParams(layoutParams);
        }
        mBabyName.setText(mNametwo);*/
        final Drawable drawable = getResources().getDrawable(R.mipmap.baby_name_xiala);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        mBabyName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("babyId", mBabyIdtwo + "");
                map.put("page", page);
                map.put("size", size);
                mPresenter.getDataP1(map, DifferentiateEnum.REVIEWEDLIST, loadingLayout);
                mPopupWindow.dismiss();
            }
        });
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                mBabyNameTv.setCompoundDrawables(null, null, drawable, null);
            }
        });
    }

    private void initViewPager() {
        int screenWidth = ScreenUtil.getInstance(this).getScreenWidth();
        int i = ScreenStatusUtil.setDp(26, this);
        ViewGroup.LayoutParams layoutParams = mViewPager.getLayoutParams();
        layoutParams.width = screenWidth - (2 * i);
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        mViewPager.setLayoutParams(layoutParams);
        List<EvaluationResultsBean.ResultBean.IndicatorsListBean> indicatorsList = new ArrayList<>();
        mLCardAdapter = new LCardAdapter(indicatorsList, this);
        //设置适配器
        mViewPager.setAdapter(mLCardAdapter);
        mViewPager.setPageMargin(13);
        mIndicatorLine.setViewPager(mViewPager, 5);
        mViewPager.setOffscreenPageLimit(5);
        //左右都有图
        mViewPager.setCurrentItem(0);

        //viewPager左右两边滑动无效的处理
        /*ll_layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return mViewPager.dispatchTouchEvent(motionEvent);
            }
        });*/
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_test_report;
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum) {
            case EVALUATIONRESULTS:
                EvaluationResultsBean evaluationResultsBean = (EvaluationResultsBean) o;
                EvaluationResultsBean.ResultBean result = evaluationResultsBean.getResult();
                List<EvaluationResultsBean.ResultBean.IndicatorsListBean> indicatorsList = result.getIndicatorsList();
                List<Double> datas = new ArrayList<>(5);
                mRadarView.setTitle(indicatorsList);
                for (int i = 0; i < indicatorsList.size(); i++) {
                    int per = indicatorsList.get(i).getPer();
                    Log.i("wowo", per + "show: " + (double) per);
                    datas.add((double) per);
                }

                mBabyId = result.getBabyId() + "";
                mSubjectId = result.getSubjectId();
                reName();
                Log.i("wowo", "show: "+datas.size());
                mRadarView.setData(datas, false);
                mLCardAdapter.addData(indicatorsList);

                break;
            case REVIEWEDLIST:
                ReviewedListBean reviewedListBean = (ReviewedListBean) o;
                List<ReviewedListBean.ResultBean> result1 = reviewedListBean.getResult();
                for (int i = 0; i < result1.size(); i++) {
                    if (mSubjectId == result1.get(i).getSubjectId()) {
                        mReportId = result1.get(i).getReportId();
                        isbo = true;
                    }
                }

                if (isbo) {
                    List<EvaluationResultsBean.ResultBean.IndicatorsListBean> indicatorsList1 = new ArrayList<>();
                    mLCardAdapter = new LCardAdapter(indicatorsList1, this);
                    //设置适配器
                    mViewPager.setAdapter(mLCardAdapter);
                    mViewPager.setPageMargin(13);
                    mIndicatorLine.setViewPager(mViewPager, 5);
                    mViewPager.setOffscreenPageLimit(5);
                    //左右都有图
                    mViewPager.setCurrentItem(0);
                    mPresenter.getDataP1(mReportId + "", DifferentiateEnum.EVALUATIONRESULTS, loadingLayout);
                    /*loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
                        @Override
                        public void onReload(View v) {
                            mPresenter.getDataP1(mReportId + "", DifferentiateEnum.EVALUATIONRESULTS, loadingLayout);
                        }
                    });*/
                } else {
                    // Toast.makeText(mActivity, "该宝宝暂未测评该月龄段", Toast.LENGTH_SHORT).show();
                    ToastUtils.showShortToast(mActivity, "该宝宝暂未测评该月龄段");
                }
                break;
        }
    }

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }


    @OnClick({R.id.iv_finis, R.id.record_line, R.id.to_measure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_finis:
                finish();
                break;
            case R.id.record_line:
                Intent intent = new Intent(TestReportActivity.this, AssessmentRecordsActivity.class);
                intent.putExtra("data", (Serializable) mResult);
                intent.putExtra("babyId", mBabyId);
                startActivity(intent);
                DestroyActivityUtil.addDestoryActivityToMap(TestReportActivity.this, "TestReportActivity");
                break;
            case R.id.to_measure:
                Intent intent1 = new Intent(TestReportActivity.this, TestCeActivity.class);
                intent1.putExtra("subjectId", mSubjectId + "");
                intent1.putExtra("babyId", mBabyId);
                intent1.putExtra("data", (Serializable) mResult);
                startActivity(intent1);
                finish();
                break;
        }
    }
}
