package com.tiancaijiazu.app.beans;

import java.util.List;

public class GetCardsBean {
    /**
     * code : 0
     * msg : OK
     * result : [{"cardNo":24320,"nickName":"天才用户9496"},{"cardNo":24321,"nickName":"阳光"},{"cardNo":24322,"nickName":"天才用户0333"},{"cardNo":24323,"nickName":"丹丹"},{"cardNo":24324,"nickName":"王宇"},{"cardNo":24325,"nickName":"小小吖"},{"cardNo":24326,"nickName":"天才用户1539"},{"cardNo":24327,"nickName":"璐璐"},{"cardNo":24328,"nickName":"丽丽"},{"cardNo":24329,"nickName":"毛毛"}]
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
         * cardNo : 24320
         * nickName : 天才用户9496
         */

        private int cardNo;
        private String nickName;

        public int getCardNo() {
            return cardNo;
        }

        public void setCardNo(int cardNo) {
            this.cardNo = cardNo;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }
    }
}
