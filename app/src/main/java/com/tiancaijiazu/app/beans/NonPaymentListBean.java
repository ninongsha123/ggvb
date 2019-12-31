package com.tiancaijiazu.app.beans;

import java.util.List;

/**
 * Created by Administrator on 2019/6/21/021.
 */

public class NonPaymentListBean {

    /**
     * code : 0
     * msg : OK
     * result : [{"orderId":24333728163696640,"status":0,"totalFee":0,"actuallyPaid":0,"itemUnits":1,"picList":"http://img.tiancaijiazu.com/123.jpg","productName":"男童套装2019夏季足球服儿童运动套装TCJZ168浅蓝色165(适合身高160-165cm)"},{"orderId":22114435929870336,"status":0,"totalFee":0.01,"actuallyPaid":0,"itemUnits":1,"picList":"http://img.tiancaijiazu.com/123.jpg","productName":"男童套装2019夏季足球服儿童运动套装TCJZ168浅蓝色150(适合身高145-155cm)"},{"orderId":21582135567716352,"status":0,"totalFee":0.02,"actuallyPaid":0.02,"itemUnits":2,"picList":"http://img.tiancaijiazu.com/123.jpg,123.jpg","productName":"男童套装2019夏季足球服儿童运动套装TCJZ168浅蓝色150(适合身高145-155cm)"},{"orderId":21537709239701504,"status":0,"totalFee":0.01,"actuallyPaid":0.01,"itemUnits":1,"picList":"http://img.tiancaijiazu.com/123.jpg","productName":"男童套装2019夏季足球服儿童运动套装TCJZ168浅蓝色150(适合身高145-155cm)"},{"orderId":21522024568590336,"status":0,"totalFee":0.01,"actuallyPaid":0.01,"itemUnits":1,"picList":"http://img.tiancaijiazu.com/123.jpg","productName":"男童套装2019夏季足球服儿童运动套装TCJZ168浅蓝色150(适合身高145-155cm)"},{"orderId":21516574238314496,"status":0,"totalFee":0.01,"actuallyPaid":0.01,"itemUnits":1,"picList":"http://img.tiancaijiazu.com/123.jpg","productName":"男童套装2019夏季足球服儿童运动套装TCJZ168浅蓝色150(适合身高145-155cm)"},{"orderId":21515415922544640,"status":0,"totalFee":0.01,"actuallyPaid":0.01,"itemUnits":1,"picList":"http://img.tiancaijiazu.com/123.jpg","productName":"男童套装2019夏季足球服儿童运动套装TCJZ168浅蓝色150(适合身高145-155cm)"},{"orderId":21506796854317056,"status":0,"totalFee":0.01,"actuallyPaid":0.01,"itemUnits":1,"picList":"http://img.tiancaijiazu.com/123.jpg","productName":"男童套装2019夏季足球服儿童运动套装TCJZ168浅蓝色150(适合身高145-155cm)"},{"orderId":21505440655478784,"status":0,"totalFee":0.01,"actuallyPaid":0.01,"itemUnits":1,"picList":"http://img.tiancaijiazu.com/123.jpg","productName":"男童套装2019夏季足球服儿童运动套装TCJZ168浅蓝色150(适合身高145-155cm)"},{"orderId":21504188332773376,"status":0,"totalFee":0.01,"actuallyPaid":0.01,"itemUnits":1,"picList":"http://img.tiancaijiazu.com/123.jpg","productName":"男童套装2019夏季足球服儿童运动套装TCJZ168浅蓝色165(适合身高160-165cm)"},{"orderId":21503485480669184,"status":0,"totalFee":0.01,"actuallyPaid":0.01,"itemUnits":1,"picList":"http://img.tiancaijiazu.com/123.jpg","productName":"男童套装2019夏季足球服儿童运动套装TCJZ168浅蓝色165(适合身高160-165cm)"},{"orderId":21497662532620288,"status":0,"totalFee":0.01,"actuallyPaid":0.01,"itemUnits":1,"picList":"http://img.tiancaijiazu.com/123.jpg","productName":"男童套装2019夏季足球服儿童运动套装TCJZ168浅蓝色150(适合身高145-155cm)"}]
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

    public static class ResultBean {
        /**
         * orderId : 24333728163696640
         * status : 0
         * totalFee : 0.0
         * actuallyPaid : 0.0
         * itemUnits : 1
         * picList : http://img.tiancaijiazu.com/123.jpg
         * productName : 男童套装2019夏季足球服儿童运动套装TCJZ168浅蓝色165(适合身高160-165cm)
         */

        private long orderId;
        private int status;
        private double totalFee;
        private double actuallyPaid;
        private int itemUnits;
        private String picList;
        private String productName;

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
    }
}
