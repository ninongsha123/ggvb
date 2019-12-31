package com.tiancaijiazu.app.activitys.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2019/8/24/024.
 */

public class CouponBean implements Serializable {

    /**
     * code : 0
     * msg : OK
     * result : [{"couponId":41743220190875648,"userId":10558706915872768,"title":"家庭早教课程专享代金券","couponFee":1000,"effectiveIn":"2019-08-24 14:42:39","expiresIn":"2020-08-24 14:42:39","tradeType":1,"tradeSummary":"限购买家庭早教课程","feeMin":1980,"tradeTime":"","orderId":-1,"creatTime":"2019-08-24 14:42:39","status":1}]
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
         * couponId : 41743220190875648
         * userId : 10558706915872768
         * title : 家庭早教课程专享代金券
         * couponFee : 1000.0
         * effectiveIn : 2019-08-24 14:42:39
         * expiresIn : 2020-08-24 14:42:39
         * tradeType : 1
         * tradeSummary : 限购买家庭早教课程
         * feeMin : 1980.0
         * tradeTime :
         * orderId : -1
         * creatTime : 2019-08-24 14:42:39
         * status : 1
         */

        private long couponId;
        private long userId;
        private String title;
        private double couponFee;
        private String effectiveIn;
        private String expiresIn;
        private int tradeType;
        private String tradeSummary;
        private double feeMin;
        private String tradeTime;
        private int orderId;
        private String creatTime;
        private int status;

        public long getCouponId() {
            return couponId;
        }

        public void setCouponId(long couponId) {
            this.couponId = couponId;
        }

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public double getCouponFee() {
            return couponFee;
        }

        public void setCouponFee(double couponFee) {
            this.couponFee = couponFee;
        }

        public String getEffectiveIn() {
            return effectiveIn;
        }

        public void setEffectiveIn(String effectiveIn) {
            this.effectiveIn = effectiveIn;
        }

        public String getExpiresIn() {
            return expiresIn;
        }

        public void setExpiresIn(String expiresIn) {
            this.expiresIn = expiresIn;
        }

        public int getTradeType() {
            return tradeType;
        }

        public void setTradeType(int tradeType) {
            this.tradeType = tradeType;
        }

        public String getTradeSummary() {
            return tradeSummary;
        }

        public void setTradeSummary(String tradeSummary) {
            this.tradeSummary = tradeSummary;
        }

        public double getFeeMin() {
            return feeMin;
        }

        public void setFeeMin(double feeMin) {
            this.feeMin = feeMin;
        }

        public String getTradeTime() {
            return tradeTime;
        }

        public void setTradeTime(String tradeTime) {
            this.tradeTime = tradeTime;
        }

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public String getCreatTime() {
            return creatTime;
        }

        public void setCreatTime(String creatTime) {
            this.creatTime = creatTime;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
