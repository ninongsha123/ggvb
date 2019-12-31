package com.tiancaijiazu.app.fragments.outermostlayer.shoppingmallfragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

import com.lzx.starrysky.manager.MusicManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.activitypage.loginpages.LoginPhoneActivity;
import com.tiancaijiazu.app.activitys.activitypage.ParentingEncyclopediaactivitys.RecDataActivity;
import com.tiancaijiazu.app.activitys.activitypage.ParentingEncyclopediaactivitys.UserCenterActivity;
import com.tiancaijiazu.app.base.fragment.BaseFragment;
import com.tiancaijiazu.app.beans.ArticleLists;
import com.tiancaijiazu.app.beans.TopicDataBean;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.fragments.outermostlayer.shoppingmallfragment.adapter.RlvAdapter_data;
import com.tiancaijiazu.app.mvp.Presenter;
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
public class HOT_SPOTFragment extends BaseFragment<IView, Presenter<IView>> implements IView {


    @BindView(R.id.recylerView)
    RecyclerView mRecylerView;
    @BindView(R.id.loadinglayout)
    LoadingLayout loadinglayout;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    Unbinder unbinder;
    public RlvAdapter_data mRlvAdapterData;
    private int page = 1;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
            HashMap<String, Object> map = new HashMap<>();
            page=1;
            map.put("page", page);
            map.put("articleType", "1");
            mPresenter.getDataP1(map, DifferentiateEnum.ARTICLELISTFOLLOW,loadinglayout);
        }
    }

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:         //刷新加载
                    if (mRefreshLayout != null) {
                        mRefreshLayout.finishRefresh(true);
                        page = 1;
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("page", page);
                        map.put("articleType", "1");
                        mPresenter.getDataP1(map, DifferentiateEnum.ARTICLELISTFOLLOW,loadinglayout);
                        loadinglayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
                            @Override
                            public void onReload(View v) {
                                mPresenter.getDataP1(map, DifferentiateEnum.ARTICLELISTFOLLOW,loadinglayout);
                            }
                        });
                    }
                    break;
                case 2:         //加载更多
                    if (mRefreshLayout != null) {
                        mRefreshLayout.finishLoadMore(true);
                        page++;
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("page", page);
                        map.put("articleType", "1");
                        mPresenter.getDataP1(map, DifferentiateEnum.ARTICLELISTFOLLOW,loadinglayout);
                        loadinglayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
                            @Override
                            public void onReload(View v) {
                                mPresenter.getDataP1(map, DifferentiateEnum.ARTICLELISTFOLLOW,loadinglayout);
                            }
                        });
                    }

                    break;
            }
            return false;
        }
    });


    public HOT_SPOTFragment() {
        // Required empty public constructor
    }


    @Override
    protected int creatrLayoutId() {
        return R.layout.fragment_hot__spot;
    }

    @Override
    protected void initData() {

        initRecylerView();

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
        HashMap<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("articleType", "1");
        mPresenter.getDataP1(map, DifferentiateEnum.ARTICLELISTFOLLOW,loadinglayout);
        loadinglayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
            @Override
            public void onReload(View v) {
                mPresenter.getDataP1(map, DifferentiateEnum.ARTICLELISTFOLLOW,loadinglayout);
            }
        });
    }

    private void initRecylerView() {
        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        gridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);//可防止Item切换
        mRecylerView.setLayoutManager(gridLayoutManager);
        ArrayList<ArticleLists.ResultBean> resultBeans = new ArrayList<>();
        List<TopicDataBean.ResultBean.ArticleListBean> articleListBeans = new ArrayList<>();
        mRlvAdapterData = new RlvAdapter_data(getContext(), resultBeans, articleListBeans);
        mRecylerView.setAdapter(mRlvAdapterData);
        mRlvAdapterData.setOnClickLisiterPraise(new RlvAdapter_data.onClickLisiterPraise() {
            @Override
            public void onClickPraise(View view, int position, CheckBox mIvCollect, ArrayList<ArticleLists.ResultBean> mData, List<TopicDataBean.ResultBean.ArticleListBean> mTopicData) {
                long articleId = mData.get(position).getArticleId();
                boolean tokenTime = TimeUtil.getTokenTime();
                if (tokenTime) {
                    Intent intent = new Intent(getContext(), LoginPhoneActivity.class);
                    intent.putExtra("login", "login");
                    startActivityForResult(intent, 11);
                } else {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("articleId", articleId);
                    map.put("contentType", "1");
                    mPresenter.getDataP1(map, DifferentiateEnum.GIVEALIKE,loadinglayout);
                    loadinglayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
                        @Override
                        public void onReload(View v) {
                            mPresenter.getDataP1(map, DifferentiateEnum.GIVEALIKE,loadinglayout);
                        }
                    });
                }
            }
        });

        mRlvAdapterData.setOnClickLisiterUser(new RlvAdapter_data.onClickLisiterUser() {
            @Override
            public void onClickerUser(View view, int position, ArrayList<ArticleLists.ResultBean> mData) {
                if (TimeUtil.isInvalidClick(view, 300)) {
                    return;
                }
                long userId = mData.get(position).getUserId();
                Intent intent1 = new Intent(getContext(), UserCenterActivity.class);
                intent1.putExtra("userId", userId);
                startActivity(intent1);
            }
        });
        mRlvAdapterData.onsetOnClickData(new RlvAdapter_data.setOnClickData() {
            @Override
            public void onClickData(View view, int position, ArrayList<ArticleLists.ResultBean> mData, List<TopicDataBean.ResultBean.ArticleListBean> mTopicData) {
                Intent intent = new Intent(getContext(), RecDataActivity.class);
                Log.i("yx", "onClickData: " + mData.get(position).getArticleId());
                long userId = mData.get(position).getUserId();
                String userAvatar = mData.get(position).getUserAvatar();
                intent.putExtra("articleId", mData.get(position).getArticleId());
                intent.putExtra("userId", userId);
                intent.putExtra("userAvatar", userAvatar);
                intent.putExtra("nickname", mData.get(position).getNickname());
                intent.putExtra("position", position);
                intent.putExtra("biao", "community");
                startActivityForResult(intent, 34);
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 34 && resultCode == 35) {
            HashMap<String, Object> map = new HashMap<>();
            page=1;
            map.put("page", page);
            map.put("articleType", "1");
            mPresenter.getDataP1(map, DifferentiateEnum.ARTICLELISTFOLLOW,loadinglayout);
        }

        if(requestCode == 34 && resultCode == 33){
            int position = data.getIntExtra("position", 0);
            mRlvAdapterData.mData.remove(position);
            mRlvAdapterData.notifyItemRemoved(position);
            mRlvAdapterData.notifyItemRangeChanged(0, mRlvAdapterData.mData.size() - position);
            mRlvAdapterData.notifyDataSetChanged();
        }
        if(requestCode == 34 && resultCode == 66){
            int position = data.getIntExtra("position", 0);
            mRlvAdapterData.mData.remove(position);
            mRlvAdapterData.notifyItemRemoved(position);
            mRlvAdapterData.notifyItemRangeChanged(0, mRlvAdapterData.mData.size() - position);
            mRlvAdapterData.notifyDataSetChanged();
        }

    }
    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum) {
            case ARTICLELISTFOLLOW:
                ArticleLists articleLists = (ArticleLists) o;
                List<ArticleLists.ResultBean> result = articleLists.getResult();
                if (result.size() != 0) {
                    if (page == 1) {
                        mRlvAdapterData.addData(result, true);
                    } else {
                        mRlvAdapterData.addData(result, false);
                    }
                } else {
                    mRlvAdapterData.addData(result, false);
                }
                break;
        }
    }

    @Override
    public void showError(String error) {

    }
}
