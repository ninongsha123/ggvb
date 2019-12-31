package com.tiancaijiazu.app.activitys.activitypage.loginpages;


import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.activitypage.LordActivity;
import com.tiancaijiazu.app.activitys.qi_activitys.BaoActivity;
import com.tiancaijiazu.app.base.activity.BaseActivity;
import com.tiancaijiazu.app.beans.AuthCode;
import com.tiancaijiazu.app.beans.BabyMessageBean;
import com.tiancaijiazu.app.beans.StrBean;
import com.tiancaijiazu.app.beans.ToKenBean;
import com.tiancaijiazu.app.beans.WXbindBean;
import com.tiancaijiazu.app.dao.DataBaseMannger;
import com.tiancaijiazu.app.dao.ToKenDaoBean;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.ScreenStatusUtil;
import com.tiancaijiazu.app.utils.TimeUtil;
import com.tiancaijiazu.app.utils.city.ToastUtils;
import com.tiancaijiazu.app.mvp.IView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *  登录-手机号
 *
 */

public class LoginPhoneActivity extends BaseActivity<IView, Presenter<IView>> implements IView {


    @BindView(R.id.iv_finis)
    ImageView mIvFinis;
    @BindView(R.id.biao)
    TextView mBiao;
    @BindView(R.id.code)
    TextView code;
    @BindView(R.id.state)
    RelativeLayout mState;
    @BindView(R.id.edit_phone)
    EditText mEditPhone;
    @BindView(R.id.edit_code)
    EditText mEditCode;
    @BindView(R.id.edit_generalize_code)
    EditText getmEditPhone;
    @BindView(R.id.fa_code)
    TextView mFaCode;
    @BindView(R.id.tv_ok)
    TextView mTvOk;
    @BindView(R.id.countryNumber)
    TextView mCountryNumber;
    @BindView(R.id.nation)
    TextView mNation;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.title_fu)
    TextView mTitleFu;
    private Intent mIntent;
    private String a;
    private CountDownTimer mTimer;
    private String mWxOpenid;
    private String mTitle1;
    private TextView mTvOne;
    private TextView mTvTwo;
    private PopupWindow mPopupWindow;
    private View mInflate;


    @Override
    protected void initEventAndData() {
        ScreenStatusUtil.setFillDip(this);
        mIntent = getIntent();
        a = mIntent.getStringExtra("login");
        mTitle1 = mIntent.getStringExtra("title");
        if (a != null) {
            mTitle.setText("手机号登录");
            mTitleFu.setText("");
        }
        if (mTitle1 != null) {
            mTitle.setText(mTitle1);
            mTitle.setVisibility(View.VISIBLE);
        }
        mWxOpenid = mIntent.getStringExtra("wxOpenid");
        initPop();
    }

    private void initPop() {
        mInflate = LayoutInflater.from(this).inflate(R.layout.referral_code_layout, null);
        mPopupWindow = new PopupWindow(mInflate,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        mPopupWindow.setFocusable(true);// 取得焦点
        mPopupWindow.setClippingEnabled(false);
        //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        //点击外部消失
        mPopupWindow.setOutsideTouchable(true);
        //设置可以点击
        mPopupWindow.setTouchable(true);
        //进入退出的动画，指定刚才定义的style
        mPopupWindow.setAnimationStyle(R.style.popwin_anim_style);

        mTvOne = mInflate.findViewById(R.id.tv_one);
        mTvTwo = mInflate.findViewById(R.id.tv_two);
        TextView ok = mInflate.findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginPhoneActivity.this, GetCodeActivity.class));
                mPopupWindow.dismiss();
            }
        });
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_login_phone;
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum) {
            case CODE:
                AuthCode authCode = (AuthCode) o;
                String result = authCode.getResult();
                //Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
                if("1013".equals(authCode.getCode())){
                    mTvOne.setText("新用户必须填写推荐码，点击下方(新用户如何获取推荐码?)按钮，查看获取推荐码方法");
                    mTvTwo.setText("填写推荐码后重新点击(发送验证码)按钮，获取验证码");
                    mPopupWindow.showAtLocation(mInflate, Gravity.CENTER,0,0);
                    //ToastUtils.showShortToast(this, authCode.getMsg());
                }else if("1014".equals(authCode.getCode())){
                    mTvOne.setText("错误的推荐码，请填写正确的推荐码");
                    mTvTwo.setText("点击下方(新用户如何获取推荐码?)按钮，查看获取推荐码方法");
                    mPopupWindow.showAtLocation(mInflate, Gravity.CENTER,0,0);
                }else {
                    /** 倒计时60秒，一次1秒 */
                    // TODO Auto-generated method stub
                    mTimer = new CountDownTimer(60 * 1000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            // TODO Auto-generated method stub
                            if(mFaCode!=null){
                                mFaCode.setText(millisUntilFinished / 1000 + "s");
                                mFaCode.setClickable(false);
                            }

                        }

                        @Override
                        public void onFinish() {
                            if(mFaCode!=null){
                                mFaCode.setText("重新发送");
                                mFaCode.setClickable(true);
                            }

                        }
                    }.start();
                    ToastUtils.showShortToast(this, result);
                }
                break;
            case CODELOGIN:
                String str = (String) o;
                Gson gson = new Gson();
                StrBean strBean = gson.fromJson(str, StrBean.class);
                String code = strBean.getCode();
                if ("0".equals(code)) {
                    ToKenBean toKenBean = gson.fromJson(str, ToKenBean.class);
                    Log.i("yx", "show: " + toKenBean.getCode());
                    if (toKenBean.getCode().equals("0")) {
                        DataBaseMannger.getIntrance().deleteAll();
                        ArrayList<ToKenDaoBean> toKenDaoBeans = new ArrayList<>();
                        String nowTime = TimeUtil.getNowTime();
                        toKenDaoBeans.add(new ToKenDaoBean(null, toKenBean.getResult().getAccess_token(), toKenBean.getResult().getRefresh_token(), nowTime, toKenBean.getResult().getExpires_in() + ""));
                        DataBaseMannger.getIntrance().insert(toKenDaoBeans);
                        if (a != null) {
                            if (a.equals("login")) {
                                setResult(12, mIntent);
                                if (mTimer != null) {
                                    mTimer.cancel();
                                    mTimer.onFinish();
                                }
                                finish();
                            } else if (a.equals("one")) {
                                if (mTimer != null) {
                                    mTimer.cancel();
                                    mTimer.onFinish();
                                }
                                mPresenter.getDataP("", DifferentiateEnum.BABYMESSAGELIST);
                            }
                        }
                    }
                } else {
                    //Toast.makeText(mActivity, strBean.getMsg(), Toast.LENGTH_SHORT).show();
                    ToastUtils.showShortToast(mActivity, strBean.getMsg());
                }

                break;
            case WXBINDING:
                WXbindBean wXbindBean = (WXbindBean) o;
                String access_token = wXbindBean.getResult().getAccess_token();
                int expires_in = wXbindBean.getResult().getExpires_in();
                String refresh_token = wXbindBean.getResult().getRefresh_token();
                DataBaseMannger.getIntrance().deleteAll();
                ArrayList<ToKenDaoBean> toKenDaoBeans = new ArrayList<>();
                String nowTime = TimeUtil.getNowTime();
                toKenDaoBeans.add(new ToKenDaoBean(null, access_token, refresh_token, nowTime, expires_in + ""));
                DataBaseMannger.getIntrance().insert(toKenDaoBeans);
                Intent intent = new Intent(this, BaoActivity.class);
                startActivity(intent);
                finish();
                break;
            case BABYMESSAGELIST:
                BabyMessageBean babyMessageBean = (BabyMessageBean) o;
                List<BabyMessageBean.ResultBean> result1 = babyMessageBean.getResult();
                if (result1.size() != 0) {
                    Intent intent1 = new Intent(this, LordActivity.class);
                    startActivity(intent1);
                    finish();
                } else {
                    Intent intent1 = new Intent(this, BaoActivity.class);
                    startActivity(intent1);
                    finish();
                }
                break;
        }

    }

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }

    @Override
    public void onBackPressed() {
        if (a.equals("login")) {
            setResult(12, mIntent);
            finish();
        }
        super.onBackPressed();
    }


    @OnClick({R.id.iv_finis, R.id.state, R.id.fa_code, R.id.tv_ok, R.id.code})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_finis:
                finish();
                break;
            case R.id.code:
                startActivity(new Intent(LoginPhoneActivity.this, GetCodeActivity.class));
                break;
            case R.id.state:
                if (TimeUtil.isInvalidClick(mState, 300))
                    return;
                Intent intent1 = new Intent(this, CountryActivity.class);
                startActivityForResult(intent1, 110);
                break;
            case R.id.fa_code:
                if (TimeUtil.isInvalidClick(mFaCode, 300))
                    return;
                String s2 = mEditPhone.getText().toString();
                // "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
                String telRegex = "^((13[0-9])|(15[^4])|(166)|(17[0-8])|(18[0-9])|(19[8-9])|(147,145))\\d{8}$";
                if (s2.matches(telRegex) && s2.length() == 11) {

                    HashMap<String, String> map = new HashMap<>();
                    map.put("phone",s2);
                    map.put("referrerCode",getmEditPhone.getText().toString());
                    mPresenter.getDataP(map, DifferentiateEnum.CODE);
                } else {
                    Toast.makeText(mActivity, "您输入的手机号有误！请重新输入！", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_ok:
                if (TimeUtil.isInvalidClick(mTvOk, 300))
                    return;
                String s = mEditPhone.getText().toString();
                String s1 = mEditCode.getText().toString();
                String s3 = getmEditPhone.getText().toString();
                if (s.length() == 0 || s1.length() == 0) {
                    Toast.makeText(mActivity, "请输入手机号或验证码！", Toast.LENGTH_SHORT).show();
                } else {
                    if (mTitle1 != null) {
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("phone", s);
                        map.put("code", s1);
                        if (s3.length() == 0) {
                            map.put("referrerCode", 0);
                        } else {
                            map.put("referrerCode", Integer.parseInt(s3));
                        }
                        mPresenter.getDataP(map, DifferentiateEnum.CODELOGIN);
                    } else if (a != null) {
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("phone", s);
                        map.put("code", s1);
                        if (s3.length() == 0) {
                            map.put("referrerCode", 0);
                        } else {
                            map.put("referrerCode", Integer.parseInt(s3));
                        }
                        mPresenter.getDataP(map, DifferentiateEnum.CODELOGIN);
                    } else {
                        if (mWxOpenid != null) {
                            HashMap<String, String> map = new HashMap<>();
                            map.put("wxOpenid", mWxOpenid);
                            map.put("phone", s);
                            map.put("code", s1);
                            mPresenter.getDataP(map, DifferentiateEnum.WXBINDING);
                        }
                    }
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 110 && resultCode == 120) {
            String countryName = data.getStringExtra("countryName");
            String countryNumber = data.getStringExtra("countryNumber");
            mCountryNumber.setText(countryNumber);
            mNation.setText(countryName);
        }
    }

}
