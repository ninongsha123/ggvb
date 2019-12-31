package com.tiancaijiazu.app.activitys.user_fragment;


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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.activitypage.ParentingEncyclopediaactivitys.RecDataActivity;
import com.tiancaijiazu.app.activitys.user_fragment.adapters.RlvAdapter_userCollect;
import com.tiancaijiazu.app.activitys.user_fragment.adapters.RlvAdapter_userGroup;
import com.tiancaijiazu.app.base.fragment.BaseFragment;
import com.tiancaijiazu.app.beans.DiaryDetailBean;
import com.tiancaijiazu.app.beans.GroupListBean;
import com.tiancaijiazu.app.beans.LikeBean;
import com.tiancaijiazu.app.beans.MyCollectListBean;
import com.tiancaijiazu.app.beans.PersonalDetailsBean;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.mvp.IView;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.city.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupDataFragment extends BaseFragment<IView, Presenter<IView>> implements IView {

    @BindView(R.id.recylerView)
    RecyclerView mRecylerView;
    Unbinder unbinder;
    @BindView(R.id.sou_chang_no)
    ImageView mSouChangNo;
    Unbinder unbinder1;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    private long mData;
    private RlvAdapter_userGroup mRlvAdapterUserCollect;
    private int page = 1;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:         //刷新加载
                    if (mRefreshLayout != null) {
                        mRefreshLayout.finishRefresh(true);
                        page = 1;
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("userId", mData);
                        map.put("page", page);
                        mPresenter.getDataP(map, DifferentiateEnum.DIARYDEITL);
                    }
                    break;
                case 2:         //加载更多
                    if (mRefreshLayout != null) {
                        mRefreshLayout.finishLoadMore(true);
                        page++;
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("userId", mData);
                        map.put("page", page);
                        mPresenter.getDataP(map, DifferentiateEnum.DIARYDEITL);
                    }

                    break;
            }
            return false;
        }
    });

    public static GroupDataFragment getInstance() {
        return new GroupDataFragment();
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.fragment_user_collect;
    }

    @Override
    protected void initData() {
        Bundle arguments = getArguments();
        mData = arguments.getLong("data");
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId", mData);
        map.put("page", page);
        mPresenter.getDataP(map, DifferentiateEnum.DIARYDEITL);

        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        gridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        mRecylerView.setLayoutManager(gridLayoutManager);

        ((DefaultItemAnimator) mRecylerView.getItemAnimator()).setSupportsChangeAnimations(false);
        ((SimpleItemAnimator) mRecylerView.getItemAnimator()).setSupportsChangeAnimations(false);
        mRecylerView.getItemAnimator().setChangeDuration(0);
        List<DiaryDetailBean.ResultBean> collectListBeans = new ArrayList<>();
        mRlvAdapterUserCollect = new RlvAdapter_userGroup(collectListBeans, getContext());
        mRecylerView.setAdapter(mRlvAdapterUserCollect);
        mRecylerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                gridLayoutManager.invalidateSpanAssignments();
            }
        });
        mRlvAdapterUserCollect.setOnClickLisiter(new RlvAdapter_userGroup.onClickLisiter() {
            @Override
            public void onClicker(View view, int positon, List<DiaryDetailBean.ResultBean> mData) {
                Intent intent = new Intent(getContext(), RecDataActivity.class);
                Log.i("yx", "onClickData: " + mData.get(positon).getArticleId());
                long userId = mData.get(positon).getUserId();
                String userAvatar = mData.get(positon).getUserAvatar();
                String nickname = mData.get(positon).getNickname();
                intent.putExtra("articleId", mData.get(positon).getArticleId());
                intent.putExtra("userId", userId);
                intent.putExtra("userAvatar", userAvatar);
                intent.putExtra("nickname",nickname);
                intent.putExtra("position",positon);
                intent.putExtra("biao","community");
                startActivityForResult(intent,22);
            }
        });


        mRlvAdapterUserCollect.setOnClickLisiterXin(new RlvAdapter_userGroup.onClickLisiterXin() {
            @Override
            public void onClickerXin(View view, int position, List<DiaryDetailBean.ResultBean> mData) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("articleId", mData.get(position).getArticleId());
                map.put("contentType", "1");
                mPresenter.getDataP(map, DifferentiateEnum.GIVEALIKE);
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
            mRlvAdapterUserCollect.mData.remove(position);
            mRlvAdapterUserCollect.notifyItemRemoved(position);
            mRlvAdapterUserCollect.notifyItemRangeChanged(0, mRlvAdapterUserCollect.mData.size() - position);
            mRlvAdapterUserCollect.notifyDataSetChanged();
        }
        if(requestCode== 22&&resultCode==35){
            int position = data.getIntExtra("position", 0);
            int likes = data.getIntExtra("likes", 0);
            int size = data.getIntExtra("size", 0);
            mRlvAdapterUserCollect.mData.get(position).setLikes(size);
            mRlvAdapterUserCollect.mData.get(position).setIsLikes(likes);
            mRlvAdapterUserCollect.notifyItemChanged(position);
        }
    }
    @Override
    public void showError(String error) {

    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum) {
            case DIARYDEITL:
                DiaryDetailBean diaryDetailBean = (DiaryDetailBean) o;
                List<DiaryDetailBean.ResultBean> result = diaryDetailBean.getResult();
                if (result.size() != 0) {
                    //DataService.startService(getContext(), result);
                    if (page == 1) {
                        mRlvAdapterUserCollect.addData(result, true);
                    } else {
                        mRlvAdapterUserCollect.addData(result, false);
                    }
                    mSouChangNo.setVisibility(View.GONE);
                    mRecylerView.setVisibility(View.VISIBLE);
                } else {
                    if (page != 1) {
                        mRlvAdapterUserCollect.addData(result, false);
                        mSouChangNo.setVisibility(View.GONE);
                        mRecylerView.setVisibility(View.VISIBLE);

                            ToastUtils.showShortToast(getContext(),"亲,已经到底了");

                    } else {
                        mSouChangNo.setVisibility(View.VISIBLE);
                        mRecylerView.setVisibility(View.GONE);
                    }
                }

                break;
            case GIVEALIKE:
                LikeBean likeBean = (LikeBean) o;
                String result1 = likeBean.getResult();
                Toast.makeText(mContext, result1, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }


    public void addData(List<PersonalDetailsBean.ResultBean.CollectListBean> collectList) {
        if (collectList.size() != 0) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder1 = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder1.unbind();
    }
}
