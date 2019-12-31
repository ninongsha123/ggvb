package com.tiancaijiazu.app.mvp;


import com.tiancaijiazu.app.base.view.BaseView;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;

/**
 * Created by Administrator on 2019/4/22/022.
 */

public interface IView<V> extends BaseView {
    //显示的数据
    void show(V v, DifferentiateEnum differentiateEnum);
}
