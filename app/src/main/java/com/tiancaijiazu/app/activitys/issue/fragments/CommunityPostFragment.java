package com.tiancaijiazu.app.activitys.issue.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.activitypage.ParentingEncyclopediaactivitys.RecDataActivity;
import com.tiancaijiazu.app.activitys.activitypage.ParentingEncyclopediaactivitys.UserCenterActivity;
import com.tiancaijiazu.app.activitys.issue.adapters.RlvAdapter_Community_post;
import com.tiancaijiazu.app.base.fragment.BaseFragment;
import com.tiancaijiazu.app.beans.LikeBean;
import com.tiancaijiazu.app.beans.MyReleasedListBean;
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
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommunityPostFragment extends BaseFragment<IView, Presenter<IView>> implements IView {


    @BindView(R.id.rlv_data)
    RecyclerView mRlvData;

    @BindView(R.id.loadingLayout)
    LoadingLayout loadingLayout;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    Unbinder unbinder;
    @BindView(R.id.state)
    ImageView mState;
    private int page = 1;
    private RlvAdapter_Community_post mRlvAdapterCommunityPost;
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
                        map.put("articleType", "1");
                        mPresenter.getDataP1(map, DifferentiateEnum.MYRELEASEDLIST,loadingLayout);
                    }

                    break;
                case 2:         //加载更多
                    if(mRefreshLayout!=null){
                        mRefreshLayout.finishLoadMore(true);
                        page++;
                        HashMap<String, Object> map1 = new HashMap<>();
                        map1.put("userid", mUserId);
                        map1.put("page", page);
                        map1.put("articleType", "1");
                        mPresenter.getDataP1(map1, DifferentiateEnum.MYRELEASEDLIST,loadingLayout);
                    }

                    break;
            }
            return false;
        }
    });
    private String mUserId;

    public CommunityPostFragment() {
        // Required empty public constructor
    }


    @Override
    protected int creatrLayoutId() {
        return R.layout.fragment_community_post;
    }

    @Override
    protected void initData() {
        initRecylerView();
        mUserId = PreUtils.getString("userId", "");
        HashMap<String, Object> map = new HashMap<>();
        map.put("userid", mUserId);
        map.put("page", page);
        map.put("articleType", "1");
        mPresenter.getDataP1(map, DifferentiateEnum.MYRELEASEDLIST,loadingLayout);
        loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
            @Override
            public void onReload(View v) {
                mPresenter.getDataP1(map, DifferentiateEnum.MYRELEASEDLIST,loadingLayout);
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

    private void initRecylerView() {
        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        gridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        mRlvData.setLayoutManager(gridLayoutManager);
        ArrayList<MyReleasedListBean.ResultBean> resultBeans = new ArrayList<>();
        mRlvAdapterCommunityPost = new RlvAdapter_Community_post(resultBeans, getContext());
        mRlvData.setAdapter(mRlvAdapterCommunityPost);

        mRlvAdapterCommunityPost.setOnClickLisiterPraise(new RlvAdapter_Community_post.onClickLisiterPraise() {
            @Override
            public void onClickPraise(View view, int position, CheckBox mIvCollect, ArrayList<MyReleasedListBean.ResultBean> mData) {
                if (TimeUtil.isInvalidClick(view, 300))
                    return;
                long articleId = mData.get(position).getArticleId();

                HashMap<String, Object> map = new HashMap<>();
                map.put("articleId", articleId);
                map.put("contentType", "1");
                mPresenter.getDataP1(map, DifferentiateEnum.GIVEALIKE,loadingLayout);
            }
        });

        mRlvAdapterCommunityPost.setOnClickLisiter(new RlvAdapter_Community_post.onClickLisiter() {
            @Override
            public void onClickLisiter(View view, int position, ArrayList<MyReleasedListBean.ResultBean> mData) {
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

        mRlvAdapterCommunityPost.setOnClickLisiterUser(new RlvAdapter_Community_post.onClickLisiterUser() {
            @Override
            public void onClickLisiter(View view, int position, ArrayList<MyReleasedListBean.ResultBean> mData) {
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 34 && resultCode == 35) {
            int position = data.getIntExtra("position", 0);
            int likes = data.getIntExtra("likes", 0);
            int size = data.getIntExtra("size", 0);
            mRlvAdapterCommunityPost.mData.get(position).setLikes(size);
            mRlvAdapterCommunityPost.mData.get(position).setIsLikes(likes);
            mRlvAdapterCommunityPost.notifyItemChanged(position);
        }

        if(requestCode == 34 && resultCode == 33){
            int position = data.getIntExtra("position", 0);
            mRlvAdapterCommunityPost.mData.remove(position);
            mRlvAdapterCommunityPost.notifyItemRemoved(position);
            mRlvAdapterCommunityPost.notifyItemRangeChanged(0, mRlvAdapterCommunityPost.mData.size() - position);
            mRlvAdapterCommunityPost.notifyDataSetChanged();
        }
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum) {
            case MYRELEASEDLIST:
                MyReleasedListBean myReleasedListBean = (MyReleasedListBean) o;
                List<MyReleasedListBean.ResultBean> result = myReleasedListBean.getResult();
                if (result.size() != 0) {
                    if (page == 1) {
                        mRlvAdapterCommunityPost.addData(result, true);

                    } else {
                        mRlvAdapterCommunityPost.addData(result, false);
                    }
                    mState.setVisibility(View.GONE);
                    mRefreshLayout.setVisibility(View.VISIBLE);
                } else {
                    mRlvAdapterCommunityPost.addData(result, false);
                    if (page == 1) {
                        mState.setVisibility(View.VISIBLE);
                        mRefreshLayout.setVisibility(View.GONE);
                    }else {
                            ToastUtils.showShortToast(getContext(),"暂无更多最新数据");
                    }
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
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
