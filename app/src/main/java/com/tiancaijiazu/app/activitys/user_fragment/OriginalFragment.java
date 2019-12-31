package com.tiancaijiazu.app.activitys.user_fragment;


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
import android.widget.ImageView;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.activitypage.ParentingEncyclopediaactivitys.RecDataActivity;
import com.tiancaijiazu.app.activitys.user_fragment.adapters.RlvAdapter_user_data;
import com.tiancaijiazu.app.base.fragment.BaseFragment;
import com.tiancaijiazu.app.beans.LikeBean;
import com.tiancaijiazu.app.beans.MyReleasedListBean;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.city.ToastUtils;
import com.tiancaijiazu.app.mvp.IView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class OriginalFragment extends BaseFragment<IView, Presenter<IView>> implements IView {


    public static RecyclerView mRlvData;
    @BindView(R.id.yuan_chuang_no)
    ImageView mYuanChuangNo;
    Unbinder unbinder;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    private RlvAdapter_user_data mRlvAdapterUserData;
    private String mData;
    private int page = 1;

    public OriginalFragment() {
        // Required empty public constructor
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
                        map.put("userid", mData);
                        map.put("page", page);
                        map.put("articleType", "1");
                        mPresenter.getDataP(map, DifferentiateEnum.MYRELEASEDLIST);
                    }
                    break;
                case 2:         //加载更多
                    if (mRefreshLayout != null) {
                        mRefreshLayout.finishLoadMore(true);
                        page++;

                        HashMap<String, Object> map = new HashMap<>();
                        map.put("userid", mData);
                        map.put("page", page);
                        map.put("articleType", "1");
                        mPresenter.getDataP(map, DifferentiateEnum.MYRELEASEDLIST);
                    }

                    break;
            }
            return false;
        }
    });

    public static OriginalFragment getInstance() {
        return new OriginalFragment();
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.fragment_original;
    }

    @Override
    protected void initData() {
        mRlvData = getActivity().findViewById(R.id.rlv_data);
        Bundle arguments = getArguments();
        mData = arguments.getString("data");
        HashMap<String, Object> map = new HashMap<>();
        map.put("userid", mData);
        map.put("page", page);
        map.put("articleType", "1");
        mPresenter.getDataP(map, DifferentiateEnum.MYRELEASEDLIST);

        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        gridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        mRlvData.setLayoutManager(gridLayoutManager);
        //mRlvData.setNestedScrollingEnabled(false);
        List<MyReleasedListBean.ResultBean> articleListBeans = new ArrayList<>();
        mRlvAdapterUserData = new RlvAdapter_user_data(articleListBeans, getContext());
        mRlvData.setAdapter(mRlvAdapterUserData);

        mRlvData.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                gridLayoutManager.invalidateSpanAssignments();
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

            }
        });
        mRlvAdapterUserData.setOnClickLisiter(new RlvAdapter_user_data.onClickLisiter() {
            @Override
            public void onClicker(View view, int position, List<MyReleasedListBean.ResultBean> mData) {
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

        mRlvAdapterUserData.setOnClickLisiterXin(new RlvAdapter_user_data.onClickLisiterXin() {
            @Override
            public void onClickerXin(View view, int position, List<MyReleasedListBean.ResultBean> mData) {
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
            mRlvAdapterUserData.mData.remove(position);
            mRlvAdapterUserData.notifyItemRemoved(position);
            mRlvAdapterUserData.notifyItemRangeChanged(0, mRlvAdapterUserData.mData.size() - position);
            mRlvAdapterUserData.notifyDataSetChanged();
        }
        if(requestCode== 22&&resultCode==35){
            int position = data.getIntExtra("position", 0);
            int likes = data.getIntExtra("likes", 0);
            int size = data.getIntExtra("size", 0);
            mRlvAdapterUserData.mData.get(position).setLikes(size);
            mRlvAdapterUserData.mData.get(position).setIsLikes(likes);

            mRlvAdapterUserData.notifyItemChanged(position);
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
                    //DataService.startService(getContext(), result);
                    if (page == 1) {
                        mRlvAdapterUserData.addData(result, true);
                    } else {
                        mRlvAdapterUserData.addData(result, false);
                    }
                    if(mYuanChuangNo!=null){
                        mYuanChuangNo.setVisibility(View.GONE);
                    }
                    if(mRlvData!=null){
                        mRlvData.setVisibility(View.VISIBLE);
                    }
                } else {
                    if(page!=1){
                        mRlvAdapterUserData.addData(result, false);
                        if(mYuanChuangNo!=null){
                            mYuanChuangNo.setVisibility(View.GONE);
                        }
                        if(mRlvData!=null){
                            mRlvData.setVisibility(View.VISIBLE);
                        }

                            ToastUtils.showShortToast(getContext(),"亲,已经到底了");

                    }else {
                        if(mYuanChuangNo!=null){
                            mYuanChuangNo.setVisibility(View.VISIBLE);
                        }
                        if(mRlvData!=null){
                            mRlvData.setVisibility(View.GONE);
                        }
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

}
