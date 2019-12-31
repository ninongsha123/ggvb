package com.tiancaijiazu.app.activitys.coupons;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.bean.CouponBean;
import com.tiancaijiazu.app.activitys.coupons.adapters.RlvAdapter_Coupons;
import com.tiancaijiazu.app.base.activity.BaseActivity;
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
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CouponsActivity extends BaseActivity<IView, Presenter<IView>> implements IView {


    @BindView(R.id.iv_finis)
    ImageView mIvFinis;
    @BindView(R.id.a)
    TextView a;
    @BindView(R.id.relative)
    RelativeLayout mRelative;
    @BindView(R.id.loadingLayout)
    LoadingLayout loadingLayout;
    @BindView(R.id.recylerView)
    RecyclerView mRecylerView;
    @BindView(R.id.state)
    ImageView mState;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    private RlvAdapter_Coupons rlvAdapterCoupons;
    private int page = 1;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:         //刷新加载
                    if (mRefreshLayout != null) {
                        mRefreshLayout.finishRefresh(true);
                        page = 1;

                        HashMap<String, Object> map = new HashMap<>();
                        map.put("status", 0);
                        map.put("page", page);
                        mPresenter.getDataP1(map, DifferentiateEnum.COUPONLIST,loadingLayout);
                    }
                    break;
                case 2:         //加载更多
                    if (mRefreshLayout != null) {
                        mRefreshLayout.finishLoadMore(true);
                        page++;

                        HashMap<String, Object> map = new HashMap<>();
                        map.put("status", 0);
                        map.put("page", page);
                        mPresenter.getDataP1(map, DifferentiateEnum.COUPONLIST,loadingLayout);
                    }

                    break;
            }
            return false;
        }
    });
    private String mBiao;
    private Intent mIntent;

    @Override
    protected void initEventAndData() {
        mIntent = getIntent();
        mBiao = mIntent.getStringExtra("biao");
        if ("shop_counpons".equals(mBiao)) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("status", 1);
            map.put("page", page);
            mPresenter.getDataP1(map, DifferentiateEnum.COUPONLIST,loadingLayout);
            loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
                @Override
                public void onReload(View v) {
                    mPresenter.getDataP1(map, DifferentiateEnum.COUPONLIST,loadingLayout);
                }
            });
        } else if ("noaddress".equals(mBiao)) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("status", 1);
            map.put("page", page);
            mPresenter.getDataP1(map, DifferentiateEnum.COUPONLIST,loadingLayout);
            loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
                @Override
                public void onReload(View v) {
                    mPresenter.getDataP1(map, DifferentiateEnum.COUPONLIST,loadingLayout);
                }
            });
        } else if ("CardType".equals(mBiao)) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("status", 1);
            map.put("page", page);
            mPresenter.getDataP1(map, DifferentiateEnum.COUPONLIST,loadingLayout);
            loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
                @Override
                public void onReload(View v) {
                    mPresenter.getDataP1(map, DifferentiateEnum.COUPONLIST,loadingLayout);
                }
            });
        } else if ("cardRenew".equals(mBiao)) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("status", 1);
            map.put("page", page);
            mPresenter.getDataP1(map, DifferentiateEnum.COUPONLIST,loadingLayout);
            loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
                @Override
                public void onReload(View v) {
                    mPresenter.getDataP1(map, DifferentiateEnum.COUPONLIST,loadingLayout);
                }
            });
        } else {
            HashMap<String, Object> map = new HashMap<>();
            map.put("status", 0);
            map.put("page", page);
            mPresenter.getDataP1(map, DifferentiateEnum.COUPONLIST,loadingLayout);
            loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
                @Override
                public void onReload(View v) {
                    mPresenter.getDataP1(map, DifferentiateEnum.COUPONLIST,loadingLayout);
                }
            });
        }

        initRecylerView();
        ScreenStatusUtil.setFillDip(this);

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                //List<String> data = initDatas();
                Message message = new Message();
                message.what = 1;
                //message.obj = data ;
                mHandler.sendMessageDelayed(message, 1500);
            }
        });

        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                Message message = new Message();
                message.what = 2;
                mHandler.sendMessageDelayed(message, 1500);
            }
        });
    }

    private void initRecylerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecylerView.setLayoutManager(linearLayoutManager);
        ArrayList<CouponBean.ResultBean> resultBeans = new ArrayList<>();
        rlvAdapterCoupons = new RlvAdapter_Coupons(this, resultBeans);
        mRecylerView.setAdapter(rlvAdapterCoupons);

        rlvAdapterCoupons.setOnClickLisiterSelect(new RlvAdapter_Coupons.onClickLisiterSelect() {
            @Override
            public void onClickerSelect(View view, int position, List<CouponBean.ResultBean> mData) {
                double feeMin = mData.get(position).getFeeMin();
                if ("shop_counpons".equals(mBiao)) {
                    String money = mIntent.getStringExtra("money");
                    double v = Double.parseDouble(money);
                    if (v > feeMin) {
                        mIntent.putExtra("data", (Serializable) mData.get(position));
                        setResult(15, mIntent);
                        finish();
                    } else {
                        Toast.makeText(mActivity, "未满足使用金额", Toast.LENGTH_SHORT).show();
                    }
                }
                if ("noaddress".equals(mBiao)) {
                    String money = mIntent.getStringExtra("money");
                    double v = Double.parseDouble(money);
                    if (v > feeMin) {
                        mIntent.putExtra("data", (Serializable) mData.get(position));
                        setResult(15, mIntent);
                        finish();
                    } else {
                        Toast.makeText(mActivity, "未满足使用金额", Toast.LENGTH_SHORT).show();
                    }
                }
                if ("CardType".equals(mBiao)) {
                    String money = mIntent.getStringExtra("money");
                    double v = Double.parseDouble(money);
                    if (v > feeMin) {
                        mIntent.putExtra("data", (Serializable) mData.get(position));
                        setResult(15, mIntent);
                        finish();
                    } else {
                        Toast.makeText(mActivity, "未满足使用金额", Toast.LENGTH_SHORT).show();
                    }
                }
                if ("cardRenew".equals(mBiao)) {
                    String money = mIntent.getStringExtra("money");
                    double v = Double.parseDouble(money);
                    if (v > feeMin) {
                        mIntent.putExtra("data", (Serializable) mData.get(position));
                        setResult(15, mIntent);
                        finish();
                    } else {
                        Toast.makeText(mActivity, "未满足使用金额", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_coupons;
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum) {
            case COUPONLIST:
                CouponBean couponBean = (CouponBean) o;
                List<CouponBean.ResultBean> result = couponBean.getResult();
                if (result.size() != 0) {
                    if ("shop_counpons".equals(mBiao)) {
                        ArrayList<CouponBean.ResultBean> resultBeans = new ArrayList<>();
                        int size = result.size();
                        for (int i = 0; i < size; i++) {
                            if (result.get(i).getTradeType() == 0 || result.get(i).getTradeType() == 3) {
                                Log.i("shop_counpons", "show: " + result.get(i).getTradeType());
                                if (result.get(i).getStatus() == 1) {
                                    String expiresIn = result.get(i).getExpiresIn();
                                    boolean b = TimeUtil.compareNowTime(expiresIn);
                                        resultBeans.add(result.get(i));
                                }
                            }
                        }
                        if (page == 1) {
                            rlvAdapterCoupons.addData(resultBeans, true);
                        } else {
                            rlvAdapterCoupons.addData(resultBeans, false);
                        }
                    } else if ("noaddress".equals(mBiao)) {
                        ArrayList<CouponBean.ResultBean> resultBeans = new ArrayList<>();
                        int size = result.size();
                        for (int i = 0; i < size; i++) {
                            if (result.get(i).getTradeType() == 0 || result.get(i).getTradeType() == 2) {
                                if (result.get(i).getStatus() == 1) {
                                    String expiresIn = result.get(i).getExpiresIn();
                                    boolean b = TimeUtil.compareNowTime(expiresIn);
                                    if (b) {
                                        resultBeans.add(result.get(i));
                                    }
                                }
                            }
                        }
                        if (page == 1) {
                            rlvAdapterCoupons.addData(resultBeans, true);
                        } else {
                            rlvAdapterCoupons.addData(resultBeans, false);
                        }
                    } else if ("CardType".equals(mBiao)) {
                        ArrayList<CouponBean.ResultBean> resultBeans = new ArrayList<>();
                        int size = result.size();
                        for (int i = 0; i < size; i++) {
                            if (result.get(i).getTradeType() == 0 || result.get(i).getTradeType() == 1) {
                                if (result.get(i).getStatus() == 1) {
                                    String expiresIn = result.get(i).getExpiresIn();
                                    boolean b = TimeUtil.compareNowTime(expiresIn);
                                    if (b) {
                                        resultBeans.add(result.get(i));
                                    }
                                }
                            }
                        }
                        if (page == 1) {
                            rlvAdapterCoupons.addData(resultBeans, true);
                        } else {
                            rlvAdapterCoupons.addData(resultBeans, false);
                        }
                    } else if ("cardRenew".equals(mBiao)) {
                        ArrayList<CouponBean.ResultBean> resultBeans = new ArrayList<>();
                        int size = result.size();
                        for (int i = 0; i < size; i++) {
                            if (result.get(i).getTradeType() == 0 || result.get(i).getTradeType() == 1) {
                                if (result.get(i).getStatus() == 1) {
                                    String expiresIn = result.get(i).getExpiresIn();
                                    boolean b = TimeUtil.compareNowTime(expiresIn);
                                    if (b) {
                                        resultBeans.add(result.get(i));
                                    }
                                }
                            }
                        }
                        if (page == 1) {
                            rlvAdapterCoupons.addData(resultBeans, true);
                        } else {
                            rlvAdapterCoupons.addData(resultBeans, false);
                        }
                    } else {
                        if (page == 1) {
                            rlvAdapterCoupons.addData(result, true);
                        } else {
                            rlvAdapterCoupons.addData(result, false);
                        }
                    }
                    mState.setVisibility(View.GONE);
                    mRefreshLayout.setVisibility(View.VISIBLE);
                } else {
                    if (page != 1) {
                        rlvAdapterCoupons.addData(result, false);
                        mState.setVisibility(View.GONE);
                        mRefreshLayout.setVisibility(View.VISIBLE);
                        ToastUtils.showShortToast(this,"暂无更多优惠券");
                    } else {
                        mState.setVisibility(View.VISIBLE);
                        mRefreshLayout.setVisibility(View.GONE);
                    }
                }
                break;
        }
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

    @OnClick({R.id.iv_finis})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_finis:
                finish();
                break;
        }
    }
}
