package com.tiancaijiazu.app.activitys.issue.fragments;


import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class CourseRecordFragment extends BaseFragment<IView, Presenter<IView>> implements IView {


    @BindView(R.id.recylerView)
    RecyclerView mRecylerView;
    @BindView(R.id.loadingLayout)
    LoadingLayout loadingLayout;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    private int page = 1;
    private RlvAdapter_record rlvAdapterRecord;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:         //刷新加载
                    if (mRefreshLayout != null) {
                        mRefreshLayout.finishRefresh(true);
                        page = 1;
                        String userId = PreUtils.getString("userId", "");
                        HashMap<String, Object> map1 = new HashMap<>();
                        map1.put("page", page);
                        map1.put("userid", userId);
                        map1.put("articleType", "2");
                        mPresenter.getDataP1(map1, DifferentiateEnum.MYRELEASEDLIST,loadingLayout);
                    }
                    break;
                case 2:         //加载更多
                    if (mRefreshLayout != null) {
                        mRefreshLayout.finishLoadMore(true);
                        page++;

                        String userId = PreUtils.getString("userId", "");
                        HashMap<String, Object> map1 = new HashMap<>();
                        map1.put("page", page);
                        map1.put("userid", userId);
                        map1.put("articleType", "2");
                        mPresenter.getDataP1(map1, DifferentiateEnum.MYRELEASEDLIST,loadingLayout);
                    }
                    break;
            }
            return false;
        }
    });

    public CourseRecordFragment() {
        // Required empty public constructor
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.fragment_course_record;
    }

    @Override
    protected void initData() {
        initRecylerView();
        String userid = PreUtils.getString("userId", "");
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
                if (mData1.size() != 0) {
                    Intent intent = new Intent(getContext(), RecordCommentActivity.class);
                    intent.putExtra("id", mData1.get(position).getArticleId());
                    intent.putExtra("biao", "two");
                    intent.putExtra("data", mData1.get(position));
                    startActivityForResult(intent, 16);
                }
            }
        });

        rlvAdapterRecord.setOnClickLisiterLike(new RlvAdapter_record.onClickLisiterLike() {
            @Override
            public void onClickerLike(View view, int position, List<ArticleLists.ResultBean> mData, List<MyReleasedListBean.ResultBean> mData1) {
                if (mData1.size() != 0) {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("articleId", mData1.get(position).getArticleId());
                    map.put("contentType", "1");
                    mPresenter.getDataP1(map, DifferentiateEnum.GIVEALIKE,loadingLayout);
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
            initData();
        }
    }

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum) {
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
    public void showError(String error) {

    }

}
