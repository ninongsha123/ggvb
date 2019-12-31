package com.tiancaijiazu.app.beans;

import java.util.List;

/**
 * Created by Administrator on 2019/8/22/022.
 */

public class ClassGardenBean {
    /**
     * code : 0
     * msg : OK
     * result : [{"companyId":41036119596470272,"companyName":"河北省邢台市早教园","logoUri":"http://img.tiancaijiazu.com/2019/08/22/41035503440629760_190x190.png?190,190","address":"河北省邢台市桥东区开元北路256-1号","longitude":"37.08524350","latitude":"114.51405500"},{"companyId":41035335093850112,"companyName":"保定市天才家族幼儿园","logoUri":"http://img.tiancaijiazu.com/2019/08/22/41034007684714496_190x190.png?190,190","address":"保定市竞秀区朝阳南大街18号","longitude":"38.87185650","latitude":"38.87185650"},{"companyId":41033573138042880,"companyName":"北京公司早教园","logoUri":"http://img.tiancaijiazu.com/2019/08/22/41032884429131776_190x190.png?190,190","address":"北京市通州区榆西一街1号院4号楼6层","longitude":"39.99610200","latitude":"116.42576100"},{"companyId":41032759636004864,"companyName":"青岛公司早教园所","logoUri":"http://img.tiancaijiazu.com/2019/08/22/41032173343608832_190x190.png?190,190","address":"青岛市即墨区鹤山路888号世贸大厦15层","longitude":"36.39996600","latitude":"120.41551200"},{"companyId":41031602976985088,"companyName":"青岛服饰公司早教园","logoUri":"http://img.tiancaijiazu.com/2019/08/22/41029711417184256_190x190.png?190,190","address":"青岛市即墨区文化路和华山三路交叉口南200米","longitude":"36.38301500","latitude":"120.43306400"}]
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
         * companyId : 41036119596470272
         * companyName : 河北省邢台市早教园
         * logoUri : http://img.tiancaijiazu.com/2019/08/22/41035503440629760_190x190.png?190,190
         * address : 河北省邢台市桥东区开元北路256-1号
         * longitude : 37.08524350
         * latitude : 114.51405500
         */

        private long companyId;
        private String companyName;
        private String logoUri;
        private String address;
        private String longitude;
        private String latitude;
        private String juli;
        private double order;

        public double getOrder() {
            return order;
        }

        public void setOrder(double order) {
            this.order = order;
        }

        public String getJuli() {
            return juli;
        }

        public void setJuli(String juli) {
            this.juli = juli;
        }

        public long getCompanyId() {
            return companyId;
        }

        public void setCompanyId(long companyId) {
            this.companyId = companyId;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getLogoUri() {
            return logoUri;
        }

        public void setLogoUri(String logoUri) {
            this.logoUri = logoUri;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }
    }
}
