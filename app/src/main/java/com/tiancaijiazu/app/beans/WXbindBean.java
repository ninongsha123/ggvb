package com.tiancaijiazu.app.beans;

/**
 * Created by Administrator on 2019/6/14/014.
 */

public class WXbindBean {
    /**
     * code : 0
     * msg : OK
     * result : {"access_token":"eyJhbGciOiJSUzI1NiIsImtpZCI6IjNBOUI5OUZEREJFQzhGRjM2NkVEMjNCNTU3NDNGQzRGM0M0MDBCMjAiLCJ0eXAiOiJKV1QiLCJ4NXQiOiJPcHVaX2R2c2pfTm03U08xVjBQOFR6eEFDeUEifQ.eyJuYmYiOjE1NjA0OTA4ODgsImV4cCI6MTU2MDQ5ODA4OCwiaXNzIjoiaHR0cDovLzE5Mi4xNjguMC4yMjAiLCJhdWQiOlsiaHR0cDovLzE5Mi4xNjguMC4yMjAvcmVzb3VyY2VzIiwiY29tX3RpYW5jYWlqaWF6dV9hcGlfdjEiXSwiY2xpZW50X2lkIjoiY29tX3RpYW5jYWlqaWF6dV9hcHBfbW9iaWxlIiwic3ViIjoiMTUwMTA1NjUyMjciLCJhdXRoX3RpbWUiOjE1NjA0OTA4ODcsImlkcCI6ImxvY2FsIiwiVXNlcklkIjoiOTU2Nzk3NDUyNDU4ODAzMiIsIlVzZXJBdmF0YXIiOiJodHRwOi8vMTkyLjE2OC4wLjIwMC9pbWFnZXMvaHR0cDovL3RoaXJkd3gucWxvZ28uY24vbW1vcGVuL3ZpXzMyL0RZQUlPZ3E4M2VyM1E5V3ZBZG9Ccm5sZWliWmdJazB2MU9zR2FzWXZRaWNUcXpLUTMxVGlhdGhYMnZOTUdpYnRTMkEycEpBRWJzM3gzR0tSc2tndkdCN213QS8xMzIiLCJuYW1lIjoi5p2o5petIiwicm9sZSI6Im5vcm1hbCIsInNjb3BlIjpbImNvbV90aWFuY2FpamlhenVfYXBpX3YxIiwib2ZmbGluZV9hY2Nlc3MiXSwiYW1yIjpbImN1c3RvbSJdfQ.RHTJUjvSUo26pSYL3o24fmeQKKwgGIHKNJGG3ma2GHTKvBSHYzdLigywDFnVnESHtVmqfaEel1mX7Grshflo7Cu5O-K2W7ITfV2_kS91e6CugxBqI2lvWcFJMe2xUxS2Ts0z_jC7r9ltDabStD49_vEUhqs-1Mb9AG5yMtrsRxMseSoCJZMv9GCPDzYkZ5NksOMtna16e_uVNW9zb_It8eE2pYVJpOn-bxMEXogvd-n7xqeI02kR8OdEl1s7-zfqtWLe3sqGmGrhbmajjluzlldQCFr0-KNJnCQbNhxbQ4BiPVWaX-nJe62PnSEYuOjkPxW_v_0B9nYukDyX7w8Enw","expires_in":7200,"token_type":"Bearer","refresh_token":"addf9a738331d07129a5fc79b9d86bd855328f91bb6bf5f59a455d6832573168"}
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
         * access_token : eyJhbGciOiJSUzI1NiIsImtpZCI6IjNBOUI5OUZEREJFQzhGRjM2NkVEMjNCNTU3NDNGQzRGM0M0MDBCMjAiLCJ0eXAiOiJKV1QiLCJ4NXQiOiJPcHVaX2R2c2pfTm03U08xVjBQOFR6eEFDeUEifQ.eyJuYmYiOjE1NjA0OTA4ODgsImV4cCI6MTU2MDQ5ODA4OCwiaXNzIjoiaHR0cDovLzE5Mi4xNjguMC4yMjAiLCJhdWQiOlsiaHR0cDovLzE5Mi4xNjguMC4yMjAvcmVzb3VyY2VzIiwiY29tX3RpYW5jYWlqaWF6dV9hcGlfdjEiXSwiY2xpZW50X2lkIjoiY29tX3RpYW5jYWlqaWF6dV9hcHBfbW9iaWxlIiwic3ViIjoiMTUwMTA1NjUyMjciLCJhdXRoX3RpbWUiOjE1NjA0OTA4ODcsImlkcCI6ImxvY2FsIiwiVXNlcklkIjoiOTU2Nzk3NDUyNDU4ODAzMiIsIlVzZXJBdmF0YXIiOiJodHRwOi8vMTkyLjE2OC4wLjIwMC9pbWFnZXMvaHR0cDovL3RoaXJkd3gucWxvZ28uY24vbW1vcGVuL3ZpXzMyL0RZQUlPZ3E4M2VyM1E5V3ZBZG9Ccm5sZWliWmdJazB2MU9zR2FzWXZRaWNUcXpLUTMxVGlhdGhYMnZOTUdpYnRTMkEycEpBRWJzM3gzR0tSc2tndkdCN213QS8xMzIiLCJuYW1lIjoi5p2o5petIiwicm9sZSI6Im5vcm1hbCIsInNjb3BlIjpbImNvbV90aWFuY2FpamlhenVfYXBpX3YxIiwib2ZmbGluZV9hY2Nlc3MiXSwiYW1yIjpbImN1c3RvbSJdfQ.RHTJUjvSUo26pSYL3o24fmeQKKwgGIHKNJGG3ma2GHTKvBSHYzdLigywDFnVnESHtVmqfaEel1mX7Grshflo7Cu5O-K2W7ITfV2_kS91e6CugxBqI2lvWcFJMe2xUxS2Ts0z_jC7r9ltDabStD49_vEUhqs-1Mb9AG5yMtrsRxMseSoCJZMv9GCPDzYkZ5NksOMtna16e_uVNW9zb_It8eE2pYVJpOn-bxMEXogvd-n7xqeI02kR8OdEl1s7-zfqtWLe3sqGmGrhbmajjluzlldQCFr0-KNJnCQbNhxbQ4BiPVWaX-nJe62PnSEYuOjkPxW_v_0B9nYukDyX7w8Enw
         * expires_in : 7200
         * token_type : Bearer
         * refresh_token : addf9a738331d07129a5fc79b9d86bd855328f91bb6bf5f59a455d6832573168
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
