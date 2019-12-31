package com.tiancaijiazu.app.activitys.offline;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.base.activity.BaseActivity;
import com.tiancaijiazu.app.beans.ToJoinBean;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.ScreenStatusUtil;
import com.tiancaijiazu.app.utils.city.ToastUtils;
import com.tiancaijiazu.app.mvp.IView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

public class BookingInfoActivity extends BaseActivity<IView, Presenter<IView>> implements IView {


    @BindView(R.id.iv_finis)
    ImageView mIvFinis;
    @BindView(R.id.relative)
    RelativeLayout mRelative;
    @BindView(R.id.baby_name)
    EditText mBabyName;
    @BindView(R.id.baby_years)
    EditText mBabyYears;
    @BindView(R.id.baby_days)
    EditText mBabyDays;
    @BindView(R.id.submit)
    TextView mSubmit; @BindView(R.id.a)
    TextView a;
    @BindView(R.id.phone)
    EditText mPhone;

    /**
     * EditText有内容的个数
     */
    private int mEditTextHaveInputCount = 0;
    /**
     * EditText总个数
     */
    private int EDITTEXT_AMOUNT = 3;
    private TextWatcher mTextWatcher;
    private String mCourseId;

    @Override
    protected void initEventAndData() {
        ScreenStatusUtil.setFillDip(this);
        Intent intent = getIntent();
        mCourseId = intent.getStringExtra("courseId");
        initEd();
    }

    private void initEd() {
        mTextWatcher = new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (TextUtils.isEmpty(s)) {
                    mEditTextHaveInputCount++;
                    if (mEditTextHaveInputCount == EDITTEXT_AMOUNT) {
                        mSubmit.setBackgroundResource(R.drawable.bg_submit_order);
                        mSubmit.setEnabled(true);
                        mSubmit.setClickable(true);
                    }
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    mEditTextHaveInputCount--;
                    mSubmit.setBackgroundResource(R.drawable.bg_reserve);
                    mSubmit.setEnabled(false);
                    mSubmit.setClickable(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        mBabyName.addTextChangedListener(mTextWatcher);
        mBabyYears.addTextChangedListener(mTextWatcher);
        mBabyDays.addTextChangedListener(mTextWatcher);
        mPhone.addTextChangedListener(mTextWatcher);
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_booking_info;
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum) {
            case TOJOINBABY:
                ToJoinBean toJoinBean = (ToJoinBean) o;
                String result = toJoinBean.getResult();
                ToastUtils.showShortToast(mActivity, result);
                if("提交成功".equals(result)){
                    Intent intent = new Intent(BookingInfoActivity.this,SucceedActivity.class);
                    startActivity(intent);
                    finish();
                }
                break;
        }
    }

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }


    @OnClick({R.id.iv_finis, R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_finis:
                finish();
                break;
            case R.id.submit:
                String phone = mPhone.getText().toString();
                String telRegex = "^((13[0-9])|(15[^4])|(166)|(17[0-8])|(18[0-9])|(19[8-9])|(147,145))\\d{8}$";
                if (phone.matches(telRegex) && phone.length() == 11) {
                    String babyName = mBabyName.getText().toString();
                    String babyYears = mBabyYears.getText().toString();
                    String babyDays = mBabyDays.getText().toString();
                    HashMap<String, String> map = new HashMap<>();
                    map.put("babyName",babyName);
                    map.put("babyAge",babyYears+"岁"+babyDays+"月");
                    map.put("userMobile",phone);
                    map.put("courseId",mCourseId);
                    mPresenter.getDataP(map,DifferentiateEnum.TOJOINBABY);
                }else {
                    Toast.makeText(mActivity, "手机号输入有误", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
