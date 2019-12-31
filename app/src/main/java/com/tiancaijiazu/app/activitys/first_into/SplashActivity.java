package com.tiancaijiazu.app.activitys.first_into;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.tfedu.update.utils.PackageUtils;
import com.tfedu.update.utils.UpdateUtils;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.activitypage.loginpages.FirstActivity;
import com.tiancaijiazu.app.activitys.activitypage.LordActivity;
import com.tiancaijiazu.app.base.activity.BaseActivity;
import com.tiancaijiazu.app.beans.AppCheckVersion;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.globals.AppConstants;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.ScreenStatusUtil;
import com.tiancaijiazu.app.utils.SpUtils;
import com.tiancaijiazu.app.utils.TimeUtil;
import com.tiancaijiazu.app.mvp.IView;

import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends BaseActivity<IView, Presenter<IView>> implements IView{


    @Override
    protected void initEventAndData() {
        ScreenStatusUtil.setFillDip(this);
        // 判断是否是第一次开启应用
        boolean isFirstOpen = SpUtils.getBoolean(this, AppConstants.FIRST_OPEN);
        // 如果是第一次启动，则先进入功能引导页
        if (!isFirstOpen) {
            if (isNeedCheck){
                checkPermissions(needPermissions);
            }
            Intent intent = new Intent(this, WelcomeGuideActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        // 如果不是第一次启动app，则正常显示启动屏
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mPresenter.getDataP("", DifferentiateEnum.APPCHECKVERSION);
            }
        }, 2000);

    }


    /**
     * 需要进行检测的权限数组
     */
    protected String[] needPermissions = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.SYSTEM_ALERT_WINDOW
    };

    /**
     * 判断是否需要检测，防止不停的弹框
     */
    private boolean isNeedCheck = true;
    private static final int PERMISSON_REQUESTCODE = 0;

    private List<String> findDeniedPermissions(String[] permissions) {
        List<String> needRequestPermissonList = new ArrayList<String>();
        //for (循环变量类型 循环变量名称 : 要被遍历的对象)
        for (String perm : permissions) {
            if (ContextCompat.checkSelfPermission(SplashActivity.this, perm) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.shouldShowRequestPermissionRationale(
                    mActivity, perm)) {
                needRequestPermissonList.add(perm);
            }
        }
        return needRequestPermissonList;
    }

    private void checkPermissions(String... permissions) {
        //获取权限列表
        List<String> needRequestPermissonList = findDeniedPermissions(permissions);
        if (null != needRequestPermissonList
                && needRequestPermissonList.size() > 0) {
            //list.toarray将集合转化为数组
            ActivityCompat.requestPermissions(mActivity,
                    needRequestPermissonList.toArray(new String[needRequestPermissonList.size()]),
                    PERMISSON_REQUESTCODE);
        }
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_splash;
    }
    private void enterHomeActivity() {
        boolean tokenTime = TimeUtil.getTokenTime();
            if (tokenTime) {
            Intent intent = new Intent(this, FirstActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, LordActivity.class);
            startActivity(intent);
        }
        finish();
    }

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum) {
            case APPCHECKVERSION:
                AppCheckVersion appCheckVersion = (AppCheckVersion) o;
                String versionName = PackageUtils.getVersionName(this);
                if (versionName.equalsIgnoreCase(appCheckVersion.getResult().getLatestVersion())){
                    //if (versionName.equalsIgnoreCase("1.0.0")) {
                    enterHomeActivity();
                } else {
                    String appName = PackageUtils.getAppName(this);
                    UpdateUtils.checkUpdate(this, appName
                            , false);
                }
                break;
        }
    }

    @Override
    public void showError(String error) {

    }
}
