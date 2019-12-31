package com.tiancaijiazu.app.base.fragment;

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

public abstract class BaseFragment<V, P extends BasePresenter<V>> extends SimpleFragment implements BaseView {
    //P层的对象
    public P mPresenter;
    private GifImageView mGifIv;

    @Override
    public void viewCread(View view) {
        super.viewCread(view);//先执行SimpleActivity中的viewCreated的方法
        //找到动画的布局
        View inflate = View.inflate(mContext, R.layout.to_load_layout, (ViewGroup) view);
        //通过动画的ID找到控件
        mGifIv = inflate.findViewById(R.id.gif_iv);
        if (mPresenter == null) {//判断P层对象为null
            mPresenter = createPresenter();//定义子类的P层对象
            if (mPresenter != null) {//判断P层对象不会null
                mPresenter.attachView((V) this);//绑定View
            }
        }
    }

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


    //创建子类的P层对象
    protected abstract P createPresenter();

    @Override
    public void onDestroyView() {
        super.onDestroyView();//先执行SimpleActivity中的onDestroy()
        if (mPresenter != null) {//判断P层对象不等于空
            mPresenter.detachView();//解绑View
            mPresenter = null;//将P层定义为空
        }
    }
}
