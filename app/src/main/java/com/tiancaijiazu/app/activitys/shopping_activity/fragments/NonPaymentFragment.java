package com.tiancaijiazu.app.activitys.shopping_activity.fragments;


import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.adapters.RlvAdapter_nonPay;
import com.tiancaijiazu.app.activitys.shopping_activity.LineItemActivity;
import com.tiancaijiazu.app.activitys.shopping_activity.PayActivity;
import com.tiancaijiazu.app.base.fragment.BaseFragment;
import com.tiancaijiazu.app.beans.NonPaymentListBean;
import com.tiancaijiazu.app.beans.OrderCannelBeans;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.city.ToastUtils;
import com.tiancaijiazu.app.mvp.IView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class NonPaymentFragment extends BaseFragment<IView, Presenter<IView>> implements IView {


    @BindView(R.id.rlv)
    RecyclerView mRlv;
    @BindView(R.id.kong)
    ImageView mKong;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    private int page = 1;
    private RlvAdapter_nonPay mRlvAdapterNonPay;
    private int i;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:         //刷新加载
                    if (mRefreshLayout != null) {
                        mRefreshLayout.finishRefresh(true);
                        page = 1;
                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("status", "0");
                        hashMap.put("page", page + "");
                        mPresenter.getDataP(hashMap, DifferentiateEnum.ORDERLIST);
                    }
                    break;
                case 2:         //加载更多
                    if (mRefreshLayout != null) {
                        mRefreshLayout.finishLoadMore(true);
                        page++;
                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("status", "0");
                        hashMap.put("page", page + "");
                        mPresenter.getDataP(hashMap, DifferentiateEnum.ORDERLIST);
                    }

                    break;
            }
            return false;
        }
    });

    public NonPaymentFragment() {
        // Required empty public constructor
    }

    public static NonPaymentFragment getInstance() {
        return new NonPaymentFragment();
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.fragment_non_payment;
    }

    @Override
    protected void initData() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("status", "0");
        hashMap.put("page", page + "");
        mPresenter.getDataP(hashMap, DifferentiateEnum.ORDERLIST);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRlv.setLayoutManager(linearLayoutManager);
        List<NonPaymentListBean.ResultBean> resultBeans = new ArrayList<>();
        mRlvAdapterNonPay = new RlvAdapter_nonPay(resultBeans, getContext());
        mRlv.setAdapter(mRlvAdapterNonPay);

        mRlvAdapterNonPay.onClickLisiterCannel(new RlvAdapter_nonPay.onClickLisiterCannel() {
            @Override
            public void onClickerCannel(View view, int position, List<NonPaymentListBean.ResultBean> mData) {
                i = position;
                long orderId = mData.get(position).getOrderId();
                Log.d("bjg", "onClickerCannel: " + orderId);
                mPresenter.getDataP(orderId, DifferentiateEnum.ORDERCANNEL);
            }
        });
        mRlvAdapterNonPay.setOnClickLisiterJin(new RlvAdapter_nonPay.onClickLisiterJin() {
            @Override
            public void onClickerJin(View view, int position, List<NonPaymentListBean.ResultBean> mData) {
                Intent intent = new Intent(getContext(), LineItemActivity.class);
                long orderId = mData.get(position).getOrderId();
                double totalFee = mData.get(position).getTotalFee();
                double round = round(totalFee, 2);
                intent.putExtra("biao", "2");
                intent.putExtra("orderId", orderId + "");
                intent.putExtra("totalFee", round);
                startActivity(intent);
            }
        });
        mRlvAdapterNonPay.onClickLisiterFuKuan(new RlvAdapter_nonPay.onClickLisiterFuKuan() {
            @Override
            public void onClickerFuKuan(View view, int position, List<NonPaymentListBean.ResultBean> mData) {
                /*
                mPresenter.getDataP(orderId+"",DifferentiateEnum.PAYBEAN);*/
                long orderId = mData.get(position).getOrderId();
                double totalFee = mData.get(position).getTotalFee();
                double round = round(totalFee, 2);
                Intent intent = new Intent(getContext(), PayActivity.class);
                intent.putExtra("orderId", orderId + "");
                intent.putExtra("totalFee", round);
                intent.putExtra("biao", "unpaid");
                startActivity(intent);
            }
        });

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
    public static double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal ne = new BigDecimal("1");
        return b.divide(ne, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    @Override
    public void showError(String error) {

    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum) {
            case ORDERLIST:
                NonPaymentListBean nonPaymentListBean = (NonPaymentListBean) o;
                List<NonPaymentListBean.ResultBean> result = nonPaymentListBean.getResult();

                if (result.size() != 0) {
                    mKong.setVisibility(View.GONE);
                    if (page == 1) {
                        mRlvAdapterNonPay.addData(result, true);
                    } else {
                        mRlvAdapterNonPay.addData(result, false);
                    }
                } else {
                    mRlvAdapterNonPay.addData(result, false);
                    if (page == 1) {
                        mKong.setVisibility(View.VISIBLE);
                    } else {
                        ToastUtils.showShortToast(getContext(), "暂无更多最新数据");

                    }
                }
                break;
            case ORDERCANNEL:
                OrderCannelBeans orderCannelBeans = (OrderCannelBeans) o;
                String msg = orderCannelBeans.getMsg();
                if (msg.equalsIgnoreCase("取消成功")) {
                    ToastUtils.showShortToast(getContext(), msg);
                    mRlvAdapterNonPay.mData.remove(i);
                    mRlvAdapterNonPay.notifyItemRemoved(i);
                    mRlvAdapterNonPay.notifyItemRangeChanged(0, mRlvAdapterNonPay.mData.size() - i);
                    mRlvAdapterNonPay.notifyDataSetChanged();
                    mRefreshLayout.autoRefresh();
                }
                break;
            /*case PAYBEAN:
                PayBean payBean = (PayBean) o;
                PayBean.ResultBean result1 = payBean.getResult();
                String appid = result1.getAppid();
                String partnerid = result1.getPartnerid();
                String prepayid = result1.getPrepayid();
                String packageX = result1.getPackageX();
                String noncestr = result1.getNoncestr();
                String timestamp = result1.getTimestamp();
                String sign = result1.getSign();
                PayReq request = new PayReq();
                request.appId = appid;
                request.partnerId = partnerid;
                request.prepayId = prepayid;
                request.packageValue = packageX;
                request.nonceStr = noncestr;
                request.timeStamp = timestamp;
                request.sign = sign;
                api.sendReq(request);
                break;*/
        }
    }

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }

}
