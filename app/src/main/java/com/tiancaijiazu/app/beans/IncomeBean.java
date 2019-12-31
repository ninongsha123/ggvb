package com.tiancaijiazu.app.beans;

public class IncomeBean {

    /**
     * code : 0
     * msg : OK
     * result : {"income_Total":6,"income_CurrentMonth":2,"income_LastMonth":1,"onlineCompanyNo":"588032","myteamCount":13}
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
         * income_Total : 6.0
         * income_CurrentMonth : 2.0
         * income_LastMonth : 1.0
         * onlineCompanyNo : 588032
         * myteamCount : 13
         */

        private double income_Total;
        private double income_CurrentMonth;
        private double income_LastMonth;
        private String onlineCompanyNo;
        private int myteamCount;


        public double getIncome_Total() {
            return income_Total;
        }

        public void setIncome_Total(double income_Total) {
            this.income_Total = income_Total;
        }

        public double getIncome_CurrentMonth() {
            return income_CurrentMonth;
        }

        public void setIncome_CurrentMonth(double income_CurrentMonth) {
            this.income_CurrentMonth = income_CurrentMonth;
        }

        public double getIncome_LastMonth() {
            return income_LastMonth;
        }

        public void setIncome_LastMonth(double income_LastMonth) {
            this.income_LastMonth = income_LastMonth;
        }

        public String getOnlineCompanyNo() {
            return onlineCompanyNo;
        }

        public void setOnlineCompanyNo(String onlineCompanyNo) {
            this.onlineCompanyNo = onlineCompanyNo;
        }

        public int getMyteamCount() {
            return myteamCount;
        }

        public void setMyteamCount(int myteamCount) {
            this.myteamCount = myteamCount;
        }
    }
}
