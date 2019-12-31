package com.tiancaijiazu.app.beans;

/**
 * Message: TextMode
 * Author: 韩甲龙
 * Time: 2019/6/6 0006 上午 10:57
 */
public class TextBean {

    private String name;
    private boolean isChoose; //是否选中

    public TextBean(String name, boolean isChoose) {
        this.name = name;
        this.isChoose = isChoose;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
    }
}
