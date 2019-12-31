package com.tiancaijiazu.app.activitys.topic.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.activitypage.ParentingEncyclopediaactivitys.RecDataActivity;
import com.tiancaijiazu.app.activitys.topic.adapters.RlvAdapter_hot;
import com.tiancaijiazu.app.base.fragment.BaseFragment;
import com.tiancaijiazu.app.beans.LikeBean;
import com.tiancaijiazu.app.beans.TopicDataBean;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.city.ToastUtils;
import com.tiancaijiazu.app.mvp.IView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class HotFragment extends BaseFragment<IView, Presenter<IView>> implements IView {


    @BindView(R.id.rlv)
    RecyclerView mRlv;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    private int page = 1;
    private String mData;
    private RlvAdapter_hot mRlvAdapterHot;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:         //刷新加载
                    if (mRefreshLayout != null) {
                        mRefreshLayout.finishRefresh(true);
                        page = 1;
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("subjectId", mData);
                        map.put("page", page);
                        map.put("orderBy", 2);
                        mPresenter.getDataP(map, DifferentiateEnum.TOPICDATAS);
                    }
                    break;
                case 2:         //加载更多
                    if (mRefreshLayout != null) {
                        mRefreshLayout.finishLoadMore(true);
                        page++;
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("subjectId", mData);
                        map.put("page", page);
                        map.put("orderBy", 2);
                        mPresenter.getDataP(map, DifferentiateEnum.TOPICDATAS);
                    }

                    break;
            }
            return false;
        }
    });

    public HotFragment() {
        // Required empty public constructor
    }

    public static HotFragment getInstance() {
        return new HotFragment();
    }


    @Override
    protected int creatrLayoutId() {
        return R.layout.fragment_hot;
    }

    @Override
    protected void initData() {
        Bundle arguments = getArguments();
        mData = arguments.getString("data");
        HashMap<String, Object> map = new HashMap<>();
        map.put("subjectId", mData);
        map.put("page", page);
        map.put("orderBy", 2);
        mPresenter.getDataP(map, DifferentiateEnum.TOPICDATAS);
        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        gridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        mRlv.setLayoutManager(gridLayoutManager);
        ((DefaultItemAnimator) mRlv.getItemAnimator()).setSupportsChangeAnimations(false);
        ((SimpleItemAnimator) mRlv.getItemAnimator()).setSupportsChangeAnimations(false);
        mRlv.getItemAnimator().setChangeDuration(0);
        List<TopicDataBean.ResultBean.ArticleListBean> articleListBeans = new ArrayList<>();
        mRlvAdapterHot = new RlvAdapter_hot(articleListBeans, getContext());
        mRlv.setAdapter(mRlvAdapterHot);
        mRlv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                gridLayoutManager.invalidateSpanAssignments();
            }
        });
        mRlvAdapterHot.setOnClickLisiter(new RlvAdapter_hot.onClickLisiter() {
            @Override
            public void onClicker(View view, int position, List<TopicDataBean.ResultBean.ArticleListBean> mData) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("articleId", mData.get(position).getArticleId());
                map.put("contentType", "1");
                mPresenter.getDataP(map, DifferentiateEnum.GIVEALIKE);
            }
        });
        mRlvAdapterHot.setOnClickLisiterData(new RlvAdapter_hot.onClickLisiterData() {
            @Override
            public void onClickerData(View view, int position, List<TopicDataBean.ResultBean.ArticleListBean> mData) {
                Intent intent = new Intent(getContext(), RecDataActivity.class);
                Log.i("yx", "onClickData: " + mData.get(position).getArticleId());
                long userId = mData.get(position).getUserId();
                String userAvatar = mData.get(position).getUserAvatar();
                String nickname = mData.get(position).getNickname();
                intent.putExtra("articleId", mData.get(position).getArticleId());
                intent.putExtra("userId", userId);
                intent.putExtra("userAvatar", userAvatar);
                intent.putExtra("nickname",nickname);
                intent.putExtra("position",position);
                intent.putExtra("biao","community");
                startActivityForResult(intent,22);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode== 22&&resultCode==33){
            int position = data.getIntExtra("position", 0);
            mRlvAdapterHot.mData.remove(position);
            mRlvAdapterHot.notifyItemRemoved(position);
            mRlvAdapterHot.notifyItemRangeChanged(0, mRlvAdapterHot.mData.size() - position);
            mRlvAdapterHot.notifyDataSetChanged();
        }
        if(requestCode== 22&&resultCode==35){
            int position = data.getIntExtra("position", 0);
            int likes = data.getIntExtra("likes", 0);
            int size = data.getIntExtra("size", 0);
            mRlvAdapterHot.mData.get(position).setLikes(size);
            mRlvAdapterHot.mData.get(position).setIsLikes(likes);
            mRlvAdapterHot.notifyItemChanged(position);
        }
    }
    @Override
    public void showError(String error) {

    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum) {
            case GIVEALIKE:
                LikeBean likeBean = (LikeBean) o;
                String result1 = likeBean.getResult();
                Toast.makeText(mContext, result1, Toast.LENGTH_SHORT).show();
                break;
            case TOPICDATAS:
                TopicDataBean topicDataBean = (TopicDataBean) o;
                List<TopicDataBean.ResultBean.ArticleListBean> articleList = topicDataBean.getResult().getArticleList();
                if (articleList.size() != 0) {
                    if (page == 1) {
                        mRlvAdapterHot.addData(articleList, true);
                    } else {
                        mRlvAdapterHot.addData(articleList, false);
                    }
                } else {
                    if(page!=1){
                        ToastUtils.showShortToast(getContext(),"暂无更多最新数据");
                    }
                    mRlvAdapterHot.addData(articleList, false);
                }
                break;
        }
    }

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }

}
