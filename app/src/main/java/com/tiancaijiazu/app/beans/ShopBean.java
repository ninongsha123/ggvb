package com.tiancaijiazu.app.beans;

import java.util.List;

/**
 * Created by Administrator on 2019/8/5/005.
 */

public class ShopBean {
    /**
     * code : 0
     * msg : OK
     * result : [{"catalogId":1,"parentId":0,"name":"自有品牌"},{"catalogId":2,"parentId":0,"name":"母婴服饰"},{"catalogId":3,"parentId":0,"name":"儿童车床"}]
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
         * catalogId : 1
         * parentId : 0
         * name : 自有品牌
         */

        private long catalogId;
        private long parentId;
        private String name;

        public long getCatalogId() {
            return catalogId;
        }

        public void setCatalogId(long catalogId) {
            this.catalogId = catalogId;
        }

        public long getParentId() {
            return parentId;
        }

        public void setParentId(long parentId) {
            this.parentId = parentId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
