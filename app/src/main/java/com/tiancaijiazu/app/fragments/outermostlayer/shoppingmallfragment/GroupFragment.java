package com.tiancaijiazu.app.fragments.outermostlayer.shoppingmallfragment;


import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.activitypage.ParentingEncyclopediaactivitys.UserCenterActivity;
import com.tiancaijiazu.app.activitys.activitypage.loginpages.LoginPhoneActivity;
import com.tiancaijiazu.app.activitys.issue.GroupActivity;
import com.tiancaijiazu.app.activitys.issue.OtherGroupActivity;
import com.tiancaijiazu.app.base.fragment.BaseFragment;
import com.tiancaijiazu.app.beans.GroupListBean;
import com.tiancaijiazu.app.beans.LikeBean;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.fragments.outermostlayer.shoppingmallfragment.adapter.RlvAdapter_groups;
import com.tiancaijiazu.app.mvp.IView;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.PreUtils;
import com.tiancaijiazu.app.utils.TimeUtil;
import com.tiancaijiazu.app.utils.city.ToastUtils;
import com.tiancaijiazu.app.utils.status.LoadingLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupFragment extends BaseFragment<IView, Presenter<IView>> implements IView {


    @BindView(R.id.recylerView)
    RecyclerView mRecylerView;
    @BindView(R.id.loadinglayout)
    LoadingLayout loadinglayout;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    Unbinder unbinder;
    public RlvAdapter_groups mRlvAdapterData;
    private int page = 1;
    private String mId;
    private StaggeredGridLayoutManager mGridLayoutManager;
    private boolean isItemDeleted=false;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
            HashMap<String, Object> map = new HashMap<>();
            page=1;
            map.put("page", page);
            mPresenter.getDataP1(map, DifferentiateEnum.GROUPLIST,loadinglayout);
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
                        mPresenter.getDataP1(map, DifferentiateEnum.GROUPLIST,loadinglayout);
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
                        mPresenter.getDataP1(map, DifferentiateEnum.GROUPLIST,loadinglayout);
                        loadinglayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
                            @Override
                            public void onReload(View v) {
                                mPresenter.getDataP1(map, DifferentiateEnum.GROUPLIST,loadinglayout);
                            }
                        });
                    }
                    break;
            }
            return false;
        }
    });

    @Override
    protected int creatrLayoutId() {
        return R.layout.fragment_hot__spot;
    }

    @Override
    protected void initData() {
        mId = PreUtils.getString("userId", "");
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
        mPresenter.getDataP1(map, DifferentiateEnum.GROUPLIST,loadinglayout);
        loadinglayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
            @Override
            public void onReload(View v) {
                mPresenter.getDataP1(map, DifferentiateEnum.GROUPLIST,loadinglayout);
            }
        });
    }

    private void initRecylerView() {
        mGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);//可防止Item切换
        mRecylerView.setLayoutManager(mGridLayoutManager);
        ArrayList<GroupListBean.ResultBean> resultBeans = new ArrayList<>();
        mRlvAdapterData = new RlvAdapter_groups(getContext(),resultBeans);
        mRecylerView.setAdapter(mRlvAdapterData);
        mRlvAdapterData.setOnClickLisiterPraise(new RlvAdapter_groups.onClickLisiterPraise() {
            @Override
            public void onClickPraise(View view, int position, CheckBox mIvCollect, ArrayList<GroupListBean.ResultBean> mData) {
                long articleId = mData.get(position).getArticleId();
                if (TimeUtil.isInvalidClick(view, 300))
                    return;
                if (mData.size() != 0) {
                    articleId = mData.get(position).getArticleId();
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("articleId", articleId);
                    map.put("contentType", "1");
                    mPresenter.getDataP(map, DifferentiateEnum.GIVEALIKE);
                }
            }
        });

        mRlvAdapterData.setOnClickLisiterUser(new RlvAdapter_groups.onClickLisiterUser() {
            @Override
            public void onClickerUser(View view, int position, ArrayList<GroupListBean.ResultBean> mData) {
                if (TimeUtil.isInvalidClick(view, 300)) {
                    return;
                }
                long userId = mData.get(position).getUserId();
                Intent intent1 = new Intent(getActivity(), UserCenterActivity.class);
                intent1.putExtra("userId", userId);
                startActivity(intent1);
            }
        });
        mRlvAdapterData.onsetOnClickData(new RlvAdapter_groups.setOnClickData() {
            @Override
            public void onClickData(View view, int position, ArrayList<GroupListBean.ResultBean> mData) {
                long ls = Long.parseLong(mId);
                if (ls==(mData.get(position).getUserId())) {
                    Intent intent = new Intent(getActivity(), GroupActivity.class);
                    intent.putExtra("articleId", mData.get(position).getArticleId());
                    intent.putExtra("userId", mData.get(position).getUserId());
                    getActivity().startActivity(intent);
                }else {
                    Intent intent = new Intent(getActivity(), OtherGroupActivity.class);
                    intent.putExtra("articleId", mData.get(position).getArticleId());
                    intent.putExtra("userId", mData.get(position).getUserId());
                    getActivity().startActivity(intent);
                }
            }
        });
        mRecylerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //防止第一行到顶部有空白区域
                //gridLayoutManager.invalidateSpanAssignments();
                //加载更多
            }
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isItemDeleted){
                    StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
                    int[] firstVisibleItem = null;
                    firstVisibleItem = layoutManager.findFirstVisibleItemPositions(firstVisibleItem);
                    if (firstVisibleItem != null && firstVisibleItem[0] == 0) {
                        if (mRlvAdapterData!=null) {
                            isItemDeleted = false;
                            mRlvAdapterData.notifyDataSetChanged();
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        HashMap<String, Object> map = new HashMap<>();
        page=1;
        map.put("page", page);
        mPresenter.getDataP1(map, DifferentiateEnum.GROUPLIST,loadinglayout);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum) {
            case GROUPLIST:
                GroupListBean groupListBean = (GroupListBean) o;
                List<GroupListBean.ResultBean> result = groupListBean.getResult();
                if (result.size() != 0) {
                    if (page == 1) {
//                            for (int i = 0; i < result.size(); i++) {
//                                long userId = result.get(i).getUserId();
//                                long l = Long.parseLong(mId);
//                                if (l == userId) {
//                                    if (i == 0)return;
//                                    mRlvAdapterData.notifyItemMoved(0, i);
//                                    mRlvAdapterData.notifyItemRangeChanged(Math.min(0, i), Math.abs(0 - i) +1);
//                                    break;
//                                }
//                            }
                        mRlvAdapterData.addData(result, true);
                    } else {
                        mRlvAdapterData.addData(result, false);
                    }
                } else {
                    mRlvAdapterData.addData(result, false);
                }
                break;
            case GIVEALIKE:
                LikeBean likeBean= (LikeBean) o;
                ToastUtils.showShortToast(mActivity, likeBean.getResult());
                break;
        }
    }

    @Override
    public void showError(String error) {

    }
}
