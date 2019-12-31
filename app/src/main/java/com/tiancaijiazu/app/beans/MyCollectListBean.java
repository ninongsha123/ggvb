package com.tiancaijiazu.app.beans;

import java.util.List;

/**
 * Created by Administrator on 2019/8/19/019.
 */

public class MyCollectListBean {
    /**
     * code : 0
     * msg : OK
     * result : [{"articleId":23291548821229568,"userId":9567974524588032,"userAvatar":"http://img.tiancaijiazu.com/2019/07/02/22627667404787712_148x148.jpg?148,148","nickname":"杨旭","picUri":"http://img.tiancaijiazu.com/2019/07/04/23291544996024320_332x0.jpg?332,443","title":"8卡","publishTime":"2019-07-04 16:42:17.975","likes":0,"collect":1,"discuss":1,"isLikes":0},{"articleId":22228656281751552,"userId":9567974524588032,"userAvatar":"http://img.tiancaijiazu.com/2019/07/02/22627667404787712_148x148.jpg?148,148","nickname":"杨旭","picUri":"http://img.tiancaijiazu.com/2019/07/01/22228655996538880_332x0.jpg?332,332","title":"ghj","publishTime":"2019-07-01 18:18:44.646","likes":1,"collect":1,"discuss":0,"isLikes":0}]
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
         * articleId : 23291548821229568
         * userId : 9567974524588032
         * userAvatar : http://img.tiancaijiazu.com/2019/07/02/22627667404787712_148x148.jpg?148,148
         * nickname : 杨旭
         * picUri : http://img.tiancaijiazu.com/2019/07/04/23291544996024320_332x0.jpg?332,443
         * title : 8卡
         * publishTime : 2019-07-04 16:42:17.975
         * likes : 0
         * collect : 1
         * discuss : 1
         * isLikes : 0
         */

        private long articleId;
        private long userId;
        private String userAvatar;
        private String nickname;
        private String picUri;
        private String title;
        private String publishTime;
        private int likes;
        private int collect;
        private int discuss;
        private int isLikes;

        public long getArticleId() {
            return articleId;
        }

        public void setArticleId(long articleId) {
            this.articleId = articleId;
        }

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public String getUserAvatar() {
            return userAvatar;
        }

        public void setUserAvatar(String userAvatar) {
            this.userAvatar = userAvatar;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getPicUri() {
            return picUri;
        }

        public void setPicUri(String picUri) {
            this.picUri = picUri;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
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

        public int getCollect() {
            return collect;
        }

        public void setCollect(int collect) {
            this.collect = collect;
        }

        public int getDiscuss() {
            return discuss;
        }

        public void setDiscuss(int discuss) {
            this.discuss = discuss;
        }

        public int getIsLikes() {
            return isLikes;
        }

        public void setIsLikes(int isLikes) {
            this.isLikes = isLikes;
        }
    }
}
