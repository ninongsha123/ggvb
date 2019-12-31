package com.tiancaijiazu.app.activitys.activitypage;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.adapters.RlvAdapter_bg;
import com.tiancaijiazu.app.base.activity.BaseActivity;
import com.tiancaijiazu.app.beans.ToKenBean;
import com.tiancaijiazu.app.dao.DataBaseMannger;
import com.tiancaijiazu.app.dao.ToKenDaoBean;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.TimeUtil;
import com.tiancaijiazu.app.mvp.IView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class LogInActivity extends BaseActivity<IView,Presenter<IView>> implements IView{


    @BindView(R.id.fast)
    LinearLayout mFast;
    @BindView(R.id.tab_color_one)
    TextView mTabColorOne;
    @BindView(R.id.pass)
    LinearLayout mPass;
    @BindView(R.id.tab_color_two)
    TextView mTabColorTwo;
    @BindView(R.id.edit_phone)
    EditText mEditPhone;
    @BindView(R.id.code_edit)
    EditText mCodeEdit;
    @BindView(R.id.pass_edit)
    EditText mPassEdit;
    @BindView(R.id.code_tv)
    TextView mCodeTv;
    @BindView(R.id.tv_slip)
    TextView mTvSlip;
    @BindView(R.id.login_bt)
    Button mLoginBt;
    @BindView(R.id.rlv)
    RecyclerView mRlv;
    @BindView(R.id.line3)
    LinearLayout mLine3;
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.tv_reg)
    TextView mTvRegister;
    @BindView(R.id.linearLayout)
    LinearLayout mLinearLayout;
    @BindView(R.id.checkbox)
    CheckBox mCheckbox;
    private int size;
    private RlvAdapter_bg mRlvAdapterBg;
    private boolean isbo = true;
    /**
     * EditText有内容的个数
     */
    private int mEditTextHave = 0;
    /**
     * EditText总个数
     */
    private final int EDITTEXT = 2;
    /**
     * EditText有内容的个数
     */
    private int mEditTextHaveInputCount = 0;
    /**
     * EditText总个数
     */
    private int EDITTEXT_AMOUNT = 2;
    private TextWatcher mTextWatcher;

    private void initEdit() {
            mTextWatcher = new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    if (TextUtils.isEmpty(s)) {
                        mEditTextHaveInputCount++;
                        if (mEditTextHaveInputCount == EDITTEXT_AMOUNT) {
                            mLoginBt.setBackgroundResource(R.mipmap.login_yes);
                            mLoginBt.setEnabled(true);
                        }
                    }
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (TextUtils.isEmpty(s)) {
                        mEditTextHaveInputCount--;
                        mLoginBt.setBackgroundResource(R.mipmap.login_no);
                        mLoginBt.setEnabled(false);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            };
            mEditPhone.addTextChangedListener(mTextWatcher);
            mCodeEdit.addTextChangedListener(mTextWatcher);
            mPassEdit.addTextChangedListener(mTextWatcher);
            mCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (isChecked && mEditTextHaveInputCount == 1) {
                        mEditTextHaveInputCount++;
                        mLoginBt.setBackgroundResource(R.mipmap.login_yes);
                        mLoginBt.setEnabled(true);
                    } else if (isChecked) {
                        mEditTextHaveInputCount++;
                    } else {
                        mEditTextHaveInputCount--;
                        mLoginBt.setBackgroundResource(R.mipmap.login_no);
                        mLoginBt.setEnabled(false);
                    }
                }
            });
    }

    private void initRlv() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRlv.setLayoutManager(layoutManager);
        mRlvAdapterBg = new RlvAdapter_bg();
        mRlv.setAdapter(mRlvAdapterBg);

        mEditPhone.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            //当键盘弹出隐藏的时候会 调用此方法。
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                //获取当前界面可视部分
                getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
                //获取屏幕的高度
                int screenHeight = getWindow().getDecorView().getRootView().getHeight();
                //此处就是用来获取键盘的高度的， 在键盘没有弹出的时候 此高度为0 键盘弹出的时候为一个正数
                int heightDifference = screenHeight - r.bottom;
                //Log.d("Keyboard Size", "Size: " + heightDifference);
                if (heightDifference > 200) {
                    int hei = heightDifference - size;
                    mRlvAdapterBg.addUi(hei);
                    mRlv.scrollToPosition(mRlvAdapterBg.getItemCount() - 1);
                    mLine3.setVisibility(View.VISIBLE);
                    mLoginBt.setVisibility(View.VISIBLE);
                    //mEditPhone.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    size = heightDifference;
                    mRlvAdapterBg.addUi(0);
                }
            }
        });
    }

    @Override
    protected void initEventAndData() {
        initSett();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setEditTextHintSize(mEditPhone, "输入手机号码", 16);
        setEditTextHintSize(mCodeEdit, "输入短信验证码", 16);
        setEditTextHintSize(mPassEdit, "输入密码", 16);
        //mPassEdit.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        initRlv();
        initEdit();

    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_log_in;
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

    public static void setEditTextHintSize(EditText editText, String hintText, int size) {
        SpannableString ss = new SpannableString(hintText);//定义hint的值
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(size, true);//设置字体大小 true表示单位是sp
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        editText.setHint(new SpannedString(ss));
    }

    @OnClick({R.id.fast, R.id.pass, R.id.tv_slip, R.id.iv_back, R.id.tv_reg, R.id.code_tv,R.id.login_bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fast:
                mTabColorOne.setVisibility(View.VISIBLE);
                mTabColorTwo.setVisibility(View.GONE);
                mCodeTv.setVisibility(View.VISIBLE);
                mCodeEdit.setVisibility(View.VISIBLE);
                mPassEdit.setVisibility(View.GONE);
                mTvSlip.setVisibility(View.GONE);
                isbo = true;
                //mEditPhone.setText("");
                mPassEdit.setText("");

                break;
            case R.id.pass:
                mTabColorOne.setVisibility(View.GONE);
                mTabColorTwo.setVisibility(View.VISIBLE);
                mCodeTv.setVisibility(View.GONE);
                mCodeEdit.setVisibility(View.GONE);
                mPassEdit.setVisibility(View.VISIBLE);
                mTvSlip.setVisibility(View.VISIBLE);
                isbo = false;
                //isFalse();
                //mEditPhone.setText("");
                mCodeEdit.setText("");

                break;
            case R.id.tv_slip:
                Intent intent = new Intent(this, RetrieveActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_reg:
                Intent intent1 = new Intent(this, RegisterActivity.class);
                startActivity(intent1);
                break;
            case R.id.code_tv:
                /** 倒计时60秒，一次1秒 */
                CountDownTimer timer = new CountDownTimer(60 * 1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        // TODO Auto-generated method stub
                        mCodeTv.setText(millisUntilFinished / 1000 + "s");
                        mCodeTv.setClickable(false);
                    }

                    @Override
                    public void onFinish() {
                        mCodeTv.setText("重新发送");
                        mCodeTv.setClickable(true);
                    }
                }.start();
                break;
            case R.id.login_bt:
                if(isbo){
                    Log.i("yx", "onViewClicked: ");
                    String phone = mEditPhone.getText().toString();
                    String code = mCodeEdit.getText().toString();
                    HashMap<String, String> map = new HashMap<>();
                    map.put("phone",phone);
                    map.put("code",code);
                    map.put("referrerCode",code);
                    mPresenter.getDataP(map,DifferentiateEnum.CODELOGIN);
                }else {

                }
                break;
        }
    }


    @Override
    public void showError(String error) {
        Log.i("yx", "showError: "+error);
    }

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum) {
            case CODELOGIN:
                ToKenBean toKenBean = (ToKenBean) o;
                String code = toKenBean.getCode();
                Log.i("yx", "show: "+code);
                if(code.equals("0")){
                    String nowTime = TimeUtil.getNowTime();
                    List<ToKenDaoBean> select = DataBaseMannger.getIntrance().select();
                    ArrayList<ToKenDaoBean> toKenDaoBeans = new ArrayList<>();
                    if(select.size()==0){
                        Log.i("yx", "show: ");
                        toKenDaoBeans.add(new ToKenDaoBean(null,toKenBean.getResult().getAccess_token(),toKenBean.getResult().getRefresh_token(),nowTime,toKenBean.getResult().getExpires_in()+""));
                        DataBaseMannger.getIntrance().insert(toKenDaoBeans);
                    }
                }
                break;
        }
    }
}
