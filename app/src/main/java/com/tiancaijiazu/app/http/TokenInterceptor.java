package com.tiancaijiazu.app.http;

import android.util.Log;

import com.tiancaijiazu.app.beans.ToKenBean;
import com.tiancaijiazu.app.dao.DataBaseMannger;
import com.tiancaijiazu.app.dao.ToKenDaoBean;
import com.tiancaijiazu.app.globals.Globals;
import com.tiancaijiazu.app.utils.TimeUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2019/5/18/018.
 */

public class TokenInterceptor implements Interceptor {
    private static final String TAG = "TokenInterceptor";
    private boolean isbo = true;
    private boolean mTokenTime;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        mTokenTime = TimeUtil.getTokenTimeRefresh();
        List<ToKenDaoBean> select1 = DataBaseMannger.getIntrance().select();
        Log.d(TAG, "response.code=" + response.code());
        Log.i("ceshitoken", "intercept: "+ mTokenTime);
        if(mTokenTime==true){
            MyCacheinterceptor myCacheinterceptor = new MyCacheinterceptor();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Globals.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(new OkHttpClient().newBuilder().addInterceptor(myCacheinterceptor).build())
                    .build();
            Log.i("ceshitoken", "intercept: "+select1.get(0).getRefresh_token());
            Call<ToKenBean> refreshToken = retrofit.create(ApiServer.class).getRefreshToken(select1.get(0).getRefresh_token());
            ToKenBean body = refreshToken.execute().body();
            Log.i("ceshitoken", "onNext:access_token "+body.getResult().getAccess_token());
            Log.i("ceshitoken", "onNext: msg"+body.getMsg());
            Log.i("ceshitoken", "onNext: refresh_token"+body.getResult().getRefresh_token());
            Log.i("ceshitoken", "onNext: code"+body.getCode());
            Log.i("ceshitoken", "onNext: result"+body.getResult().toString());
            Log.i("ceshitoken", "onNext:expires "+body.getResult().getExpires_in());
            String access_token = body.getResult().getAccess_token();
            int expires_in = body.getResult().getExpires_in();
            String refresh_token = body.getResult().getRefresh_token();
            DataBaseMannger.getIntrance().deleteAll();
            ArrayList<ToKenDaoBean> toKenDaoBeans = new ArrayList<>();
            String nowTime = TimeUtil.getNowTime();
            toKenDaoBeans.add(new ToKenDaoBean(null, access_token, refresh_token, nowTime, expires_in + ""));
            DataBaseMannger.getIntrance().insert(toKenDaoBeans);
                    /*.compose(RxUtils.rxOBserableSchedulerHelper())
                    .subscribe(new Observer<ToKenBean>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }
                        @Override
                        public void onNext(ToKenBean toKenBean) {
                            Log.i("ceshitoken", "onNext:access_token "+toKenBean.getResult().getAccess_token());
                            Log.i("ceshitoken", "onNext: "+toKenBean.getMsg());
                            Log.i("ceshitoken", "onNext: "+toKenBean.getCode());
                            Log.i("ceshitoken", "onNext:expires "+toKenBean.getResult().getExpires_in());
                            String access_token = toKenBean.getResult().getAccess_token();
                            int expires_in = toKenBean.getResult().getExpires_in();
                            String refresh_token = toKenBean.getResult().getRefresh_token();
                            DataBaseMannger.getIntrance().deleteAll();
                            ArrayList<ToKenDaoBean> toKenDaoBeans = new ArrayList<>();
                            String nowTime = TimeUtil.getNowTime();
                            toKenDaoBeans.add(new ToKenDaoBean(null, access_token, refresh_token, nowTime, expires_in + ""));
                            DataBaseMannger.getIntrance().insert(toKenDaoBeans);

                            mTokenTime = false;
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });*/
            mTokenTime = false;
        }

        List<ToKenDaoBean> select = DataBaseMannger.getIntrance().select();
        if (select.size() != 0) {
            String access_token = select.get(select.size() - 1).getAccess_token();
            String refresh_token = select.get(select.size() - 1).getRefresh_token();
            Log.i(TAG, "intercept: " + access_token);
            Log.i(TAG, "intercept: "+refresh_token);
            Request authorization = request.newBuilder()
                    .header("Authorization", "Bearer " + access_token)
                    .build();
            return chain.proceed(authorization);
        } else {
            return response;
        }
    }

    //缓存拦截器
    private class MyCacheinterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);
            List<ToKenDaoBean> select = DataBaseMannger.getIntrance().select();
            if (select.size() != 0) {
                String access_token = select.get(select.size() - 1).getAccess_token();
                String refresh_token = select.get(select.size() - 1).getRefresh_token();
                //Log.i(TAG, "intercept: " + access_token);
                //Log.i(TAG, "intercept: " + refresh_token);
                Request authorization = request.newBuilder()
                        .header("Authorization", "Bearer " + access_token)
                        .build();
                return chain.proceed(authorization);
            } else {
                return response;
            }

        }

        /**
         * 根据Response，判断Token是否失效
         *
         * @param response
         * @return
         */
        private boolean isTokenExpired(Response response) {
            List<ToKenDaoBean> select = DataBaseMannger.getIntrance().select();
            int validtime = Integer.parseInt(select.get(select.size() - 1).getValidtime());
            String time = select.get(select.size() - 1).getTime();
            String nowTime = TimeUtil.getNowTime();
            String timeDifference = TimeUtil.getTimeDifference(time, nowTime);
            String timeDifferenceHour = TimeUtil.getTimeDifferenceHour(time, nowTime);
            TimeUtil.getTimeExpend(time, nowTime);
            Log.i(TAG, "intercept: " + timeDifference);
            Log.i(TAG, "intercept: " + timeDifferenceHour);
            int i = TimeUtil.dataOneMiao(timeDifference);
            long l = TimeUtil.dataOne(timeDifference);
            Log.i(TAG, "intercept: " + l);
            Log.i("yx", i + "intercept: " + validtime);
            if (validtime - i <= 1200) {
                return true;
            }
            return false;
        }

    }
}

