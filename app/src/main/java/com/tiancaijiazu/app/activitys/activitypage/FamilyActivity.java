package com.tiancaijiazu.app.activitys.activitypage;


import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.shopping_activity.OwnBrandActivity;
import com.tiancaijiazu.app.activitys.shopping_activity.ShoppingCarActivity;
import com.tiancaijiazu.app.base.activity.SimpleActivity;
import com.tiancaijiazu.app.utils.ScreenStatusUtil;
import com.tiancaijiazu.app.utils.TimeUtil;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FamilyActivity extends SimpleActivity {

    @BindView(R.id.banner)
    Banner mBanner; @BindView(R.id.a)
    TextView a;
    @BindView(R.id.ll_index_container)
    LinearLayout mLlIndexContainer;
    @BindView(R.id.rlv)
    RecyclerView mRlv;
    @BindView(R.id.recylerView)
    RecyclerView mRecylerView;
    @BindView(R.id.gouwu_car)
    ImageView mGouwuCar;
    @BindView(R.id.relative)
    RelativeLayout mRelative;
    @BindView(R.id.line_own)
    LinearLayout mLineOwn;
    private ArrayList<Integer> mList;

    @Override
    protected void initEventAndData() {
        ScreenStatusUtil.setFillDip(this);
  /*      mList = new ArrayList<>();
        mList.add(R.mipmap.pic1);
        mList.add(R.mipmap.pic2);
        mList.add(R.mipmap.pic3);
        mList.add(R.mipmap.pic4);
        mBanner.setBannerStyle(BannerConfig.NOT_INDICATOR);
        mBanner.setImages(mList).setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                Glide.with(context).load(path).into(imageView);
            }
        }).start();*/

        //initRlv();
        //initRecylerView();
    }

  /*  private void initRecylerView() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        mRecylerView.setLayoutManager(layoutManager);
        RlvAdapter_own_brand rlvAdapterOwnBrand = new RlvAdapter_own_brand();
        mRecylerView.setAdapter(rlvAdapterOwnBrand);
    }

    private void initRlv() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        mRlv.setLayoutManager(layoutManager);
        RlvAdapter_hot_sale rlvAdapterHotSale = new RlvAdapter_hot_sale();
        mRlv.setAdapter(rlvAdapterHotSale);
    }*/

    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_family;
    }

    @OnClick({R.id.line_own, R.id.gouwu_car,R.id.iv_finis})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.line_own:
                if (TimeUtil.isInvalidClick(view, 300))
                    return;
                intoActivity(OwnBrandActivity.class);
                break;
            case R.id.gouwu_car:
                if (TimeUtil.isInvalidClick(view, 300))
                    return;
                intoActivity(ShoppingCarActivity.class);
                break;
            case R.id.iv_finis:
                finish();
                break;
        }
    }

    public void intoActivity(Class mClass) {
        Intent intent = new Intent(this, mClass);
        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
