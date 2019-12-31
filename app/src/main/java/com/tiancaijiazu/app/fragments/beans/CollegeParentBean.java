package com.tiancaijiazu.app.fragments.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2019/6/10/010.
 */

public class CollegeParentBean implements Serializable{


    /**
     * code : 0
     * msg : OK
     * result : [{"catalogId":1,"parentId":0,"name":"家庭学院","ico":"","description":"家庭学院"},{"catalogId":2,"parentId":0,"name":"早教学院","ico":"","description":""},{"catalogId":3,"parentId":0,"name":"智慧学院","ico":"","description":""}]
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

    public static class ResultBean implements Serializable{
        /**
         * catalogId : 1
         * parentId : 0
         * name : 家庭学院
         * ico :
         * description : 家庭学院
         */

        private long catalogId;
        private long parentId;
        private String name;
        private String ico;
        private String description;

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

        public String getIco() {
            return ico;
        }

        public void setIco(String ico) {
            this.ico = ico;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
