package com.tiancaijiazu.app.activitys.early;

/**
 * Created by Administrator on 2019/7/5/005.
 */

public class BeanShi {
    private Integer titleColor;
    private Integer dataColor;
    private String title;
    private String data1;
    private String data2;
    private String name1;
    private String name2;

    public BeanShi(Integer titleColor, Integer dataColor, String title, String data1, String data2, String name1, String name2) {
        this.titleColor = titleColor;
        this.dataColor = dataColor;
        this.title = title;
        this.data1 = data1;
        this.data2 = data2;
        this.name1 = name1;
        this.name2 = name2;
    }

    public Integer getTitleColor() {
        return titleColor;
    }

    public void setTitleColor(Integer titleColor) {
        this.titleColor = titleColor;
    }

    public Integer getDataColor() {
        return dataColor;
    }

    public void setDataColor(Integer dataColor) {
        this.dataColor = dataColor;
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

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }
}
