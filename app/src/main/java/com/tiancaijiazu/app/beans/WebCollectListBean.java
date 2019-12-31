package com.tiancaijiazu.app.beans;

import java.util.List;

public class WebCollectListBean {
    /**
     * code : 0
     * msg : OK
     * result : [{"articleId":49287110749982720,"title":"辅食添加方法","summary":"一般情况下宝宝4-6个月可以添加辅食，最好的添加辅食时间是6个月。","pic":"img/2019/09/14/49287046132535296_220x140.jpg?220,140","url":"http://wiki.h5.tiancaijiazu.com/EncyclopediaParenting/content.html?contentid=49287110749982720","lastUpdateTime":"2019-10-10 16:43:51.762"}]
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
         * articleId : 49287110749982720
         * title : 辅食添加方法
         * summary : 一般情况下宝宝4-6个月可以添加辅食，最好的添加辅食时间是6个月。
         * pic : img/2019/09/14/49287046132535296_220x140.jpg?220,140
         * url : http://wiki.h5.tiancaijiazu.com/EncyclopediaParenting/content.html?contentid=49287110749982720
         * lastUpdateTime : 2019-10-10 16:43:51.762
         */

        private long articleId;
        private String title;
        private String summary;
        private String pic;
        private String url;
        private String lastUpdateTime;

        public long getArticleId() {
            return articleId;
        }

        public void setArticleId(long articleId) {
            this.articleId = articleId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getLastUpdateTime() {
            return lastUpdateTime;
        }

        public void setLastUpdateTime(String lastUpdateTime) {
            this.lastUpdateTime = lastUpdateTime;
        }
    }
}
