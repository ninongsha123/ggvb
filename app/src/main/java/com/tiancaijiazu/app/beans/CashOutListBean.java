package com.tiancaijiazu.app.beans;

import java.util.List;

public class CashOutListBean {

    /**
     * code : 0
     * msg : OK
     * result : [{"date":"2019-11-26","items":[{"tradeId":75819273687273472,"cardId":53348069114253312,"cardNo":"48948489151212","bank":"工商银行","city":"156152e123123","openingBank":"51561312312213","mobile":"15010245227","name":"156112113r2","idNo":"5152131215645646","cash":10000,"applyTime":"2019-11-26 15:28:43.501","grantTime":""},{"tradeId":75819250333388800,"cardId":53348069114253312,"cardNo":"48948489151212","bank":"工商银行","city":"156152e123123","openingBank":"51561312312213","mobile":"15010245227","name":"156112113r2","idNo":"5152131215645646","cash":200,"applyTime":"2019-11-26 15:28:37.933","grantTime":""},{"tradeId":75819171962818560,"cardId":53348069114253312,"cardNo":"48948489151212","bank":"工商银行","city":"156152e123123","openingBank":"51561312312213","mobile":"15010245227","name":"156112113r2","idNo":"5152131215645646","cash":20,"applyTime":"2019-11-26 15:28:19.248","grantTime":""},{"tradeId":75818377721024512,"cardId":53348069114253312,"cardNo":"48948489151212","bank":"工商银行","city":"156152e123123","openingBank":"51561312312213","mobile":"15010245227","name":"156112113r2","idNo":"5152131215645646","cash":20,"applyTime":"2019-11-26 15:25:09.886","grantTime":""}]}]
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
         * date : 2019-11-26
         * items : [{"tradeId":75819273687273472,"cardId":53348069114253312,"cardNo":"48948489151212","bank":"工商银行","city":"156152e123123","openingBank":"51561312312213","mobile":"15010245227","name":"156112113r2","idNo":"5152131215645646","cash":10000,"applyTime":"2019-11-26 15:28:43.501","grantTime":""},{"tradeId":75819250333388800,"cardId":53348069114253312,"cardNo":"48948489151212","bank":"工商银行","city":"156152e123123","openingBank":"51561312312213","mobile":"15010245227","name":"156112113r2","idNo":"5152131215645646","cash":200,"applyTime":"2019-11-26 15:28:37.933","grantTime":""},{"tradeId":75819171962818560,"cardId":53348069114253312,"cardNo":"48948489151212","bank":"工商银行","city":"156152e123123","openingBank":"51561312312213","mobile":"15010245227","name":"156112113r2","idNo":"5152131215645646","cash":20,"applyTime":"2019-11-26 15:28:19.248","grantTime":""},{"tradeId":75818377721024512,"cardId":53348069114253312,"cardNo":"48948489151212","bank":"工商银行","city":"156152e123123","openingBank":"51561312312213","mobile":"15010245227","name":"156112113r2","idNo":"5152131215645646","cash":20,"applyTime":"2019-11-26 15:25:09.886","grantTime":""}]
         */

        private String date;
        private List<ItemsBean> items;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public static class ItemsBean {
            /**
             * tradeId : 75819273687273472
             * cardId : 53348069114253312
             * cardNo : 48948489151212
             * bank : 工商银行
             * city : 156152e123123
             * openingBank : 51561312312213
             * mobile : 15010245227
             * name : 156112113r2
             * idNo : 5152131215645646
             * cash : 10000.0
             * applyTime : 2019-11-26 15:28:43.501
             * grantTime :
             */

            private long tradeId;
            private long cardId;
            private String cardNo;
            private String bank;
            private String city;
            private String openingBank;
            private String mobile;
            private String name;
            private String idNo;
            private double cash;
            private String applyTime;
            private String grantTime;

            public long getTradeId() {
                return tradeId;
            }

            public void setTradeId(long tradeId) {
                this.tradeId = tradeId;
            }

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

            public double getCash() {
                return cash;
            }

            public void setCash(double cash) {
                this.cash = cash;
            }

            public String getApplyTime() {
                return applyTime;
            }

            public void setApplyTime(String applyTime) {
                this.applyTime = applyTime;
            }

            public String getGrantTime() {
                return grantTime;
            }

            public void setGrantTime(String grantTime) {
                this.grantTime = grantTime;
            }
        }
    }
}
