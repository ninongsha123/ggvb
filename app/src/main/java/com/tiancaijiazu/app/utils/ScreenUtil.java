package com.tiancaijiazu.app.utils;

/**
 * Created by Administrator on 2019/5/10/010.
 */

import android.content.Context;
import android.view.WindowManager;
import android.util.DisplayMetrics;

/**
 * Created by liuli on 2015/11/27.
 */
public class ScreenUtil {
    public static int height;
    public static int width;
    private Context context;
    public final static String WIDTH = "width";
    public final static String HEIGHT = "height";
    private static ScreenUtil instance;

    public ScreenUtil(Context context) {
        this.context = context;
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(dm);
        width = dm.widthPixels;
        height = dm.heightPixels;
    }

    public static ScreenUtil getInstance(Context context) {
        if (instance == null) {
            instance = new ScreenUtil(context);
        }
        return instance;
    }

    /**
     * 得到手机屏幕的宽度, pix单位
     */
    public static int getScreenWidth() {
        return width;
    }
}