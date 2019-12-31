package com.tiancaijiazu.app.activitys.activitypage.ParentingEncyclopediaactivitys;


import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.adapters.RlvAdapter_like;
import com.tiancaijiazu.app.base.activity.BaseActivity;
import com.tiancaijiazu.app.beans.LikeListsBean;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.ScreenStatusUtil;
import com.tiancaijiazu.app.utils.TimeUtil;
import com.tiancaijiazu.app.utils.city.ToastUtils;
import com.tiancaijiazu.app.mvp.IView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *
 * 家族圈-条目详情（文字叙述下面，点击赞字进入点赞列表）
 */

public class LikeListActivity extends BaseActivity<IView, Presenter<IView>> implements IView {

    @BindView(R.id.iv_finis)
    ImageView mIvFinis;
    @BindView(R.id.a)
    TextView a;
    @BindView(R.id.rlv)
    RecyclerView mRlv;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    private long mArtivle;
    private int page = 1;
    private RlvAdapter_like mRlvAdapterLike;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:         //刷新加载
                    if (mRefreshLayout != null) {
                        mRefreshLayout.finishRefresh(true);
                        page = 1;
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("articleId", mArtivle);
                        map.put("page", page);
                        mPresenter.getDataP(map, DifferentiateEnum.LIKELISTS);
                    }
                    break;
                case 2:         //加载更多
                    if (mRefreshLayout != null) {
                        mRefreshLayout.finishLoadMore(true);
                        page++;
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("articleId", mArtivle);
                        map.put("page", page);
                        mPresenter.getDataP(map, DifferentiateEnum.LIKELISTS);
                    }

                    break;
            }
            return false;
        }
    });
    @Override
    protected void initEventAndData() {
        ScreenStatusUtil.setFillDip(this);
        Intent intent = getIntent();
        mArtivle = intent.getLongExtra("artivle", 0);
        HashMap<String, Object> map = new HashMap<>();
        map.put("articleId", mArtivle);
        map.put("page", page);
        mPresenter.getDataP(map, DifferentiateEnum.LIKELISTS);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRlv.setLayoutManager(linearLayoutManager);
        ArrayList<LikeListsBean.ResultBean> resultBeans = new ArrayList<>();
        mRlvAdapterLike = new RlvAdapter_like(resultBeans, this);
        mRlv.setAdapter(mRlvAdapterLike);

        mRlvAdapterLike.setOnClickLisiterUser(new RlvAdapter_like.onClickLisiterUser() {
            @Override
            public void onClickerUser(View view, int position, ArrayList<LikeListsBean.ResultBean> mData) {
                if (TimeUtil.isInvalidClick(view, 300))
                    return;
                long userId = mData.get(position).getUserId();
                Intent intent1 = new Intent(LikeListActivity.this, UserCenterActivity.class);
                intent1.putExtra("userId", userId);
                startActivity(intent1);
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
        return R.layout.activity_like_list;
    }

    @OnClick(R.id.iv_finis)
    public void onViewClicked() {
        finish();
    }


    @Override
    public void showError(String error) {

    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum) {
            case LIKELISTS:
                LikeListsBean likeListsBean = (LikeListsBean) o;
                Log.i("yx", "show: " + likeListsBean.getCode());
                List<LikeListsBean.ResultBean> result = likeListsBean.getResult();
                if(result.size()!=0){
                    if (page == 1) {
                        mRlvAdapterLike.addData(result, true);
                    } else {
                        mRlvAdapterLike.addData(result, false);
                    }

                }else {
                    if(page!=1){
                        ToastUtils.showShortToast(this,"暂无更多点赞");
                    }
                }
                break;
        }
    }

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }


}
