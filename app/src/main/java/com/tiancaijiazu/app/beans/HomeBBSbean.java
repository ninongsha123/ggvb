package com.tiancaijiazu.app.beans;

import java.util.List;

/**
 * Created by Administrator on 2019/8/14/014.
 */

public class HomeBBSbean {
    /**
     * code : 0
     * msg : OK
     * result : [{"articleId":22179163414859776,"userId":9567974524588032,"userAvatar":"http://img.tiancaijiazu.com/2019/07/02/22627667404787712_148x148.jpg?148,148","nickname":"杨旭","picList":"http://img.tiancaijiazu.com/2019/07/01/22179162538250240_0x562.jpg|http://img.tiancaijiazu.com/2019/07/01/22179162542444544_0x562.jpg|http://img.tiancaijiazu.com/2019/07/01/22179162542444545_0x562.jpg|http://img.tiancaijiazu.com/2019/07/01/22179162542444546_0x562.jpg","title":"国家科技奖","summary":"国家科技奖国家科技奖国家科技奖国家科技奖国家科技奖国家科技奖国家科技奖国家科技奖国家科技奖国家科技奖国家科技奖国家科技奖国家科技奖国家科技奖国家科技奖国家科技奖国家科技奖国家科技奖国家科技奖国家科技奖国家科技奖国家科技奖国家科技奖国家科技奖国家科技奖国家科技奖国家科技奖国家科技奖"},{"articleId":28248604019920896,"userId":19571089252421632,"userAvatar":"http://img.tiancaijiazu.com/2019/07/30/32548871817269248_148x148.jpg?148,148","nickname":"刘泰隆！","picList":"http://img.tiancaijiazu.com/2019/07/18/28248603504021504_0x562.jpg","title":"天才家族线上早教","summary":"天才家族线上早教天才家族线上早教天才家族线上早教天才家族线上早教天才家族线上早教天才家族线上早教天才家族线上早教天才家族线上早教天才家族线上早教天才家族线上早教天才家族线上早教天才家族线上早教天才家族线上早教天才家族线上早教天才家族线上早教v"},{"articleId":37461830788059136,"userId":9567974524588032,"userAvatar":"http://img.tiancaijiazu.com/2019/07/02/22627667404787712_148x148.jpg?148,148","nickname":"杨旭","picList":"http://img.tiancaijiazu.com/2019/08/12/37461829643014144_0x562.jpg","title":"记录了","summary":"记录了记录了记录了记录了记录了记录了记录了记录了记录了记录了记录了记录了记录了记录了记录了记录了记录了记录了记录了记录了记录了记录了记录了记录了记录了记录了记录了记录了记录了记录了记录了记录了记录了记录了记录了记录了"}]
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
         * articleId : 22179163414859776
         * userId : 9567974524588032
         * userAvatar : http://img.tiancaijiazu.com/2019/07/02/22627667404787712_148x148.jpg?148,148
         * nickname : 杨旭
         * picList : http://img.tiancaijiazu.com/2019/07/01/22179162538250240_0x562.jpg|http://img.tiancaijiazu.com/2019/07/01/22179162542444544_0x562.jpg|http://img.tiancaijiazu.com/2019/07/01/22179162542444545_0x562.jpg|http://img.tiancaijiazu.com/2019/07/01/22179162542444546_0x562.jpg
         * title : 国家科技奖
         * summary : 国家科技奖国家科技奖国家科技奖国家科技奖国家科技奖国家科技奖国家科技奖国家科技奖国家科技奖国家科技奖国家科技奖国家科技奖国家科技奖国家科技奖国家科技奖国家科技奖国家科技奖国家科技奖国家科技奖国家科技奖国家科技奖国家科技奖国家科技奖国家科技奖国家科技奖国家科技奖国家科技奖国家科技奖
         */

        private long articleId;
        private long userId;
        private String userAvatar;
        private String nickname;
        private String picList;
        private String title;
        private String summary;

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

        public String getPicList() {
            return picList;
        }

        public void setPicList(String picList) {
            this.picList = picList;
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
    }
}
