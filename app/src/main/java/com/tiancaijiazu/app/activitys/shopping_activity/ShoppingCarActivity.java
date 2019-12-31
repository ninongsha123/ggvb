package com.tiancaijiazu.app.activitys.shopping_activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.views.SwipeItemLayout;
import com.tiancaijiazu.app.adapters.RlvAdapter_shop_car;
import com.tiancaijiazu.app.base.activity.BaseActivity;
import com.tiancaijiazu.app.beans.Remove_carBean;
import com.tiancaijiazu.app.beans.Shopping_carBean;
import com.tiancaijiazu.app.beans.Update_carBean;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.TimeUtil;
import com.tiancaijiazu.app.utils.status.LoadingLayout;
import com.tiancaijiazu.app.mvp.IView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ShoppingCarActivity extends BaseActivity<IView, Presenter<IView>> implements IView {

    @BindView(R.id.rlv)
    RecyclerView mRlv;
    @BindView(R.id.money)
    TextView mMoney;
    @BindView(R.id.checkbox)
    CheckBox mCheckbox;
    @BindView(R.id.zong_sum)
    TextView mZongSum;
    @BindView(R.id.a)
    TextView a;
    @BindView(R.id.jiesuan)
    TextView mJiesuan;
    @BindView(R.id.iv_finis)
    ImageView mIvFinis;
    @BindView(R.id.loadingLayout)
    LoadingLayout loadingLayout;
    private double sum = 0;
    private int select = 0;
    private RlvAdapter_shop_car mRlvAdapterShopCar;
    private String result1;
    int page = 1;
    private ArrayList<Shopping_carBean.ResultBean> mBeans;

    @Override
    protected void initEventAndData() {
        initSett();
        mBeans = new ArrayList<>();
        initRlv();
        mCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = mCheckbox.isChecked();
                sum = 0;
                if (checked) {
                    mRlvAdapterShopCar.addIsBo(true);
                    for (int i = 0; i < mRlvAdapterShopCar.mData.size(); i++) {
                        double unitPrice = mRlvAdapterShopCar.mData.get(i).getOldPrice();
                        int quantity = mRlvAdapterShopCar.mData.get(i).getQuantity();
                        sum += unitPrice * quantity;
                    }
                    mMoney.setText(sum + "");
                    select = mRlvAdapterShopCar.mData.size();
                    mJiesuan.setText("结算(" + select + ")");
                    mJiesuan.setBackgroundResource(R.mipmap.jie_suan);
                    for (int i = 0; i < mRlvAdapterShopCar.mData.size(); i++) {
                        mBeans.add(mRlvAdapterShopCar.mData.get(i));
                    }
                } else {
                    mRlvAdapterShopCar.addIsBo(false);
                    mMoney.setText(sum + "");
                    select = 0;
                    mJiesuan.setText("结算(" + select + ")");
                    mJiesuan.setBackgroundResource(R.mipmap.zero_jie_suan);
                    for (int i = 0; i < mRlvAdapterShopCar.mData.size(); i++) {
                        mBeans.remove(mRlvAdapterShopCar.mData.get(i));
                    }
                }
            }
        });
        //mPresenter.getDataP(stokid, DifferentiateEnum.UPDATESHOPCAT);

        mPresenter.getDataP1(page, DifferentiateEnum.SHOPPINGLIST,loadingLayout);
        loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
            @Override
            public void onReload(View v) {
                mPresenter.getDataP1(page, DifferentiateEnum.SHOPPINGLIST,loadingLayout);
            }
        });
    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum) {
            case UPDATESHOPCAT:
                Update_carBean update_carBean = (Update_carBean) o;
                String result = update_carBean.getResult();
                Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
                break;
            case REMOVESHOPCAR:
                Remove_carBean remove_carBean = (Remove_carBean) o;
                result1 = remove_carBean.getResult();
                Toast.makeText(mActivity, "" + result1, Toast.LENGTH_SHORT).show();
                break;
            case SHOPPINGLIST:
                Shopping_carBean carBean = (Shopping_carBean) o;
                List<Shopping_carBean.ResultBean> result2 = carBean.getResult();
                mRlvAdapterShopCar.addData(result2);
                mZongSum.setText("共 " + result2.size() + " 件商品");
                break;

        }
    }

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_shopping_car;
    }

    @OnClick({R.id.jiesuan, R.id.iv_finis})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.jiesuan:
                if (TimeUtil.isInvalidClick(view, 300))
                    return;
                if (select == 0) {
                    Toast.makeText(mActivity, "未选中商品", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(this, POActivity.class);
                    intent.putExtra("data", mBeans);
                    intent.putExtra("biao", "1");
                    startActivity(intent);
                }
                break;
            case R.id.iv_finis:
                finish();
                break;
        }
    }

    @Override
    public void showError(String error) {

    }

    private void initRlv() {

        List<Shopping_carBean.ResultBean> resultBeans = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRlv.setLayoutManager(layoutManager);
        mRlvAdapterShopCar = new RlvAdapter_shop_car(this, resultBeans, mMoney, mZongSum);
        mRlv.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(this));
        mRlv.setAdapter(mRlvAdapterShopCar);
        mRlvAdapterShopCar.OnsetOnClickLisiter(new RlvAdapter_shop_car.setOnClickLisiter() {
            @Override
            public void onClickGx(View view, int position, List<Shopping_carBean.ResultBean> data, boolean isbo, int su) {
                if (isbo) {
                    select++;
                    mJiesuan.setText("结算(" + select + ")");
                    mJiesuan.setBackgroundResource(R.mipmap.jie_suan);
                    String text = String.valueOf(mMoney.getText());
                    sum = Float.parseFloat(text);
                    double integer = data.get(position).getOldPrice();
                    integer = integer * su;
                    sum = (sum + integer);
                    double round = round(sum, 2);
                    mMoney.setText(round + "");
                    mBeans.add(data.get(position));
                } else {
                    select--;
                    mJiesuan.setText("结算(" + select + ")");
                    if (select == 0) {
                        mJiesuan.setBackgroundResource(R.mipmap.zero_jie_suan);
                    }
                    String text = String.valueOf(mMoney.getText());
                    sum = Float.parseFloat(text);
                    double integer = data.get(position).getOldPrice();
                    integer = integer * su;
                    sum = (sum - integer);
                    double round = round(sum, 2);
                    mMoney.setText(round + "");
                    mBeans.remove(data.get(position));
                }
                if (data.size() != 0) {
                    if (select == data.size()) {
                        mCheckbox.setChecked(true);
                    } else {
                        mCheckbox.setChecked(false);
                    }
                } else {
                    mCheckbox.setChecked(false);
                }
            }
        });


        mRlvAdapterShopCar.setOnClickLisiterJia(new RlvAdapter_shop_car.onClickLisiterJia() {
            @Override
            public void onClickerJia(View view, int position, List<Shopping_carBean.ResultBean> mData, int sum) {
                long stockId = mData.get(position).getStockId();
                HashMap<String, Object> map = new HashMap<>();
                map.put("stockId", stockId + "");
                map.put("quantity", sum);
                mPresenter.getDataP(map, DifferentiateEnum.UPDATESHOPCAT);
            }
        });

        mRlvAdapterShopCar.setOnClickLisiterJian(new RlvAdapter_shop_car.onClickLisiterJian() {
            @Override
            public void onClickerJian(View view, int position, List<Shopping_carBean.ResultBean> mData, int sum) {
                long stockId = mData.get(position).getStockId();
                HashMap<String, Object> map = new HashMap<>();
                map.put("stockId", stockId + "");
                map.put("quantity", sum);
                mPresenter.getDataP(map, DifferentiateEnum.UPDATESHOPCAT);
            }
        });
//        mRlvAdapterShopCar.setOnClickLisiterKong(new RlvAdapter_shop_car.onClickLisiterKong() {
//            @Override
//            public void onClickerKong(int position, List<Shopping_carBean.ResultBean> mData, int sum) {
//                HashMap<String, Object> map = new HashMap<>();
//                long stockId = mData.get(position).getStockId();
//                map.put("stockId", stockId + "");
//                map.put("quantity", sum);
//                mPresenter.getDataP(map, DifferentiateEnum.UPDATESHOPCAT);
//            }
//        });

        mRlvAdapterShopCar.setOnClickLisiterDelete(new RlvAdapter_shop_car.onClickLisiterDelete() {
            @Override
            public void onClickerDelete(View view, int position, List<Shopping_carBean.ResultBean> mData, boolean isbo) {
                long stockId = mData.get(position).getStockId();
                mPresenter.getDataP(stockId + "", DifferentiateEnum.REMOVESHOPCAR);
                if (isbo) {
                    select--;
                    mJiesuan.setText("结算(" + select + ")");
                    if (select == 0) {
                        mJiesuan.setBackgroundResource(R.mipmap.zero_jie_suan);
                    }
                }
                if (mData.size() - 1 != 0) {
                    if (select == mData.size() - 1) {
                        mCheckbox.setChecked(true);
                    } else {
                        mCheckbox.setChecked(false);
                    }
                } else {
                    mCheckbox.setChecked(false);
                }
            }
        });
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
}
