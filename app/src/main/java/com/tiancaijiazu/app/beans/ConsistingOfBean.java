package com.tiancaijiazu.app.beans;

/**
 * Created by Administrator on 2019/5/28/028.
 */

public class ConsistingOfBean {

    private String title;

    private Integer iv_title;

    public ConsistingOfBean(String title, Integer iv_title) {
        this.title = title;
        this.iv_title = iv_title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getIv_title() {
        return iv_title;
    }

    public void setIv_title(Integer iv_title) {
        this.iv_title = iv_title;
    }
}
