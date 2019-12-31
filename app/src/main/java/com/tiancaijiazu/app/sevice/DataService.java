package com.tiancaijiazu.app.sevice;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Parcelable;

import com.tiancaijiazu.app.beans.ArticleLists;
import com.tiancaijiazu.app.utils.ImageLoader;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Othershe
 * Time: 2016/8/18 11:48
 */
public class DataService extends IntentService {
    public DataService() {
        super("");
    }

    public static void startService(Context context, List<ArticleLists.ResultBean> datas) {
        Intent intent = new Intent(context, DataService.class);
        intent.putExtra("data", (Serializable) datas);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent == null) {
            return;
        }
        List<ArticleLists.ResultBean> datas = (List<ArticleLists.ResultBean>) intent.getSerializableExtra("data");
        handleGirlItemData(datas);
    }

    private void handleGirlItemData(List<ArticleLists.ResultBean> datas) {
        if (datas.size() == 0) {
            EventBus.getDefault().post("finish");
            return;
        }
        for (ArticleLists.ResultBean data : datas) {
            Bitmap bitmap = ImageLoader.load(this, data.getPicUri());
            if (bitmap != null) {
                data.setWidth(bitmap.getWidth());
                data.setHeight(bitmap.getHeight());
            }
        }
        EventBus.getDefault().post(datas);
    }
}
