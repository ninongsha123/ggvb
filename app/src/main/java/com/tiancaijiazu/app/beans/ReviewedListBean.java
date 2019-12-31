package com.tiancaijiazu.app.beans;

import java.util.List;

/**
 * Created by Administrator on 2019/8/21/021.
 */

public class ReviewedListBean {
    /**
     * code : 0
     * msg : OK
     * result : [{"reportId":40618172440776704,"userId":9567974524588032,"babyId":36242251206234112,"subjectId":39147773613969408,"subjectTitle":"2个月","reportTime":"2019-08-21 12:12:06","indicatorsList":[]}]
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
         * reportId : 40618172440776704
         * userId : 9567974524588032
         * babyId : 36242251206234112
         * subjectId : 39147773613969408
         * subjectTitle : 2个月
         * reportTime : 2019-08-21 12:12:06
         * indicatorsList : []
         */

        private long reportId;
        private long userId;
        private long babyId;
        private long subjectId;
        private String subjectTitle;
        private String reportTime;
        private List<?> indicatorsList;

        public long getReportId() {
            return reportId;
        }

        public void setReportId(long reportId) {
            this.reportId = reportId;
        }

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public long getBabyId() {
            return babyId;
        }

        public void setBabyId(long babyId) {
            this.babyId = babyId;
        }

        public long getSubjectId() {
            return subjectId;
        }

        public void setSubjectId(long subjectId) {
            this.subjectId = subjectId;
        }

        public String getSubjectTitle() {
            return subjectTitle;
        }

        public void setSubjectTitle(String subjectTitle) {
            this.subjectTitle = subjectTitle;
        }

        public String getReportTime() {
            return reportTime;
        }

        public void setReportTime(String reportTime) {
            this.reportTime = reportTime;
        }

        public List<?> getIndicatorsList() {
            return indicatorsList;
        }

        public void setIndicatorsList(List<?> indicatorsList) {
            this.indicatorsList = indicatorsList;
        }
    }
}
