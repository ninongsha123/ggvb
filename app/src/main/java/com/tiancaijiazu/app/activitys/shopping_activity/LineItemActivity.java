package com.tiancaijiazu.app.activitys.shopping_activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.shopping_activity.adapter.RlvAdapter_lineitem;
import com.tiancaijiazu.app.activitys.shopping_activity.adapter.RlvAdapter_order_detail;
import com.tiancaijiazu.app.alipay.AuthResult;
import com.tiancaijiazu.app.alipay.PayResult;
import com.tiancaijiazu.app.base.activity.BaseActivity;
import com.tiancaijiazu.app.beans.AlipayBean;
import com.tiancaijiazu.app.beans.OrderCannelBeans;
import com.tiancaijiazu.app.beans.OrderFormBean;
import com.tiancaijiazu.app.beans.OrderdetailBean;
import com.tiancaijiazu.app.beans.PayBean;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.MediumBoldTextViewStandard;
import com.tiancaijiazu.app.mvp.IView;
import com.tiancaijiazu.app.utils.city.ToastUtils;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.droidsonroids.gif.GifImageView;

import static com.tiancaijiazu.app.app.App.api;

public class LineItemActivity extends BaseActivity<IView, Presenter<IView>> implements IView {


    @BindView(R.id.iv_finis)
    ImageView mIvFinis;
    @BindView(R.id.rela)
    RelativeLayout mRela;
    @BindView(R.id.tv_zf_ok)
    TextView mTvZfOk;
    @BindView(R.id.await)
    TextView mAwait;
    @BindView(R.id.line1)
    RelativeLayout mLine1;
    @BindView(R.id.address)
    ImageView mAddress;
    @BindView(R.id.name)
    TextView mName;
    @BindView(R.id.a)
    TextView a;
    @BindView(R.id.relat)
    RelativeLayout mRelat;
    @BindView(R.id.recylerView)
    RecyclerView mRecylerView;
    @BindView(R.id.tv_evaluate)
    Button mTvEvaluate;
    @BindView(R.id.fu_kuan)
    Button mFuKuan;
    @BindView(R.id.cancel_order)
    Button mCancelOrder;
    @BindView(R.id.phone)
    TextView mPhone;
    @BindView(R.id.dizhi)
    TextView mDizhi;
    @BindView(R.id.biaohao)
    TextView mBiaohao;
    @BindView(R.id.time)
    TextView mTime;
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
    @BindView(R.id.notNumbers)
    TextView mNotNumbers;
    @BindView(R.id.logistics)
    TextView mLogistics;
    @BindView(R.id.tracking_number)
    TextView mTrackingNumber;
    @BindView(R.id.relative)
    RelativeLayout mRelative;
    @BindView(R.id.gif_iv)
    GifImageView mGifIv;
    @BindView(R.id.zong_menoy)
    MediumBoldTextViewStandard mZongMenoy;
    @BindView(R.id.juan)
    TextView mJuan;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
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
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(mActivity, "成功", Toast.LENGTH_SHORT).show();
                        // showAlert(PayDemoActivity.this, getString(R.string.pay_success) + payResult);
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        // showAlert(PayDemoActivity.this, getString(R.string.pay_failed) + payResult);
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

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

        ;
    };

    /**
     * 获取权限使用的 RequestCode
     */
    private static final int PERMISSIONS_REQUEST_CODE = 1002;
    private RlvAdapter_order_detail mRlvAdapterOrderDetail;
    private String mBiao;
    private Intent mIntent;

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

    @Override
    protected void initEventAndData() {
        initSett();
        requestPermission();
        mIntent = getIntent();
        mBiao = mIntent.getStringExtra("biao");
        if ("1".equals(mBiao)) {
            OrderFormBean.ResultBean result = (OrderFormBean.ResultBean) mIntent.getSerializableExtra("data");
            String consigneeName = result.getConsigneeName();
            String consigneeMobile = result.getConsigneeMobile();
            String consigneeAddress = result.getConsigneeAddress();
            long orderId = result.getOrderId();
            String createTime = result.getCreateTime();
            String substring = createTime.substring(0, 19);
            List<OrderFormBean.ResultBean.ItemListBean> itemList = result.getItemList();
            mName.setText(consigneeName);
            mPhone.setText(consigneeMobile);
            mDizhi.setText(consigneeAddress);
            mBiaohao.setText("订单编号：" + orderId);
            mTime.setText("下单时间：" + substring);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            mRecylerView.setLayoutManager(linearLayoutManager);
            RlvAdapter_lineitem rlvAdapterLineitem = new RlvAdapter_lineitem(itemList, this);
            mRecylerView.setAdapter(rlvAdapterLineitem);
        } else if ("2".equals(mBiao)) {
            mTvZfOk.setVisibility(View.GONE);
            mAwait.setVisibility(View.VISIBLE);
            mFuKuan.setVisibility(View.VISIBLE);
            mCancelOrder.setVisibility(View.VISIBLE);
            mTvEvaluate.setVisibility(View.GONE);
            String orderId = mIntent.getStringExtra("orderId");
            mPresenter.getDataP(orderId, DifferentiateEnum.ORDERDETAIL);
        } else if ("3".equals(mBiao)) {
            mTvZfOk.setVisibility(View.VISIBLE);
            mAwait.setVisibility(View.GONE);
            mFuKuan.setVisibility(View.GONE);
            mCancelOrder.setVisibility(View.GONE);
            mTvEvaluate.setVisibility(View.VISIBLE);
            String orderId = mIntent.getStringExtra("orderId");
            mPresenter.getDataP(orderId, DifferentiateEnum.ORDERDETAIL);

            mTvEvaluate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(LineItemActivity.this, EvaluateActivity.class);
                    intent.putExtra("picture", mRlvAdapterOrderDetail.mData.get(0).getPicUri());
                    intent.putExtra("orderId", orderId);
                    startActivity(intent);
                }
            });
        }

        mIvFinis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_line_item;
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
    public void showError(String error) {

    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum) {
            case ORDERDETAIL:
                OrderdetailBean orderdetailBean = (OrderdetailBean) o;
                OrderdetailBean.ResultBean result = orderdetailBean.getResult();
                String consigneeName = result.getConsigneeName();
                String consigneeMobile = result.getConsigneeMobile();
                String consigneeAddress = result.getConsigneeAddress();

                mCancelOrder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        long orderId = result.getOrderId();
                        Log.d("bjg", "onClickerCannel: " + orderId);
                        mPresenter.getDataP(orderId, DifferentiateEnum.ORDERCANNEL);
                    }
                });
                mFuKuan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // mPresenter.getDataP(orderId,DifferentiateEnum.PAYBEAN);
                        //mPresenter.getDataP(orderId,DifferentiateEnum.ALIPAY);
                        double juan = mIntent.getDoubleExtra("juan", 0);
                        Intent intent = new Intent(LineItemActivity.this, PayActivity.class);
                        intent.putExtra("orderId", result.getOrderId() + "");
                        intent.putExtra("zong", result.getTotalFee()-juan);
                        intent.putExtra("totalFee", result.getTotalFee());
                        intent.putExtra("juan", juan);
                        intent.putExtra("biao", "unpaid");
                        startActivity(intent);
                    }
                });
                if ("3".equals(mBiao)) {
                    String trackingNo = result.getTrackingNo();
                    String expressCompany = result.getExpressCompany();
                    if (!"".equals(trackingNo) && !"".equals(expressCompany)) {
                        mLogistics.setText("快递公司：" + expressCompany);
                        mTrackingNumber.setText("物流单号：" + trackingNo);
                        mLogistics.setVisibility(View.VISIBLE);
                        mTrackingNumber.setVisibility(View.VISIBLE);
                    } else {
                        mNotNumbers.setVisibility(View.VISIBLE);
                    }
                }
                long orderId = result.getOrderId();
                String createTime = result.getCreateTime();
                double totalFee = result.getTotalFee();
                double actuallyPaid = result.getActuallyPaid();
                double card_fee = result.getCard_fee();
                if (actuallyPaid == 0) {
                    double juan = mIntent.getDoubleExtra("juan",0);
                    if (juan==0){
                        mJuan.setText("-¥"+result.getCard_fee());
                    }else {
                        mJuan.setText("-¥" + juan);
                    }
                    double v = totalFee - juan;
                    mZongMenoy.setText("¥" + v);
                } else {
                    mJuan.setText("-¥"+card_fee);
                    mZongMenoy.setText("¥" + actuallyPaid);
                }
                Log.i("shopping", "show: " + createTime);
                String substring = createTime.substring(0, 19);
                List<OrderdetailBean.ResultBean.ItemListBean> itemList = result.getItemList();
                mName.setText(consigneeName);
                mPhone.setText(consigneeMobile);
                mDizhi.setText(consigneeAddress);
                mBiaohao.setText("订单编号：" + orderId);
                mTime.setText("下单时间：" + substring);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                mRecylerView.setLayoutManager(linearLayoutManager);
                mRlvAdapterOrderDetail = new RlvAdapter_order_detail(itemList, this);
                mRecylerView.setAdapter(mRlvAdapterOrderDetail);

                mRlvAdapterOrderDetail.setOnClickLisiter(new RlvAdapter_order_detail.onClickLisiter() {
                    @Override
                    public void onClicker(View view, int position, List<OrderdetailBean.ResultBean.ItemListBean> mData) {
                        long skuId = mData.get(position).getSkuId();
                        Intent intent = new Intent(LineItemActivity.this, ShopActivity.class);
                        intent.putExtra("id", skuId + "");
                        startActivity(intent);
                    }
                });
                break;
            case PAYBEAN:
                PayBean payBean = (PayBean) o;
                PayBean.ResultBean result1 = payBean.getResult();
                String appid = result1.getAppid();
                String partnerid = result1.getPartnerid();
                String prepayid = result1.getPrepayid();
                String packageX = result1.getPackageX();
                String noncestr = result1.getNoncestr();
                String timestamp = result1.getTimestamp();
                String sign = result1.getSign();
                PayReq request = new PayReq();
                request.appId = appid;
                request.partnerId = partnerid;
                request.prepayId = prepayid;
                request.packageValue = packageX;
                request.nonceStr = noncestr;
                request.timeStamp = timestamp;
                request.sign = sign;
                api.sendReq(request);
                break;
            case ALIPAY:
                AlipayBean alipayBean = (AlipayBean) o;
                String authInfo = alipayBean.getResult();

                Runnable authRunnable = new Runnable() {

                    @Override
                    public void run() {
                        // 构造AuthTask 对象
                        PayTask authTask = new PayTask(LineItemActivity.this);
                        // 调用授权接口，获取授权结果
                        Map<String, String> result = authTask.payV2(authInfo, true);

                        Message msg = new Message();
                        msg.what = SDK_AUTH_FLAG;
                        msg.obj = result;
                        mHandler.sendMessage(msg);
                    }
                };
                // 必须异步调用
                Thread authThread = new Thread(authRunnable);
                authThread.start();
                break;
            case ORDERCANNEL:
                OrderCannelBeans orderCannelBeans = (OrderCannelBeans) o;
                String msg = orderCannelBeans.getMsg();
                if (msg.equalsIgnoreCase("取消成功")) {
                    ToastUtils.showShortToast(LineItemActivity.this, msg);
                }
                finish();
                break;
        }
    }

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
