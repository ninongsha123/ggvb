package com.tiancaijiazu.app.activitys.evalua;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextPaint;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.evalua.adapters.RlvAdapter_Record;
import com.tiancaijiazu.app.base.activity.BaseActivity;
import com.tiancaijiazu.app.beans.BabyAgeBean;
import com.tiancaijiazu.app.beans.BabyMessageBean;
import com.tiancaijiazu.app.beans.ReviewedListBean;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.DestroyActivityUtil;
import com.tiancaijiazu.app.utils.ScreenStatusUtil;
import com.tiancaijiazu.app.utils.city.ToastUtils;
import com.tiancaijiazu.app.mvp.IView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class AssessmentRecordsActivity extends BaseActivity<IView, Presenter<IView>> implements IView {

    @BindView(R.id.iv_finis)
    ImageView mIvFinis;
    @BindView(R.id.a)
    TextView a;
    @BindView(R.id.relative)
    RelativeLayout mRelative;
    @BindView(R.id.recylerView)
    RecyclerView mRecylerView;
    @BindView(R.id.baby_name_tv)
    TextView mBabyNameTv;
    private List<BabyMessageBean.ResultBean> mResult;
    private String mBabyId;
    private RlvAdapter_Record mRlvAdapterRecord;
    private int page = 1;
    private int size = 20;
    private PopupWindow mPopupWindow;
    private float mTextLength;
    private TextView mBabyName;
    private long mBabyIdtwo;
    private String mNametwo;
    private boolean isboo;
    private List<ReviewedListBean.ResultBean> mResult1;

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
            mBabyNameTv.setPadding(i, 0, i, 0);
            isboo = false;
        } else {
            isboo = true;
            int i = ScreenStatusUtil.setDp(22, AssessmentRecordsActivity.this);
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
            initPop();
            mBabyNameTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mBabyNameTv.setCompoundDrawables(null, null, drawable, null);
                    int[] location = new int[2];
                    mBabyNameTv.getLocationOnScreen(location);
                    int i = ScreenStatusUtil.setDp(10, AssessmentRecordsActivity.this);
                    mPopupWindow.showAtLocation(mBabyNameTv, Gravity.NO_GRAVITY, location[0], location[1] + mBabyNameTv.getHeight() + i);
                }
            });
        }
        initRecylerView();
        HashMap<String, Object> map = new HashMap<>();
        map.put("babyId", mBabyId + "");
        map.put("page", page);
        map.put("size", size);
        mPresenter.getDataP(map, DifferentiateEnum.REVIEWEDLIST);
        mPresenter.getDataP("", DifferentiateEnum.EVALUATIONLIST);
    }

    private void initPop() {
        View inflate = LayoutInflater.from(this).inflate(R.layout.pop_baby_name_record, null);
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

        final Drawable drawable = getResources().getDrawable(R.mipmap.baby_name_xiala);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        mBabyName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("babyId", mBabyIdtwo + "");
                map.put("page", page);
                map.put("size", size);
                mPresenter.getDataP(map, DifferentiateEnum.REVIEWEDLIST);
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

    public void reName() {
        int i = ScreenStatusUtil.setDp(22, AssessmentRecordsActivity.this);
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
                int i1 = ScreenStatusUtil.setDp(30, AssessmentRecordsActivity.this);
                ViewGroup.LayoutParams layoutParams = mBabyName.getLayoutParams();
                layoutParams.width = (int) (mTextLength + i1);
                mBabyName.setLayoutParams(layoutParams);
                ViewGroup.LayoutParams layoutParams1 = mBabyNameTv.getLayoutParams();
                layoutParams1.width = (int) (mTextLength + i1);
                mBabyNameTv.setLayoutParams(layoutParams1);
            } else {
                int i1 = ScreenStatusUtil.setDp(30, AssessmentRecordsActivity.this);
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

    private void initRecylerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecylerView.setLayoutManager(linearLayoutManager);
        List<BabyAgeBean.ResultBean> resultBeans = new ArrayList<>();
        ArrayList<ReviewedListBean.ResultBean> resultBeans1 = new ArrayList<>();
        mRlvAdapterRecord = new RlvAdapter_Record(resultBeans, this, resultBeans1, mResult);
        mRecylerView.setAdapter(mRlvAdapterRecord);

        mRlvAdapterRecord.setOnClickLisiter(new RlvAdapter_Record.onClickLisiterLook() {
            @Override
            public void onClikerLook(View view, int position, List<BabyAgeBean.ResultBean> mData) {
                for (int i = 0; i < mResult1.size(); i++) {
                    String baby = mResult1.get(i).getBabyId() + "";
                    long subjectId = mResult1.get(i).getSubjectId();
                    if (subjectId == mData.get(position).getSubjectId() && baby.equals(mBabyId)) {
                        Intent intent = new Intent(AssessmentRecordsActivity.this, TestReportActivity.class);
                        intent.putExtra("reportId", mResult1.get(i).getReportId() + "");
                        intent.putExtra("babyId", mBabyId);
                        intent.putExtra("data", (Serializable) mResult);
                        startActivity(intent);
                        break;
                    }
                }
                DestroyActivityUtil.destoryActivity("TestReportActivity");
                finish();
            }
        });

        mRlvAdapterRecord.setOnClickLisiterBegin(new RlvAdapter_Record.onClickLisiterBegin() {
            @Override
            public void onClickerBegin(View view, int position, List<BabyAgeBean.ResultBean> mData, String biao) {
                if ("未开启测评".equals(biao)) {
                    ToastUtils.showShortToast(mActivity,"您的宝宝还未到该月龄段");
                } else if ("开始测评".equals(biao)) {
                    String s = mData.get(position).getSubjectId() + "";
                    Intent intent = new Intent(AssessmentRecordsActivity.this, TestCeActivity.class);
                    intent.putExtra("subjectId", s + "");
                    intent.putExtra("babyId", mBabyId);
                    intent.putExtra("data", (Serializable) mResult);
                    startActivity(intent);
                    finish();
                }

            }
        });
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_assessment_records;
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum) {
            case EVALUATIONLIST:
                BabyAgeBean babyAgeBean = (BabyAgeBean) o;
                List<BabyAgeBean.ResultBean> result = babyAgeBean.getResult();
                mRlvAdapterRecord.addData(result);
                break;
            case REVIEWEDLIST:
                ReviewedListBean reviewedListBean = (ReviewedListBean) o;
                mResult1 = reviewedListBean.getResult();
                if (mResult1 != null) {
                    if (mResult1.size() != 0) {
                        mBabyId = mResult1.get(0).getBabyId() + "";
                        reName();
                        mRlvAdapterRecord.addQu(mResult1);
                    } else {
                        ToastUtils.showShortToast(mActivity,"该宝宝暂无测评记录");
                    }
                }
                break;
        }
    }

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }


    @OnClick({R.id.iv_finis})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_finis:
                finish();
                break;
        }
    }
}
