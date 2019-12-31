package com.tiancaijiazu.app.beans;

/**
 * Created by Administrator on 2019/6/19/019.
 */

public class WxCodeBean {
    /**
     * code : 3
     * msg : 用户微信号未绑定
     * result : {"wxOpenid":"osMLB579T7jpJf_ntFqSPCRGhfkA"}
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
         * wxOpenid : osMLB579T7jpJf_ntFqSPCRGhfkA
         */

        private String wxUnionid;

        public String getWxOpenid() {
            return wxUnionid;
        }

        public void setWxOpenid(String wxOpenid) {
            this.wxUnionid = wxOpenid;
        }
    }
}
