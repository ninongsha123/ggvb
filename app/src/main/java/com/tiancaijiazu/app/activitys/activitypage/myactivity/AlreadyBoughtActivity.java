package com.tiancaijiazu.app.activitys.activitypage.myactivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.activitypage.collegeactivitys.ClassListActivity;
import com.tiancaijiazu.app.activitys.adapters.RlvAdapter_already_bounght;
import com.tiancaijiazu.app.base.activity.BaseActivity;
import com.tiancaijiazu.app.beans.AlreadyBoughtCourse;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.ScreenStatusUtil;
import com.tiancaijiazu.app.utils.city.ToastUtils;
import com.tiancaijiazu.app.utils.status.LoadingLayout;
import com.tiancaijiazu.app.mvp.IView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *    已购课程
 *
 */

public class AlreadyBoughtActivity extends BaseActivity<IView, Presenter<IView>> implements IView {


    @BindView(R.id.recylerView)
    RecyclerView mRecylerView;
    @BindView(R.id.a)
    TextView a;
    @BindView(R.id.iv_finis)
    ImageView mIvFinis;
    @BindView(R.id.loadingLayout)
    LoadingLayout loadingLayout;
    @BindView(R.id.relative)
    RelativeLayout mRelative;
    @BindView(R.id.v)
    View mV;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.state)
    ImageView mState;
    private int page = 1;
    private RlvAdapter_already_bounght mRlvAdapterAlreadyBounght;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:         //刷新加载
                    if (mRefreshLayout != null) {
                        mRefreshLayout.finishRefresh(true);
                        page = 1;
                        mPresenter.getDataP1(page, DifferentiateEnum.ABC,loadingLayout);
                    }
                    break;
                case 2:         //加载更多
                    if (mRefreshLayout != null) {
                        mRefreshLayout.finishLoadMore(true);
                        page++;

                        mPresenter.getDataP1(page, DifferentiateEnum.ABC,loadingLayout);
                    }

                    break;
            }
            return false;
        }
    });

    @Override
    protected void initEventAndData() {
        ScreenStatusUtil.setFillDip(this);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(this,2);
        mPresenter.getDataP1(page, DifferentiateEnum.ABC,loadingLayout);
        mRecylerView.setLayoutManager(linearLayoutManager);
        List<AlreadyBoughtCourse.ResultBean> resultBeans = new ArrayList<>();
        mRlvAdapterAlreadyBounght = new RlvAdapter_already_bounght(resultBeans, this);
        mRecylerView.setAdapter(mRlvAdapterAlreadyBounght);
        mIvFinis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
            @Override
            public void onReload(View v) {
                mPresenter.getDataP1(page, DifferentiateEnum.ABC,loadingLayout);
            }
        });
        mRlvAdapterAlreadyBounght.setOnClickLisiter(new RlvAdapter_already_bounght.onClickLisiter() {
            @Override
            public void onClicker(View view, int position, List<AlreadyBoughtCourse.ResultBean> mData) {
                long courseId = mData.get(position).getCourseId();
                Intent intent = new Intent(AlreadyBoughtActivity.this, ClassListActivity.class);
                intent.putExtra("target", courseId + "");
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
    protected int creatrLayoutId() {
        return R.layout.activity_already_bought;
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum) {
            case ABC:
                AlreadyBoughtCourse alreadyBoughtCourse = (AlreadyBoughtCourse) o;
                List<AlreadyBoughtCourse.ResultBean> result = alreadyBoughtCourse.getResult();
                if (result.size() != 0) {
                    if (page == 1) {
                        mRlvAdapterAlreadyBounght.addData(result, true);
                    } else {
                        mRlvAdapterAlreadyBounght.addData(result, false);
                    }
                    mState.setVisibility(View.GONE);
                    mRefreshLayout.setVisibility(View.VISIBLE);
                } else {
                    if (page != 1) {
                        mRlvAdapterAlreadyBounght.addData(result, false);
                        mState.setVisibility(View.GONE);
                        mRefreshLayout.setVisibility(View.VISIBLE);

                            ToastUtils.showShortToast(this,"暂无更多最新数据");

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
}
