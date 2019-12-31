package com.tiancaijiazu.app.beans;

/**
 * Created by Administrator on 2019/8/20/020.
 */

public class VideoExtractBean {
    private long contentsId;
    private String title;
    private int duration;
    private long chapterId;
    private long courseId;
    private int type;
    private String picUri;
    private String mediaUri;
    private int isFree;
    private String description;

    public VideoExtractBean(long contentsId, String title, int duration, long chapterId, long courseId, int type, String picUri, String mediaUri, int isFree, String description) {
        this.contentsId = contentsId;
        this.title = title;
        this.duration = duration;
        this.chapterId = chapterId;
        this.courseId = courseId;
        this.type = type;
        this.picUri = picUri;
        this.mediaUri = mediaUri;
        this.isFree = isFree;
        this.description = description;
    }

    public long getContentsId() {
        return contentsId;
    }

    public void setContentsId(long contentsId) {
        this.contentsId = contentsId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public long getChapterId() {
        return chapterId;
    }

    public void setChapterId(long chapterId) {
        this.chapterId = chapterId;
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPicUri() {
        return picUri;
    }

    public void setPicUri(String picUri) {
        this.picUri = picUri;
    }

    public String getMediaUri() {
        return mediaUri;
    }

    public void setMediaUri(String mediaUri) {
        this.mediaUri = mediaUri;
    }

    public int getIsFree() {
        return isFree;
    }

    public void setIsFree(int isFree) {
        this.isFree = isFree;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
