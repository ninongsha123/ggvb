package com.tiancaijiazu.app.activitys.evalua;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.evalua.adapters.RlvAdapter_evalua_baby;
import com.tiancaijiazu.app.base.activity.BaseActivity;
import com.tiancaijiazu.app.beans.BabyAgeBean;
import com.tiancaijiazu.app.beans.BabyMessageBean;
import com.tiancaijiazu.app.beans.GetSubIDBean;
import com.tiancaijiazu.app.beans.ReviewedListBean;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.ScreenStatusUtil;
import com.tiancaijiazu.app.utils.TimeUtil;
import com.tiancaijiazu.app.utils.city.ToastUtils;
import com.tiancaijiazu.app.utils.status.LoadingLayout;
import com.tiancaijiazu.app.mvp.IView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

public class EvaluaBabyActivity extends BaseActivity<IView, Presenter<IView>> implements IView {

    @BindView(R.id.iv_finis)
    ImageView mIvFinis;
    @BindView(R.id.a)
    TextView a;
    @BindView(R.id.loadingLayout)
    LoadingLayout loadingLayout;
    @BindView(R.id.relative)
    RelativeLayout mRelative;
    @BindView(R.id.recylerView)
    RecyclerView mRecylerView;
    private RlvAdapter_evalua_baby mRlvAdapterEvaluaBaby;
    private List<BabyAgeBean.ResultBean> mResult;
    private List<BabyMessageBean.ResultBean> mResult1;
    private int page = 1;
    private int size = 20;
    private Intent mIntent;
    private String mBabyId;
    private long mSubjectId;
    private boolean isbo = true;
    private long mReportId;
    private boolean isboo = true;

    @Override
    protected void initEventAndData() {
        ScreenStatusUtil.setFillDip(this);
        ScreenStatusUtil.setNotStatus(this, mRelative);
        mPresenter.getDataP1("", DifferentiateEnum.BABYMESSAGELIST,loadingLayout);
        mPresenter.getDataP1("", DifferentiateEnum.EVALUATIONLIST,loadingLayout);
        initRecyclerView();
        mIvFinis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
            @Override
            public void onReload(View v) {
                mPresenter.getDataP1("", DifferentiateEnum.BABYMESSAGELIST,loadingLayout);
                mPresenter.getDataP1("", DifferentiateEnum.EVALUATIONLIST,loadingLayout);
            }
        });
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecylerView.setLayoutManager(linearLayoutManager);
        List<BabyMessageBean.ResultBean> resultBeans = new ArrayList<>();
        mRlvAdapterEvaluaBaby = new RlvAdapter_evalua_baby(resultBeans, this);
        mRecylerView.setAdapter(mRlvAdapterEvaluaBaby);

        mRlvAdapterEvaluaBaby.setOnClickLisiter(new RlvAdapter_evalua_baby.onClickLisiter() {
            @Override
            public void onClicker(View view, int position, List<BabyMessageBean.ResultBean> mData) {
                String birthday = mData.get(position).getBirthday();
                String nowTime = TimeUtil.getNowTime();
                int month = TimeUtil.getMonth(birthday, nowTime);
                int day = TimeUtil.getDayAge(birthday, nowTime);
                mBabyId = mData.get(position).getBabyId() + "";
                Log.i("yx159", day+"onClicker: " + mBabyId);
                if (mResult.size() != 0) {
                   /* for (int i = 0; i < mResult.size(); i++) {
                        int monthMax = mResult.get(i).getMonthMax();
                        int monthMin = mResult.get(i).getMonthMin();
                        if (month < 12 && month > 0) {
                            if (((monthMin - month) == 1 && day > 15 && month < monthMax) ||( (monthMin - month) == 0 && day <= 15 && month < monthMax)) {
                                HashMap<String, Object> map = new HashMap<>();
                                map.put("babyId", mBabyId);
                                map.put("page", page);
                                map.put("size", size);
                                mPresenter.getDataP1(map, DifferentiateEnum.REVIEWEDLIST,loadingLayout);
                                mSubjectId = mResult.get(i).getSubjectId();
                                Log.i("yxSub", "onClicker: "+mSubjectId);
                                mIntent = new Intent(EvaluaBabyActivity.this, EvaluationActivity.class);
                                mIntent.putExtra("subjectId", mSubjectId + "");
                                mIntent.putExtra("babyId", mBabyId);
                                mIntent.putExtra("data", (Serializable) mResult1);
                                isboo = false;
                                loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
                                    @Override
                                    public void onReload(View v) {
                                        mPresenter.getDataP1(map, DifferentiateEnum.REVIEWEDLIST,loadingLayout);
                                    }
                                });
                                break;
                            }
                        } else if (month >= 12 && month <= mResult.get(0).getMonthMax()) {
                            if (month <= monthMax && month >= monthMin) {
                                if (month - monthMin == 0 && day >= 15) {
                                    HashMap<String, Object> map = new HashMap<>();
                                    map.put("babyId", mBabyId);
                                    map.put("page", page);
                                    map.put("size", size);
                                    mPresenter.getDataP1(map, DifferentiateEnum.REVIEWEDLIST,loadingLayout);
                                    mSubjectId = mResult.get(i).getSubjectId();
                                    mIntent = new Intent(EvaluaBabyActivity.this, EvaluationActivity.class);
                                    mIntent.putExtra("subjectId", mSubjectId + "");
                                    mIntent.putExtra("babyId", mBabyId);
                                    mIntent.putExtra("data", (Serializable) mResult1);
                                    isboo = false;
                                    loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
                                        @Override
                                        public void onReload(View v) {
                                            mPresenter.getDataP1(map, DifferentiateEnum.REVIEWEDLIST,loadingLayout);
                                        }
                                    });
                                    break;
                                } else if (month - monthMax == 0 && day < 15) {
                                    HashMap<String, Object> map = new HashMap<>();
                                    map.put("babyId", mBabyId);
                                    map.put("page", page);
                                    map.put("size", size);
                                    mPresenter.getDataP1(map, DifferentiateEnum.REVIEWEDLIST,loadingLayout);
                                    mSubjectId = mResult.get(i).getSubjectId();
                                    mIntent = new Intent(EvaluaBabyActivity.this, EvaluationActivity.class);
                                    mIntent.putExtra("subjectId", mSubjectId + "");
                                    mIntent.putExtra("babyId", mBabyId);
                                    mIntent.putExtra("data", (Serializable) mResult1);
                                    isboo = false;
                                    loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
                                        @Override
                                        public void onReload(View v) {
                                            mPresenter.getDataP1(map, DifferentiateEnum.REVIEWEDLIST,loadingLayout);
                                        }
                                    });
                                    break;
                                } else {
                                    HashMap<String, Object> map = new HashMap<>();
                                    map.put("babyId", mBabyId);
                                    map.put("page", page);
                                    map.put("size", size);
                                    mPresenter.getDataP1(map, DifferentiateEnum.REVIEWEDLIST,loadingLayout);
                                    mSubjectId = mResult.get(i).getSubjectId();
                                    mIntent = new Intent(EvaluaBabyActivity.this, EvaluationActivity.class);
                                    mIntent.putExtra("subjectId", mSubjectId + "");
                                    mIntent.putExtra("babyId", mBabyId);
                                    mIntent.putExtra("data", (Serializable) mResult1);
                                    isboo = false;
                                    loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
                                        @Override
                                        public void onReload(View v) {
                                            mPresenter.getDataP1(map, DifferentiateEnum.REVIEWEDLIST,loadingLayout);
                                        }
                                    });
                                    break;
                                }
                            }
                        }

                    }

                    if (isboo) {
                        Toast.makeText(mActivity, "无该宝宝当前月龄测评", Toast.LENGTH_SHORT).show();
                    }*/
                   mPresenter.getDataP(mBabyId,DifferentiateEnum.GETSUBID);
                }

            }
        });

    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_evalua_baby;
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum) {
            case BABYMESSAGELIST:
                BabyMessageBean babyMessageBean = (BabyMessageBean) o;
                mResult1 = babyMessageBean.getResult();
                mRlvAdapterEvaluaBaby.addData(mResult1);
                break;
            case EVALUATIONLIST:
                BabyAgeBean babyAgeBean = (BabyAgeBean) o;
                mResult = babyAgeBean.getResult();
                break;
            case REVIEWEDLIST:
                ReviewedListBean reviewedListBean = (ReviewedListBean) o;
                List<ReviewedListBean.ResultBean> result = reviewedListBean.getResult();
                for (int i = 0; i < result.size(); i++) {
                    String s = result.get(i).getSubjectId() + "";
                    //Log.i("yx333", mBabyId+"show: "+result.get(i).getBabyId());
                    //Log.i("yx333", s+"show: "+mSubjectId);
                    if (mBabyId.equals(result.get(i).getBabyId() + "") && s.equals(mSubjectId + "")) {
                        isbo = false;
                        mReportId = result.get(i).getReportId();
                        break;
                    }
                }
                if (isbo) {
                    startActivity(mIntent);
                    finish();
                } else {
                    Intent intent = new Intent(EvaluaBabyActivity.this, TestReportActivity.class);
                    intent.putExtra("babyId", mBabyId);
                    intent.putExtra("reportId", mReportId + "");
                    intent.putExtra("data", (Serializable) mResult1);
                    startActivity(intent);
                    finish();
                }
                break;
            case GETSUBID:
                GetSubIDBean getSubIDBean = (GetSubIDBean) o;
                GetSubIDBean.ResultBean result1 = getSubIDBean.getResult();
                mSubjectId = result1.getSubjectId();
                if(mSubjectId == -1){
                    ToastUtils.showShortToast(this, "无该宝宝当前月龄测评");
                }else {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("babyId", mBabyId);
                    map.put("page", page);
                    map.put("size", size);
                    mPresenter.getDataP1(map, DifferentiateEnum.REVIEWEDLIST,loadingLayout);

                    mIntent = new Intent(EvaluaBabyActivity.this, TestCeActivity.class);
                    mIntent.putExtra("subjectId", mSubjectId + "");
                    mIntent.putExtra("babyId", mBabyId);
                    mIntent.putExtra("data", (Serializable) mResult1);
                }
                break;
        }
    }

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }

}
