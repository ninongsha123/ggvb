package com.tiancaijiazu.app.activitys.early;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.bean.CouponBean;
import com.tiancaijiazu.app.activitys.coupons.CouponsActivity;
import com.tiancaijiazu.app.activitys.early.datepicker.CustomDatePicker;
import com.tiancaijiazu.app.activitys.early.datepicker.DateFormatUtils;
import com.tiancaijiazu.app.activitys.shopping_activity.PayActivity;
import com.tiancaijiazu.app.base.activity.BaseActivity;
import com.tiancaijiazu.app.beans.CardTypeBean;
import com.tiancaijiazu.app.beans.StudyCardOrderCreateBenas;
import com.tiancaijiazu.app.beans.ValidCouponBeans;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.ScreenStatusUtil;
import com.tiancaijiazu.app.utils.TimeUtil;
import com.tiancaijiazu.app.utils.city.ToastUtils;
import com.tiancaijiazu.app.mvp.IView;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.eventbus.EventBus;

/**
 *  早教学院正式卡
 *
 */

public class TheFormalCardActivity extends BaseActivity<IView, Presenter<IView>> implements IView, View.OnClickListener {


    @BindView(R.id.iv_finis)
    ImageView mIvFinis;
    @BindView(R.id.card_ok)
    ImageView mCardOk;
    @BindView(R.id.title_name)
    TextView mTitleName;
    @BindView(R.id.card_name_one)
    TextView mCardNameOne;
    @BindView(R.id.card_name_two)
    TextView mCardNameTwo;
    @BindView(R.id.card_name_three)
    TextView mCardNameThree;
    @BindView(R.id.money_one)
    TextView mMoneyOne;
    @BindView(R.id.money_two)
    TextView mMoneyTwo;
    @BindView(R.id.money_three)
    TextView mMoneyThree;
    @BindView(R.id.type_sum_one)
    TextView mTypeSumOne;
    @BindView(R.id.type_tv_name_one)
    TextView mTypeTvNameOne;
    @BindView(R.id.type_sum_two)
    TextView mTypeSumTwo;
    @BindView(R.id.type_tv_name_two)
    TextView mTypeTvNameTwo;
    @BindView(R.id.type_sum_three)
    TextView mTypeSumThree;
    @BindView(R.id.type_tv_name_three)
    TextView mTypeTvNameThree;
    @BindView(R.id.type_sum_one_two)
    TextView mTypeSumOneTwo;
    @BindView(R.id.type_tv_name_one_two)
    TextView mTypeTvNameOneTwo;
    @BindView(R.id.type_sum_two_two)
    TextView mTypeSumTwoTwo;
    @BindView(R.id.type_tv_name_two_two)
    TextView mTypeTvNameTwoTwo;
    @BindView(R.id.type_sum_three_two)
    TextView mTypeSumThreeTwo;
    @BindView(R.id.type_tv_name_three_two)
    TextView mTypeTvNameThreeTwo;
    @BindView(R.id.type_sum_one_three)
    TextView mTypeSumOneThree;
    @BindView(R.id.type_tv_name_one_three)
    TextView mTypeTvNameOneThree;
    @BindView(R.id.type_sum_two_three)
    TextView mTypeSumTwoThree;
    @BindView(R.id.type_tv_name_two_three)
    TextView mTypeTvNameTwoThree;
    @BindView(R.id.type_sum_three_three)
    TextView mTypeSumThreeThree;
    @BindView(R.id.type_tv_name_three_three)
    TextView mTypeTvNameThreeThree;
    @BindView(R.id.commom_one)
    TextView mCommomOne;
    @BindView(R.id.commom_two)
    TextView mCommomTwo;
    @BindView(R.id.commom_three)
    TextView mCommomThree;
    @BindView(R.id.yuan_jia_one)
    TextView mYuanJiaOne;
    @BindView(R.id.yuan_jia_two)
    TextView mYuanJiaTwo;
    @BindView(R.id.yuan_jia_three)
    TextView mYuanJiaThree;
    @BindView(R.id.shopp_card)
    NestedScrollView  sHoppCard;
    @BindView(R.id.buyNow)
    RelativeLayout bUyNow;
    private PopupWindow popupWindow;
    private TextView grow_up;
    private TextView annual_card;
    private TextView falf_year_card;
    private TextView specia_offer;
    private TextView money;
    private ImageView card_ok;
    private RelativeLayout baby_birthday;
    private TextView day;
    private TextView month;
    private boolean isBottomShowcard = true;
    private TextView year;
    private TextView text_day;
    private TextView text_month;
    private TextView text_year;
    private CustomDatePicker mDatePicker;
    private String time;
    private String s;
    private List<CardTypeBean.ResultBean> result;
    private StudyCardOrderCreateBenas.ResultBean result1;
    private int mResult;
    private RelativeLayout mXuan;
    private double mCouponFee;

    @Override
    protected void initEventAndData() {
        ScreenStatusUtil.setFillDip(this);
        //添加横线
        mYuanJiaOne.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);
        //添加横线
        mYuanJiaTwo.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);
        //添加横线
        mYuanJiaThree.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);
        initDatePicker();
        initPop();
        initShop();
        mPresenter.getDataP("", DifferentiateEnum.CARDTYPEBEAN);
        mPresenter.getDataP(1, DifferentiateEnum.VALIDCOUPONS);
    }

    private void initShop() {
        sHoppCard.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView nestedScrollView, int i, int i1, int i2, int i3) {
                if (i1 - i3 > 0 && isBottomShowcard) {//下移隐藏
                    isBottomShowcard = false;
                    bUyNow.animate().translationY(bUyNow.getHeight());
                } else if (i1 - i3 < 0 && !isBottomShowcard) {//上移出现
                    isBottomShowcard = true;
                    bUyNow.animate().translationY(0);
                }
            }
        });
    }

    private void initPop() {
        popupWindow = new PopupWindow();
        popupWindow.setWidth(ActionBar.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ActionBar.LayoutParams.WRAP_CONTENT);
        View inflate = LayoutInflater.from(mActivity).inflate(R.layout.pop_car_ok, null, false);
        grow_up = inflate.findViewById(R.id.grow_up);
        annual_card = inflate.findViewById(R.id.annual_card);
        falf_year_card = inflate.findViewById(R.id.falf_year_card);
        specia_offer = inflate.findViewById(R.id.specia_offer);
        money = inflate.findViewById(R.id.money);
        card_ok = inflate.findViewById(R.id.card_ok);
        baby_birthday = inflate.findViewById(R.id.baby_birthday);
        year = inflate.findViewById(R.id.year);
        month = inflate.findViewById(R.id.month);
        day = inflate.findViewById(R.id.day);
        text_day = inflate.findViewById(R.id.text_day);
        text_year = inflate.findViewById(R.id.text_year);
        text_month = inflate.findViewById(R.id.text_month);
        mXuan = inflate.findViewById(R.id.xuan);
        popupWindow.setContentView(inflate);
        popupWindow.setOutsideTouchable(true);
        //优惠券
        mXuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cardType == 100) {
                    Toast.makeText(mActivity, "请先选择卡类型", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(TheFormalCardActivity.this, CouponsActivity.class);
                    intent.putExtra("biao", "CardType");
                    String s1 = money.getText().toString();
                    intent.putExtra("money", s1);
                    startActivityForResult(intent, 14);
                }
            }
        });
        //设置背景透明才能显示
        popupWindow.setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                darkenBackground(1f);
            }
        });

        poplistener();
    }

    private CouponBean.ResultBean mResultBean;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 14 && resultCode == 15) {
            mResultBean = (CouponBean.ResultBean) data.getSerializableExtra("data");
            String s = money.getText().toString();
            double v = Double.parseDouble(s);
            mCouponFee = mResultBean.getCouponFee();
            double zong = v - mCouponFee;
            double round = round(zong, 2);
            double round1 = round(mCouponFee, 2);
            money.setText(round + "");
            specia_offer.setText("-¥" + round1);
            specia_offer.setTextColor(Color.parseColor("#C5A86E"));
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

    private int cardType = 100;
    private String cardStudy = "cardStudy";
    private double promoPrice = 0;

    private void poplistener() {
        grow_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < result.size(); i++) {
                    String name = result.get(i).getName();
                    if (name.equalsIgnoreCase(grow_up.getText().toString())) {
                        cardType = result.get(i).getCardType();
                        promoPrice = result.get(i).getPromoPrice();
                        double price = result.get(i).getPrice();
                        double round = round(promoPrice - mCouponFee, 2);
                        money.setText(round + "");
                        grow_up.setText(name);

                        break;
                    }
                }
                grow_up.setBackgroundResource(R.drawable.pop_btn_car_ok_true);
                annual_card.setBackgroundResource(R.drawable.pop_btn_car_ok_flase);
                falf_year_card.setBackgroundResource(R.drawable.pop_btn_car_ok_flase);
            }
        });
        falf_year_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < result.size(); i++) {
                    String name = result.get(i).getName();
                    if (name.equalsIgnoreCase(falf_year_card.getText().toString())) {
                        cardType = result.get(i).getCardType();
                        promoPrice = result.get(i).getPromoPrice();
                        double price = result.get(i).getPrice();
                        double round = round(promoPrice - mCouponFee, 2);
                        money.setText(round + "");

                        falf_year_card.setText(name);
                        break;
                    }
                }
                falf_year_card.setBackgroundResource(R.drawable.pop_btn_car_ok_true);
                annual_card.setBackgroundResource(R.drawable.pop_btn_car_ok_flase);
                grow_up.setBackgroundResource(R.drawable.pop_btn_car_ok_flase);
            }
        });
        annual_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < result.size(); i++) {
                    String name = result.get(i).getName();
                    if (name.equalsIgnoreCase(annual_card.getText().toString())) {
                        cardType = result.get(i).getCardType();
                        promoPrice = result.get(i).getPromoPrice();
                        double price = result.get(i).getPrice();
                        double round = round(promoPrice - mCouponFee, 2);
                        money.setText(round + "");
                        annual_card.setText(name);
                        break;
                    }
                }
                annual_card.setBackgroundResource(R.drawable.pop_btn_car_ok_true);
                falf_year_card.setBackgroundResource(R.drawable.pop_btn_car_ok_flase);
                grow_up.setBackgroundResource(R.drawable.pop_btn_car_ok_flase);
            }
        });
        //立即购买
        card_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //    initJump("1");
                if (cardType == 100 && s == null) {
                    Toast.makeText(mActivity, "请填写与选择类型", Toast.LENGTH_SHORT).show();
                } else {
                    if (cardType == 100) {
                        ToastUtils.showShortToast(TheFormalCardActivity.this, "还未选择卡片类型");
                    } else if (s == null) {
                        ToastUtils.showShortToast(TheFormalCardActivity.this, "还未选择宝宝生日");
                    } else {
                        //创建家庭早教学习卡订单  满足条件之后掉接口
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("babyBirthday", s);
                        map.put("cardType", cardType);
                        mPresenter.getDataP(map, DifferentiateEnum.STUDYCARDORDERCREATE);
                        popupWindow.dismiss();
                    }
                }
            }
        });

        baby_birthday.setOnClickListener(this);
        year.setOnClickListener(this);
        month.setOnClickListener(this);
        day.setOnClickListener(this);
        text_day.setOnClickListener(this);
        text_month.setOnClickListener(this);
        text_year.setOnClickListener(this);

    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_the_formal_card;
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum) {
            case CARDTYPEBEAN:
                CardTypeBean cardTypeBean = (CardTypeBean) o;
                result = cardTypeBean.getResult();
                for (int i = 0; i < result.size(); i++) {
                    if (result.get(i).getCardType() == 1) {
                        mCardNameOne.setText(result.get(i).getName());
                        mMoneyOne.setText("¥" + result.get(i).getPromoPrice());
                        mTypeSumOne.setText(result.get(i).getClassAmount1() + "节");
                        String class1 = result.get(i).getClass1();
                        String substring = class1.substring(0, 4);
                        String substring1 = class1.substring(4, class1.length());
                        String a = substring + "\n" + substring1;
                        mTypeTvNameOne.setText(a);
                        mTypeSumOneTwo.setText(result.get(i).getClassAmount2() + "节");
                        mTypeTvNameTwo.setText(result.get(i).getClass2());
                        mTypeSumThree.setText(result.get(i).getClassAmount3() + "节");
                        mTypeTvNameThree.setText(result.get(i).getClass3());
                        mCommomOne.setText("共" + result.get(i).getClassCount() + "周课程推送\n一次性买到宝宝三岁前所有课程，全程启蒙无忧");
                        mYuanJiaOne.setText("¥"+result.get(i).getPrice());
                    } else if (result.get(i).getCardType() == 2) {
                        mCardNameTwo.setText(result.get(i).getName());
                        mMoneyTwo.setText("¥" + result.get(i).getPromoPrice());
                        mTypeSumOneTwo.setText(result.get(i).getClassAmount1() + "节");
                        String class1 = result.get(i).getClass1();
                        String substring = class1.substring(0, 4);
                        String substring1 = class1.substring(4, class1.length());
                        String a = substring + "\n" + substring1;
                        mTypeTvNameOneTwo.setText(a);
                        mTypeSumTwoTwo.setText(result.get(i).getClassAmount2() + "节");
                        mTypeTvNameTwoTwo.setText(result.get(i).getClass2());
                        mTypeSumThreeTwo.setText(result.get(i).getClassAmount3() + "节");
                        mTypeTvNameThreeTwo.setText(result.get(i).getClass3());
                        mCommomTwo.setText("共" + result.get(i).getClassCount() + "周课程推送");
                        mYuanJiaTwo.setText("¥"+result.get(i).getPrice());
                    } else if (result.get(i).getCardType() == 3) {
                        mCardNameThree.setText(result.get(i).getName());
                        mMoneyThree.setText("¥" + result.get(i).getPromoPrice());
                        mTypeSumOneThree.setText(result.get(i).getClassAmount1() + "节");
                        String class1 = result.get(i).getClass1();
                        String substring = class1.substring(0, 4);
                        String substring1 = class1.substring(4, class1.length());
                        String a = substring + "\n" + substring1;
                        mTypeTvNameOneThree.setText(a);
                        mTypeSumTwoThree.setText(result.get(i).getClassAmount2() + "节");
                        mTypeTvNameTwoThree.setText(result.get(i).getClass2());
                        mTypeSumThreeThree.setText(result.get(i).getClassAmount3() + "节");
                        mTypeTvNameThreeThree.setText(result.get(i).getClass3());
                        mCommomThree.setText("共" + result.get(i).getClassCount() + "周课程推送");
                        mYuanJiaThree.setText("¥"+result.get(i).getPrice());
                    }
                }
                break;
            case STUDYCARDORDERCREATE:
                StudyCardOrderCreateBenas studyCardOrderCreateBenas = (StudyCardOrderCreateBenas) o;
                result1 = studyCardOrderCreateBenas.getResult();
                if (studyCardOrderCreateBenas.getCode().equals("0")) {
                    Intent intent = new Intent(TheFormalCardActivity.this, PayActivity.class);
                    intent.putExtra("biao", cardStudy);
                    intent.putExtra("birthday", s);
                    intent.putExtra("promoPrice", promoPrice);
                    intent.putExtra("cardType", cardType);
                    if (mResultBean != null) {
                        if (mResultBean.getCouponFee() != 0) {
                            intent.putExtra("juan", mResultBean);
                        }
                    }
                    intent.putExtra("have", mResult);
                    intent.putExtra("data", (Serializable) result1);
                    startActivity(intent);
                }

                break;
            case VALIDCOUPONS:
                ValidCouponBeans validCouponBeans = (ValidCouponBeans) o;
                mResult = validCouponBeans.getResult();
                if (mResult > 0) {
                    specia_offer.setText("选择优惠券");
                    specia_offer.setTextColor(Color.parseColor("#333333"));
                } else if (mResult == 0) {
                    specia_offer.setText("无可用优惠券");
                    specia_offer.setTextColor(Color.parseColor("#BBBBBB"));
                    mXuan.setClickable(false);
                }
                break;
        }
    }

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDatePicker.onDestroy();
    }

    /**
     * 改变背景颜色
     */
    private void darkenBackground(Float bgcolor) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgcolor;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setAttributes(lp);

    }

    @OnClick({R.id.iv_finis, R.id.card_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_finis:
                finish();
                break;
            case R.id.card_ok:
                darkenBackground(0.5f);
                popupWindow.showAtLocation(mIvFinis, Gravity.BOTTOM, 0, 0);
                break;
          /*  case R.id.card_ok_one:
                initJump("1");
                PreUtils.putString("ka","1");
                break;
            case R.id.card_ok_two:
                initJump("2");
                PreUtils.putString("ka","2");
                break;
            case R.id.card_ok_three:
                initJump("3");
                PreUtils.putString("ka","3");
                break;*/
        }
    }

    public void initJump(String str) {
        Intent intent = new Intent(TheFormalCardActivity.this, ExclusiveCourseActivity.class);
        intent.putExtra("card", str);
        startActivity(intent);
    }

    private void initDatePicker() {
        long beginTimestamp = DateFormatUtils.str2Long("2009-05-01", false);
        long endTimestamp = System.currentTimeMillis();
        // 通过时间戳初始化日期，毫秒级别
        mDatePicker = new CustomDatePicker(this, new CustomDatePicker.Callback() {
            @Override
            public void onTimeSelected(long timestamp) {
                s = DateFormatUtils.long2Str(timestamp, false);
                Log.d("bjg", "onTimeSelected: " + s);
                String[] split = s.split("-");
                year.setText(split[0]);
                month.setText(split[1]);
                day.setText(split[2]);
            }
        }, beginTimestamp, endTimestamp);
        // 不允许点击屏幕或物理返回键关闭
        mDatePicker.setCancelable(false);
        // 不显示时和分
        mDatePicker.setCanShowPreciseTime(false);
        // 不允许循环滚动
        mDatePicker.setScrollLoop(false);
        // 不允许滚动动画
        mDatePicker.setCanShowAnim(false);
    }

    @Override
    public void onClick(View v) {
        String s = year.getText().toString();
        String s1 = month.getText().toString();
        String s2 = day.getText().toString();
        time = s + "-" + s1 + "-" + s2;
        String nowThree = TimeUtil.getNowThree();
        if (time == null) {
            mDatePicker.show(nowThree);
        } else {
            mDatePicker.show(time);
        }
    }
}
