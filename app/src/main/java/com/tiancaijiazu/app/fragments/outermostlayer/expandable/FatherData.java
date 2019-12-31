package com.tiancaijiazu.app.fragments.outermostlayer.expandable;

import java.util.ArrayList;

/**
 * 一级列表数据类
 */
public class FatherData {

    private int chapterId;
    private int courseId;
    private String title;
    private int sortId;
    private ArrayList<ChildrenData> contentsList;

    public FatherData() {
        super();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSortId() {
        return sortId;
    }

    public void setSortId(int sortId) {
        this.sortId = sortId;
    }

    public ArrayList<ChildrenData> getContentsList() {
        return contentsList;
    }

    public void setContentsList(ArrayList<ChildrenData> contentsList) {
        this.contentsList = contentsList;
    }

    public FatherData(int chapterId, int courseId, String title, int sortId, ArrayList<ChildrenData> contentsList) {

        this.chapterId = chapterId;
        this.courseId = courseId;
        this.title = title;
        this.sortId = sortId;
        this.contentsList = contentsList;
    }
}
