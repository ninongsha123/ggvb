package com.tiancaijiazu.app.beans;

/**
 * Created by Administrator on 2019/6/26/026.
 */

public class CourseBean {
    private String title;
    private String picUri;
    private String courseId;
    private float promoPrice;

    public CourseBean(String title, String picUri, String courseId, float promoPrice) {
        this.title = title;
        this.picUri = picUri;
        this.courseId = courseId;
        this.promoPrice = promoPrice;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPicUri() {
        return picUri;
    }

    public void setPicUri(String picUri) {
        this.picUri = picUri;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public float getPromoPrice() {
        return promoPrice;
    }

    public void setPromoPrice(float promoPrice) {
        this.promoPrice = promoPrice;
    }
}
