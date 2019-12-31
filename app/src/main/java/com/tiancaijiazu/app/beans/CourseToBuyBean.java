package com.tiancaijiazu.app.beans;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/6/26/026.
 */

public class CourseToBuyBean implements Serializable{
    /**
     * code : 0
     * msg : OK
     * result : {"orderId":20395309330993152,"status":0,"createTime":"2019-06-26 16:53:40.671","paidTime":"","payType":0,"totalFee":198,"actuallyPaid":0,"consigneeName":"","consigneeMobile":"","consigneeAddress":"","itemUnits":0,"picList":"http://192.168.0.200/images/course.jpg","courseTitle":"好好说话，幸福生活","orderType":0,"courseId":1}
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
         * orderId : 20395309330993152
         * status : 0
         * createTime : 2019-06-26 16:53:40.671
         * paidTime :
         * payType : 0
         * totalFee : 198
         * actuallyPaid : 0
         * consigneeName :
         * consigneeMobile :
         * consigneeAddress :
         * itemUnits : 0
         * picList : http://192.168.0.200/images/course.jpg
         * courseTitle : 好好说话，幸福生活
         * orderType : 0
         * courseId : 1
         */

        private long orderId;
        private int status;
        private String createTime;
        private String paidTime;
        private int payType;
        private float totalFee;
        private float actuallyPaid;
        private String consigneeName;
        private String consigneeMobile;
        private String consigneeAddress;
        private int itemUnits;
        private String picList;
        private String courseTitle;
        private int orderType;
        private long courseId;

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

        public float getTotalFee() {
            return totalFee;
        }

        public void setTotalFee(float totalFee) {
            this.totalFee = totalFee;
        }

        public float getActuallyPaid() {
            return actuallyPaid;
        }

        public void setActuallyPaid(float actuallyPaid) {
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

        public String getCourseTitle() {
            return courseTitle;
        }

        public void setCourseTitle(String courseTitle) {
            this.courseTitle = courseTitle;
        }

        public int getOrderType() {
            return orderType;
        }

        public void setOrderType(int orderType) {
            this.orderType = orderType;
        }

        public long getCourseId() {
            return courseId;
        }

        public void setCourseId(long courseId) {
            this.courseId = courseId;
        }
    }
}
