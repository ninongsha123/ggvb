package com.tiancaijiazu.app.beans;

import java.io.Serializable;

public class StudyCardOrderCreateBenas implements Serializable {

    /**
     * code : 0
     * msg : OK
     * result : {"orderId":45471323618676736,"status":0,"totalFee":0.01,"cardType":3,"cardName":"天才家族早教课,半年卡","babyBirthday":"","expiresIn":""}
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
         * orderId : 45471323618676736
         * status : 0
         * totalFee : 0.01
         * cardType : 3
         * cardName : 天才家族早教课,半年卡
         * babyBirthday :
         * expiresIn :
         */

        private long orderId;
        private int status;
        private double totalFee;
        private int cardType;
        private String cardName;
        private String babyBirthday;
        private String expiresIn;

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

        public int getCardType() {
            return cardType;
        }

        public void setCardType(int cardType) {
            this.cardType = cardType;
        }

        public String getCardName() {
            return cardName;
        }

        public void setCardName(String cardName) {
            this.cardName = cardName;
        }

        public String getBabyBirthday() {
            return babyBirthday;
        }

        public void setBabyBirthday(String babyBirthday) {
            this.babyBirthday = babyBirthday;
        }

        public String getExpiresIn() {
            return expiresIn;
        }

        public void setExpiresIn(String expiresIn) {
            this.expiresIn = expiresIn;
        }
    }
}
