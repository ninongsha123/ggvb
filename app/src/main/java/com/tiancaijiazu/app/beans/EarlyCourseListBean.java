package com.tiancaijiazu.app.beans;

import java.util.List;

/**
 * Created by Administrator on 2019/8/22/022.
 */

public class EarlyCourseListBean {
    /**
     * code : 0
     * msg : OK
     * result : [{"courseId":38430057701707776,"catalogId":80,"title":"家庭早教课程第三周","monthMin":0,"monthMax":0,"summary":"幼儿一会拿笔就喜欢画画，这是本能，他们画在手上、画在身上、画在纸上、画在地上、画在墙上，到处都有儿童绘画的痕迹，","instructor":"绿泡泡","price":39,"promoPrice":0.01,"picUri":"http://img.tiancaijiazu.com/2019/08/15/38430007160344576_220x240.png?220,240","classCount":8,"description":"","courseType":0,"isBought":0},{"courseId":38423781479419904,"catalogId":80,"title":"家庭早教课程第二周","monthMin":0,"monthMax":0,"summary":"一个关于幼儿园安全教育问题的专项研究通过生活及报刊、电视、互联网等传媒，收集了近几年来有关幼儿园安全事故的若干案例，对幼儿园安全事故发生的原因作了以下分析","instructor":"小红果","price":29,"promoPrice":0.01,"picUri":"http://img.tiancaijiazu.com/2019/08/15/38423726915719168_220x240.png?220,240","classCount":8,"description":"","courseType":0,"isBought":0},{"courseId":38412289317146624,"catalogId":80,"title":"家庭早教课程第一周","monthMin":0,"monthMax":0,"summary":"人类对于太空总是抱有有很多问题，比如太空的形成以及成长，是否有外星人等等，到太空旅游相信是很多人的理想","instructor":"小花","price":29,"promoPrice":0.01,"picUri":"http://img.tiancaijiazu.com/2019/08/15/38412111218610176_220x240.png?220,240","classCount":18,"description":"","courseType":0,"isBought":0}]
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
         * courseId : 38430057701707776
         * catalogId : 80
         * title : 家庭早教课程第三周
         * monthMin : 0
         * monthMax : 0
         * summary : 幼儿一会拿笔就喜欢画画，这是本能，他们画在手上、画在身上、画在纸上、画在地上、画在墙上，到处都有儿童绘画的痕迹，
         * instructor : 绿泡泡
         * price : 39.0
         * promoPrice : 0.01
         * picUri : http://img.tiancaijiazu.com/2019/08/15/38430007160344576_220x240.png?220,240
         * classCount : 8
         * description :
         * courseType : 0
         * isBought : 0
         */

        private long courseId;
        private int catalogId;
        private String title;
        private int monthMin;
        private int monthMax;
        private String summary;
        private String instructor;
        private double price;
        private double promoPrice;
        private String picUri;
        private int classCount;
        private String description;
        private String shareUrl;
        private int courseType;
        private int isBought;

        public String getShareUrl() {
            return shareUrl;
        }

        public void setShareUrl(String shareUrl) {
            this.shareUrl = shareUrl;
        }

        public long getCourseId() {
            return courseId;
        }

        public void setCourseId(long courseId) {
            this.courseId = courseId;
        }

        public int getCatalogId() {
            return catalogId;
        }

        public void setCatalogId(int catalogId) {
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

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getInstructor() {
            return instructor;
        }

        public void setInstructor(String instructor) {
            this.instructor = instructor;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public double getPromoPrice() {
            return promoPrice;
        }

        public void setPromoPrice(double promoPrice) {
            this.promoPrice = promoPrice;
        }

        public String getPicUri() {
            return picUri;
        }

        public void setPicUri(String picUri) {
            this.picUri = picUri;
        }

        public int getClassCount() {
            return classCount;
        }

        public void setClassCount(int classCount) {
            this.classCount = classCount;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getCourseType() {
            return courseType;
        }

        public void setCourseType(int courseType) {
            this.courseType = courseType;
        }

        public int getIsBought() {
            return isBought;
        }

        public void setIsBought(int isBought) {
            this.isBought = isBought;
        }
    }
}
