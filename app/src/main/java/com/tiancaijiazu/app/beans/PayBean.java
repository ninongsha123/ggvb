package com.tiancaijiazu.app.beans;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2019/6/24/024.
 */

public class PayBean {
    /**
     * code : 0
     * msg : OK
     * result : {"appid":"wx101b51155a36e63f","noncestr":"3153794862","package":"Sign=WXPay","partnerid":"1539480901","prepayid":"wx2410064462279198f0f75b731829885400","sign":"56d33011f54cd37efca05dd724d9efc1ae00de9237f0829e9bd5434f7f1c912e","timestamp":"1561342004"}
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

    public static class ResultBean {
        /**
         * appid : wx101b51155a36e63f
         * noncestr : 3153794862
         * package : Sign=WXPay
         * partnerid : 1539480901
         * prepayid : wx2410064462279198f0f75b731829885400
         * sign : 56d33011f54cd37efca05dd724d9efc1ae00de9237f0829e9bd5434f7f1c912e
         * timestamp : 1561342004
         */

        private String appid;
        private String noncestr;
        @SerializedName("package")
        private String packageX;
        private String partnerid;
        private String prepayid;
        private String sign;
        private String timestamp;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }
    }
}
