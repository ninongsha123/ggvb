package com.tiancaijiazu.app.beans;

import java.util.List;

/**
 * Created by Administrator on 2019/8/10/010.
 */

public class AdPositionIdBean {
    /**
     * code : 0
     * msg : OK
     * result : [{"picUri":"http://img.tiancaijiazu.com/2019/08/10/36587583035281408_686x130.png?686,130","route":"WEB","target":""}]
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
         * picUri : http://img.tiancaijiazu.com/2019/08/10/36587583035281408_686x130.png?686,130
         * route : WEB
         * target :
         */

        private String picUri;
        private String route;
        private String target;

        public String getPicUri() {
            return picUri;
        }

        public void setPicUri(String picUri) {
            this.picUri = picUri;
        }

        public String getRoute() {
            return route;
        }

        public void setRoute(String route) {
            this.route = route;
        }

        public String getTarget() {
            return target;
        }

        public void setTarget(String target) {
            this.target = target;
        }
    }
}
