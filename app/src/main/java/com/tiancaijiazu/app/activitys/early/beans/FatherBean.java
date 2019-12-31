package com.tiancaijiazu.app.activitys.early.beans;

import java.util.ArrayList;

/**
 * Created by Administrator on 2019/7/6/006.
 */

public class FatherBean {
    private String title;
    private ArrayList<ChildrenBean> mChildrenBeans;

    public FatherBean(String title, ArrayList<ChildrenBean> childrenBeans) {
        this.title = title;
        mChildrenBeans = childrenBeans;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<ChildrenBean> getChildrenBeans() {
        return mChildrenBeans;
    }

    public void setChildrenBeans(ArrayList<ChildrenBean> childrenBeans) {
        mChildrenBeans = childrenBeans;
    }
}
