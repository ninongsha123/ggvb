package com.tiancaijiazu.app.homepagefragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.activitypage.collegeactivitys.ClassListActivity;
import com.tiancaijiazu.app.activitys.activitypage.WebActivity;
import com.tiancaijiazu.app.activitys.shopping_activity.InfantActivity;
import com.tiancaijiazu.app.activitys.shopping_activity.OwnBrandActivity;
import com.tiancaijiazu.app.activitys.shopping_activity.ShopActivity;
import com.tiancaijiazu.app.activitys.shopping_activity.ShoppingCarActivity;
import com.tiancaijiazu.app.activitys.shopping_activity.ToyActivity;
import com.tiancaijiazu.app.base.fragment.BaseFragment;
import com.tiancaijiazu.app.beans.AdPositionIdBean;
import com.tiancaijiazu.app.beans.MallAdBean;
import com.tiancaijiazu.app.beans.ShopBean;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.fragments.adapters.RlvAdapter_hot_sale;
import com.tiancaijiazu.app.fragments.adapters.RlvAdapter_own_brand;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.ScreenStatusUtil;
import com.tiancaijiazu.app.utils.TimeUtil;
import com.tiancaijiazu.app.utils.status.LoadingLayout;
import com.tiancaijiazu.app.mvp.IView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 *
 *  商城
 */
public class ParentingEncyclopediaFragment extends BaseFragment<IView, Presenter<IView>> implements IView {


    @BindView(R.id.banner)
    Banner mBanner;
    @BindView(R.id.ll_index_container)
    LinearLayout mLlIndexContainer;
    @BindView(R.id.rlv)
    RecyclerView mRlv;
    @BindView(R.id.recylerView)
    RecyclerView mRecylerView;
    @BindView(R.id.line_own)
    LinearLayout mLineOwn;
    @BindView(R.id.gouwu_car)
    ImageView mGouwuCar;
    @BindView(R.id.relative)
    RelativeLayout mRelative;
    @BindView(R.id.brand_one)
    TextView mBrandOne;
    @BindView(R.id.you)
    TextView you;
    @BindView(R.id.ziyou)
    TextView ziyou;
    @BindView(R.id.brand_two)
    TextView mBrandTwo;
    @BindView(R.id.brand_three)
    TextView mBrandThree;
    @BindView(R.id.text)
    TextView textView;
    @BindView(R.id.line_infant)
    LinearLayout mLineInfant;
    @BindView(R.id.line_toy)
    LinearLayout mLineToy;
    @BindView(R.id.rounded)
    RoundedImageView mRounded;
    @BindView(R.id.loadingLayout)
    LoadingLayout mLoadingLayout;
    Unbinder unbinder;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    private ArrayList<String> mList;
    private ShopBean mShopBean;
    private RlvAdapter_hot_sale mRlvAdapterHotSale;
    private RlvAdapter_own_brand mRlvAdapterOwnBrand;
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
    private String mName;
    private String mName1;
    private String mName2;

    public static ParentingEncyclopediaFragment newInstance() {
        ParentingEncyclopediaFragment fragment = new ParentingEncyclopediaFragment();
        return fragment;
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.fragment_parenting_encyclopedia;
    }

    @Override
    protected void initData() {
        ScreenStatusUtil.setNotStatus(getContext(), mRelative);
        mPresenter.getDataP1("0", DifferentiateEnum.SHOPBEAN, mLoadingLayout);
        mPresenter.getDataP1("", DifferentiateEnum.SHOPBANNAR, mLoadingLayout);
        mPresenter.getDataP1("", DifferentiateEnum.MALLADONE, mLoadingLayout);
        mPresenter.getDataP1("", DifferentiateEnum.MALLADTWO, mLoadingLayout);
        mPresenter.getDataP1("", DifferentiateEnum.MALLADNINE, mLoadingLayout);
        mLoadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
            @Override
            public void onReload(View v) {
                mPresenter.getDataP1("0", DifferentiateEnum.SHOPBEAN, mLoadingLayout);
                mPresenter.getDataP1("", DifferentiateEnum.SHOPBANNAR, mLoadingLayout);
                mPresenter.getDataP1("", DifferentiateEnum.MALLADONE, mLoadingLayout);
                mPresenter.getDataP1("", DifferentiateEnum.MALLADTWO, mLoadingLayout);
                mPresenter.getDataP1("", DifferentiateEnum.MALLADNINE, mLoadingLayout);
            }
        });

        initRlv();
        initRecylerView();
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

        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                Message message = new Message();
                message.what = 2;
                mHandler.sendMessageDelayed(message, 200);
            }
        });
    }

    public void initUp() {
        mPresenter.getDataP("0", DifferentiateEnum.SHOPBEAN);
        mPresenter.getDataP("", DifferentiateEnum.SHOPBANNAR);
        mPresenter.getDataP("", DifferentiateEnum.MALLADONE);
        mPresenter.getDataP("", DifferentiateEnum.MALLADTWO);
        mPresenter.getDataP("", DifferentiateEnum.MALLADNINE);
    }

    private void initRecylerView() {
        GridLayoutManager layoutManager = new GridLayoutManager(mContext, 2);
        mRecylerView.setLayoutManager(layoutManager);
        List<MallAdBean.ResultBean> resultBeans = new ArrayList<>();
        mRlvAdapterOwnBrand = new RlvAdapter_own_brand(resultBeans, getContext());
        mRecylerView.setAdapter(mRlvAdapterOwnBrand);

        mRlvAdapterOwnBrand.setOnClickLisiter(new RlvAdapter_own_brand.onClickLisiter() {
            @Override
            public void onClicker(View view, int position, List<MallAdBean.ResultBean> mData) {
                String target = mData.get(position).getTarget();
                String route = mData.get(position).getRoute();
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
                    intent.putExtra("title", mData.get(position).getTitle());
                    startActivity(intent);
                }
            }
        });
    }

    private void initRlv() {
        GridLayoutManager layoutManager = new GridLayoutManager(mContext, 3);
        mRlv.setLayoutManager(layoutManager);
        List<MallAdBean.ResultBean> resultBeans = new ArrayList<>();
        mRlvAdapterHotSale = new RlvAdapter_hot_sale(resultBeans, getContext());
        mRlv.setAdapter(mRlvAdapterHotSale);

        mRlvAdapterHotSale.setOnClickLisiter(new RlvAdapter_hot_sale.onClickLisiter() {
            @Override
            public void onClicker(View view, int position, List<MallAdBean.ResultBean> mData) {
                String target = mData.get(position).getTarget();
                String route = mData.get(position).getRoute();
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
                    intent.putExtra("title", mData.get(position).getTitle());
                    startActivity(intent);
                }
            }
        });
    }

    @OnClick({R.id.line_own, R.id.gouwu_car, R.id.line_infant, R.id.line_toy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.line_own:
                if (TimeUtil.isInvalidClick(view, 300))
                    return;
                String catalogId = mShopBean.getResult().get(0).getCatalogId() + "";
                Intent intent = new Intent(getContext(), OwnBrandActivity.class);
                intent.putExtra("catalogId", catalogId);
                intent.putExtra("title",mName);
                startActivity(intent);
                break;
            case R.id.gouwu_car:
                if (TimeUtil.isInvalidClick(view, 300))
                    return;
                intoActivity(ShoppingCarActivity.class);
                break;
            case R.id.line_infant:
                if (TimeUtil.isInvalidClick(view, 300))
                    return;
                String catalogId1 = mShopBean.getResult().get(1).getCatalogId() + "";
                Intent intent1 = new Intent(getContext(), InfantActivity.class);
                intent1.putExtra("catalogId", catalogId1);
                intent1.putExtra("title",mName1);
                startActivity(intent1);
                break;
            case R.id.line_toy:
                if (TimeUtil.isInvalidClick(view, 300))
                    return;
                String catalogId2 = mShopBean.getResult().get(2).getCatalogId() + "";
                Intent intent2 = new Intent(getContext(), ToyActivity.class);
                intent2.putExtra("catalogId", catalogId2);
                intent2.putExtra("title",mName2);
                startActivity(intent2);
                break;
        }
    }

    public void intoActivity(Class mClass) {
        Intent intent = new Intent(getContext(), mClass);
        startActivity(intent);
    }

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }


    @Override
    public void showError(String error) {

    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum) {
            case SHOPBEAN:
                mShopBean = (ShopBean) o;
                mName = mShopBean.getResult().get(0).getName();
                mName1 = mShopBean.getResult().get(1).getName();
                mName2 = mShopBean.getResult().get(2).getName();
                mBrandOne.setText(mName);
                mBrandTwo.setText(mName1);
                mBrandThree.setText(mName2);
                break;
            case SHOPBANNAR:
                AdPositionIdBean adPositionIdBean = (AdPositionIdBean) o;
                List<AdPositionIdBean.ResultBean> result = adPositionIdBean.getResult();
                mList = new ArrayList<>();
                for (int i = 0; i < result.size(); i++) {
                    mList.add(result.get(i).getPicUri());
                }
                mBanner.setBannerStyle(BannerConfig.NOT_INDICATOR);
                mBanner.setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {
                        String route = result.get(position).getRoute();
                        String target = result.get(position).getTarget();
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
                            startActivity(intent);
                        }
                    }
                }).setImages(mList).setImageLoader(new ImageLoader() {
                    @Override
                    public void displayImage(Context context, Object path, ImageView imageView) {
                        Glide.with(context).load(path).into(imageView);
                    }
                }).start();
                break;
            case MALLADONE:
                MallAdBean mallAdBean = (MallAdBean) o;
                List<MallAdBean.ResultBean> result1 = mallAdBean.getResult();
                mRlvAdapterHotSale.addData(result1);
                break;
            case MALLADTWO:
                MallAdBean mallAdBean1 = (MallAdBean) o;
                List<MallAdBean.ResultBean> result2 = mallAdBean1.getResult();
                mRlvAdapterOwnBrand.addData(result2);
                break;
            case MALLADNINE:
                MallAdBean mallAdBean2 = (MallAdBean) o;
                List<MallAdBean.ResultBean> result3 = mallAdBean2.getResult();
                Glide.with(getContext()).load(result3.get(0).getPicUri()).into(mRounded);
                mRounded.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String route = result3.get(0).getRoute();
                        String target = result3.get(0).getTarget();
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
                            intent.putExtra("title", result3.get(0).getTitle());
                            startActivity(intent);
                        }
                    }
                });

                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
