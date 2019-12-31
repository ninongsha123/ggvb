package com.tiancaijiazu.app.beans;

public class BringBean
{
    /**
     * code : 0
     * msg : OK
     * result : {"H5_FenLingYangYu":"http://www.tiancaijiazu.com","H5_YuErBaiKe":"http://www.tiancaijiazu.com"}
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
         * H5_FenLingYangYu : http://www.tiancaijiazu.com
         * H5_YuErBaiKe : http://www.tiancaijiazu.com
         */

        private String H5_FenLingYangYu;
        private String H5_YuErBaiKe;

        public String getH5_FenLingYangYu() {
            return H5_FenLingYangYu;
        }

        public void setH5_FenLingYangYu(String H5_FenLingYangYu) {
            this.H5_FenLingYangYu = H5_FenLingYangYu;
        }

        public String getH5_YuErBaiKe() {
            return H5_YuErBaiKe;
        }

        public void setH5_YuErBaiKe(String H5_YuErBaiKe) {
            this.H5_YuErBaiKe = H5_YuErBaiKe;
        }
    }
}
