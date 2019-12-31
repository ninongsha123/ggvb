package com.tiancaijiazu.app.activitys.collect.fragments;


import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.activitypage.ShortVideoActivity;
import com.tiancaijiazu.app.activitys.collect.adapters.ShortCollectAdapter;
import com.tiancaijiazu.app.base.fragment.BaseFragment;
import com.tiancaijiazu.app.beans.ShortCollectListBean;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.city.ToastUtils;
import com.tiancaijiazu.app.utils.status.LoadingLayout;
import com.tiancaijiazu.app.mvp.IView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineShortFragment extends BaseFragment<IView, Presenter<IView>> implements IView {


    @BindView(R.id.recylerView)
    RecyclerView mRecylerView;
    @BindView(R.id.loadingLayout)
    LoadingLayout mLoadingLayout;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    private ShortCollectAdapter mShortCollectAdapter;
    private int page = 1;

    public MineShortFragment() {
        // Required empty public constructor
    }

    public static MineShortFragment getInstance() {
        MineShortFragment mineShortFragment = new MineShortFragment();
        return mineShortFragment;
    }
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:         //刷新加载
                    if (mRefreshLayout != null) {
                        mRefreshLayout.finishRefresh(true);
                        page = 1;
                        mPresenter.getDataP1(page, DifferentiateEnum.SHORTCOLLECTLIST,mLoadingLayout);
                    }
                    break;
                case 2:         //加载更多
                    if (mRefreshLayout != null) {
                        mRefreshLayout.finishLoadMore(true);
                        page++;
                        mPresenter.getDataP1(page, DifferentiateEnum.SHORTCOLLECTLIST,mLoadingLayout);
                    }

                    break;
            }
            return false;
        }
    });

    @Override
    protected int creatrLayoutId() {
        return R.layout.fragment_mine_short;
    }

    @Override
    protected void initData() {
        initView();
        mPresenter.getDataP1(page, DifferentiateEnum.SHORTCOLLECTLIST,mLoadingLayout);
        mLoadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
            @Override
            public void onReload(View v) {
                mPresenter.getDataP1(page, DifferentiateEnum.SHORTCOLLECTLIST,mLoadingLayout);
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
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        mRecylerView.setLayoutManager(gridLayoutManager);
        List<ShortCollectListBean.ResultBean> resultBeans = new ArrayList<>();
        mShortCollectAdapter = new ShortCollectAdapter(resultBeans,getContext());
        mRecylerView.setAdapter(mShortCollectAdapter);

        mShortCollectAdapter.setOnClickLisiter(new ShortCollectAdapter.onClickLisiter() {
            @Override
            public void onClicker(View view, int position, List<ShortCollectListBean.ResultBean> mData) {
                Intent intent = new Intent(getContext(), ShortVideoActivity.class);
                intent.putExtra("data", (Serializable) mData);
                intent.putExtra("biao", "collect");
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
    }
/*

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 11&&resultCode == 22){
            mRefreshLayout.autoRefresh();
        }
    }
*/

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum) {
            case SHORTCOLLECTLIST:
                ShortCollectListBean shortCollectListBean = (ShortCollectListBean) o;
                List<ShortCollectListBean.ResultBean> result = shortCollectListBean.getResult();
                if (result.size() != 0) {
                    if (page == 1) {
                        mShortCollectAdapter.addData(result,true);
                    } else {
                        mShortCollectAdapter.addData(result,false);
                    }
                } else {
                    if(page!=1){
                        ToastUtils.showShortToast(getContext(),"暂无更多收藏数据");
                    }
                    mShortCollectAdapter.addData(result,false);
                }
                break;
        }
    }

    @Override
    public void showError(String error) {

    }

}
