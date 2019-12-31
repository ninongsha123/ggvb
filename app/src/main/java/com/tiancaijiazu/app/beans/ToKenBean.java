package com.tiancaijiazu.app.beans;

/**
 * Created by Administrator on 2019/5/18/018.
 */

public class ToKenBean {
    /**
     * code : 0
     * msg : OK
     * result : {"access_token":"eyJhbGciOiJSUzI1NiIsImtpZCI6IjNBOUI5OUZEREJFQzhGRjM2NkVEMjNCNTU3NDNGQzRGM0M0MDBCMjAiLCJ0eXAiOiJKV1QiLCJ4NXQiOiJPcHVaX2R2c2pfTm03U08xVjBQOFR6eEFDeUEifQ.eyJuYmYiOjE1NTgxNTc5NTgsImV4cCI6MTU1ODE2NTE1OCwiaXNzIjoiaHR0cDovLzE5Mi4xNjguMC4yMjAiLCJhdWQiOlsiaHR0cDovLzE5Mi4xNjguMC4yMjAvcmVzb3VyY2VzIiwiY29tX3RpYW5jYWlqaWF6dV9hcGlfdjEiXSwiY2xpZW50X2lkIjoiY29tX3RpYW5jYWlqaWF6dV9hcHBfbW9iaWxlIiwic3ViIjoiMTMyNDA4MTExODciLCJhdXRoX3RpbWUiOjE1NTgxNTc5NTgsImlkcCI6ImxvY2FsIiwiVXNlcklkIjoiMTEyOTYyMjQ2MzY0MzI1ODg4MCIsIm5hbWUiOiIiLCJyb2xlIjoibm9ybWFsIiwic2NvcGUiOlsiY29tX3RpYW5jYWlqaWF6dV9hcGlfdjEiLCJvZmZsaW5lX2FjY2VzcyJdLCJhbXIiOlsiY3VzdG9tIl19.MPoS-XMm_KEIRFPzZ_mdxk-bL13lqIijr-usQRF1cPtsTMqvx6U7r8OJLTsqX_9g6pXIry87weizFvQDJXIxYFFlcsgNiiIXvFjl2pChQL4l87xq05Zkil-6acS6t782cWRpa4AObm4pzDWfL_voaOUjm2xZMul-kwmkLmuRSqix3-Tvzkb-jjCP7Zy5Vl9zUKWX-uBA0ZWIIS5wfn9lYJhDziOOTDvKhzLjyS7N4DJba_QnH1-a3HVo9mluAyqwMh_YRQOTfSTeKyewZhDvnX4zQfOeVfhjwgygIoB5gozTCUj2RFxGdMi5Ll6hdXjO8JEFwZCDZo90c8pSNnpoGw","expires_in":7200,"token_type":"Bearer","refresh_token":"cb032e79fc2225e5f99882d639160c1e97f3016e92819536381f36e7ffc93245"}
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
         * access_token : eyJhbGciOiJSUzI1NiIsImtpZCI6IjNBOUI5OUZEREJFQzhGRjM2NkVEMjNCNTU3NDNGQzRGM0M0MDBCMjAiLCJ0eXAiOiJKV1QiLCJ4NXQiOiJPcHVaX2R2c2pfTm03U08xVjBQOFR6eEFDeUEifQ.eyJuYmYiOjE1NTgxNTc5NTgsImV4cCI6MTU1ODE2NTE1OCwiaXNzIjoiaHR0cDovLzE5Mi4xNjguMC4yMjAiLCJhdWQiOlsiaHR0cDovLzE5Mi4xNjguMC4yMjAvcmVzb3VyY2VzIiwiY29tX3RpYW5jYWlqaWF6dV9hcGlfdjEiXSwiY2xpZW50X2lkIjoiY29tX3RpYW5jYWlqaWF6dV9hcHBfbW9iaWxlIiwic3ViIjoiMTMyNDA4MTExODciLCJhdXRoX3RpbWUiOjE1NTgxNTc5NTgsImlkcCI6ImxvY2FsIiwiVXNlcklkIjoiMTEyOTYyMjQ2MzY0MzI1ODg4MCIsIm5hbWUiOiIiLCJyb2xlIjoibm9ybWFsIiwic2NvcGUiOlsiY29tX3RpYW5jYWlqaWF6dV9hcGlfdjEiLCJvZmZsaW5lX2FjY2VzcyJdLCJhbXIiOlsiY3VzdG9tIl19.MPoS-XMm_KEIRFPzZ_mdxk-bL13lqIijr-usQRF1cPtsTMqvx6U7r8OJLTsqX_9g6pXIry87weizFvQDJXIxYFFlcsgNiiIXvFjl2pChQL4l87xq05Zkil-6acS6t782cWRpa4AObm4pzDWfL_voaOUjm2xZMul-kwmkLmuRSqix3-Tvzkb-jjCP7Zy5Vl9zUKWX-uBA0ZWIIS5wfn9lYJhDziOOTDvKhzLjyS7N4DJba_QnH1-a3HVo9mluAyqwMh_YRQOTfSTeKyewZhDvnX4zQfOeVfhjwgygIoB5gozTCUj2RFxGdMi5Ll6hdXjO8JEFwZCDZo90c8pSNnpoGw
         * expires_in : 7200
         * token_type : Bearer
         * refresh_token : cb032e79fc2225e5f99882d639160c1e97f3016e92819536381f36e7ffc93245
         */

        private String access_token;
        private int expires_in;
        private String token_type;
        private String refresh_token;

        public String getAccess_token() {
            return access_token;
        }

        public void setAccess_token(String access_token) {
            this.access_token = access_token;
        }

        public int getExpires_in() {
            return expires_in;
        }

        public void setExpires_in(int expires_in) {
            this.expires_in = expires_in;
        }

        public String getToken_type() {
            return token_type;
        }

        public void setToken_type(String token_type) {
            this.token_type = token_type;
        }

        public String getRefresh_token() {
            return refresh_token;
        }

        public void setRefresh_token(String refresh_token) {
            this.refresh_token = refresh_token;
        }
    }
}
