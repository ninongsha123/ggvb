package com.tiancaijiazu.app.fragments.outermostlayer.college_child;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
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
import com.tiancaijiazu.app.activitys.activitypage.collegeactivitys.ClassListActivity;
import com.tiancaijiazu.app.base.fragment.BaseFragment;
import com.tiancaijiazu.app.beans.CourseListBean;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.fragments.outermostlayer.college_adapters.RlvAdapter_vip;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.PreUtils;
import com.tiancaijiazu.app.utils.TimeUtil;
import com.tiancaijiazu.app.utils.status.LoadingLayout;
import com.tiancaijiazu.app.mvp.IView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class Vip_classFragment extends BaseFragment<IView, Presenter<IView>> implements IView {


    @BindView(R.id.r)
    RecyclerView r;
    @BindView(R.id.loadingLayout)
    LoadingLayout loadingLayout;
    Unbinder unbinder;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    private int page = 1;
    private RlvAdapter_vip mRlvAdapter_vip;
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
                        mPresenter.getDataP(map, DifferentiateEnum.COURSELIST);
                    }

                    break;
                case 2:         //加载更多
                    if (mRefreshLayout != null) {
                        mRefreshLayout.finishLoadMore(true);
                        page++;
                        HashMap<String, Object> map1 = new HashMap<>();
                        map1.put("catalogId", mCatalogId);
                        map1.put("page", page);
                        mPresenter.getDataP(map1, DifferentiateEnum.COURSELIST);
                    }

                    break;
            }
            return false;
        }
    });
    private String mCatalogId;

    public Vip_classFragment() {

    }

    public static Vip_classFragment getInstance(String name, String title) {
        Bundle bundle = new Bundle();
        bundle.putString("catalogId", name);
        bundle.putString("title", title);
        Vip_classFragment vip_classFragment = new Vip_classFragment();
        vip_classFragment.setArguments(bundle);
        return vip_classFragment;
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.fragment_vip_class;
    }

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        mCatalogId = bundle.getString("catalogId");
        page = 1;
        HashMap<String, Object> map = new HashMap<>();
        map.put("catalogId", mCatalogId);
        map.put("page", page);
        mPresenter.getDataP1(map, DifferentiateEnum.COURSELIST,loadingLayout);
        loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
            @Override
            public void onReload(View v) {
                    mPresenter.getDataP1(map, DifferentiateEnum.COURSELIST,loadingLayout);
            }
        });
        List<CourseListBean.ResultBean> resultBeans = new ArrayList<>();
        mRlvAdapter_vip = new RlvAdapter_vip(getContext(), resultBeans);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        r.setLayoutManager(manager);
        r.setAdapter(mRlvAdapter_vip);
        mRlvAdapter_vip.setOnItemClick(new RlvAdapter_vip.OnItemClick() {
            @Override
            public void onClick(View view, int position, List<CourseListBean.ResultBean> dataList) {
                if (TimeUtil.isInvalidClick(view, 300))
                    return;
                long courseId = dataList.get(position).getCourseId();
                String summary = dataList.get(position).getSummary();
                Intent in = new Intent(getContext(), ClassListActivity.class);
                in.putExtra("courseId", courseId + "");
                PreUtils.putString("courseId", courseId + "");
                in.putExtra("summary",summary);
                startActivity(in);
            }
        });
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                //List<String> data = initDatas();
                Message message = new Message();
                message.what = 1;
                //message.obj = data ;
                mHandler.sendMessageDelayed(message, 2000);
            }
        });

        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                Message message = new Message();
                message.what = 2;
                mHandler.sendMessageDelayed(message, 2000);
            }
        });


    }



    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }


    @Override
    public void showError(String error) {

    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum) {
            case COURSELIST:
                CourseListBean courseListBean = (CourseListBean) o;
                List<CourseListBean.ResultBean> result = courseListBean.getResult();
                if (result.size() != 0) {
                    if (page == 1) {
                        mRlvAdapter_vip.addData(result, true);
                    } else {
                        mRlvAdapter_vip.addData(result, false);
                    }
                } else {

                }
                break;
        }
    }
}
