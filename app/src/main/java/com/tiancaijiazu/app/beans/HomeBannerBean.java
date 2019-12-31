package com.tiancaijiazu.app.beans;

import java.util.List;

/**
 * Created by Administrator on 2019/8/8/008.
 */

public class HomeBannerBean {
    /**
     * code : 0
     * msg : OK
     * result : [{"picUri":"http://img.tiancaijiazu.com/2019/08/08/35962310455595008_750x400.png?750,400","route":"WEB","target":"http://www.tiancaijiazu.com"},{"picUri":"http://img.tiancaijiazu.com/2019/08/08/35960958069379072_750x400.png?750,400","route":"STUDY_COURSE","target":"30231205656006656"},{"picUri":"http://img.tiancaijiazu.com/2019/08/08/35959610938626048_750x400.png?750,400","route":"MALL_SKU","target":"33730248625491968"}]
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
         * picUri : http://img.tiancaijiazu.com/2019/08/08/35962310455595008_750x400.png?750,400
         * route : WEB
         * target : http://www.tiancaijiazu.com
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
