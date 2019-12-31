package com.tiancaijiazu.app.activitys.diy;

import android.content.Intent;
import android.graphics.Paint;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.shopping_activity.PayActivity;
import com.tiancaijiazu.app.base.activity.BaseActivity;
import com.tiancaijiazu.app.beans.CardTypeBean;
import com.tiancaijiazu.app.beans.StudyCardOrderCreateBenas;
import com.tiancaijiazu.app.beans.UserCardTypeBean;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.PreUtils;
import com.tiancaijiazu.app.utils.ScreenStatusUtil;
import com.tiancaijiazu.app.utils.TimeUtil;
import com.tiancaijiazu.app.mvp.IView;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class RenewActivity extends BaseActivity<IView, Presenter<IView>> implements IView {


    @BindView(R.id.iv_finis)
    ImageView mIvFinis;
    @BindView(R.id.title_name)
    TextView mTitleName;
    @BindView(R.id.day)
    TextView mDay;
    @BindView(R.id.hour)
    TextView mHour;
    @BindView(R.id.minute)
    TextView mMinute;
    @BindView(R.id.second)
    TextView mSecond;
    @BindView(R.id.card_name_two)
    TextView mCardNameTwo;
    @BindView(R.id.money_two)
    TextView mMoneyTwo;
    @BindView(R.id.commom_two)
    TextView mCommomTwo;
    @BindView(R.id.card_name_three)
    TextView mCardNameThree;
    @BindView(R.id.money_three)
    TextView mMoneyThree;
    @BindView(R.id.commom_three)
    TextView mCommomThree;
    @BindView(R.id.line_one)
    LinearLayout mLineOne;
    @BindView(R.id.line_two)
    LinearLayout mLineTwo;
    @BindView(R.id.yuan_jia_one)
    TextView mYuanJiaOne;
    @BindView(R.id.xu_one)
    TextView mXuOne;
    @BindView(R.id.yuan_jia_two)
    TextView mYuanJiaTwo;
    @BindView(R.id.xu_two)
    TextView mXuTwo;

    private CountDownTimer mTimer;
    private String babyBirthday;
    private int cardType_two = 3;
    private int cardType_one = 2;
    private StudyCardOrderCreateBenas.ResultBean result1;
    private String cardRenew = "cardRenew";
    private List<CardTypeBean.ResultBean> result;
    private double promoPrice;
    private double promoPrice1;

    @Override
    protected void initEventAndData() {
        ScreenStatusUtil.setFillDip(this);
        //添加横线
        mYuanJiaOne.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);
        //添加横线
        mYuanJiaTwo.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);
        mPresenter.getDataP("", DifferentiateEnum.CARDTYPEBEAN);
        mPresenter.getDataP("", DifferentiateEnum.USERCARDTYPE);
        PreUtils.putString("renew", "no");
    }

    protected void onNewIntent(Intent intent) {
        String cards = PreUtils.getString("renew", "");
        if ("ok".equals(cards)) {
            //添加横线
            mYuanJiaOne.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);
            //添加横线
            mYuanJiaTwo.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);
            mPresenter.getDataP("", DifferentiateEnum.CARDTYPEBEAN);
            mPresenter.getDataP("", DifferentiateEnum.USERCARDTYPE);
            PreUtils.putString("renew", "no");
        }
        super.onNewIntent(intent);
        setIntent(intent);
        //here we can use getIntent() to get the extra data.

    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_renew;
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

                    } else if (result.get(i).getCardType() == 2) {//年卡
                        mCardNameTwo.setText(result.get(i).getName());
                        mCommomTwo.setText("共" + result.get(i).getClassCount() + "周课程推送");
                        promoPrice = result.get(i).getPromoPrice();
                        mMoneyTwo.setText("¥" + promoPrice);
                        mYuanJiaOne.setText("¥"+result.get(i).getPrice());
                    } else if (result.get(i).getCardType() == 3) {//半年卡
                        mCardNameThree.setText(result.get(i).getName());
                        mCommomThree.setText("共" + result.get(i).getClassCount() + "周课程推送");
                        promoPrice1 = result.get(i).getPromoPrice();
                        mMoneyThree.setText("¥" + promoPrice1);
                        mYuanJiaTwo.setText("¥"+result.get(i).getPrice());
                    }
                }
                break;
            case STUDYCARDORDERCREATE:
                StudyCardOrderCreateBenas studyCardOrderCreateBenas = (StudyCardOrderCreateBenas) o;
                result1 = studyCardOrderCreateBenas.getResult();
                if (studyCardOrderCreateBenas.getCode().equalsIgnoreCase("0")) {
                    Intent intent = new Intent(RenewActivity.this, PayActivity.class);
                    intent.putExtra("biao", cardRenew);
                    intent.putExtra("birthday", babyBirthday);
                    intent.putExtra("data", (Serializable) result1);
                    intent.putExtra("promoPrice", promoPrice);
                    intent.putExtra("promoPrice1", promoPrice1);
                    startActivity(intent);
                }
                break;
            case USERCARDTYPE:
                UserCardTypeBean userCardTypeBean = (UserCardTypeBean) o;
                UserCardTypeBean.ResultBean result = userCardTypeBean.getResult();
                String time = result.getExpiresIn();
                babyBirthday = result.getBabyBirthday();
                String nowTime = TimeUtil.getNowTime();

                long l = TimeUtil.dataOne(time);
                long l1 = TimeUtil.dataOne(nowTime);
                long l2 = l - l1;
                // TODO Auto-generated method stub
                mTimer = new CountDownTimer(l2, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        // TODO Auto-generated method stub
                        //String dateToString1 = TimeUtil.getDateToString(millisUntilFinished);
                        if (mDay != null && mHour != null && mMinute != null && mSecond != null) {
                            String day = TimeUtil.getTimeDay(millisUntilFinished);
                            mDay.setText(day);
                            String timeHour = TimeUtil.getTimeHour(millisUntilFinished);
                            mHour.setText(timeHour);
                            String timeMin = TimeUtil.getTimeMin(millisUntilFinished);
                            mMinute.setText(timeMin);
                            String s = TimeUtil.getTimeS(millisUntilFinished);
                            mSecond.setText(s);
                        }

                    }

                    @Override
                    public void onFinish() {

                    }
                }.start();
                break;
        }
    }

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }

    @Override
    protected void onRestart() {
        String cards = PreUtils.getString("renew", "");
        if ("ok".equals(cards)) {
        } else {
            initEventAndData();
        }
        super.onRestart();
    }

    @Override
    protected void onStop() {
        mTimer.onFinish();
        mTimer.cancel();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTimer.onFinish();
        mTimer.cancel();
    }


    @OnClick({R.id.iv_finis, R.id.xu_one, R.id.xu_two})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_finis:
                finish();
                break;
            case R.id.xu_one:
                //创建家庭早教学习卡订单
                HashMap<String, Object> map = new HashMap<>();
                map.put("babyBirthday", babyBirthday);
                map.put("cardType", cardType_one);
                mPresenter.getDataP(map, DifferentiateEnum.STUDYCARDORDERCREATE);
                break;
            case R.id.xu_two:
                //创建家庭早教学习卡订单
                HashMap<String, Object> map1 = new HashMap<>();
                map1.put("babyBirthday", babyBirthday);
                map1.put("cardType", cardType_two);
                mPresenter.getDataP(map1, DifferentiateEnum.STUDYCARDORDERCREATE);
                break;
        }
    }
}
