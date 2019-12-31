package com.tiancaijiazu.app.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayResp;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tiancaijiazu.app.activitys.activitypage.collegeactivitys.ClassListActivity;
import com.tiancaijiazu.app.activitys.diy.RenewActivity;
import com.tiancaijiazu.app.activitys.early.EarlyActivity;
import com.tiancaijiazu.app.activitys.shopping_activity.LineItemActivity;
import com.tiancaijiazu.app.utils.PreUtils;

import static com.tiancaijiazu.app.app.App.api;

public class WXPayEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api.handleIntent(getIntent(),this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        //微信响应的回调
        //我们只对code做处理
        // 这个code与errorCode不一样,有很大的区别,一定要注意
        int errorCode = baseResp.errCode;
        int type = baseResp.getType();
        PayResp resp = (PayResp) baseResp;
        String payType = resp.extData;
        Log.i("yx123", "onResp: "+errorCode);
        switch (errorCode) {
            case 0://支付成功:
                switch (payType) {
                    case "course":
                        Intent intent = new Intent(this, ClassListActivity.class);
                        PreUtils.putString("biao","ok");
                        startActivity(intent);
                        finish();
                        break;
                    case "shopping":
                        String orderId = PreUtils.getString("orderId", "");
                        Intent intent1 = new Intent(this, LineItemActivity.class);
                        intent1.putExtra("biao","3");
                        intent1.putExtra("orderId",orderId+"");
                        startActivity(intent1);
                        finish();
                        break;
                    case "vip":
                        setResult(147,getIntent());
                        finish();
                        break;
                    case "cardStudy":
                        Intent intent2 = new Intent(this, EarlyActivity.class);
                       /* DestroyActivityUtil.destoryActivity("TheFormalCardActivity");
                        DestroyActivityUtil.destoryActivity("PayActivity");
                        DestroyActivityUtil.destoryActivity("EarlyActivity");*/
                        PreUtils.putString("cards","ok");
                        startActivity(intent2);
                        finish();
                        break;
                    case "cardRenew":
                        Intent intent3 = new Intent(this, RenewActivity.class);
                        PreUtils.putString("renew","ok");
                        startActivity(intent3);
                        finish();
                        break;
                }
                break;
            case -1://配置信息错误：appId和签名文件
                break;
            case -2://取消支付
                switch (payType) {
                    case "shopping":
                        String orderId = PreUtils.getString("orderId", "");
                        Intent intent1 = new Intent(this, LineItemActivity.class);
                        intent1.putExtra("biao","2");
                        intent1.putExtra("orderId",orderId+"");
                        startActivity(intent1);
                        break;
                }
                finish();
                break;
            default:
                break;
        }
        //Log.d(TAG, "onResp: "+baseResp.errStr);
        //这个页面一般会显示空白页,直接跳回第三方登录页即可

    }

}
