package com.tiancaijiazu.app.activitys.early.fragments;


import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.early.RecordCommentActivity;
import com.tiancaijiazu.app.activitys.early.adapters.RlvAdapter_record;
import com.tiancaijiazu.app.activitys.shopping_activity.LookPhotoActivity;
import com.tiancaijiazu.app.base.fragment.BaseFragment;
import com.tiancaijiazu.app.beans.ArticleLists;
import com.tiancaijiazu.app.beans.LikeBean;
import com.tiancaijiazu.app.beans.MyReleasedListBean;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.PreUtils;
import com.tiancaijiazu.app.utils.city.ToastUtils;
import com.tiancaijiazu.app.utils.status.LoadingLayout;
import com.tiancaijiazu.app.mvp.IView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecordFragment extends BaseFragment<IView, Presenter<IView>> implements IView {

    @BindView(R.id.tv_all)
    TextView mTvAll;
    @BindView(R.id.tv_my)
    TextView mTvMy;
    @BindView(R.id.loadingLayout)
    LoadingLayout loadingLayout;
    Unbinder unbinder;
    @BindView(R.id.recylerView)
    RecyclerView mRecylerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    private int page = 1;
    private int biao = 10;
    private RlvAdapter_record rlvAdapterRecord;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:         //刷新加载
                    if (mRefreshLayout != null) {
                        mRefreshLayout.finishRefresh(true);
                        page = 1;
                        if (biao == 10) {
                            HashMap<String, Object> map = new HashMap<>();
                            map.put("page", page);
                            map.put("articleType", "2");
                            map.put("orderByType", 0);
                            mPresenter.getDataP1(map, DifferentiateEnum.ARTICLELISTS,loadingLayout);
                        } else {
                            String userId = PreUtils.getString("userId", "");
                            HashMap<String, Object> map1 = new HashMap<>();
                            map1.put("page", page);
                            map1.put("userid", userId);
                            map1.put("articleType", "2");
                            mPresenter.getDataP1(map1, DifferentiateEnum.MYRELEASEDLIST,loadingLayout);
                        }
                    }
                    break;
                case 2:         //加载更多
                    if (mRefreshLayout != null) {
                        mRefreshLayout.finishLoadMore(true);
                        page++;
                        if (biao == 10) {
                            HashMap<String, Object> map = new HashMap<>();
                            map.put("page", page);
                            map.put("articleType", "2");
                            map.put("orderByType", 0);
                            mPresenter.getDataP1(map, DifferentiateEnum.ARTICLELISTS,loadingLayout);
                        } else {
                            String userId = PreUtils.getString("userId", "");
                            HashMap<String, Object> map1 = new HashMap<>();
                            map1.put("page", page);
                            map1.put("userid", userId);
                            map1.put("articleType", "2");
                            mPresenter.getDataP1(map1, DifferentiateEnum.MYRELEASEDLIST,loadingLayout);
                        }

                    }

                    break;
            }
            return false;
        }
    });

    @Override
    public void onStart() {
        String cards = PreUtils.getString("cards", "");
        if ("ok".equals(cards)) {
            Log.i("asdf", "onStart: ---------------");
            initData();
        }
        super.onStart();
    }

    public static RecordFragment getInstance() {
        return new RecordFragment();
    }


    public RecordFragment() {
        // Required empty public constructor
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.fragment_record;
    }

    @Override
    protected void initData() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("articleType", "2");
        map.put("orderByType", 0);
        mPresenter.getDataP1(map, DifferentiateEnum.ARTICLELISTS,loadingLayout);
        loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
            @Override
            public void onReload(View v) {
                mPresenter.getDataP1(map, DifferentiateEnum.ARTICLELISTS,loadingLayout);
            }
        });
        initRecylerView();
    }

    private void initRecylerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecylerView.setLayoutManager(linearLayoutManager);
        List<ArticleLists.ResultBean> resultBeans = new ArrayList<>();
        List<MyReleasedListBean.ResultBean> resultBeans1 = new ArrayList<>();
        rlvAdapterRecord = new RlvAdapter_record(getContext(), resultBeans, resultBeans1);
        mRecylerView.setAdapter(rlvAdapterRecord);

        rlvAdapterRecord.setOnClickLisiterPicture(new RlvAdapter_record.onClickLisiterPicture() {
            @Override
            public void onClickerPicture(View view, int position, ArrayList<String> strings) {
                //查看大图
                Intent intent = new Intent(getContext(), LookPhotoActivity.class);
                intent.putStringArrayListExtra("images", strings);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
        rlvAdapterRecord.setOnClickLisiterComment(new RlvAdapter_record.onClickLisiterComment() {
            @Override
            public void onClickerComment(View view, int position, List<ArticleLists.ResultBean> mData, List<MyReleasedListBean.ResultBean> mData1) {
                if (mData.size() != 0) {
                    Intent intent = new Intent(getContext(), RecordCommentActivity.class);
                    intent.putExtra("id", mData.get(position).getArticleId());
                    intent.putExtra("biao", "one");
                    intent.putExtra("position",position);
                    intent.putExtra("data", (Serializable) mData.get(position));
                    startActivityForResult(intent, 16);
                }
                if (mData1.size() != 0) {
                    Intent intent = new Intent(getContext(), RecordCommentActivity.class);
                    intent.putExtra("id", mData1.get(position).getArticleId());
                    intent.putExtra("biao", "two");
                    intent.putExtra("position",position);
                    intent.putExtra("data", mData1.get(position));
                    startActivityForResult(intent, 16);
                }
            }
        });

        rlvAdapterRecord.setOnClickLisiterLike(new RlvAdapter_record.onClickLisiterLike() {
            @Override
            public void onClickerLike(View view, int position, List<ArticleLists.ResultBean> mData, List<MyReleasedListBean.ResultBean> mData1) {
                if (mData.size() != 0) {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("articleId", mData.get(position).getArticleId());
                    map.put("contentType", "1");
                    mPresenter.getDataP1(map, DifferentiateEnum.GIVEALIKE,loadingLayout);
                    loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
                        @Override
                        public void onReload(View v) {
                            mPresenter.getDataP1(map, DifferentiateEnum.GIVEALIKE,loadingLayout);
                        }
                    });
                }
                if (mData1.size() != 0) {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("articleId", mData1.get(position).getArticleId());
                    map.put("contentType", "1");
                    mPresenter.getDataP1(map, DifferentiateEnum.GIVEALIKE,loadingLayout);
                    loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
                        @Override
                        public void onReload(View v) {
                            mPresenter.getDataP1(map, DifferentiateEnum.GIVEALIKE,loadingLayout);
                        }
                    });
                }
            }
        });
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                Message message = new Message();
                message.what = 1;
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
        if (requestCode == 16 && resultCode == 17) {
            //mRefreshLayout.autoRefresh();
            int position = data.getIntExtra("position", 0);
            boolean checked = data.getBooleanExtra("checked", false);
            String likes = data.getStringExtra("likes");
            int discuss = data.getIntExtra("discuss", 0);
            int i =0;
            if (!likes.equals("")) {
                i=Integer.parseInt(likes);
            }
            if(biao == 10){
                if(checked){
                    rlvAdapterRecord.mData.get(position).setIsLikes(1);
                    rlvAdapterRecord.mData.get(position).setLikes(i);
                    rlvAdapterRecord.mData.get(position).setDiscuss(discuss);
                    rlvAdapterRecord.notifyItemChanged(position);
                }else {
                    rlvAdapterRecord.mData.get(position).setIsLikes(0);
                    rlvAdapterRecord.mData.get(position).setLikes(i);
                    rlvAdapterRecord.mData.get(position).setDiscuss(discuss);
                    rlvAdapterRecord.notifyItemChanged(position);
                }
            }else if(biao == 11){
                if(checked){
                    rlvAdapterRecord.mData1.get(position).setIsLikes(1);
                    rlvAdapterRecord.mData1.get(position).setLikes(i);
                    rlvAdapterRecord.mData1.get(position).setDiscuss(discuss);
                    rlvAdapterRecord.notifyItemChanged(position);
                }else {
                    rlvAdapterRecord.mData1.get(position).setIsLikes(0);
                    rlvAdapterRecord.mData1.get(position).setLikes(i);
                    rlvAdapterRecord.mData1.get(position).setDiscuss(discuss);
                    rlvAdapterRecord.notifyItemChanged(position);
                }
            }

        }
    }

    @Override
    public void showError(String error) {

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
                        rlvAdapterRecord.addData(result, true);
                    } else {
                        rlvAdapterRecord.addData(result, false);
                    }
                } else {
                    if(page!=1){
                        ToastUtils.showShortToast(getContext(),"暂无更多最新数据");
                    }
                    rlvAdapterRecord.addData(result, false);
                }

                break;
            case MYRELEASEDLIST:
                MyReleasedListBean myReleasedListBean = (MyReleasedListBean) o;
                List<MyReleasedListBean.ResultBean> result1 = myReleasedListBean.getResult();
                if (result1.size() != 0) {
                    if (page == 1) {
                        rlvAdapterRecord.addData1(result1, true);
                    } else {
                        rlvAdapterRecord.addData1(result1, false);
                    }
                } else {
                    if(page!=1){
                        ToastUtils.showShortToast(getContext(),"暂无更多最新数据");
                    }
                    rlvAdapterRecord.addData1(result1, false);
                }
                break;
            case GIVEALIKE:
                LikeBean likeBean = (LikeBean) o;
                String result2 = likeBean.getResult();
                Toast.makeText(getContext(), result2, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }

    @OnClick({R.id.tv_all, R.id.tv_my})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_all:
                biao = 10;
                page = 1;
                mTvAll.setTextColor(Color.parseColor("#FFFFFF"));
                mTvMy.setTextColor(Color.parseColor("#00DEFF"));
                mTvAll.setBackgroundResource(R.drawable.bg_tab_ceshi_one);
                mTvMy.setBackgroundResource(R.drawable.bg_tab_ceshi_two);
                HashMap<String, Object> map = new HashMap<>();
                map.put("page", page);
                map.put("articleType", "2");
                map.put("orderByType", 0);
                mPresenter.getDataP1(map, DifferentiateEnum.ARTICLELISTS,loadingLayout);
                loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
                    @Override
                    public void onReload(View v) {
                        mPresenter.getDataP1(map, DifferentiateEnum.ARTICLELISTS,loadingLayout);
                    }
                });
                //PreUtils.putString("tab","tab");
                //mRefreshLayout.autoRefresh();
                break;
            case R.id.tv_my:
                biao = 11;
                page = 1;
                String userid = PreUtils.getString("userId", "");
                mTvAll.setTextColor(Color.parseColor("#00DEFF"));
                mTvMy.setTextColor(Color.parseColor("#FFFFFF"));
                mTvAll.setBackgroundResource(R.drawable.bg_tab_ceshi_three);
                mTvMy.setBackgroundResource(R.drawable.bg_tab_ceshi_four);
                HashMap<String, Object> map1 = new HashMap<>();
                map1.put("page", page);
                map1.put("userid", userid);
                map1.put("articleType", "2");
                mPresenter.getDataP1(map1, DifferentiateEnum.MYRELEASEDLIST,loadingLayout);
                loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
                    @Override
                    public void onReload(View v) {
                        mPresenter.getDataP1(map1, DifferentiateEnum.MYRELEASEDLIST,loadingLayout);
                    }
                });
                //mRefreshLayout.autoRefresh();
                break;
        }
    }
}
