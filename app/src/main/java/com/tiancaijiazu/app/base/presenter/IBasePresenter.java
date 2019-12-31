package com.tiancaijiazu.app.base.presenter;



import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2019/4/22/022.
 */

public class IBasePresenter<V> implements BasePresenter<V> {
    //弱引用
    private WeakReference<V> mWeakReference;
    //View对象，用于绑定
    public V mView;


    @Override
    public void attachView(V v) {
        if (mWeakReference == null) {
            //创建弱引用对象，并将View放入包裹
            mWeakReference = new WeakReference<V>(v);
            //得到弱引用包裹的View对象
            mView = mWeakReference.get();
        }
    }

    @Override
    public void detachView() {
        if (mWeakReference != null && mWeakReference.get() != null) {
            //清除弱引用里的包裹内容
            mWeakReference.clear();
            //赋值弱引用为空
            mWeakReference = null;
        }
    }
}
