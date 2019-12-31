package com.tiancaijiazu.app.activitys.collect.fragments;


import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.activitypage.ParentingEncyclopediaactivitys.RecDataActivity;
import com.tiancaijiazu.app.activitys.activitypage.ParentingEncyclopediaactivitys.UserCenterActivity;
import com.tiancaijiazu.app.activitys.collect.adapters.RlvAdapter_my_collect;
import com.tiancaijiazu.app.base.fragment.BaseFragment;
import com.tiancaijiazu.app.beans.LikeBean;
import com.tiancaijiazu.app.beans.MyCollectListBean;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.PreUtils;
import com.tiancaijiazu.app.utils.TimeUtil;
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
public class MineCollectFragment extends BaseFragment<IView, Presenter<IView>> implements IView {


    @BindView(R.id.rlv_data)
    RecyclerView mRlvData;
    @BindView(R.id.loadingLayout)
    LoadingLayout mLoadingLayout;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;

    public MineCollectFragment() {
        // Required empty public constructor
    }

    public static MineCollectFragment getInstance() {
        MineCollectFragment mineCollectFragment = new MineCollectFragment();
        return mineCollectFragment;
    }


    @Override
    protected int creatrLayoutId() {
        return R.layout.fragment_mine_collect;
    }
    private String mUserId;
    private int page = 1;
    private RlvAdapter_my_collect mRlvAdapterMyCollect;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:         //刷新加载
                    if(mRefreshLayout!=null){
                        mRefreshLayout.finishRefresh(true);
                        page = 1;
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("userid", mUserId);
                        map.put("page", page);
                        mPresenter.getDataP(map, DifferentiateEnum.MYCOLLECTLIST);
                    }

                    break;
                case 2:         //加载更多
                    if(mRefreshLayout!=null){
                        mRefreshLayout.finishLoadMore(true);
                        page++;
                        HashMap<String, Object> map1 = new HashMap<>();
                        map1.put("userid", mUserId);
                        map1.put("page", page);
                        mPresenter.getDataP(map1, DifferentiateEnum.MYCOLLECTLIST);
                    }

                    break;
            }
            return false;
        }
    });

    @Override
    protected void initData() {
        initRecylerView();
        mUserId = PreUtils.getString("userId", "");
        HashMap<String, Object> map = new HashMap<>();
        map.put("userid", mUserId);
        map.put("page", page);
        mPresenter.getDataP1(map, DifferentiateEnum.MYCOLLECTLIST, mLoadingLayout);

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
        mLoadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
            @Override
            public void onReload(View v) {
                mPresenter.getDataP1(map, DifferentiateEnum.MYCOLLECTLIST, mLoadingLayout);
            }
        });
    }

     private void initRecylerView() {
        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        gridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        mRlvData.setLayoutManager(gridLayoutManager);
        ArrayList<MyCollectListBean.ResultBean> resultBeans = new ArrayList<>();
        mRlvAdapterMyCollect = new RlvAdapter_my_collect(resultBeans, getContext());
        mRlvData.setAdapter(mRlvAdapterMyCollect);

        mRlvAdapterMyCollect.setOnClickLisiterPraise(new RlvAdapter_my_collect.onClickLisiterPraise() {
            @Override
            public void onClickPraise(View view, int position, CheckBox mIvCollect, ArrayList<MyCollectListBean.ResultBean> mData) {
                if (TimeUtil.isInvalidClick(view, 300))
                    return;
                long articleId = mData.get(position).getArticleId();

                HashMap<String, Object> map = new HashMap<>();
                map.put("articleId", articleId);
                map.put("contentType", "1");
                mPresenter.getDataP1(map, DifferentiateEnum.GIVEALIKE,mLoadingLayout);

            }
        });

        mRlvAdapterMyCollect.setOnClickLisiter(new RlvAdapter_my_collect.onClickLisiter() {
            @Override
            public void onClickLisiter(View view, int position, ArrayList<MyCollectListBean.ResultBean> mData) {
                if (TimeUtil.isInvalidClick(view, 300))
                    return;
                long articleId = mData.get(position).getArticleId();
                Intent intent = new Intent(getContext(), RecDataActivity.class);
                long userId = mData.get(position).getUserId();
                String userAvatar = mData.get(position).getUserAvatar();
                intent.putExtra("articleId", articleId);
                intent.putExtra("userId", userId);
                intent.putExtra("userAvatar", userAvatar);
                intent.putExtra("nickname", mData.get(position).getNickname());
                intent.putExtra("position", position);
                intent.putExtra("biao", "community");
                startActivityForResult(intent, 34);
            }
        });

        mRlvAdapterMyCollect.setOnClickLisiterUser(new RlvAdapter_my_collect.onClickLisiterUser() {
            @Override
            public void onClickLisiter(View view, int position, ArrayList<MyCollectListBean.ResultBean> mData) {
                if (TimeUtil.isInvalidClick(view, 300))
                    return;
                long userId = mData.get(position).getUserId();
                Intent intent1 = new Intent(getContext(), UserCenterActivity.class);
                intent1.putExtra("userId", userId);
                startActivity(intent1);
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
            case MYCOLLECTLIST:
                MyCollectListBean myCollectListBean = (MyCollectListBean) o;
                List<MyCollectListBean.ResultBean> result = myCollectListBean.getResult();
                if (result.size() != 0) {
                    if (page == 1) {
                        mRlvAdapterMyCollect.addData(result, true);
                    } else {
                        mRlvAdapterMyCollect.addData(result, false);
                    }
                } else {
                    if (page != 1) {
                        ToastUtils.showShortToast(getContext(), "暂无更多收藏");
                    }
                    mRlvAdapterMyCollect.addData(result, false);
                }

                break;
            case GIVEALIKE:
                LikeBean likeBean = (LikeBean) o;
                String result1 = likeBean.getResult();
                Toast.makeText(getContext(), result1, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 34 && resultCode == 35) {
            int position = data.getIntExtra("position", 0);
            int likes = data.getIntExtra("likes", 0);
            int size = data.getIntExtra("size", 0);
            mRlvAdapterMyCollect.mData.get(position).setLikes(size);
            mRlvAdapterMyCollect.mData.get(position).setIsLikes(likes);
            boolean checked = data.getBooleanExtra("checked", true);
            if(checked){
                mRlvAdapterMyCollect.notifyItemChanged(position);
            }else {
                mRlvAdapterMyCollect.mData.remove(position);
                mRlvAdapterMyCollect.notifyItemRemoved(position);
                mRlvAdapterMyCollect.notifyItemRangeChanged(0, mRlvAdapterMyCollect.mData.size() - position);
            }
        }

        if (requestCode == 34 && resultCode == 33) {
            int position = data.getIntExtra("position", 0);
            mRlvAdapterMyCollect.mData.remove(position);
            mRlvAdapterMyCollect.notifyItemRemoved(position);
            mRlvAdapterMyCollect.notifyItemRangeChanged(0, mRlvAdapterMyCollect.mData.size() - position);
            mRlvAdapterMyCollect.notifyDataSetChanged();
        }
    }
}
