package com.tiancaijiazu.app.beans;

import java.util.List;

/**
 * Created by Administrator on 2019/8/17/017.
 */

public class BabyAgeBean {
    /**
     * code : 0
     * msg : OK
     * result : [{"subjectId":39259329542623232,"catalogId":39147620165357568,"title":"48个月","monthMin":36,"monthMax":48,"contentList":[]},{"subjectId":39258278676533248,"catalogId":39147620165357568,"title":"15个月","monthMin":12,"monthMax":15,"contentList":[]},{"subjectId":39256834275676160,"catalogId":39147620165357568,"title":"4个月","monthMin":4,"monthMax":5,"contentList":[]},{"subjectId":39254662708334592,"catalogId":39147620165357568,"title":"3个月","monthMin":2,"monthMax":3,"contentList":[]},{"subjectId":39147773613969408,"catalogId":39147620165357568,"title":"2个月","monthMin":2,"monthMax":2,"contentList":[]}]
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
         * subjectId : 39259329542623232
         * catalogId : 39147620165357568
         * title : 48个月
         * monthMin : 36
         * monthMax : 48
         * contentList : []
         */

        private long subjectId;
        private long catalogId;
        private String title;
        private int monthMin;
        private int monthMax;
        private List<?> contentList;

        public long getSubjectId() {
            return subjectId;
        }

        public void setSubjectId(long subjectId) {
            this.subjectId = subjectId;
        }

        public long getCatalogId() {
            return catalogId;
        }

        public void setCatalogId(long catalogId) {
            this.catalogId = catalogId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getMonthMin() {
            return monthMin;
        }

        public void setMonthMin(int monthMin) {
            this.monthMin = monthMin;
        }

        public int getMonthMax() {
            return monthMax;
        }

        public void setMonthMax(int monthMax) {
            this.monthMax = monthMax;
        }

        public List<?> getContentList() {
            return contentList;
        }

        public void setContentList(List<?> contentList) {
            this.contentList = contentList;
        }
    }
}
