package com.tiancaijiazu.app.fragments.outermostlayer.college_child;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.activitypage.collegeactivitys.ClassListActivity;
import com.tiancaijiazu.app.activitys.activitypage.WebActivity;
import com.tiancaijiazu.app.activitys.shopping_activity.ShopActivity;
import com.tiancaijiazu.app.base.fragment.BaseFragment;
import com.tiancaijiazu.app.beans.MallAdBean;
import com.tiancaijiazu.app.beans.MallAdBeanTwo;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.fragments.beans.CollegeParentBean;
import com.tiancaijiazu.app.fragments.outermostlayer.college_adapters.RlvAdapter_music;
import com.tiancaijiazu.app.fragments.outermostlayer.college_adapters.RlvAdapter_parent;
import com.tiancaijiazu.app.fragments.outermostlayer.college_adapters.RlvAdapter_video;
import com.tiancaijiazu.app.globals.WechatShareTool;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.PreUtils;
import com.tiancaijiazu.app.utils.TimeUtil;
import com.tiancaijiazu.app.utils.status.LoadingLayout;
import com.tiancaijiazu.app.mvp.IView;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 *
 * 父母学院
 */
public class Parent_educationFragment extends BaseFragment<IView, Presenter<IView>> implements IView {

    @BindView(R.id.rl_vip)
    RecyclerView rlVip;
    @BindView(R.id.loadinglayout)
    LoadingLayout loadinglayout;
    @BindView(R.id.rlv_video)
    RecyclerView rlvVideo;
    @BindView(R.id.rlv_music)
    RecyclerView rlvMusic;
    @BindView(R.id.short2)
    TextView short2;
    @BindView(R.id.short1)
    TextView short1;
    Unbinder unbinder;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    Unbinder unbinder1;
    private RlvAdapter_parent mAdapter;
    private RlvAdapter_video mRlvAdapter_video;
    private RlvAdapter_music mAdapter_music;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:         //刷新加载
                    if (mRefreshLayout != null) {
                        mRefreshLayout.finishRefresh(true);
                        initUp();
                    }
                    break;
                case 2:         //加载更多
                    if (mRefreshLayout != null) {
                        mRefreshLayout.finishLoadMore(true);
                    }
                    break;
            }
            return false;

        }

    });
    private View mInflate;
    private PopupWindow mPopupWindow1;

    public static Parent_educationFragment newInstance(String name, String catalogId) {
        Bundle bundle = new Bundle();
        bundle.putString("title",name);
        bundle.putString("catalogId",catalogId);
        Parent_educationFragment fragment = new Parent_educationFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.fragment_parent_education;
    }

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        String title = bundle.getString("title");
        String catalogId1 = bundle.getString("catalogId");
        String catalogId = PreUtils.getString("collegeone", "");
        mPresenter.getDataP1(catalogId1, DifferentiateEnum.PARENTID, loadinglayout);
        mPresenter.getDataP1("", DifferentiateEnum.STUDYADONE, loadinglayout);
        mPresenter.getDataP1("", DifferentiateEnum.STUDYADTWO, loadinglayout);
        loadinglayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
            @Override
            public void onReload(View v) {
                mPresenter.getDataP1(catalogId, DifferentiateEnum.PARENTID, loadinglayout);
                mPresenter.getDataP1("", DifferentiateEnum.STUDYADONE, loadinglayout);
                mPresenter.getDataP1("", DifferentiateEnum.STUDYADTWO, loadinglayout);
            }
        });
        GridLayoutManager manager = new GridLayoutManager(getContext(), 3);
        rlVip.setLayoutManager(manager);
        List<CollegeParentBean.ResultBean> list = new ArrayList<>();
        mAdapter = new RlvAdapter_parent(getContext(), list);
        rlVip.setAdapter(mAdapter);
        mAdapter.setOnItemClick(new RlvAdapter_parent.OnItemClick() {
            @Override
            public void onClick(View view, int position, List<CollegeParentBean.ResultBean> dataList) {
                if (TimeUtil.isInvalidClick(view, 300))
                    return;
                Intent in = new Intent(getContext(), Vip_classList.class);
                in.putExtra("data", (Serializable) dataList);
                in.putExtra("position", position);
                in.putExtra("biao", "父母教育学院");
                startActivity(in);
            }
        });

        GridLayoutManager manager1 = new GridLayoutManager(getContext(), 2);
        rlvVideo.setLayoutManager(manager1);
        List<MallAdBean.ResultBean> resultBeans = new ArrayList<>();
        mRlvAdapter_video = new RlvAdapter_video(getContext(), resultBeans);
        rlvVideo.setAdapter(mRlvAdapter_video);

        mRlvAdapter_video.setOnClickLisiter(new RlvAdapter_video.onClickLisiter() {
            @Override
            public void onClicker(View view, int position, List<MallAdBean.ResultBean> dataList) {
                String target = dataList.get(position).getTarget();
                String route = dataList.get(position).getRoute();
                if ("STUDY_COURSE".equals(route)) {
                    Intent intent = new Intent(getContext(), ClassListActivity.class);
                    intent.putExtra("target", target);
                    startActivity(intent);
                } else if ("MALL_SKU".equals(route)) {
                    Intent intent = new Intent(getContext(), ShopActivity.class);
                    intent.putExtra("target", target);
                    startActivity(intent);
                } else if ("WEB".equals(route)) {
                    Intent intent = new Intent(getContext(), WebActivity.class);
                    intent.putExtra("target", target);
                    intent.putExtra("title", dataList.get(position).getTitle());
                    startActivity(intent);
                }
            }
        });
        GridLayoutManager manager2 = new GridLayoutManager(getContext(), 2);
        rlvMusic.setLayoutManager(manager2);
        List<MallAdBeanTwo.ResultBean> resultBeans1 = new ArrayList<>();
        mAdapter_music = new RlvAdapter_music(getContext(), resultBeans1);
        rlvMusic.setAdapter(mAdapter_music);

        mAdapter_music.setOnClickLisiter(new RlvAdapter_music.onClickLisiter() {
            @Override
            public void onClicker(View view, int position, List<MallAdBeanTwo.ResultBean> mList) {
                String target = mList.get(position).getTarget();
                String route = mList.get(position).getRoute();
                if ("STUDY_COURSE".equals(route)) {
                    Intent intent = new Intent(getContext(), ClassListActivity.class);
                    intent.putExtra("target", target);
                    startActivity(intent);
                } else if ("MALL_SKU".equals(route)) {
                    Intent intent = new Intent(getContext(), ShopActivity.class);
                    intent.putExtra("target", target);
                    startActivity(intent);
                } else if ("WEB".equals(route)) {
                    Intent intent = new Intent(getContext(), WebActivity.class);
                    intent.putExtra("target", target);
                    intent.putExtra("title", mList.get(position).getTitle());
                    startActivity(intent);
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
                mHandler.sendMessageDelayed(message, 1000);
            }
        });

        /*mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                Message message = new Message();
                message.what = 2;
                mHandler.sendMessageDelayed(message, 1000);
            }
        });*/

    }



    public void initUp() {
        String catalogId = PreUtils.getString("collegeone", "");
        mPresenter.getDataP1(catalogId, DifferentiateEnum.PARENTID, loadinglayout);
        mPresenter.getDataP1("", DifferentiateEnum.STUDYADONE, loadinglayout);
        mPresenter.getDataP1("", DifferentiateEnum.STUDYADTWO, loadinglayout);

    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum) {
            case PARENTID:
                CollegeParentBean collegeParentBean = (CollegeParentBean) o;
                List<CollegeParentBean.ResultBean> result = collegeParentBean.getResult();
                Log.i("yx489", "show: " + result);
                mAdapter.addData(result);
                break;
            case STUDYADONE:
                MallAdBean mallAdBean = (MallAdBean) o;
                List<MallAdBean.ResultBean> result1 = mallAdBean.getResult();
                Log.i("yx433", "show: " + result1.size());
                mRlvAdapter_video.addData(result1);

                break;
            case STUDYADTWO:
                MallAdBeanTwo mallAdBean1 = (MallAdBeanTwo) o;
                List<MallAdBeanTwo.ResultBean> result2 = mallAdBean1.getResult();
                mAdapter_music.addData(result2);
                break;
        }
    }

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }

}
