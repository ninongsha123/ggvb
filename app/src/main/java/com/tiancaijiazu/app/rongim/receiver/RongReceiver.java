package com.tiancaijiazu.app.rongim.receiver;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.tiancaijiazu.app.activitys.activitypage.loginpages.FirstActivity;
import com.tiancaijiazu.app.activitys.activitypage.LordActivity;
import com.tiancaijiazu.app.dao.DataBaseMannger;
import com.tiancaijiazu.app.utils.ActivityController;
import com.tiancaijiazu.app.utils.DestroyActivityUtil;
import com.tiancaijiazu.app.utils.PreUtils;


public class RongReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("yx3333", "onReceive: 执行");
        final Activity activity = ActivityController.getCurrentActivity();
        AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
        dialog.setTitle("提示")
                .setMessage("您的账号在其他设备登录，请重新登录")
                .setCancelable(false)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityController.finishAll();
                        DataBaseMannger.getIntrance().deleteAll();
                        PreUtils.putString("first", "yes");
                        Intent intent = new Intent(activity, FirstActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        LordActivity.isbo = false;
                        LordActivity.sboo = false;
                        LordActivity.mCollegeFragment = null;
                        LordActivity.mParentingEncyclopediaFragment = null;
                        LordActivity.mShoppingMallFragment = null;
                        LordActivity.fragment_now = null;
                        LordActivity.mLine = null;
                        DestroyActivityUtil.destoryActivity("LordActivity");
                        activity.startActivity(intent);  //重新启动LoginActivity
                    }
                });
        if(!activity.isFinishing()){
            dialog.create().show();
        }

    }
}
