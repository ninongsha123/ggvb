package com.tiancaijiazu.app.beans;

/**
 * Created by Administrator on 2019/6/24/024.
 */

public class AlipayBean {
    /**
     * code : 0
     * msg : OK
     * result : app_id=2019062465618885&biz_content=%7b++++%22body%22%3a%22%e6%b5%8b%e8%af%95%e5%95%86%e5%93%81%22%2c++++%22subject%22%3a%22%e6%b5%8b%e8%af%95%e5%95%86%e5%93%81%22%2c++++%22out_trade_no%22%3a%2219689116191035392%22%2c++++%22total_amount%22%3a0.02%2c++++%22product_code%22%3a%22QUICK_MSECURITY_PAY%22++%7d&charset=utf-8&format=json&method=alipay.trade.app.pay&notify_url=http%3a%2f%2fwww.tiancaijiazu.com%2findex.html&sign_type=RSA2&timestamp=2019-06-24+18%3a07%3a31&version=1.0&sign=XroFv0hA0Kk5g8F4RyetARbohYmiVZPrGJsM0ifY%2bjIwqMzJwKhn2b58kyiLbItp6r8vwTl8ME3WaOAgehnWuvhSEu2vQI3rDRhloVJN1dxg%2bV7FZHineNdf%2bvX5fUrtXppytnuvdFiyggG%2fjY8cILYjY8tnejk1FVvgOSiRA107FU%2f57MPrQC%2bo9vIg4Obvy6vU6OApp4KIq4sqQRfY2HK%2bGP%2b2FzlVPnOfeWIBYROoserLFUypdApOUIPbQpZjfeisWdpUnby4zm7A7ILKkGRC3f4ywsOqY2H4%2f%2fbEZxloPMYeIiUGOK0MBtX50Np%2bSy10MciPq%2fMNRZY5C051Kg%3d%3d
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
