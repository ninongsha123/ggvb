package com.tiancaijiazu.app.beans;

import java.io.Serializable;
import java.util.List;

public class MinVideoBean implements Serializable {
    /**
     * code : 0
     * msg : OK
     * result : [{"title":"44434","picUri":"http://img.tiancaijiazu.com/2019/09/07/46825565419868160_222x306.jpg?222,306","videoUri":"http://img.tiancaijiazu.com/2019/09/07/46825592447963136.mp4?13","duration":13},{"title":"12121212","picUri":"http://img.tiancaijiazu.com/2019/09/07/46825517437030400_222x306.jpg?222,306","videoUri":"http://img.tiancaijiazu.com/2019/09/07/46825500810809344.mp4?13","duration":13},{"title":"33333","picUri":"http://img.tiancaijiazu.com/2019/09/07/46825433244766208_222x306.jpg?222,306","videoUri":"http://img.tiancaijiazu.com/2019/09/07/46825447731892224.mp4?13","duration":13},{"title":"1212","picUri":"http://img.tiancaijiazu.com/2019/09/07/46825368816062464_222x306.jpg?222,306","videoUri":"http://img.tiancaijiazu.com/2019/09/07/46825378156777472.mp4?13","duration":13},{"title":"好的视频","picUri":"http://img.tiancaijiazu.com/2019/09/07/46822653025193984_222x306.jpg?222,306","videoUri":"http://img.tiancaijiazu.com/2019/09/07/46822664853131264.mp4?13","duration":13}]
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
         * title : 44434
         * picUri : http://img.tiancaijiazu.com/2019/09/07/46825565419868160_222x306.jpg?222,306
         * videoUri : http://img.tiancaijiazu.com/2019/09/07/46825592447963136.mp4?13
         * duration : 13
         */
        private long videoId;
        private String title;
        private String picUri;
        private String videoUri;
        private int duration;
        private String largePicUri;
        private String summary;

        public long getVideoId() {
            return videoId;
        }

        public void setVideoId(long videoId) {
            this.videoId = videoId;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getLargePicUri() {
            return largePicUri;
        }

        public void setLargePicUri(String largePicUri) {
            this.largePicUri = largePicUri;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPicUri() {
            return picUri;
        }

        public void setPicUri(String picUri) {
            this.picUri = picUri;
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
