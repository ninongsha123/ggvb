package com.tiancaijiazu.app.activitys.apply_for;

import android.graphics.Color;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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

/**
 *
 * 首页-线下课堂-顶部加盟天才家族联盟园
 */
public class ApplyForActivity extends BaseActivity<IView, Presenter<IView>> implements IView {


    @BindView(R.id.iv_finis)
    ImageView ivFinis;
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.namevalue)
    EditText namevalue;
    @BindView(R.id.phonevalue)
    EditText phonevalue;
    @BindView(R.id.dizhivalue)
    EditText dizhivalue;
    @BindView(R.id.jieshovalue)
    EditText jieshovalue;
    @BindView(R.id.data_sum)
    TextView dataSum;
    @BindView(R.id.comit)
    TextView comit;
    /**
     * EditText有内容的个数
     */
    private int mEditTextHaveInputCount = 0;
    /**
     * EditText总个数
     */
    private int EDITTEXT_AMOUNT = 4;
    private TextWatcher mTextWatcher;

    @Override
    protected void initEventAndData() {
        ScreenStatusUtil.setFillDip(this);
        initFlow();
    }


    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_apply_for;
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum) {
            case TOJOIN:
                ToJoinBean toJoinBean = (ToJoinBean) o;
                String result = toJoinBean.getResult();
                //Toast.makeText(mActivity, result, Toast.LENGTH_SHORT).show();
                ToastUtils.showShortToast(mActivity,result);
                if("提交成功".equals(result)){
                    finish();
                }
                break;
        }
    }

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }



    private void initFlow() {
        mTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (TextUtils.isEmpty(s)) {
                    mEditTextHaveInputCount++;
                    if (mEditTextHaveInputCount == EDITTEXT_AMOUNT) {
                        comit.setTextColor(Color.parseColor("#333333"));
                        comit.setEnabled(true);
                        comit.setClickable(true);
                    }
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    mEditTextHaveInputCount--;
                    comit.setTextColor(Color.parseColor("#999999"));
                    comit.setEnabled(false);
                    comit.setClickable(false);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        namevalue.addTextChangedListener(mTextWatcher);
        phonevalue.addTextChangedListener(mTextWatcher);
        dizhivalue.addTextChangedListener(mTextWatcher);
        jieshovalue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (TextUtils.isEmpty(charSequence)) {
                    mEditTextHaveInputCount++;
                    if (mEditTextHaveInputCount == EDITTEXT_AMOUNT) {
                        comit.setTextColor(Color.parseColor("#333333"));
                        comit.setEnabled(true);
                        comit.setClickable(true);
                    }
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (TextUtils.isEmpty(charSequence)) {
                    mEditTextHaveInputCount--;
                    comit.setTextColor(Color.parseColor("#999999"));
                    comit.setEnabled(false);
                    comit.setClickable(false);
                }
                if (charSequence.length() > 500) {
                    jieshovalue.setText(charSequence.toString().substring(0, 500));
                    jieshovalue.setSelection(500);
                    dataSum.setText(500 + "");
                } else {
                    dataSum.setText(charSequence.length() + "/" + 500 + "字");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }


    @OnClick({R.id.iv_finis, R.id.comit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_finis:
                finish();
                break;
            case R.id.comit:
                String phone = phonevalue.getText().toString();
                String telRegex = "^((13[0-9])|(15[^4])|(166)|(17[0-8])|(18[0-9])|(19[8-9])|(147,145))\\d{8}$";
                if (phone.matches(telRegex) && phone.length() == 11) {
                    String name = namevalue.getText().toString();
                    String dizhi = dizhivalue.getText().toString();
                    String jieshao = jieshovalue.getText().toString();
                    HashMap<String, String> map = new HashMap<>();
                    map.put("userName",name);
                    map.put("userMobile",phone);
                    map.put("address",dizhi);
                    map.put("summary",jieshao);
                    mPresenter.getDataP(map,DifferentiateEnum.TOJOIN);
                } else {
                    //Toast.makeText(mActivity, "输入手机号有误", Toast.LENGTH_SHORT).show();
                    ToastUtils.showShortToast(mActivity, "输入手机号有误");
                }
                break;
        }
    }
}

