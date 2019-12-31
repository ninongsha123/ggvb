package com.tiancaijiazu.app.fragments.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2019/6/10/010.
 */

public class CollegeCourseBean implements Serializable {

    /**
     * code : 0
     * msg : OK
     * result : {"courseInfo":{"courseId":1,"catalogId":1,"title":"好好说话，幸福生活","summary":"幸福生活幸福生活","instructor":"龙龙","price":198,"promoPrice":168,"picUri":"http://192.168.0.200/images/course.jpg","classCount":3,"description":"好好说话，幸福生活的详情","courseType":0},"chapterList":[{"chapterId":1,"courseId":1,"title":"夫妻关系中如何定位自己的身份","sortId":1,"contentsList":[{"contentsId":1,"title":"夫妻关系1","duration":188,"chapterId":1,"courseId":1,"type":2,"picUri":"http://192.168.0.200/images/course.jpg","mediaUri":"http://music.163.com/song/media/outer/url?id=25906124.mp3","isFree":1,"description":"夫妻关系1的课程文稿"},{"contentsId":2,"title":"夫妻关系2","duration":163,"chapterId":1,"courseId":1,"type":2,"picUri":"http://192.168.0.200/images/course.jpg","mediaUri":"http://music.163.com/song/media/outer/url?id=317151.mp3","isFree":0,"description":"夫妻关系2的课程文稿"},{"contentsId":3,"title":"夫妻关系3","duration":188,"chapterId":1,"courseId":1,"type":1,"picUri":"http://192.168.0.200/images/course.jpg","mediaUri":"http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4","isFree":0,"description":"夫妻关系3的课程文稿"}]}]}
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
         * courseInfo : {"courseId":1,"catalogId":1,"title":"好好说话，幸福生活","summary":"幸福生活幸福生活","instructor":"龙龙","price":198,"promoPrice":168,"picUri":"http://192.168.0.200/images/course.jpg","classCount":3,"description":"好好说话，幸福生活的详情","courseType":0}
         * chapterList : [{"chapterId":1,"courseId":1,"title":"夫妻关系中如何定位自己的身份","sortId":1,"contentsList":[{"contentsId":1,"title":"夫妻关系1","duration":188,"chapterId":1,"courseId":1,"type":2,"picUri":"http://192.168.0.200/images/course.jpg","mediaUri":"http://music.163.com/song/media/outer/url?id=25906124.mp3","isFree":1,"description":"夫妻关系1的课程文稿"},{"contentsId":2,"title":"夫妻关系2","duration":163,"chapterId":1,"courseId":1,"type":2,"picUri":"http://192.168.0.200/images/course.jpg","mediaUri":"http://music.163.com/song/media/outer/url?id=317151.mp3","isFree":0,"description":"夫妻关系2的课程文稿"},{"contentsId":3,"title":"夫妻关系3","duration":188,"chapterId":1,"courseId":1,"type":1,"picUri":"http://192.168.0.200/images/course.jpg","mediaUri":"http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4","isFree":0,"description":"夫妻关系3的课程文稿"}]}]
         */

        private CourseInfoBean courseInfo;
        private List<ChapterListBean> chapterList;

        public CourseInfoBean getCourseInfo() {
            return courseInfo;
        }

        public void setCourseInfo(CourseInfoBean courseInfo) {
            this.courseInfo = courseInfo;
        }

        public List<ChapterListBean> getChapterList() {
            return chapterList;
        }

        public void setChapterList(List<ChapterListBean> chapterList) {
            this.chapterList = chapterList;
        }

        public static class CourseInfoBean implements Serializable{
            /**
             * courseId : 1
             * catalogId : 1
             * title : 好好说话，幸福生活
             * summary : 幸福生活幸福生活
             * instructor : 龙龙
             * price : 198
             * promoPrice : 168
             * picUri : http://192.168.0.200/images/course.jpg
             * classCount : 3
             * description : 好好说话，幸福生活的详情
             * courseType : 0
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

        public static class ChapterListBean implements Serializable{
            /**
             * chapterId : 1
             * courseId : 1
             * title : 夫妻关系中如何定位自己的身份
             * sortId : 1
             * contentsList : [{"contentsId":1,"title":"夫妻关系1","duration":188,"chapterId":1,"courseId":1,"type":2,"picUri":"http://192.168.0.200/images/course.jpg","mediaUri":"http://music.163.com/song/media/outer/url?id=25906124.mp3","isFree":1,"description":"夫妻关系1的课程文稿"},{"contentsId":2,"title":"夫妻关系2","duration":163,"chapterId":1,"courseId":1,"type":2,"picUri":"http://192.168.0.200/images/course.jpg","mediaUri":"http://music.163.com/song/media/outer/url?id=317151.mp3","isFree":0,"description":"夫妻关系2的课程文稿"},{"contentsId":3,"title":"夫妻关系3","duration":188,"chapterId":1,"courseId":1,"type":1,"picUri":"http://192.168.0.200/images/course.jpg","mediaUri":"http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4","isFree":0,"description":"夫妻关系3的课程文稿"}]
             */

            private long chapterId;
            private long courseId;
            private String title;
            private long sortId;
            private List<ContentsListBean> contentsList;

            public long getChapterId() {
                return chapterId;
            }

            public void setChapterId(long chapterId) {
                this.chapterId = chapterId;
            }

            public long getCourseId() {
                return courseId;
            }

            public void setCourseId(long courseId) {
                this.courseId = courseId;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public long getSortId() {
                return sortId;
            }

            public void setSortId(long sortId) {
                this.sortId = sortId;
            }

            public List<ContentsListBean> getContentsList() {
                return contentsList;
            }

            public void setContentsList(List<ContentsListBean> contentsList) {
                this.contentsList = contentsList;
            }

            public static class ContentsListBean implements Serializable{
                /**
                 * contentsId : 1
                 * title : 夫妻关系1
                 * duration : 188
                 * chapterId : 1
                 * courseId : 1
                 * type : 2
                 * picUri : http://192.168.0.200/images/course.jpg
                 * mediaUri : http://music.163.com/song/media/outer/url?id=25906124.mp3
                 * isFree : 1
                 * description : 夫妻关系1的课程文稿
                 */

                private long contentsId;
                private String title;
                private int duration;
                private long chapterId;
                private long courseId;
                private int type;
                private String picUri;
                private String mediaUri;
                private int isFree;
                private String description;

                public long getContentsId() {
                    return contentsId;
                }

                public void setContentsId(long contentsId) {
                    this.contentsId = contentsId;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public int getDuration() {
                    return duration;
                }

                public void setDuration(int duration) {
                    this.duration = duration;
                }

                public long getChapterId() {
                    return chapterId;
                }

                public void setChapterId(long chapterId) {
                    this.chapterId = chapterId;
                }

                public long getCourseId() {
                    return courseId;
                }

                public void setCourseId(long courseId) {
                    this.courseId = courseId;
                }

                public int getType() {
                    return type;
                }

                public void setType(int type) {
                    this.type = type;
                }

                public String getPicUri() {
                    return picUri;
                }

                public void setPicUri(String picUri) {
                    this.picUri = picUri;
                }

                public String getMediaUri() {
                    return mediaUri;
                }

                public void setMediaUri(String mediaUri) {
                    this.mediaUri = mediaUri;
                }

                public int getIsFree() {
                    return isFree;
                }

                public void setIsFree(int isFree) {
                    this.isFree = isFree;
                }

                public String getDescription() {
                    return description;
                }

                public void setDescription(String description) {
                    this.description = description;
                }
            }
        }
    }
}
