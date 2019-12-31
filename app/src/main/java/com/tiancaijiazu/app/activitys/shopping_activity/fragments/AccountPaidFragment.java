package com.tiancaijiazu.app.activitys.shopping_activity.fragments;


import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.adapters.RlvAdapter_account;
import com.tiancaijiazu.app.activitys.shopping_activity.EvaluateActivity;
import com.tiancaijiazu.app.activitys.shopping_activity.LineItemActivity;
import com.tiancaijiazu.app.base.fragment.BaseFragment;
import com.tiancaijiazu.app.beans.NonPaymentListBean;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.city.ToastUtils;
import com.tiancaijiazu.app.mvp.IView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountPaidFragment extends BaseFragment<IView, Presenter<IView>> implements IView {


    @BindView(R.id.rlv)
    RecyclerView mRlv;
    @BindView(R.id.iv)
    ImageView mIv;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:         //刷新加载
                    if (mRefreshLayout != null) {
                        mRefreshLayout.finishRefresh(true);
                        page = 1;
                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("status", "1");
                        hashMap.put("page", page + "");
                        mPresenter.getDataP(hashMap, DifferentiateEnum.ORDERLIST);
                    }
                    break;
                case 2:         //加载更多
                    if (mRefreshLayout != null) {
                        mRefreshLayout.finishLoadMore(true);
                        page++;
                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("status", "1");
                        hashMap.put("page", page + "");
                        mPresenter.getDataP(hashMap, DifferentiateEnum.ORDERLIST);
                    }

                    break;
            }
            return false;
        }
    });
    private int page = 1;
    private RlvAdapter_account mRlvAdapterAccount;

    public AccountPaidFragment() {
        // Required empty public constructor
    }

    public static AccountPaidFragment getInstance() {
        return new AccountPaidFragment();
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.fragment_account_paid;
    }

    @Override
    protected void initData() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("status", "1");
        hashMap.put("page", page + "");
        mPresenter.getDataP(hashMap, DifferentiateEnum.ORDERLIST);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        List<NonPaymentListBean.ResultBean> resultBeans = new ArrayList<>();
        mRlv.setLayoutManager(linearLayoutManager);
        mRlvAdapterAccount = new RlvAdapter_account(resultBeans, getContext());
        mRlv.setAdapter(mRlvAdapterAccount);

        mRlvAdapterAccount.setOnClickLisiter(new RlvAdapter_account.onClickLisiter() {
            @Override
            public void onClicker(View view, int position, List<NonPaymentListBean.ResultBean> mData) {
                Intent intent = new Intent(getContext(), LineItemActivity.class);
                long orderId = mData.get(position).getOrderId();
                intent.putExtra("biao", "3");
                intent.putExtra("orderId", orderId + "");
                startActivity(intent);
            }
        });
        mRlvAdapterAccount.setEvaluateOnClickLisiter(new RlvAdapter_account.onEvaluateClickLisiter() {
            @Override
            public void onClicker(View view, int position, String pic, List<NonPaymentListBean.ResultBean> mData) {
                Intent intent = new Intent(getContext(), EvaluateActivity.class);
                intent.putExtra("picture", pic);
                intent.putExtra("orderId", mData.get(position).getOrderId() + "");
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
                    mIv.setVisibility(View.GONE);
                    if (page == 1) {
                        mRlvAdapterAccount.addData(result, true);
                    } else {
                        mRlvAdapterAccount.addData(result, false);
                    }
                } else {
                    mRlvAdapterAccount.addData(result, false);
                    if (page == 1) {
                        mIv.setVisibility(View.VISIBLE);
                    } else {

                        ToastUtils.showShortToast(getContext(), "暂无更多最新数据");

                    }
                }

                break;
        }
    }

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }
}
