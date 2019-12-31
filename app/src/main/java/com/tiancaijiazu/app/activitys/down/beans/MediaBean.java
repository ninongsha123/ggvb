package com.tiancaijiazu.app.activitys.down.beans;

/**
 * Created by Administrator on 2019/8/20/020.
 */

public class MediaBean {
    String name;
    String AbsolutePath;
    String fileSize;
    boolean isbo;

    public boolean isIsbo() {
        return isbo;
    }

    public void setIsbo(boolean isbo) {
        this.isbo = isbo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbsolutePath() {
        return AbsolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        AbsolutePath = absolutePath;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }
}
