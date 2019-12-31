package com.tiancaijiazu.app.activitys.activitypage.homepageactivitys;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzx.starrysky.manager.MusicManager;
import com.lzx.starrysky.utils.TimerTaskManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.base.activity.BaseActivity;
import com.tiancaijiazu.app.beans.SongBeanss;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.fragments.adapters.ZaoAdapter;
import com.tiancaijiazu.app.mvp.IView;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.status.LoadingLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ZaoAnActivity extends BaseActivity<IView, Presenter<IView>> implements IView {


    @BindView(R.id.tuis)
    ImageView mTuis;
    @BindView(R.id.fan)
    ImageView mFan;
    @BindView(R.id.firsts)
    TextView mFirst;
    @BindView(R.id.bo)
    TextView mBo;
    @BindView(R.id.myRecyclerview)
    RecyclerView mMyRecyclerview;
    @BindView(R.id.line2)
    View mLine2;
    @BindView(R.id.loadingLayout)
    LoadingLayout loadingLayout;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    private MusicManager mInstance;
    TimerTaskManager mtimerTask;
    private ZaoAdapter mZaoAnAdapter;
    private List<SongBeanss.ResultBean> mResult;
    //    private List<MusicBean.ResultBean> mResult1;
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
                        map.put("catalogId","36673792621285376");
                        map.put("page", page);
                        mPresenter.getDataP1(map, DifferentiateEnum.SONGMORNS, loadingLayout);
                    }
                    break;
                case 2:         //加载更多
                    if (mRefreshLayout != null) {
                        mRefreshLayout.finishLoadMore(true);
                        page++;
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("catalogId","36673792621285376");
                        map.put("page", page);
                        mPresenter.getDataP1(map, DifferentiateEnum.SONGMORNS, loadingLayout);
                    }
                    break;
            }
            return false;
        }
    });


    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }

    @Override
    protected void initEventAndData() {
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        if (name!=null) {
            mFirst.setText("用音乐开启" + name + "的美好一天吧!");
        }
        mInstance = MusicManager.getInstance();
        mResult = new ArrayList<>();
        mtimerTask = new TimerTaskManager();
        HashMap<String, Object> map = new HashMap<>();
        map.put("catalogId","36673792621285376");
        map.put("page", page);
        mPresenter.getDataP1(map, DifferentiateEnum.SONGMORNS,loadingLayout);

        mZaoAnAdapter = new ZaoAdapter(mResult, this);
        mMyRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mMyRecyclerview.setAdapter(mZaoAnAdapter);

        loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
            @Override
            public void onReload(View v) {
                mPresenter.getDataP1(map, DifferentiateEnum.SONGMUSIC, loadingLayout);
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





        mFan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mInstance.stopMusic();
                finish();
            }
        });
    }


    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_zao_an;
    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum) {
            case SONGMORNS:
                SongBeanss songBean1 = (SongBeanss) o;
                List<SongBeanss.ResultBean> itemList = songBean1.getResult();
                if (itemList.size() != 0) {
                    if (page == 1) {
                        mZaoAnAdapter.addData(itemList, true);
                    } else {
                        mZaoAnAdapter.addData(itemList, false);
                    }
                } else {
                    mZaoAnAdapter.addData(itemList, false);
                }
                break;
        }
    }

    @Override
    public void showError(String error) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }
}
