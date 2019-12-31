package com.tiancaijiazu.app.beans;

import java.util.List;

/**
 * Created by Administrator on 2019/7/23/023.
 */

public class CourseListBean {
    /**
     * code : 0
     * msg : OK
     * result : [{"courseId":30175172283731968,"catalogId":100,"title":"公司工会开展\u201c压力与情绪管理\u201d专题讲座","summary":"生活节奏快、事业追求高、精神压力大成为常态，滋生负面情绪在所难免，如果任其发展，那最后失去的可就是整个人生。当危机来临，智慧的人懂得控制情绪，坦然面对，成就自我\u2026\u2026","instructor":"人文关怀","price":199,"promoPrice":129,"picUri":"http://img.tiancaijiazu.com/2019/07/23/30175164704624640_220x240.jpg?220,240","classCount":0,"description":"","courseType":0,"isBought":0},{"courseId":30174225729982464,"catalogId":101,"title":"如何提升社交能力","summary":"想办法扩展话题。在聊完新闻时事等普遍话题之后，你可以试着挑起一些更为亲近或者更贴近对方的话题，想办法扩展话题。在聊完新闻时事等普遍话题之后，你可以试着挑起一些更为亲近或者更贴近对方的话题，","instructor":"共同创作人","price":99,"promoPrice":9.9,"picUri":"http://img.tiancaijiazu.com/2019/07/23/30174171258556416_220x240.jpg?220,240","classCount":0,"description":"","courseType":0,"isBought":0},{"courseId":30173421925175296,"catalogId":100,"title":"如何控制自己的情绪，让自己变成一个情商高的人呢？","summary":"在生活中，我们会发现身边有这样一种人他们很少在外人面前发脾气，对待别人总是非常温和非常善良，跟他们在一起时总会让人觉得特别舒服，因为他们在任何时候都会懂得控制自己的情绪，即懂得保护自己，","instructor":"三叶草的芳香","price":299,"promoPrice":199,"picUri":"http://img.tiancaijiazu.com/2019/07/23/30173017791401984_220x240.jpg?220,240","classCount":0,"description":"","courseType":1,"isBought":0},{"courseId":30171897450860544,"catalogId":100,"title":"如何管理自己的情绪","summary":"情绪就是心理的晴雨表，每当兴奋的时候，心理感觉很开心，阳光灿烂；每当焦虑的时候，又会感觉很烦躁，忐忑不安；每当抑郁的时候，又会感觉很失落，郁郁寡欢\u2026\u2026多少人因为没有管理好自己的情绪","instructor":"严俊霞","price":199,"promoPrice":139,"picUri":"http://img.tiancaijiazu.com/2019/07/23/30170692339240960_220x240.jpg?220,240","classCount":0,"description":"","courseType":1,"isBought":0},{"courseId":3,"catalogId":3,"title":"生活处处有诗意","summary":"诗意诗意诗意","instructor":"丽丽","price":188,"promoPrice":0.01,"picUri":"http://img.tiancaijiazu.com/course.jpg","classCount":7,"description":"","courseType":0,"isBought":0},{"courseId":2,"catalogId":2,"title":"孩子边界意识弱，爱捣乱怎么办","summary":"爱捣乱怎么办爱捣乱怎么办","instructor":"毛毛","price":168,"promoPrice":0.01,"picUri":"http://img.tiancaijiazu.com/course.jpg","classCount":13,"description":"","courseType":0,"isBought":0},{"courseId":1,"catalogId":1,"title":"好好说话，幸福生活","summary":"幸福生活幸福生活","instructor":"龙龙","price":198,"promoPrice":0.01,"picUri":"http://img.tiancaijiazu.com/course.jpg","classCount":3,"description":"","courseType":1,"isBought":0}]
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
         * courseId : 30175172283731968
         * catalogId : 100
         * title : 公司工会开展“压力与情绪管理”专题讲座
         * summary : 生活节奏快、事业追求高、精神压力大成为常态，滋生负面情绪在所难免，如果任其发展，那最后失去的可就是整个人生。当危机来临，智慧的人懂得控制情绪，坦然面对，成就自我……
         * instructor : 人文关怀
         * price : 199
         * promoPrice : 129
         * picUri : http://img.tiancaijiazu.com/2019/07/23/30175164704624640_220x240.jpg?220,240
         * classCount : 0
         * description :
         * courseType : 0
         * isBought : 0
         */

        private long courseId;
        private long catalogId;
        private String title;
        private String summary;
        private String instructor;
        private float price;
        private float promoPrice;
        private String picUri;
        private int classCount;
        private String description;
        private int courseType;
        private int isBought;

        public long getCourseId() {
            return courseId;
        }

        public void setCourseId(long courseId) {
            this.courseId = courseId;
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

        public float getPrice() {
            return price;
        }

        public void setPrice(float price) {
            this.price = price;
        }

        public float getPromoPrice() {
            return promoPrice;
        }

        public void setPromoPrice(float promoPrice) {
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
