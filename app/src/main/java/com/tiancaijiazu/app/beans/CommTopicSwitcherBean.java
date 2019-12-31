package com.tiancaijiazu.app.beans;

import java.util.List;

public class CommTopicSwitcherBean {
    /**
     * code : 0
     * msg : OK
     * result : [{"subjectId":22247956484853760,"parentId":1,"subjectName":"育儿","articleCount":0,"picUri":"","summary":""},{"subjectId":27551534858506240,"parentId":1,"subjectName":"早教","articleCount":0,"picUri":"","summary":""},{"subjectId":28251047889866752,"parentId":1,"subjectName":"亲子","articleCount":0,"picUri":"","summary":""},{"subjectId":32433616424407040,"parentId":1,"subjectName":"天才","articleCount":0,"picUri":"","summary":""}]
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
         * subjectId : 22247956484853760
         * parentId : 1
         * subjectName : 育儿
         * articleCount : 0
         * picUri :
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
