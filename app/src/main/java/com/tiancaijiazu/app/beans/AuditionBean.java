package com.tiancaijiazu.app.beans;

public class AuditionBean {

    /**
     * code : 0
     * msg : OK
     * result : {"cover":"http://img.tiancaijiazu.com/2019/09/07/46909330125099008_658x370.jpg?658,370","vidoUri":"http://img.tiancaijiazu.com/2019/09/07/46825592447963136.mp4?13"}
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
         * cover : http://img.tiancaijiazu.com/2019/09/07/46909330125099008_658x370.jpg?658,370
         * vidoUri : http://img.tiancaijiazu.com/2019/09/07/46825592447963136.mp4?13
         */

        private String cover;
        private String vidoUri;

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getVidoUri() {
            return vidoUri;
        }

        public void setVidoUri(String vidoUri) {
            this.vidoUri = vidoUri;
        }
    }
}
