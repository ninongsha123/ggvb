package com.tiancaijiazu.app.base.activity;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.base.presenter.BasePresenter;
import com.tiancaijiazu.app.base.view.BaseView;


import pl.droidsonroids.gif.GifImageView;


/**
 * Created by Administrator on 2019/4/22/022.
 */

public abstract class BaseActivity<V, P extends BasePresenter<V>> extends SimpleActivity implements BaseView {

    //P层的对象
    public P mPresenter;
    private GifImageView mGifIv;
    //绑定布局
    @Override
    public void viewCreated(View view) {
        super.viewCreated(view);//先执行SimpleActivity中的viewCreated的方法
        //找到动画的布局
        View inflate = View.inflate(this, R.layout.to_load_layout, (ViewGroup) view);
        //通过动画的ID找到控件
        mGifIv = inflate.findViewById(R.id.gif_iv);
        //定义子类的P层对象
        mPresenter = createPresenter();
        if (mPresenter != null) {//判断P层对象不会null
            mPresenter.attachView((V) this);//绑定View
        }
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.fontScale != 1)//非默认值
            getResources();
        super.onConfigurationChanged(newConfig);
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
    //创建子类的P层对象
    protected abstract P createPresenter();

    //开启动画
    @Override
    public void showAnimation() {
        mGifIv.setVisibility(View.VISIBLE);
    }

    //关闭动画
    @Override
    public void hidoAnimation() {
        mGifIv.setVisibility(View.GONE);
    }

    //工作线程的销毁和停止的时候执行
    @Override
    protected void onDestroy() {
        super.onDestroy();//先执行SimpleActivity中的onDestroy()
        if (mPresenter != null) {//判断P层对象不等于空
            mPresenter.detachView();//解绑View
            mPresenter = null;//将P层定义为空
        }
    }
}
