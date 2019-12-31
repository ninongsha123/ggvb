package com.tiancaijiazu.app.beans;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/6/28/028.
 */

public class UserInfoBean implements Serializable{


    /**
     * code : 0
     * msg : OK
     * result : {"userid":43114236725039104,"mobile":"15648550590","signin":"2019-09-04 16:49:40","nickname":"天才用户0590","gender":2,"avatar":"http://img.tiancaijiazu.com/2019/08/30/43841610571714560_148x148.jpg?148,148","province":"浙江省","city":"嘉兴市","country":"中国","level":0,"isvip":0,"qrcode":"","summary":"看见了","vipLevel":0,"vipTitle":"","rongcloudToken":"Eo8QOoj76/1WxOKVg3b2PC4m4KYYISgUu81rKkZZlgigbF8Je50kYy3uiwW6dHiumSaSYG17X1s4gTWZOCnGWuNFjitp6ZO3sEqX5qHL1Dw=","follow":3,"fans":0,"likes":0}
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
         * userid : 43114236725039104
         * mobile : 15648550590
         * signin : 2019-09-04 16:49:40
         * nickname : 天才用户0590
         * gender : 2
         * avatar : http://img.tiancaijiazu.com/2019/08/30/43841610571714560_148x148.jpg?148,148
         * province : 浙江省
         * city : 嘉兴市
         * country : 中国
         * level : 0
         * isvip : 0
         * qrcode :
         * summary : 看见了
         * vipLevel : 0
         * vipTitle :
         * rongcloudToken : Eo8QOoj76/1WxOKVg3b2PC4m4KYYISgUu81rKkZZlgigbF8Je50kYy3uiwW6dHiumSaSYG17X1s4gTWZOCnGWuNFjitp6ZO3sEqX5qHL1Dw=
         * follow : 3
         * fans : 0
         * likes : 0
         */

        private long userid;
        private String mobile;
        private String signin;
        private String nickname;
        private int gender;
        private int referrerCode;
        private String avatar;
        private String province;
        private String city;
        private String country;
        private int level;
        private int isvip;
        private String qrcode;
        private String summary;
        private int vipLevel;
        private String vipTitle;
        private String rongcloudToken;
        private int follow;
        private int fans;
        private int likes;

        public int getReferrerCode() {
            return referrerCode;
        }

        public void setReferrerCode(int referrerCode) {
            this.referrerCode = referrerCode;
        }

        public long getUserid() {
            return userid;
        }

        public void setUserid(long userid) {
            this.userid = userid;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getSignin() {
            return signin;
        }

        public void setSignin(String signin) {
            this.signin = signin;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public int getIsvip() {
            return isvip;
        }

        public void setIsvip(int isvip) {
            this.isvip = isvip;
        }

        public String getQrcode() {
            return qrcode;
        }

        public void setQrcode(String qrcode) {
            this.qrcode = qrcode;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public int getVipLevel() {
            return vipLevel;
        }

        public void setVipLevel(int vipLevel) {
            this.vipLevel = vipLevel;
        }

        public String getVipTitle() {
            return vipTitle;
        }

        public void setVipTitle(String vipTitle) {
            this.vipTitle = vipTitle;
        }

        public String getRongcloudToken() {
            return rongcloudToken;
        }

        public void setRongcloudToken(String rongcloudToken) {
            this.rongcloudToken = rongcloudToken;
        }

        public int getFollow() {
            return follow;
        }

        public void setFollow(int follow) {
            this.follow = follow;
        }

        public int getFans() {
            return fans;
        }

        public void setFans(int fans) {
            this.fans = fans;
        }

        public int getLikes() {
            return likes;
        }

        public void setLikes(int likes) {
            this.likes = likes;
        }
    }
}
