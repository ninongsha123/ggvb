package com.tiancaijiazu.app.beans;

import java.util.List;

/**
 * Created by Administrator on 2019/8/25/025.
 */

public class MallAdBean {
    /**
     * code : 0
     * msg : OK
     * result : [{"picUri":"http://img.tiancaijiazu.com/2019/08/25/42099062828306432_226x186.png?226,186","route":"MALL_SKU","target":"42054792499040256","price":456,"promoPrice":234,"title":"儿童电动摩托车三轮车男女宝宝可坐人小孩子玩具车1-6岁遥控童车1"},{"picUri":"http://img.tiancaijiazu.com/2019/08/25/42098959841366016_226x186.png?226,186","route":"MALL_SKU","target":"42054792507428864","price":345,"promoPrice":234,"title":"儿童电动摩托车三轮车男女宝宝可坐人小孩子玩具车1-6岁遥控童车2"},{"picUri":"http://img.tiancaijiazu.com/2019/08/25/42098840832184320_226x186.png?226,186","route":"MALL_SKU","target":"42054792520011776","price":678,"promoPrice":456,"title":"儿童电动摩托车三轮车男女宝宝可坐人小孩子玩具车1-6岁遥控童车3"},{"picUri":"http://img.tiancaijiazu.com/2019/08/25/42098699748380672_226x186.png?226,186","route":"MALL_SKU","target":"42055510161231872","price":789,"promoPrice":678,"title":"儿童电动摩托车三轮车男女宝宝可坐人小孩子玩具车1-6岁遥控童车4"},{"picUri":"http://img.tiancaijiazu.com/2019/08/25/42098565350297600_226x186.png?226,186","route":"MALL_SKU","target":"42055510173814784","price":234,"promoPrice":123,"title":"儿童电动摩托车三轮车男女宝宝可坐人小孩子玩具车1-6岁遥控童车5"},{"picUri":"http://img.tiancaijiazu.com/2019/08/25/42098467404910592_226x186.png?226,186","route":"MALL_SKU","target":"42055510186397696","price":456,"promoPrice":234,"title":"儿童电动摩托车三轮车男女宝宝可坐人小孩子玩具车1-6岁遥控童车6"}]
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
         * picUri : http://img.tiancaijiazu.com/2019/08/25/42099062828306432_226x186.png?226,186
         * route : MALL_SKU
         * target : 42054792499040256
         * price : 456.0
         * promoPrice : 234.0
         * title : 儿童电动摩托车三轮车男女宝宝可坐人小孩子玩具车1-6岁遥控童车1
         */

        private String picUri;
        private String route;
        private String target;
        private double price;
        private double promoPrice;
        private String title;

        public String getPicUri() {
            return picUri;
        }

        public void setPicUri(String picUri) {
            this.picUri = picUri;
        }

        public String getRoute() {
            return route;
        }

        public void setRoute(String route) {
            this.route = route;
        }

        public String getTarget() {
            return target;
        }

        public void setTarget(String target) {
            this.target = target;
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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
