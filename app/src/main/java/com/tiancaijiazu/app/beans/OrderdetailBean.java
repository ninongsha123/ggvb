package com.tiancaijiazu.app.beans;

import java.util.List;

/**
 * Created by Administrator on 2019/6/22/022.
 */

public class OrderdetailBean {

    /**
     * code : 0
     * msg : OK
     * result : {"orderId":34824255011164160,"status":1,"createTime":"2019-08-05 12:29:09.398","paidTime":"2019-08-05 12:29:21","payType":1,"totalFee":0.01,"actuallyPaid":0.01,"consigneeName":"聚","consigneeMobile":"15010565227","consigneeAddress":"北京市-海淀区们","itemUnits":1,"picList":"http://img.tiancaijiazu.com/2019/08/02/33729362838818816_330x330.jpg?330,330","productName":"测试商品0803|浅蓝色;XXL;国标","cash_fee":0,"card_fee":0,"couponId":0,"expressCompany":"","trackingNo":"","itemList":[{"skuId":33454441814429696,"stockId":34068064815419392,"quantity":1,"productName":"测试商品0803","specName":"浅蓝色;XXL;国标","price":0.01,"picUri":"http://img.tiancaijiazu.com/2019/08/02/33729362838818816_330x330.jpg?330,330"}]}
     */

    private String code;
    private String msg;
    private ResultBean result;

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

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * orderId : 34824255011164160
         * status : 1
         * createTime : 2019-08-05 12:29:09.398
         * paidTime : 2019-08-05 12:29:21
         * payType : 1
         * totalFee : 0.01
         * actuallyPaid : 0.01
         * consigneeName : 聚
         * consigneeMobile : 15010565227
         * consigneeAddress : 北京市-海淀区们
         * itemUnits : 1
         * picList : http://img.tiancaijiazu.com/2019/08/02/33729362838818816_330x330.jpg?330,330
         * productName : 测试商品0803|浅蓝色;XXL;国标
         * cash_fee : 0.0
         * card_fee : 0.0
         * couponId : 0
         * expressCompany :
         * trackingNo :
         * itemList : [{"skuId":33454441814429696,"stockId":34068064815419392,"quantity":1,"productName":"测试商品0803","specName":"浅蓝色;XXL;国标","price":0.01,"picUri":"http://img.tiancaijiazu.com/2019/08/02/33729362838818816_330x330.jpg?330,330"}]
         */

        private long orderId;
        private int status;
        private String createTime;
        private String paidTime;
        private int payType;
        private double totalFee;
        private double actuallyPaid;
        private String consigneeName;
        private String consigneeMobile;
        private String consigneeAddress;
        private int itemUnits;
        private String picList;
        private String productName;
        private double cash_fee;
        private double card_fee;
        private long couponId;
        private String expressCompany;
        private String trackingNo;
        private List<ItemListBean> itemList;

        public long getOrderId() {
            return orderId;
        }

        public void setOrderId(long orderId) {
            this.orderId = orderId;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getPaidTime() {
            return paidTime;
        }

        public void setPaidTime(String paidTime) {
            this.paidTime = paidTime;
        }

        public int getPayType() {
            return payType;
        }

        public void setPayType(int payType) {
            this.payType = payType;
        }

        public double getTotalFee() {
            return totalFee;
        }

        public void setTotalFee(double totalFee) {
            this.totalFee = totalFee;
        }

        public double getActuallyPaid() {
            return actuallyPaid;
        }

        public void setActuallyPaid(double actuallyPaid) {
            this.actuallyPaid = actuallyPaid;
        }

        public String getConsigneeName() {
            return consigneeName;
        }

        public void setConsigneeName(String consigneeName) {
            this.consigneeName = consigneeName;
        }

        public String getConsigneeMobile() {
            return consigneeMobile;
        }

        public void setConsigneeMobile(String consigneeMobile) {
            this.consigneeMobile = consigneeMobile;
        }

        public String getConsigneeAddress() {
            return consigneeAddress;
        }

        public void setConsigneeAddress(String consigneeAddress) {
            this.consigneeAddress = consigneeAddress;
        }

        public int getItemUnits() {
            return itemUnits;
        }

        public void setItemUnits(int itemUnits) {
            this.itemUnits = itemUnits;
        }

        public String getPicList() {
            return picList;
        }

        public void setPicList(String picList) {
            this.picList = picList;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public double getCash_fee() {
            return cash_fee;
        }

        public void setCash_fee(double cash_fee) {
            this.cash_fee = cash_fee;
        }

        public double getCard_fee() {
            return card_fee;
        }

        public void setCard_fee(double card_fee) {
            this.card_fee = card_fee;
        }

        public long getCouponId() {
            return couponId;
        }

        public void setCouponId(long couponId) {
            this.couponId = couponId;
        }

        public String getExpressCompany() {
            return expressCompany;
        }

        public void setExpressCompany(String expressCompany) {
            this.expressCompany = expressCompany;
        }

        public String getTrackingNo() {
            return trackingNo;
        }

        public void setTrackingNo(String trackingNo) {
            this.trackingNo = trackingNo;
        }

        public List<ItemListBean> getItemList() {
            return itemList;
        }

        public void setItemList(List<ItemListBean> itemList) {
            this.itemList = itemList;
        }

        public static class ItemListBean {
            /**
             * skuId : 33454441814429696
             * stockId : 34068064815419392
             * quantity : 1
             * productName : 测试商品0803
             * specName : 浅蓝色;XXL;国标
             * price : 0.01
             * picUri : http://img.tiancaijiazu.com/2019/08/02/33729362838818816_330x330.jpg?330,330
             */

            private long skuId;
            private long stockId;
            private int quantity;
            private String productName;
            private String specName;
            private double price;
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

            public int getQuantity() {
                return quantity;
            }

            public void setQuantity(int quantity) {
                this.quantity = quantity;
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

            public String getPicUri() {
                return picUri;
            }

            public void setPicUri(String picUri) {
                this.picUri = picUri;
            }
        }
    }
}