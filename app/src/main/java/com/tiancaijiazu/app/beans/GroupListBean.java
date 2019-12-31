package com.tiancaijiazu.app.beans;

import java.util.List;

public class GroupListBean {

    /**
     * code : 0
     * msg : OK
     * result : [{"articleId":86251260642201600,"userId":72891853338447872,"userAvatar":"http://thirdwx.qlogo.cn/mmopen/vi_32/PiajxSqBRaELTFUV1hJTgJS8ahjnuMcrvv2YkicAoh5rz6k18Gbg2W06IychgsmTTTYrRfSJg8nbSrWCeRJc0ITg/132","nickname":"家族会员6659","picUri":"http://img.tiancaijiazu.com/2019/12/25/86251260218576896_332x0.png?85,85","title":"","publishTime":"2019-12-25 10:21:43.009","likes":0,"collect":0,"discuss":0,"isLikes":0,"coverPics":"","largePics":"http://img.tiancaijiazu.com/2019/12/25/86251260218576896_750x0.png?85,85?0","detail":"测试","babyBirthday":"2009-05-01","isRecommend":0,"study_contents_title":""},{"articleId":86236510436855808,"userId":9567974524588032,"userAvatar":"http://img.tiancaijiazu.com/2019/12/14/82338775723806720_148x148.jpg?148,148","nickname":"华为","picUri":"http://img.tiancaijiazu.com/2019/12/25/86236509853847552_332x0.jpg?332,590","title":"","publishTime":"2019-12-25 09:23:06.285","likes":0,"collect":0,"discuss":0,"isLikes":0,"coverPics":"","largePics":"http://img.tiancaijiazu.com/2019/12/25/86236509853847552_750x0.jpg?720,1280?0","detail":"测测","babyBirthday":"2018-11-27","isRecommend":0,"study_contents_title":""},{"articleId":86233604480438272,"userId":9567974524588032,"userAvatar":"http://img.tiancaijiazu.com/2019/12/14/82338775723806720_148x148.jpg?148,148","nickname":"华为","picUri":"http://img.tiancaijiazu.com/2019/12/25/86233604249751552_332x0.png?332,332","title":"","publishTime":"2019-12-25 09:11:33.451","likes":0,"collect":0,"discuss":0,"isLikes":0,"coverPics":"","largePics":"http://img.tiancaijiazu.com/2019/12/25/86233604249751552_750x0.png?400,400?0","detail":"测测","babyBirthday":"2018-11-27","isRecommend":0,"study_contents_title":""},{"articleId":86043520745476096,"userId":9567974524588032,"userAvatar":"http://img.tiancaijiazu.com/2019/12/14/82338775723806720_148x148.jpg?148,148","nickname":"华为","picUri":"http://img.tiancaijiazu.com/2019/12/24/86043520506400768_332x0.png?332,332","title":"","publishTime":"2019-12-24 20:36:13.957","likes":0,"collect":0,"discuss":0,"isLikes":0,"coverPics":"","largePics":"http://img.tiancaijiazu.com/2019/12/24/86043520506400768_750x0.png?400,400?0","detail":"测测","babyBirthday":"2018-11-27","isRecommend":0,"study_contents_title":""},{"articleId":86042595318435840,"userId":9567974524588032,"userAvatar":"http://img.tiancaijiazu.com/2019/12/14/82338775723806720_148x148.jpg?148,148","nickname":"华为","picUri":"http://img.tiancaijiazu.com/2019/12/24/86042595087749120_332x0.png?332,332","title":"","publishTime":"2019-12-24 20:32:33.318","likes":0,"collect":0,"discuss":0,"isLikes":0,"coverPics":"","largePics":"http://img.tiancaijiazu.com/2019/12/24/86042595087749120_750x0.png?400,400?0","detail":"测测","babyBirthday":"2018-11-27","isRecommend":0,"study_contents_title":""},{"articleId":86040322928414720,"userId":9567974524588032,"userAvatar":"http://img.tiancaijiazu.com/2019/12/14/82338775723806720_148x148.jpg?148,148","nickname":"华为","picUri":"http://img.tiancaijiazu.com/2019/12/24/86040322685145088_332x0.png?332,332","title":"","publishTime":"2019-12-24 20:23:31.538","likes":0,"collect":0,"discuss":0,"isLikes":0,"coverPics":"","largePics":"http://img.tiancaijiazu.com/2019/12/24/86040322685145088_750x0.png?400,400?0","detail":"测测","babyBirthday":"2018-11-27","isRecommend":0,"study_contents_title":""},{"articleId":86024759715958784,"userId":9567974524588032,"userAvatar":"http://img.tiancaijiazu.com/2019/12/14/82338775723806720_148x148.jpg?148,148","nickname":"华为","picUri":"http://img.tiancaijiazu.com/2019/12/24/86024758583496704_332x0.jpg?332,590","title":"","publishTime":"2019-12-24 19:21:40.979","likes":0,"collect":0,"discuss":0,"isLikes":0,"coverPics":"","largePics":"http://img.tiancaijiazu.com/2019/12/24/86024758583496704_750x0.jpg?750,1333?0","detail":"ce","babyBirthday":"2018-11-27","isRecommend":0,"study_contents_title":""},{"articleId":86009411935539200,"userId":9567974524588032,"userAvatar":"http://img.tiancaijiazu.com/2019/12/14/82338775723806720_148x148.jpg?148,148","nickname":"华为","picUri":"http://img.tiancaijiazu.com/2019/12/24/86009410438172672_332x0.jpg?332,590","title":"","publishTime":"2019-12-24 18:20:41.783","likes":0,"collect":0,"discuss":0,"isLikes":0,"coverPics":"","largePics":"http://img.tiancaijiazu.com/2019/12/24/86009410438172672_750x0.jpg?750,1333?0","detail":"ceshi","babyBirthday":"2018-11-27","isRecommend":0,"study_contents_title":""}]
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
         * articleId : 86251260642201600
         * userId : 72891853338447872
         * userAvatar : http://thirdwx.qlogo.cn/mmopen/vi_32/PiajxSqBRaELTFUV1hJTgJS8ahjnuMcrvv2YkicAoh5rz6k18Gbg2W06IychgsmTTTYrRfSJg8nbSrWCeRJc0ITg/132
         * nickname : 家族会员6659
         * picUri : http://img.tiancaijiazu.com/2019/12/25/86251260218576896_332x0.png?85,85
         * title :
         * publishTime : 2019-12-25 10:21:43.009
         * likes : 0
         * collect : 0
         * discuss : 0
         * isLikes : 0
         * coverPics :
         * largePics : http://img.tiancaijiazu.com/2019/12/25/86251260218576896_750x0.png?85,85?0
         * detail : 测试
         * babyBirthday : 2009-05-01
         * isRecommend : 0
         * study_contents_title :
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

        public String getBabyBirthday() {
            return babyBirthday;
        }

        public void setBabyBirthday(String babyBirthday) {
            this.babyBirthday = babyBirthday;
        }

        public int getIsRecommend() {
            return isRecommend;
        }

        public void setIsRecommend(int isRecommend) {
            this.isRecommend = isRecommend;
        }

        public String getStudy_contents_title() {
            return study_contents_title;
        }

        public void setStudy_contents_title(String study_contents_title) {
            this.study_contents_title = study_contents_title;
        }
    }
}
