package com.tiancaijiazu.app.beans;

import java.io.Serializable;
import java.util.List;

public class ShortCollectListBean implements Serializable {

    /**
     * code : 0
     * msg : OK
     * result : [{"videoId":56263264224546816,"title":"爱心无限大-天才家族育儿早教","summary":"爱心无限大，亲在的家长朋友们，你们的爱心足够大吗？让我们一起开看看吧。","picUri":"http://img.tiancaijiazu.com/2019/10/03/56263239029362688_222x306.jpg?222,306","largePicUri":"http://img.tiancaijiazu.com/2019/10/03/56263239029362688_750x1334.jpg?750,1334","videoUri":"http://img.tiancaijiazu.com/2019/10/03/56263225943134208.mp4?15","duration":15}]
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
         * videoId : 56263264224546816
         * title : 爱心无限大-天才家族育儿早教
         * summary : 爱心无限大，亲在的家长朋友们，你们的爱心足够大吗？让我们一起开看看吧。
         * picUri : http://img.tiancaijiazu.com/2019/10/03/56263239029362688_222x306.jpg?222,306
         * largePicUri : http://img.tiancaijiazu.com/2019/10/03/56263239029362688_750x1334.jpg?750,1334
         * videoUri : http://img.tiancaijiazu.com/2019/10/03/56263225943134208.mp4?15
         * duration : 15
         */

        private long videoId;
        private String title;
        private String summary;
        private String picUri;
        private String largePicUri;
        private String videoUri;
        private int duration;

        public long getVideoId() {
            return videoId;
        }

        public void setVideoId(long videoId) {
            this.videoId = videoId;
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

        public String getPicUri() {
            return picUri;
        }

        public void setPicUri(String picUri) {
            this.picUri = picUri;
        }

        public String getLargePicUri() {
            return largePicUri;
        }

        public void setLargePicUri(String largePicUri) {
            this.largePicUri = largePicUri;
        }

        public String getVideoUri() {
            return videoUri;
        }

        public void setVideoUri(String videoUri) {
            this.videoUri = videoUri;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }
    }
}
