package com.tiancaijiazu.app.utils;

import android.app.Activity;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2019/5/30/030.
 */

public class DestroyActivityUtil {


    private static Map<String, Activity> destoryMap;

    //将Activity添加到队列中
    public static void addDestoryActivityToMap(Activity activity, String activityName) {
        destoryMap = new HashMap<>();
        destoryMap.put(activityName, activity);
    }

    //根据名字销毁制定Activity
    public static void destoryActivity(String activityName) {
        if (destoryMap != null) {
            Set<String> keySet = destoryMap.keySet();
            if (keySet.size() > 0) {
                for (String key : keySet) {
                    if (activityName.equals(key)) {
                        destoryMap.get(key).finish();
                        destoryMap.remove(key);
                    }
                }
            }
            /*if (destoryMap.size() == 0) {
                destoryMap = null;
            }*/
        }
    }

}
