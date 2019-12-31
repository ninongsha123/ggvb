package com.tiancaijiazu.app.activitys.shopping_activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.shopping_activity.adapter.RlvAdapter_DiZhi;
import com.tiancaijiazu.app.base.activity.BaseActivity;
import com.tiancaijiazu.app.beans.DeleSiteBean;
import com.tiancaijiazu.app.beans.SiteBean;
import com.tiancaijiazu.app.beans.TacitlysiteBean;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.status.LoadingLayout;
import com.tiancaijiazu.app.mvp.IView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class DiZhiActivity extends BaseActivity<IView, Presenter<IView>> implements IView {


    @BindView(R.id.iv_finis)
    ImageView mIvFinis;
    @BindView(R.id.a)
    TextView a;
    @BindView(R.id.rlv)
    RecyclerView mRlv;
    @BindView(R.id.loadingLayout)
    LoadingLayout loadingLayout;
    private RlvAdapter_DiZhi mRlvAdapterDiZhi;
    private int page = 1;
    private Intent mIntent;

    @Override
    protected void initEventAndData() {
        initSett();
        mIntent = getIntent();
        initRlv();
    }

    private void initRlv() {
        ArrayList<SiteBean.ResultBean> resultBeans = new ArrayList<>();
        mPresenter.getDataP1(page, DifferentiateEnum.SITELIST,loadingLayout);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRlv.setLayoutManager(linearLayoutManager);
        mRlvAdapterDiZhi = new RlvAdapter_DiZhi(resultBeans);
        mRlv.setAdapter(mRlvAdapterDiZhi);

        mRlvAdapterDiZhi.onSetClickLisiterLine(new RlvAdapter_DiZhi.setOnClickLisiter() {
            @Override
            public void onClickLine(View view, int position) {
                Intent intent = new Intent(DiZhiActivity.this, LocationActivity.class);
                intent.putExtra("biao", "0");
                startActivityForResult(intent, 11);
            }
        });

        mRlvAdapterDiZhi.setOnClickLisiterDele(new RlvAdapter_DiZhi.onClickLisiterDele() {
            @Override
            public void onClickerDele(View view, int position, ArrayList<SiteBean.ResultBean> mData) {
                long consigneeId = mData.get(position).getConsigneeId();
                mPresenter.getDataP1(consigneeId + "", DifferentiateEnum.DELESITE,loadingLayout);
            }
        });

        mRlvAdapterDiZhi.setOnClickLisiterChclikbox(new RlvAdapter_DiZhi.onClickLisiterChclikbox() {
            @Override
            public void onClickerChclikbox(View view, int position, ArrayList<SiteBean.ResultBean> mData) {
                long consigneeId = mData.get(position).getConsigneeId();
                mPresenter.getDataP1(consigneeId + "", DifferentiateEnum.TACITLYSITE,loadingLayout);
            }
        });

        mRlvAdapterDiZhi.setOnClickLisiterBian(new RlvAdapter_DiZhi.onClickLisiterBian() {
            @Override
            public void onClickerBian(View view, int position, ArrayList<SiteBean.ResultBean> mData) {
                String name = mData.get(position).getName();
                String area = mData.get(position).getArea();
                String address = mData.get(position).getAddress();
                String mobile = mData.get(position).getMobile();
                long consigneeId = mData.get(position).getConsigneeId();
                int isDefault = mData.get(position).getIsDefault();
                Intent intent = new Intent(DiZhiActivity.this, LocationActivity.class);
                intent.putExtra("biao", "1");
                intent.putExtra("name", name);
                intent.putExtra("area", area);
                intent.putExtra("address", address);
                intent.putExtra("mobile", mobile);
                intent.putExtra("consigneeId", consigneeId + "");
                intent.putExtra("isDefault", isDefault + "");
                startActivityForResult(intent, 11);
            }
        });
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 11 && resultCode == 12) {
            page = 1;
            mPresenter.getDataP1(page, DifferentiateEnum.SITELIST,loadingLayout);

        }
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_di_zhi;
    }

    @OnClick(R.id.iv_finis)
    public void onViewClicked() {
        setResult(156, mIntent);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            setResult(156, mIntent);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum) {
            case SITELIST:
                SiteBean siteBean = (SiteBean) o;
                List<SiteBean.ResultBean> result = siteBean.getResult();
                if(result.size()!=0){
                    mRlvAdapterDiZhi.addData(result);
                    if(result.size()==1){
                        long consigneeId = result.get(0).getConsigneeId();
                        mPresenter.getDataP1(consigneeId + "", DifferentiateEnum.TACITLYSITE,loadingLayout);
                    }
                }
                break;
            case DELESITE:
                DeleSiteBean deleSiteBean = (DeleSiteBean) o;
                String result1 = deleSiteBean.getResult();
                Toast.makeText(mActivity, "" + result1, Toast.LENGTH_SHORT).show();
                break;
            case TACITLYSITE:
                TacitlysiteBean tacitlysiteBean = (TacitlysiteBean) o;
                String result2 = tacitlysiteBean.getResult();
                Toast.makeText(mActivity, "" + result2, Toast.LENGTH_SHORT).show();
                if(mRlvAdapterDiZhi.mData.size()==1){
                    mRlvAdapterDiZhi.mData.get(0).setIsDefault(1);
                    mRlvAdapterDiZhi.notifyDataSetChanged();
                }
                //mPresenter.getDataP(page,DifferentiateEnum.SITELIST);
                break;
        }
    }

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }
}
