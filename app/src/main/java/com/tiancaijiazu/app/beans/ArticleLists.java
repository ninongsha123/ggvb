package com.tiancaijiazu.app.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2019/5/29/029.
 */

public class ArticleLists implements Serializable{

    /**
     * code : 0
     * msg : OK
     * result : [{"articleId":46118372118761472,"userId":43114236725039104,"userAvatar":"http://img.tiancaijiazu.com/2019/08/30/43841610571714560_148x148.jpg?148,148","nickname":"天才用户0590","picUri":"http://img.tiancaijiazu.com/2019/09/05/46118370990493696_332x0.jpg?300,400","title":"","publishTime":"2019-09-05 16:27:56.751","likes":0,"collect":0,"discuss":0,"isLikes":0,"coverPics":"http://img.tiancaijiazu.com/2019/09/05/46118370990493696_222x222.jpg?222,222|http://img.tiancaijiazu.com/2019/09/05/46118370998882304_222x222.jpg?222,222|http://img.tiancaijiazu.com/2019/09/05/46118371003076608_222x222.png?222,222|http://img.tiancaijiazu.com/2019/09/05/46118371003076609_222x222.png?222,222|http://img.tiancaijiazu.com/2019/09/05/46118371003076610_222x222.png?222,222|http://img.tiancaijiazu.com/2019/09/05/46118371003076611_222x222.png?222,222|http://img.tiancaijiazu.com/2019/09/05/46118371003076612_222x222.jpg?222,222|http://img.tiancaijiazu.com/2019/09/05/46118371007270912_222x222.png?222,222","largePics":"http://img.tiancaijiazu.com/2019/09/05/46118370990493696_750x0.jpg?300,400|http://img.tiancaijiazu.com/2019/09/05/46118370998882304_750x0.jpg?300,400|http://img.tiancaijiazu.com/2019/09/05/46118371003076608_750x0.png?46,46|http://img.tiancaijiazu.com/2019/09/05/46118371003076609_750x0.png?276,22|http://img.tiancaijiazu.com/2019/09/05/46118371003076610_750x0.png?45,45|http://img.tiancaijiazu.com/2019/09/05/46118371003076611_750x0.png?33,33|http://img.tiancaijiazu.com/2019/09/05/46118371003076612_750x0.jpg?300,400|http://img.tiancaijiazu.com/2019/09/05/46118371007270912_750x0.png?750,1333","detail":"杨旭"},{"articleId":46118369551847424,"userId":43114236725039104,"userAvatar":"http://img.tiancaijiazu.com/2019/08/30/43841610571714560_148x148.jpg?148,148","nickname":"天才用户0590","picUri":"http://img.tiancaijiazu.com/2019/09/05/46118368008343552_332x0.jpg?300,400","title":"","publishTime":"2019-09-05 16:27:56.139","likes":0,"collect":0,"discuss":0,"isLikes":0,"coverPics":"http://img.tiancaijiazu.com/2019/09/05/46118368008343552_222x222.jpg?222,222|http://img.tiancaijiazu.com/2019/09/05/46118368016732160_222x222.jpg?222,222|http://img.tiancaijiazu.com/2019/09/05/46118368020926464_222x222.png?222,222|http://img.tiancaijiazu.com/2019/09/05/46118368025120768_222x222.png?222,222|http://img.tiancaijiazu.com/2019/09/05/46118368025120769_222x222.png?222,222|http://img.tiancaijiazu.com/2019/09/05/46118368025120770_222x222.png?222,222|http://img.tiancaijiazu.com/2019/09/05/46118368025120771_222x222.jpg?222,222|http://img.tiancaijiazu.com/2019/09/05/46118368029315072_222x222.png?222,222","largePics":"http://img.tiancaijiazu.com/2019/09/05/46118368008343552_750x0.jpg?300,400|http://img.tiancaijiazu.com/2019/09/05/46118368016732160_750x0.jpg?300,400|http://img.tiancaijiazu.com/2019/09/05/46118368020926464_750x0.png?46,46|http://img.tiancaijiazu.com/2019/09/05/46118368025120768_750x0.png?276,22|http://img.tiancaijiazu.com/2019/09/05/46118368025120769_750x0.png?45,45|http://img.tiancaijiazu.com/2019/09/05/46118368025120770_750x0.png?33,33|http://img.tiancaijiazu.com/2019/09/05/46118368025120771_750x0.jpg?300,400|http://img.tiancaijiazu.com/2019/09/05/46118368029315072_750x0.png?750,1333","detail":"杨旭"}]
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

    public static class ResultBean implements Serializable{
        /**
         * articleId : 46118372118761472
         * userId : 43114236725039104
         * userAvatar : http://img.tiancaijiazu.com/2019/08/30/43841610571714560_148x148.jpg?148,148
         * nickname : 天才用户0590
         * picUri : http://img.tiancaijiazu.com/2019/09/05/46118370990493696_332x0.jpg?300,400
         * title :
         * publishTime : 2019-09-05 16:27:56.751
         * likes : 0
         * collect : 0
         * discuss : 0
         * isLikes : 0
         * coverPics : http://img.tiancaijiazu.com/2019/09/05/46118370990493696_222x222.jpg?222,222|http://img.tiancaijiazu.com/2019/09/05/46118370998882304_222x222.jpg?222,222|http://img.tiancaijiazu.com/2019/09/05/46118371003076608_222x222.png?222,222|http://img.tiancaijiazu.com/2019/09/05/46118371003076609_222x222.png?222,222|http://img.tiancaijiazu.com/2019/09/05/46118371003076610_222x222.png?222,222|http://img.tiancaijiazu.com/2019/09/05/46118371003076611_222x222.png?222,222|http://img.tiancaijiazu.com/2019/09/05/46118371003076612_222x222.jpg?222,222|http://img.tiancaijiazu.com/2019/09/05/46118371007270912_222x222.png?222,222
         * largePics : http://img.tiancaijiazu.com/2019/09/05/46118370990493696_750x0.jpg?300,400|http://img.tiancaijiazu.com/2019/09/05/46118370998882304_750x0.jpg?300,400|http://img.tiancaijiazu.com/2019/09/05/46118371003076608_750x0.png?46,46|http://img.tiancaijiazu.com/2019/09/05/46118371003076609_750x0.png?276,22|http://img.tiancaijiazu.com/2019/09/05/46118371003076610_750x0.png?45,45|http://img.tiancaijiazu.com/2019/09/05/46118371003076611_750x0.png?33,33|http://img.tiancaijiazu.com/2019/09/05/46118371003076612_750x0.jpg?300,400|http://img.tiancaijiazu.com/2019/09/05/46118371007270912_750x0.png?750,1333
         * detail : 杨旭
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
        private String coverPics;
        private String largePics;
        private String detail;
        private String babyBirthday;
        private int isRecommend;
        private String study_contents_title;
        private int width;
        private int height;

        public String getStudy_contents_title() {
            return study_contents_title;
        }

        public void setStudy_contents_title(String study_contents_title) {
            this.study_contents_title = study_contents_title;
        }

        public int getIsRecommend() {
            return isRecommend;
        }

        public void setIsRecommend(int isRecommend) {
            this.isRecommend = isRecommend;
        }

        public String getBabyBirthday() {
            return babyBirthday;
        }

        public void setBabyBirthday(String babyBirthday) {
            this.babyBirthday = babyBirthday;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

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

        public String getCoverPics() {
            return coverPics;
        }

        public void setCoverPics(String coverPics) {
            this.coverPics = coverPics;
        }

        public String getLargePics() {
            return largePics;
        }

        public void setLargePics(String largePics) {
            this.largePics = largePics;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }
    }
}
