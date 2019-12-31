package com.tiancaijiazu.app.beans;

import java.util.List;

public class RespectivelyBean {
    /**
     * code : 0
     * msg : OK
     * result : [{"catalogId":46822606782992384,"name":"专家来了"},{"catalogId":46825935500087296,"name":"天赋启蒙"},{"catalogId":46825996359438336,"name":"早教游戏"},{"catalogId":46826061836718080,"name":"舞蹈天赋"}]
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
         * catalogId : 46822606782992384
         * name : 专家来了
         */

        private long catalogId;
        private String name;

        public long getCatalogId() {
            return catalogId;
        }

        public void setCatalogId(long catalogId) {
            this.catalogId = catalogId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
