package com.tfedu.update.entity;

import java.io.Serializable;

public class AppCheckVersion implements Serializable {

    /**
     * code : 0
     * msg : OK
     * result : {"mustBeUpdate":"1","latestVersion":"1.0.1","apkDownloadUrl":"http://www.tiancaijiazu.com/"}
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
         * mustBeUpdate : 1
         * latestVersion : 1.0.1
         * apkDownloadUrl : http://www.tiancaijiazu.com/
         */

        private String mustBeUpdate;
        private String latestVersion;
        private String apkDownloadUrl;

        public String getMustBeUpdate() {
            return mustBeUpdate;
        }

        public void setMustBeUpdate(String mustBeUpdate) {
            this.mustBeUpdate = mustBeUpdate;
        }

        public String getLatestVersion() {
            return latestVersion;
        }

        public void setLatestVersion(String latestVersion) {
            this.latestVersion = latestVersion;
        }

        public String getApkDownloadUrl() {
            return apkDownloadUrl;
        }

        public void setApkDownloadUrl(String apkDownloadUrl) {
            this.apkDownloadUrl = apkDownloadUrl;
        }
    }
}
