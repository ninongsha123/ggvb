package com.tiancaijiazu.app.utils.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Administrator on 2019/7/10/010.
 */

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "short alarm", Toast.LENGTH_LONG).show();
        Log.e("cc","fangfazou");
    }
}
