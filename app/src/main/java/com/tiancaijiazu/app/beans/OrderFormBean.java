package com.tiancaijiazu.app.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2019/6/22/022.
 */

public class OrderFormBean implements Serializable{

    /**
     * code : 0
     * msg : OK
     * result : {"orderId":34072368519450624,"status":0,"createTime":"2019-08-03 10:41:25.689","paidTime":"","payType":0,"totalFee":0,"actuallyPaid":0,"consigneeName":"杨旭","consigneeMobile":"15010565227","consigneeAddress":"北京市-海淀区们","itemUnits":1,"picList":"http://img.tiancaijiazu.com/2019/08/02/33729362838818816_330x330.jpg?330,330","productName":"测试商品0803|浅蓝色;XXL;国标","itemList":[{"skuId":33454441814429696,"stockId":34068064815419392,"quantity":1,"productName":"测试商品0803","specName":"浅蓝色;XXL;国标","price":0,"picUri":"http://img.tiancaijiazu.com/2019/08/02/33729362838818816_330x330.jpg?330,330"}]}
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

    public static class ResultBean implements Serializable{
        /**
         * orderId : 34072368519450624
         * status : 0
         * createTime : 2019-08-03 10:41:25.689
         * paidTime :
         * payType : 0
         * totalFee : 0.0
         * actuallyPaid : 0.0
         * consigneeName : 杨旭
         * consigneeMobile : 15010565227
         * consigneeAddress : 北京市-海淀区们
         * itemUnits : 1
         * picList : http://img.tiancaijiazu.com/2019/08/02/33729362838818816_330x330.jpg?330,330
         * productName : 测试商品0803|浅蓝色;XXL;国标
         * itemList : [{"skuId":33454441814429696,"stockId":34068064815419392,"quantity":1,"productName":"测试商品0803","specName":"浅蓝色;XXL;国标","price":0,"picUri":"http://img.tiancaijiazu.com/2019/08/02/33729362838818816_330x330.jpg?330,330"}]
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

        public List<ItemListBean> getItemList() {
            return itemList;
        }

        public void setItemList(List<ItemListBean> itemList) {
            this.itemList = itemList;
        }

        public static class ItemListBean implements Serializable{
            /**
             * skuId : 33454441814429696
             * stockId : 34068064815419392
             * quantity : 1
             * productName : 测试商品0803
             * specName : 浅蓝色;XXL;国标
             * price : 0.0
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
