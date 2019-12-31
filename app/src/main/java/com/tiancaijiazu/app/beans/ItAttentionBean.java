package com.tiancaijiazu.app.beans;

import java.util.List;

/**
 * Created by Administrator on 2019/6/4/004.
 */

public class ItAttentionBean {
    /**
     * code : 0
     * msg : OK
     * result : [{"userId":9567974524588032,"nickname":"杨旭","avatar":"","summary":"杨旭的签名","isFollow":0}]
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
         * userId : 9567974524588032
         * nickname : 杨旭
         * avatar :
         * summary : 杨旭的签名
         * isFollow : 0
         */

        private long userId;
        private String nickname;
        private String avatar;
        private String summary;
        private int isFollow;

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
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

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public int getIsFollow() {
            return isFollow;
        }

        public void setIsFollow(int isFollow) {
            this.isFollow = isFollow;
        }
    }
}
