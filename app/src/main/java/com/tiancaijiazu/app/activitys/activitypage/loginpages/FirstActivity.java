package com.tiancaijiazu.app.activitys.activitypage.loginpages;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tfedu.update.utils.PackageUtils;
import com.tfedu.update.utils.UpdateUtils;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.activitypage.LordActivity;
import com.tiancaijiazu.app.activitys.qi_activitys.BaoActivity;
import com.tiancaijiazu.app.base.activity.BaseActivity;
import com.tiancaijiazu.app.beans.AppCheckVersion;
import com.tiancaijiazu.app.beans.BabyMessageBean;
import com.tiancaijiazu.app.beans.StrBean;
import com.tiancaijiazu.app.beans.WXBean;
import com.tiancaijiazu.app.beans.WxCodeBean;
import com.tiancaijiazu.app.dao.DataBaseMannger;
import com.tiancaijiazu.app.dao.ToKenDaoBean;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.DestroyActivityUtil;
import com.tiancaijiazu.app.utils.PreUtils;
import com.tiancaijiazu.app.utils.ScreenStatusUtil;
import com.tiancaijiazu.app.utils.TimeUtil;
import com.tiancaijiazu.app.utils.city.ToastUtils;
import com.tiancaijiazu.app.mvp.IView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.tiancaijiazu.app.app.App.api;

/**
 *   登录页面
 *
 */

public class FirstActivity extends BaseActivity<IView, Presenter<IView>> implements IView {


    @BindView(R.id.first_wan)
    TextView mFirstWan;
    @BindView(R.id.a)
    TextView a;
    @BindView(R.id.b)
    TextView b;
    @BindView(R.id.lin_phono)
    LinearLayout mLinPhono;
    @BindView(R.id.checkbox)
    CheckBox mCheckbox;
    private boolean isbo = false;

    @Override
    protected void initEventAndData() {
        ScreenStatusUtil.setFillDip(this);
        /*String appName = PackageUtils.getAppName(this);
        UpdateUtils.checkUpdate(this, appName
                , false);*/
        //mPresenter.getDataP("", DifferentiateEnum.APPCHECKVERSION);
        String code = PreUtils.getString("code", "");
        if (!code.equals("")) {
            mPresenter.getDataP(code, DifferentiateEnum.WXLOGIN);
        }

        PreUtils.putString("first", "no");
    }

    protected void onNewIntent(Intent intent) {
        String cards = PreUtils.getString("first", "");
        if ("ok".equals(cards)) {
            DestroyActivityUtil.destoryActivity("LordActivity");
            boolean tokenTime = TimeUtil.getTokenTime();
            if (tokenTime) {
                String code = PreUtils.getString("code", "");
                if (!code.equals("")) {
                    mPresenter.getDataP(code, DifferentiateEnum.WXLOGIN);
                }
            } else {
                Intent intent1 = new Intent(this, LordActivity.class);
                startActivity(intent1);
            }
            PreUtils.putString("first", "no");
        }
        super.onNewIntent(intent);
        setIntent(intent);
        //here we can use getIntent() to get the extra data.

    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_first;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @OnClick({R.id.first_wan, R.id.lin_phono, R.id.a, R.id.b})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.first_wan:
                boolean checked = mCheckbox.isChecked();
                if(checked){
                    //先判断是否安装微信APP,按照微信的说法，目前移动应用上微信登录只提供原生的登录方式，需要用户安装微信客户端才能配合使用。
                    if (!api.isWXAppInstalled()) {
                        Toast.makeText(mActivity, "您还未安装微信客户端", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    //获取code
                    SendAuth.Req req = new SendAuth.Req();
                    req.scope = "snsapi_userinfo";
                    req.state = "wechat_sdk_demo_test";
                    api.sendReq(req);
                }else {
                    ToastUtils.showShortToast(this,"请阅读协议并勾选同意在进行登录！");
                }
                break;
            case R.id.lin_phono:
                if (TimeUtil.isInvalidClick(mLinPhono, 300))
                    return;
                boolean checked1 = mCheckbox.isChecked();
                if(checked1){
                    Intent intent = new Intent(this, LoginPhoneActivity.class);
                    intent.putExtra("login", "one");
                    intent.putExtra("title", "手机号登录");
                    startActivity(intent);
                }else {
                    ToastUtils.showShortToast(this,"请阅读协议并勾选同意在进行登录！");
                }
                break;
            case R.id.a:
                Intent intent1 = new Intent(FirstActivity.this, UserAgreementActivity.class);
                startActivity(intent1);
                break;
            case R.id.b:
                Intent intent2 = new Intent(FirstActivity.this, PrivacyPolicyActivity.class);
                startActivity(intent2);
                break;
        }
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum) {
            case WXLOGIN:
                String str = (String) o;
                Gson gson = new Gson();
                StrBean strBean = gson.fromJson(str, StrBean.class);
                if ("3".equals(strBean.getCode())) {
                    WxCodeBean wxCodeBean = gson.fromJson(str, WxCodeBean.class);
                    String wxOpenid = wxCodeBean.getResult().getWxOpenid();
                    Intent intent = new Intent(this, LoginPhoneActivity.class);
                    intent.putExtra("wxOpenid", wxOpenid);
                    startActivity(intent);
                } else if ("0".equals(strBean.getCode())) {
                    WXBean wxBean = gson.fromJson(str, WXBean.class);
                    String access_token = wxBean.getResult().getAccess_token();
                    int expires_in = wxBean.getResult().getExpires_in();
                    String refresh_token = wxBean.getResult().getRefresh_token();
                    DataBaseMannger.getIntrance().deleteAll();
                    ArrayList<ToKenDaoBean> toKenDaoBeans = new ArrayList<>();
                    String nowTime = TimeUtil.getNowTime();
                    toKenDaoBeans.add(new ToKenDaoBean(null, access_token, refresh_token, nowTime, expires_in + ""));
                    DataBaseMannger.getIntrance().insert(toKenDaoBeans);
                    mPresenter.getDataP("", DifferentiateEnum.BABYMESSAGELIST);
                }
                break;
            case BABYMESSAGELIST:
                BabyMessageBean babyMessageBean = (BabyMessageBean) o;
                List<BabyMessageBean.ResultBean> result = babyMessageBean.getResult();
                if (result.size() != 0) {
                    Intent intent = new Intent(this, LordActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(this, BaoActivity.class);
                    startActivity(intent);
                }
                break;
            case APPCHECKVERSION:
                AppCheckVersion appCheckVersion = (AppCheckVersion) o;
                String versionName = PackageUtils.getVersionName(this);
//                if (versionName.equalsIgnoreCase(appCheckVersion.getResult().getLatestVersion())){
                if (versionName.equalsIgnoreCase("1.0.0")) {
                    boolean tokenTime = TimeUtil.getTokenTime();
                    if (tokenTime) {
                        String code = PreUtils.getString("code", "");
                        if (!code.equals("")) {
                            mPresenter.getDataP(code, DifferentiateEnum.WXLOGIN);
                        }

                    } else {
                        Intent intent = new Intent(this, LordActivity.class);
                        startActivity(intent);
                    }
                    PreUtils.putString("first", "no");
                } else {
                    String appName = PackageUtils.getAppName(this);
                    UpdateUtils.checkUpdate(this, appName
                            , false);
                }
                break;
        }
    }

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }

}
