package com.tiancaijiazu.app.beans;

import java.util.List;

public class GetSubIDBean {
    /**
     * code : 0
     * msg : OK
     * result : {"subjectId":39256834275676160,"catalogId":39147620165357568,"title":"4个月","monthMin":3,"dayMin":15,"monthMax":4,"dayMax":14,"contentList":[]}
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
         * subjectId : 39256834275676160
         * catalogId : 39147620165357568
         * title : 4个月
         * monthMin : 3
         * dayMin : 15
         * monthMax : 4
         * dayMax : 14
         * contentList : []
         */

        private long subjectId;
        private long catalogId;
        private String title;
        private int monthMin;
        private int dayMin;
        private int monthMax;
        private int dayMax;
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

        public int getDayMin() {
            return dayMin;
        }

        public void setDayMin(int dayMin) {
            this.dayMin = dayMin;
        }

        public int getMonthMax() {
            return monthMax;
        }

        public void setMonthMax(int monthMax) {
            this.monthMax = monthMax;
        }

        public int getDayMax() {
            return dayMax;
        }

        public void setDayMax(int dayMax) {
            this.dayMax = dayMax;
        }

        public List<?> getContentList() {
            return contentList;
        }

        public void setContentList(List<?> contentList) {
            this.contentList = contentList;
        }
    }
}
