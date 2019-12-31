package com.tiancaijiazu.app.activitys.activitypage.myactivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.activitypage.ChangePhoneActivity;
import com.tiancaijiazu.app.activitys.activitypage.LordActivity;
import com.tiancaijiazu.app.activitys.activitypage.loginpages.FirstActivity;
import com.tiancaijiazu.app.base.activity.SimpleActivity;
import com.tiancaijiazu.app.beans.UserInfoBean;
import com.tiancaijiazu.app.dao.DataBaseMannger;
import com.tiancaijiazu.app.utils.ActivityController;
import com.tiancaijiazu.app.utils.DestroyActivityUtil;
import com.tiancaijiazu.app.utils.PreUtils;
import com.tiancaijiazu.app.utils.ScreenStatusUtil;
import com.tiancaijiazu.app.utils.TimeUtil;
import com.tiancaijiazu.app.utils.cache.DataCleanManager;
import com.tiancaijiazu.app.utils.city.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.refactor.lib.colordialog.ColorDialog;

//import com.tfedu.update.utils.UpdateUtils;

/**
 * 我的-设置
 */
public class SettingActivity extends SimpleActivity {


    @BindView(R.id.iv_finis)
    ImageView ivFinis;
    @BindView(R.id.text_clear_cach)
    TextView text_clear_cach;
    @BindView(R.id.a)
    TextView a1;
    @BindView(R.id.bianji)
    RelativeLayout bianji;
    @BindView(R.id.clear_rela)
    RelativeLayout clear_rela;
    @BindView(R.id.exit_login)
    RelativeLayout exit_login;
    @BindView(R.id.give_review)
    RelativeLayout give_review;
    @BindView(R.id.about_us)
    RelativeLayout about_us;
    @BindView(R.id.bing_wact)
    RelativeLayout mBingWact;
    @BindView(R.id.number)
    TextView mNumber;
    @BindView(R.id.phone)
    RelativeLayout mPhone;
    @BindView(R.id.right_arrow)
    ImageView mRightArrow;
    private UserInfoBean.ResultBean mResultBean;
    private AlertDialog.Builder ibuilder;
    private Intent mIntent;

    @Override
    protected void initEventAndData() {
        ScreenStatusUtil.setFillDip(this);
        mIntent = getIntent();
        mResultBean = (UserInfoBean.ResultBean) mIntent.getSerializableExtra("data");
        String mobile = mResultBean.getMobile();
        mNumber.setText("+86 " + mobile);
        initClick();
        try {
            String data = DataCleanManager.getTotalCacheSize(SettingActivity.this);
            text_clear_cach.setText(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        setResult(120, mIntent);
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_setting;
    }

    private void initClick() {
        //返回
        ivFinis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(120, mIntent);
                finish();
            }
        });
        //编辑资料
        bianji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(SettingActivity.this, BianjiziliaoActivity.class);
                in.putExtra("data", mResultBean);
                startActivity(in);
            }
        });

        //换绑手机号
        mPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this, ChangePhoneActivity.class);
                intent.putExtra("number",mResultBean.getMobile());
                startActivity(intent);
            }
        });

        clear_rela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TimeUtil.isInvalidClick(v, 300))
                    return;
                DataCleanManager.clearAllCache(SettingActivity.this);
                text_clear_cach.setText("0.0M");
                ToastUtils.showShortToast(SettingActivity.this, "缓存已清理");
            }
        });
        exit_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorDialog dialog = new ColorDialog(SettingActivity.this);
                dialog.setTitle("温馨提示！");
                dialog.setContentText("退出登录将清空用户数据，确定退出登录?");
                dialog.setPositiveListener("确定", new ColorDialog.OnPositiveListener() {
                    @Override
                    public void onClick(ColorDialog dialog) {
                       /* DataBaseMannger.getIntrance().deleteAll();
                        PreUtils.putString("first", "yes");
                        Intent intent = new Intent(SettingActivity.this, FirstActivity.class);
                        LordActivity.isbo = false;
                        LordActivity.sboo = false;
                        LordActivity.mCollegeFragment = null;
                        LordActivity.mParentingEncyclopediaFragment = null;
                        LordActivity.mShoppingMallFragment = null;
                        LordActivity.fragment_now = null;
                        LordActivity.mLine = null;
                        DestroyActivityUtil.destoryActivity("LordActivity");
                        dialog.dismiss();
                        startActivity(intent);*/
                        ActivityController.finishAll();
                        DataBaseMannger.getIntrance().deleteAll();
                        PreUtils.putString("first", "yes");
                        Intent intent = new Intent(SettingActivity.this, FirstActivity.class);
                        LordActivity.isbo = false;
                        LordActivity.sboo = false;
                        LordActivity.mCollegeFragment = null;
                        LordActivity.mParentingEncyclopediaFragment = null;
                        LordActivity.mShoppingMallFragment = null;
                        LordActivity.fragment_now = null;
                        LordActivity.mLine = null;
                        DestroyActivityUtil.destoryActivity("LordActivity");
                        startActivity(intent);  //重新启动LoginActivity
                        finish();
                    }
                }).setNegativeListener("取消", new ColorDialog.OnNegativeListener() {
                    @Override
                    public void onClick(ColorDialog dialog) {

                        dialog.dismiss();
                    }
                });
                dialog.show();

            }
        });
        about_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, AboutUsActivity.class);
                startActivity(intent);
            }
        });
        give_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mAddress = "market://details?id=" + getPackageName();
                Intent marketIntent = new Intent("android.intent.action.VIEW");
                marketIntent.setData(Uri.parse(mAddress));
                startActivity(marketIntent);
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
