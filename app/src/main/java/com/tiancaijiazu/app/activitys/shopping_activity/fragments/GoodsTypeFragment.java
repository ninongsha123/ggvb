package com.tiancaijiazu.app.activitys.shopping_activity.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.shopping_activity.ShopActivity;
import com.tiancaijiazu.app.activitys.shopping_activity.adapter.RlvAdapter_goods_type;
import com.tiancaijiazu.app.base.fragment.BaseFragment;
import com.tiancaijiazu.app.beans.ShangpinBean;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.TimeUtil;
import com.tiancaijiazu.app.mvp.IView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class GoodsTypeFragment extends BaseFragment<IView, Presenter<IView>> implements IView {


    @BindView(R.id.recylerView)
    RecyclerView mRecylerView;
    Unbinder unbinder;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    Unbinder unbinder1;
    private RlvAdapter_goods_type mRlvAdapterGoodsType;
    private int page = 1;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:         //刷新加载
                    if(mRefreshLayout!=null){
                        mRefreshLayout.finishRefresh(true);
                        page = 1;
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("id",mCatalogId);
                        map.put("page",page);
                        mPresenter.getDataP(map, DifferentiateEnum.SHANGPINLIST);
                    }

                case 2:         //加载更多
                    if(mRefreshLayout!=null){
                        mRefreshLayout.finishLoadMore(true);
                        page++;
                        HashMap<String, Object> map1 = new HashMap<>();
                        map1.put("id",mCatalogId);
                        map1.put("page",page);
                        mPresenter.getDataP(map1, DifferentiateEnum.SHANGPINLIST);
                    }
                    break;
            }
            return false;
        }
    });
    private String mCatalogId;

    public GoodsTypeFragment() {
        // Required empty public constructor
    }

    public static GoodsTypeFragment getInstance(String title, String id) {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("catalogId", id);
        GoodsTypeFragment goodsTypeFragment = new GoodsTypeFragment();
        goodsTypeFragment.setArguments(bundle);
        return goodsTypeFragment;
    }


    @Override
    protected int creatrLayoutId() {
        return R.layout.fragment_goods_type;
    }

    @Override
    protected void initData() {
        page = 1;
        Bundle arguments = getArguments();
        mCatalogId = arguments.getString("catalogId");
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        mRecylerView.setLayoutManager(gridLayoutManager);
        List<ShangpinBean.ResultBean.ItemListBean> itemListBeans = new ArrayList<>();
        mRlvAdapterGoodsType = new RlvAdapter_goods_type(itemListBeans, getContext());
        mRecylerView.setAdapter(mRlvAdapterGoodsType);

        mRlvAdapterGoodsType.onSetClickLisiter(new RlvAdapter_goods_type.setClickLisiter() {
            @Override
            public void onClickComm(View view, int position, List<ShangpinBean.ResultBean.ItemListBean> mData) {
                if (TimeUtil.isInvalidClick(view, 300))
                    return;
                Intent intent = new Intent(getContext(), ShopActivity.class);
                intent.putExtra("id", mData.get(position).getSkuId() + "");
                startActivity(intent);
            }
        });
        Log.i("yxShop", "initData: "+mCatalogId);
        HashMap<String, Object> map = new HashMap<>();
        map.put("id",mCatalogId);
        map.put("page",page);
        mPresenter.getDataP(map, DifferentiateEnum.SHANGPINLIST);


        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                //List<String> data = initDatas();
                Message message = new Message();
                message.what = 1;
                //message.obj = data ;
                mHandler.sendMessageDelayed(message, 2000);
            }
        });

        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                Message message = new Message();
                message.what = 2;
                mHandler.sendMessageDelayed(message, 2000);
            }
        });
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum) {
            case SHANGPINLIST:
                ShangpinBean shangpinBean = (ShangpinBean) o;
                List<ShangpinBean.ResultBean.ItemListBean> itemList = shangpinBean.getResult().getItemList();
                Log.i("yx333", "show: "+itemList.size());
                if(itemList.size()!=0){
                    if(page==1){
                        mRlvAdapterGoodsType.addData(itemList,true);
                    }else {
                        mRlvAdapterGoodsType.addData(itemList,false);
                    }
                }else{
                    if(page!=1){
                        //ToastUtils.showShortToast(getContext(),"暂无更多最新数据");
                    }
                }
                break;
        }
    }

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }

}
