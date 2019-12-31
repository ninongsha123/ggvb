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
import com.tiancaijiazu.app.activitys.user_fragment.adapters.RlvAdapter_it_attention;
import com.tiancaijiazu.app.base.activity.BaseActivity;
import com.tiancaijiazu.app.beans.ConcernBean;
import com.tiancaijiazu.app.beans.ItAttentionBean;
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

public class AttentionOfItActivity extends BaseActivity<IView, Presenter<IView>> implements IView {

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
    private RlvAdapter_it_attention mRlvAdapterItAttention;
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
                        mPresenter.getDataP(map, DifferentiateEnum.ITATTENTION);
                    }
                    break;
                case 2:         //加载更多
                    if (mRefreshLayout != null) {
                        mRefreshLayout.finishLoadMore(true);
                        page++;

                        HashMap<String, Object> map = new HashMap<>();
                        map.put("userId", mUserId + "");
                        map.put("page", page);
                        mPresenter.getDataP(map, DifferentiateEnum.ITATTENTION);
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

        mUserId = intent.getLongExtra("userId", 0);
        String biao = intent.getStringExtra("biao");
        if (biao != null) {
            mTitle.setText("我的关注");
        }
        boolean isbo = intent.getBooleanExtra("isbo", false);
        Log.i("1234", "initEventAndData: " + isbo);
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId", mUserId + "");
        map.put("page", page);
        mPresenter.getDataP1(map, DifferentiateEnum.ITATTENTION,loadingLayout);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRlv.setLayoutManager(linearLayoutManager);
        List<ItAttentionBean.ResultBean> resultBeans = new ArrayList<>();
        mRlvAdapterItAttention = new RlvAdapter_it_attention(resultBeans, this, isbo);
        mRlv.setAdapter(mRlvAdapterItAttention);
        loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
            @Override
            public void onReload(View v) {
                mPresenter.getDataP1(map, DifferentiateEnum.ITATTENTION,loadingLayout);
            }
        });
        mRlvAdapterItAttention.setOnClickLisiterUser(new RlvAdapter_it_attention.onClickLisiterUser() {
            @Override
            public void onClickerUser(View view, int position, List<ItAttentionBean.ResultBean> mData) {
                if (TimeUtil.isInvalidClick(view, 300))
                    return;
                long userId = mData.get(position).getUserId();
                Intent intent1 = new Intent(AttentionOfItActivity.this, UserCenterActivity.class);
                intent1.putExtra("userId", userId);
                startActivity(intent1);
            }
        });

        mIvFinis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mRlvAdapterItAttention.setOnClickLisiter(new RlvAdapter_it_attention.onClickLisiter() {
            @Override
            public void onClicker(View view, int position, List<ItAttentionBean.ResultBean> mData) {
                long userId1 = mData.get(position).getUserId();
                mPresenter.getDataP1(userId1 + "", DifferentiateEnum.CONCERN,loadingLayout);
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
        return R.layout.activity_attention_of_it;
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum) {
            case ITATTENTION:
                ItAttentionBean itAttentionBean = (ItAttentionBean) o;
                List<ItAttentionBean.ResultBean> result = itAttentionBean.getResult();

                if (result.size() != 0) {
                    if (page == 1) {
                        mRlvAdapterItAttention.addData(result, true);
                    } else {
                        mRlvAdapterItAttention.addData(result, false);
                    }
                    mState.setVisibility(View.GONE);
                    mRefreshLayout.setVisibility(View.VISIBLE);
                } else {
                    if (page != 1) {
                        mRlvAdapterItAttention.addData(result, false);
                        mState.setVisibility(View.GONE);
                        mRefreshLayout.setVisibility(View.VISIBLE);
                        ToastUtils.showShortToast(this,"暂无更多关注");
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
