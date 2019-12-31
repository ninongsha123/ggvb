package com.tiancaijiazu.app.activitys.early.beans;

/**
 * Created by Administrator on 2019/7/6/006.
 */

public class ChildrenBean {
    private String iv_nice;
    private String title;
    private String data1;
    private String data2;

    public ChildrenBean(String iv_nice, String title, String data1, String data2) {
        this.iv_nice = iv_nice;
        this.title = title;
        this.data1 = data1;
        this.data2 = data2;
    }

    public String getIv_nice() {
        return iv_nice;
    }

    public void setIv_nice(String iv_nice) {
        this.iv_nice = iv_nice;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getData1() {
        return data1;
    }

    public void setData1(String data1) {
        this.data1 = data1;
    }

    public String getData2() {
        return data2;
    }

    public void setData2(String data2) {
        this.data2 = data2;
    }
}
