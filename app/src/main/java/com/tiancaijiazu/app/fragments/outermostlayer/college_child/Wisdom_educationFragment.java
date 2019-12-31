package com.tiancaijiazu.app.fragments.outermostlayer.college_child;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.activitypage.collegeactivitys.ClassListActivity;
import com.tiancaijiazu.app.activitys.activitypage.WebActivity;
import com.tiancaijiazu.app.activitys.adapters.RlvAdapter_wisdom;
import com.tiancaijiazu.app.activitys.shopping_activity.ShopActivity;
import com.tiancaijiazu.app.base.fragment.BaseFragment;
import com.tiancaijiazu.app.beans.MallAdBean;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.fragments.beans.CollegeParentBean;
import com.tiancaijiazu.app.fragments.outermostlayer.college_adapters.RlvAdapter_parent;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.PreUtils;
import com.tiancaijiazu.app.utils.status.LoadingLayout;
import com.tiancaijiazu.app.mvp.IView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Wisdom_educationFragment extends BaseFragment<IView, Presenter<IView>> implements IView {

    @BindView(R.id.recylerView)
    RecyclerView mRecylerView;
    @BindView(R.id.recyler)
    RecyclerView mRecyler;
    @BindView(R.id.jing)
    TextView jing;
    @BindView(R.id.loadinglayout)
    LoadingLayout loadinglayout;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    private RlvAdapter_parent mAdapter;
    private RlvAdapter_wisdom mRlvAdapterWisdom;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:         //刷新加载
                    if (mRefreshLayout != null) {
                        mRefreshLayout.finishRefresh(true);
                        initUp();
                    }
                    break;
                case 2:         //加载更多
                    if (mRefreshLayout != null) {
                        mRefreshLayout.finishLoadMore(true);

                    }

                    break;
            }
            return false;
        }
    });
    public static Wisdom_educationFragment newInstance(String name, String catalogId) {
        Bundle bundle = new Bundle();
        bundle.putString("title",name);
        bundle.putString("catalogId",catalogId);
        Wisdom_educationFragment fragment = new Wisdom_educationFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public Wisdom_educationFragment() {

    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.fragment_wisdom_education;
    }

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        String title = bundle.getString("title");
        String catalogId1 = bundle.getString("catalogId");
        String catalogId = PreUtils.getString("collegethree", "");
        mPresenter.getDataP1(catalogId1, DifferentiateEnum.PARENTID, loadinglayout);
        mPresenter.getDataP1("", DifferentiateEnum.STUDYADFIVE, loadinglayout);
        loadinglayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
            @Override
            public void onReload(View v) {
                mPresenter.getDataP1(catalogId, DifferentiateEnum.PARENTID, loadinglayout);
                mPresenter.getDataP1("", DifferentiateEnum.STUDYADFIVE, loadinglayout);
            }
        });

        initRecylerView(title);
        initRecyler();
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                //List<String> data = initDatas();
                Message message = new Message();
                message.what = 1;
                //message.obj = data ;
                mHandler.sendMessageDelayed(message, 1000);
            }
        });

        /*mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                Message message = new Message();
                message.what = 2;
                mHandler.sendMessageDelayed(message, 1000);
            }
        });*/

    }

    public void initUp() {
        String catalogId = PreUtils.getString("collegethree", "");
        mPresenter.getDataP1(catalogId, DifferentiateEnum.PARENTID, loadinglayout);
        mPresenter.getDataP1("", DifferentiateEnum.STUDYADFIVE, loadinglayout);

    }

    private void initRecyler() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        mRecyler.setLayoutManager(gridLayoutManager);
        List<MallAdBean.ResultBean> resultBeans = new ArrayList<>();
        mRlvAdapterWisdom = new RlvAdapter_wisdom(resultBeans, getContext());
        mRecyler.setAdapter(mRlvAdapterWisdom);

        mRlvAdapterWisdom.setOnClickLisiter(new RlvAdapter_wisdom.onClickLisiter() {
            @Override
            public void onClicker(View view, int position, List<MallAdBean.ResultBean> mData) {
                String target = mData.get(position).getTarget();
                String route = mData.get(position).getRoute();
                String title = mData.get(position).getTitle();
                if ("STUDY_COURSE".equals(route)) {
                    Intent intent = new Intent(getContext(), ClassListActivity.class);
                    intent.putExtra("target", target);
                    startActivity(intent);
                } else if ("MALL_SKU".equals(route)) {
                    Intent intent = new Intent(getContext(), ShopActivity.class);
                    intent.putExtra("target", target);
                    startActivity(intent);
                } else if ("WEB".equals(route)) {
                    Intent intent = new Intent(getContext(), WebActivity.class);
                    intent.putExtra("target", target);
                    intent.putExtra("title", title);
                    startActivity(intent);
                }
            }
        });
    }

    private void initRecylerView(String title) {
        GridLayoutManager manager = new GridLayoutManager(getContext(), 3);
        mRecylerView.setLayoutManager(manager);
        List<CollegeParentBean.ResultBean> list = new ArrayList<>();
        mAdapter = new RlvAdapter_parent(getContext(), list);
        mRecylerView.setAdapter(mAdapter);
        mAdapter.setOnItemClick(new RlvAdapter_parent.OnItemClick() {
            @Override
            public void onClick(View view, int position, List<CollegeParentBean.ResultBean> dataList) {
                Intent in = new Intent(getContext(), Vip_classList.class);
                in.putExtra("data", (Serializable) dataList);
                in.putExtra("position", position);
                in.putExtra("biao", "幼儿教育学院");
                startActivity(in);
            }
        });
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum) {
            case PARENTID:
                CollegeParentBean collegeParentBean = (CollegeParentBean) o;
                List<CollegeParentBean.ResultBean> result = collegeParentBean.getResult();
                Log.i("college", "show: "+result.size());
                mAdapter.addData(result);
                break;
            case STUDYADFIVE:
                MallAdBean mallAdBean = (MallAdBean) o;
                List<MallAdBean.ResultBean> result1 = mallAdBean.getResult();
                mRlvAdapterWisdom.addData(result1);
                break;
        }
    }

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }

}
