package com.tiancaijiazu.app.app;

import android.app.ActivityManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Environment;
import android.os.Handler;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.danikula.videocache.HttpProxyCacheServer;
import com.hpplay.sdk.source.browse.api.ILelinkServiceManager;
import com.hpplay.sdk.source.browse.api.LelinkServiceManager;
import com.hpplay.sdk.source.browse.api.LelinkSetting;
import com.lzx.starrysky.manager.MusicManager;
import com.lzx.starrysky.notification.NotificationConstructor;
import com.lzx.starrysky.playback.download.ExoDownload;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tiancaijiazu.app.activitys.shortvideo.widget.MyFileNameGenerator;
import com.tiancaijiazu.app.beans.DiaryDetailBean;
import com.tiancaijiazu.app.globals.Globals;
import com.tiancaijiazu.app.music.NotificationReceiver;
import com.tiancaijiazu.app.music.imageloader.GlideLoader;
import com.tiancaijiazu.app.rongim.PhoneInfo;
import com.tiancaijiazu.app.rongim.RedPackageItemProvider;
import com.tiancaijiazu.app.utils.city.ToastUtils;
import com.tiancaijiazu.app.utils.status.LoadingLayout;

import java.util.List;

import cn.jpush.android.api.JPushInterface;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;


/**
 * Created by Administrator on 2019/4/22/022.
 */

public class App extends MultiDexApplication {
    private static App sApp;
    public static String ACTION_PLAY_OR_PAUSE = "ACTION_PLAY_OR_PAUSE";
    public static String ACTION_NEXT = "ACTION_NEXT";
    public static String ACTION_PRE = "ACTION_PRE";
    public static String ACTION_FAVORITE = "ACTION_FAVORITE";
    public static String ACTION_LYRICS = "ACTION_LYRICS";
    private ILelinkServiceManager mLelinkServiceManager;
    private static Context context;
    public String birtoday;
    public List<String> mUrl;

    public List<String> getUrl() {
        return mUrl;
    }

    public void setUrl(List<String> url) {
        mUrl = url;
    }

    public String getBirtoday() {
        return birtoday;
    }
    public void setBirtoday(String birtoday) {
        this.birtoday = birtoday;
    }

    // IWXAPI 是第三方app和微信通信的openApi接口
    public static IWXAPI api;
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.fontScale != 1)//非默认值
            getResources();
        super.onConfigurationChanged(newConfig);
    }
    private void initImageLoader() {
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(this);
        ImageLoader.getInstance().init(configuration);

    }
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        if (res.getConfiguration().fontScale != 1) {//非默认值
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();//设置默认
            res.updateConfiguration(newConfig, res.getDisplayMetrics());
        }
        return res;
    }

    private PendingIntent getPendingIntent(String action) {
        Intent intent = new Intent(action);
        intent.setClass(this, NotificationReceiver.class);
        return PendingIntent.getBroadcast(this, 0, intent, 0);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoader();
        //initLeakCanary();
        JPushInterface.setDebugMode(true);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);            // 初始化 JPush
        //乐播初始化
        LelinkSetting lelinkSetting = new LelinkSetting.LelinkSettingBuilder("11831", "8916a6c454cb6cb3e555f5c265330757").build();
        mLelinkServiceManager = LelinkServiceManager.getInstance(getApplicationContext());
        mLelinkServiceManager.setLelinkSetting(lelinkSetting);
        LoadingLayout.getConfig();
        //初始化handler
        mHandler = new Handler();
        regToWx();
        //初始化
        MusicManager.initMusicManager(this);
        //设置图片加载器
        MusicManager.setImageLoader(new GlideLoader());
        //配置通知栏
        NotificationConstructor constructor = new NotificationConstructor.Builder()
                .setCreateSystemNotification(true)
                .bulid();
        MusicManager.getInstance().setNotificationConstructor(constructor);
        //设置缓存
        String destFileDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/11ExoCacheDir";
        ExoDownload.getInstance().setOpenCache(false); //打开缓存开关
        ExoDownload.getInstance().setShowNotificationWhenDownload(false);
        ExoDownload.getInstance().setCacheDestFileDir(destFileDir); //设置缓存文件夹
        sApp = this;

        /**
         *
         * OnCreate 会被多个进程重入，这段保护代码，确保只有您需要使用 RongIM 的进程和 Push 进程执行了 init。
         * io.rong.push 为融云 push 进程名称，不可修改。
         */
        if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext())) ||
                "io.rong.push".equals(getCurProcessName(getApplicationContext()))) {

            /**
             * IMKit SDK调用第一步 初始化
             */
            RongIM.init(this);
            //注册自定义消息
            RongIM.registerMessageType(PhoneInfo.class);
            //注册消息模板，可自定义
            //注意，要在初始化之后注册
            RongIM.registerMessageTemplate(new RedPackageItemProvider());

            RongIM.getInstance().setMessageAttachedUserInfo(true);

            RongIM.setConnectionStatusListener(new RongIMClient.ConnectionStatusListener() {
                @Override
                public void onChanged(RongIMClient.ConnectionStatusListener.ConnectionStatus connectionStatus) {
                    Log.i("yx3333", "onChanged: "+connectionStatus.getValue());
                    if(connectionStatus.getValue()==3){
                        Intent intent = new Intent("com.tiancaijiazu.app.rongim.receiver.RongReceiver");
                        intent.setComponent( new ComponentName( "com.tiancaijiazu.app" ,
                                "com.tiancaijiazu.app.rongim.receiver.RongReceiver") );
                        sendBroadcast(intent);
                    }
                }
            });

        }
        App.context = getApplicationContext();

    }

    /**
     * 获得当前进程的名字
     *
     * @param context
     * @return
     */
    public static String getCurProcessName(Context context) {

        int pid = android.os.Process.myPid();

        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {

            if (appProcess.pid == pid) {

                return appProcess.processName;
            }
        }
        return null;
    }
    /*@Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }*/

    public static App getApplication() {
        return sApp;
    }

    private void initLeakCanary() {
        /*if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this)*/;
    }

    private void regToWx() {
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, Globals.APP_ID, true);

        // 将应用的appId注册到微信
        //api.registerApp(Globals.APP_ID);
        //建议动态监听微信启动广播进行注册到微信
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // 将该app注册到微信
                api.registerApp(Globals.APP_ID);
            }
        }, new IntentFilter(ConstantsAPI.ACTION_REFRESH_WXAPP));

    }

    public ILelinkServiceManager getILelinkServiceManager() {
        return mLelinkServiceManager;
    }

    private HttpProxyCacheServer proxy;

    public static HttpProxyCacheServer getProxy(Context context) {
        App app = (App) context.getApplicationContext();
        return app.proxy == null ? (app.proxy = app.newProxy()) : app.proxy;
    }

    private HttpProxyCacheServer newProxy() {
        return new HttpProxyCacheServer.Builder(this)
                .maxCacheSize(1024 * 1024 * 1024)       // 1 Gb for cache
                .fileNameGenerator(new MyFileNameGenerator())
                .build();
    }

    public static Context getAppContext() {
        return App.context;
    }

    /**
     * 在主线程中刷新UI的方法
     *
     * @param r
     */
    public static void runOnUIThread(Runnable r) {
        App.getMainHandler().post(r);
    }

    //qcl用来在主线程中刷新ui
    private static Handler mHandler;

    public static Handler getMainHandler() {
        return mHandler;
    }
}
