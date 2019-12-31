package com.tiancaijiazu.app.activitys.shopping_activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.bean.CouponBean;
import com.tiancaijiazu.app.activitys.coupons.CouponsActivity;
import com.tiancaijiazu.app.activitys.shopping_activity.adapter.PoAdapter;
import com.tiancaijiazu.app.base.activity.BaseActivity;
import com.tiancaijiazu.app.beans.AtOnceBean;
import com.tiancaijiazu.app.beans.CourseBean;
import com.tiancaijiazu.app.beans.CourseToBuyBean;
import com.tiancaijiazu.app.beans.JsonBean;
import com.tiancaijiazu.app.beans.OrderFormBean;
import com.tiancaijiazu.app.beans.Shopping_carBean;
import com.tiancaijiazu.app.beans.SiteBean;
import com.tiancaijiazu.app.beans.StrBean;
import com.tiancaijiazu.app.beans.ValidCouponBeans;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.ScreenStatusUtil;
import com.tiancaijiazu.app.mvp.IView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class POActivity extends BaseActivity<IView, Presenter<IView>> implements IView {

    @BindView(R.id.rlv)
    RecyclerView mRlv;
    @BindView(R.id.ti_jiao)
    TextView mTiJiao;  @BindView(R.id.a)
    TextView a;
    @BindView(R.id.sum)
    TextView mSum;
    @BindView(R.id.fu_hao)
    TextView mFuHao;
    @BindView(R.id.iv_finis)
    ImageView mIvFinis;
    private PoAdapter mPoAdapter;
    private int page = 1;
    private double mSum1;
    private ArrayList<Shopping_carBean.ResultBean> mBeans;
    private AtOnceBean mAtOnceBean;
    private String mBiao;
    private CourseBean mCourseBean;
    private DifferentiateEnum mOrderform;
    private String shop_counpons="shop_counpons";
    private CouponBean.ResultBean mResultBean;
    private int mResult1;

    @Override
    protected void initEventAndData() {
        ScreenStatusUtil.setFillDip(this);
        mPresenter.getDataP(page,DifferentiateEnum.SITELIST);
        initRlv();
    }


    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_po;
    }

    public static double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal ne = new BigDecimal("1");
        return b.divide(ne, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    /**
     * 1是从购物车过来
     * 2是直接立即购买
     * course是学院购买带物品过来
     */
    private void initRlv() {
        Intent intent = getIntent();
        mBiao = intent.getStringExtra("biao");
        if(mBiao.equals("1")){
            mBeans = (ArrayList<Shopping_carBean.ResultBean>) intent.getSerializableExtra("data");
            mPresenter.getDataP(3,DifferentiateEnum.VALIDCOUPONS);
            for (int i = 0; i < mBeans.size(); i++) {
                mSum1 += mBeans.get(i).getOldPrice() * mBeans.get(i).getQuantity();
            }
            double round = round(mSum1, 2);
            mSum.setText(round+"");

        }else if(mBiao.equals("2")){
            mPresenter.getDataP(3,DifferentiateEnum.VALIDCOUPONS);
            mAtOnceBean = (AtOnceBean) intent.getSerializableExtra("data");
            mSum1 = mAtOnceBean.getSum()* mAtOnceBean.getPrice();
            double round = round(mSum1, 2);
            mSum.setText(round+"");
        }else if(mBiao.equals("course")){
            String title = intent.getStringExtra("title");
            String picUri = intent.getStringExtra("picUri");
            String courseId = intent.getStringExtra("courseId");
            float promoPrice = intent.getFloatExtra("promoPrice", 0);
            double round = round(promoPrice, 2);
            mSum.setText(round+"");
            mCourseBean = new CourseBean(title, picUri, courseId, promoPrice);
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRlv.setLayoutManager(linearLayoutManager);
        List<SiteBean.ResultBean> resultBeans = new ArrayList<>();
        mPoAdapter = new PoAdapter(this, resultBeans, mBeans, mBiao,mAtOnceBean,mCourseBean);
        mRlv.setAdapter(mPoAdapter);

        mPoAdapter.setOnClickLisiterCoupon(new PoAdapter.onClickLisiterCoupon() {
            @Override
            public void onClickerCoupon(View view, int position, TextView coupons) {
                Intent intent = new Intent(POActivity.this, CouponsActivity.class);
                if(!"course".equals(mBiao)){
                    intent.putExtra("biao",shop_counpons);
                    String s = mSum.getText().toString();
                    intent.putExtra("money",s);
                }
                startActivityForResult(intent,14);
            }
        });

        mPoAdapter.onSetClickLisiter(new PoAdapter.setOnClickLisiter() {
            @Override
            public void onClickDiZhi(View view, int position) {
                Intent intent = new Intent(POActivity.this, DiZhiActivity.class);
                startActivityForResult(intent,150);
            }
        });
        mRlv.scrollToPosition(0);
    }

    @OnClick({R.id.iv_finis, R.id.ti_jiao})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_finis:
                finish();
                break;
            case R.id.ti_jiao:
                if(mBiao.equals("1")){
                    ArrayList<JsonBean> jsonBeans = new ArrayList<>();
                    for (int i = 0; i < mPoAdapter.mData.size(); i++) {
                        jsonBeans.add(new JsonBean(mPoAdapter.mData.get(i).getSkuId(),mPoAdapter.mData.get(i).getStockId(),mPoAdapter.mData.get(i).getQuantity()));
                    }
                    Gson gson = new Gson();
                    String s = gson.toJson(jsonBeans);
                    Log.i("yx123", "onViewClicked: "+s);
                    if(mPoAdapter.list.size()!=0){
                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("consigneeName",mPoAdapter.list.get(0).getName());
                        hashMap.put("consigneeMobile",mPoAdapter.list.get(0).getMobile());
                        hashMap.put("consigneeAddress",mPoAdapter.list.get(0).getArea()+mPoAdapter.list.get(0).getAddress());
                        if (mResultBean!=null) {
                            hashMap.put("courseId", mResultBean.getCouponId() + "");
                        }
                        hashMap.put("items",s);
                        mPresenter.getDataP(hashMap, DifferentiateEnum.ORDERFORM);
                    }else {
                        Toast.makeText(mActivity, "请填写地址", Toast.LENGTH_SHORT).show();
                    }
                }else if(mBiao.equals("2")){
                    ArrayList<JsonBean> jsonBeans = new ArrayList<>();
                    jsonBeans.add(new JsonBean(mAtOnceBean.getSkuId(),mAtOnceBean.getStockId(),mAtOnceBean.getSum()));
                    Gson gson = new Gson();
                    String s = gson.toJson(jsonBeans);
                    Log.i("yx123", "onViewClicked: "+s);
                    if(mPoAdapter.list.size()!=0){
                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("consigneeName",mPoAdapter.list.get(0).getName());
                        hashMap.put("consigneeMobile",mPoAdapter.list.get(0).getMobile());
                        hashMap.put("consigneeAddress",mPoAdapter.list.get(0).getArea()+mPoAdapter.list.get(0).getAddress());
                        if (mResultBean!=null) {
                            hashMap.put("courseId", mResultBean.getCouponId() + "");
                        }
                        hashMap.put("items",s);
                        mPresenter.getDataP(hashMap, DifferentiateEnum.ORDERFORM);
                    }else {
                        Toast.makeText(mActivity, "请填写地址", Toast.LENGTH_SHORT).show();
                    }
                }else if(mBiao.equals("course")){
                    String courseId = mPoAdapter.mCourseBean.getCourseId();
                    if(mPoAdapter.list.size()!=0){
                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("consigneeName",mPoAdapter.list.get(0).getName());
                        hashMap.put("consigneeMobile",mPoAdapter.list.get(0).getMobile());
                        hashMap.put("consigneeAddress",mPoAdapter.list.get(0).getArea()+mPoAdapter.list.get(0).getAddress());
                        hashMap.put("courseId",courseId);
                        Log.i("yx852", "onViewClicked: "+courseId);
                        mPresenter.getDataP(hashMap,DifferentiateEnum.COURSETOBUYCOMM);
                    }else {
                        Toast.makeText(mActivity, "请填写地址", Toast.LENGTH_SHORT).show();
                    }

                }
                break;
        }
    }

    boolean isFirst=true;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==150&&resultCode==156){
            mPresenter.getDataP(page,DifferentiateEnum.SITELIST);
        }
        if(requestCode==14&&resultCode==15){
            if(!"course".equals(mBiao)){
                if (isFirst) {
                    mResultBean = (CouponBean.ResultBean) data.getSerializableExtra("data");
                    mPoAdapter.addUpTv(mResultBean);
                    String s = mSum.getText().toString();
                    double v = Double.parseDouble(s);
                    double couponFee = mResultBean.getCouponFee();
                    double zong = v - couponFee;
                    double round = round(zong, 2);
                    mSum.setText(round + "");
                    isFirst=false;
                }
            }
        }
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum) {
            case SITELIST:
                SiteBean bean = (SiteBean) o;
                List<SiteBean.ResultBean> result = bean.getResult();
                mPoAdapter.addData(result);
                break;
            case ORDERFORM:
                String order = (String) o;
                Log.i("yx654", "show: "+order);
                Gson gson = new Gson();
                StrBean strBean = gson.fromJson(order, StrBean.class);
                String code = strBean.getCode();
                Log.i("yx654", "show: "+code);
                if("0".equals(code)){
                    OrderFormBean orderFormBean = gson.fromJson(order, OrderFormBean.class);
                    if(orderFormBean.getCode().equals("0")){
                        Intent intent = new Intent(this, PayActivity.class);
                        OrderFormBean.ResultBean result1 = orderFormBean.getResult();
                        intent.putExtra("data",result1);
                        if(mResultBean!=null){
                            if(mResultBean.getCouponFee() != 0){
                                intent.putExtra("juan",mResultBean);
                            }
                        }
                        intent.putExtra("have",mResult1);
                        intent.putExtra("biao","shopping");
                        startActivity(intent);
                        finish();
                    }else {
                        Toast.makeText(mActivity, "提交失败", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    String str = (String) strBean.getResult();
                    Toast.makeText(mActivity, str, Toast.LENGTH_SHORT).show();
                }
                break;
            case COURSETOBUYCOMM:
                String course = (String) o;
                Gson gson1 = new Gson();
                StrBean strBean1 = gson1.fromJson(course, StrBean.class);
                String code1 = strBean1.getCode();
                if("0".equals(code1)){
                    CourseToBuyBean courseToBuyBean = gson1.fromJson(course, CourseToBuyBean.class);
                    CourseToBuyBean.ResultBean result2 = courseToBuyBean.getResult();
                    Intent intent = new Intent(POActivity.this, PayActivity.class);
                    intent.putExtra("data",result2);
                    if(mResultBean!=null){
                        if(mResultBean.getCouponFee() != 0){
                            intent.putExtra("juan",mResultBean);
                        }
                    }
                    intent.putExtra("have",mResult1);
                    intent.putExtra("biao","course");
                    startActivity(intent);
                    finish();
                }else {
                    String str = (String) strBean1.getMsg();
                    Toast.makeText(mActivity,str, Toast.LENGTH_SHORT).show();
                }

                break;
            case VALIDCOUPONS:
                ValidCouponBeans validCouponBeans= (ValidCouponBeans) o;
                mResult1 = validCouponBeans.getResult();
                mPoAdapter.setData(mResult1);
                break;
        }
    }

    @Override
    protected Presenter<IView> createPresenter(){
        return new Presenter<>();
    }
}
