package com.tiancaijiazu.app.activitys.user_fragment.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
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
import com.tiancaijiazu.app.activitys.activitypage.ParentingEncyclopediaactivitys.UserCenterActivity;
import com.tiancaijiazu.app.activitys.user_fragment.adapters.RlvAdapter_it_fans;
import com.tiancaijiazu.app.base.activity.BaseActivity;
import com.tiancaijiazu.app.beans.ConcernBean;
import com.tiancaijiazu.app.beans.FansBean;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.ScreenStatusUtil;
import com.tiancaijiazu.app.utils.TimeUtil;
import com.tiancaijiazu.app.utils.city.ToastUtils;
import com.tiancaijiazu.app.utils.status.LoadingLayout;
import com.tiancaijiazu.app.mvp.IView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FansItActivity extends BaseActivity<IView, Presenter<IView>> implements IView {


    @BindView(R.id.iv_finis)
    ImageView mIvFinis;
    @BindView(R.id.loadingLayout)
    LoadingLayout loadingLayout;
    @BindView(R.id.rlv)
    RecyclerView mRlv;
    @BindView(R.id.relative)
    RelativeLayout mRelative;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.state)
    ImageView mState;
    @BindView(R.id.title)
    TextView mTitle;
    private int page = 1;
    private RlvAdapter_it_fans mRlvAdapterItFans;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:         //刷新加载
                    if (mRefreshLayout != null) {
                        mRefreshLayout.finishRefresh(true);
                        page = 1;

                        HashMap<String, Object> map = new HashMap<>();
                        map.put("userId", mUserId + "");
                        map.put("page", page);
                        mPresenter.getDataP(map, DifferentiateEnum.ITFANS);
                    }
                    break;
                case 2:         //加载更多
                    if (mRefreshLayout != null) {
                        mRefreshLayout.finishLoadMore(true);
                        page++;

                        HashMap<String, Object> map = new HashMap<>();
                        map.put("userId", mUserId + "");
                        map.put("page", page);
                        mPresenter.getDataP(map, DifferentiateEnum.ITFANS);
                    }

                    break;
            }
            return false;
        }
    });
    private long mUserId;

    @Override
    protected void initEventAndData() {
        ScreenStatusUtil.setFillDip(this);
        Intent intent = getIntent();
        String biao = intent.getStringExtra("biao");
        if (biao != null) {
            mTitle.setText("我的粉丝");
        }
        mUserId = intent.getLongExtra("userId", 0);
        boolean isbo = intent.getBooleanExtra("isbo", false);
        Log.i("1234", "initEventAndData: " + isbo);
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId", mUserId + "");
        map.put("page", page);
        mPresenter.getDataP1(map, DifferentiateEnum.ITFANS,loadingLayout);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRlv.setLayoutManager(linearLayoutManager);
        ArrayList<FansBean.ResultBean> resultBeans = new ArrayList<>();
        mRlvAdapterItFans = new RlvAdapter_it_fans(resultBeans, this, isbo);
        mRlv.setAdapter(mRlvAdapterItFans);
        loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
            @Override
            public void onReload(View v) {
                mPresenter.getDataP1(map, DifferentiateEnum.ITFANS,loadingLayout);
            }
        });
        mRlvAdapterItFans.setOnClickLisiterUser(new RlvAdapter_it_fans.onClickLisiterUser() {
            @Override
            public void onClickerUser(View view, int position, List<FansBean.ResultBean> mData) {
                if (TimeUtil.isInvalidClick(view, 300))
                    return;
                long userId = mData.get(position).getUserId();
                Intent intent1 = new Intent(FansItActivity.this, UserCenterActivity.class);
                intent1.putExtra("userId", userId);
                startActivity(intent1);
            }
        });

        mRlvAdapterItFans.setOnClickLisiter(new RlvAdapter_it_fans.onClickLisiter() {
            @Override
            public void onClicker(View view, int position, List<FansBean.ResultBean> mData) {
                long userId1 = mData.get(position).getUserId();
                mPresenter.getDataP(userId1 + "", DifferentiateEnum.CONCERN);
            }
        });
        mIvFinis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
        return R.layout.activity_fans_it;
    }


    @Override
    public void showError(String error) {

    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum) {
            case ITFANS:
                FansBean fansBean = (FansBean) o;
                List<FansBean.ResultBean> result = fansBean.getResult();

                if (result.size() != 0) {
                    if (page == 1) {
                        mRlvAdapterItFans.addData(result, true);
                    } else {
                        mRlvAdapterItFans.addData(result, false);
                    }
                    mState.setVisibility(View.GONE);
                    mRefreshLayout.setVisibility(View.VISIBLE);
                } else {
                    if (page != 1) {
                        mRlvAdapterItFans.addData(result, false);
                        mState.setVisibility(View.GONE);
                        mRefreshLayout.setVisibility(View.VISIBLE);
                        ToastUtils.showShortToast(this,"暂无更多粉丝");
                    } else {
                        mState.setVisibility(View.VISIBLE);
                        mRefreshLayout.setVisibility(View.GONE);
                    }
                }
                break;
            case CONCERN:
                ConcernBean concernBean = (ConcernBean) o;
                String result1 = concernBean.getResult();
                Toast.makeText(mActivity, "" + result1, Toast.LENGTH_SHORT).show();
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
