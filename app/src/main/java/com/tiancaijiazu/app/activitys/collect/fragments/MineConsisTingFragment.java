package com.tiancaijiazu.app.activitys.collect.fragments;


import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.activitypage.WebActivity;
import com.tiancaijiazu.app.activitys.bringfragments.adapters.RlvAdapter_consis_list;
import com.tiancaijiazu.app.base.fragment.BaseFragment;
import com.tiancaijiazu.app.beans.WebCollectListBean;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.city.ToastUtils;
import com.tiancaijiazu.app.utils.status.LoadingLayout;
import com.tiancaijiazu.app.mvp.IView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineConsisTingFragment extends BaseFragment<IView, Presenter<IView>> implements IView {


    @BindView(R.id.rlv_data)
    RecyclerView mRlvData;
    @BindView(R.id.loadingLayout)
    LoadingLayout mLoadingLayout;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    private int page = 1;
    private RlvAdapter_consis_list mRlvAdapterConsisList;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:         //刷新加载
                    if (mRefreshLayout != null) {
                        mRefreshLayout.finishRefresh(true);
                        page = 1;
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("articleType","1");
                        map.put("page",page);
                        mPresenter.getDataP1(map,DifferentiateEnum.WEBCOLLECTLIST,mLoadingLayout);
                    }
                    break;
                case 2:         //加载更多
                    if (mRefreshLayout != null) {
                        mRefreshLayout.finishLoadMore(true);
                        page++;
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("articleType","1");
                        map.put("page",page);
                        mPresenter.getDataP1(map,DifferentiateEnum.WEBCOLLECTLIST,mLoadingLayout);
                    }

                    break;
            }
            return false;
        }
    });
    public MineConsisTingFragment() {
        // Required empty public constructor
    }

    public static MineConsisTingFragment getInstance() {
        MineConsisTingFragment mineConsisTingFragment = new MineConsisTingFragment();
        return mineConsisTingFragment;
    }


    @Override
    protected int creatrLayoutId() {
        return R.layout.fragment_mine_consis_ting;
    }

    @Override
    protected void initData() {
        initView();
        HashMap<String, Object> map = new HashMap<>();
        map.put("articleType","1");
        map.put("page",page);
        mPresenter.getDataP1(map,DifferentiateEnum.WEBCOLLECTLIST,mLoadingLayout);
        mLoadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
            @Override
            public void onReload(View v) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("articleType","1");
                map.put("page",page);
                mPresenter.getDataP1(map,DifferentiateEnum.WEBCOLLECTLIST,mLoadingLayout);
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

    private void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRlvData.setLayoutManager(linearLayoutManager);
        List<WebCollectListBean.ResultBean> resultBeans = new ArrayList<>();
        mRlvAdapterConsisList = new RlvAdapter_consis_list(resultBeans,getContext());
        mRlvData.setAdapter(mRlvAdapterConsisList);

        mRlvAdapterConsisList.setOnClickLisiter(new RlvAdapter_consis_list.onClickLisiter() {
            @Override
            public void onClicker(View view, int position, List<WebCollectListBean.ResultBean> mData) {
                String url = mData.get(position).getUrl();
                Intent intent = new Intent(getContext(), WebActivity.class);
                intent.putExtra("biao","collect");
                intent.putExtra("target", url);
                startActivityForResult(intent,24);
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
            case WEBCOLLECTLIST:
                WebCollectListBean webCollectListBean = (WebCollectListBean) o;
                List<WebCollectListBean.ResultBean> result = webCollectListBean.getResult();
                if(result.size()!=0){
                    if(page == 1){
                        mRlvAdapterConsisList.addData(result,true);
                    }else {
                        mRlvAdapterConsisList.addData(result,false);
                    }
                }else {
                    if(page!=1){
                        ToastUtils.showShortToast(getContext(),"暂无更多收藏数据");
                    }
                    mRlvAdapterConsisList.addData(result, false);
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 24 && resultCode == 25) {
            int position = data.getIntExtra("position", 0);
            boolean checked = data.getBooleanExtra("checked", true);
            if(checked){
                mRlvAdapterConsisList.notifyItemChanged(position);
            }else {
                mRlvAdapterConsisList.mData.remove(position);
                mRlvAdapterConsisList.notifyItemRemoved(position);
                mRlvAdapterConsisList.notifyItemRangeChanged(0, mRlvAdapterConsisList.mData.size() - position);
            }

        }
    }

    @Override
    public void showError(String error) {

    }

}
