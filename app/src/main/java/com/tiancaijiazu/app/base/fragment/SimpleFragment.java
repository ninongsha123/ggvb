package com.tiancaijiazu.app.base.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2019/4/22/022.
 */

public abstract class SimpleFragment extends Fragment {
    //fragment上下文定义为全局方便调用
    public Context mContext;
    //定义当前的Activity
    public Activity mActivity;
    //ButterKnife对象
    private Unbinder mBind;


    //当fragment和activity建立关联的时候调用，fragment的生命周期第一个执行onAttach
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //将fragment的上下文转换为Activity建立关联
        mActivity = (Activity) context;
        //赋值上下文
        this.mContext = context;
    }

    //为fragment创建视图调用，在onCreate之后
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //找到fragment绑定的布局
        View view = inflater.inflate(creatrLayoutId(), null, false);
        //绑定布局的方法
        viewCread(view);
        return view;//要显示的布局
    }

    //绑定布局
    public void viewCread(View view) {

    }

    //初始化布局
    protected abstract int creatrLayoutId();

    //onViewCreated在onCreateView执行完后立即执行。 onCreateView返回的就是fragment要显示的view
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //找控件。this为把id控件找到当前的位置。view为布局
        mBind = ButterKnife.bind(this, view);
        //初始化数据的方法
        initData();
    }

    //初始化数据
    protected abstract void initData();

    //懒加载方法
    //setUserVisibleHint(boolean isVisibleToUser) 是当使用viewPager去管理若干fragment的时候进行对于某fragment在显示跟隐藏的时候回调的方法
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        //isVisibleToUser是关联与fragment生命周期的可见性的判断
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {//判断布尔值
            load();//懒加载中初始化数据方法
        }
    }

    //懒加载初始化数据
    public void load() {

    }

    //当与fragment关联的视图被移除的时候调用
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mBind != null) {//判断布局不等于空的时候
            mBind.unbind();//释放资源
        }
    }
}
