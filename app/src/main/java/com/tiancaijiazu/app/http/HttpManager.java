package com.tiancaijiazu.app.http;

import com.tiancaijiazu.app.app.App;
import com.tiancaijiazu.app.globals.Globals;
import com.tiancaijiazu.app.utils.cache.SystemUtil;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Administrator on 2019/4/22/022.
 */

public class HttpManager {
    //修饰静态可以类名直接调用
    private static HttpManager sHttpManager;
    private OkHttpClient mOkHttpClient;

    //无参
    private HttpManager() {

    }

    //单例模式HttpManager对象
    public static HttpManager getInstance() {
        if (sHttpManager == null) {
            synchronized (HttpManager.class) {
                if (sHttpManager == null) {
                    sHttpManager = new HttpManager();
                }
            }
        }
        return sHttpManager;
    }

    //retrofit解析网络请求,返回retrofit对象
    public ApiServer getServer() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Globals.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getOkHttpClient())//缓存
                .build();
        return retrofit.create(ApiServer.class);
    }
    //retrofit解析网络请求,返回retrofit对象
    public ApiServer getTiXian() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Globals.BASES_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getOkHttpClient())//缓存
                .build();
        return retrofit.create(ApiServer.class);
    }
    //retrofit解析网络请求,返回retrofit对象
    public ApiServer getServerStr() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Globals.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getOkHttpClient())//缓存
                .build();
        return retrofit.create(ApiServer.class);
    }
    //retrofit解析网络请求,返回retrofit对象
    public ApiServer getServerWx() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Globals.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                //.client(getOkHttpClient())//缓存
                .build();
        return retrofit.create(ApiServer.class);
    }

    //retrofit解析网络请求,返回retrofit对象
    public ApiServer getServerCatalog() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Globals.BASE_URL_CATALOG)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getOkHttpClient())//缓存
                .build();
        return retrofit.create(ApiServer.class);
    }

    //缓存设置
    public OkHttpClient getOkHttpClient() {
        //缓存文件定义：缓存到当前项目的包路径下
        Cache cache = new Cache(new File(App.getApplication().getCacheDir(), "cache"), 1024 * 1024 * 10);
        //网络请求的Log的日志输出
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        //缓存拦截器
        MyCacheinterceptor myCacheinterceptor = new MyCacheinterceptor();
        return new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .cache(cache)//缓存路径
                .addInterceptor(httpLoggingInterceptor)//拦截器网络请求的Log的日志输出
                .addInterceptor(myCacheinterceptor)//注册Application拦截器调用的是addInterceptor()
                .addNetworkInterceptor(myCacheinterceptor)//注册Network拦截器 调用 addNetworkInterceptor() 来代替 addInterceptor():
                .addInterceptor(new TokenInterceptor())
                //.addNetworkInterceptor(new TokenInterceptor())
                .retryOnConnectionFailure(true)//启动错误重连
                .build();
    }
    //缓存拦截器
    private class MyCacheinterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            //这里就是说判读我们的网络条件，要是有网络的话我们就直接获取网络上面的数据，要是没有网络的话我们就去缓存里面取数据
            if (!SystemUtil.isNetworkConnected()) {
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
            }
            Response originalResponse = chain.proceed(request);
            if (SystemUtil.isNetworkConnected()) {
                int maxAge = 0;
                return originalResponse.newBuilder()
                        //清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public ,max-age=" + maxAge)
                        .build();
            } else {
                int maxStale = 60 * 60 * 24 * 7;
                return originalResponse.newBuilder()
                        //清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                        .removeHeader("Pragma")
                        //这里的设置的是我们的没有网络的缓存时间，想设置多少就是多少。
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
        }
    }
}
