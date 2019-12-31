package com.tiancaijiazu.app.beans;

import java.util.List;

/**
 * Created by Administrator on 2019/6/4/004.
 */

public class TopicListsBean {
    /**
     * code : 0
     * msg : OK
     * result : [{"subjectId":10572699764133888,"parentId":1,"subjectName":"辅食打卡","articleCount":15,"picUri":"http://192.168.0.200/images/subject_banner.jpg","summary":""},{"subjectId":10572699759939584,"parentId":1,"subjectName":"第二奶粉","articleCount":15,"picUri":"http://192.168.0.200/images/subject_banner.jpg","summary":""},{"subjectId":10572699755745280,"parentId":1,"subjectName":"我喜欢妈妈","articleCount":15,"picUri":"http://192.168.0.200/images/subject_banner.jpg","summary":""},{"subjectId":9890654813556736,"parentId":1,"subjectName":"添加话题","articleCount":6,"picUri":"http://192.168.0.200/images/subject_banner.jpg","summary":""},{"subjectId":10576168231243776,"parentId":1,"subjectName":"妈妈的jk高难","articleCount":2,"picUri":"http://192.168.0.200/images/subject_banner.jpg","summary":""},{"subjectId":10576168227049472,"parentId":1,"subjectName":"男士护肤指南","articleCount":2,"picUri":"http://192.168.0.200/images/subject_banner.jpg","summary":""},{"subjectId":10203872995971072,"parentId":1,"subjectName":"丽丽与毛毛","articleCount":2,"picUri":"http://192.168.0.200/images/subject_banner.jpg","summary":""},{"subjectId":9892579978121216,"parentId":1,"subjectName":"丽丽","articleCount":2,"picUri":"http://192.168.0.200/images/subject_banner.jpg","summary":""},{"subjectId":10587180955209728,"parentId":1,"subjectName":"奥森奥森北京奥森北京奥森","articleCount":1,"picUri":"http://192.168.0.200/images/subject_banner.jpg","summary":""},{"subjectId":10586896669478912,"parentId":1,"subjectName":"奥森北京奥森北京奥森北京奥森北京奥森北京","articleCount":1,"picUri":"http://192.168.0.200/images/subject_banner.jpg","summary":""},{"subjectId":10577360046919680,"parentId":1,"subjectName":"女生夏季穿搭","articleCount":1,"picUri":"http://192.168.0.200/images/subject_banner.jpg","summary":""},{"subjectId":10576168222855168,"parentId":1,"subjectName":"车载好物分享","articleCount":1,"picUri":"http://192.168.0.200/images/subject_banner.jpg","summary":""},{"subjectId":9579677907816448,"parentId":1,"subjectName":"奥森","articleCount":1,"picUri":"http://192.168.0.200/images/subject_banner.jpg","summary":""},{"subjectId":9579677903622144,"parentId":1,"subjectName":"北京","articleCount":1,"picUri":"http://192.168.0.200/images/subject_banner.jpg","summary":""}]
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
         * subjectId : 10572699764133888
         * parentId : 1
         * subjectName : 辅食打卡
         * articleCount : 15
         * picUri : http://192.168.0.200/images/subject_banner.jpg
         * summary :
         */

        private long subjectId;
        private int parentId;
        private String subjectName;
        private int articleCount;
        private String picUri;
        private String summary;

        public long getSubjectId() {
            return subjectId;
        }

        public void setSubjectId(long subjectId) {
            this.subjectId = subjectId;
        }

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }

        public String getSubjectName() {
            return subjectName;
        }

        public void setSubjectName(String subjectName) {
            this.subjectName = subjectName;
        }

        public int getArticleCount() {
            return articleCount;
        }

        public void setArticleCount(int articleCount) {
            this.articleCount = articleCount;
        }

        public String getPicUri() {
            return picUri;
        }

        public void setPicUri(String picUri) {
            this.picUri = picUri;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }
    }
}
