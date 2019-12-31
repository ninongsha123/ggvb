package com.tiancaijiazu.app.http;




import android.util.Log;

import com.tiancaijiazu.app.base.module.HttpFinishCallBack;

import org.json.JSONException;

import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * Created by Administrator on 2019/4/22/022.
 */

public abstract class BaseObserver<T> implements Observer<T> {
    //回调结果处理
    private HttpFinishCallBack mHttpFinishCallBack;

    public BaseObserver(HttpFinishCallBack httpFinishCallBack) {
        mHttpFinishCallBack = httpFinishCallBack;
    }

    //管理内存网络请求
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    //当Observable被订阅(subscribe) OnSubscribe接口的call方法会被执行
    @Override
    public void onSubscribe(Disposable d) {
        mCompositeDisposable.add(d);
    }

    //网络请求错误的时候
    @Override
    public void onError(Throwable e) {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();//如果内存里有数据就清除
        }
        if (mHttpFinishCallBack != null) {//接口回调有值的时候

            Log.i("MyError", "onError: "+e.getMessage());
            if (e instanceof HttpException) {//请求失败的时候
                mHttpFinishCallBack.setError("网络请求错误,请稍后再试!");//返回失败异常
                Log.i("yx", "onError: "+((HttpException) e).code());
            }else if(e instanceof JSONException){
                mHttpFinishCallBack.setError("解析错误");
            }else if(e instanceof TimeoutException){
                mHttpFinishCallBack.setError("超时异常");
            }else if(e instanceof SocketTimeoutException){
                mHttpFinishCallBack.setError("套接字超时异常");
            }else {
                mHttpFinishCallBack.setError("其他请求错误");
            }
            mHttpFinishCallBack.setHidoAnimation();
        }
    }

    //请求完成的时候
    @Override
    public void onComplete() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();//如果内存里有数据就清除
        }
    }
}
