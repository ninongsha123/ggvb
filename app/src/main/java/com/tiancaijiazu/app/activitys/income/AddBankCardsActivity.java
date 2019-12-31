package com.tiancaijiazu.app.activitys.income;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.income.views.BankCardTextWatcher;
import com.tiancaijiazu.app.base.activity.BaseActivity;
import com.tiancaijiazu.app.beans.BankcardBeans;
import com.tiancaijiazu.app.beans.BankcardInfoBeans;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.mvp.IView;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.pickerview.AddressPickTask;
import com.tiancaijiazu.app.utils.MediumBoldTextViewTitle;
import com.tiancaijiazu.app.utils.ScreenStatusUtil;
import com.tiancaijiazu.app.utils.city.ToastUtils;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.addapp.pickers.entity.City;
import cn.addapp.pickers.entity.County;
import cn.addapp.pickers.entity.Province;

public class AddBankCardsActivity extends BaseActivity<IView, Presenter<IView>> implements IView {


    @BindView(R.id.iv_finis)
    ImageView mIvFinis;
    @BindView(R.id.relative)
    RelativeLayout mRelative;
    @BindView(R.id.ed_bank_cards)
    EditText mEdBankCards;
    @BindView(R.id.a)
    MediumBoldTextViewTitle a;
    @BindView(R.id.issuing_bank)
    EditText mIssuingBank;
    @BindView(R.id.opening_city)
    EditText mOpeningCity;
    @BindView(R.id.opening_branch)
    EditText mOpeningBranch;
    @BindView(R.id.id_card_phone)
    EditText mIdCardPhone;
    @BindView(R.id.id_card_name)
    EditText mIdCardName;
    @BindView(R.id.id_number)
    EditText mIdNumber;
    @BindView(R.id.ok)
    TextView ok;
    @BindView(R.id.imgbank)
    ImageView mImgbank;
    @BindView(R.id.imgcard)
    ImageView mImgcard;
    @BindView(R.id.id_submit)
    TextView mIdSubmit;
    /**
     * EditText有内容的个数
     */
    private int mEditTextHaveInputCount = 0;
    /**
     * EditText总个数
     */
    private int EDITTEXT_AMOUNT = 7;
    private TextWatcher textWatcher;

    @Override
    protected void initEventAndData() {
        Intent intent = getIntent();
        String message = intent.getStringExtra("message");
        if (message.equals("ok")){
            a.setText("添加银行卡");
            ok.setVisibility(View.GONE);
            mIdSubmit.setVisibility(View.VISIBLE);
            mImgbank.setVisibility(View.VISIBLE);
            mImgcard.setVisibility(View.VISIBLE);
            mEdBankCards.setClickable(true);
            mEdBankCards.setEnabled(true);
            mOpeningBranch.setClickable(true);
            mOpeningBranch.setEnabled(true);
            mOpeningCity.setFocusable(true);
            mIssuingBank.setClickable(true);
            mIssuingBank.setEnabled(true);
            mIdNumber.setClickable(true);
            mIdNumber.setEnabled(true);
            mIdCardPhone.setClickable(true);
            mIdCardPhone.setEnabled(true);
            mIdCardName.setClickable(true);
            mIdCardName.setEnabled(true);
        }else {
            a.setText("我的银行卡");
            mIdSubmit.setVisibility(View.GONE);
            ok.setVisibility(View.VISIBLE);
            ToastUtils.showShortToast(AddBankCardsActivity.this, "点击编辑后才可编辑");
        }
        ScreenStatusUtil.setFillDip(this);
        mPresenter.getDataP("", DifferentiateEnum.BANKCARDINFO);
        BankCardTextWatcher.bind(mEdBankCards);
       /* textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (TextUtils.isEmpty(s)) {
                    mEditTextHaveInputCount++;
                    if (mEditTextHaveInputCount == EDITTEXT_AMOUNT) {
                        ok.setText("完成");
                        ok.setEnabled(true);
                        ok.setClickable(true);
                    }
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    mEditTextHaveInputCount--;
                    ok.setText("编辑");
                    ok.setEnabled(false);
                    ok.setClickable(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        mEdBankCards.addTextChangedListener(textWatcher);
        mOpeningBranch.addTextChangedListener(textWatcher);
        mOpeningCity.addTextChangedListener(textWatcher);
        mIssuingBank.addTextChangedListener(textWatcher);
        mIdNumber.addTextChangedListener(textWatcher);
        mIdCardPhone.addTextChangedListener(textWatcher);
        mIdCardName.addTextChangedListener(textWatcher);*/
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = ok.getText().toString();
                if (s.equalsIgnoreCase("编辑")) {
                    ToastUtils.showShortToast(AddBankCardsActivity.this, "请填写或修改银行卡信息");
                    ok.setText("完成");
                    mImgbank.setVisibility(View.VISIBLE);
                    mImgcard.setVisibility(View.VISIBLE);
                    mEdBankCards.setClickable(true);
                    mEdBankCards.setEnabled(true);
                    mOpeningBranch.setClickable(true);
                    mOpeningBranch.setEnabled(true);
                    mOpeningCity.setClickable(true);
                    mOpeningCity.setEnabled(true);
                    mIssuingBank.setClickable(true);
                    mIssuingBank.setEnabled(true);
                    mIdNumber.setClickable(true);
                    mIdNumber.setEnabled(true);
                    mIdCardPhone.setClickable(true);
                    mIdCardPhone.setEnabled(true);
                    mIdCardName.setClickable(true);
                    mIdCardName.setEnabled(true);
                } else if (s.equalsIgnoreCase("完成")) {
                    ok.setText("编辑");
                    mImgbank.setVisibility(View.GONE);
                    mImgcard.setVisibility(View.GONE);
                    mEdBankCards.setClickable(false);
                    mEdBankCards.setEnabled(false);
                    mOpeningBranch.setClickable(false);
                    mOpeningBranch.setEnabled(false);
                    mIssuingBank.setClickable(false);
                    mIssuingBank.setEnabled(false);
                    mIdNumber.setClickable(false);
                    mIdNumber.setEnabled(false);
                    mIdCardPhone.setClickable(false);
                    mIdCardPhone.setEnabled(false);
                    mIdCardName.setClickable(false);
                    mIdCardName.setEnabled(false);
                    String name = mIdCardName.getText().toString();
                    String mobile = mIdCardPhone.getText().toString();
                    String idNo = mIdNumber.getText().toString();
                    String bank = mIssuingBank.getText().toString();
                    String cardNo = mEdBankCards.getText().toString();
                    String city = mOpeningCity.getText().toString();
                    String openingBank = mOpeningBranch.getText().toString();
                    HashMap<String, String> stringStringHashMap = new HashMap<>();
                    stringStringHashMap.put("cardNo", cardNo);
                    stringStringHashMap.put("bank", bank);
                    stringStringHashMap.put("city", city);
                    stringStringHashMap.put("openingBank", openingBank);
                    stringStringHashMap.put("mobile", mobile);
                    stringStringHashMap.put("name", name);
                    stringStringHashMap.put("idNo", idNo);
                    mPresenter.getDataP(stringStringHashMap, DifferentiateEnum.BANKCARDMODIFY);
                }
            }
        });
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_add_bank_cards;
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum) {
            case BANKCARDMODIFY:
                BankcardBeans bankcardBeans = (BankcardBeans) o;
                String result = bankcardBeans.getResult();
                if (result.equalsIgnoreCase("提交成功")) {
                    ToastUtils.showShortToast(AddBankCardsActivity.this, "提交成功");
                    finish();
                } else {
                    ToastUtils.showShortToast(AddBankCardsActivity.this, bankcardBeans.getMsg());
                }
                break;
            case BANKCARDINFO:
                BankcardInfoBeans bankcardInfoBeans = (BankcardInfoBeans) o;
                BankcardInfoBeans.ResultBean result1 = bankcardInfoBeans.getResult();
                if (result1 != null) {
                    mEdBankCards.setText(result1.getCardNo());
                    mIdNumber.setText(result1.getIdNo());
                    mIdCardPhone.setText(result1.getMobile());
                    mIdCardName.setText(result1.getName());
                    mOpeningBranch.setText(result1.getOpeningBank());
                    mOpeningCity.setText(result1.getCity());
                    mIssuingBank.setText(result1.getBank());
                }
                break;
        }
    }

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }

    @OnClick({R.id.iv_finis,R.id.id_submit,R.id.opening_city})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_finis:
                finish();
                break;
            case R.id.id_submit:
                String name = mIdCardName.getText().toString();
                String mobile = mIdCardPhone.getText().toString();
                String idNo = mIdNumber.getText().toString();
                String bank = mIssuingBank.getText().toString();
                String cardNo = mEdBankCards.getText().toString();
                String city = mOpeningCity.getText().toString();
                String openingBank = mOpeningBranch.getText().toString();
                HashMap<String, String> stringStringHashMap = new HashMap<>();
                stringStringHashMap.put("cardNo", cardNo);
                stringStringHashMap.put("bank", bank);
                stringStringHashMap.put("city", city);
                stringStringHashMap.put("openingBank", openingBank);
                stringStringHashMap.put("mobile", mobile);
                stringStringHashMap.put("name", name);
                stringStringHashMap.put("idNo", idNo);
                mPresenter.getDataP(stringStringHashMap, DifferentiateEnum.BANKCARDMODIFY);
                if (!name.equals("") && !mobile.equals("") && !idNo.equals("") && !bank.equals("") && !cardNo.equals("") && !city.equals("") && !openingBank.equals("")) {
                    Intent intent = getIntent();
                    String substring = cardNo.substring(cardNo.length() - 5, cardNo.length());
                    String s3 = substring.replaceAll(" ", "");
                    String s2 = mIssuingBank.getText().toString();
                    intent.putExtra("card", s3);
                    intent.putExtra("bank", s2);
                    setResult(200, intent);
                }
                break;
            case R.id.opening_city:
                onAddressPicker();
                break;
        }
    }

    public void onAddressPicker() {
        AddressPickTask task = new AddressPickTask(this);
        task.setHideProvince(false);
        task.setHideCounty(true);
        task.setCallback(new AddressPickTask.Callback() {
            @Override
            public void onAddressInitFailed() {
                Toast.makeText(mActivity, "数据初始化失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAddressPicked(Province province, City city, County county) {
                Toast.makeText(mActivity, province.getAreaName() + city.getAreaName(), Toast.LENGTH_SHORT).show();
                mOpeningCity.setText(province.getAreaName() + "-" + city.getAreaName());
                mOpeningCity.setTextColor(Color.parseColor("#333333"));
            }
        });
        task.execute("北京市", "北京市");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }
}
