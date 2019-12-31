package com.tiancaijiazu.app.beans;

import java.util.List;

/**
 * Created by Administrator on 2019/7/1/001.
 */

public class AlreadyBoughtCourse {
    /**
     * code : 0
     * msg : OK
     * result : [{"totalFee":0.01,"actuallyPaid":0.01,"itemUnits":0,"picList":"http://img.tiancaijiazu.com/course.jpg","courseTitle":"好好说话，幸福生活","orderType":1,"courseId":1}]
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
         * totalFee : 0.01
         * actuallyPaid : 0.01
         * itemUnits : 0
         * picList : http://img.tiancaijiazu.com/course.jpg
         * courseTitle : 好好说话，幸福生活
         * orderType : 1
         * courseId : 1
         */

        private double totalFee;
        private double actuallyPaid;
        private int itemUnits;
        private String picList;
        private String courseTitle;
        private int orderType;
        private long courseId;

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
