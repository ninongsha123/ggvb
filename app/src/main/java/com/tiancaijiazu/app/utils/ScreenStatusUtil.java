package com.tiancaijiazu.app.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2019/7/4/004.
 */

public class ScreenStatusUtil {

    /*刘海屏全屏显示FLAG*/
    public static final int FLAG_NOTCH_SUPPORT = 0x00010000;
    public static final String DISPLAY_NOTCH_STATUS = "display_notch_status";

    /**
     * dp转px
     * @param s
     * @param context
     * @return
     */
    public static int setDp(int s,Context context){
        int i = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, s, context.getResources().getDisplayMetrics());
        return i;
    }

    /**
     * 设置标题栏高度
     * @param context
     * @param view
     */
    public static void setNotStatus(Context context, View view){
        //判断是否是刘海屏
        boolean b = ScreenStatusUtil.hasNotchInScreen(context);
        if(b){
            int mIsNotchSwitchOpen = Settings.Secure.getInt(context.getContentResolver(), ScreenStatusUtil.DISPLAY_NOTCH_STATUS, 0);
            if(mIsNotchSwitchOpen==1){
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                int i = ScreenStatusUtil.setDp(44, context);
                layoutParams.height = i;
                view.setLayoutParams(layoutParams);
            }
        }
    }

    /**
     * 判断是否需要全浸式状态栏
     * @param context
     */
    public static void setFillDip(Activity context){
        //判断是否是刘海屏
        boolean b = ScreenStatusUtil.hasNotchInScreen(context);
        if(b){
            int mIsNotchSwitchOpen = Settings.Secure.getInt(context.getContentResolver(), ScreenStatusUtil.DISPLAY_NOTCH_STATUS, 0);
            if(mIsNotchSwitchOpen==0){
                initSett(context);
            }
        }else {
            initSett(context);
        }
    }

    //设置状态栏与状态栏字体颜色
    public static void initSett(Activity context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = context.getWindow();
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
    /**
     * 设置应用窗口在华为刘海屏手机使用刘海区
     *
     * @param window 应用页面window对象
     */
    public static void setFullScreenWindowLayoutInDisplayCutout(Window window) {
        if (window == null) {
            return;
        }
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        try {
            Class layoutParamsExCls = Class.forName("com.huawei.android.view.LayoutParamsEx");
            Constructor con = layoutParamsExCls.getConstructor(WindowManager.LayoutParams.class);
            Object layoutParamsExObj = con.newInstance(layoutParams);
            Method method = layoutParamsExCls.getMethod("addHwFlags", int.class);
            method.invoke(layoutParamsExObj, FLAG_NOTCH_SUPPORT);
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException
                | InvocationTargetException e) {
            Log.e("test", "hw add notch screen flag api error");
        } catch (Exception e) {
            Log.e("test", "other Exception");
        }
    }

    public static boolean hasNotchInScreen(Context context) {
        boolean ret = false;
        try {
            ClassLoader cl = context.getClassLoader();
            Class HwNotchSizeUtil = cl.loadClass("com.huawei.android.util.HwNotchSizeUtil");
            Method get = HwNotchSizeUtil.getMethod("hasNotchInScreen");
            ret = (boolean) get.invoke(HwNotchSizeUtil);
        } catch (ClassNotFoundException e) {
            Log.e("test", "hasNotchInScreen ClassNotFoundException");
        } catch (NoSuchMethodException e) {
            Log.e("test", "hasNotchInScreen NoSuchMethodException");
        } catch (Exception e) {
            Log.e("test", "hasNotchInScreen Exception");
        } finally {
            return ret;
        }
    }

    public static int[] getNotchSize(Context context) {
        int[] ret = new int[]{0, 0};
        try {
            ClassLoader cl = context.getClassLoader();
            Class HwNotchSizeUtil = cl.loadClass("com.huawei.android.util.HwNotchSizeUtil");
            Method get = HwNotchSizeUtil.getMethod("getNotchSize");
            ret = (int[]) get.invoke(HwNotchSizeUtil);
        } catch (ClassNotFoundException e) {
            Log.e("test", "getNotchSize ClassNotFoundException");
        } catch (NoSuchMethodException e) {
            Log.e("test", "getNotchSize NoSuchMethodException");
        } catch (Exception e) {
            Log.e("test", "getNotchSize Exception");
        } finally {
            return ret;
        }
    }
}
