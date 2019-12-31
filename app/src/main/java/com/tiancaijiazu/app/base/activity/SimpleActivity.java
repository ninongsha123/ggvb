package com.tiancaijiazu.app.base.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.tiancaijiazu.app.utils.ActivityController;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2019/4/22/022.
 */

public abstract class SimpleActivity extends AppCompatActivity {
    //定义当前的Activity
    public Activity mActivity;
    //ButterKnife对象
    private Unbinder mBind;

    /**
     * onCreate()是在Activity创建时被系统调用，是一个Activity生命周期的开始；
     * 它主要做这个activity启动时一些必要的初始化工作，这个函数调用完后，
     * 这个activity并不是说就已经启动了，或者是跳到前台了
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);//父类onCreate()执行
        //找到Activity绑定的布局
        View view = LayoutInflater.from(this).inflate(creatrLayoutId(), null, false);
        //给当前的Activity绑定view布局
        setContentView(view);
        //找控件。this为把id控件找到当前的位置。view为布局
        mBind = ButterKnife.bind(this, view);
        //赋值mActivity为当前的Activity的环境上下文
        mActivity = this;
        //绑定布局的方法
        viewCreated(view);
        //初始化数据的抽象方法，继承后必须重写
        initEventAndData();
        ActivityController.addActivity(this);
        ActivityController.setCurrentActivity(this);
    }

    //绑定布局
    public void viewCreated(View view) {

    }

    //初始化数据
    protected abstract void initEventAndData();

    //初始化布局
    protected abstract int creatrLayoutId();

    //工作线程的销毁和停止的时候执行
    @Override
    protected void onDestroy() {
        super.onDestroy();//父类的onDestroy()中操作
        if (mBind != null) {//判断布局不等于空的时候
            mBind.unbind();//释放资源
        }
        ActivityController.removeActivity(this);
    }
}
