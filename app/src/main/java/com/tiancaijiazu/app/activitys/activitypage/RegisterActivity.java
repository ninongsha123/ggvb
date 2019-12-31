package com.tiancaijiazu.app.activitys.activitypage;

import android.graphics.Rect;
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
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.adapters.RlvAdapter_bg;
import com.tiancaijiazu.app.base.activity.BaseActivity;
import com.tiancaijiazu.app.beans.AuthCode;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.ScreenStatusUtil;
import com.tiancaijiazu.app.mvp.IView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity<IView, Presenter<IView>> implements IView {


    @BindView(R.id.rlv)
    RecyclerView mRlv;
    @BindView(R.id.code_edit)
    EditText mCodeEdit;
    @BindView(R.id.code_tv)
    TextView mCodeTv;
    @BindView(R.id.pass_edit)
    EditText mPassEdit;
    @BindView(R.id.line3_z)
    LinearLayout mLine3;
    @BindView(R.id.login_z)
    Button mLogin;
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.edit_phone)
    EditText mEditPhone;
    @BindView(R.id.checkbox)
    CheckBox mCheckbox;
    private RlvAdapter_bg mRlvAdapterBg;
    private int size;
    private TextWatcher mTextWatcher;
    /**
     * EditText有内容的个数
     */
    private int mEditTextHaveInputCount = 0;
    /**
     * EditText总个数
     */
    private int EDITTEXT_AMOUNT = 3;

    @Override
    protected void initEventAndData() {
        setEditTextHintSize(mEditPhone, "输入手机号码", 16);
        setEditTextHintSize(mCodeEdit, "输入短信验证码", 16);
        setEditTextHintSize(mPassEdit, "请设置登录密码(至少6个字符)", 16);
        ScreenStatusUtil.setFillDip(this);
        initRlv();
        initEdit();
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_register;
    }

    private void initEdit() {
        mTextWatcher = new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (TextUtils.isEmpty(s)) {
                    mEditTextHaveInputCount++;
                    if (mEditTextHaveInputCount == EDITTEXT_AMOUNT) {
                        mLogin.setBackgroundResource(R.mipmap.login_yes);
                        mLogin.setEnabled(true);
                    }
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    mEditTextHaveInputCount--;
                    mLogin.setBackgroundResource(R.mipmap.login_no);
                    mLogin.setEnabled(false);
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

                if (isChecked && mEditTextHaveInputCount == 2) {
                    mEditTextHaveInputCount++;
                    mLogin.setBackgroundResource(R.mipmap.login_yes);
                    mLogin.setEnabled(true);
                } else if (isChecked) {
                    mEditTextHaveInputCount++;
                } else {
                    mEditTextHaveInputCount--;
                    mLogin.setBackgroundResource(R.mipmap.login_no);
                    mLogin.setEnabled(false);
                }
            }
        });

    }

    private void initRlv() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRlv.setLayoutManager(layoutManager);
        mRlvAdapterBg = new RlvAdapter_bg();
        mRlv.setAdapter(mRlvAdapterBg);

        mCodeEdit.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

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
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mLogin.getLayoutParams();
                    layoutParams.bottomMargin = hei;//将默认的距离底部20dp，改为0
                    mLogin.setLayoutParams(layoutParams);
                    RelativeLayout.LayoutParams layoutParams1 = (RelativeLayout.LayoutParams) mLine3.getLayoutParams();
                    int i = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 55, getResources().getDisplayMetrics());
                    layoutParams1.bottomMargin = hei + i;//将默认的距离底部20dp，改为0
                    mLine3.setLayoutParams(layoutParams1);
                    mRlvAdapterBg.addUi(hei);
                    mRlv.scrollToPosition(mRlvAdapterBg.getItemCount() - 1);
                    mLogin.setVisibility(View.VISIBLE);
//                    mEditPhone.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    size = heightDifference;
                    mRlvAdapterBg.addUi(0);
                    int i = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 45, getResources().getDisplayMetrics());
                    //Log.i("yx", "onGlobalLayout: "+i);
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mLogin.getLayoutParams();
                    layoutParams.bottomMargin = i;//将默认的距离底部20dp，改为0，
                    mLogin.setLayoutParams(layoutParams);
                    RelativeLayout.LayoutParams layoutParams1 = (RelativeLayout.LayoutParams) mLine3.getLayoutParams();
                    int i1 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 110, getResources().getDisplayMetrics());
                    layoutParams1.bottomMargin = i1;//将默认的距离底部20dp，改为0，
                    mLine3.setLayoutParams(layoutParams1);
                    mLogin.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void setEditTextHintSize(EditText editText, String hintText, int size) {
        SpannableString ss = new SpannableString(hintText);//定义hint的值
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(size, true);//设置字体大小 true表示单位是sp
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        editText.setHint(new SpannedString(ss));
    }


    @OnClick({R.id.iv_back, R.id.code_tv,R.id.login_z})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.code_tv:
                String s = mEditPhone.getText().toString();
                // "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
                String telRegex = "^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))\\d{8}$";
                if(s.matches(telRegex)&&s.length()==11){
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
                    mPresenter.getDataP(s,DifferentiateEnum.CODE);
                }else {
                    Toast.makeText(mActivity, "您输入的手机号有误！请重新输入！", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.login_z:
                String phone = mEditPhone.getText().toString();
                String code = mCodeEdit.getText().toString();
                String pass = mPassEdit.getText().toString();
                Map<String, Object> map = new HashMap<>();
                map.put("phone",phone);
                map.put("code",code);
                map.put("pass",pass);
                mPresenter.getDataP(map,DifferentiateEnum.REGISTER);
                break;
        }
    }



    @Override
    public void showError(String error) {

    }

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum) {
            case CODE:
                AuthCode authCode = (AuthCode) o;
                Toast.makeText(mActivity, ""+authCode.getResult(), Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
