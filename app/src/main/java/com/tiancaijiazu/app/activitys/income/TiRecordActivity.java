package com.tiancaijiazu.app.activitys.income;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.tiancaijiazu.app.activitys.income.adapters.RlvAdapter_jilu_detail;
import com.tiancaijiazu.app.base.activity.BaseActivity;
import com.tiancaijiazu.app.beans.CashOutListBean;
import com.tiancaijiazu.app.beans.DetailBean;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.mvp.IView;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.ScreenStatusUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class TiRecordActivity extends BaseActivity<IView, Presenter<IView>> implements IView   {

    @BindView(R.id.recylerView)
    RecyclerView mRecylerView;
    @BindView(R.id.a)
    TextView a;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.iv_finis)
    ImageView mIvFinis;
    @BindView(R.id.relative)
    RelativeLayout mRelative;
    private int page = 1;
    private RlvAdapter_jilu_detail mRlvAdapterDetail;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:         //刷新加载
                    if (mRefreshLayout != null) {
                        mRefreshLayout.finishRefresh(true);
                        page = 1;
                        mPresenter.getDataP(page+"", DifferentiateEnum.DetailList);
                    }
                    break;
                case 2:         //加载更多
                    if (mRefreshLayout != null) {
                        mRefreshLayout.finishLoadMore(true);
                        page++;
                        mPresenter.getDataP(page+"", DifferentiateEnum.DetailList);
                    }
                    break;
            }
            return false;
        }
    });
    @Override
    protected void initEventAndData() {
        ScreenStatusUtil.setFillDip(this);
        initRecylerView();
        mPresenter.getDataP(page+"", DifferentiateEnum.DetailList);
    }

    private void initRecylerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecylerView.setLayoutManager(linearLayoutManager);
        List<CashOutListBean.ResultBean> resultBeans = new ArrayList<>();
        mRlvAdapterDetail = new RlvAdapter_jilu_detail(resultBeans,this);
        mRecylerView.setAdapter(mRlvAdapterDetail);

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
    protected int creatrLayoutId() {
        return R.layout.activity_ti_record;
    }

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum) {
            case DetailList:
                CashOutListBean cashOutListBean = (CashOutListBean) o;
                List<CashOutListBean.ResultBean> result = cashOutListBean.getResult();
                Log.i("itemMsg", result.size()+"");
                if(result.size()!=0){
                    List<CashOutListBean.ResultBean> resultBeans = new ArrayList<>();
                    resultBeans.addAll(result);
                    for (int i = 0; i < result.size(); i++)  //外循环是循环的次数
                    {
                        String tradeTime = result.get(i).getDate();
                        for (int j = result.size() - 1; j > i; j--)  //内循环是 外循环一次比较的次数
                        {
                            String tradeTime1 = result.get(j).getDate();
                            if (tradeTime.equals(tradeTime1)) {
                                result.remove(j);
                            }
                        }
                    }
                    if(page == 1){
                        mRlvAdapterDetail.addData(result,true);
                    }else {
                        mRlvAdapterDetail.addData(result,false);
                    }
                }else {
                    List<CashOutListBean.ResultBean> resultBeans = new ArrayList<>();
                    resultBeans.addAll(result);
                    for (int i = 0; i < result.size(); i++)  //外循环是循环的次数
                    {
                        String tradeTime = result.get(i).getDate();
                        for (int j = result.size() - 1; j > i; j--)  //内循环是 外循环一次比较的次数
                        {
                            String tradeTime1 = result.get(j).getDate();
                            if (tradeTime.equals(tradeTime1)) {
                                result.remove(j);
                            }
                        }
                    }
                    Toast.makeText(mActivity, "没有更多数据", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void showError(String error) {

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
