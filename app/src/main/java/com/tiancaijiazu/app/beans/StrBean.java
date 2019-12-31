package com.tiancaijiazu.app.beans;

/**
 * Created by Administrator on 2019/8/2/002.
 */

public class StrBean {
    /**
     * code : 20102
     * msg : 订单创建失败
     * result : 商品不存在或已下架
     */

    private String code;
    private String msg;
    private Object result;

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

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
