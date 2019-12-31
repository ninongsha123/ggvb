package com.tiancaijiazu.app.activitys.shopping_activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.activitypage.collegeactivitys.ClassListActivity;
import com.tiancaijiazu.app.activitys.activitypage.WebActivity;
import com.tiancaijiazu.app.activitys.shopping_activity.fragments.GoodsTypeFragment;
import com.tiancaijiazu.app.activitys.views.ComFragmentAdapter;
import com.tiancaijiazu.app.adapters.RlvAdapter_own;
import com.tiancaijiazu.app.base.activity.BaseActivity;
import com.tiancaijiazu.app.beans.MallAdBean;
import com.tiancaijiazu.app.beans.ShopBean;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.ScreenStatusUtil;
import com.tiancaijiazu.app.utils.status.LoadingLayout;
import com.tiancaijiazu.app.utils.tabmin.ColorFlipPagerTitleViewYx;
import com.tiancaijiazu.app.utils.tabmin.SimplePagerTitleViewYx;
import com.tiancaijiazu.app.mvp.IView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class OwnBrandActivity extends BaseActivity<IView, Presenter<IView>> implements IView {
    @BindView(R.id.iv_finis)
    ImageView mIvFinis;
    @BindView(R.id.a)
    TextView a;
    @BindView(R.id.recylerView)
    RecyclerView mRecylerView;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    @BindView(R.id.magic_indicator)
    MagicIndicator mMagicIndicator;
    @BindView(R.id.share)
    ImageView mShare;
    @BindView(R.id.banner)
    Banner mBanner;
    @BindView(R.id.loadingLayout)
    LoadingLayout loadingLayout;
    private List<ShopBean.ResultBean> mResult;
    private RlvAdapter_own mRlvAdapterOwn;

    @Override
    public void showError(String error) {

    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum) {
            case SHOPBEAN:
                ShopBean shopBean = (ShopBean) o;
                mResult = shopBean.getResult();
                ArrayList<Fragment> fragments = new ArrayList<>();
                for (int i = 0; i < mResult.size(); i++) {
                    fragments.add(GoodsTypeFragment.getInstance(mResult.get(i).getName(), mResult.get(i).getCatalogId() + ""));
                }
                mViewpager.setAdapter(new ComFragmentAdapter(getSupportFragmentManager(), fragments));
                mViewpager.setOffscreenPageLimit(mResult.size());
                initTab();
                break;
            case MALLADTHREE:
                MallAdBean mallAdBean = (MallAdBean) o;
                List<MallAdBean.ResultBean> result = mallAdBean.getResult();
                mRlvAdapterOwn.addData(result);
                break;
            case MALLADSIX:
                MallAdBean mallAdBean1 = (MallAdBean) o;
                List<MallAdBean.ResultBean> result1 = mallAdBean1.getResult();
                ArrayList<String> bannars = new ArrayList<>();
                for (int i = 0; i < result1.size(); i++) {
                    bannars.add(result1.get(i).getPicUri());
                }
                mBanner.setBannerStyle(BannerConfig.NOT_INDICATOR);
                mBanner.setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {
                        String route = result1.get(position).getRoute();
                        String target = result1.get(position).getTarget();
                        if ("STUDY_COURSE".equals(route)) {
                            Intent intent = new Intent(OwnBrandActivity.this, ClassListActivity.class);
                            intent.putExtra("target", target);
                            startActivity(intent);
                        } else if ("MALL_SKU".equals(route)) {
                            Intent intent = new Intent(OwnBrandActivity.this, ShopActivity.class);
                            intent.putExtra("target", target);
                            startActivity(intent);
                        } else if ("WEB".equals(route)) {
                            Intent intent = new Intent(OwnBrandActivity.this, WebActivity.class);
                            intent.putExtra("target", target);
                            intent.putExtra("title", result1.get(position).getTitle());
                            startActivity(intent);
                        }
                    }
                }).setImages(bannars).setImageLoader(new ImageLoader() {
                    @Override
                    public void displayImage(Context context, Object path, ImageView imageView) {
                        Glide.with(context).load(path).into(imageView);
                    }
                }).start();
                break;
        }
    }

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }

    @Override
    protected void initEventAndData() {
        ScreenStatusUtil.setFillDip(this);
        initRecyclerView();
        Intent intent = getIntent();
        String catalogId = intent.getStringExtra("catalogId");
        String title = intent.getStringExtra("title");
        a.setText(title);
        mPresenter.getDataP1(catalogId, DifferentiateEnum.SHOPBEAN,loadingLayout);
        mPresenter.getDataP1("", DifferentiateEnum.MALLADTHREE,loadingLayout);
        mPresenter.getDataP1("", DifferentiateEnum.MALLADSIX,loadingLayout);
        loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
            @Override
            public void onReload(View v) {
                mPresenter.getDataP1(catalogId, DifferentiateEnum.SHOPBEAN,loadingLayout);
                mPresenter.getDataP1("", DifferentiateEnum.MALLADTHREE,loadingLayout);
                mPresenter.getDataP1("", DifferentiateEnum.MALLADSIX,loadingLayout);
            }
        });
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecylerView.setLayoutManager(linearLayoutManager);
        List<MallAdBean.ResultBean> resultBeans = new ArrayList<>();
        mRlvAdapterOwn = new RlvAdapter_own(resultBeans, this);
        mRecylerView.setAdapter(mRlvAdapterOwn);

        mRlvAdapterOwn.setOnClickLisiter(new RlvAdapter_own.onClickLisiter() {
            @Override
            public void onClicker(View view, int position, List<MallAdBean.ResultBean> mData) {
                String route = mData.get(position).getRoute();
                String target = mData.get(position).getTarget();
                if ("STUDY_COURSE".equals(route)) {
                    Intent intent = new Intent(OwnBrandActivity.this, ClassListActivity.class);
                    intent.putExtra("target", target);
                    startActivity(intent);
                } else if ("MALL_SKU".equals(route)) {
                    Intent intent = new Intent(OwnBrandActivity.this, ShopActivity.class);
                    intent.putExtra("target", target);
                    startActivity(intent);
                } else if ("WEB".equals(route)) {
                    Intent intent = new Intent(OwnBrandActivity.this, WebActivity.class);
                    intent.putExtra("target", target);
                    intent.putExtra("title", mData.get(position).getTitle());
                    startActivity(intent);
                }
            }
        });
    }

    private void initTab() {
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setScrollPivotX(0.65f);
        commonNavigator.setAdjustMode(false);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mResult == null ? 0 : mResult.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleViewYx simplePagerTitleView = new ColorFlipPagerTitleViewYx(context);
                simplePagerTitleView.setText(mResult.get(index).getName());
                simplePagerTitleView.setNormalColor(ContextCompat.getColor(OwnBrandActivity.this, R.color.colorTextSubhead));
                simplePagerTitleView.setSelectedColor(ContextCompat.getColor(OwnBrandActivity.this, R.color.colorTextTitleHome));
                simplePagerTitleView.setNormalSize(15);
                simplePagerTitleView.setSelectedSize(17);
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewpager.setCurrentItem(index, false);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setLineHeight(UIUtil.dip2px(context, 2));
                indicator.setLineWidth(UIUtil.dip2px(context, 25));
                indicator.setRoundRadius(UIUtil.dip2px(context, 1));
                indicator.setStartInterpolator(new AccelerateInterpolator());
                indicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
                indicator.setColors(ContextCompat.getColor(OwnBrandActivity.this, R.color.colorZhu));
                return indicator;
            }
        });
        mMagicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mMagicIndicator, mViewpager);
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_own_brand;
    }


    @OnClick({R.id.iv_finis, R.id.share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_finis:
                finish();
                break;
            case R.id.share:
                break;
        }
    }


    /*@BindView(R.id.rlv)
    RecyclerView mRlv;
    @BindView(R.id.iv_finis)
    ImageView mIvFinis;
    private RlvAdapter_own rlvAdapterOwn;

    @Override
    protected void initEventAndData() {
        initSett();
        initRlv();
        Intent intent = getIntent();
        int catalogId = intent.getIntExtra("catalogId", 0);
        Log.i("yx", "initEventAndData: "+catalogId);
        mPresenter.getDataP(catalogId, DifferentiateEnum.SHANGPINLIST);
    }

    private void initRlv() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        mRlv.setLayoutManager(layoutManager);
        List<ShangpinBean.ResultBean.ItemListBean> itemListBeans = new ArrayList<>();
        rlvAdapterOwn = new RlvAdapter_own(itemListBeans,this);
        mRlv.setAdapter(rlvAdapterOwn);

        rlvAdapterOwn.onSetClickLisiter(new RlvAdapter_own.setClickLisiter() {
            @Override
            public void onClickComm(View view, int position,List<ShangpinBean.ResultBean.ItemListBean> mData) {
                if (TimeUtil.isInvalidClick(view, 300))
                    return;
                Intent intent = new Intent(OwnBrandActivity.this, ShopActivity.class);
                intent.putExtra("id",mData.get(position).getSkuId()+"");
                startActivity(intent);
            }
        });
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_own_brand;
    }

    //设置状态栏与状态栏字体颜色
    private void initSett() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR//设置显示为白色背景，黑色字体
            );
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @OnClick(R.id.iv_finis)
    public void onViewClicked() {
        finish();
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
                rlvAdapterOwn.addData(itemList);
                break;
        }
    }

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }*/
}
