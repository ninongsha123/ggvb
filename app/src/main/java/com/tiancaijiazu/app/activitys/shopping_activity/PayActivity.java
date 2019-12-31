package com.tiancaijiazu.app.activitys.shopping_activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.activitypage.collegeactivitys.ClassListActivity;
import com.tiancaijiazu.app.activitys.bean.CouponBean;
import com.tiancaijiazu.app.activitys.coupons.CouponsActivity;
import com.tiancaijiazu.app.activitys.early.EarlyActivity;
import com.tiancaijiazu.app.alipay.AuthResult;
import com.tiancaijiazu.app.alipay.PayResult;
import com.tiancaijiazu.app.base.activity.BaseActivity;
import com.tiancaijiazu.app.beans.AlipayBean;
import com.tiancaijiazu.app.beans.CourseToBuyBean;
import com.tiancaijiazu.app.beans.OrderFormBean;
import com.tiancaijiazu.app.beans.PayBean;
import com.tiancaijiazu.app.beans.StudyCardOrderCreateBenas;
import com.tiancaijiazu.app.beans.ValidCouponBeans;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.DestroyActivityUtil;
import com.tiancaijiazu.app.utils.PreUtils;
import com.tiancaijiazu.app.utils.ScreenStatusUtil;
import com.tiancaijiazu.app.mvp.IView;
import com.tiancaijiazu.app.utils.SpUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.photoview.log.LoggerDefault;

import static com.tiancaijiazu.app.app.App.api;

//支付界面

public class PayActivity extends BaseActivity<IView, Presenter<IView>> implements IView {

    @BindView(R.id.iv_finis)
    ImageView mIvFinis;
    @BindView(R.id.rela)
    RelativeLayout mRela;
    @BindView(R.id.line)
    LinearLayout mLine;
    @BindView(R.id.line4)
    LinearLayout line4;
    @BindView(R.id.select_counpons)
    TextView select_counpons;
    @BindView(R.id.line1)
    LinearLayout mLine1;
    @BindView(R.id.line2)
    LinearLayout mLine2;
    @BindView(R.id.confirm)
    Button mConfirm;
    @BindView(R.id.cb_zhifubao)
    CheckBox mCbZhifubao;
    @BindView(R.id.cb_weixin)
    CheckBox mCbWeixin;
    @BindView(R.id.sum)
    TextView mSum; @BindView(R.id.a)
    TextView a;
    private Intent mIntent;
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
    @SuppressLint("HandlerLeak")
    private final Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    Log.i("yx987", "handleMessage: " + resultStatus);
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(mActivity, "成功", Toast.LENGTH_SHORT).show();
                        if ("course".equals(mBiao)) {
                            Intent intent = new Intent(PayActivity.this, ClassListActivity.class);
                            PreUtils.putString("biao", "ok");
                            startActivity(intent);
                            finish();
                        }
                        if ("vip".equals(mBiao)) {
                            setResult(147, mIntent);
                            finish();
                        }
                        if ("cardStudy".equals(mBiao)) {
                            Intent intent = new Intent(PayActivity.this, EarlyActivity.class);
                           /* ActivityManageUtils.getInstance().finishActivity(new EarlyActivity());
                            ActivityManageUtils.getInstance().finishActivity(new TheFormalCardActivity());*/
                            PreUtils.putString("cards","ok");
                            startActivity(intent);
                            finish();
                        }
                        if("cardRenew".equals(mBiao)){
                            PreUtils.putString("finis","ok");
                            finish();
                        }
                        if ("shopping".equals(mBiao)) {
                            String orderId = PreUtils.getString("orderId", "");
                            Intent intent1 = new Intent(PayActivity.this, LineItemActivity.class);
                            intent1.putExtra("biao","3");
                            intent1.putExtra("orderId",orderId+"");
                            startActivity(intent1);
                            finish();
                        }
                        // showAlert(PayDemoActivity.this, getString(R.string.pay_success) + payResult);
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        // showAlert(PayDemoActivity.this, getString(R.string.pay_failed) + payResult);
                        if ("shopping".equals(mBiao)) {
                            String orderId = PreUtils.getString("orderId", "");
                            Intent intent1 = new Intent(PayActivity.this, LineItemActivity.class);
                            intent1.putExtra("biao", "2");
                            intent1.putExtra("orderId", orderId + "");
                            intent1.putExtra("juan", mResultBean1.getCouponFee());
                            startActivity(intent1);
                            finish();
                        }
                        Toast.makeText(mActivity, "取消支付", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();
                    Log.i("yx987", "handleMessage: " + resultStatus);
                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        //showAlert(PayDemoActivity.this, getString(R.string.auth_success) + authResult);
                    } else {
                        // 其他状态值则为授权失败
                        //showAlert(PayDemoActivity.this, getString(R.string.auth_failed) + authResult);
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };
    private OrderFormBean.ResultBean mResult;
    private String mBiao;
    private CourseToBuyBean.ResultBean mResultBean;
    private int mVipLevel;
    private String mOrderId;
    private StudyCardOrderCreateBenas.ResultBean data;
    private String birthday;
    private String noaddress="shop_counpons";
    private double mTotalFee;
    private CouponBean.ResultBean mResultBean1;
    private float mTotalFee1;
    private double mPromoPrice;
    private String birthdaycard;
    private StudyCardOrderCreateBenas.ResultBean data1;
    private double promoPrice1;
    private double promoPrice;
    private double mTotalFee2;
    private double mZong;
    private double mJuan;

    /**
     * course是从学院不带物品支付结算
     * shopping是从商场提交完订单过来
     */
    @Override
    protected void initEventAndData() {
        ScreenStatusUtil.setFillDip(this);
        requestPermission();
        initCheckBox();
        mIntent = getIntent();
        mBiao = mIntent.getStringExtra("biao");
        if ("course".equals(mBiao)) {
            mResultBean = (CourseToBuyBean.ResultBean) mIntent.getSerializableExtra("data");
            mTotalFee1 = mResultBean.getTotalFee();
            mSum.setText(mTotalFee1 + "");
        } else if ("shopping".equals(mBiao)) {
            mResult = (OrderFormBean.ResultBean) mIntent.getSerializableExtra("data");
            mResultBean1 = (CouponBean.ResultBean) mIntent.getSerializableExtra("juan");
            PreUtils.putString("orderId", mResult.getOrderId() + "");
            int have = mIntent.getIntExtra("have", 0);
            mTotalFee = mResult.getTotalFee();
            if(mResultBean1 !=null){
                double couponFee = mResultBean1.getCouponFee();
                double zong = mTotalFee - couponFee;
                double round = round(zong, 2);
                double round1 = round(couponFee, 2);
                mSum.setText(round + "");
                select_counpons.setText("-￥"+round1);
            }else {
                if (have>0){
                    select_counpons.setText("选择优惠券");
                }else if (have==0){
                    select_counpons.setText("无可用优惠券");
                    select_counpons.setTextColor(Color.parseColor("#BBBBBB"));
                    select_counpons.setClickable(false);
                }
                double round = round(mResult.getTotalFee(), 2);
                mSum.setText(round + "");
            }
        } else if ("vip".equals(mBiao)) {

            line4.setVisibility(View.GONE);
            mVipLevel = mIntent.getIntExtra("vipLevel", 0);
            String totalFee = mIntent.getStringExtra("totalFee");
            mSum.setText(totalFee);

        } else if ("unpaid".equals(mBiao)) {
            mOrderId = mIntent.getStringExtra("orderId");
            mZong = mIntent.getDoubleExtra("zong", 0);
            mTotalFee2 = mIntent.getDoubleExtra("totalFee",0);
            mJuan = mIntent.getDoubleExtra("juan", 0);
            double round = round(mJuan, 2);
            select_counpons.setText("-￥"+round);
            mSum.setText(""+mZong);
        } else if ("cardStudy".equals(mBiao)) {
            data = (StudyCardOrderCreateBenas.ResultBean) mIntent.getSerializableExtra("data");
            birthday = mIntent.getStringExtra("birthday");
            DestroyActivityUtil.addDestoryActivityToMap(PayActivity.this,"PayActivity");
            mResultBean1 = (CouponBean.ResultBean) mIntent.getSerializableExtra("juan");
            int have = mIntent.getIntExtra("have", 0);
            mPromoPrice = mIntent.getDoubleExtra("promoPrice", 0);
            if(mResultBean1 !=null){
                double couponFee = mResultBean1.getCouponFee();
                double totalFee = data.getTotalFee();
                double zong = mPromoPrice - couponFee;
                double round = round(zong, 2);
                double round1 = round(couponFee, 2);
                Log.i("cardStudy", "initEventAndData: "+round);
                mSum.setText(round + "");
                select_counpons.setText("-￥"+round1);
            }else {
                if (have>0){
                    select_counpons.setText("选择优惠券");
                }else if (have==0){
                    select_counpons.setText("无可用优惠券");
                    select_counpons.setTextColor(Color.parseColor("#BBBBBB"));
                    select_counpons.setClickable(false);
                }
                double round = round(mPromoPrice, 2);
                mSum.setText( round+ "");
            }
        }else if ("cardRenew".equals(mBiao)){
            birthdaycard = mIntent.getStringExtra("birthday");
            data1 = (StudyCardOrderCreateBenas.ResultBean) mIntent.getSerializableExtra("data");
            promoPrice1 = mIntent.getDoubleExtra("promoPrice1", 0);
            promoPrice = mIntent.getDoubleExtra("promoPrice", 0);
            mPresenter.getDataP(1,DifferentiateEnum.VALIDCOUPONS);
            double round = round(promoPrice, 2);
            double round1 = round(promoPrice1, 2);
            if (data1.getCardType()==2){
                mSum.setText(round+"");
            }else if (data1.getCardType()==3){
                mSum.setText(round1+"");
            }
        }
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
     * 获取权限使用的 RequestCode
     */
    private static final int PERMISSIONS_REQUEST_CODE = 1002;

    /**
     * 检查支付宝 SDK 所需的权限，并在必要的时候动态获取。
     * 在 targetSDK = 23 以上，READ_PHONE_STATE 和 WRITE_EXTERNAL_STORAGE 权限需要应用在运行时获取。
     * 如果接入支付宝 SDK 的应用 targetSdk 在 23 以下，可以省略这个步骤。
     */
    private void requestPermission() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.READ_PHONE_STATE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    }, PERMISSIONS_REQUEST_CODE);

        } else {

        }
    }

    private void initCheckBox() {

        mCbZhifubao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = mCbZhifubao.isChecked();
                if (checked) {
                    mCbWeixin.setChecked(false);
                    mCbZhifubao.setClickable(false);
                    mCbWeixin.setClickable(true);
                }
            }
        });
        mLine1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCbZhifubao.setChecked(true);
                boolean checked = mCbZhifubao.isChecked();
                if (checked) {
                    mCbWeixin.setChecked(false);
                    mCbZhifubao.setClickable(false);
                    mCbWeixin.setClickable(true);
                }
            }
        });
        mLine2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCbWeixin.setChecked(true);
                boolean checked = mCbWeixin.isChecked();
                if (checked) {
                    mCbZhifubao.setChecked(false);
                    mCbWeixin.setClickable(false);
                    mCbZhifubao.setClickable(true);
                }
            }
        });
        mCbWeixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = mCbWeixin.isChecked();
                if (checked) {
                    mCbZhifubao.setChecked(false);
                    mCbWeixin.setClickable(false);
                    mCbZhifubao.setClickable(true);
                }
            }
        });
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_pay;
    }


    @OnClick({R.id.iv_finis, R.id.confirm,R.id.line4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_finis:
                if ("shopping".equals(mBiao)) {
                    String orderId = PreUtils.getString("orderId", "");
                    Intent intent1 = new Intent(PayActivity.this, LineItemActivity.class);
                    intent1.putExtra("biao", "2");
                    intent1.putExtra("orderId", orderId + "");
                    if (mResultBean1!=null) {
                        intent1.putExtra("juan", mResultBean1.getCouponFee());
                    }
                    startActivity(intent1);
                    finish();
                }
                if ("course".equals(mBiao)) {
                    Intent intent = new Intent(PayActivity.this, ClassListActivity.class);
                    PreUtils.putString("biao", "no");
                    startActivity(intent);
                }
                finish();
                break;
            case R.id.confirm:
                String s = mSum.getText().toString();
                if ("course".equals(mBiao)) {
                    long courseId = mResultBean.getCourseId();
                    HashMap<String, String> map = new HashMap<>();
                    map.put("courseId",courseId+"");
                    if(mResultBean1!=null){
                        long couponId = mResultBean1.getCouponId();
                        map.put("couponId",couponId+"");
                        Log.i("yx5555", courseId+"onViewClicked: "+couponId);
                    }else {
                        map.put("couponId","");
                    }
                    if(Double.parseDouble(s)>0){
                        boolean checked = mCbWeixin.isChecked();
                        if (checked) {
                            mPresenter.getDataP(map, DifferentiateEnum.PAYBEANCURSE);
                        }
                        boolean checked1 = mCbZhifubao.isChecked();
                        if (checked1) {
                            mPresenter.getDataP(map, DifferentiateEnum.ALIPAYCURSE);
                        }
                    }
                } else if ("shopping".equals(mBiao)) {
                    long orderId = mResult.getOrderId();
                    boolean checked = mCbWeixin.isChecked();
                    Log.i("MyOrderid", " "+orderId);
                    HashMap<String, String> map = new HashMap<>();
                    map.put("orderId",orderId+"");
                    if(mResultBean1!=null){
                        long couponId = mResultBean1.getCouponId();
                        map.put("couponId",couponId+"");
                    }else {
                        map.put("couponId","");
                    }
                    PreUtils.putString("orderId", orderId + "");
                    if(Double.parseDouble(s)>0){
                        if (checked) {
                            mPresenter.getDataP(map, DifferentiateEnum.PAYBEAN);
                        }
                        boolean checked1 = mCbZhifubao.isChecked();
                        if (checked1) {
                            mPresenter.getDataP(map, DifferentiateEnum.ALIPAY);
                        }
                    }
                } else if ("vip".equals(mBiao)) {
                    boolean checked = mCbWeixin.isChecked();
                    if(Double.parseDouble(s)>0){
                        if (checked) {
                            mPresenter.getDataP(mVipLevel, DifferentiateEnum.VIPWXPAY);
                        }
                        boolean checked1 = mCbZhifubao.isChecked();
                        if (checked1) {
                            mPresenter.getDataP(mVipLevel, DifferentiateEnum.VIPALIPAY);
                        }
                    }
                } else if ("unpaid".equals(mBiao)) {
                    if(Double.parseDouble(s)>0){
                        boolean checked = mCbWeixin.isChecked();
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("orderId",mOrderId);
                        if(mResultBean1!=null){
                            long couponId = mResultBean1.getCouponId();
                            map.put("couponId",couponId+"");
                        }else {
                            map.put("couponId","");
                        }
                        if (checked) {
                            mPresenter.getDataP(map, DifferentiateEnum.PAYBEAN);
                        }
                        boolean checked1 = mCbZhifubao.isChecked();
                        if (checked1) {
                            mPresenter.getDataP(map, DifferentiateEnum.ALIPAY);
                        }
                    }

                } else if ("cardStudy".equals(mBiao)) {
                    boolean checked = mCbWeixin.isChecked();
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("cardType", data.getCardType());
                    map.put("babyBirthday", birthday);
                    if(mResultBean1!=null){
                        long couponId = mResultBean1.getCouponId();
                        map.put("couponId",couponId+"");
                    }else {
                        map.put("couponId","");
                    }
                    if(Double.parseDouble(s)>0){
                        if (checked) {
                            mPresenter.getDataP(map, DifferentiateEnum.STUDYCARDWEIXIN);
                        }
                        boolean checked1 = mCbZhifubao.isChecked();
                        if (checked1) {
                            //学习卡支付宝支付
                            mPresenter.getDataP(map, DifferentiateEnum.STUDYCARDZHIFUBAO);
                        }
                    }
                }else if ("cardRenew".equalsIgnoreCase(mBiao)){
                    boolean checked = mCbWeixin.isChecked();
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("cardType", data1.getCardType());
                    map.put("babyBirthday", birthdaycard);
                    if(mResultBean1!=null){
                        long couponId = mResultBean1.getCouponId();
                        map.put("couponId",couponId+"");
                    }else {
                        map.put("couponId","");
                    }
                    if(Double.parseDouble(s)>0){
                        if (checked) {
                            mPresenter.getDataP(map, DifferentiateEnum.STUDYCARDWEIXIN);
                        }
                        boolean checked1 = mCbZhifubao.isChecked();
                        if (checked1) {
                            mPresenter.getDataP(map, DifferentiateEnum.STUDYCARDZHIFUBAO);
                        }
                    }

                }
                break;
            case R.id.line4:
                Intent intent = new Intent(PayActivity.this, CouponsActivity.class);
                if ("shopping".equals(mBiao)){
                    intent.putExtra("biao",noaddress);
                    intent.putExtra("money",mTotalFee+"");
                }
                if("course".equals(mBiao)){
                    intent.putExtra("biao","noaddress");
                    intent.putExtra("money",mTotalFee1+"");
                }
                if("cardStudy".equals(mBiao)){
                    intent.putExtra("biao","CardType");
                    intent.putExtra("money",mPromoPrice+"");
                }
                if("cardRenew".equals(mBiao)){
                    intent.putExtra("biao","cardRenew");
                    if (data1.getCardType()==2){
                        intent.putExtra("money",promoPrice+"");
                    }else if (data1.getCardType()==3){
                        intent.putExtra("money",promoPrice1+"");
                    }
                }
                if("unpaid".equals(mBiao)){
                    intent.putExtra("biao",noaddress);
                    intent.putExtra("money",mTotalFee2+"");
                }
                startActivityForResult(intent,144);
                break;
        }
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum) {
            case PAYBEAN:
            case PAYBEANCURSE:
            case STUDYCARDWEIXIN:
            case VIPWXPAY:
                PayBean payBean = (PayBean) o;
                PayBean.ResultBean result = payBean.getResult();
                Log.i("yx520", "show: " + payBean.getCode());
                if(payBean.getResult().getAppid()!=null){
                    Log.i("yx520", "appid: " + payBean.getResult().getAppid());
                    Log.i("yx520", "partnerid: " + payBean.getResult().getPartnerid());
                    Log.i("yx520", "prepayid: " + payBean.getResult().getPrepayid());
                    Log.i("yx520", "packageX: " + payBean.getResult().getPackageX());
                    Log.i("yx520", "noncestr: " + payBean.getResult().getNoncestr());
                    Log.i("yx520", "timestamp: " + payBean.getResult().getTimestamp());
                    Log.i("yx520", "sign: " + payBean.getResult().getSign());
                    String appid = result.getAppid();
                    String partnerid = result.getPartnerid();
                    String prepayid = result.getPrepayid();
                    String packageX = result.getPackageX();
                    String noncestr = result.getNoncestr();
                    String timestamp = result.getTimestamp();
                    String sign = result.getSign();
                    PayReq request = new PayReq();
                    request.extData = mBiao;
                    request.appId = appid;
                    request.partnerId = partnerid;
                    request.prepayId = prepayid;
                    request.packageValue = packageX;
                    request.nonceStr = noncestr;
                    request.timeStamp = timestamp;
                    request.sign = sign;
                    api.sendReq(request);
                    finish();
                }
                break;
            case ALIPAY:
            case ALIPAYCURSE:
            case STUDYCARDZHIFUBAO:
            case VIPALIPAY:
                AlipayBean alipayBean = (AlipayBean) o;
                String authInfo = alipayBean.getResult();
                Runnable authRunnable = new Runnable() {
                    @Override
                    public void run() {
                        // 构造AuthTask 对象
                        PayTask authTask = new PayTask(PayActivity.this);
                        // 调用授权接口，获取授权结果
                        Map<String, String> result = authTask.payV2(authInfo, true);
                        Message msg = new Message();
                        msg.what = SDK_PAY_FLAG;
                        msg.obj = result;
                        mHandler.sendMessage(msg);
                    }
                };
                // 必须异步调用
                Thread authThread = new Thread(authRunnable);
                authThread.start();
                break;
            case VALIDCOUPONS:
                ValidCouponBeans validCouponBeans= (ValidCouponBeans) o;
                int result1 = validCouponBeans.getResult();
                if (result1>0){
                    select_counpons.setText("选择优惠券");
                    select_counpons.setTextColor(Color.parseColor("#333333"));
                }else if (result1==0){
                    select_counpons.setText("无可用优惠券");
                    select_counpons.setTextColor(Color.parseColor("#BBBBBB"));
                    line4.setClickable(false);
                }
                break;
        }
    }

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == 147) {
            setResult(147, mIntent);
            finish();
        }
        if(requestCode==144&&resultCode==15){
            mResultBean1 = (CouponBean.ResultBean) data.getSerializableExtra("data");
            double couponFee = mResultBean1.getCouponFee();
            if("shopping".equals(mBiao)){
                double zong = mTotalFee - couponFee;
                Log.i("shopping", "onActivityResult: "+zong);
                Log.i("shopping", "onActivityResult: "+couponFee);
                double round = round(zong, 2);
                double round1 = round(couponFee, 2);
                mSum.setText(round+"");
                select_counpons.setText("-￥"+round1);
            }
            if("unpaid".equals(mBiao)){
                double zong = mTotalFee2 - couponFee;
                //Log.i("shopping", "onActivityResult: "+zong);
                //Log.i("shopping", "onActivityResult: "+couponFee);
                double round = round(zong, 2);
                double round1 = round(couponFee, 2);
                mSum.setText(round+"");
                select_counpons.setText("-￥"+round1);
            }
           if("course".equals(mBiao)){
               double zong = mTotalFee1 - couponFee;
               double round = round(zong, 2);
               double round1 = round(couponFee, 2);
               mSum.setText(round+"");
               select_counpons.setText("-￥"+round1);
           }
           if("cardStudy".equals(mBiao)){
               double zong = mPromoPrice - couponFee;
               double round = round(zong, 2);
               double round1 = round(couponFee, 2);
               mSum.setText(round+"");
               select_counpons.setText("-￥"+round1);
           }
           if("cardRenew".equals(mBiao)){
               double zong = 0;
               if (data1.getCardType()==2){
                    zong=promoPrice-couponFee;
               }else if (data1.getCardType()==3){
                   zong= promoPrice1-couponFee;
               }
               double round = round(zong, 2);
               double round1 = round(couponFee, 2);
               mSum.setText(round+"");
               select_counpons.setText("-￥"+round1);
           }
        }
    }

}
