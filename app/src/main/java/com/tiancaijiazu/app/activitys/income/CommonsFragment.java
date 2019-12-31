package com.tiancaijiazu.app.activitys.income;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.income.adapters.RlvAdapter_detail;
import com.tiancaijiazu.app.activitys.income.adapters.RlvAdapter_detail_nei;
import com.tiancaijiazu.app.base.fragment.BaseFragment;
import com.tiancaijiazu.app.beans.CashOutListBean;
import com.tiancaijiazu.app.beans.DetailBean;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.mvp.IView;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.status.LoadingLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommonsFragment extends BaseFragment<IView, Presenter<IView>> implements IView {


    @BindView(R.id.rlv_data)
    RecyclerView mRlvData;
    @BindView(R.id.loadingLayout)
    LoadingLayout mLoadingLayout;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.state)
    ImageView mState;
    Unbinder unbinder;
    private int page=1;
    private int tradeType=555;
    private RlvAdapter_detail_nei mRlvAdapter_detail;
        private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:         //刷新加载
                    if (mRefreshLayout != null) {
                        mRefreshLayout.finishRefresh(true);
                        page = 1;
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("page", page);
                        map.put("tradeType", tradeType);
                        mPresenter.getDataP1(map, DifferentiateEnum.MYRELEASEDLIST, mLoadingLayout);
                    }

                    break;
                case 2:         //加载更多
                    if (mRefreshLayout != null) {
                        mRefreshLayout.finishLoadMore(true);
                        page++;
                        HashMap<String, Object> map1 = new HashMap<>();
                        map1.put("page", page);
                        map1.put("tradeType", tradeType);
                        mPresenter.getDataP1(map1, DifferentiateEnum.MYRELEASEDLIST, mLoadingLayout);
                    }

                    break;
            }
            return false;
        }
    });


    @Override
    protected int creatrLayoutId() {
        return R.layout.fragment_commons;
    }

    @Override
    protected void initData() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("tradeType",tradeType);
        map.put("page",page);
        Log.i("page22", "getData: "+map.get("page"));
        Log.i("tradeTypec", "getData: "+map.get("tradeType"));
        mPresenter.getDataP1(map, DifferentiateEnum.DETAIL,mLoadingLayout);
        List<DetailBean.ResultBean> resultBeans  =new ArrayList<>();
        mRlvAdapter_detail = new RlvAdapter_detail_nei(resultBeans);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRlvData.setLayoutManager(linearLayoutManager);
        mRlvData.setAdapter(mRlvAdapter_detail);

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
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum) {
            case DETAIL:
                DetailBean detailBean = (DetailBean) o;
                List<DetailBean.ResultBean> result = detailBean.getResult();
                if(result.size()!=0){
                    if(page == 1){
                        mRlvAdapter_detail.addData(result,true);
                    }else {
                        mRlvAdapter_detail.addData(result,false);
                    }
                }else {
                    Toast.makeText(mActivity, "没有更多数据", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
