package com.tiancaijiazu.app.beans;

import java.io.Serializable;

public class MemberFeedbackBeans implements Serializable {

    /**
     * code : 0
     * msg : OK
     * result : 提交成功
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
