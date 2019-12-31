package com.tiancaijiazu.app.beans;

import java.util.List;

/**
 * Created by Administrator on 2019/6/20/020.
 */

public class SiteBean {
    /**
     * code : 0
     * msg : OK
     * result : [{"consigneeId":18206524069515264,"name":"丽丽","area":"北京市-海淀区","address":"立方庭","mobile":"15010565227","isDefault":0}]
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
         * consigneeId : 18206524069515264
         * name : 丽丽
         * area : 北京市-海淀区
         * address : 立方庭
         * mobile : 15010565227
         * isDefault : 0
         */

        private long consigneeId;
        private String name;
        private String area;
        private String address;
        private String mobile;
        private int isDefault;

        public long getConsigneeId() {
            return consigneeId;
        }

        public void setConsigneeId(long consigneeId) {
            this.consigneeId = consigneeId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public int getIsDefault() {
            return isDefault;
        }

        public void setIsDefault(int isDefault) {
            this.isDefault = isDefault;
        }
    }
}
