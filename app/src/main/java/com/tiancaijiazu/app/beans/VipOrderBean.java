package com.tiancaijiazu.app.beans;

/**
 * Created by Administrator on 2019/8/25/025.
 */

public class VipOrderBean {
    /**
     * code : 0
     * msg : OK
     * result : {"orderId":42036966266638336,"status":0,"createTime":"2019-08-25 10:09:53.767","paidTime":"","payType":0,"totalFee":0.01,"actuallyPaid":0,"vipLevel":11,"vipTitle":"VIP会员"}
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
         * orderId : 42036966266638336
         * status : 0
         * createTime : 2019-08-25 10:09:53.767
         * paidTime :
         * payType : 0
         * totalFee : 0.01
         * actuallyPaid : 0.0
         * vipLevel : 11
         * vipTitle : VIP会员
         */

        private long orderId;
        private int status;
        private String createTime;
        private String paidTime;
        private int payType;
        private double totalFee;
        private double actuallyPaid;
        private int vipLevel;
        private String vipTitle;

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

        public int getVipLevel() {
            return vipLevel;
        }

        public void setVipLevel(int vipLevel) {
            this.vipLevel = vipLevel;
        }

        public String getVipTitle() {
            return vipTitle;
        }

        public void setVipTitle(String vipTitle) {
            this.vipTitle = vipTitle;
        }
    }
}
