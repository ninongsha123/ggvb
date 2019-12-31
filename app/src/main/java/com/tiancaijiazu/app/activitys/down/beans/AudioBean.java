package com.tiancaijiazu.app.activitys.down.beans;

/**
 * Created by Administrator on 2019/8/22/022.
 */

public class AudioBean {
    String name;
    String AbsolutePath;
    String time;
    String fileSize;
    boolean isbo;

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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public boolean isIsbo() {
        return isbo;
    }

    public void setIsbo(boolean isbo) {
        this.isbo = isbo;
    }
}
