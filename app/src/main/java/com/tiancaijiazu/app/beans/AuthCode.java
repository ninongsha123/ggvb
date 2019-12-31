package com.tiancaijiazu.app.beans;

/**
 * Created by Administrator on 2019/5/17/017.
 */

public class AuthCode {
    /**
     * code : 0
     * msg : OK
     * result : 短信验证码发送成功
     */

    private String code;
    private String msg;
    private String result;

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

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
