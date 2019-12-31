package com.tiancaijiazu.app.mvp;


import android.util.Log;

import com.tiancaijiazu.app.app.App;
import com.tiancaijiazu.app.base.presenter.IBasePresenter;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.mvp.Module;
import com.tiancaijiazu.app.utils.cache.SystemUtil;

import com.tiancaijiazu.app.utils.status.LoadingLayout;
import com.tiancaijiazu.app.mvp.IView;

/**
 * Created by Administrator on 2019/4/22/022.
 */

public class Presenter<V extends IView> extends IBasePresenter<V> implements Module.Callback {
    private LoadingLayout loadingLayout;
    //创建Modle层实例化对象
    private Module mModule = new Module();

    @Override
    public void setAnimation() {
        //向V层发送显示Progressbar信号
        mView.showAnimation();
    }

    @Override
    public void setHidoAnimation() {
        //向V层发送隐藏Progressbar信号
        mView.hidoAnimation();
    }

    //使M层与V关联
    public void getDataP(Object obj, DifferentiateEnum differentiateEnum) {
        if (mView != null) {//判断V层不为空
            //向M层发送数据
            mModule.getData(this, obj, differentiateEnum);
        }
    }

    //使M层与V关联
    public void getDataP1(Object obj, DifferentiateEnum differentiateEnum, LoadingLayout loadingLayout) {
        this.loadingLayout = loadingLayout;
        boolean networkConnected =
                SystemUtil.getInstance(App.getApplication()).isNetworkConnected();
        Log.i("asdfg", "getDataP1: "+networkConnected);
        if (mView != null) {//判断V层不为空
            if (!networkConnected) {
                loadingLayout.setStatus(LoadingLayout.No_Network);
            } else {
                //向M层发送数据
                mModule.getData(this, obj, differentiateEnum);
            }
        }
    }


    @Override
    public void setError(String error) {
        //向V层发送错误信息
        if (mView != null) {
            if(loadingLayout!=null){
                loadingLayout.setStatus(LoadingLayout.No_Network);
            }
            mView.showError(error);
        }
    }

    @Override
    public void setData(Object data, DifferentiateEnum differentiateEnum) {
        if (mView != null) {
            if(loadingLayout!=null){
                loadingLayout.setStatus(LoadingLayout.Success);
            }
            //向V层发送数据
            mView.show(data, differentiateEnum);
        }
    }
}
