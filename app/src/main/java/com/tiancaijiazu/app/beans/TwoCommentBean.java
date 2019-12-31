package com.tiancaijiazu.app.beans;

import java.util.List;

/**
 * Created by Administrator on 2019/5/31/031.
 */

public class TwoCommentBean {
    /**
     * code : 0
     * msg : OK
     * result : [{"discussId":11019478490025984,"articleId":10887329585696768,"replyId":10974797169299456,"replyCount":0,"userId":9567974524588032,"userNickname":"杨旭","userAvatar":"http://192.168.0.200/images/Avatar_200.jpg","content":"回复@丽丽：啦啦啦","publishTime":"2019-05-31 19:57:28.404","likes":0,"isLikes":0,"replyUserId":10558706915872768,"replyNickname":"甲龙","subDiscuss":[]},{"discussId":10988724590612480,"articleId":10887329585696768,"replyId":10974797169299456,"replyCount":0,"userId":9569814792245248,"userNickname":"丽丽","userAvatar":"http://192.168.0.200/images/Avatar_200.jpg","content":"Ttttttttt","publishTime":"2019-05-31 17:55:16.103","likes":0,"isLikes":0,"replyUserId":10558706915872768,"replyNickname":"甲龙","subDiscuss":[]},{"discussId":10975189395443712,"articleId":10887329585696768,"replyId":10974797169299456,"replyCount":0,"userId":9569814792245248,"userNickname":"丽丽","userAvatar":"http://192.168.0.200/images/Avatar_200.jpg","content":"Rrrrrrrrrrrrryyyyy","publishTime":"2019-05-31 17:01:29.061","likes":1,"isLikes":0,"replyUserId":10558706915872768,"replyNickname":"甲龙","subDiscuss":[]}]
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
         * discussId : 11019478490025984
         * articleId : 10887329585696768
         * replyId : 10974797169299456
         * replyCount : 0
         * userId : 9567974524588032
         * userNickname : 杨旭
         * userAvatar : http://192.168.0.200/images/Avatar_200.jpg
         * content : 回复@丽丽：啦啦啦
         * publishTime : 2019-05-31 19:57:28.404
         * likes : 0
         * isLikes : 0
         * replyUserId : 10558706915872768
         * replyNickname : 甲龙
         * subDiscuss : []
         */

        private long discussId;
        private long articleId;
        private long replyId;
        private int replyCount;
        private long userId;
        private String userNickname;
        private String userAvatar;
        private String content;
        private String publishTime;
        private int likes;
        private int isLikes;
        private long replyUserId;
        private String replyNickname;

        public long getDiscussId() {
            return discussId;
        }

        public void setDiscussId(long discussId) {
            this.discussId = discussId;
        }

        public long getArticleId() {
            return articleId;
        }

        public void setArticleId(long articleId) {
            this.articleId = articleId;
        }

        public long getReplyId() {
            return replyId;
        }

        public void setReplyId(long replyId) {
            this.replyId = replyId;
        }

        public int getReplyCount() {
            return replyCount;
        }

        public void setReplyCount(int replyCount) {
            this.replyCount = replyCount;
        }

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public String getUserNickname() {
            return userNickname;
        }

        public void setUserNickname(String userNickname) {
            this.userNickname = userNickname;
        }

        public String getUserAvatar() {
            return userAvatar;
        }

        public void setUserAvatar(String userAvatar) {
            this.userAvatar = userAvatar;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getPublishTime() {
            return publishTime;
        }

        public void setPublishTime(String publishTime) {
            this.publishTime = publishTime;
        }

        public int getLikes() {
            return likes;
        }

        public void setLikes(int likes) {
            this.likes = likes;
        }

        public int getIsLikes() {
            return isLikes;
        }

        public void setIsLikes(int isLikes) {
            this.isLikes = isLikes;
        }

        public long getReplyUserId() {
            return replyUserId;
        }

        public void setReplyUserId(long replyUserId) {
            this.replyUserId = replyUserId;
        }

        public String getReplyNickname() {
            return replyNickname;
        }

        public void setReplyNickname(String replyNickname) {
            this.replyNickname = replyNickname;
        }


}
}
