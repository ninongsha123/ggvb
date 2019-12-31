package com.tiancaijiazu.app.fragments.outermostlayer.shoppingmallfragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.activitypage.ParentingEncyclopediaactivitys.RecDataActivity;
import com.tiancaijiazu.app.activitys.activitypage.ParentingEncyclopediaactivitys.UserCenterActivity;
import com.tiancaijiazu.app.base.fragment.BaseFragment;
import com.tiancaijiazu.app.beans.ArticleLists;
import com.tiancaijiazu.app.beans.CommTopicSwitcherBean;
import com.tiancaijiazu.app.beans.LikeBean;
import com.tiancaijiazu.app.beans.ToKenBean;
import com.tiancaijiazu.app.beans.TopicDataBean;
import com.tiancaijiazu.app.dao.DataBaseMannger;
import com.tiancaijiazu.app.dao.ToKenDaoBean;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.fragments.outermostlayer.shoppingmallfragment.adapter.RlvAdapter_data;
import com.tiancaijiazu.app.fragments.outermostlayer.shoppingmallfragment.adapter.RlvAdapter_topic_switcher;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.TimeUtil;
import com.tiancaijiazu.app.utils.city.ToastUtils;
import com.tiancaijiazu.app.utils.status.LoadingLayout;
import com.tiancaijiazu.app.mvp.IView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecommendFragment extends BaseFragment<IView, Presenter<IView>> implements IView {


    @BindView(R.id.rlv_data)
    RecyclerView mRlvData;
    @BindView(R.id.line)
    LinearLayout mLine;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.to_load)
    RelativeLayout mToLoad;
    @BindView(R.id.loadinglayout)
    LoadingLayout loadinglayout;
    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    private int page = 1;
    public RlvAdapter_data mRlvAdapterData;
    private boolean isItemDeleted = false;
    private int mPosition;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:         //刷新加载
                    if (mRefreshLayout != null) {
                        mRefreshLayout.finishRefresh(true);
                        page = 1;
                        if (mSwitcher == 0) {
                            initBeg(1);
                        } else if(mSwitcher == 1){
                            initBeg(2);
                        }else {
                            HashMap<String, Object> map = new HashMap<>();
                            map.put("subjectId", mSubjectId + "");
                            map.put("page", page);
                            map.put("orderBy", 1);
                            mPresenter.getDataP1(map, DifferentiateEnum.TOPICDATAS, loadinglayout);
                        }
                    }
                    break;
                case 2:         //加载更多
                    if (mRefreshLayout != null) {
                        mRefreshLayout.finishLoadMore(true);
                        page++;
                        if (mSwitcher == 0) {
                            initBeg(1);
                        } else if(mSwitcher == 1){
                            initBeg(2);
                        }else {
                            HashMap<String, Object> map = new HashMap<>();
                            map.put("subjectId", mSubjectId + "");
                            map.put("page", page);
                            map.put("orderBy", 1);
                            mPresenter.getDataP1(map, DifferentiateEnum.TOPICDATAS, loadinglayout);
                        }
                    }

                    break;
            }
            return false;
        }
    });
    private RlvAdapter_topic_switcher mRlvAdapterTopicSwitcher;
    private int mSwitcher;
    private long mSubjectId;

    public RecommendFragment() {
        // Required empty public constructor
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.fragment_recommend;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
    private void moveToCenter(View itemView) {
        int[] locationView = new int[2];
        itemView.getLocationOnScreen(locationView);
        int viewWidth = itemView.getWidth();
        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int centerX = dm.widthPixels / 2;
        int distance = locationView[0] - centerX + viewWidth / 2;
        mRecycler.smoothScrollBy(distance, 0);
    }

    @Override
    protected void initData() {
        mPresenter.getDataP1("", DifferentiateEnum.COMMTOPICSWITCHER, loadinglayout);
        initRecycler();
        mRlvAdapterTopicSwitcher.addSwit(1);
        initBeg(2);
        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        gridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);//可防止Item切换
        mRlvData.setLayoutManager(gridLayoutManager);
        ArrayList<ArticleLists.ResultBean> resultBeans = new ArrayList<>();
        List<TopicDataBean.ResultBean.ArticleListBean> articleListBeans = new ArrayList<>();
        mRlvAdapterData = new RlvAdapter_data(getContext(), resultBeans, articleListBeans);
        //mRlvAdapterData.setHasStableIds(true);
        mRlvData.setAdapter(mRlvAdapterData);
//        Bundle arguments = getArguments();
//        if (arguments!=null) {
//            int num = arguments.getInt("num", 0);
//            if (num !=0) {
//                mRlvAdapterTopicSwitcher.addSwit(1);
//                initBeg(2);
//            }
//        }
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
        mRlvAdapterData.setOnClickLisiterUsers(new RlvAdapter_data.onClickLisiterUsers() {
            @Override
            public void onClickerUsers(View view, int position, List<TopicDataBean.ResultBean.ArticleListBean> mTopicData) {
                if (TimeUtil.isInvalidClick(view, 300)) {
                    return;
                }
                long userId = mTopicData.get(position).getUserId();
                Intent intent1 = new Intent(getContext(), UserCenterActivity.class);
                intent1.putExtra("userId", userId);
                startActivity(intent1);
            }
        });

        //mRlvData.setLoadingListener(this);
        mRlvData.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
        mRlvAdapterData.setOnClickLisiterPraise(new RlvAdapter_data.onClickLisiterPraise() {
            @Override
            public void onClickPraise(View view, int position, CheckBox
                    mIvCollect, ArrayList<ArticleLists.ResultBean> mData, List<TopicDataBean.ResultBean.ArticleListBean> mTopicData) {
                if (TimeUtil.isInvalidClick(view, 300))
                    return;
                if (mData.size() != 0) {
                    long articleId = mData.get(position).getArticleId();
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("articleId", articleId);
                    map.put("contentType", "1");
                    mPresenter.getDataP1(map, DifferentiateEnum.GIVEALIKE, loadinglayout);
                }
                if (mTopicData.size() != 0) {
                    long articleId = mTopicData.get(position).getArticleId();
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("articleId", articleId);
                    map.put("contentType", "1");
                    mPresenter.getDataP1(map, DifferentiateEnum.GIVEALIKE, loadinglayout);
                }
            }
        });
        mRlvAdapterData.onsetOnClickData(new RlvAdapter_data.setOnClickData() {
            @Override
            public void onClickData(View view, int position, ArrayList<
                    ArticleLists.ResultBean> mData, List<TopicDataBean.ResultBean.ArticleListBean> mTopicData) {
                if (TimeUtil.isInvalidClick(view, 300))
                    return;
                if (mData.size() != 0) {
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
                if (mTopicData.size() != 0) {
                    Intent intent = new Intent(getContext(), RecDataActivity.class);
                    Log.i("yx", "onClickData: " + mTopicData.get(position).getArticleId());
                    long userId = mTopicData.get(position).getUserId();
                    String userAvatar = mTopicData.get(position).getUserAvatar();
                    intent.putExtra("articleId", mTopicData.get(position).getArticleId());
                    intent.putExtra("userId", userId);
                    intent.putExtra("userAvatar", userAvatar);
                    intent.putExtra("nickname", mTopicData.get(position).getNickname());
                    intent.putExtra("position", position);
                    intent.putExtra("biao", "community");
                    startActivityForResult(intent, 78);
                }
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

    private void initRecycler() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecycler.setLayoutManager(linearLayoutManager);
        List<CommTopicSwitcherBean.ResultBean> resultBeans = new ArrayList<>();
        mRlvAdapterTopicSwitcher = new RlvAdapter_topic_switcher(resultBeans, getContext());
        mRecycler.setAdapter(mRlvAdapterTopicSwitcher);

        mRlvAdapterTopicSwitcher.setOnClickLisiter(new RlvAdapter_topic_switcher.onClickLisiter() {
            @Override
            public void onClicker(View view, int position, List<CommTopicSwitcherBean.ResultBean> mData) {
                mPosition = position;
                mRlvAdapterTopicSwitcher.addSwit(position);
                mSwitcher = position;
                page = 1;
                int size = mData.size()+2;
                if (size>2) {
                    if ( position > 1 && position < size - 2) {
                        moveToCenter(view);
                    } else if (position >= 0 && position < 2) {
                        mRecycler.smoothScrollToPosition(0);
                    } else {
                        mRecycler.smoothScrollToPosition(size-1);
                    }
                }
                if (position == 0) {
                    initBeg(1);
                } else if(position == 1){
                    initBeg(2);
                }else {
                    mSubjectId = mData.get(position - 2).getSubjectId();
                    HashMap<String, Object> map = new HashMap<>();
                    Log.i("yx565", "onClicker: "+mSubjectId);
                    map.put("subjectId", mSubjectId + "");
                    map.put("page", page);
                    map.put("orderBy", 1);
                    mPresenter.getDataP1(map, DifferentiateEnum.TOPICDATAS, loadinglayout);
                }
                mRlvData.scrollToPosition(0);
            }
        });
    }

    public void initBeg(int orderByType) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("articleType", "1");
        map.put("orderByType", orderByType);
        mPresenter.getDataP1(map, DifferentiateEnum.ARTICLELISTS, loadinglayout);

        loadinglayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
            @Override
            public void onReload(View v) {
                mPresenter.getDataP1(map, DifferentiateEnum.ARTICLELISTS, loadinglayout);
                mPresenter.getDataP1("", DifferentiateEnum.COMMTOPICSWITCHER, loadinglayout);
            }
        });
    }

    @Override
    public void showError(String error) {
        Log.i("yx", "showError: " + error);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void dataEvent(List<ArticleLists.ResultBean> data) {
        if (data.size() != 0) {
            Log.i("yx123", "dataEvent: 123456");
            if (page == 1) {
                mRlvAdapterData.addData(data, true);
            } else {
                mRlvAdapterData.addData(data, false);
            }
        } else {
            mRlvAdapterData.addData(data, false);
        }
    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {

        switch (differentiateEnum) {
            case ARTICLELISTS:
                ArticleLists articleLists = (ArticleLists) o;
                List<ArticleLists.ResultBean> result = articleLists.getResult();
                if (result.size() != 0) {
                    //DataService.startService(getContext(), result);
                    if (page == 1) {
                        mRlvAdapterData.addData(result, true);
                    } else {
                        mRlvAdapterData.addData(result, false);
                    }
                } else {
                    if(page!=1){
                        ToastUtils.showShortToast(getContext(),"暂无更多最新数据");
                    }
                    mRlvAdapterData.addData(result, false);
                }

                break;
            case GIVEALIKE:
                LikeBean likeBean = (LikeBean) o;
                String result1 = likeBean.getResult();
                //initBeg();
                Toast.makeText(mContext, result1, Toast.LENGTH_SHORT).show();
                break;
            case REFRESH:
                ToKenBean toKenBean = (ToKenBean) o;
                String access_token = toKenBean.getResult().getAccess_token();
                int expires_in = toKenBean.getResult().getExpires_in();
                String refresh_token = toKenBean.getResult().getRefresh_token();
                DataBaseMannger.getIntrance().deleteAll();
                ArrayList<ToKenDaoBean> toKenDaoBeans = new ArrayList<>();
                String nowTime = TimeUtil.getNowTime();
                toKenDaoBeans.add(new ToKenDaoBean(null, access_token, refresh_token, nowTime, expires_in + ""));
                DataBaseMannger.getIntrance().insert(toKenDaoBeans);
                break;
            case COMMTOPICSWITCHER:
                CommTopicSwitcherBean commTopicSwitcherBean = (CommTopicSwitcherBean) o;
                List<CommTopicSwitcherBean.ResultBean> result2 = commTopicSwitcherBean.getResult();
                mRlvAdapterTopicSwitcher.addData(result2);
                break;
            case TOPICDATAS:
                TopicDataBean topicDataBean = (TopicDataBean) o;
                List<TopicDataBean.ResultBean.ArticleListBean> articleList = topicDataBean.getResult().getArticleList();
                if (articleList.size() != 0) {
                    //DataService.startService(getContext(), result);
                    if (page == 1) {
                        mRlvAdapterData.addTopicData(articleList, true,mPosition);
                    } else {
                        mRlvAdapterData.addTopicData(articleList, false,mPosition);
                    }
                } else {
                    mRlvAdapterData.addTopicData(articleList, false,mPosition);
                    if(page!=1){
                        ToastUtils.showShortToast(getContext(),"暂无更多最新数据");
                    }
                }
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 11 && resultCode == 12) {
            mRefreshLayout.autoRefresh();
        }
        if (requestCode == 34 && resultCode == 35) {
            int position = data.getIntExtra("position", 0);
            int likes = data.getIntExtra("likes", 0);
            int size = data.getIntExtra("size", 0);
            mRlvAdapterData.mData.get(position).setLikes(size);
            mRlvAdapterData.mData.get(position).setIsLikes(likes);

            mRlvAdapterData.notifyItemChanged(position);
        }
        if(requestCode == 34&&resultCode == 33){
            int position = data.getIntExtra("position", 0);
            mRlvAdapterData.mData.remove(position);
            mRlvAdapterData.notifyItemRemoved(position);
            mRlvAdapterData.notifyItemRangeChanged(0, mRlvAdapterData.mData.size() - position);
            mRlvAdapterData.notifyDataSetChanged();
        }

        if(requestCode == 78 && resultCode == 33){
            int position = data.getIntExtra("position", 0);
            mRlvAdapterData.mTopicData.remove(position);
            mRlvAdapterData.notifyItemRemoved(position);
            mRlvAdapterData.notifyItemRangeChanged(0, mRlvAdapterData.mTopicData.size() - position);
            mRlvAdapterData.notifyDataSetChanged();
        }
        if (requestCode == 78 && resultCode == 35) {
            int position = data.getIntExtra("position", 0);
            int likes = data.getIntExtra("likes", 0);
            int size = data.getIntExtra("size", 0);
            mRlvAdapterData.mTopicData.get(position).setLikes(size);
            mRlvAdapterData.mTopicData.get(position).setIsLikes(likes);

            mRlvAdapterData.notifyItemChanged(position);
        }
    }

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }

}
