package com.tiancaijiazu.app.activitys.shopping_activity.fragments;


import android.content.Intent;
import android.os.Bundle;
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
import com.tiancaijiazu.app.activitys.adapters.RlvAdapter_subhead_one;
import com.tiancaijiazu.app.base.fragment.BaseFragment;
import com.tiancaijiazu.app.beans.MinVideoBean;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.mvp.IView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShortVideosFragment extends BaseFragment<IView, Presenter<IView>> implements IView {


    @BindView(R.id.recylerView)
    RecyclerView mRecylerView;
    Unbinder unbinder;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    Unbinder unbinder1;
    private int page = 1;
    private RlvAdapter_subhead_one mRlvAdapterSubheadOne;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:         //刷新加载
                    if (mRefreshLayout != null) {
                        mRefreshLayout.finishRefresh(true);
                        page = 1;
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("catalogId", mCatalogId);
                        map.put("page", page);
                        mPresenter.getDataP(map, DifferentiateEnum.MINVIDEO);
                    }
                    break;
                case 2:         //加载更多
                    if (mRefreshLayout != null) {
                        mRefreshLayout.finishLoadMore(true);
                        page++;
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("catalogId", mCatalogId);
                        map.put("page", page);
                        mPresenter.getDataP(map, DifferentiateEnum.MINVIDEO);
                    }

                    break;
            }
            return false;
        }
    });
    private String mCatalogId;

    public ShortVideosFragment() {
        // Required empty public constructor
    }

    public static ShortVideosFragment getInstance(String title, String catalogId) {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("catalogId", catalogId);
        ShortVideosFragment shortVideosFragment = new ShortVideosFragment();
        shortVideosFragment.setArguments(bundle);
        return shortVideosFragment;
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.fragment_short_videos;
    }

    @Override
    protected void initData() {
        initRecyclerView();
        Bundle bundle = getArguments();
        mCatalogId = bundle.getString("catalogId");
        HashMap<String, Object> map = new HashMap<>();
        map.put("catalogId", mCatalogId);
        map.put("page", page);
        mPresenter.getDataP(map, DifferentiateEnum.MINVIDEO);
        page = 1;
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

    private void initRecyclerView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        mRecylerView.setLayoutManager(gridLayoutManager);
        ArrayList<MinVideoBean.ResultBean> itemListBeans = new ArrayList<>();
        mRlvAdapterSubheadOne = new RlvAdapter_subhead_one(getContext(), itemListBeans);
        mRecylerView.setAdapter(mRlvAdapterSubheadOne);

        mRlvAdapterSubheadOne.setOnClickLisiter(new RlvAdapter_subhead_one.onClickLisiter() {
            @Override
            public void onClicker(View view, int position, ArrayList<MinVideoBean.ResultBean> mData) {
                Intent intent = new Intent(getContext(), ShortVideoActivity.class);
                intent.putExtra("data", mData);
                intent.putExtra("biao", "lei");
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum) {
            case MINVIDEO:
                MinVideoBean minVideoBean = (MinVideoBean) o;
                List<MinVideoBean.ResultBean> result = minVideoBean.getResult();
                if (result.size() != 0) {
                    //DataService.startService(getContext(), result);
                    if (page == 1) {
                        mRlvAdapterSubheadOne.addData(result,true);
                    } else {
                        mRlvAdapterSubheadOne.addData(result,false);
                    }
                } else {
                    mRlvAdapterSubheadOne.addData(result,false);
                }
                break;
        }
    }

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }

}
