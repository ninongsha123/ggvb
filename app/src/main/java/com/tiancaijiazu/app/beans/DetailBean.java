package com.tiancaijiazu.app.beans;

import java.util.List;

public class DetailBean {
    /**
     * code : 0
     * msg : OK
     * result : [{"income":1,"nickname":"138000138000","tradeTitle":"购买商品","tradeTime":"2019-06-05 14:38:07.557"},{"income":1,"nickname":"138000138000","tradeTitle":"购买课程","tradeTime":"2019-07-05 14:38:07.557"},{"income":1,"nickname":"138000138000","tradeTitle":"升级总经理","tradeTime":"2019-08-05 14:38:07.557"},{"income":1,"nickname":"138000138000","tradeTitle":"升级总园长","tradeTime":"2019-06-05 14:38:07.557"},{"income":1,"nickname":"138000138000","tradeTitle":"升级园长","tradeTime":"2019-09-05 14:38:07.557"},{"income":1,"nickname":"138000138000","tradeTitle":"升级VIP","tradeTime":"2019-09-05 14:38:07.557"}]
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
         * income : 1.0
         * nickname : 138000138000
         * tradeTitle : 购买商品
         * tradeTime : 2019-06-05 14:38:07.557
         */

        private double income;
        private String nickname;
        private String mobile;
        private String tradeTitle;
        private String tradeTime;
        private String sourceOfIncome;

        public String getSourceOfIncome() {
            return sourceOfIncome;
        }

        public void setSourceOfIncome(String sourceOfIncome) {
            this.sourceOfIncome = sourceOfIncome;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public double getIncome() {
            return income;
        }

        public void setIncome(double income) {
            this.income = income;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getTradeTitle() {
            return tradeTitle;
        }

        public void setTradeTitle(String tradeTitle) {
            this.tradeTitle = tradeTitle;
        }

        public String getTradeTime() {
            return tradeTime;
        }

        public void setTradeTime(String tradeTime) {
            this.tradeTime = tradeTime;
        }
    }
}
