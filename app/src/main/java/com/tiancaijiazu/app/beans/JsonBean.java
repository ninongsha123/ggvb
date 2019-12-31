package com.tiancaijiazu.app.beans;

/**
 * Created by Administrator on 2019/6/22/022.
 */

public class JsonBean {
    private long skuId;
    private long stockId;
    private int quantity;

    public JsonBean(long skuId, long stockId, int quantity) {
        this.skuId = skuId;
        this.stockId = stockId;
        this.quantity = quantity;
    }

    public long getSkuId() {
        return skuId;
    }

    public void setSkuId(long skuId) {
        this.skuId = skuId;
    }

    public long getStockId() {
        return stockId;
    }

    public void setStockId(long stockId) {
        this.stockId = stockId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
