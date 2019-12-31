package com.tiancaijiazu.app.beans;

import java.util.List;

/**
 * Created by Administrator on 2019/6/1/001.
 */

public class LikeListsBean {
    /**
     * code : 0
     * msg : OK
     * result : [{"likesId":11248141449957376,"contentId":10887329585696768,"userId":9567974524588032,"likesTime":"2019-06-01 11:06:05.902","nickname":"杨旭","avatar":"http://192.168.0.200/images/Avatar_200.jpg"}]
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
         * likesId : 11248141449957376
         * contentId : 10887329585696768
         * userId : 9567974524588032
         * likesTime : 2019-06-01 11:06:05.902
         * nickname : 杨旭
         * avatar : http://192.168.0.200/images/Avatar_200.jpg
         */

        private long likesId;
        private long contentId;
        private long userId;
        private String likesTime;
        private String nickname;
        private String avatar;
        private String summary;


        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

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
