package com.tiancaijiazu.app.beans;

import java.io.Serializable;

public class BankcardInfoBeans implements Serializable {


    /**
     * code : 0
     * msg : OK
     * result : {"cardId":53348069114253312,"cardNo":"48948489151212","bank":"工商银行","city":"156152e123123","openingBank":"51561312312213","mobile":"15010245227","name":"156112113r2","idNo":"5152131215645646"}
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
         * cardId : 53348069114253312
         * cardNo : 48948489151212
         * bank : 工商银行
         * city : 156152e123123
         * openingBank : 51561312312213
         * mobile : 15010245227
         * name : 156112113r2
         * idNo : 5152131215645646
         */

        private long cardId;
        private String cardNo;
        private String bank;
        private String city;
        private String openingBank;
        private String mobile;
        private String name;
        private String idNo;

        public long getCardId() {
            return cardId;
        }

        public void setCardId(long cardId) {
            this.cardId = cardId;
        }

        public String getCardNo() {
            return cardNo;
        }

        public void setCardNo(String cardNo) {
            this.cardNo = cardNo;
        }

        public String getBank() {
            return bank;
        }

        public void setBank(String bank) {
            this.bank = bank;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getOpeningBank() {
            return openingBank;
        }

        public void setOpeningBank(String openingBank) {
            this.openingBank = openingBank;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIdNo() {
            return idNo;
        }

        public void setIdNo(String idNo) {
            this.idNo = idNo;
        }
    }
}
