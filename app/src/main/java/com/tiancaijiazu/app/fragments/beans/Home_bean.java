package com.tiancaijiazu.app.fragments.beans;

/**
 * Created by Administrator on 2019/4/27/027.
 */

public class Home_bean {

    private String title;
    private String subhead;

    public Home_bean(String title, String subhead) {
        this.title = title;
        this.subhead = subhead;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubhead() {
        return subhead;
    }

    public void setSubhead(String subhead) {
        this.subhead = subhead;
    }
}
