package com.tiancaijiazu.app.base.presenter;


/**
 * Created by Administrator on 2019/4/22/022.
 */

public interface BasePresenter<V> {
    //绑定View
    void attachView(V v);

    //解绑View
    void detachView();
}
