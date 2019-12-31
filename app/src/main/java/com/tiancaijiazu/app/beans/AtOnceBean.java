package com.tiancaijiazu.app.beans;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/6/26/026.
 */

public class AtOnceBean implements Serializable{

    private String name;
    private String colour;
    private String imgurl;
    private double price;
    private int sum;
    private long stockId;
    private long skuId;

    public AtOnceBean(String name, String colour, String imgurl, double price, int sum, long stockId, long skuId) {
        this.name = name;
        this.colour = colour;
        this.imgurl = imgurl;
        this.price = price;
        this.sum = sum;
        this.stockId = stockId;
        this.skuId = skuId;
    }

    public long getSkuId() {
        return skuId;
    }

    public void setSkuId(long skuId) {
        this.skuId = skuId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }


    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public long getStockId() {
        return stockId;
    }

    public void setStockId(long stockId) {
        this.stockId = stockId;
    }
}
