package com.tiancaijiazu.app.activitys.shopping_activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.base.activity.BaseActivity;
import com.tiancaijiazu.app.beans.DizhiBean;
import com.tiancaijiazu.app.beans.EditAddressBean;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.pickerview.AddressPickTask;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.mvp.IView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import cn.addapp.pickers.entity.City;
import cn.addapp.pickers.entity.County;
import cn.addapp.pickers.entity.Province;

public class LocationActivity extends BaseActivity<IView, Presenter<IView>> implements IView {


    @BindView(R.id.edit_name)
    EditText mEditName;
    @BindView(R.id.edit_phone)
    EditText mEditPhone;
    @BindView(R.id.edit_xiangxi)
    EditText mEditXiangxi;
    @BindView(R.id.edit_diqu)
    EditText mEditDiqu;
    @BindView(R.id.iv_finis)
    ImageView mIvFinis;
    @BindView(R.id.iv_ok)
    ImageView mIvOk;  @BindView(R.id.a)
    TextView a;
    private Intent mIntent;
    private String mBiao;

    @SuppressLint("NewApi")
    @Override
    protected void initEventAndData() {
        mIntent = getIntent();
        mBiao = mIntent.getStringExtra("biao");
        if ("1".equals(mBiao)) {
            String name = mIntent.getStringExtra("name");
            String area = mIntent.getStringExtra("area");
            String address = mIntent.getStringExtra("address");
            String mobile = mIntent.getStringExtra("mobile");
            mEditName.setText(name);
            mEditPhone.setText(mobile);
            mEditDiqu.setText(area);
            mEditXiangxi.setText(address);
        }

        initSett();
        setEditTextHintSize(mEditPhone, "手机号码", 14);
        setEditTextHintSize(mEditName, "收货人", 14);
        setEditTextHintSize(mEditDiqu, "所在地区", 14);
        setEditTextHintSize(mEditXiangxi, "详细地址：如街道、小区、楼牌号等", 14);

        mEditDiqu.setInputType(InputType.TYPE_NULL);
        //mEditDiqu.setKeyListener(null);
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_location;
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

    @SuppressLint("NewApi")
    public static void setEditTextHintSize(EditText editText, String hintText, int size) {
        SpannableString ss = new SpannableString(hintText);//定义hint的值
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(size, true);//设置字体大小 true表示单位是sp
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        editText.setHint(new SpannedString(ss));
    }


    @OnClick({R.id.iv_finis, R.id.iv_ok, R.id.edit_diqu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_finis:
                finish();
                break;
            case R.id.iv_ok:
                String name = mEditName.getText().toString();
                String phone = mEditPhone.getText().toString();
                String details = mEditXiangxi.getText().toString();
                String diqu = mEditDiqu.getText().toString();
                if ("0".equals(mBiao)) {
                    if (name.length() == 0 || phone.length() == 0 || details.length() == 0 || diqu.length() == 0) {
                        Toast.makeText(mActivity, "请将地址填写完整！", Toast.LENGTH_SHORT).show();
                    } else {
                        String telRegex = "^((13[0-9])|(15[^4])|(166)|(17[0-8])|(18[0-9])|(19[8-9])|(147,145))\\d{8}$";
                        if(phone.matches(telRegex) && phone.length() == 11){
                            HashMap<Object, Object> map = new HashMap<>();
                            map.put("name", name);
                            map.put("area", diqu);
                            map.put("address", details);
                            map.put("mobile", phone);
                            map.put("isDefault", 0);
                            mPresenter.getDataP(map, DifferentiateEnum.ADDDIZHI);
                        }else {
                            Toast.makeText(mActivity, "您输入的手机号有误！请重新输入！", Toast.LENGTH_SHORT).show();
                        }

                    }
                } else if ("1".equals(mBiao)) {
                    String consigneeId = mIntent.getStringExtra("consigneeId");
                    String isDefault = mIntent.getStringExtra("isDefault");
                    if (name.length() == 0 || phone.length() == 0 || details.length() == 0 || diqu.length() == 0) {
                        Toast.makeText(mActivity, "请将地址填写完整！", Toast.LENGTH_SHORT).show();
                    } else {

                        HashMap<String, String> map = new HashMap<>();
                        map.put("consigneeId", consigneeId);
                        map.put("name", name);
                        map.put("area", diqu);
                        map.put("address", details);
                        map.put("mobile", phone);
                        map.put("isDefault", isDefault);
                        mPresenter.getDataP(map, DifferentiateEnum.EDITADDRESS);
                    }
                }

                break;
            case R.id.edit_diqu:
                hideInput();
//                wheel();
                onAddressPicker();
                break;
        }
    }

    /**
     * 隐藏键盘
     */
    @SuppressLint("NewApi")
    protected void hideInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        View v = getWindow().peekDecorView();
        if (null != v) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    /**
     * 弹出选择器
     */
    public void onAddressPicker() {
        AddressPickTask task = new AddressPickTask(this);
        task.setHideProvince(false);
        task.setHideCounty(false);
        task.setCallback(new AddressPickTask.Callback() {
            @Override
            public void onAddressInitFailed() {
                Toast.makeText(mActivity, "数据初始化失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAddressPicked(Province province, City city, County county) {
               /* HashMap<String, String> map = new HashMap<>();
                if (county == null) {
                    Toast.makeText(mActivity, province.getAreaName() + city.getAreaName(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mActivity, province.getAreaName() + city.getAreaName() + county.getAreaName(), Toast.LENGTH_SHORT).show();
                }
                map.put("country", "中国");
                map.put("province", province.getAreaName());
                map.put("city", city.getAreaName());
                mPresenter.getDataP(map, DifferentiateEnum.UPADDRESS);*/
                mEditDiqu.setText(province.getAreaName() + "-" + city.getAreaName()+"-"+county.getAreaName());
                mEditDiqu.setTextColor(Color.parseColor("#333333"));
            }
        });
        task.execute("北京市", "北京市", "海淀区");

    }
    @Override
    public void showError(String error) {

    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum) {
            case ADDDIZHI:
                DizhiBean dizhiBean = (DizhiBean) o;
                String result = dizhiBean.getResult();
                Toast.makeText(this, "" + result, Toast.LENGTH_SHORT).show();
                setResult(12, mIntent);
                finish();
                break;
            case EDITADDRESS:
                EditAddressBean editAddressBean = (EditAddressBean) o;
                String result1 = editAddressBean.getResult();
                Toast.makeText(mActivity, "" + result1, Toast.LENGTH_SHORT).show();
                setResult(12, mIntent);
                finish();
                break;
        }
    }

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }
}
