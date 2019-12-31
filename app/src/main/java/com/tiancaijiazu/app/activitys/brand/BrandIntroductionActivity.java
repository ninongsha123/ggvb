package com.tiancaijiazu.app.activitys.brand;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.brand.adapters.RlvAdapter_brand_certificate;
import com.tiancaijiazu.app.activitys.brand.adapters.RlvAdapter_brand_picture;
import com.tiancaijiazu.app.activitys.shopping_activity.LookPhotoActivity;
import com.tiancaijiazu.app.app.App;
import com.tiancaijiazu.app.base.activity.BaseActivity;
import com.tiancaijiazu.app.beans.GardenDetailsByBean;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.fragments.views.NiceImageView;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.FastBlurUtil;
import com.tiancaijiazu.app.utils.ScreenStatusUtil;
import com.tiancaijiazu.app.mvp.IView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *
 * 线下课堂-条目点击进入-底部品牌介绍点击进入
 */

public class BrandIntroductionActivity extends BaseActivity<IView, Presenter<IView>> implements IView {


    @BindView(R.id.iv_finis)
    ImageView mIvFinis;
    @BindView(R.id.relative)
    RelativeLayout mRelative;
    @BindView(R.id.nice_iv)
    NiceImageView mNiceIv;
    @BindView(R.id.recylerView)
    RecyclerView mRecylerView;
    @BindView(R.id.recyler)
    RecyclerView mRecyler;
    @BindView(R.id.summary)
    TextView mSummary;
    @BindView(R.id.tupian)
    TextView tupian;
    @BindView(R.id.rongyu)
    TextView rongyu;
    @BindView(R.id.a)
    TextView a;
    @BindView(R.id.iv_back)
    ImageView mIvBack;

    @Override
    protected void initEventAndData() {
        ScreenStatusUtil.setFillDip(this);
        ScreenStatusUtil.setNotStatus(this, mRelative);
        Intent intent = getIntent();
        GardenDetailsByBean.ResultBean data = (GardenDetailsByBean.ResultBean) intent.getSerializableExtra("data");
        Glide.with(this).load(data.getLogoUri()).into(mNiceIv);
        new Thread(new Runnable() {
            @Override
            public void run() {
                //下面的这个方法必须在子线程中执行
                final Bitmap blurBitmap2 = FastBlurUtil.GetUrlBitmap(data.getLogoUri(), 8);
                //刷新ui必须在主线程中执行
                App.runOnUIThread(new Runnable() {
                    @Override
                    public void run() {
                        mIvBack.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        mIvBack.setImageBitmap(blurBitmap2);
                    }
                });
            }
        }).start();
        mSummary.setText(data.getSummary());
        String pics = data.getPics();
        String[] split = pics.split("[|]");
        String certificate = data.getCertificate();
        String[] split1 = certificate.split("[|]");
        initRecyclerView(split);
        initRecycler(split1);

    }

    private void initRecycler(String[] split1) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyler.setLayoutManager(linearLayoutManager);
        RlvAdapter_brand_certificate rlvAdapterBrandCertificate = new RlvAdapter_brand_certificate(split1, BrandIntroductionActivity.this);
        mRecyler.setAdapter(rlvAdapterBrandCertificate);

        rlvAdapterBrandCertificate.setOnClickLisiter(new RlvAdapter_brand_certificate.onClickLisiter() {
            @Override
            public void onClicker(View view, int position, String[] mData) {
                List<String> data = Arrays.asList(mData);
                ArrayList<String> mStringArrayList = new ArrayList<>();
                mStringArrayList.addAll(data);
                Intent intent = new Intent(BrandIntroductionActivity.this, LookPhotoActivity.class);
                intent.putStringArrayListExtra("images", mStringArrayList);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
    }

    private void initRecyclerView(String[] split) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecylerView.setLayoutManager(linearLayoutManager);
        RlvAdapter_brand_picture rlvAdapterBrandPicture = new RlvAdapter_brand_picture(split, BrandIntroductionActivity.this);
        mRecylerView.setAdapter(rlvAdapterBrandPicture);

        rlvAdapterBrandPicture.setOnClickLisiter(new RlvAdapter_brand_picture.onClickLisiter() {
            @Override
            public void onClicker(View view, int position, String[] mData) {
                List<String> data = Arrays.asList(mData);
                ArrayList<String> mStringArrayList = new ArrayList<>();
                mStringArrayList.addAll(data);
                Intent intent = new Intent(BrandIntroductionActivity.this, LookPhotoActivity.class);
                intent.putStringArrayListExtra("images", mStringArrayList);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_brand_introduction;
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {

    }

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }


    @OnClick({R.id.iv_finis})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_finis:
                finish();
                break;
        }
    }

}
