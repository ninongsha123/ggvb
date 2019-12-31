package com.tiancaijiazu.app.activitys.income;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.activitypage.BenefitsThatActivity;
import com.tiancaijiazu.app.activitys.activitypage.collegeactivitys.ClassListActivity;
import com.tiancaijiazu.app.base.activity.BaseActivity;
import com.tiancaijiazu.app.beans.AmountBean;
import com.tiancaijiazu.app.beans.BankcardInfoBeans;
import com.tiancaijiazu.app.beans.CashoutBean;
import com.tiancaijiazu.app.beans.UpgradepromotionBean;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.mvp.IView;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.MediumBoldTextViewStandard;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TiInComeActivity extends BaseActivity<IView, Presenter<IView>> implements IView {

    @BindView(R.id.iv_finis)
    ImageView mIvFinis;
    @BindView(R.id.jias)
    ImageView mJias;
    @BindView(R.id.qingchu)
    ImageView mQingchu;
    @BindView(R.id.add_cards)
    RelativeLayout mAddCarsd;
    @BindView(R.id.a)
    MediumBoldTextViewStandard mA;
    @BindView(R.id.relative)
    RelativeLayout mRelative;
    @BindView(R.id.add_card)
    TextView mAddCard;
    @BindView(R.id.qian_tu)
    TextView mQian;
    @BindView(R.id.cardss)
    TextView mCards;
    @BindView(R.id.banks)
    TextView mBanks;
    @BindView(R.id.jin_e)
    EditText mJinE;
    @BindView(R.id.yue)
    TextView mYue;
    @BindView(R.id.quanti)
    TextView mQuanti;
    @BindView(R.id.ti_xians)
    TextView mTiXian;
    private Intent mIntent;
    private String mCard;
    private String mBank;
    private View mInflate;
    private PopupWindow mPopupWindow1;
    private double mResult;
    private String mMsg;
    private long mCardId;
    private Spanned mSpanned;
    private String mSubstring;
    private String mBank1;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void initEventAndData() {
         mSpanned = Html.fromHtml("&yen;", Html.FROM_HTML_MODE_LEGACY);
         mQian.setText(mSpanned);
        mPresenter.getDataP("", DifferentiateEnum.AMOUNT);
        mPresenter.getDataP("",DifferentiateEnum.BANKCARDINFO);
        mJinE.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        mIntent = getIntent();
        String jin_e = mIntent.getStringExtra("jin_e");

        mJinE.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //输入内容变化监听
                String s = mJinE.getText().toString();
                if ("".equals(s)){
                    mQingchu.setVisibility(View.GONE);
                    return;
                }else {
                    mQingchu.setVisibility(View.VISIBLE);
                }
                 if (Double.parseDouble(s)>mResult){
                    mYue.setTextColor(Color.parseColor("#DF2A2A"));
                    mYue.setText("输入金额超过可提现金额");
                }else {
                    mYue.setTextColor(Color.parseColor("#666666"));
                     mYue.setText("可提现余额"+mSpanned+mResult+", 满"+mSpanned+"20可提现");
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //开始输入
                String s = mJinE.getText().toString();
                if ("".equals(s)){
                    mQingchu.setVisibility(View.GONE);
                    return;
                }else {
                    mQingchu.setVisibility(View.VISIBLE);
                }
                 if (Double.parseDouble(s)>mResult){
                    mYue.setTextColor(Color.parseColor("#DF2A2A"));
                    mYue.setText("输入金额超过可提现金额");
                }else {
                    mYue.setTextColor(Color.parseColor("#666666"));
                    mYue.setText("可提现余额"+mSpanned+mResult+", 满"+mSpanned+"20可提现");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //输入结束
                String s = mJinE.getText().toString();
                if ("".equals(s)){
                    mQingchu.setVisibility(View.GONE);
                    return;
                }else {
                    mQingchu.setVisibility(View.VISIBLE);
                }
                if (Double.parseDouble(s)>mResult){
                    mYue.setTextColor(Color.parseColor("#DF2A2A"));
                    mYue.setText("输入金额超过可提现金额");
                }else {
                    mYue.setTextColor(Color.parseColor("#666666"));
                    mYue.setText("可提现余额"+mSpanned+mResult+", 满"+mSpanned+"20可提现");
                }
            }
        });
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_ti_in_come;
    }

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum) {
            case AMOUNT:
                AmountBean amountBean= (AmountBean) o;
                mResult = amountBean.getResult();
                mYue.setText("可提现余额"+mSpanned+mResult+", 满"+mSpanned+"20可提现");
                break;
            case APPLY:
                CashoutBean cashoutBean= (CashoutBean) o;
                String result = cashoutBean.getResult();
                String mMsg = cashoutBean.getMsg();
                if ("OK".equals(mMsg)){
                    Intent intent1 = new Intent(this, TiDeitlActivity.class);
                    intent1.putExtra("money", mJinE.getText().toString());
                    intent1.putExtra("bank", mBank1);
                    intent1.putExtra("cards", mSubstring);
                    startActivity(intent1);
                }else if ("平台结算期间不可提现".equals(mMsg)){
                    backgroundAlpha(0.5f);
                    initPop();
                }else {
                    Toast.makeText(this, mMsg, Toast.LENGTH_SHORT).show();
                }
                Log.i("mResult", result);
                Log.i("qwers", mMsg);
                break;
            case BANKCARDINFO:
                BankcardInfoBeans bankcardInfoBeans = (BankcardInfoBeans) o;
                mCardId = bankcardInfoBeans.getResult().getCardId();
                String cardNo = bankcardInfoBeans.getResult().getCardNo();
                mBank1 = bankcardInfoBeans.getResult().getBank();
                if (mCardId>1000&&cardNo.length()>4){
                    mSubstring = cardNo.substring(cardNo.length()-4, cardNo.length());
                    mAddCard.setVisibility(View.GONE);
                    mJias.setVisibility(View.GONE);
                    mCards.setVisibility(View.VISIBLE);
                    mBanks.setVisibility(View.VISIBLE);
                    mBanks.setText("尾号"+ mSubstring +"的储蓄卡");
                    mCards.setText(mBank1);
               }
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }



    @OnClick({R.id.iv_finis,R.id.quanti,R.id.ti_xians,R.id.add_cards,R.id.qingchu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_finis:
                finish();
                break;
            case R.id.qingchu:
                mJinE.setText("");
                mYue.setTextColor(Color.parseColor("#666666"));
                mYue.setText("可提现余额 ￥ "+mResult+"，满 ￥ 20可提现");
                break;
            case R.id.add_cards:
                Intent intent = new Intent(this, AddBankCardsActivity.class);
                intent.putExtra("message","ok");
                startActivityForResult(intent,100);
                break;
            case R.id.quanti:
                Log.i("Ti_result", mResult +"");
                mJinE.setText(mResult+"");
                break;
            case R.id.ti_xians:
                String s = mJinE.getText().toString();
               if ("".equals(s)){
                    Toast.makeText(mActivity, "提现金额不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }else if (Double.parseDouble(s)>mResult){
                    Toast.makeText(mActivity, "输入金额超过可提现金额！", Toast.LENGTH_SHORT).show();
                    return;
                }else if (mAddCard.getVisibility()==View.VISIBLE){
                    Toast.makeText(mActivity, "请先添加银行卡！", Toast.LENGTH_SHORT).show();
                    return;
                }else if (Double.parseDouble(s)<20){
                    Toast.makeText(mActivity, "满20元才能提现！", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                   Calendar now = Calendar.getInstance();
                   int i = now.get(Calendar.DAY_OF_MONTH);
                   if (mCardId != 0) {
                       String s1 = mJinE.getText().toString();
                       HashMap<String, Object> stringStringHashMap = new HashMap<>();
                       stringStringHashMap.put("cardId", mCardId + "");
                       stringStringHashMap.put("cash", s1);
                       mPresenter.getDataP(stringStringHashMap, DifferentiateEnum.APPLY);
                   }
               }

                break;
        }
    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }

    private void initPop() {
        mInflate = LayoutInflater.from(TiInComeActivity.this).inflate(R.layout.pop_tixian_date, null);
        mPopupWindow1 = new PopupWindow(mInflate, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow1.setFocusable(true);// 取得焦点
        //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
        mPopupWindow1.setBackgroundDrawable(new BitmapDrawable());
        //点击外部消失
        mPopupWindow1.setOutsideTouchable(true);
        //设置可以点击
        mPopupWindow1.setTouchable(true);
        //进入退出的动画，指定刚才定义的style
        mPopupWindow1.setAnimationStyle(R.style.popwin_anim_style);
        TextView fanhui = mInflate.findViewById(R.id.fanhui);
        mPopupWindow1.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
               backgroundAlpha(1f);
            }
        });
        fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindow1.dismiss();
            }
        });
        mPopupWindow1.showAtLocation(mInflate, Gravity.CENTER,0,0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==100&&resultCode==200){
            mCard = data.getStringExtra("card");
            mBank = data.getStringExtra("bank");
            if (mCards!=null) {
                mJias.setVisibility(View.GONE);
                mAddCard.setVisibility(View.GONE);
                mCards.setVisibility(View.VISIBLE);
                mBanks.setVisibility(View.VISIBLE);
                mCards.setText(mBank);
                mBanks.setText("尾号" + mCard + "的储蓄卡");
            }
        }
    }
}
