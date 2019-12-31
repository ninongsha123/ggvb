package com.tiancaijiazu.app.beans;

import java.util.List;

/**
 * Created by Administrator on 2019/6/19/019.
 */

public class ShangpinBean {
    /**
     * code : 0
     * msg : OK
     * result : {"itemCount":2,"itemList":[{"skuId":2,"name":"Lee Cooper休闲裤","picUri":"http://192.168.0.200/images//images/125.jpg","price":188,"promoPrice":168},{"skuId":1,"name":"男童套装2019夏季足球服儿童运动套装","picUri":"http://192.168.0.200/images//images/123.jpg","price":188,"promoPrice":168}]}
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
         * itemCount : 2
         * itemList : [{"skuId":2,"name":"Lee Cooper休闲裤","picUri":"http://192.168.0.200/images//images/125.jpg","price":188,"promoPrice":168},{"skuId":1,"name":"男童套装2019夏季足球服儿童运动套装","picUri":"http://192.168.0.200/images//images/123.jpg","price":188,"promoPrice":168}]
         */

        private int itemCount;
        private List<ItemListBean> itemList;

        public int getItemCount() {
            return itemCount;
        }

        public void setItemCount(int itemCount) {
            this.itemCount = itemCount;
        }

        public List<ItemListBean> getItemList() {
            return itemList;
        }

        public void setItemList(List<ItemListBean> itemList) {
            this.itemList = itemList;
        }

        public static class ItemListBean {
            /**
             * skuId : 2
             * name : Lee Cooper休闲裤
             * picUri : http://192.168.0.200/images//images/125.jpg
             * price : 188
             * promoPrice : 168
             */

            private long skuId;
            private String name;
            private String picUri;
            private float price;
            private float promoPrice;

            public long getSkuId() {
                return skuId;
            }

            public void setSkuId(long skuId) {
                this.skuId = skuId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPicUri() {
                return picUri;
            }

            public void setPicUri(String picUri) {
                this.picUri = picUri;
            }

            public float getPrice() {
                return price;
            }

            public void setPrice(float price) {
                this.price = price;
            }

            public float getPromoPrice() {
                return promoPrice;
            }

            public void setPromoPrice(float promoPrice) {
                this.promoPrice = promoPrice;
            }
        }
    }
}
