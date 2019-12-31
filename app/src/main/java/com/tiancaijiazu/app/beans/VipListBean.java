package com.tiancaijiazu.app.beans;

import java.util.List;

/**
 * Created by Administrator on 2019/8/25/025.
 */

public class VipListBean {
    /**
     * code : 0
     * msg : OK
     * result : [{"vipLevel":11,"vipTitle":"VIP会员","price":1980,"promoPrice":0.01},{"vipLevel":21,"vipTitle":"园长","price":9800,"promoPrice":0.01},{"vipLevel":31,"vipTitle":"总园长","price":20000,"promoPrice":0.01},{"vipLevel":41,"vipTitle":"总经理","price":100000,"promoPrice":0.01}]
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
         * vipLevel : 11
         * vipTitle : VIP会员
         * price : 1980.0
         * promoPrice : 0.01
         */

        private int vipLevel;
        private String vipTitle;
        private double price;
        private double promoPrice;

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

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public double getPromoPrice() {
            return promoPrice;
        }

        public void setPromoPrice(double promoPrice) {
            this.promoPrice = promoPrice;
        }
    }
}
