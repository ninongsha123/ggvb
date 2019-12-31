package com.tiancaijiazu.app.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2019/5/29/029.
 */

public class ArticleDatas implements Serializable{

    /**
     * code : 0
     * msg : OK
     * result : {"articleId":10577360017559552,"userId":9569814792245248,"title":"10572699734773760","detail":"Ahhdhhhhhhhjksafhkjdsafdkjsakjfkjsanksafkjsajkfsakldflkjsajflkaskfjlsalkjfglkjasngkjfdnkljgnkljdfnglkjnfdljkngjklfndjklgnljkfdngknfdjklngkldfngklnsdfkljngjklfdslgndsfjklgndsfngnljkds","publishTime":"2019-05-30 14:40:39.146","likes":0,"collect":0,"discuss":3,"isLikes":0,"imgList":[{"imgId":10577360126611456,"userId":9569814792245248,"articleId":10577360017559552,"imgUri":"http://192.168.0.200/images/2019/05/30/10577359963033600.jpg","imgSize":"original","publishTime":"2019-05-30 14:40:39.172"}],"subjectList":[{"subjectId":10576168227049472,"parentId":0,"subjectName":"男士护肤指南"},{"subjectId":10577360046919680,"parentId":0,"subjectName":"女生夏季穿搭"},{"subjectId":10576168231243776,"parentId":0,"subjectName":"妈妈的jk高难"}],"discussList":[{"discussId":10583298841645056,"articleId":10577360017559552,"replyId":0,"replyCount":26,"userId":9567974524588032,"userNickname":"杨旭","userAvatar":"http://192.168.0.200/images/Avatar_200.jpg","content":"毛毛是不是你","publishTime":"2019-05-30 15:04:15.072","likes":0,"isLikes":0,"replyUserId":0,"replyNickname":"","subDiscuss":[{"discussId":10931045415391232,"articleId":10577360017559552,"replyId":10583298841645056,"replyCount":0,"userId":10571612466319360,"userNickname":"毛毛","userAvatar":"http://192.168.0.200/images/Avatar_200.jpg","content":"Haha day ","publishTime":"2019-05-31 14:06:04.316","likes":0,"isLikes":0,"replyUserId":9567974524588032,"replyNickname":"杨旭"}]},{"discussId":10582120313524224,"articleId":10577360017559552,"replyId":0,"replyCount":0,"userId":9567974524588032,"userNickname":"杨旭","userAvatar":"http://192.168.0.200/images/Avatar_200.jpg","content":"说话啊","publishTime":"2019-05-30 14:59:34.089","likes":0,"isLikes":0,"replyUserId":0,"replyNickname":"","subDiscuss":[]},{"discussId":10582087396626432,"articleId":10577360017559552,"replyId":0,"replyCount":0,"userId":9567974524588032,"userNickname":"杨旭","userAvatar":"http://192.168.0.200/images/Avatar_200.jpg","content":"丽丽，是不是你发的。","publishTime":"2019-05-30 14:59:26.241","likes":0,"isLikes":0,"replyUserId":0,"replyNickname":"","subDiscuss":[]}]}
     */

    private String code;
    private String msg;
    private ResultBean result;

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

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean implements Serializable{
        /**
         * articleId : 10577360017559552
         * userId : 9569814792245248
         * title : 10572699734773760
         * detail : Ahhdhhhhhhhjksafhkjdsafdkjsakjfkjsanksafkjsajkfsakldflkjsajflkaskfjlsalkjfglkjasngkjfdnkljgnkljdfnglkjnfdljkngjklfndjklgnljkfdngknfdjklngkldfngklnsdfkljngjklfdslgndsfjklgndsfngnljkds
         * publishTime : 2019-05-30 14:40:39.146
         * likes : 0
         * collect : 0
         * discuss : 3
         * isLikes : 0
         * imgList : [{"imgId":10577360126611456,"userId":9569814792245248,"articleId":10577360017559552,"imgUri":"http://192.168.0.200/images/2019/05/30/10577359963033600.jpg","imgSize":"original","publishTime":"2019-05-30 14:40:39.172"}]
         * subjectList : [{"subjectId":10576168227049472,"parentId":0,"subjectName":"男士护肤指南"},{"subjectId":10577360046919680,"parentId":0,"subjectName":"女生夏季穿搭"},{"subjectId":10576168231243776,"parentId":0,"subjectName":"妈妈的jk高难"}]
         * discussList : [{"discussId":10583298841645056,"articleId":10577360017559552,"replyId":0,"replyCount":26,"userId":9567974524588032,"userNickname":"杨旭","userAvatar":"http://192.168.0.200/images/Avatar_200.jpg","content":"毛毛是不是你","publishTime":"2019-05-30 15:04:15.072","likes":0,"isLikes":0,"replyUserId":0,"replyNickname":"","subDiscuss":[{"discussId":10931045415391232,"articleId":10577360017559552,"replyId":10583298841645056,"replyCount":0,"userId":10571612466319360,"userNickname":"毛毛","userAvatar":"http://192.168.0.200/images/Avatar_200.jpg","content":"Haha day ","publishTime":"2019-05-31 14:06:04.316","likes":0,"isLikes":0,"replyUserId":9567974524588032,"replyNickname":"杨旭"}]},{"discussId":10582120313524224,"articleId":10577360017559552,"replyId":0,"replyCount":0,"userId":9567974524588032,"userNickname":"杨旭","userAvatar":"http://192.168.0.200/images/Avatar_200.jpg","content":"说话啊","publishTime":"2019-05-30 14:59:34.089","likes":0,"isLikes":0,"replyUserId":0,"replyNickname":"","subDiscuss":[]},{"discussId":10582087396626432,"articleId":10577360017559552,"replyId":0,"replyCount":0,"userId":9567974524588032,"userNickname":"杨旭","userAvatar":"http://192.168.0.200/images/Avatar_200.jpg","content":"丽丽，是不是你发的。","publishTime":"2019-05-30 14:59:26.241","likes":0,"isLikes":0,"replyUserId":0,"replyNickname":"","subDiscuss":[]}]
         */

        private long articleId;
        private long userId;
        private String title;
        private String detail;
        private String publishTime;
        private String coverPics;
        private String largePics;
        private int likes;
        private int collect;
        private int discuss;
        private int isLikes;
        private int isCollect;
        private int isFollow;
        private List<ImgListBean> imgList;
        private List<SubjectListBean> subjectList;
        private List<DiscussListBean> discussList;
        private List<LikesListBean> likesList;

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

        public int getIsCollect() {
            return isCollect;
        }

        public void setIsCollect(int isCollect) {
            this.isCollect = isCollect;
        }

        public int getIsFollow() {
            return isFollow;
        }

        public void setIsFollow(int isFollow) {
            this.isFollow = isFollow;
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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
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

        public List<ImgListBean> getImgList() {
            return imgList;
        }

        public void setImgList(List<ImgListBean> imgList) {
            this.imgList = imgList;
        }

        public List<SubjectListBean> getSubjectList() {
            return subjectList;
        }

        public void setSubjectList(List<SubjectListBean> subjectList) {
            this.subjectList = subjectList;
        }

        public List<DiscussListBean> getDiscussList() {
            return discussList;
        }

        public void setDiscussList(List<DiscussListBean> discussList) {
            this.discussList = discussList;
        }
        public List<LikesListBean> getLikesList() {
            return likesList;
        }

        public void setLikesList(List<LikesListBean> likesList) {
            this.likesList = likesList;
        }

        public static class ImgListBean implements Serializable{
            /**
             * imgId : 10577360126611456
             * userId : 9569814792245248
             * articleId : 10577360017559552
             * imgUri : http://192.168.0.200/images/2019/05/30/10577359963033600.jpg
             * imgSize : original
             * publishTime : 2019-05-30 14:40:39.172
             */

            private long imgId;
            private long userId;
            private long articleId;
            private String imgUri;
            private String imgSize;
            private String publishTime;

            public long getImgId() {
                return imgId;
            }

            public void setImgId(long imgId) {
                this.imgId = imgId;
            }

            public long getUserId() {
                return userId;
            }

            public void setUserId(long userId) {
                this.userId = userId;
            }

            public long getArticleId() {
                return articleId;
            }

            public void setArticleId(long articleId) {
                this.articleId = articleId;
            }

            public String getImgUri() {
                return imgUri;
            }

            public void setImgUri(String imgUri) {
                this.imgUri = imgUri;
            }

            public String getImgSize() {
                return imgSize;
            }

            public void setImgSize(String imgSize) {
                this.imgSize = imgSize;
            }

            public String getPublishTime() {
                return publishTime;
            }

            public void setPublishTime(String publishTime) {
                this.publishTime = publishTime;
            }
        }

        public static class SubjectListBean implements Serializable{
            /**
             * subjectId : 10576168227049472
             * parentId : 0
             * subjectName : 男士护肤指南
             */

            private long subjectId;
            private int parentId;
            private String subjectName;

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
        }

        public static class DiscussListBean implements Serializable{

            /**
             * discussId : 10583298841645056
             * articleId : 10577360017559552
             * replyId : 0
             * replyCount : 26
             * userId : 9567974524588032
             * userNickname : 杨旭
             * userAvatar : http://192.168.0.200/images/Avatar_200.jpg
             * content : 毛毛是不是你
             * publishTime : 2019-05-30 15:04:15.072
             * likes : 0
             * isLikes : 0
             * replyUserId : 0
             * replyNickname :
             * subDiscuss : [{"discussId":10931045415391232,"articleId":10577360017559552,"replyId":10583298841645056,"replyCount":0,"userId":10571612466319360,"userNickname":"毛毛","userAvatar":"http://192.168.0.200/images/Avatar_200.jpg","content":"Haha day ","publishTime":"2019-05-31 14:06:04.316","likes":0,"isLikes":0,"replyUserId":9567974524588032,"replyNickname":"杨旭"}]
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
            private List<SubDiscussBean> subDiscuss;

            public DiscussListBean(long discussId, long articleId, long replyId, int replyCount, long userId, String userNickname, String userAvatar, String content, String publishTime, int likes, int isLikes, long replyUserId, String replyNickname, List<SubDiscussBean> subDiscuss) {
                this.discussId = discussId;
                this.articleId = articleId;
                this.replyId = replyId;
                this.replyCount = replyCount;
                this.userId = userId;
                this.userNickname = userNickname;
                this.userAvatar = userAvatar;
                this.content = content;
                this.publishTime = publishTime;
                this.likes = likes;
                this.isLikes = isLikes;
                this.replyUserId = replyUserId;
                this.replyNickname = replyNickname;
                this.subDiscuss = subDiscuss;
            }

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

            public List<SubDiscussBean> getSubDiscuss() {
                return subDiscuss;
            }

            public void setSubDiscuss(List<SubDiscussBean> subDiscuss) {
                this.subDiscuss = subDiscuss;
            }

            public static class SubDiscussBean implements Serializable{
                public SubDiscussBean(long discussId, long articleId, long replyId, int replyCount, long userId, String userNickname, String userAvatar, String content, String publishTime, int likes, int isLikes, long replyUserId, String replyNickname) {
                    this.discussId = discussId;
                    this.articleId = articleId;
                    this.replyId = replyId;
                    this.replyCount = replyCount;
                    this.userId = userId;
                    this.userNickname = userNickname;
                    this.userAvatar = userAvatar;
                    this.content = content;
                    this.publishTime = publishTime;
                    this.likes = likes;
                    this.isLikes = isLikes;
                    this.replyUserId = replyUserId;
                    this.replyNickname = replyNickname;
                }

                /**
                 * discussId : 10931045415391232
                 * articleId : 10577360017559552
                 * replyId : 10583298841645056
                 * replyCount : 0
                 * userId : 10571612466319360
                 * userNickname : 毛毛
                 * userAvatar : http://192.168.0.200/images/Avatar_200.jpg
                 * content : Haha day
                 * publishTime : 2019-05-31 14:06:04.316
                 * likes : 0
                 * isLikes : 0
                 * replyUserId : 9567974524588032
                 * replyNickname : 杨旭
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
        public static class LikesListBean {
            /**
             * likesId : 11287016931201024
             * contentId : 10887329585696768
             * userId : 9569814792245248
             * likesTime : 2019-06-01 13:40:34.537
             * nickname : 丽丽
             * avatar : http://192.168.0.200/images/Avatar_200.jpg
             */

            private long likesId;
            private long contentId;
            private long userId;
            private String likesTime;
            private String nickname;
            private String avatar;

            public long getLikesId() {
                return likesId;
            }

            public void setLikesId(long likesId) {
                this.likesId = likesId;
            }

            public long getContentId() {
                return contentId;
            }

            public void setContentId(long contentId) {
                this.contentId = contentId;
            }

            public long getUserId() {
                return userId;
            }

            public void setUserId(long userId) {
                this.userId = userId;
            }

            public String getLikesTime() {
                return likesTime;
            }

            public void setLikesTime(String likesTime) {
                this.likesTime = likesTime;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }
        }
    }
}
