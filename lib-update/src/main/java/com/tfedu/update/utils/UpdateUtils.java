package com.tfedu.update.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tfedu.update.UpdateAppBean;
import com.tfedu.update.UpdateAppManager;
import com.tfedu.update.UpdateCallback;
import com.tfedu.update.entity.AppCheckVersion;
import com.tfedu.update.entity.UpdateEntity;
import com.tfedu.update.listener.ExceptionHandler;
import com.tfedu.update.service.DownloadService;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * desc :应用更新工具类
 * author：panyy
 * data：2018/5/7
 */
public class UpdateUtils {
    private static ProgressDialog progressDialog;

    private static String mUpdateUrl = "http://api.tiancaijiazu.com/api/v1/AppPart/app_check_version";

    public static void checkUpdate(final Activity activity, String packageName, final boolean isShowDialog) {
        //如果正在运行则返回
        if (DownloadService.isRunning) {
            return;
        }
        Map<String, String> params = new HashMap<String, String>();
        new UpdateAppManager
                .Builder()
                //必须设置，当前Activity
                .setActivity(activity)
                //必须设置，实现httpManager接口的对象
                .setHttpManager(new UpdateHttpUtil())
                //必须设置，更新地址
                .setUpdateUrl(mUpdateUrl)
                //全局异常捕获
                .handleException(new ExceptionHandler() {
                    @Override
                    public void onException(Exception e) {

                    }
                })
                .setPost(false)
                .setParams(params)
                .build()
                //检测是否有新版本
                .checkNewApp(new UpdateCallback() {
                    @Override
                    protected UpdateAppBean parseJson(String json) {
                        UpdateAppBean updateAppBean = new UpdateAppBean();
//                        UpdateEntity update = new Gson().fromJson(json, UpdateEntity.class);
                        AppCheckVersion appCheckVersion = new Gson().fromJson(json, AppCheckVersion.class);
                        String versionName = PackageUtils.getVersionName(activity);
                        if (versionName.equalsIgnoreCase(appCheckVersion.getResult().getLatestVersion())){
                            updateAppBean.setUpdate("No");
                            Toast.makeText(activity,"已是最新版本",Toast.LENGTH_SHORT).show();
                        }else {
                            //需要更新
                            updateAppBean.setApkFileUrl(appCheckVersion.getResult().getApkDownloadUrl())
                                    .setNewVersion(appCheckVersion.getResult().getLatestVersion())
                                    .setUpdate("Yes")
                                    .setConstraint(appCheckVersion.getResult().getMustBeUpdate());
                        }
                       /* UpdateEntity.DataEntity.LatestversionEntity latestversion = update.getData().getLatestversion();
                        if (latestversion != null) {
                            UpdateEntity.DataEntity.UpdateitemsEntity updateitem = update.getData().getUpdateitems().get(0);
                            updateAppBean

                                    //（必须）是否更新Yes,No
                                  //  .setUpdate("Yes")
                                    //（必须）新版本号
                                    .setNewVersion(latestversion.getName())
                                    //（必须）下载地址
                                    .setApkFileUrl(updateitem.getAddress())
                                    //（必须）更新内容
                                  //  .setUpdateLog(latestversion.getDescription())
                                    //大小，不设置不显示大小，可以不设置
                                  //  .setTargetSize(btoKM(updateitem.getFilesize()))
                                    //是否强制更新，可以不设置
                                    .setConstraint(latestversion.isMandatory());
                        } else {
                          //  updateAppBean.setUpdate("No");
                        }*/
                        return updateAppBean;
                    }

                    @Override
                    protected void hasNewApp(UpdateAppBean updateApp, UpdateAppManager updateAppManager) {
                        updateAppManager.showDialogFragment();
                    }

                    /**
                     * 网络请求之前
                     */
                    @Override
                    public void onBefore() {
                        if (isShowDialog) {
                            showProgressDialog(activity, "请稍等", "正在检查新版本...");
                        }
                    }

                    /**
                     * 网路请求之后
                     */
                    @Override
                    public void onAfter() {
                        if (isShowDialog) {
                            dismissProgressDialog();
                        }

                    }

                    @Override
                    public void noNewApp(String error) {
                        if (isShowDialog) {
                            Toast.makeText(activity, error, Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public static String btoKM(float size) {
        String sizeStr = "";
        DecimalFormat df = new DecimalFormat(".0");
        if (size < 1024) {
            sizeStr = String.valueOf(size) + "  B";
        } else if (size >= 1024 && size < 1024 * 1024) {
            sizeStr = String.valueOf(df.format(size / 1024)) + "  KB";
        } else if (size >= 1024 * 1024) {
            sizeStr = String.valueOf(df.format(size / 1024 / 1024)) + "  MB";
        }
        return sizeStr;
    }

    public static String getCurrVersionCode(Activity activity) {
        try {
            PackageManager packageManager = activity.getPackageManager();
            PackageInfo packInfo = packageManager.getPackageInfo(activity.getPackageName(), 0);
            return packInfo == null ? "0" : String.valueOf(packInfo.versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "0";
    }

    public static String getCurrVersionName(Activity activity) {
        try {
            PackageManager packageManager = activity.getPackageManager();
            PackageInfo packInfo = packageManager.getPackageInfo(activity.getPackageName(), 0);
            return packInfo == null ? "0" : packInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "0";
    }

    public static void showProgressDialog(Context context, String title, String message) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setTitle(title);
        progressDialog.setMessage(message);
        progressDialog.show();
    }
    public static void dismissProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

}
