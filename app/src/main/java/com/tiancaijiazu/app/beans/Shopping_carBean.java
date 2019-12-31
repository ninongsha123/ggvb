package com.tiancaijiazu.app.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2019/6/19/019.
 */

public class Shopping_carBean implements Serializable{


    /**
     * code : 0
     * msg : OK
     * result : [{"skuId":33454441814429696,"stockId":33454441864761344,"productName":"测试商品","specName":"浅蓝色;XXL","price":0.01,"oldPrice":0.01,"quantity":1,"picUri":"http://img.tiancaijiazu.com/2019/08/01/33473425716678656_330x330.jpg?330,330"},{"skuId":33473891951316992,"stockId":33473891989065728,"productName":"儿童人工智能机器人多功能玩具语音对话早教机学习益智教育故事机","specName":"浅蓝色;XXL","price":0.01,"oldPrice":0.01,"quantity":1,"picUri":"http://img.tiancaijiazu.com/2019/08/01/33473794324697088_330x330.jpg?330,330"},{"skuId":0,"stockId":1,"productName":"","specName":"","price":0,"oldPrice":0,"quantity":3,"picUri":""},{"skuId":0,"stockId":2,"productName":"","specName":"","price":0,"oldPrice":0,"quantity":6,"picUri":""}]
     */

    private String code;
    private String msg;
    private List<ResultBean> result;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean implements Serializable{
        /**
         * skuId : 33454441814429696
         * stockId : 33454441864761344
         * productName : 测试商品
         * specName : 浅蓝色;XXL
         * price : 0.01
         * oldPrice : 0.01
         * quantity : 1
         * picUri : http://img.tiancaijiazu.com/2019/08/01/33473425716678656_330x330.jpg?330,330
         */

        private long skuId;
        private long stockId;
        private String productName;
        private String specName;
        private double price;
        private double oldPrice;
        private int quantity;
        private String picUri;

        public long getSkuId() {
            return skuId;
        }

        public void setSkuId(long skuId) {
            this.skuId = skuId;
        }

        public long getStockId() {
            return stockId;
        }

        public void setStockId(long stockId) {
            this.stockId = stockId;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getSpecName() {
            return specName;
        }

        public void setSpecName(String specName) {
            this.specName = specName;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public double getOldPrice() {
            return oldPrice;
        }

        public void setOldPrice(double oldPrice) {
            this.oldPrice = oldPrice;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public String getPicUri() {
            return picUri;
        }

        public void setPicUri(String picUri) {
            this.picUri = picUri;
        }
    }
}
