package com.tiancaijiazu.app.activitys.bean;

/**
 * Created by Administrator on 2019/5/17/017.
 */

public class Bean {
    String name;
    String phone;
    String diqu;
    String dizhi;

    public Bean(String name, String phone, String diqu, String dizhi) {
        this.name = name;
        this.phone = phone;
        this.diqu = diqu;
        this.dizhi = dizhi;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDiqu() {
        return diqu;
    }

    public void setDiqu(String diqu) {
        this.diqu = diqu;
    }

    public String getDizhi() {
        return dizhi;
    }

    public void setDizhi(String dizhi) {
        this.dizhi = dizhi;
    }
}
