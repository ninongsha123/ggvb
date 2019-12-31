package com.tiancaijiazu.app.utils.events;

/**
 * Created by Administrator on 2019/6/26/026.
 */

public class ControlEvent {

    private boolean mIsbo;

    public ControlEvent(boolean isbo) {
        this.mIsbo = isbo;
    }

    public boolean isIsbo() {
        return mIsbo;
    }

    public void setIsbo(boolean isbo) {
        mIsbo = isbo;
    }
}
